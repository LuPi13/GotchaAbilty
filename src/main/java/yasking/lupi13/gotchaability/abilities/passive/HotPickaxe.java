package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import yasking.lupi13.gotchaability.*;

import java.util.Arrays;
import java.util.List;

public class HotPickaxe implements Listener {
    private GotchaAbility plugin;

    String name = "뜨거운 곡괭이";
    String codename = "HotPickaxe";
    String grade = "B";
    Material material = Material.NETHERITE_PICKAXE;
    String[] strings = {ChatColor.WHITE + "곡괭이로 광물을 캐면 익혀서 나오고, ", ChatColor.WHITE + "공격하면 적을 3초간 불태웁니다.",
            ChatColor.WHITE + "" + ChatColor.ITALIC + "이 능력은 특정 조건을 통해" , ChatColor.WHITE + "" + ChatColor.ITALIC + "다른 능력을 해금할 수 있습니다.",
            QuestManager.getQuests(codename).get(0).toPlainText()};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public HotPickaxe(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
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
}
