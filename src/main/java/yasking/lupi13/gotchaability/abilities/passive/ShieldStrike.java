package yasking.lupi13.gotchaability.abilities.passive;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
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

public class ShieldStrike implements Listener {
    private GotchaAbility plugin;

    String name = "방패강타";
    String codename = "ShieldStrike";
    String grade = "B";
    Material material = Material.SHIELD;
    String[] strings = {ChatColor.WHITE + "방패를 들고 빠르게 두 번 웅크려 전방으로", ChatColor.WHITE + "돌진합니다. 부딪힌 적은 8의 피해와", ChatColor.WHITE + "함께 뒤로 밀려납니다. (쿨타임: 10초)",
            ChatColor.WHITE + "" + ChatColor.ITALIC + "이 능력은 특정 조건을 통해" , ChatColor.WHITE + "" + ChatColor.ITALIC + "다른 능력을 해금할 수 있습니다.",
            QuestManager.getQuests(codename).get(0).toPlainText(), QuestManager.getQuests(codename).get(1).toPlainText()};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public ShieldStrike(GotchaAbility plugin) {
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
            if (event.isSneaking() && ((LivingEntity) player).isOnGround()) {
                if ((time.get(player) != null) && ((System.currentTimeMillis() - time.get(player)) <= 300)) {
                    if (player.getInventory().getItemInMainHand().getType().equals(Material.SHIELD)
                            ||player.getInventory().getItemInOffHand().getType().equals(Material.SHIELD)) {
                        if ((coolTime.get(player) == null) || ((coolTime.get(player) != null) && (System.currentTimeMillis() - coolTime.get(player) >= 10000))) {
                            coolTime.put(player, System.currentTimeMillis());

                            timer.put(player, 1);

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if ((timer.get(player) >= 1) && (timer.get(player) <= 5)) {
                                        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_INSIDE, 1, 0.5F);
                                    }

                                    if ((timer.get(player) >= 1) && (timer.get(player) <= 14)) {
                                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2, 7, true, false, false));
                                    }
                                    if (timer.get(player) == 15) {
                                        vector.put(player, player.getLocation().getDirection().setY(0).normalize().multiply(1.1));
                                    }
                                    if ((timer.get(player) >= 16) && (timer.get(player) <= 20)) {
                                        try {
                                            player.setVelocity(vector.get(player));
                                            player.getWorld().playSound(player.getLocation(), Sound.ITEM_TRIDENT_RIPTIDE_2, 1F, 2F);
                                        }
                                        catch (IllegalArgumentException ignored) {
                                        }
                                    }

                                    if ((timer.get(player) >= 16) && (timer.get(player) <= 25)) {
                                        for (Entity entity : player.getNearbyEntities(4, 4, 4)) {
                                            if (entity instanceof LivingEntity) {
                                                LivingEntity target = ((LivingEntity) entity);
                                                if (player.getBoundingBox().overlaps(target.getBoundingBox())) {
                                                    if (timer.get(player) == 25) {
                                                        QuestManager.clearQuest(player, "PerfectDistance", codename, "ShieldStrikePlus", 0);
                                                    }

                                                    if (player.getFireTicks() > 0 && (target.getHealth() <= 8)) {
                                                        QuestManager.clearQuest(player, "FireStrike", codename, "RoaringFlame", 1);
                                                    }

                                                    target.damage(8, player);
                                                    target.setVelocity(vector.get(player).multiply(1.2).setY(0.3));
                                                    player.setVelocity(vector.get(player).multiply(-0.1).setY(0.2));
                                                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1.3F);
                                                    target.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, entity.getLocation(), 3, 0.1, 0.1, 0.1, 0, null, true);
                                                    timer.put(player, 26);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    if ((timer.get(player) > 25)) {
                                        timer.put(player, 0);
                                        cancel();
                                    }
                                    timer.put(player, timer.get(player) + 1);
                                }
                            }.runTaskTimer(plugin, 0L, 1L);

                            event.setCancelled(true);
                        }
                        else {
                            BaseComponent text = new TextComponent(ChatColor.YELLOW + "남은 쿨타임: " + (((10000 - System.currentTimeMillis() + coolTime.get(player)) / 10) / 100.0) + "초");
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, text);
                        }
                    }
                }
                time.put(player, System.currentTimeMillis());
            }
        }
    }
}
