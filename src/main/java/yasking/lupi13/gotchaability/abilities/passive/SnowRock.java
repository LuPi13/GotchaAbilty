package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.Arrays;
import java.util.List;

public class SnowRock implements Listener {
    private GotchaAbility plugin;

    String name = "눈에 돌을 넣었나";
    String codename = "SnowRock";
    String grade = "C";
    String displayName = Functions.makeDisplayName(name, grade);
    Material material = Material.SNOWBALL;
    String[] strings = {ChatColor.WHITE + "눈덩이에 피해량 1을 부여합니다."};
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public SnowRock(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }





    @EventHandler
    public void onSnowHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball) {
            if (event.getEntity().getShooter() instanceof Player) {
                Player player = ((Player) event.getEntity().getShooter());
                List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
                if (abilities.contains(codename)) {
                    LivingEntity entity = (LivingEntity) event.getHitEntity();
                    entity.damage(1, player);
                }
            }
        }
    }
}
