package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.Arrays;
import java.util.List;

public class FriendlyCreeper implements Listener {
    private GotchaAbility plugin;

    String name = "폭발하는 친구";
    String codename = "FriendlyCreeper";
    String grade = "B";
    Material material = Material.CREEPER_HEAD;
    String[] strings = {ChatColor.WHITE + "크리퍼가 나를 적대하지 않습니다."};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public FriendlyCreeper(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }




    @EventHandler
    public void onTarget(EntityTargetLivingEntityEvent event) {
        if (event.getEntity() instanceof Creeper) {
            if (event.getTarget() instanceof Player) {
                Player player = (Player) event.getTarget();
                List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
                if (abilities.contains(codename)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
