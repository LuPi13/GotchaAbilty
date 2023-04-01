package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.ItemStack;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.Arrays;
import java.util.List;

public class DefenseAllIn implements Listener {
    private GotchaAbility plugin;

    String name = "아픈 건 싫으니까 방어력에 올인하려고 합니다.";
    String codename = "DefenseAllIn";
    String grade = "B";
    Material material = Material.NETHERITE_CHESTPLATE;
    String[] strings = {ChatColor.WHITE + "피격 후 무적시간이 2초로 늘어납니다.", ChatColor.WHITE + "받는 모든 피해량을 1로 바꿉니다.", ChatColor.WHITE + "가하는 모든 피해량을 1로 바꿉니다."};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public DefenseAllIn(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }




    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = ((Player) event.getEntity());
            List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
            if (abilities.contains(codename)) {
                if (event.getDamage() > 1) {
                    event.setDamage(1);
                }
                player.setNoDamageTicks(40);
            }
        }
    }


    @EventHandler
    public void onDeal(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = ((Player) event.getDamager());
            List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
            if (abilities.contains(codename)) {
                if (event.getDamage() > 1) {
                    event.setDamage(1);
                }
            }
        }

        if ((event.getDamager() instanceof Projectile)) {
            if (((Projectile) event.getDamager()).getShooter() != null && ((Projectile) event.getDamager()).getShooter() instanceof Player) {
                Player player = ((Player) ((Projectile) event.getDamager()).getShooter());
                List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
                if (abilities.contains(codename)) {
                    if (event.getDamage() > 1) {
                        event.setDamage(1);
                    }
                }
            }
        }
    }
}
