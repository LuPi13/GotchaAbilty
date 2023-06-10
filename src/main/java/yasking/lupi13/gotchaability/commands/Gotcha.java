package yasking.lupi13.gotchaability.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;
import yasking.lupi13.gotchaability.events.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Gotcha implements CommandExecutor {
    private GotchaAbility plugin;
    public Gotcha(GotchaAbility plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            try {

                if (args[0].equalsIgnoreCase("help")) {
                    player.getWorld().dropItemNaturally(player.getLocation(), ItemManager.NewbieBook);
                    player.sendMessage("가챠 능력자 도움말 북이 인벤토리에 들어갔습니다!");
                }


                else if (args[0].equalsIgnoreCase("prob")) {
                    player.sendMessage("-----------------------------------------------------");
                    player.sendMessage(Functions.getColorGrade("S") + "" + ChatColor.BOLD + "S" + ChatColor.RESET + "등급은 평균 1.42%로, 약 70.2번 당 1개가 등장합니다.\n" +
                            "1번째부터 80번째까지는 0.5%의 확률로 등장하며, 81번째부터 확률이 5%p씩 오르기 시작합니다.\n" +
                            "즉, 81번째는 5.5%, 82번째는 10.5%, 83번째는 15.5% ... 99번째는 95.5%, 100번째는 100%의 확률을 갖습니다.\n" +
                            Functions.getColorGrade("S") + "" + ChatColor.BOLD + "S" + ChatColor.RESET + "등급 등장 시, 확률은 첫 0.5%로 초기화됩니다.\n");
                    player.sendMessage("-----------------------------------------------------");
                    player.sendMessage(Functions.getColorGrade("S") + "" + ChatColor.BOLD + "S" + ChatColor.RESET + "등급이 등장하지 않았을 때, " + Functions.getColorGrade("A") + "" + ChatColor.BOLD + "A" + ChatColor.RESET + "등급은 평균 12.8%로, 약 7.81번 당 1개가 등장합니다.\n" +
                            "1번째부터 6번째까지는 3%의 확률로 등장하며, 7번째부터 확률이 30%p씩 오르기 시작합니다.\n" +
                            "즉, 7번째는 33%, 8번째는 63%, 9번째는 93%, 10번째는 100%의 확률을 갖습니다.\n" +
                            Functions.getColorGrade("A") + "" + ChatColor.BOLD + "A" + ChatColor.RESET + "등급 등장 시, 확률은 첫 3%로 초기화됩니다.\n");
                    player.sendMessage("-----------------------------------------------------");
                    player.sendMessage(Functions.getColorGrade("S") + "" + ChatColor.BOLD + "S" + ChatColor.RESET + "등급도, " + Functions.getColorGrade("A") + "" + ChatColor.BOLD + "A" + ChatColor.RESET + "등급도 등장하지 않았을 때, " +
                            Functions.getColorGrade("B") + "" + ChatColor.BOLD + "B" + ChatColor.RESET + "등급과 " + Functions.getColorGrade("C") + "" + ChatColor.BOLD + "C" + ChatColor.RESET + "등급은 남은 확률을 1:3 비율로 나누어 갖습니다.");
                    player.sendMessage("-----------------------------------------------------");
                    player.sendMessage(Functions.getColorGrade("S") + "" + ChatColor.BOLD + "S" + ChatColor.RESET + "등급 중 1개, " + Functions.getColorGrade("A") + "" + ChatColor.BOLD + "A" + ChatColor.RESET + "등급 중 3개가 픽업으로 지정되며, " +
                            "해당 등급 등장 시, 50% 확률로 픽업 능력이 등장합니다. 50%확률로 픽업 능력이 등장하지 않으면, 다음 해당 등급은 반드시 픽업 능력이 등장합니다.\n" +
                            "픽업은 30분마다 변경되며, '/gotcha pickup' 으로 다음 픽업을 미리 볼 수 있습니다.\n");
                    player.sendMessage("-----------------------------------------------------");
                }


                else if (args[0].equalsIgnoreCase("shop")) {
                    Inventory shop = Bukkit.createInventory(player, 9, ShopGUI.shopName);
                    //shop.setItem(0, ItemManager.Close);
                    shop.setItem(2, ItemManager.Possession);
                    shop.setItem(3, ItemManager.WillC);
                    shop.setItem(4, ItemManager.WillB);
                    shop.setItem(5, ItemManager.WillA);
                    shop.setItem(6, ItemManager.WillS);
                    shop.setItem(7, ItemManager.WillSS);

                    player.openInventory(shop);
                }


                else if (args[0].equalsIgnoreCase("run")) {
                    Inventory gotcha = Bukkit.createInventory(player, 54, GotchaGUI.gotchaName);
                    for (int i = 0; i <= 8; i++) {
                        gotcha.setItem(i, ItemManager.GotchaDummy);
                    }
                    for (int i = 36; i <= 44; i++) {
                        gotcha.setItem(i, ItemManager.GotchaDummy);
                    }

                    //gotcha.setItem(45, ItemManager.Close);
                    gotcha.setItem(49, ItemManager.Run);

                    player.openInventory(gotcha);
                }


                else if (args[0].equalsIgnoreCase("select")) {
                    Inventory select = Functions.makeSelectGUI(player, 1);
                    SelectGUI.page.put(player, 1);
                    player.openInventory(select);
                }


                else if (args[0].equalsIgnoreCase("dictionary") || args[0].equalsIgnoreCase("dict")) {
                    Inventory select = DictionaryGUI.makeDictionaryGUI(player, 1);
                    DictionaryGUI.page.put(player, 1);
                    player.openInventory(select);
                }


                else if (args[0].equalsIgnoreCase("alchemy")) {
                    player.sendMessage("-----------------------------------------------------");
                    player.sendMessage(ChatColor.BLACK + "" + ChatColor.BOLD + "석탄" + ChatColor.RESET + ": 20%");
                    player.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "철 조각" + ChatColor.RESET + ": 20%");
                    player.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "철 주괴" + ChatColor.RESET + ": 10%");
                    player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "금 조각" + ChatColor.RESET + ": 20%");
                    player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "금 주괴" + ChatColor.RESET + ": 10%");
                    player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "레드스톤" + ChatColor.RESET + ": 10%");
                    player.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "청금석" + ChatColor.RESET + ": 5%");
                    player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "에메랄드" + ChatColor.RESET + ": 4%");
                    player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "다이아몬드" + ChatColor.RESET + ": 0.9%");
                    player.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "네더라이트 주괴" + ChatColor.RESET + ": 0.1%");
                    player.sendMessage("-----------------------------------------------------");
                    player.sendMessage(Functions.makeDisplayName("연금술+...?", "B*") + ChatColor.RESET + "" + ChatColor.WHITE + "의 경우,");
                    player.sendMessage(ChatColor.BLACK + "" + ChatColor.BOLD + "석탄" + ChatColor.RESET + ": 5%");
                    player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "금 조각" + ChatColor.RESET + ": 25%");
                    player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "금 주괴" + ChatColor.RESET + ": 20%");
                    player.sendMessage("-----------------------------------------------------");

                }


                else if (args[0].equalsIgnoreCase("pickup")) {
                    Calendar date = (Calendar) Calendar.getInstance().clone();
                    int minute = (Calendar.getInstance().get(Calendar.MINUTE) < 30) ? 0 : 30;
                    date.set(Calendar.MINUTE, minute);
                    date.set(Calendar.SECOND, 0);

                    for (int i = 0; i <= 4; i++) {
                        if (i != 0) {
                            date.add(Calendar.MINUTE, 30);
                        }
                        player.sendMessage(date.getTime() + "---------------------------\n" + PickUpRotation.getPickUpList(i).get(0).getItemMeta().getDisplayName() + ChatColor.RESET + ", " + PickUpRotation.getPickUpList(i).get(1).getItemMeta().getDisplayName()
                                + ChatColor.RESET + ", " + PickUpRotation.getPickUpList(i).get(2).getItemMeta().getDisplayName() + ChatColor.RESET + ", " + PickUpRotation.getPickUpList(i).get(3).getItemMeta().getDisplayName());
                        player.sendMessage("\n");
                    }
                }
                else if (args[0].equalsIgnoreCase("update")) {
                    player.sendMessage("");
                    player.sendMessage(ChatColor.AQUA + "Gotcha Ability " + ChatColor.RESET + "ver 0.3.0 업데이트 내역");
                    player.sendMessage("-이제 마인크래프트 1.20버전만을 지원합니다.");
                    player.sendMessage("-몇몇 능력의 아이콘을 더 직관적인 아이템으로 변경했습니다.");
                    player.sendMessage("-능력 10개 추가.");
                    player.sendMessage("-숨겨진 업적의 내용을 알아낼 수 있는 아이템이 추가되었습니다. 다이아몬드, 자수정 조각, 깃털, 종이 각각 하나씩을 모양 없이 조합하면 됩니다.");
                    player.sendMessage("-이제 /gotcha dictionary에서도 보유한 능력 클릭 시 능력이 적용됩니다.");
                    player.sendMessage("-갑옷이 탈/장착 되는 은신 중에 사망 시 갑옷을 드랍하지 않는 버그를 수정했습니다.");
                    player.sendMessage("-" + Functions.makeDisplayName("매의 눈", "B") + ChatColor.RESET + ", " + Functions.makeDisplayName("매의 눈+", "B*") + ChatColor.RESET + " 능력으로 플레이어를 처치 시 플레이어의 인벤토리 또한 두배로 증식하여 2명이서 아이템 복사 행위를 방지하기 위해, 플레이어의 전리품은 2배로 증가하지 않습니다.");

                }
            }
            catch (ArrayIndexOutOfBoundsException ignored) {
            }

        }
        else {
            sender.sendMessage(ChatColor.RED + "You are not a player!");
        }
        return true;
    }
}
