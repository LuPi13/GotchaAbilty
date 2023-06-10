package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.*;

public class DetailedStrike implements Listener {
    private GotchaAbility plugin;

    String name = "섬세한 일격";
    String codename = "DetailedStrike";
    String grade = "A";
    Material material = Material.BLADE_POTTERY_SHERD;
    String[] strings = {ChatColor.WHITE + "가하는 모든 피해가 절반으로 줄어드는 대신,", ChatColor.WHITE + "적의 남은 체력에 비례하여 확률적으로 즉사시킵니다."};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public DetailedStrike(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }



    public static List<LivingEntity> freeze = new ArrayList<>();

    public static Map<Player, Integer> timer = new HashMap<>();


    public void probDeath(Player player, LivingEntity entity) {
        double maxHealth = Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue();
        double health = entity.getHealth();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            double prob = Math.pow((maxHealth - health) / maxHealth, 2);
            if (prob >= Math.random()) {
                freeze.add(entity);
                timer.put(player, 1);
                entity.setAI(false);
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VEX_DEATH, SoundCategory.PLAYERS, 1F, 2F);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (entity.isDead()) {
                            cancel();
                        }
                        if (timer.get(player) >= 10) {
                            BoundingBox boundingBox = entity.getBoundingBox();
                            Location corner;
                            Vector vector;

                            double ran = Math.random();
                            if (ran <= 0.25) {
                                corner = new Location(entity.getWorld(), boundingBox.getMinX(), boundingBox.getMinY(), boundingBox.getMinZ());
                                vector = new Vector(boundingBox.getWidthX(), boundingBox.getHeight(), boundingBox.getWidthZ());
                            }
                            else if (ran <= 0.5) {
                                corner = new Location(entity.getWorld(), boundingBox.getMinX(), boundingBox.getMinY(), boundingBox.getMaxZ());
                                vector = new Vector(boundingBox.getWidthX(), boundingBox.getHeight(), boundingBox.getWidthZ() * -1);
                            }
                            else if (ran <= 0.75) {
                                corner = new Location(entity.getWorld(), boundingBox.getMaxX(), boundingBox.getMinY(), boundingBox.getMinZ());
                                vector = new Vector(boundingBox.getWidthX() * -1, boundingBox.getHeight(), boundingBox.getWidthZ());
                            }
                            else {
                                corner = new Location(entity.getWorld(), boundingBox.getMaxX(), boundingBox.getMinY(), boundingBox.getMaxZ());
                                vector = new Vector(boundingBox.getWidthX() * -1, boundingBox.getHeight(), boundingBox.getWidthZ() * -1);
                            }

                            for (double i = 0.0; i <= 100.0; i++) {
                                Location location = corner.add(vector.clone().multiply(i / 5000.0));
                                Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB((int) Math.round(Math.random() * 20 + 50), (int) Math.round(Math.random() * 10), (int) Math.round(Math.random() * 20 + 50)), 0.5F);
                                entity.getWorld().spawnParticle(Particle.REDSTONE, location, 3, 0.01, 0.01, 0.01, 0.01, dustOptions);
                            }

                            entity.setAI(true);
                            freeze.remove(entity);
                            entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_ALLAY_DEATH, 1f, 2f);
                            entity.damage(2147483647, player);
                            entity.setLastDamageCause(new EntityDamageEvent(player, EntityDamageEvent.DamageCause.MAGIC, 2147483647));
                            cancel();
                        }
                        timer.put(player, timer.get(player) + 1);
                    }
                }.runTaskTimer(plugin, 0L, 1L);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            if (event.getEntity() instanceof LivingEntity) {
                Player player = (Player) event.getDamager();
                LivingEntity entity = ((LivingEntity) event.getEntity());
                List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
                if (abilities.contains(codename)) {
                    event.setDamage(event.getDamage() * 0.5);
                    probDeath(player, entity);
                }
            }
        }
        if ((event.getDamager() instanceof Projectile)) {
            if (((Projectile) event.getDamager()).getShooter() != null && ((Projectile) event.getDamager()).getShooter() instanceof Player) {
                Player player = ((Player) ((Projectile) event.getDamager()).getShooter());
                if (event.getEntity() instanceof LivingEntity) {
                    LivingEntity entity = ((LivingEntity) event.getEntity());
                    List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
                    if (abilities.contains(codename)) {
                        event.setDamage(event.getDamage() * 0.5);
                        probDeath(player, entity);
                    }
                }
            }
        }

        if ((event.getDamager() instanceof LivingEntity) && (freeze.contains(((LivingEntity) event.getDamager())))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(EntityInteractEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            if (freeze.contains(((LivingEntity) event.getEntity()))) {
                event.setCancelled(true);
            }
        }
    }
}
