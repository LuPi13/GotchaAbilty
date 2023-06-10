package yasking.lupi13.gotchaability.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

public class ShopGUI implements Listener {
    private GotchaAbility plugin;
    public ShopGUI(GotchaAbility plugin) {
        this.plugin = plugin;
    }

    public static String shopName = ChatColor.GREEN + "" + ChatColor.BOLD + "SHOP";

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(shopName) && (event.getCurrentItem() != null)) {
            Player player = ((Player) event.getWhoClicked());
            int playerEmeralds = Functions.countItems(player, Material.EMERALD);
            if (event.isLeftClick()) {
                /*
                if (event.getCurrentItem().equals(ItemManager.Close)) {
                    event.setCancelled(true);
                    player.closeInventory();
                    return;
                }
                 */

                if (event.getCurrentItem().equals(ItemManager.Possession)) {
                    if (playerEmeralds >= 10) {
                        int count = event.isShiftClick() ? Math.min(64, playerEmeralds / 10) : 1;
                        for (int i = 1; i <= count; i++) {
                            player.getInventory().addItem(ItemManager.Possession);
                        }
                        Functions.removeItems(player, Material.EMERALD, count * 10);
                        player.sendMessage(ChatColor.WHITE + "에메랄드 " + ChatColor.GREEN + (count * 10) + ChatColor.WHITE + "개를 이용하여 " + event.getCurrentItem().getItemMeta().getDisplayName() + ChatColor.WHITE + "를 " + ChatColor.AQUA + count + ChatColor.WHITE + "개 구입하였습니다.");
                        player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_HIT, 1f, 2f);
                        event.setCancelled(true);
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "에메랄드가 부족합니다! (10 에메랄드)");
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5f, 1f);
                        event.setCancelled(true);
                    }
                }

                if (event.getCurrentItem().equals(ItemManager.WillC)) {
                    if (playerEmeralds >= 5) {
                        int count = event.isShiftClick() ? Math.min(64, playerEmeralds / 5) : 1;
                        for (int i = 1; i <= count; i++) {
                            player.getInventory().addItem(ItemManager.WillC);
                        }
                        Functions.removeItems(player, Material.EMERALD, count * 5);
                        player.sendMessage(ChatColor.WHITE + "에메랄드 " + ChatColor.GREEN + (count * 5) + ChatColor.WHITE + "개를 이용하여 " + event.getCurrentItem().getItemMeta().getDisplayName() + ChatColor.WHITE + "를 " + ChatColor.AQUA + count + ChatColor.WHITE + "개 구입하였습니다.");
                        player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_HIT, 1f, 2f);
                        event.setCancelled(true);
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "에메랄드가 부족합니다! (5 에메랄드)");
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5f, 1f);
                        event.setCancelled(true);
                    }
                }

                if (event.getCurrentItem().equals(ItemManager.WillB)) {
                    if (playerEmeralds >= 10) {
                        int count = event.isShiftClick() ? Math.min(64, playerEmeralds / 10) : 1;
                        for (int i = 1; i <= count; i++) {
                            player.getInventory().addItem(ItemManager.WillB);
                        }
                        Functions.removeItems(player, Material.EMERALD, count * 10);
                        player.sendMessage(ChatColor.WHITE + "에메랄드 " + ChatColor.GREEN + (count * 10) + ChatColor.WHITE + "개를 이용하여 " + event.getCurrentItem().getItemMeta().getDisplayName() + ChatColor.WHITE + "를 " + ChatColor.AQUA + count + ChatColor.WHITE + "개 구입하였습니다.");
                        player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_HIT, 1f, 2f);
                        event.setCancelled(true);
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "에메랄드가 부족합니다! (10 에메랄드)");
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5f, 1f);
                        event.setCancelled(true);
                    }
                }

                if (event.getCurrentItem().equals(ItemManager.WillA)) {
                    if (playerEmeralds >= 15) {
                        int count = event.isShiftClick() ? Math.min(64, playerEmeralds / 15) : 1;
                        for (int i = 1; i <= count; i++) {
                            player.getInventory().addItem(ItemManager.WillA);
                        }
                        Functions.removeItems(player, Material.EMERALD, count * 15);
                        player.sendMessage(ChatColor.WHITE + "에메랄드 " + ChatColor.GREEN + (count * 15) + ChatColor.WHITE + "개를 이용하여 " + event.getCurrentItem().getItemMeta().getDisplayName() + ChatColor.WHITE + "를 " + ChatColor.AQUA + count + ChatColor.WHITE + "개 구입하였습니다.");
                        player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_HIT, 1f, 2f);
                        event.setCancelled(true);
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "에메랄드가 부족합니다! (15 에메랄드)");
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5f, 1f);
                        event.setCancelled(true);
                    }
                }

                if (event.getCurrentItem().equals(ItemManager.WillS)) {
                    if (playerEmeralds >= 20) {
                        int count = event.isShiftClick() ? Math.min(64, playerEmeralds / 20) : 1;
                        for (int i = 1; i <= count; i++) {
                            player.getInventory().addItem(ItemManager.WillS);
                        }
                        Functions.removeItems(player, Material.EMERALD, count * 20);
                        player.sendMessage(ChatColor.WHITE + "에메랄드 " + ChatColor.GREEN + (count * 20) + ChatColor.WHITE + "개를 이용하여 " + event.getCurrentItem().getItemMeta().getDisplayName() + ChatColor.WHITE + "를 " + ChatColor.AQUA + count + ChatColor.WHITE + "개 구입하였습니다.");
                        player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_HIT, 1f, 2f);
                        event.setCancelled(true);
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "에메랄드가 부족합니다! (20 에메랄드)");
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5f, 1f);
                        event.setCancelled(true);
                    }
                }

                if (event.getCurrentItem().equals(ItemManager.WillSS)) {
                    if (playerEmeralds >= 30) {
                        int count = event.isShiftClick() ? Math.min(64, playerEmeralds / 30) : 1;
                        for (int i = 1; i <= count; i++) {
                            player.getInventory().addItem(ItemManager.WillSS);
                        }
                        Functions.removeItems(player, Material.EMERALD, count * 30);
                        player.sendMessage(ChatColor.WHITE + "에메랄드 " + ChatColor.GREEN + (count * 30) + ChatColor.WHITE + "개를 이용하여 " + event.getCurrentItem().getItemMeta().getDisplayName() + ChatColor.WHITE + "를 " + ChatColor.AQUA + count + ChatColor.WHITE + "개 구입하였습니다.");
                        player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_HIT, 1f, 2f);
                        event.setCancelled(true);
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "에메랄드가 부족합니다! (30 에메랄드)");
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5f, 1f);
                        event.setCancelled(true);
                    }
                }
            }
            event.setCancelled(true);
        }
    }
}
