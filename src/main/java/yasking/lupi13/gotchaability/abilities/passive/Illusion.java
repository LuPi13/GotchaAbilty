package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.ItemStack;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.Arrays;
import java.util.List;

public class Illusion implements Listener {
    private GotchaAbility plugin;

    String name = "도깨비불";
    String codename = "Illusion";
    String grade = "A";
    Material material = Material.BLAZE_POWDER;
    String[] strings = {ChatColor.WHITE + "눈이 마주친 적에게 실명을 부여합니다.", ChatColor.GRAY + "" + ChatColor.ITALIC + "겁내지 말고 이리온"};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public Illusion(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }

    //Method at ATTRunnable
}
