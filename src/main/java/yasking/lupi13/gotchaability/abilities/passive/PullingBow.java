package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.Arrays;
import java.util.List;

public class PullingBow implements Listener {
    private GotchaAbility plugin;

    String name = "갈고리 화살";
    String codename = "PullingBow";
    String grade = "B";
    String displayName = Functions.makeDisplayName(name, grade);
    Material material = Material.BOW;
    String[] strings = {ChatColor.WHITE + "자신이 쏜 화살의 넉백방향을", ChatColor.WHITE + "끌어당기는 방향으로 전환합니다.", ChatColor.WHITE + "밀어내기 마법부여에 영향을 받습니다."};
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public PullingBow(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }




    @EventHandler
    public void onBowHit(ProjectileHitEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            Player player = ((Player) event.getEntity().getShooter());
            if ((event.getEntity() instanceof Arrow) || (event.getEntity() instanceof SpectralArrow)) {
                LivingEntity target = ((LivingEntity) event.getHitEntity());
                if (target != null) {
                    List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
                    if (abilities.contains(codename)) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                target.setVelocity(new Vector(target.getVelocity().getX() * -1, target.getVelocity().getY(), target.getVelocity().getZ() * -1));
                                cancel();
                            }
                        }.runTaskTimer(plugin, 1L, 1L);
                    }
                }
            }
        }
    }
}
