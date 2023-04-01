package yasking.lupi13.gotchaability.abilities.active;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.*;
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

public class OverBalance implements Listener {
    private GotchaAbility plugin;

    String name = "밸런스 붕괴";
    String codename = "OverBalance";
    String grade = "S";
    Material material = Material.ECHO_SHARD;
    String[] strings = {ChatColor.WHITE + "1회용입니다. 왼손이 레일건으로 고정됩니다.", ChatColor.WHITE + "F키를 눌러 사용 시 5초간 움직일 수 없고,", ChatColor.WHITE + "5초 후 체력이 1만 남으며, 127의 피해를"
    , ChatColor.WHITE + "주며 모든 지형을 관통하는 거대한 탄환을", ChatColor.WHITE + "발사합니다.", ChatColor.WHITE + "from. " + ChatColor.AQUA + "" + ChatColor.BOLD + "Blue" + ChatColor.WHITE + "" + ChatColor.BOLD + "Archive"};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    String activeName = "레일건";
    String[] activeStrings = {ChatColor.WHITE + "F키를 누르면 거대한 탄환을 발사합니다."};
    ItemStack active = Functions.makeActiveItem(material, activeName, Arrays.asList(activeStrings));

    public OverBalance(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.activeList.add(item);
        ItemManager.hardDenyList.add(active);
        ItemManager.codenameMap.put(item, codename);
        ItemManager.activeMap.put(item, active);
    }



    public static Map<Player, Integer> timer = new HashMap<>();
    public static Map<ArmorStand, Vector> railVector = new HashMap<>();

    @EventHandler
    public void onSwapHand(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            if (event.getMainHandItem() != null && event.getMainHandItem().equals(active)) {
                Location fixed = player.getLocation();
                player.getInventory().setItemInOffHand(null);
                abilities.remove(0);
                FileManager.getAbilityConfig().set(player.getDisplayName() + "@ability", abilities);
                FileManager.saveAbility();

                event.setCancelled(true);
                timer.put(player, 1);

                ArmorStand rail = ((ArmorStand) player.getWorld().spawnEntity(player.getEyeLocation().add(player.getLocation().getDirection().multiply(3)), EntityType.ARMOR_STAND));
                rail.setMarker(true);
                rail.setGravity(false);
                rail.setInvisible(true);
                rail.setBasePlate(false);
                rail.setInvulnerable(true);
                rail.getLocation().setDirection(fixed.getDirection());
                railVector.put(rail, fixed.getDirection());

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (timer.get(player) <= 99) {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2, 7, true, false, false));
                            player.teleport(fixed);
                            player.setVelocity(new Vector().zero());
                        }
                        if ((timer.get(player) <= 90) && (timer.get(player) % 5 == 0)) {
                            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_INSIDE, 1F, (timer.get(player) / 53.3F) + 0.5F);
                        }

                        Vector y0vector = new Vector(railVector.get(rail).getX(), 0, railVector.get(rail).getZ());
                        y0vector.normalize();
                        Vector vertical;
                        if (y0vector.length() <= 0.01) {
                            vertical = new Vector(1, 0, 0);
                        }
                        else {
                            vertical = y0vector.crossProduct(railVector.get(rail)).normalize().multiply(2);
                        }
                        vertical.rotateAroundAxis(railVector.get(rail), timer.get(player) / 2.0);

                        if (timer.get(player) >= 1) {
                            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB((int) Math.round(Math.random() * 30) + 225, (int) Math.round(Math.random() * 20) + 50, (int) Math.round(Math.random() * 20) + 50), 2F);
                            rail.getWorld().spawnParticle(Particle.REDSTONE, rail.getLocation().add(vertical), Math.min(timer.get(player) , 10), 0.2, 0.2, 0.2, 0.01, dustOptions, true);
                        }
                        if (timer.get(player) >= 16) {
                            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB((int) Math.round(Math.random() * 30) + 225, (int) Math.round(Math.random() * 30) + 225, (int) Math.round(Math.random() * 20) + 50), 2F);
                            rail.getWorld().spawnParticle(Particle.REDSTONE, rail.getLocation().add(vertical.rotateAroundAxis(railVector.get(rail), Math.toRadians(60))), Math.min(timer.get(player) - 15 , 10), 0.2, 0.2, 0.2, 0.01, dustOptions, true);
                        }
                        if (timer.get(player) >= 31) {
                            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB((int) Math.round(Math.random() * 20) + 50, (int) Math.round(Math.random() * 30) + 225, (int) Math.round(Math.random() * 20) + 50), 2F);
                            rail.getWorld().spawnParticle(Particle.REDSTONE, rail.getLocation().add(vertical.rotateAroundAxis(railVector.get(rail), Math.toRadians(60))), Math.min(timer.get(player) - 30 , 10), 0.2, 0.2, 0.2, 0.01, dustOptions, true);
                        }
                        if (timer.get(player) >= 46) {
                            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB((int) Math.round(Math.random() * 20) + 50, (int) Math.round(Math.random() * 30) + 225, (int) Math.round(Math.random() * 30) + 225), 2F);
                            rail.getWorld().spawnParticle(Particle.REDSTONE, rail.getLocation().add(vertical.rotateAroundAxis(railVector.get(rail), Math.toRadians(60))), Math.min(timer.get(player) - 45 , 10), 0.2, 0.2, 0.2, 0.01, dustOptions, true);
                        }
                        if (timer.get(player) >= 61) {
                            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB((int) Math.round(Math.random() * 20) + 50, (int) Math.round(Math.random() * 20) + 50, (int) Math.round(Math.random() * 30) + 225), 2F);
                            rail.getWorld().spawnParticle(Particle.REDSTONE, rail.getLocation().add(vertical.rotateAroundAxis(railVector.get(rail), Math.toRadians(60))), Math.min(timer.get(player) - 60 , 10), 0.2, 0.2, 0.2, 0.01, dustOptions, true);
                        }
                        if (timer.get(player) >= 76) {
                            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB((int) Math.round(Math.random() * 30) + 225, (int) Math.round(Math.random() * 20) + 50, (int) Math.round(Math.random() * 30) + 225), 2F);
                            rail.getWorld().spawnParticle(Particle.REDSTONE, rail.getLocation().add(vertical.rotateAroundAxis(railVector.get(rail), Math.toRadians(60))), Math.min(timer.get(player) - 75 , 10), 0.2, 0.2, 0.2, 0.01, dustOptions, true);
                        }

                        if (timer.get(player) == 100) {
                            player.setHealth(1.0);
                        }

                        if (timer.get(player) >= 101) {
                            rail.teleport(rail.getLocation().add(railVector.get(rail).normalize().multiply(Math.min((timer.get(player) - 100.0) / 20.0, 1.5))));
                            rail.getWorld().playSound(rail.getLocation(), Sound.ENTITY_ZOMBIE_CONVERTED_TO_DROWNED, 0.8F, 0.5F);
                            rail.getWorld().playSound(rail.getLocation(), Sound.ENTITY_HUSK_CONVERTED_TO_ZOMBIE, 0.8F, 0.5F);
                            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB((int) Math.round(Math.random() * 30) + 225, (int) Math.round(Math.random() * 30) + 225, (int) Math.round(Math.random() * 30) + 225), 4F);
                            Particle.DustOptions dustOptions1 = new Particle.DustOptions(Color.fromRGB((int) Math.round(Math.random() * 30), (int) Math.round(Math.random() * 30), (int) Math.round(Math.random() * 30)), 4F);
                            rail.getWorld().spawnParticle(Particle.REDSTONE, rail.getLocation(), Math.min(timer.get(player) - 100, 20), 0.6, 0.6, 0.6, 0.01, dustOptions, true);
                            rail.getWorld().spawnParticle(Particle.REDSTONE, rail.getLocation(), Math.min(timer.get(player) - 100, 10), 0.6, 0.6, 0.6, 0.01, dustOptions1, true);


                            List<Entity> entities = rail.getNearbyEntities(4, 4, 4);
                            for (Entity entity : entities) {
                                if ((entity instanceof LivingEntity) && (entity.getLocation().distance(rail.getLocation()) <= 3) && !(entity.equals(player))) {
                                    ((LivingEntity) entity).damage(127, player);
                                    entity.setLastDamageCause(new EntityDamageEvent(player, EntityDamageEvent.DamageCause.PROJECTILE, 127));
                                }
                            }
                        }

                        if (timer.get(player) >= 271) {
                            timer.put(player, 0);
                            rail.remove();
                            cancel();
                        }

                        timer.put(player, timer.get(player) + 1);
                    }
                }.runTaskTimer(plugin, 0L, 1L);

            }
        }
    }



}
