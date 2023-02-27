package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.inventory.ItemStack;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.Arrays;
import java.util.List;

public class DoubleExp implements Listener {
    private GotchaAbility plugin;

    String name = "경험치 보너스";
    String codename = "DoubleExp";
    String grade = "A";
    String displayName = Functions.makeDisplayName(name, grade);
    Material material = Material.EXPERIENCE_BOTTLE;
    String[] strings = {ChatColor.WHITE + "경험치 획득량이 2배로 증가합니다."};
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public DoubleExp(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }




    @EventHandler
    public void onTarget(PlayerExpChangeEvent event) {
        Player player = event.getPlayer();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            event.setAmount(event.getAmount() * 2);
        }
    }
}
