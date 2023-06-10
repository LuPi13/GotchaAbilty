package yasking.lupi13.gotchaability.events;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.abilities.active.*;
import yasking.lupi13.gotchaability.abilities.passive.Cloak;
import yasking.lupi13.gotchaability.abilities.passive.SuperCloak;

import java.util.*;

public class ATTRunnable implements Listener {
    static Plugin plugin = GotchaAbility.getPlugin(GotchaAbility.class);

    public static Map<Snowball, Integer> timer = new HashMap<>();


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
                    if (abilities.contains("Cloak")) {
                        Cloak.gauge.putIfAbsent(player, 1000);
                        Cloak.toggle.putIfAbsent(player, false);
                        int newGauge = Cloak.gauge.get(player) / 20;
                        StringBuilder text = new StringBuilder("Cloak Gauge 【");
                        for (int i = 1; i <= (newGauge / 20); i++) {
                            text.append("■");
                        }
                        for (int j = 10; j > (newGauge / 20); j--) {
                            text.append("□");
                        }
                        text.append("】 ");
                        text.append(newGauge);
                        if (Cloak.gauge.get(player) == 1) {
                            Cloak.toggle.put(player, false);
                        }
                        if (Cloak.toggle.get(player)) {
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + text));
                            Cloak.gauge.put(player, Cloak.gauge.get(player) - 1);
                        }
                        else if (!Cloak.toggle.get(player)) {
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.YELLOW + "" + ChatColor.BOLD + text));
                            if (Cloak.gauge.get(player) <= 4000) {
                                Cloak.gauge.put(player, Cloak.gauge.get(player) + 1);
                            }
                        }
                    }
                    if (abilities.contains("SuperCloak")) {
                        SuperCloak.gauge.putIfAbsent(player, 1250);
                        SuperCloak.toggle.putIfAbsent(player, false);
                        int newGauge = SuperCloak.gauge.get(player) / 20;
                        StringBuilder text = new StringBuilder("SuperCloak Gauge 【");
                        for (int i = 1; i <= (newGauge / 25); i++) {
                            text.append("■");
                        }
                        for (int j = 10; j > (newGauge / 25); j--) {
                            text.append("□");
                        }
                        text.append("】 ");
                        text.append(newGauge);
                        if (SuperCloak.gauge.get(player) == 1) {
                            SuperCloak.toggle.put(player, false);
                        }
                        if (SuperCloak.toggle.get(player)) {
                            if ((Functions.velocity.get(player) != null) && (Functions.velocity.get(player).length() >= 0.03)) {
                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + text));
                                SuperCloak.gauge.put(player, SuperCloak.gauge.get(player) - 1);
                            }
                            else {
                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + text));
                            }
                        }
                        else if (!SuperCloak.toggle.get(player)) {
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.YELLOW + "" + ChatColor.BOLD + text));
                            if ((SuperCloak.gauge.get(player) <= 5000)) {
                                int delta = (Math.random() <= 0.5) ? 1 : 2;
                                SuperCloak.gauge.put(player, SuperCloak.gauge.get(player) + delta);
                            }
                        }
                    }
                    if (abilities.contains("ExplosionMove")) {
                        ExplosionMove.timer.putIfAbsent(player, 1);
                        ExplosionMove.count.putIfAbsent(player, 0);
                        if ((ExplosionMove.count.get(player) == null) || (ExplosionMove.count.get(player) <= 5)) {
                            if (ExplosionMove.timer.get(player) % 120 == 0) {
                                ExplosionMove.count.put(player, ExplosionMove.count.get(player) + 1);
                                ExplosionMove.timer.put(player, 1);
                                player.playSound(player.getLocation(), Sound.ITEM_CROSSBOW_LOADING_END, 1F, 1F);
                            }
                            ExplosionMove.timer.put(player, ExplosionMove.timer.get(player) + 1);
                        }
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.YELLOW + "" + ChatColor.BOLD + "현재 장탄 수: " + ExplosionMove.count.get(player) + "발"));
                    }
                }



                List<Object> remove = new ArrayList<>();

                for (Snowball bullet : timer.keySet()) {
                    timer.put(bullet, timer.get(bullet) + 1);
                    if ((timer.get(bullet) >= 21) || bullet.isDead()) {
                        remove.add(bullet);
                        bullet.remove();
                    }
                }
                for (Object del : remove) {
                    if (del instanceof Snowball) {
                        timer.remove(((Snowball) del));
                    }
                }


                for (Interaction bullet : ChargeShot.bulletTimer.keySet()) {
                    String name = bullet.getCustomName();
                    assert name != null;
                    Player shooter = Functions.getPlayerByName(name.split("@")[0]);
                    double damage = Double.parseDouble(name.split("@")[1]);

                    ChargeShot.bulletTimer.put(bullet, ChargeShot.bulletTimer.get(bullet) + 1);
                    bullet.teleport(bullet.getLocation().add(ChargeShot.bulletVector.get(bullet)));
                    Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB((int) (damage * 4) + 125 + (int) (Math.random() * 60), 245 + (int) (Math.random() * 10), 245 + (int) (Math.random() * 10)), (float) damage * 0.1F + 1F);
                    bullet.getWorld().spawnParticle(Particle.REDSTONE, bullet.getBoundingBox().getCenter().toLocation(bullet.getWorld()), (int) (damage / 2.0) + 16, (damage / 80.0) + 0.125, (damage / 80.0) + 0.125, (damage / 80.0) + 0.125, 0.01, dustOptions, true);
                    bullet.getWorld().playSound(bullet.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, (float) (damage * 0.15) + 0.5F);


                    for (Entity entity :bullet.getNearbyEntities(2, 2, 2)) {
                        if ((entity instanceof LivingEntity) && (!(entity.equals(shooter))) && entity.getBoundingBox().overlaps(bullet.getBoundingBox())) {
                            bullet.getWorld().playSound(bullet.getLocation(), Sound.ENTITY_EVOKER_CAST_SPELL, 1F, 2F);
                            remove.add(bullet);
                            bullet.remove();
                            ((LivingEntity) entity).damage(damage, shooter);
                        }
                    }

                    if ((!bullet.getBoundingBox().getCenter().toLocation(shooter.getWorld()).getBlock().isPassable()) || (ChargeShot.bulletTimer.get(bullet) >= 127)) {
                        remove.add(bullet);
                        bullet.remove();
                    }
                }
                for (Object del : remove) {
                    if (del instanceof Interaction) {
                        ChargeShot.bulletTimer.remove((Interaction) del);
                        ChargeShot.bulletVector.remove((Interaction) del);
                    }
                }


                for (Snowball cannon : RealHandCannon.cannons) {
                    if (cannon.isDead()) {
                        cannon.getWorld().createExplosion(cannon.getLocation(), 1.5F, false, true);
                        remove.add(cannon);
                    }
                }
                for (Object del : remove) {
                    if (del instanceof Snowball) {
                        RealHandCannon.cannons.remove(((Snowball) del));
                    }
                }

            }
        }.runTaskTimer(plugin, 0L, 1L);
    }
}
