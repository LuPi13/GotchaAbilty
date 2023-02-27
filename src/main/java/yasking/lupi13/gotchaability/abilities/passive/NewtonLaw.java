package yasking.lupi13.gotchaability.abilities.passive;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class NewtonLaw implements Listener {
    private GotchaAbility plugin;

    String name = "F=ma";
    String codename = "NewtonLaw";
    String grade = "A";
    Material material = Material.POLISHED_DEEPSLATE;
    String[] strings = {ChatColor.WHITE + "인벤토리에 빈 공간이 적을 수록 근접공격 피해가", ChatColor.WHITE + "최대 1.5배 증가합니다. 또, 움직이는 속도에 비례하게", ChatColor.WHITE + "피해가 증가합니다. (기본 달리기 시 약 1.2배)"};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public NewtonLaw(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }





    @EventHandler
    public void onPunch(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
            if (abilities.contains(codename)) {
                double count = 0.0;
                for (int i = 0; i < 36; i++) {
                    if (player.getInventory().getItem(i) != null) {
                        count += 1.0;
                    }
                }
                event.setDamage(event.getDamage() * (count/72.0 + 1) * Math.pow(2, Functions.velocity.get(player).length()));
                BaseComponent text;
                text = new TextComponent(ChatColor.WHITE + "피해가 " + ChatColor.YELLOW + Math.round((count / 72.0 + 1) * Math.pow(2, Functions.velocity.get(player).length()) * 1000.0) / 1000.0 + ChatColor.WHITE + "배 증가했습니다!");
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, text);
            }
        }
    }
}
