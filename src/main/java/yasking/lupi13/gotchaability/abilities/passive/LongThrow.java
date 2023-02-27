package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.Arrays;
import java.util.List;

public class LongThrow implements Listener {
    private GotchaAbility plugin;

    String name = "템버리기 고수";
    String codename = "LongThrow";
    String grade = "C";
    Material material = Material.RABBIT_HIDE;
    String[] strings = {ChatColor.WHITE + "아이템 투척 거리가 매우 증가합니다."};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public LongThrow(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }




    @EventHandler
    public void onThrow(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            event.getItemDrop().setVelocity(player.getLocation().getDirection().multiply(2));
        }
    }
}
