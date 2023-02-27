package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.scheduler.BukkitRunnable;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.Arrays;
import java.util.List;

public class ProjSword implements Listener {
    private GotchaAbility plugin;

    String name = "검기";
    String codename = "ProjSword";
    String grade = "B";
    String displayName = Functions.makeDisplayName(name, grade);
    Material material = Material.NETHERITE_SWORD;
    String[] strings = {ChatColor.WHITE + "검을 휘두르면 투사체가 나갑니다.", ChatColor.WHITE + "발사 시 내구도가 1 더 감소합니다.", ChatColor.WHITE + "피해량: 3, 쿨타임: 1초"};
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public ProjSword(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }




    @EventHandler
    public void usingSword(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getItem() == null) {
            return;
        }
        if (event.getItem().getType().equals(Material.WOODEN_SWORD) ||
                event.getItem().getType().equals(Material.STONE_SWORD) ||
                event.getItem().getType().equals(Material.GOLDEN_SWORD) ||
                event.getItem().getType().equals(Material.IRON_SWORD) ||
                event.getItem().getType().equals(Material.DIAMOND_SWORD) ||
                event.getItem().getType().equals(Material.NETHERITE_SWORD)) {
            List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
            if (abilities.contains(codename)) {
                if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                    if (player.getCooldown(event.getMaterial()) > 0) {
                        return;
                    }
                    player.setCooldown(event.getMaterial(), 20);
                    Damageable meta = (Damageable) event.getItem().getItemMeta();
                    if (meta.getDamage() >= event.getItem().getType().getMaxDurability()) {
                        event.getItem().setAmount(0);
                        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                        return;
                    }
                    meta.setDamage(meta.getDamage() + 1);
                    event.getItem().setItemMeta(meta);

                    Snowball proj = (Snowball)player.getWorld().spawnEntity(player.getEyeLocation().add(player.getLocation().getDirection()), EntityType.SNOWBALL);
                    proj.setShooter(player);
                    proj.setItem(new ItemStack(Material.CONDUIT, 1));
                    proj.setVisualFire(true);
                    proj.setGravity(false);
                    proj.setCustomName("swordproj");
                    proj.setCustomNameVisible(false);
                    proj.setVelocity(player.getLocation().getDirection().multiply(3));
                    player.playSound(player.getLocation(), Sound.ITEM_DYE_USE, 1, 2);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (player.getLocation().distance(proj.getLocation()) >= 127) {
                                proj.remove();
                                cancel();
                            }
                            if (proj.isDead()) {
                                cancel();
                            }
                        }
                    }.runTaskTimer(plugin, 0L, 1L);
                }
            }
        }
    }

    @EventHandler
    public void onHit(ProjectileHitEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getEntity().getShooter();
        if (event.getEntity().getCustomName() != null && event.getEntity().getCustomName().equals("swordproj")) {
            if (event.getHitEntity() != null && event.getHitEntity() instanceof LivingEntity) {
                ((LivingEntity) event.getHitEntity()).damage(3, player);
            }
        }
    }
}
