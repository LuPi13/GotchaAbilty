package yasking.lupi13.gotchaability.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class HardDenying implements Listener {
    private GotchaAbility plugin;

    //상호작용
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (ItemManager.hardDenyList.contains(event.getItem())) {
            event.setCancelled(true);
        }
    }


    //죽으면 템 없애버리기
    @EventHandler
    public void onDropItem(ItemSpawnEvent event) {
        if (ItemManager.hardDenyList.contains(event.getEntity().getItemStack())) {
            event.getEntity().remove();
            event.setCancelled(true);
        }
    }


    //부활 시 바로 꽂아주기
    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        try {
            if (ItemManager.activeList.contains(Functions.getItemStackFromMap(abilities.get(0)))) {
                player.getInventory().setItemInOffHand(ItemManager.activeMap.get(Functions.getItemStackFromMap(abilities.get(0))));
            }
        }
        catch (IllegalArgumentException | IndexOutOfBoundsException ignored) {
        }
    }

    //인벤토리 내 이동 금지
    @EventHandler
    public void onInventory(InventoryClickEvent event) {
        Player player = ((Player) event.getWhoClicked());
        ItemStack item = event.getCurrentItem();
        if (item != null && !item.getType().equals(Material.AIR)) {
            if (ItemManager.hardDenyList.contains(item)) {
                if (event.isShiftClick() || event.isLeftClick() || event.isRightClick()) {
                    event.getCursor().setAmount(0);
                    event.setCancelled(true);
                }
            }
        }
    }

    //템버리기
    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (ItemManager.hardDenyList.contains(event.getItemDrop().getItemStack())) {
            event.setCancelled(true);
        }
    }

}
