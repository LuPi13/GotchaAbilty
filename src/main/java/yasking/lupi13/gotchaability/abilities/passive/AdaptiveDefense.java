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
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdaptiveDefense implements Listener {
    private GotchaAbility plugin;

    String name = "적응형 피해 감쇄기";
    String codename = "AdaptiveDefense";
    String grade = "B";
    Material material = Material.GOLDEN_CHESTPLATE;
    String[] strings = {ChatColor.WHITE + "같은 유형의 피해를 연속으로 받으면", ChatColor.WHITE + "피해가 20%씩 곱연산 감소합니다."};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public AdaptiveDefense(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }



    public static Map<Player, EntityDamageEvent.DamageCause> lastDamageCause = new HashMap<>();
    public static Map<Player, Integer> stack = new HashMap<>();

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = ((Player) event.getEntity());
            List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
            if (abilities.contains(codename)) {
                if (lastDamageCause.get(player) == null) {
                    lastDamageCause.put(player, event.getCause());
                    stack.put(player, 1);
                    return;
                }
                if (lastDamageCause.get(player).equals(event.getCause())) {
                    stack.merge(player, 1, Integer::sum);
                }
                else {
                    lastDamageCause.put(player, event.getCause());
                    stack.put(player, 1);
                }
                event.setDamage(event.getDamage() * Math.pow(0.8, stack.get(player) - 1));
            }
        }
    }
}
