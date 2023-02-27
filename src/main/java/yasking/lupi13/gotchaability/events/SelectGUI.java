package yasking.lupi13.gotchaability.events;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import yasking.lupi13.gotchaability.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectGUI implements Listener {
    private GotchaAbility plugin;
    public SelectGUI(GotchaAbility plugin) {
        this.plugin = plugin;
    }

    public static String SelectName = ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "SELECT";

    public static Map<Player, Integer> page = new HashMap<>();
    public static Map<Player, Integer> maxPage = new HashMap<>();

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(SelectName) && (event.getCurrentItem() != null)) {
            Inventory inventory = event.getClickedInventory();
            Player player = ((Player) event.getWhoClicked());

            if (event.isLeftClick()) {
                if (event.getCurrentItem().equals(ItemManager.Close)) {
                    player.closeInventory();
                    event.setCancelled(true);
                }

                else if (event.getCurrentItem().equals(ItemManager.NextPage)) {
                    player.closeInventory();
                    page.put(player, page.get(player) + 1);
                    Inventory nextSelect = Functions.makeSelectGUI(player, page.get(player));
                    player.openInventory(nextSelect);
                    event.setCancelled(true);
                }

                else if (event.getCurrentItem().equals(ItemManager.PrevPage)) {
                    player.closeInventory();
                    page.put(player, page.get(player) - 1);
                    Inventory prevSelect = Functions.makeSelectGUI(player, page.get(player));
                    player.openInventory(prevSelect);
                    event.setCancelled(true);
                }

                else {
                    ItemStack item = event.getCurrentItem();
                    String grade = Functions.getGrade(item);
                    String codename = ItemManager.codenameMap.get(item);

                    if (event.isShiftClick()) {
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
                    }
                    else {
                        if (FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability").contains(codename)) {
                            player.sendMessage(ChatColor.RED + "이미 적용되어있는 능력입니다!");
                            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5f, 1f);

                            event.setCancelled(true);
                        }

                        else if ((grade.equals("C")) || (grade.equals("C*"))) {
                            int WillCount = Functions.countItems(player, ItemManager.WillC);
                            if (WillCount >= 1) {
                                Functions.removeItems(player, ItemManager.WillC, 1);
                                List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");

                                if (abilities.size() >= 1) {
                                    abilities.set(0, codename);
                                }
                                else {
                                    abilities.add(codename);
                                }
                                FileManager.getAbilityConfig().set(player.getDisplayName() + "@ability", abilities);
                                FileManager.saveAbility();

                                player.sendMessage("능력이 " + item.getItemMeta().getDisplayName() + ChatColor.RESET + "(으)로 설정되었습니다.");
                                player.playSound(player.getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 1F, 2F);
                                event.setCancelled(true);
                            }
                            else {
                                player.sendMessage(ItemManager.WillC.getItemMeta().getDisplayName() + ChatColor.RESET + "이 부족합니다!");
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5f, 1f);
                                event.setCancelled(true);
                            }
                        }
                        else if ((grade.equals("B")) || (grade.equals("B*"))) {
                            int WillCount = Functions.countItems(player, ItemManager.WillB);
                            if (WillCount >= 1) {
                                Functions.removeItems(player, ItemManager.WillB, 1);
                                List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");

                                if (abilities.size() >= 1) {
                                    abilities.set(0, codename);
                                }
                                else {
                                    abilities.add(codename);
                                }
                                FileManager.getAbilityConfig().set(player.getDisplayName() + "@ability", abilities);
                                FileManager.saveAbility();

                                player.sendMessage("능력이 " + item.getItemMeta().getDisplayName() + ChatColor.RESET + "(으)로 설정되었습니다.");
                                player.playSound(player.getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 1F, 2F);
                                event.setCancelled(true);
                            }
                            else {
                                player.sendMessage(ItemManager.WillB.getItemMeta().getDisplayName() + ChatColor.RESET + "이 부족합니다!");
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5f, 1f);
                                event.setCancelled(true);
                            }
                        }
                        else if ((grade.equals("A")) || (grade.equals("A*"))) {
                            int WillCount = Functions.countItems(player, ItemManager.WillA);
                            if (WillCount >= 1) {
                                Functions.removeItems(player, ItemManager.WillA, 1);
                                List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");

                                if (abilities.size() >= 1) {
                                    abilities.set(0, codename);
                                }
                                else {
                                    abilities.add(codename);
                                }
                                FileManager.getAbilityConfig().set(player.getDisplayName() + "@ability", abilities);
                                FileManager.saveAbility();

                                player.sendMessage("능력이 " + item.getItemMeta().getDisplayName() + ChatColor.RESET + "(으)로 설정되었습니다.");
                                player.playSound(player.getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 1F, 2F);
                                event.setCancelled(true);
                            }
                            else {
                                player.sendMessage(ItemManager.WillA.getItemMeta().getDisplayName() + ChatColor.RESET + "이 부족합니다!");
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5f, 1f);
                                event.setCancelled(true);
                            }
                        }
                        else if ((grade.equals("S")) || (grade.equals("S*"))) {
                            int WillCount = Functions.countItems(player, ItemManager.WillS);
                            if (WillCount >= 1) {
                                Functions.removeItems(player, ItemManager.WillS, 1);
                                List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");

                                if (abilities.size() >= 1) {
                                    abilities.set(0, codename);
                                }
                                else {
                                    abilities.add(codename);
                                }
                                FileManager.getAbilityConfig().set(player.getDisplayName() + "@ability", abilities);
                                FileManager.saveAbility();

                                player.sendMessage("능력이 " + item.getItemMeta().getDisplayName() + ChatColor.RESET + "(으)로 설정되었습니다.");
                                player.playSound(player.getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 1F, 2F);
                                event.setCancelled(true);
                            }
                            else {
                                player.sendMessage(ItemManager.WillS.getItemMeta().getDisplayName() + ChatColor.RESET + "이 부족합니다!");
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5f, 1f);
                                event.setCancelled(true);
                            }
                        }
                        else if (grade.equals("SS*")) {
                            int WillCount = Functions.countItems(player, ItemManager.WillSS);
                            if (WillCount >= 1) {
                                Functions.removeItems(player, ItemManager.WillSS, 1);
                                List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");

                                if (abilities.size() >= 1) {
                                    abilities.set(0, codename);
                                }
                                else {
                                    abilities.add(codename);
                                }
                                FileManager.getAbilityConfig().set(player.getDisplayName() + "@ability", abilities);
                                FileManager.saveAbility();

                                player.sendMessage("능력이 " + item.getItemMeta().getDisplayName() + ChatColor.RESET + "(으)로 설정되었습니다.");
                                player.playSound(player.getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 1F, 2F);
                                event.setCancelled(true);
                            }
                            else {
                                player.sendMessage(ItemManager.WillSS.getItemMeta().getDisplayName() + ChatColor.RESET + "이 부족합니다!");
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5f, 1f);
                                event.setCancelled(true);
                            }
                        }
                    }
                }
            }
            event.setCancelled(true);
        }
    }
}
