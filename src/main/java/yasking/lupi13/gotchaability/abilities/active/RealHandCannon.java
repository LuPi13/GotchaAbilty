package yasking.lupi13.gotchaability.abilities.active;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;
import yasking.lupi13.gotchaability.events.ATTRunnable;

import java.util.*;

public class RealHandCannon implements Listener {
    private GotchaAbility plugin;

    String name = "진짜 핸드'캐논'";
    String codename = "RealHandCannon";
    String grade = "A*";
    Material material = Material.FIRE_CHARGE;
    String[] strings = {ChatColor.WHITE + "왼손이 핸드'캐논'으로 고정됩니다.", ChatColor.WHITE + "F키를 누르면 인벤토리 내의 화약 1개를 소비해",
            ChatColor.WHITE + "지형을 파괴하는 짧은 곡사포를 발사합니다."};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    String activeName = "핸드'캐논'";
    String[] activeStrings = {ChatColor.WHITE + "F키를 누르면 화약 1개로 곡사포를 발사합니다."};
    ItemStack active = Functions.makeActiveItem(material, activeName, Arrays.asList(activeStrings));

    public RealHandCannon(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.activeList.add(item);
        ItemManager.hardDenyList.add(active);
        ItemManager.codenameMap.put(item, codename);
        ItemManager.activeMap.put(item, active);
    }


    public static List<Snowball> cannons = new ArrayList<>();

    @EventHandler
    public void onSwapHand(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            if (event.getMainHandItem() != null && event.getMainHandItem().equals(active)) {
                event.setCancelled(true);

                if ((Functions.countItems(player, Material.GUNPOWDER) >= 1) || player.getGameMode().equals(GameMode.CREATIVE)) {
                    World world = player.getWorld();

                    if (!player.getGameMode().equals(GameMode.CREATIVE)) {
                        Functions.removeItems(player, Material.GUNPOWDER, 1);
                    }

                    Snowball cannon = world.spawn(player.getEyeLocation().add(player.getLocation().getDirection()), Snowball.class);
                    cannon.setShooter(player);
                    cannon.setVelocity(player.getLocation().getDirection().multiply(0.4));
                    cannon.setItem(new ItemStack(Material.TNT));
                    cannon.setVisualFire(true);
                    cannon.setGravity(true);

                    cannons.add(cannon);

                    world.playSound(cannon.getLocation(), Sound.ENTITY_CREEPER_PRIMED, 1F, 1F);
                    world.playSound(cannon.getLocation(), Sound.ITEM_TRIDENT_THROW, 1F, 0.5F);
                }
                else {
                    player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1F, 2F);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.YELLOW + "화약을 갖고 있지 않습니다!"));
                }
            }
        }
    }
}
