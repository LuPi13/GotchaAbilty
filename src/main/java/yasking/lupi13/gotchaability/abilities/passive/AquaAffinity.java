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
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.Arrays;
import java.util.List;

public class AquaAffinity implements Listener {
    private GotchaAbility plugin;

    String name = "친수성";
    String codename = "AquaAffinity";
    String grade = "C";
    String displayName = Functions.makeDisplayName(name, grade);
    Material material = Material.WATER_BUCKET;
    String[] strings = {ChatColor.WHITE + "물에서 근접공격 시 1.5배의 피해를 줍니다."};
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public AquaAffinity(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }




    @EventHandler
    public void onPunch(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if (event.getEntity() instanceof LivingEntity) {
                List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
                if (abilities.contains(codename)) {
                    if (player.getLocation().getBlock().getType().equals(Material.WATER)) {
                        event.setDamage(event.getDamage() * 1.5);
                    }
                }
            }
        }
    }
}
