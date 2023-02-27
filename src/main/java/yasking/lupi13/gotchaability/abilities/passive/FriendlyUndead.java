package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.Arrays;
import java.util.List;

public class FriendlyUndead implements Listener {
    private GotchaAbility plugin;

    String name = "언데드와 친구하기";
    String codename = "FriendlyUndead";
    String grade = "B";
    String displayName = Functions.makeDisplayName(name, grade);
    Material material = Material.ZOMBIE_HEAD;
    String[] strings = {ChatColor.WHITE + "언데드 몹들이 나를 적대하지 않습니다."};
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public FriendlyUndead(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }





    @EventHandler
    public void onTarget(EntityTargetLivingEntityEvent event) {
        if (event.getEntity() instanceof Zombie ||
                event.getEntity() instanceof Drowned ||
                event.getEntity() instanceof Husk ||
                event.getEntity() instanceof Phantom ||
                event.getEntity() instanceof Skeleton ||
                event.getEntity() instanceof SkeletonHorse ||
                event.getEntity() instanceof Wither ||
                event.getEntity() instanceof WitherSkeleton ||
                event.getEntity() instanceof ZombieHorse ||
                event.getEntity() instanceof ZombieVillager ||
                event.getEntity() instanceof PigZombie ||
                event.getEntity() instanceof Stray) {
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
