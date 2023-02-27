package yasking.lupi13.gotchaability.events;

import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.*;

public class PickUpRotation implements Listener {
    static Plugin plugin = GotchaAbility.getPlugin(GotchaAbility.class);

    public static List<ItemStack> SPickUp = new ArrayList<>();
    public static List<ItemStack> APickUp = new ArrayList<>();
    public static boolean toggle = true;

    public static void PickUp() {

        //init
        if (SPickUp.size() == 0) {
            List<ItemStack> slist = ItemManager.sList;
            List<ItemStack> alist = ItemManager.aList;
            for (int i = 1; i <= 5; i++) {
                Collections.shuffle(slist);
                SPickUp.add(slist.get(0));
                Collections.shuffle(alist);
                APickUp.add(alist.get(0));
                APickUp.add(alist.get(1));
                APickUp.add(alist.get(2));
            }
        }

        //rotate
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Calendar.getInstance().get(Calendar.MINUTE) % 30 == 0) {
                    if (toggle) {
                        SPickUp.remove(0);
                        APickUp.subList(0, 4).clear();

                        List<ItemStack> slist = ItemManager.sList;
                        List<ItemStack> alist = ItemManager.aList;
                        Collections.shuffle(slist);
                        Collections.shuffle(alist);

                        SPickUp.add(slist.get(0));
                        APickUp.add(alist.get(0));
                        APickUp.add(alist.get(1));
                        APickUp.add(alist.get(2));
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
        List<ItemStack> items = new ArrayList<>();

        items.add(SPickUp.get(order));
        for (int i = 0; i <= 2; i++) {
            items.add(APickUp.get((order * 3) + i));
        }

        return items;
    }
}
