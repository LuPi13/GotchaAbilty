package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LittleEat implements Listener {
    private GotchaAbility plugin;

    String name = "소식가";
    String codename = "LittleEat";
    String grade = "C";
    String displayName = Functions.makeDisplayName(name, grade);
    Material material = Material.DRIED_KELP;
    String[] strings = {ChatColor.WHITE + "무엇을 먹든 허기가 가득 찹니다.",
            ChatColor.WHITE + "대신 허기가 2배 빠르게 줄어듭니다."};
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public LittleEat(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }





    @EventHandler
    public void onEat(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            player.setFoodLevel(22);
        }
    }

    @EventHandler
    public void onHungry(FoodLevelChangeEvent event) {
        Player player = (Player) event.getEntity();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            event.setFoodLevel(player.getFoodLevel() - 2);
        }
    }
}