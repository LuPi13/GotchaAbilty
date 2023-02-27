package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.Arrays;
import java.util.List;

public class LavaJump implements Listener {
    private GotchaAbility plugin;

    String name = "용암수제비";
    String codename = "LavaJump";
    String grade = "C";
    Material material = Material.LAVA_BUCKET;
    String[] strings = {ChatColor.WHITE + "용암에 닿으면 위로 강하게 튀어오릅니다."};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public LavaJump(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }




    @EventHandler
    public void onHit(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = ((Player) event.getEntity());
            List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
            if (abilities.contains(codename)) {
                if (event.getCause().equals(EntityDamageEvent.DamageCause.LAVA)) {
                    event.setCancelled(true);
                    player.setVelocity(new Vector((Math.random() * 0.5) - 0.25, 2, (Math.random() * 0.5) - 0.25).normalize().multiply(1.5));
                }
            }
        }
    }
}
