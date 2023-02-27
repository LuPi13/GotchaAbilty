package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.Arrays;
import java.util.List;

public class SugarRush implements Listener {
    private GotchaAbility plugin;

    String name = "슈가러쉬";
    String codename = "SugarRush";
    String grade = "C";
    Material material = Material.SUGAR;
    String[] strings = {ChatColor.WHITE + "설탕을 우클릭하면 3초간 신속3을 받습니다."};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public SugarRush(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }




    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            if (event.getItem() != null && event.getItem().getType().equals(Material.SUGAR)) {
                if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    if (player.getCooldown(Material.SUGAR) > 0) {
                        return;
                    }
                    player.setCooldown(Material.SUGAR, 60);
                    event.getItem().setAmount(event.getItem().getAmount() - 1);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 2));
                    player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SHOOT, 0.4F, 2.0F);
                }
            }
        }
    }
}
