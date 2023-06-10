package yasking.lupi13.gotchaability.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.Collections;
import java.util.List;

public class GotchaGUI implements Listener {
    private GotchaAbility plugin;
    public GotchaGUI(GotchaAbility plugin) {
        this.plugin = plugin;
    }

    public static String gotchaName = ChatColor.AQUA + "" + ChatColor.BOLD + "GOTCHA";


    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(gotchaName) && (event.getCurrentItem() != null)) {
            Inventory inventory = event.getClickedInventory();
            Player player = ((Player) event.getWhoClicked());
            int possessions = Functions.countItems(player, ItemManager.Possession);
            if (event.isLeftClick()) {
                /*
                if (event.getCurrentItem().equals(ItemManager.Close)) {
                    event.setCancelled(true);
                    player.closeInventory();
                    return;
                }
                 */

                if (event.getCurrentItem().equals(ItemManager.GotchaDummy)) {
                    event.setCancelled(true);
                }

                if (event.getCurrentItem().equals(ItemManager.Run)) {
                    int count = event.isShiftClick() ? 10 : 1;
                    if (possessions >= count) {
                        event.getCurrentItem().setAmount(0);
                        for (int i = 9; i <= 35; i++) {
                            inventory.setItem(i, null);
                        }
                        Functions.removeItems(player, ItemManager.Possession, count);

                        if (count == 1) {
                            final int[] timer = {1};

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (timer[0] <= 20) {
                                        Functions.coverPut(player, inventory, Functions.rollCover(), 22);
                                    }
                                    else if ((timer[0] <= 30) && (timer[0] % 2 == 0)) {
                                        Functions.coverPut(player, inventory, Functions.rollCover(), 22);
                                    }
                                    else if ((timer[0] <= 40) && (timer[0] % 3 == 0)) {
                                        Functions.coverPut(player, inventory, Functions.rollCover(), 22);
                                    }
                                    else if ((timer[0] <= 60) && (timer[0] % 5 == 4)) {
                                        Functions.coverPut(player, inventory, Functions.rollCover(), 22);
                                    }
                                    else if (timer[0] == 70) {
                                        Functions.coverPut(player, inventory, Functions.randomCover(player), 22);
                                    }
                                    else if (timer[0] == 90) {
                                        player.playSound(player.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1f, 2f);
                                        Functions.coverToAbility(player, inventory, 22);

                                        inventory.setItem(49, ItemManager.Run);
                                        timer[0] = 0;
                                        cancel();
                                    }
                                    timer[0] += 1;
                                }
                            }.runTaskTimer(plugin, 0L, 1L);
                        }
                        else {
                            final int[] timer = {1};

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    for (int i = 9; i <= 17; i += 2) {
                                        if (timer[0] <= 20 + (((i + 1) / 2) - 5)) {
                                            Functions.coverPut(player, inventory, Functions.rollCover(), i);
                                        }
                                        else if ((timer[0] <= 30 + (((i + 1) / 2) - 5)) && ((timer[0] + ((i + 1) / 2)) % 2 == 0)) {
                                            Functions.coverPut(player, inventory, Functions.rollCover(), i);
                                        }
                                        else if ((timer[0] <= 40 + (((i + 1) / 2) - 5)) && ((timer[0] + ((i + 1) / 2)) % 3 == 0)) {
                                            Functions.coverPut(player, inventory, Functions.rollCover(), i);
                                        }
                                        else if ((timer[0] <= 60 + (((i + 1) / 2) - 5)) && ((timer[0] + ((i + 1) / 2)) % 5 == 4)) {
                                            Functions.coverPut(player, inventory, Functions.rollCover(), i);
                                        }
                                        else if (timer[0] == 70 + (((i + 1) / 2) - 5)) {
                                            Functions.coverPut(player, inventory, Functions.randomCover(player), i);
                                        }
                                        else if (timer[0] == 90 + (((i + 1) / 2) - 5)) {
                                            player.playSound(player.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1f, 2f);
                                            Functions.coverToAbility(player, inventory, i);
                                        }
                                    }

                                    for (int i = 27; i <= 35; i += 2) {
                                        if (timer[0] <= 20 + (((i + 1) / 2) - 9)) {
                                            Functions.coverPut(player, inventory, Functions.rollCover(), i);
                                        }
                                        else if ((timer[0] <= 30 + (((i + 1) / 2) - 9)) && ((timer[0] + ((i + 1) / 2)) % 2 == 0)) {
                                            Functions.coverPut(player, inventory, Functions.rollCover(), i);
                                        }
                                        else if ((timer[0] <= 40 + (((i + 1) / 2) - 9)) && ((timer[0] + ((i + 1) / 2)) % 3 == 0)) {
                                            Functions.coverPut(player, inventory, Functions.rollCover(), i);
                                        }
                                        else if ((timer[0] <= 60 + (((i + 1) / 2) - 9)) && ((timer[0] + ((i + 1) / 2)) % 5 == 4)) {
                                            Functions.coverPut(player, inventory, Functions.rollCover(), i);
                                        }
                                        else if (timer[0] == 70 + (((i + 1) / 2) - 9)) {
                                            Functions.coverPut(player, inventory, Functions.randomCover(player), i);
                                        }
                                        else if (timer[0] == 90 + (((i + 1) / 2) - 9)) {
                                            player.playSound(player.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1f, 2f);
                                            Functions.coverToAbility(player, inventory, i);
                                        }
                                    }
                                    if (timer[0] == 100) {
                                        inventory.setItem(49, ItemManager.Run);
                                        timer[0] = 0;
                                        cancel();
                                    }

                                    timer[0] += 1;
                                }
                            }.runTaskTimer(plugin, 0L, 1L);
                        }
                    }
                    else {
                        player.sendMessage(ItemManager.PossessionName + ChatColor.RESET + "" + ChatColor.RED + "이 부족합니다!");
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5f, 1f);
                        event.setCancelled(true);
                    }
                }
            }
            event.setCancelled(true);
        }
    }
}
