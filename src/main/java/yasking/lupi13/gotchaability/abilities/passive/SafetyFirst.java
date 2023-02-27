package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.Arrays;
import java.util.List;

public class SafetyFirst implements Listener {
    private GotchaAbility plugin;

    String name = "안전제일";
    String codename = "SafetyFirst";
    String grade = "C";
    String displayName = Functions.makeDisplayName(name, grade);
    Material material = Material.GOLDEN_HELMET;
    String[] strings = {ChatColor.WHITE + "웅크렸을 때만 블럭을 설치할 수 있습니다.", ChatColor.GRAY + "" + ChatColor.ITALIC + "도대체 누가 이런 걸 능력이라고 낸거야?"};
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public SafetyFirst(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }




    @EventHandler
    public void onPutBlock(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            if (!player.isSneaking()) {
                if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    if (event.getItem() != null) {
                        if (event.getItem().getType().isBlock()) {
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
}
