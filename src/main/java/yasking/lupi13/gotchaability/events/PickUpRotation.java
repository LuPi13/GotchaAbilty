package yasking.lupi13.gotchaability.events;

import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.*;

public class PickUpRotation implements Listener {
    static Plugin plugin = GotchaAbility.getPlugin(GotchaAbility.class);

    //public static List<ItemStack> SPickUp = new ArrayList<>();
    //public static List<ItemStack> APickUp = new ArrayList<>();
    public static boolean toggle = true;

    public static void PickUp() {

        //init
        List<String> InitSPickUp = FileManager.getMiscConfig().getStringList("SPickUp");
        List<String> InitAPickUp = FileManager.getMiscConfig().getStringList("APickUp");
        List<ItemStack> slist = ItemManager.sList;
        List<ItemStack> alist = ItemManager.aList;


        int count = InitSPickUp.size();

        for (int i = 1; i <= (5 - count); i++) {
            Collections.shuffle(slist);
            InitSPickUp.add(ItemManager.codenameMap.get(slist.get(0)));
            Collections.shuffle(alist);
            InitAPickUp.add(ItemManager.codenameMap.get(alist.get(0)));
            InitAPickUp.add(ItemManager.codenameMap.get(alist.get(1)));
            InitAPickUp.add(ItemManager.codenameMap.get(alist.get(2)));
        }

        FileManager.getMiscConfig().set("SPickUp", InitSPickUp);
        FileManager.getMiscConfig().set("APickUp", InitAPickUp);
        FileManager.saveMisc();

        //rotate
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Calendar.getInstance().get(Calendar.MINUTE) % 30 == 0) {
                    if (toggle) {
                        List<String> ChangeSPickUp = FileManager.getMiscConfig().getStringList("SPickUp");
                        List<String> ChangeAPickUp = FileManager.getMiscConfig().getStringList("APickUp");
                        ChangeSPickUp.remove(0);
                        ChangeAPickUp.subList(0, 3).clear();

                        List<ItemStack> slist = ItemManager.sList;
                        List<ItemStack> alist = ItemManager.aList;
                        Collections.shuffle(slist);
                        Collections.shuffle(alist);

                        ChangeSPickUp.add(ItemManager.codenameMap.get(slist.get(0)));
                        ChangeAPickUp.add(ItemManager.codenameMap.get(alist.get(0)));
                        ChangeAPickUp.add(ItemManager.codenameMap.get(alist.get(1)));
                        ChangeAPickUp.add(ItemManager.codenameMap.get(alist.get(2)));

                        FileManager.getMiscConfig().set("SPickUp", ChangeSPickUp);
                        FileManager.getMiscConfig().set("APickUp", ChangeAPickUp);
                        FileManager.saveMisc();
                        plugin.getServer().broadcastMessage(ChatColor.GOLD + "픽업이 변경되었습니다!");
                        toggle = false;
                    }
                }
                else if (Calendar.getInstance().get(Calendar.MINUTE) % 30 == 27 && !toggle) {
                    toggle = true;
                    plugin.getServer().broadcastMessage(ChatColor.GOLD + "3분 후 픽업이 변경됩니다!");
                }
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }


    public static List<ItemStack> getPickUpList(int order) {
        List<String> codenames = new ArrayList<>();

        codenames.add(FileManager.getMiscConfig().getStringList("SPickUp").get(order));
        for (int i = 0; i <= 2; i++) {
            codenames.add(FileManager.getMiscConfig().getStringList("APickUp").get((order * 3) + i));
        }

        List<ItemStack> items = new ArrayList<>();
        for (String codename : codenames) {
            items.add(Functions.getItemStackFromMap(codename));
        }
        return items;
    }
}
