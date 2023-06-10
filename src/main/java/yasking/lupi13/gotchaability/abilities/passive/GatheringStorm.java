package yasking.lupi13.gotchaability.abilities.passive;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Consumer;
import org.bukkit.util.Vector;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.*;

public class GatheringStorm implements Listener {
    private GotchaAbility plugin;

    String name = "폭풍 결집";
    String codename = "GatheringStorm";
    String grade = "S";
    Material material = Material.TRIDENT;
    String[] strings = {ChatColor.WHITE + "삼지창의 집전 마법부여를 대폭 강화합니다.", ChatColor.WHITE + "집전 삼지창의 투사 속도가 크게 증가합니다.",
            ChatColor.WHITE + "밤 또는 비, 뇌우 날씨 중에 삼지창을 던지면", ChatColor.WHITE + "지속적인 번개를 내리칩니다. (쿨타임: 20초)",
            ChatColor.WHITE + "" + ChatColor.ITALIC + "from. " + ChatColor.BOLD + "DESTINY 2"};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public GatheringStorm(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }


    public static Map<Player, Integer> timer = new HashMap<>();
    public static Map<Player, Long> coolTime = new HashMap<>();


    @EventHandler
    public void onTarget(ProjectileLaunchEvent event) {
        if (event.getEntity() instanceof Trident) {
            if ((event.getEntity().getShooter() != null) && (event.getEntity().getShooter() instanceof Player)) {
                Player player = ((Player) event.getEntity().getShooter());
                List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
                if (abilities.contains(codename)) {
                    Trident trident = ((Trident) event.getEntity());
                    if (trident.getItem().getEnchantments().containsKey(Enchantment.CHANNELING)) {
                        trident.setVelocity(trident.getVelocity().multiply(2));
                    }
                }
            }
        }
    }



    @EventHandler
    public void onHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Trident) {
            if ((event.getEntity().getShooter() != null) && (event.getEntity().getShooter() instanceof Player)) {
                Player player = ((Player) event.getEntity().getShooter());
                List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
                if (abilities.contains(codename)) {
                    Trident trident = ((Trident) event.getEntity());
                    if (trident.getItem().getEnchantments().containsKey(Enchantment.CHANNELING)) {
                        trident.setVelocity(trident.getVelocity().multiply(2));

                        World world = player.getWorld();
                        if ((((world.getTime() >= 14000) && (world.getTime() <= 22000)) || !world.isClearWeather())) {
                            if ((coolTime.get(player) != null) && (System.currentTimeMillis() - coolTime.get(player) < 20000)) {
                                BaseComponent text = new TextComponent(ChatColor.YELLOW + "남은 쿨타임: " + (((20000 - System.currentTimeMillis() + coolTime.get(player)) / 10) / 100.0) + "초");
                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, text);
                                return;
                            }
                            coolTime.put(player, System.currentTimeMillis());
                            Location location = (event.getHitEntity() == null) ? event.getHitBlock().getLocation().add(0.5, 1.0, 0.5) : event.getHitEntity().getLocation().getBlock().getLocation().add(0.5, 0.0, 0.5);
                            world.playSound(location, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1F, 0.5F);

                            timer.put(player, 1);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if ((timer.get(player) % 4) == 0) {
                                        List<Entity> entities = trident.getNearbyEntities(10, 2, 10);
                                        for (Entity entity : entities) {
                                            if ((entity instanceof LivingEntity) && !entity.isDead()) {
                                                LivingEntity target = ((LivingEntity) entity);
                                                if (target.getBoundingBox().getCenter().distance(location.toVector()) <= 10) {
                                                    if (Math.random() <= 0.25) {
                                                        Vector vector = target.getBoundingBox().getCenter().subtract(location.toVector()).normalize().multiply(0.1);
                                                        Location partLoc = location.clone();
                                                        target.setNoDamageTicks(0);
                                                        target.damage(2.0, player);
                                                        target.setVelocity(new Vector().zero());
                                                        target.setLastDamageCause(new EntityDamageEvent(player, EntityDamageEvent.DamageCause.LIGHTNING, 2.0));
                                                        target.setNoDamageTicks(0);
                                                        target.getWorld().playSound(target.getLocation(), Sound.ENTITY_GUARDIAN_ATTACK, 1F, 2F);
                                                        for (double dist = 0.0; dist <= target.getBoundingBox().getCenter().distance(location.toVector()); dist += 0.1) {
                                                            target.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, partLoc.add(vector), 1, 0.01, 0.01, 0.01, 0.01, null, true);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    for (int i = 0; i <= Math.min(20, timer.get(player)); i++) {
                                        world.spawnParticle(Particle.ELECTRIC_SPARK, location.clone().add(0, i * 10, 0), 10, 0.2, 3, 0.2, 0.01, null, true);
                                        world.spawnParticle(Particle.GLOW_SQUID_INK, location.clone().add(0, 0.1, 0), 1, i * 0.2, 0.1, i * 0.2, 0.01, null, true);
                                    }
                                    for (int j = 0; j <= 360; j++) {
                                        if (Math.random() <= Math.min(0.3, ((double) timer.get(player)) / 66.7)) {
                                            world.spawnParticle(Particle.SOUL_FIRE_FLAME, location.clone().add(10 * Math.sin(Math.toRadians(j)), 0.1, 10 * Math.cos(Math.toRadians(j))), 1, 0.1, 0.1, 0.1, 0.01, null, true);
                                        }
                                    }

                                    if (timer.get(player) == 20) {
                                        createLightning(world, location);
                                    }
                                    if (timer.get(player) == 65) {
                                        for (int k = 0; k < 360; k+= 60) {
                                            Location circleLoc = location.clone().add(2 * Math.sin(Math.toRadians(k)), 0.1, 2 * Math.cos(Math.toRadians(k)));
                                            createLightning(world, circleLoc);
                                        }
                                    }
                                    if (timer.get(player) == 110) {
                                        for (int k = 30; k < 360; k+= 60) {
                                            Location circleLoc = location.clone().add(4 * Math.sin(Math.toRadians(k)), 0.1, 4 * Math.cos(Math.toRadians(k)));
                                            createLightning(world, circleLoc);
                                        }
                                    }
                                    if (timer.get(player) == 155) {
                                        for (int k = 0; k < 360; k+= 60) {
                                            Location circleLoc = location.clone().add(6 * Math.sin(Math.toRadians(k)), 0.1, 6 * Math.cos(Math.toRadians(k)));
                                            createLightning(world, circleLoc);
                                        }
                                    }
                                    if (timer.get(player) == 200) {
                                        for (int k = 30; k < 360; k+= 60) {
                                            Location circleLoc = location.clone().add(8 * Math.sin(Math.toRadians(k)), 0.1, 8 * Math.cos(Math.toRadians(k)));
                                            createLightning(world, circleLoc);
                                        }
                                        timer.put(player, -1);
                                        cancel();
                                    }

                                    if (timer.get(player) >= 20) {
                                        if (Math.random() <= Math.min(0.1, (((double) timer.get(player)) - 20.0) / 400.0)) {
                                            Location ranLoc = location.clone().add(Math.random() * 9 * Math.sin(Math.random() * 2 * Math.PI), 0.1, Math.random() * 9 * Math.sin(Math.random() * 2 * Math.PI));
                                            createLightning(world, ranLoc);
                                        }
                                    }

                                    timer.put(player, timer.get(player) + 1);
                                }
                            }.runTaskTimer(plugin, 0L, 1L);
                        }
                    }
                }
            }
        }
    }


    public void createLightning(World world, Location location) {
        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB((int) (Math.random() * 20) + 30, (int) (Math.random() * 10) + 240, (int) (Math.random() * 1) + 240), 4F);
        world.spawnParticle(Particle.REDSTONE, location, 10, 1, 0.3, 1, 0.5, dustOptions);
        LightningStrike lightningStrike = world.strikeLightning(location);
        for (Player player : world.getPlayers()) {
            world.strikeLightningEffect(player.getLocation().add(0, 255, 0));
        }
    }
}
