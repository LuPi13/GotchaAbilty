package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.Arrays;
import java.util.List;

public class LetsBoom implements Listener {
    private GotchaAbility plugin;

    String name = "함께 폭★4하자";
    String codename = "LetsBoom";
    String grade = "C";
    String displayName = Functions.makeDisplayName(name, grade);
    Material material = Material.TNT;
    String[] strings = {ChatColor.WHITE + "죽으면 6번의 큰 연쇄폭발을 일으킵니다.", ChatColor.WHITE + "지형을 파괴하지 않으나, 주변의 아이템을", ChatColor.WHITE + "모두 없애버립니다."};
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public LetsBoom(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }




    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            final int[] timer = {0};
            new BukkitRunnable() {
                @Override
                public void run() {
                    if ((timer[0] % 5) == 0) {
                        player.getWorld().createExplosion(player.getLocation(), 5, false, false, player);
                    }
                    if (timer[0] >= 30) {
                        cancel();
                    }

                    timer[0] += 1;
                }
            }.runTaskTimer(plugin, 0L, 1L);
        }
    }
}
