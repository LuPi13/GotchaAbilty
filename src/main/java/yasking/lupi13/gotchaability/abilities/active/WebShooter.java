package yasking.lupi13.gotchaability.abilities.active;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class WebShooter implements Listener {
    private GotchaAbility plugin;

    String name = "웹슈터";
    String codename = "WebShooter";
    String grade = "S";
    Material material = Material.STRING;
    String[] strings = {ChatColor.WHITE + "왼손이 웹슈터로 고정됩니다.", ChatColor.WHITE + "F키를 눌러 사거리 127블럭의 거미줄을 발사합니다.",
            ChatColor.WHITE + "벽이나 개체에 닿으면 해당 방향으로 끌려갑니다.", ChatColor.WHITE + "끌려가는 중에 개체와 부딪히면", ChatColor.WHITE + "속도에 비례하는 피해를 줍니다.",
            ChatColor.WHITE + "끌려가는 중에 F키를 눌러 이동을 중지합니다.", ChatColor.WHITE + "from. " + ChatColor.BOLD + "SPIDER-MAN"};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    String activeName = "웹슈터";
    String[] activeStrings = {ChatColor.WHITE + "F키를 눌러 거미줄을 발사합니다."};
    ItemStack active = Functions.makeActiveItem(material, activeName, Arrays.asList(activeStrings));

    public WebShooter(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.activeList.add(item);
        ItemManager.hardDenyList.add(active);
        ItemManager.codenameMap.put(item, codename);
        ItemManager.activeMap.put(item, active);
    }



    public static Map<Player, Boolean> toggle = new HashMap<>();

    @EventHandler
    public void onSwapHand(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            if (event.getMainHandItem() != null && event.getMainHandItem().equals(active)) {
                event.setCancelled(true);
                if ((toggle.get(player) == null) || !toggle.get(player)) {
                    World world = player.getWorld();
                    world.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_LAUNCH, 1F, 2F);

                    Vector vector = player.getLocation().getDirection();

                    RayTraceResult result = player.getWorld().rayTrace(player.getEyeLocation(), vector, 127, FluidCollisionMode.NEVER, true, 0.1, entity -> !entity.equals(player));

                    if (result != null) {

                        if (result.getHitEntity() != null) {
                            Entity target = result.getHitEntity();
                            BoundingBox box = target.getBoundingBox();
                            player.playSound(player.getLocation(), Sound.BLOCK_CAVE_VINES_FALL, 1F, 1F);
                            world.playSound(target.getLocation(), Sound.BLOCK_CAVE_VINES_FALL, 1F, 1F);

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (player.isDead()) {
                                        toggle.put(player, false);
                                    }

                                    if (!toggle.get(player)) {
                                        cancel();
                                    }
                                    Vector movingVector = target.getLocation().subtract(player.getLocation()).toVector().length() <= 0.01 ? new Vector(0, -0.12, 0) : target.getLocation().subtract(player.getLocation()).toVector().normalize().multiply(0.2).add(new Vector(0, 0.08, 0)); //0.0784000015258789

                                    player.setVelocity(player.getVelocity().add(movingVector));

                                    if (player.getVelocity().getY() >= 0) {
                                        player.setFallDistance(0F);
                                    }

                                    if (Math.random() <= Math.max(0.01, player.getVelocity().length())) {
                                        player.playSound(player.getLocation(), Sound.ITEM_SPYGLASS_STOP_USING, 1F, Math.min(2F, ((float) player.getVelocity().length() * 2)));
                                        player.playSound(player.getLocation(), Sound.ITEM_SPYGLASS_USE, 1F, Math.min(2F, ((float) player.getVelocity().length() * 2)));
                                    }

                                    world.spawnParticle(Particle.FIREWORKS_SPARK, target.getBoundingBox().getCenter().toLocation(world), 3, box.getWidthX() * 0.3, box.getHeight() * 0.3, box.getWidthZ() * 0.3, 0);
                                    Vector vector = player.getBoundingBox().getCenter().subtract(box.getCenter()).normalize();
                                    for (double dist = 0.0; dist <= player.getBoundingBox().getCenter().distance(box.getCenter()); dist += 0.1) {
                                        world.spawnParticle(Particle.ELECTRIC_SPARK, target.getBoundingBox().getCenter().toLocation(world).add(vector.clone().multiply(dist)), 1, 0, 0, 0, 0);
                                    }

                                    List<Entity> entities = player.getNearbyEntities(4, 4, 4);
                                    for (Entity entity : entities) {
                                        if ((entity instanceof LivingEntity) && (entity.getBoundingBox().overlaps(player.getBoundingBox().expand(1.2)))) {
                                            if (((LivingEntity) entity).getNoDamageTicks() == 0) {
                                                double damage = Math.round(player.getVelocity().length() * 5);
                                                ((LivingEntity) entity).damage(damage, player);
                                                entity.setLastDamageCause(new EntityDamageEvent(player, EntityDamageEvent.DamageCause.CONTACT, damage));
                                                entity.setVelocity(player.getVelocity().clone().multiply(0.8).add(new Vector(0, 0.2, 0)));
                                                world.playSound(entity.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 1F, (float) (Math.random() * 0.2 + 0.9));
                                            }

                                            if (entity.equals(target)) {
                                                player.setVelocity(new Vector().zero());
                                                toggle.put(player, false);
                                            }
                                        }
                                    }
                                }
                            }.runTaskTimer(plugin, 0L, 1L);
                        }
                        else {
                            Location pin = result.getHitPosition().toLocation(world);
                            player.playSound(player.getLocation(), Sound.BLOCK_CAVE_VINES_FALL, 1F, 1F);
                            world.playSound(pin, Sound.BLOCK_CAVE_VINES_FALL, 1F, 1F);

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (player.isDead()) {
                                        toggle.put(player, false);
                                    }

                                    if (!toggle.get(player)) {
                                        cancel();
                                    }
                                    Vector movingVector = pin.clone().subtract(player.getLocation()).toVector().length() <= 0.01 ? new Vector(0, -0.2 ,0) : pin.clone().subtract(player.getLocation()).toVector().normalize().multiply(0.2); //0.0784000015258789
                                    if (((LivingEntity) player).isOnGround()) {
                                        movingVector.add(new Vector(0, 0.08, 0));
                                    }

                                    player.setVelocity(player.getVelocity().add(movingVector));

                                    if (player.getVelocity().getY() >= 0) {
                                        player.setFallDistance(0F);
                                    }

                                    if (Math.random() <= Math.max(0.01, player.getVelocity().length())) {
                                        player.playSound(player.getLocation(), Sound.ITEM_SPYGLASS_STOP_USING, 1F, Math.min(2F, ((float) player.getVelocity().length() * 2)));
                                        player.playSound(player.getLocation(), Sound.ITEM_SPYGLASS_USE, 1F, Math.min(2F, ((float) player.getVelocity().length() * 2)));
                                    }
                                    world.spawnParticle(Particle.FIREWORKS_SPARK, pin, 1, 0.1, 0.1, 0.1, 0);
                                    Vector vector = player.getBoundingBox().getCenter().subtract(pin.toVector()).normalize();
                                    for (double dist = 0.0; dist <= player.getBoundingBox().getCenter().distance(pin.toVector()); dist += 0.1) {
                                        world.spawnParticle(Particle.ELECTRIC_SPARK, pin.clone().add(vector.clone().multiply(dist)), 1, 0, 0, 0, 0);
                                    }

                                    List<Entity> entities = player.getNearbyEntities(4, 4, 4);
                                    for (Entity entity : entities) {
                                        if ((entity instanceof LivingEntity) && (entity.getBoundingBox().overlaps(player.getBoundingBox().expand(1.2)))) {
                                            if (((LivingEntity) entity).getNoDamageTicks() == 0) {
                                                double damage = Math.round(player.getVelocity().length() * 5);
                                                ((LivingEntity) entity).damage(damage, player);
                                                entity.setLastDamageCause(new EntityDamageEvent(player, EntityDamageEvent.DamageCause.CONTACT, damage));
                                                entity.setVelocity(player.getVelocity().clone().multiply(0.8).add(new Vector(0, 0.2, 0)));
                                                world.playSound(entity.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 1F, (float) (Math.random() * 0.2 + 0.9));
                                            }
                                        }
                                    }
                                }
                            }.runTaskTimer(plugin, 0L, 1L);
                        }
                        toggle.put(player, true);

                    }
                    else {
                        BaseComponent text = new TextComponent(ChatColor.YELLOW + "아무것도 닿지 않은 것 같습니다...");
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, text);
                    }
                }
                else {
                    toggle.put(player, false);
                }
            }
        }
    }
}
