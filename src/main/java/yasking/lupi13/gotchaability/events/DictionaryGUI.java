package yasking.lupi13.gotchaability.events;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;
import yasking.lupi13.gotchaability.QuestManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DictionaryGUI implements Listener {
    private GotchaAbility plugin;
    public DictionaryGUI(GotchaAbility plugin) {
        this.plugin = plugin;
    }

    public static String DictionaryName = ChatColor.WHITE + "" + ChatColor.BOLD + "DICTIONARY";


    public static Inventory makeDictionaryGUI(Player player, int page) {
        Inventory inventory = Bukkit.createInventory(player, 54, DictionaryName);
        List<ItemStack> all = new ArrayList<>();
        all.addAll(ItemManager.cList);
        all.addAll(ItemManager.csList);
        all.addAll(ItemManager.bList);
        all.addAll(ItemManager.bsList);
        all.addAll(ItemManager.aList);
        all.addAll(ItemManager.asList);
        all.addAll(ItemManager.sList);
        all.addAll(ItemManager.ssList);
        all.addAll(ItemManager.sssList);

        int count = all.size();
        int maxPage = (count % 45 == 0) ? count / 45 : (count / 45) + 1;
        inventory.setItem(45, ItemManager.Close);

        if (page == maxPage) {
            for (int i = 0; i <= (count - (45 * (page - 1)) - 1); i++) {
                inventory.setItem(i, all.get((45 * (page - 1)) + i));
            }
        }
        else {
            for (int i = 0; i <= 44; i++) {
                inventory.setItem(i, all.get((45 * (page - 1)) + i));
            }
            inventory.setItem(50, ItemManager.NextPage);
        }
        if (page != 1) {
            inventory.setItem(48, ItemManager.PrevPage);
        }
        return inventory;
    }


    public static Map<Player, Integer> page = new HashMap<>();


    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(DictionaryName) && (event.getCurrentItem() != null)) {


            Inventory inventory = event.getClickedInventory();
            Player player = ((Player) event.getWhoClicked());

            if (event.isLeftClick()) {
                assert inventory != null;
                if (inventory.getType().equals(InventoryType.PLAYER)) {
                    event.setCancelled(true);
                    return;
                }

                if (event.getCurrentItem().equals(ItemManager.Close)) {
                    player.closeInventory();
                    event.setCancelled(true);
                }

                else if (event.getCurrentItem().equals(ItemManager.NextPage)) {
                    player.closeInventory();
                    page.put(player, page.get(player) + 1);
                    Inventory nextDictionary = makeDictionaryGUI(player, page.get(player));
                    player.openInventory(nextDictionary);
                    player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1F, 2F);
                    event.setCancelled(true);
                }

                else if (event.getCurrentItem().equals(ItemManager.PrevPage)) {
                    player.closeInventory();
                    page.put(player, page.get(player) - 1);
                    Inventory prevDictionary = makeDictionaryGUI(player, page.get(player));
                    player.openInventory(prevDictionary);
                    player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1F, 2F);
                    event.setCancelled(true);
                }
                else {
                    event.setCancelled(true);
                }

                if (event.isShiftClick()) {
                    ItemStack item = event.getCurrentItem();
                    String codename = ItemManager.codenameMap.get(item);
                    List<BaseComponent> quests = QuestManager.getQuests(codename);
                    if (quests.size() != 0) {
                        player.sendMessage(item.getItemMeta().getDisplayName() + ChatColor.RESET + " 의 업적입니다. (마우스를 올리면 상세히 볼 수 있습니다.)");
                        for (BaseComponent quest : quests) {
                            player.spigot().sendMessage(ChatMessageType.CHAT, quest);
                        }
                        player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_HIT, 1f, 2f);
                    }
                    else {
                        player.sendMessage(item.getItemMeta().getDisplayName() + ChatColor.RESET + "" + ChatColor.RED + " 은(는) 업적이 없습니다!");
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5f, 1f);
                    }
                    event.setCancelled(true);
                }

            }
            event.setCancelled(true);
        }
    }
}
