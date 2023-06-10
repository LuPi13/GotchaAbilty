package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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

public class Counter implements Listener {
    private GotchaAbility plugin;

    String name = "카운터";
    String codename = "Counter";
    String grade = "B";
    Material material = Material.DIAMOND_SWORD;
    String[] strings = {ChatColor.WHITE + "검을 들고 우클릭하면 2초간 반격태세에 ", ChatColor.WHITE + "들어갑니다. 시전 후 0.5초 이내에 피해를 입으면", ChatColor.WHITE + "피해를 무시하고 전방에 2배의 피해로 반격합니다.",
            ChatColor.WHITE + "반격태세 중에는 움직일 수 없습니다.", ChatColor.WHITE + "" + ChatColor.ITALIC + "from. " + ChatColor.BOLD + "Super Smash Bros."};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public Counter(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }



    public static Map<Player, Long> timer = new HashMap<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            Material[] swords = {Material.WOODEN_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLDEN_SWORD, Material.DIAMOND_SWORD, Material.NETHERITE_SWORD};
            if (event.getItem() != null) {
                for (Material material : swords) {
                    if (event.getItem().getType().equals(material)) {
                        if (player.getCooldown(event.getItem().getType()) == 0) {
                            for (Material material1 : swords) {
                                player.setCooldown(material1, 100);
                            }
                            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_CONVERTED_TO_DROWNED, 0.7F, 2F);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 7, true, false, false));
                            timer.put(player, System.currentTimeMillis());
                        }
                        break;
                    }
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
                if ((timer.get(player) != null) && (System.currentTimeMillis() - timer.get(player) <= 500)) {
                    double damage = event.getDamage();
                    player.getWorld().playSound(player.getLocation(), Sound.ITEM_TRIDENT_HIT_GROUND, 1F, 2F);
                    Interaction hitbox = player.getWorld().spawn(player.getEyeLocation().add(player.getLocation().getDirection().multiply(2).subtract(new Vector(0, 0.5, 0))), Interaction.class);
                    hitbox.setGravity(false);
                    hitbox.setInvulnerable(true);
                    hitbox.setInteractionHeight(1);
                    hitbox.setInteractionWidth(3);
                    List<Entity> entities = player.getNearbyEntities(4, 4, 4);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for (Entity entity : entities) {
                                if (entity instanceof LivingEntity) {
                                    if (entity.getBoundingBox().overlaps(hitbox.getBoundingBox())) {
                                        ((LivingEntity) entity).damage(damage * 2);
                                        entity.setVelocity((player.getLocation().getDirection().setY(0).normalize().multiply(0.5).setY(0.1)));
                                    }
                                    player.removePotionEffect(PotionEffectType.SLOW);
                                }
                            }
                            player.spawnParticle(Particle.SWEEP_ATTACK, player.getEyeLocation().add(player.getLocation().getDirection().normalize().multiply(0.1)), 1, 0, 0, 0);
                            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 1F, 1F);
                            hitbox.remove();
                            cancel();
                        }
                    }.runTaskTimer(plugin, 10L, 1L);
                    event.setCancelled(true);
                }
            }
        }
    }
}
