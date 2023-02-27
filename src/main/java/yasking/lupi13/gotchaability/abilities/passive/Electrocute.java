package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
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
import java.util.HashMap;
import java.util.List;

public class Electrocute implements Listener {
    private GotchaAbility plugin;

    String name = "감전";
    String codename = "Electrocute";
    String grade = "A";
    Material material = Material.TIPPED_ARROW;
    String[] strings = {ChatColor.WHITE + "번개피해에 면역이 됩니다." , ChatColor.WHITE + "각 1초 간격 내로 3회 연속 공격 시", ChatColor.WHITE + "적에게 번개를 떨어뜨립니다.",
    ChatColor.WHITE + "" + ChatColor.ITALIC + "from. " + ChatColor.GOLD + "" + ChatColor.BOLD + "LEAGUE of LEGENDS"};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public Electrocute(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }




    public HashMap<Player, LivingEntity> oldtarget = new HashMap<>();
    public HashMap<Player, Integer> hitcount = new HashMap<>();
    public HashMap<Player, Long> timer = new HashMap<>();

    @EventHandler
    public void onPunch(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity) {
            Player player = ((Player) event.getDamager());
            LivingEntity target = ((LivingEntity) event.getEntity());
            List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
            if (abilities.contains(codename)) {
                if (!timer.containsKey(player)) {
                    timer.put(player, System.currentTimeMillis());
                    hitcount.put(player, 1);
                    oldtarget.put(player, target);
                }
                else if (System.currentTimeMillis() - timer.get(player) <= 1000){
                    if (!oldtarget.get(player).equals(target)) {
                        hitcount.put(player, 1);
                        oldtarget.put(player, target);
                        timer.put(player, System.currentTimeMillis());
                    }
                    else {
                        hitcount.put(player, hitcount.get(player) + 1);
                        timer.put(player, System.currentTimeMillis());
                    }
                }
                else {
                    timer.put(player, System.currentTimeMillis());
                    hitcount.put(player, 1);
                    oldtarget.put(player, target);
                }

                if (hitcount.get(player) == 3) {
                    target.getWorld().spawnEntity(target.getLocation(), EntityType.LIGHTNING);
                    hitcount.put(player, 0);
                    oldtarget.remove(player);
                }
            }
        }
    }

    @EventHandler
    public void onHit(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = ((Player) event.getEntity());
            List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
            if (abilities.contains(codename)) {
                if (event.getCause().equals(EntityDamageEvent.DamageCause.LIGHTNING)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
