package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.ItemStack;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.Arrays;
import java.util.List;

public class BookPunch implements Listener {
    private GotchaAbility plugin;

    String name = "지식의 무게";
    String codename = "BookPunch";
    String grade = "C";
    Material material = Material.BOOK;
    String[] strings = {ChatColor.WHITE + "책으로 때리면 추가 피해를 줍니다.", ChatColor.WHITE + "많은 책으로 때리면 더 아프며, 최대", ChatColor.WHITE + "8의 피해를 줍니다."};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public BookPunch(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }




    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
            if (abilities.contains(codename)) {
                if (player.getInventory().getItemInMainHand().getType().equals(Material.BOOK)) {
                    event.setDamage(1.0 + ((2.0 * player.getInventory().getItemInMainHand().getAmount() + 19.0) / 21.0));
                }
            }
        }
    }
}
