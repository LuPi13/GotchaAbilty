package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.Arrays;
import java.util.List;

public class BlockThrower implements Listener {
    private GotchaAbility plugin;

    String name = "투척 설치";
    String codename = "BlockThrower";
    String grade = "B";
    Material material = Material.STONE;
    String[] strings = {ChatColor.WHITE + "솔리드 블럭류의 아이템을 우클릭하면 던지고,", ChatColor.WHITE + "떨어진 자리에 설치됩니다.", ChatColor.WHITE + "적이 맞으면 2의 피해를 줍니다."};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public BlockThrower(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }




    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            if (event.getItem() != null) {
                for (ItemStack item : ItemManager.softDenyList) {
                    if (event.getItem().isSimilar(item)) {
                        event.setCancelled(true);
                        return;
                    }
                }
                for (ItemStack item : ItemManager.hardDenyList) {
                    if (event.getItem().isSimilar(item)) {
                        event.setCancelled(true);
                        return;
                    }
                }

                if (event.getItem().getType().isSolid()) {
                    if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                        Snowball snowball = ((Snowball) player.getWorld().spawnEntity(player.getEyeLocation(), EntityType.SNOWBALL));
                        snowball.setShooter(player);
                        snowball.setItem(new ItemStack(event.getMaterial()));
                        snowball.setCustomName("bt");
                        snowball.setCustomNameVisible(false);
                        snowball.setVelocity(player.getLocation().getDirection());
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1F, 1.2F);
                        if (!player.getGameMode().equals(GameMode.CREATIVE)) {
                            event.getItem().setAmount(event.getItem().getAmount() - 1);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onLand(ProjectileHitEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player)) {
            return;
        }
        Player player = ((Player) event.getEntity().getShooter());
        if (event.getEntity().getCustomName() != null && event.getEntity().getCustomName().equals("bt")) {
            Snowball bt = ((Snowball) event.getEntity());
            if (event.getHitBlock() != null && event.getHitBlockFace() != null) {
                if (event.getHitBlockFace().equals(BlockFace.UP)) {
                    bt.getWorld().getBlockAt(event.getHitBlock().getLocation().add(0, 1, 0)).setType(bt.getItem().getType(), true);
                }
                else if (event.getHitBlockFace().equals(BlockFace.DOWN)) {
                    bt.getWorld().getBlockAt(event.getHitBlock().getLocation().add(0, -1, 0)).setType(bt.getItem().getType(), true);
                }
                else if (event.getHitBlockFace().equals(BlockFace.WEST)) {
                    bt.getWorld().getBlockAt(event.getHitBlock().getLocation().add(-1, 0, 0)).setType(bt.getItem().getType(), true);
                }
                else if (event.getHitBlockFace().equals(BlockFace.EAST)) {
                    bt.getWorld().getBlockAt(event.getHitBlock().getLocation().add(1, 0, 0)).setType(bt.getItem().getType(), true);
                }
                else if (event.getHitBlockFace().equals(BlockFace.NORTH)) {
                    bt.getWorld().getBlockAt(event.getHitBlock().getLocation().add(0, 0, -1)).setType(bt.getItem().getType(), true);
                }
                else if (event.getHitBlockFace().equals(BlockFace.SOUTH)) {
                    bt.getWorld().getBlockAt(event.getHitBlock().getLocation().add(0, 0, 1)).setType(bt.getItem().getType(), true);
                }
            }
            if (event.getHitEntity() != null && event.getHitEntity() instanceof LivingEntity) {
                LivingEntity target = ((LivingEntity) event.getHitEntity());
                target.damage(2, player);
            }
        }
    }
}
