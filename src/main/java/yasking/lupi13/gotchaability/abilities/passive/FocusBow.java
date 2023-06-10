package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.*;

public class FocusBow implements Listener {
    private GotchaAbility plugin;

    String name = "집중의 활시위";
    String codename = "FocusBow";
    String grade = "A";
    Material material = Material.ARCHER_POTTERY_SHERD;
    String[] strings = {ChatColor.WHITE + "공중에서 활시위를 당기면 시야가 확대되고", ChatColor.WHITE + "제자리에서 아주 느리게 낙하합니다."};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public FocusBow(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }



    public static Map<Player, Long> time = new HashMap<>();
    public static Map<Player, Integer> toggle = new HashMap<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            if (event.getItem() != null && event.getItem().getType().equals(Material.BOW) && (player.getGameMode().equals(GameMode.CREATIVE) || ((Functions.countItems(player, Material.ARROW) >= 1) || (Functions.countItems(player, Material.TIPPED_ARROW) >= 1)|| (Functions.countItems(player, Material.SPECTRAL_ARROW) >= 1)))) {
                if (!((LivingEntity) player).isOnGround()) {
                    time.put(player, System.currentTimeMillis());
                    toggle.put(player, 1);
                }
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (toggle.get(player) != null && toggle.get(player) == 1) {
            if (System.currentTimeMillis() - time.get(player) <= 1000) {
                player.setVelocity(player.getVelocity().multiply(0.3));
            }
            else {
                player.setVelocity(new Vector().zero());
                player.setFallDistance(0);
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2, 7, true, false, false));
        }
        if (((LivingEntity) player).isOnGround()) {
            toggle.put(player, 0);
        }
    }

    @EventHandler
    public void onShoot(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() != null && (event.getEntity().getShooter() instanceof Player)) {
            Player player = ((Player) event.getEntity().getShooter());
            if (toggle.get(player) != null && toggle.get(player) == 1) {
                toggle.put(player, 0);
                player.setFallDistance(0);
            }
        }
    }
}
