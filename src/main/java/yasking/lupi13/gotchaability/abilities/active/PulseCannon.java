package yasking.lupi13.gotchaability.abilities.active;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import yasking.lupi13.gotchaability.*;
import yasking.lupi13.gotchaability.events.ATTRunnable;

import java.util.*;

public class PulseCannon implements Listener {
    private GotchaAbility plugin;

    String name = "펄스캐논";
    String codename = "PulseCannon";
    String grade = "C*";
    Material material = Material.INK_SAC;
    String[] strings = {Functions.getItemStackFromMap("HandCannon").getItemMeta().getDisplayName() + ChatColor.WHITE + " 능력을 계승합니다.", ChatColor.WHITE + "1회 사격에 5점사를 가하며,",
            ChatColor.WHITE + "10% 확률로 무적시간을 제공하지 않습니다."};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    String activeName = "펄스캐논";
    String[] activeStrings = {ChatColor.WHITE + "F키를 누르면 작은 탄환을 5개 발사합니다."};
    ItemStack active = Functions.makeActiveItem(material, activeName, Arrays.asList(activeStrings));

    public PulseCannon(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.activeList.add(item);
        ItemManager.hardDenyList.add(active);
        ItemManager.codenameMap.put(item, codename);
        ItemManager.activeMap.put(item, active);
    }


    public static Map<Player, Integer> playerTimer = new HashMap<>();

    @EventHandler
    public void onSwapHand(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            if (event.getMainHandItem() != null && event.getMainHandItem().equals(active)) {
                event.setCancelled(true);
                if ((playerTimer.get(player) == null) || (playerTimer.get(player) <= 0)) {
                    playerTimer.put(player, 1);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (playerTimer.get(player) <= 5) {
                                Snowball bullet = player.getWorld().spawn(player.getEyeLocation().add(player.getLocation().getDirection()), Snowball.class);
                                bullet.setGravity(false);
                                bullet.setInvulnerable(true);
                                bullet.setPersistent(true);
                                bullet.setCustomName("pulsecannon");
                                bullet.setCustomNameVisible(false);
                                bullet.setItem(new ItemStack(Material.STONE_BUTTON));
                                bullet.setVelocity(player.getLocation().getDirection().multiply(3));
                                bullet.setShooter(player);
                                ATTRunnable.timer.put(bullet, 1);
                            }
                            else {
                                playerTimer.put(player, -1);
                                cancel();
                            }
                            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.5F, (float) ((Math.random() * 1.5) + 0.5));
                            playerTimer.put(player, playerTimer.get(player) + 1);
                        }
                    }.runTaskTimer(plugin, 0L, 1L);
                }
            }
        }
    }
    
    @EventHandler
    public void onHit(ProjectileHitEvent event) {
        if ((event.getEntity() instanceof Snowball) && (Objects.equals(event.getEntity().getCustomName(), "pulsecannon"))) {
            Projectile bullet = event.getEntity();
            if (event.getHitEntity() instanceof LivingEntity) {
                ((LivingEntity) event.getHitEntity()).damage(1.0, ((Entity) bullet.getShooter()));
                if (Math.random() <= 0.1) {
                    ((LivingEntity) event.getHitEntity()).setNoDamageTicks(0);
                }
            }
        }
    }
}
