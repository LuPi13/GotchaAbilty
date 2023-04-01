package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.Arrays;
import java.util.List;

public class UpperCut implements Listener {
    private GotchaAbility plugin;

    String name = "어퍼컷";
    String codename = "UpperCut";
    String grade = "B";
    String displayName = Functions.makeDisplayName(name, grade);
    Material material = Material.IRON_BLOCK;
    String[] strings = {ChatColor.WHITE + "자신이 가하는 근접공격의 넉백을", ChatColor.WHITE + "수직방향으로 전환합니다.", ChatColor.WHITE + "밀치기 마법부여에 영향을 받습니다."};
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public UpperCut(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }




    @EventHandler
    public void onPunch(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = ((Player) event.getDamager());
            if (event.getEntity() instanceof LivingEntity) {
                LivingEntity target = ((LivingEntity) event.getEntity());

                List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
                if (abilities.contains(codename)) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            target.setVelocity(new Vector(0, target.getVelocity().length(), 0));
                            cancel();
                        }
                    }.runTaskLater(plugin, 1L);
                }
            }
        }
    }
}
