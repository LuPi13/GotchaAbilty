package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.Arrays;
import java.util.List;

public class CookieRun implements Listener {
    private GotchaAbility plugin;

    String name = "쿠키런";
    String codename = "CookieRun";
    String grade = "C";
    String displayName = Functions.makeDisplayName(name, grade);
    Material material = Material.COOKIE;
    String[] strings = {ChatColor.WHITE + "쿠키를 먹으면 10초의 랜덤 버프가 주어집니다."};
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public CookieRun(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }




    @EventHandler
    public void onEat(PlayerItemConsumeEvent event) {
        if (event.getItem().getType().equals(Material.COOKIE)) {
            Player player = event.getPlayer();
            List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
            if (abilities.contains(codename)) {
                double random = Math.random();
                if (random < 0.1) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 200, 0));
                }
                else if (random < 0.2) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 200, 0));
                }
                else if (random < 0.3) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, 1));
                }
                else if (random < 0.4) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 200, 0));
                }
                else if (random < 0.5) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 0));
                }
                else if (random < 0.6) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 200, 0));
                }
                else if (random < 0.7) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 0));
                }
                else if (random < 0.8) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200, 0));
                }
                else if (random < 0.9) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 200, 0));
                }
                else {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 0));
                }
            }
        }
    }
}
