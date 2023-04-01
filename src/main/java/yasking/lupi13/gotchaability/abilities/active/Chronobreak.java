package yasking.lupi13.gotchaability.abilities.active;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
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

public class Chronobreak implements Listener {
    private GotchaAbility plugin;

    String name = "시공간 붕괴";
    String codename = "Chronobreak";
    String grade = "S*";
    Material material = Material.MUSIC_DISC_5;
    String[] strings = {ChatColor.WHITE + "시간 역행 후 도착 지점 주변에 있는", ChatColor.WHITE + "모든 적에게 최대체력의 50%에 해당하는", ChatColor.WHITE + "피해를 줍니다. 준 피해에 비례하여 흡수효과를"
            , ChatColor.WHITE + "얻습니다. 쿨타임이 20초로 감소합니다."};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    String activeName = "시공간 붕괴";
    String[] activeStrings = {ChatColor.WHITE + "F키를 누르면 3초 전의 위치와 체력으로 돌아갑니다.", ChatColor.WHITE + "(쿨타임: 20초)"};
    ItemStack active = Functions.makeActiveItem(material, activeName, Arrays.asList(activeStrings));

    public Chronobreak(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.activeList.add(item);
        ItemManager.hardDenyList.add(active);
        ItemManager.codenameMap.put(item, codename);
        ItemManager.activeMap.put(item, active);
    }



    public static Map<Player, List<Location>> locations = new HashMap<>();
    public static Map<Player, List<Double>> healths = new HashMap<>();
    public static Map<Player, Long> coolTime = new HashMap<>();
    public static Map<Player, Integer> timer = new HashMap<>();

    @EventHandler
    public void onSwapHand(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            if (event.getMainHandItem() != null && event.getMainHandItem().equals(active)) {
                event.setCancelled(true);
                if ((coolTime.get(player) == null) || ((coolTime.get(player) != null) && (System.currentTimeMillis() - coolTime.get(player) >= 20000))) {
                    timer.put(player, locations.get(player).size() - 1);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            try {
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_SWIM, 0.2F, 2F);
                                player.setVelocity(new Vector().zero());
                                if (timer.get(player) <= 0) {
                                    player.teleport(locations.get(player).get(0));
                                }
                                else {
                                    player.teleport(locations.get(player).get(timer.get(player)));
                                }
                            }
                            catch (NullPointerException exception) {
                                player.sendMessage("이동 기록이 없습니다!");
                                cancel();
                            }

                            if (timer.get(player) <= 0) {
                                player.setHealth(healths.get(player).get(0));
                                player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 1F, 2F);
                                player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 1F, 1.7F);
                                player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 1F, 1.4F);
                                for (int i = 1; i <= 3000; i++) {
                                    Vector snow = new Vector(Math.random() - 0.5, 0, Math.random() - 0.5).normalize();
                                    player.getWorld().spawnParticle(Particle.SCULK_CHARGE_POP, player.getLocation(), 0, snow.getX(), 0, snow.getZ(), 1, null, true);
                                }
                                double damaged = 0;

                                for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
                                    if (entity instanceof LivingEntity) {
                                        damaged += ((LivingEntity) entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() / 2.0;
                                        ((LivingEntity) entity).damage(((LivingEntity) entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() / 2.0, player);
                                        entity.setLastDamageCause(new EntityDamageEvent(player, EntityDamageEvent.DamageCause.MAGIC, ((LivingEntity) entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() / 2.0));
                                    }
                                }
                                if (damaged != 0) {
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 100, Math.min(4, ((int) (damaged / 15))), true, true, true));
                                }

                                timer.put(player, -10);
                                coolTime.put(player, System.currentTimeMillis());
                                cancel();
                            }
                            timer.put(player, timer.get(player) - 2);
                        }
                    }.runTaskTimer(plugin, 0L, 1L);
                }
                else {
                    BaseComponent text = new TextComponent(ChatColor.YELLOW + "남은 쿨타임: " + (((20000 - System.currentTimeMillis() + coolTime.get(player)) / 10) / 100.0) + "초");
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, text);
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = ((Player) event.getEntity());
            List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
            if (abilities.contains(codename)) {
                if (timer.get(player) != null && timer.get(player) != -10) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
