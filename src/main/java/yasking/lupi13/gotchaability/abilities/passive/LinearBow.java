package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.Arrays;
import java.util.List;

public class LinearBow implements Listener {
    private GotchaAbility plugin;

    String name = "직선 화살";
    String codename = "LinearBow";
    String grade = "A";
    String displayName = Functions.makeDisplayName(name, grade);
    Material material = Material.ARROW;
    String[] strings = {ChatColor.WHITE + "최대로 당겨 쏜 화살이 잠시 후 직선방향의", ChatColor.WHITE + "히트스캔방식으로 발사됩니다.",
            ChatColor.WHITE + "최대 200블럭을 날아가며, 벽에 부딪히지", ChatColor.WHITE + "않으면 사라집니다."};
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public LinearBow(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }



    private Vector velocity;

    @EventHandler
    public void onBowShoot(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            Player player = (Player) event.getEntity().getShooter();
            if (event.getEntity() instanceof Arrow) {
                Arrow arrow = (Arrow) event.getEntity();
                if (arrow.getVelocity().length() >= 2.9) {
                    List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
                    if (abilities.contains(codename)) {
                        velocity = arrow.getVelocity();
                        Location location = player.getEyeLocation();
                        final int[] timer = {0};
                        final Arrow[] linarrow = new Arrow[1];
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (timer[0] == 0) {
                                    arrow.remove();
                                }
                                if (timer[0] == 5) {
                                    linarrow[0] = (Arrow) player.getWorld().spawnEntity(location, EntityType.ARROW);
                                    linarrow[0].setRotation(arrow.getLocation().getYaw(), arrow.getLocation().getPitch());
                                    linarrow[0].setVelocity(arrow.getVelocity().multiply(100));
                                    linarrow[0].setGravity(false);
                                    linarrow[0].setDamage(arrow.getDamage());
                                    linarrow[0].setCritical(arrow.isCritical());
                                    linarrow[0].setKnockbackStrength(arrow.getKnockbackStrength());
                                    linarrow[0].setGlowing(arrow.isGlowing());
                                    linarrow[0].setPickupStatus(arrow.getPickupStatus());
                                    linarrow[0].setFireTicks(arrow.getFireTicks());
                                    linarrow[0].setFreezeTicks(arrow.getFreezeTicks());
                                    linarrow[0].setPierceLevel(arrow.getPierceLevel());
                                    linarrow[0].setShooter(arrow.getShooter());
                                    linarrow[0].setShotFromCrossbow(arrow.isShotFromCrossbow());
                                    linarrow[0].setVisualFire(arrow.isVisualFire());
                                    linarrow[0].setBasePotionData(arrow.getBasePotionData());
                                    linarrow[0].setCustomName("linearbow");
                                    linarrow[0].setCustomNameVisible(false);
                                }

                                if (timer[0] >= 7 && !linarrow[0].isOnGround()) {
                                    linarrow[0].remove();
                                    cancel();
                                }

                                timer[0] += 1;
                            }
                        }.runTaskTimer(plugin, 0L, 1L);
                    }
                }

            }
            if (event.getEntity() instanceof SpectralArrow) {
                SpectralArrow arrow = (SpectralArrow) event.getEntity();
                if (arrow.getVelocity().length() >= 2.9) {
                    List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
                    if (abilities.contains(codename)) {
                        velocity = arrow.getVelocity();
                        Location location = player.getEyeLocation();
                        final int[] timer = {0};
                        final SpectralArrow[] linarrow = new SpectralArrow[1];
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (timer[0] == 0) {
                                    arrow.remove();
                                }
                                if (timer[0] == 5) {
                                    linarrow[0] = (SpectralArrow) player.getWorld().spawnEntity(location, EntityType.SPECTRAL_ARROW);
                                    linarrow[0].setRotation(arrow.getLocation().getYaw(), arrow.getLocation().getPitch());
                                    linarrow[0].setVelocity(arrow.getVelocity().multiply(100));
                                    linarrow[0].setGravity(false);
                                    linarrow[0].setDamage(arrow.getDamage());
                                    linarrow[0].setCritical(arrow.isCritical());
                                    linarrow[0].setKnockbackStrength(arrow.getKnockbackStrength());
                                    linarrow[0].setGlowing(arrow.isGlowing());
                                    linarrow[0].setPickupStatus(arrow.getPickupStatus());
                                    linarrow[0].setFireTicks(arrow.getFireTicks());
                                    linarrow[0].setFreezeTicks(arrow.getFreezeTicks());
                                    linarrow[0].setPierceLevel(arrow.getPierceLevel());
                                    linarrow[0].setShooter(arrow.getShooter());
                                    linarrow[0].setShotFromCrossbow(arrow.isShotFromCrossbow());
                                    linarrow[0].setVisualFire(arrow.isVisualFire());
                                    linarrow[0].setCustomName("linearbow");
                                    linarrow[0].setCustomNameVisible(false);
                                }

                                if (timer[0] >= 7 && !linarrow[0].isOnGround()) {
                                    linarrow[0].remove();
                                    cancel();
                                }
                                timer[0] += 1;
                            }
                        }.runTaskTimer(plugin, 0L, 1L);



                    }
                }

            }
        }
    }

    @EventHandler
    public void onHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getEntity();
            if (arrow.getCustomName() != null && arrow.getCustomName().equals("linearbow")) {
                if (event.getHitEntity() instanceof LivingEntity) {
                    arrow.setVelocity(velocity.normalize().multiply(3));
                    if (((LivingEntity) event.getHitEntity()).getNoDamageTicks() >= 1) {
                        arrow.remove();
                        return;
                    }
                    if (arrow.isShotFromCrossbow()) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                arrow.setVelocity(arrow.getVelocity().multiply(100));
                                cancel();
                            }
                        }.runTaskTimer(plugin, 1L, 1L);
                    }
                }
            }
        }
        else if (event.getEntity() instanceof SpectralArrow) {
            SpectralArrow arrow = (SpectralArrow) event.getEntity();
            if (arrow.getCustomName() != null && arrow.getCustomName().equals("linearbow")) {
                if (event.getHitEntity() instanceof LivingEntity) {
                    arrow.setVelocity(velocity.normalize().multiply(3));
                    if (((LivingEntity) event.getHitEntity()).getNoDamageTicks() >= 1) {
                        arrow.remove();
                        return;
                    }
                    if (arrow.isShotFromCrossbow()) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                arrow.setVelocity(arrow.getVelocity().multiply(100));
                                cancel();
                            }
                        }.runTaskTimer(plugin, 1L, 1L);
                    }
                }
            }
        }
    }
}
