package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.Arrays;
import java.util.List;

public class AlchemyPlus implements Listener {
    private GotchaAbility plugin;

    String name = "연금술+...?";
    String codename = "AlchemyPlus";
    String grade = "B*";
    Material material = Material.RAW_GOLD;
    String[] strings = {Functions.getItemStackFromMap("Alchemy").getItemMeta().getDisplayName() + ChatColor.WHITE + " 능력을 계승합니다.", ChatColor.WHITE + "석탄 확률이 크게 줄고, 금 확률이 크게 늘어납니다."};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public AlchemyPlus(GotchaAbility plugin) {
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
                    if (random < 0.05) {
                        player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.COAL, 1));
                        player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 0.4F, 1.7F);
                    }
                    else if (random < 0.25) {
                        player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.IRON_NUGGET, 1));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_TOOLSMITH, 1.0F, 1.5F);
                    }
                    else if (random < 0.35) {
                        player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.IRON_INGOT, 1));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_TOOLSMITH, 1.0F, 1.5F);
                    }
                    else if (random < 0.6) {
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
}
