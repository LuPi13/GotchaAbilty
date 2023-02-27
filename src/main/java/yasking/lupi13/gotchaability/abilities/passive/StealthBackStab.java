package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import yasking.lupi13.gotchaability.*;

import java.util.Arrays;
import java.util.List;

public class StealthBackStab implements Listener {
    private GotchaAbility plugin;

    String name = "스텔스 백스탭";
    String codename = "StealthBackStab";
    String grade = "B*";
    Material material = Material.GOLDEN_SWORD;
    String[] strings = {ChatColor.WHITE + "웅크리면 적의 감지범위가 3칸으로 줄어듭니다."};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public StealthBackStab(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }




    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity) {
            Player player = ((Player) event.getDamager());
            LivingEntity target = ((LivingEntity) event.getEntity());
            List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
            if (abilities.contains(codename)) {
                Vector playervec = player.getLocation().getDirection().setY(0).normalize();
                Vector targetvec = target.getLocation().getDirection().setY(0).normalize();

                if (playervec.length() <= 0.01) {
                    float yaw = target.getLocation().getYaw();
                    double x = - Math.sin(yaw);
                    double z = Math.cos(yaw);
                    playervec = new Vector(x, 0, z).normalize();
                }

                if (targetvec.length() <= 0.01) {
                    float yaw = target.getLocation().getYaw();
                    double x = - Math.sin(yaw);
                    double z = Math.cos(yaw);
                    targetvec = new Vector(x, 0, z).normalize();
                }

                if (targetvec.dot(playervec) >= 0.5) {
                    event.setDamage(event.getDamage() * 1.5);
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_DOLPHIN_ATTACK, SoundCategory.PLAYERS, 1F, 2F);
                }
            }
        }
    }

    @EventHandler
    public void onTarget(EntityTargetLivingEntityEvent event) {
        if (event.getTarget() instanceof Player && event.getEntity() instanceof LivingEntity) {
            LivingEntity monster = ((LivingEntity) event.getEntity());
            Player player = ((Player) event.getTarget());
            List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
            if (abilities.contains(codename)) {
                if (player.isSneaking() && player.getLocation().distance(monster.getLocation()) >= 3) {
                    if (event.getReason().equals(EntityTargetEvent.TargetReason.CLOSEST_PLAYER)) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
