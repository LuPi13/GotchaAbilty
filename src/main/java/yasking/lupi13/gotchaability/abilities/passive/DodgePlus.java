package yasking.lupi13.gotchaability.abilities.passive;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import yasking.lupi13.gotchaability.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DodgePlus implements Listener {
    private GotchaAbility plugin;

    String name = "회피+";
    String codename = "DodgePlus";
    String grade = "C*";
    Material material = Material.RABBIT_FOOT;
    String[] strings = {Functions.getItemStackFromMap("Dodge").getItemMeta().getDisplayName() + ChatColor.WHITE + " 능력을 계승합니다.", ChatColor.WHITE + "회피 쿨타임이 5초로 감소합니다."};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public DodgePlus(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }




    public static Map<Player, Long> time = new HashMap<>();
    public static Map<Player, Long> coolTime = new HashMap<>();

    @EventHandler
    public void onDoubleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            if (event.isSneaking() && ((LivingEntity) player).isOnGround()) {
                if ((time.get(player) != null) && ((System.currentTimeMillis() - time.get(player)) <= 300)) {
                    if ((coolTime.get(player) == null) || ((coolTime.get(player) != null) && (System.currentTimeMillis() - coolTime.get(player) >= 5000))) {
                        coolTime.put(player, System.currentTimeMillis());

                        Vector velocity = Functions.velocity.get(player).setY(0).normalize();
                        Vector newVel;

                        try {
                            newVel = (velocity.length() >= 2) ? velocity.multiply(3) : velocity.normalize().multiply(3);
                        } catch (NullPointerException ignored) {
                            newVel = player.getLocation().getDirection().setY(0).normalize().multiply(3);
                        }
                        try {
                            player.setVelocity(newVel);
                        }
                        catch (IllegalArgumentException ignored) {
                        }
                        player.getWorld().playSound(player.getLocation(), Sound.ITEM_BUNDLE_DROP_CONTENTS, 1F, 1F);
                        event.setCancelled(true);
                    } else {
                        BaseComponent text = new TextComponent(ChatColor.YELLOW + "남은 쿨타임: " + (((5000 - System.currentTimeMillis() + coolTime.get(player)) / 10) / 100.0) + "초");
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, text);
                    }
                }
                time.put(player, System.currentTimeMillis());
            }
        }
    }
}
