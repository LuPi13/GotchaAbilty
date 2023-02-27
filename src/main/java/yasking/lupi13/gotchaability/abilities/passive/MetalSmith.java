package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.Arrays;
import java.util.List;

public class MetalSmith implements Listener {
    private GotchaAbility plugin;

    String name = "광석 세공사";
    String codename = "MetalSmith";
    String grade = "A*";
    Material material = Material.SMITHING_TABLE;
    String[] strings = {Functions.makeDisplayName("연금술", "B") + ChatColor.WHITE + ", "  + Functions.makeDisplayName("뜨거운 곡괭이", "B") + ChatColor.WHITE + ", ", Functions.makeDisplayName("에메랄드 수리", "A") + ChatColor.WHITE + " 능력을",
            ChatColor.WHITE + "모두 사용합니다."};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public MetalSmith(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }




    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            if (event.getItem() != null && event.getItem().getType().equals(Material.COPPER_INGOT) && event.getItem().getAmount() >= 10) {
                if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    event.getItem().setAmount(event.getItem().getAmount() - 10);
                    double random = Math.random();
                    if (random < 0.2) {
                        player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.COAL, 1));
                        player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 0.4F, 1.7F);
                    }
                    else if (random < 0.4) {
                        player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.IRON_NUGGET, 1));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_TOOLSMITH, 1.0F, 1.5F);
                    }
                    else if (random < 0.5) {
                        player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.IRON_INGOT, 1));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_TOOLSMITH, 1.0F, 1.5F);
                    }
                    else if (random < 0.7) {
                        player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.GOLD_NUGGET, 1));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_TOOLSMITH, 1.0F, 1.5F);
                    }
                    else if (random < 0.8) {
                        player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.GOLD_INGOT, 1));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_TOOLSMITH, 1.0F, 1.5F);
                    }
                    else if (random < 0.9) {
                        player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.REDSTONE, 1));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_TOOLSMITH, 1.0F, 1.5F);
                    }
                    else if (random < 0.95) {
                        player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.LAPIS_LAZULI, 1));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_TOOLSMITH, 1.0F, 1.5F);
                    }
                    else if (random < 0.99) {
                        player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.EMERALD, 1));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_TOOLSMITH, 1.0F, 1.5F);
                    }
                    else if (random < 0.999) {
                        player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.DIAMOND, 1));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_TOOLSMITH, 1.0F, 1.5F);
                        player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 2.0F, 1.0F);
                    }
                    else {
                        player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.NETHERITE_INGOT, 1));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_TOOLSMITH, 1.0F, 1.5F);
                        player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 2.0F, 1.0F);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPunch(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if (event.getEntity() instanceof LivingEntity) {
                LivingEntity target = (LivingEntity) event.getEntity();
                List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
                if (abilities.contains(codename)) {
                    if (player.getInventory().getItemInMainHand().getType().equals(Material.WOODEN_PICKAXE)
                            || player.getInventory().getItemInMainHand().getType().equals(Material.STONE_PICKAXE)
                            || player.getInventory().getItemInMainHand().getType().equals(Material.IRON_PICKAXE)
                            || player.getInventory().getItemInMainHand().getType().equals(Material.GOLDEN_PICKAXE)
                            || player.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_PICKAXE)
                            || player.getInventory().getItemInMainHand().getType().equals(Material.NETHERITE_PICKAXE)) {
                        target.setFireTicks(60);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBreak(BlockDropItemEvent event) {
        Player player = event.getPlayer();
        List<Item> items = event.getItems();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            for (Item item : items) {
                if (item.getItemStack().getType().equals(Material.RAW_COPPER)) {
                    item.getItemStack().setType(Material.COPPER_INGOT);
                }
                if (item.getItemStack().getType().equals(Material.RAW_COPPER_BLOCK)) {
                    item.getItemStack().setType(Material.COPPER_BLOCK);
                }
                if (item.getItemStack().getType().equals(Material.RAW_IRON)) {
                    item.getItemStack().setType(Material.IRON_INGOT);
                }
                if (item.getItemStack().getType().equals(Material.RAW_IRON_BLOCK)) {
                    item.getItemStack().setType(Material.IRON_BLOCK);
                }
                if (item.getItemStack().getType().equals(Material.RAW_GOLD)) {
                    item.getItemStack().setType(Material.GOLD_INGOT);
                }
                if (item.getItemStack().getType().equals(Material.RAW_GOLD_BLOCK)) {
                    item.getItemStack().setType(Material.GOLD_BLOCK);
                }
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = ((Player) event.getWhoClicked());
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            if (event.getCursor() != null && event.getCursor().getType().equals(Material.EMERALD)) {
                if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null && ((Damageable) event.getCurrentItem().getItemMeta()).hasDamage()) {
                    if (event.isRightClick()) {
                        ItemStack item = event.getCurrentItem();
                        Damageable data = ((Damageable) item.getItemMeta());
                        data.setDamage(data.getDamage() - 20);
                        item.setItemMeta(data);
                        event.setCurrentItem(item);
                        event.getCursor().setAmount(event.getCursor().getAmount() - 1);
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1F, 2F);
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
