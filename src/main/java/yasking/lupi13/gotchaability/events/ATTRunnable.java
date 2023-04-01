package yasking.lupi13.gotchaability.events;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.abilities.active.Chronobreak;
import yasking.lupi13.gotchaability.abilities.active.TimeLeaper;
import yasking.lupi13.gotchaability.abilities.active.TimeLeaperPlus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ATTRunnable implements Listener {
    static Plugin plugin = GotchaAbility.getPlugin(GotchaAbility.class);

    public static void init() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : plugin.getServer().getOnlinePlayers()) {
                    List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
                    if (abilities.contains("TimeLeaper")) {
                        TimeLeaper.timer.putIfAbsent(player, -10);
                        if (TimeLeaper.timer.get(player) <= -1) {
                            TimeLeaper.locations.computeIfAbsent(player, k -> new ArrayList<>());
                            TimeLeaper.locations.get(player).add(player.getLocation());
                            TimeLeaper.healths.computeIfAbsent(player, k -> new ArrayList<>());
                            TimeLeaper.healths.get(player).add(player.getHealth());
                            if (TimeLeaper.locations.get(player).size() >= 61) {
                                TimeLeaper.locations.get(player).remove(0);
                                TimeLeaper.healths.get(player).remove(0);
                            }
                        }
                        if ((TimeLeaper.coolTime.get(player) == null) || ((TimeLeaper.coolTime.get(player) != null) && (System.currentTimeMillis() - TimeLeaper.coolTime.get(player) >= 30000))) {
                            for (int i = TimeLeaper.locations.get(player).size() - 1; i >= 0; i--) {
                                Location location = TimeLeaper.locations.get(player).get(i);
                                if (i == 0) {
                                    player.spawnParticle(Particle.BUBBLE_POP, location.clone().add(0, 0.9, 0), 3, 0.15, 0.3, 0.15, 0.1);
                                }
                                else {
                                    player.spawnParticle(Particle.WATER_BUBBLE, location.clone().add(0, 0.9, 0), 1, 0, 0, 0, 0);
                                }
                            }
                        }
                    }
                    if (abilities.contains("TimeLeaperPlus")) {
                        TimeLeaperPlus.timer.putIfAbsent(player, -10);
                        if (TimeLeaperPlus.timer.get(player) <= -1) {
                            TimeLeaperPlus.locations.computeIfAbsent(player, k -> new ArrayList<>());
                            TimeLeaperPlus.locations.get(player).add(player.getLocation());
                            TimeLeaperPlus.healths.computeIfAbsent(player, k -> new ArrayList<>());
                            TimeLeaperPlus.healths.get(player).add(player.getHealth());
                            if (TimeLeaperPlus.locations.get(player).size() >= 61) {
                                TimeLeaperPlus.locations.get(player).remove(0);
                                TimeLeaperPlus.healths.get(player).remove(0);
                            }
                        }
                        if ((TimeLeaperPlus.coolTime.get(player) == null) || ((TimeLeaperPlus.coolTime.get(player) != null) && (System.currentTimeMillis() - TimeLeaperPlus.coolTime.get(player) >= 20000))) {
                            for (int i = TimeLeaperPlus.locations.get(player).size() - 1; i >= 0; i--) {
                                Location location = TimeLeaperPlus.locations.get(player).get(i);
                                if (i == 0) {
                                    player.spawnParticle(Particle.BUBBLE_POP, location.clone().add(0, 0.9, 0), 3, 0.15, 0.3, 0.15, 0.1);
                                }
                                else {
                                    player.spawnParticle(Particle.WATER_BUBBLE, location.clone().add(0, 0.9, 0), 1, 0, 0, 0, 0);
                                }
                            }
                        }
                    }
                    if (abilities.contains("Chronobreak")) {
                        Chronobreak.timer.putIfAbsent(player, -10);
                        if (Chronobreak.timer.get(player) <= -1) {
                            Chronobreak.locations.computeIfAbsent(player, k -> new ArrayList<>());
                            Chronobreak.locations.get(player).add(player.getLocation());
                            Chronobreak.healths.computeIfAbsent(player, k -> new ArrayList<>());
                            Chronobreak.healths.get(player).add(player.getHealth());
                            if (Chronobreak.locations.get(player).size() >= 61) {
                                Chronobreak.locations.get(player).remove(0);
                                Chronobreak.healths.get(player).remove(0);
                            }
                        }
                        if ((Chronobreak.coolTime.get(player) == null) || ((Chronobreak.coolTime.get(player) != null) && (System.currentTimeMillis() - Chronobreak.coolTime.get(player) >= 20000))) {
                            for (int i = Chronobreak.locations.get(player).size() - 1; i >= 0; i--) {
                                Location location = Chronobreak.locations.get(player).get(i);
                                if (i == 0) {
                                    player.spawnParticle(Particle.BUBBLE_POP, location.clone().add(0, 0.9, 0), 3, 0.15, 0.3, 0.15, 0.1);
                                }
                                else {
                                    player.spawnParticle(Particle.WATER_BUBBLE, location.clone().add(0, 0.9, 0), 1, 0, 0, 0, 0);
                                }
                            }
                        }
                    }
                    if (abilities.contains("Illusion")) {
                        List<LivingEntity> entities = new ArrayList<>();
                        for (Entity entity : player.getNearbyEntities(32, 32, 32)) {
                            if (entity instanceof LivingEntity) {
                                entities.add(((LivingEntity) entity));
                            }
                        }

                        Vector playerDirection = player.getLocation().getDirection();
                        for (LivingEntity entity : entities) {
                            Vector entityDirection = entity.getLocation().getDirection();
                            try {
                                if (playerDirection.dot(entityDirection) <= Math.cos(Math.toRadians(170)) && player.getWorld().rayTraceEntities(player.getEyeLocation(), playerDirection, 64, 0.1, entity1 -> !entity1.equals(player)).getHitEntity().equals(entity)) {
                                    entity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 2, true, true, true));
                                    entity.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 60, 2, true, true, true));
                                    ((Mob) entity).setTarget(null);
                                }
                            }
                            catch (NullPointerException ignored) {
                            }

                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }
}
