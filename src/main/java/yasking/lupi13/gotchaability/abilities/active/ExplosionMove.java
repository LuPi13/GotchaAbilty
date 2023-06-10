package yasking.lupi13.gotchaability.abilities.active;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;
import yasking.lupi13.gotchaability.events.ATTRunnable;

import java.util.*;
import java.util.function.Function;

public class ExplosionMove implements Listener {
    private GotchaAbility plugin;

    String name = "익스플로전 무브";
    String codename = "ExplosionMove";
    String grade = "B*";
    Material material = Material.HONEYCOMB;
    String[] strings = {ChatColor.WHITE + "왼손이 리볼버 캐논으로 고정됩니다.", ChatColor.WHITE + "F키를 눌러 짧은 전방에 6의 폭발피해를 주며", ChatColor.WHITE + "시전자는 반대방향으로 강하게 밀려납니다.",
            ChatColor.WHITE + "최대 6번까지 축적됩니다. (쿨타임: 6초)", ChatColor.WHITE + "from. " + ChatColor.BOLD + "MAPLESTORY"};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    String activeName = "리볼버 캐논";
    String[] activeStrings = {ChatColor.WHITE + "F키를 누르면 전방에 폭발피해를 줍니다."};
    ItemStack active = Functions.makeActiveItem(material, activeName, Arrays.asList(activeStrings));

    public ExplosionMove(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.activeList.add(item);
        ItemManager.hardDenyList.add(active);
        ItemManager.codenameMap.put(item, codename);
        ItemManager.activeMap.put(item, active);
    }


    public static Map<Player, Integer> timer = new HashMap<>();
    public static Map<Player, Integer> count = new HashMap<>();

    @EventHandler
    public void onSwapHand(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            if (event.getMainHandItem() != null && event.getMainHandItem().equals(active)) {
                event.setCancelled(true);
                if ((count.get(player) == null) || (count.get(player) >= 1)) {
                    count.put(player, count.get(player) - 1);

                    Interaction hitbox = player.getWorld().spawn(player.getEyeLocation().add(player.getLocation().getDirection().multiply(2)).subtract(0, 1.5, 0), Interaction.class);
                    hitbox.setGravity(false);
                    hitbox.setInvulnerable(true);
                    hitbox.setInteractionHeight(3F);
                    hitbox.setInteractionWidth(3F);

                    for (Entity entity : player.getNearbyEntities(4, 4, 4)) {
                        if ((entity instanceof LivingEntity) && !entity.equals(player) && (entity.getBoundingBox().overlaps(hitbox.getBoundingBox()))) {
                            ((LivingEntity) entity).damage(6, player);
                            entity.setLastDamageCause(new EntityDamageEvent(player, EntityDamageEvent.DamageCause.ENTITY_EXPLOSION, 6));
                        }
                    }
                    hitbox.remove();

                    Vector direction = player.getLocation().getDirection();
                    Vector vector = player.getLocation().getDirection().multiply(-1.5);
                    vector.setY(vector.getY() / 2);
                    if (vector.getY() >= 0) {
                        vector.add(new Vector(0, 0.2, 0));
                    }
                    player.setVelocity(vector);


                    for (int i = 1; i <= 100; i++) {
                        player.getWorld().spawnParticle(Particle.FLAME, player.getEyeLocation(), 0, direction.getX() + (Math.random() * 1) - 0.5, direction.getY() + (Math.random() * 1) - 0.5, direction.getZ() + (Math.random() * 1) - 0.5, (Math.random() * 0.3) + 0.1);
                        player.getWorld().spawnParticle(Particle.SMOKE_NORMAL, player.getEyeLocation(), 0, direction.getX() + (Math.random() * 1) - 0.5, direction.getY() + (Math.random() * 1) - 0.5, direction.getZ() + (Math.random() * 1) - 0.5, (Math.random() * 0.3) + 0.1);
                    }

                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 0.7F, 1F);
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.7F, 2F);
                }
            }
        }
    }
}
