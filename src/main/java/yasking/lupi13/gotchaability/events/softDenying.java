package yasking.lupi13.gotchaability.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class softDenying implements Listener {
    private GotchaAbility plugin;

    List<InventoryType> allowed = new ArrayList<>();
    public softDenying(GotchaAbility plugin) {
        this.plugin = plugin;

        allowed.add(InventoryType.CREATIVE);
        allowed.add(InventoryType.CHEST);
        allowed.add(InventoryType.BARREL);
        allowed.add(InventoryType.SHULKER_BOX);
        allowed.add(InventoryType.DISPENSER);
        allowed.add(InventoryType.DROPPER);
        allowed.add(InventoryType.ENDER_CHEST);
        allowed.add(InventoryType.HOPPER);
        allowed.add(InventoryType.PLAYER);
        allowed.add(InventoryType.CRAFTING);
    }

    //상호작용
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        for (ItemStack itemStack : ItemManager.softDenyList) {
            if ((event.getItem() != null) &&(event.getItem().isSimilar(itemStack))) {
                event.setCancelled(true);
            }
        }
    }

    //소모성 인벤토리(조합대 등)에 넣기 금지
    @EventHandler
    public void onCreate(InventoryClickEvent event) {
        for (ItemStack itemStack : ItemManager.softDenyList) {
            try {
                if (event.getCursor().isSimilar(itemStack)) {
                    if (!allowed.contains(event.getClickedInventory().getType())) {
                        event.setCancelled(true);
                    }
                }
                if (event.isShiftClick() || event.isLeftClick() || event.isRightClick() || event.getClick().isKeyboardClick()) {
                    if (event.getCurrentItem().isSimilar(itemStack)) {
                        if (!allowed.contains(event.getView().getType())) {
                            event.setCancelled(true);
                        }
                    }
                }
            }
            catch (NullPointerException ignored) {
            }
        }
    }

}
