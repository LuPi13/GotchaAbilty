package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import yasking.lupi13.gotchaability.*;

import java.util.Arrays;
import java.util.List;

public class EmeraldFix implements Listener {
    private GotchaAbility plugin;

    String name = "에메랄드 수리";
    String codename = "EmeraldFix";
    String grade = "A";
    Material material = Material.EMERALD;
    String[] strings = {ChatColor.WHITE + "인벤토리에서 에메랄드로 장비를 우클릭하면", ChatColor.WHITE + "내구도 20을 수리합니다.",
            ChatColor.WHITE + "" + ChatColor.ITALIC + "이 능력은 특정 조건을 통해" , ChatColor.WHITE + "" + ChatColor.ITALIC + "다른 능력을 해금할 수 있습니다.",
            QuestManager.getQuests(codename).get(0).toPlainText()};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public EmeraldFix(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
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
