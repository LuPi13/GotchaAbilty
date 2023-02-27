package yasking.lupi13.gotchaability.abilities.passive;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import yasking.lupi13.gotchaability.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShieldStrikePlus implements Listener {
    private GotchaAbility plugin;

    String name = "방패강타+";
    String codename = "ShieldStrikePlus";
    String grade = "B*";
    Material material = Material.SHIELD;
    String[] strings = {ChatColor.WHITE + "더 빠르고 멀리 돌진합니다. 돌진이", ChatColor.WHITE + "끝나는 시점에 부딪히거나, 적을 처치하면", ChatColor.WHITE + "쿨타임이 초기화됩니다."};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public ShieldStrikePlus(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }




    public static Map<Player, Long> time = new HashMap<>();
    public static Map<Player, Long> coolTime = new HashMap<>();
    public static Map<Player, Integer> timer = new HashMap<>();
    public static Map<Player, Vector> vector = new HashMap<>();

    @EventHandler
    public void onDoubleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            if (event.isSneaking() && player.isOnGround()) {
                if ((time.get(player) != null) && ((System.currentTimeMillis() - time.get(player)) <= 300)) {
                    if ((coolTime.get(player) == null) || ((coolTime.get(player) != null) && (System.currentTimeMillis() - coolTime.get(player) >= 10000))) {
                        if (player.getInventory().getItemInMainHand().getType().equals(Material.SHIELD)
                        ||player.getInventory().getItemInOffHand().getType().equals(Material.SHIELD)) {
                            coolTime.put(player, System.currentTimeMillis());

                            timer.put(player, 1);

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if ((timer.get(player) >= 1) && (timer.get(player) <= 5)) {
                                        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_INSIDE, 1, 0.5F);
                                    }

                                    if ((timer.get(player) >= 1) && (timer.get(player) <= 9)) {
                                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2, 7, true, false, false));
                                    }
                                    if (timer.get(player) == 10) {
                                        vector.put(player, player.getLocation().getDirection().setY(0).normalize().multiply(1.2));
                                    }
                                    if ((timer.get(player) >= 11) && (timer.get(player) <= 17)) {
                                        try {
                                            player.setVelocity(vector.get(player));
                                            player.getWorld().playSound(player.getLocation(), Sound.ITEM_TRIDENT_RIPTIDE_2, 1F, 2F);
                                        }
                                        catch (IllegalArgumentException ignored) {
                                        }
                                    }
                                    if ((timer.get(player) >= 11) && (timer.get(player) <= 22)) {
                                        for (Entity entity : player.getNearbyEntities(4, 4, 4)) {
                                            if (entity instanceof LivingEntity) {
                                                LivingEntity target = ((LivingEntity) entity);
                                                if (timer.get(player) == 22 || (target.getHealth() <= 8)) {
                                                    coolTime.put(player, coolTime.get(player) - 10000);
                                                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 2F);
                                                }

                                                if (player.getBoundingBox().overlaps(target.getBoundingBox())) {
                                                    target.damage(8, player);
                                                    target.setVelocity(vector.get(player).multiply(1.2).setY(0.3));
                                                    player.setVelocity(vector.get(player).multiply(-0.1).setY(0.2));
                                                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1.3F);
                                                    target.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, entity.getLocation(), 3, 0.1, 0.1, 0.1, 0, null, true);
                                                    timer.put(player, 23);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    if ((timer.get(player) > 22)) {
                                        timer.put(player, 0);
                                        cancel();
                                    }
                                    timer.put(player, timer.get(player) + 1);
                                }
                            }.runTaskTimer(plugin, 0L, 1L);

                            event.setCancelled(true);
                        }
                    } else {
                        BaseComponent text = new TextComponent(ChatColor.YELLOW + "남은 쿨타임: " + (((10000 - System.currentTimeMillis() + coolTime.get(player)) / 10) / 100.0) + "초");
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, text);
                    }
                }
                time.put(player, System.currentTimeMillis());
            }
        }
    }
}
