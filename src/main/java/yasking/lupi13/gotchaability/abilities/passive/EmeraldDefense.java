package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.ItemStack;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.Arrays;
import java.util.List;

public class EmeraldDefense implements Listener {
    private GotchaAbility plugin;

    String name = "뒤져서 나오면 10원에 한 대";
    String codename = "EmeraldDefense";
    String grade = "C";
    String[] strings = {ChatColor.WHITE + "공격당할 때 에메랄드를 갖고 있으면 피해가 20%", ChatColor.WHITE + "감소하고, 에메랄드 하나를 바닥에 떨어뜨립니다.", ChatColor.WHITE + "이 에메랄드는 50% 확률로 즉시 없어집니다."};
    String displayName = Functions.makeDisplayName(name, grade);
    Material material = Material.CHAINMAIL_CHESTPLATE;
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public EmeraldDefense(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }




    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = ((Player) event.getEntity());
            List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
            if (abilities.contains(codename)) {
                if (Functions.countItems(player, Material.EMERALD) >= 1) {
                    event.setDamage(event.getDamage() * 0.8);
                    Functions.removeItems(player, Material.EMERALD, 1);
                    player.getWorld().playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 1, 1.5F);

                    double random = Math.random();
                    if (random <= 0.5) {
                        player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.EMERALD, 1)).setPickupDelay(20);
                    }
                }
            }
        }
    }
}
