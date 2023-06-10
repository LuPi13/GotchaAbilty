package yasking.lupi13.gotchaability.abilities.passive;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
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
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoaringFlame implements Listener {
    private GotchaAbility plugin;

    String name = "포효하는 불길";
    String codename = "RoaringFlame";
    String grade = "A*";
    Material material = Material.BURN_POTTERY_SHERD;
    String[] strings = {Functions.getItemStackFromMap("ShieldStrike").getItemMeta().getDisplayName() + ChatColor.WHITE + " 능력을 계승합니다.", ChatColor.WHITE + "적과 부딪히면 5초간 불태우고,", ChatColor.WHITE + "전방으로 불을 지핍니다."};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public RoaringFlame(GotchaAbility plugin) {
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
                                        player.getWorld().spawnParticle(Particle.FLAME, player.getLocation().add(0, 0.9, 0), 20, 0.2, 0.5, 0.2, 0, null, true);
                                        for (Entity entity : player.getNearbyEntities(4, 4, 4)) {
                                            if (entity instanceof LivingEntity) {
                                                LivingEntity target = ((LivingEntity) entity);
                                                if (player.getBoundingBox().overlaps(target.getBoundingBox())) {
                                                    target.setFireTicks(100);
                                                    target.damage(8, player);
                                                    target.setVelocity(vector.get(player).multiply(1.2).setY(0.3));
                                                    player.setVelocity(vector.get(player).multiply(-0.1).setY(0.2));
                                                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1, 1F);
                                                    target.getWorld().spawnParticle(Particle.FLAME, entity.getLocation(), 100, 0.1, 0.1, 0.1, 1, null, true);

                                                    Vector sight = player.getLocation().getDirection().setY(0).normalize();
                                                    Vector adder = new Vector().zero();
                                                    for (int x = -1; x <= 1; x++) {
                                                        for (int y = 0; y <= 2; y++) {
                                                            for (int z = 1; z <= 7; z++) {
                                                                adder.zero();
                                                                adder.add(player.getLocation().getDirection().rotateAroundY(Math.toRadians(90)).multiply(x));
                                                                adder.add(new Vector(0, y, 0));
                                                                adder.add(player.getLocation().getDirection().multiply(z));
                                                                Block block = player.getLocation().add(sight).add(adder).getBlock();
                                                                if (block.getBlockData().getMaterial().equals(Material.AIR)) {
                                                                    block.setType(Material.FIRE);
                                                                }
                                                            }
                                                        }
                                                    }

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
