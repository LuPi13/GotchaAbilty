package yasking.lupi13.gotchaability.abilities.passive;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import yasking.lupi13.gotchaability.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EndDodge implements Listener {
    private GotchaAbility plugin;

    String name = "엔드의 회피";
    String codename = "EndDodge";
    String grade = "B*";
    Material material = Material.ENDER_PEARL;
    String[] strings = {Functions.getItemStackFromMap("Dodge").getItemMeta().getDisplayName() + ChatColor.WHITE + " 능력을 계승합니다.", ChatColor.WHITE + "회피를 텔레포트로 변경합니다."};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public EndDodge(GotchaAbility plugin) {
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
            if (event.isSneaking() && player.isOnGround()) {
                if ((time.get(player) != null) && ((System.currentTimeMillis() - time.get(player)) <= 300)) {
                    if ((coolTime.get(player) == null) || ((coolTime.get(player) != null) && (System.currentTimeMillis() - coolTime.get(player) >= 10000))) {
                        coolTime.put(player, System.currentTimeMillis());

                        Vector velocity = Functions.velocity.get(player).setY(0);
                        Vector newVel;

                        try {
                            newVel = (velocity.length() >= 2) ? velocity.multiply(15) : velocity.normalize().multiply(15);
                        } catch (NullPointerException ignored) {
                            newVel = player.getLocation().getDirection().setY(0).normalize().multiply(15);
                        }
                        player.getWorld().spawnParticle(Particle.SMOKE_NORMAL, player.getLocation().add(new Vector(0, 0.9, 0)), 30, 0.2, 0.4, 0.2, 0, null);
                        player.getWorld().spawnParticle(Particle.SPELL_INSTANT, player.getLocation().add(newVel).add(new Vector(0, 0.9, 0)), 30, 0.2, 0.4, 0.2, 0, null);
                        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 1F, 1F);
                        try {
                            Vector finalNewVel = newVel;
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    player.teleport(player.getLocation().add(finalNewVel));
                                    player.getWorld().spawnParticle(Particle.GLOW, player.getLocation().add(new Vector(0, 0.9, 0)), 30, 0.2, 0.4, 0.2, 0, null);
                                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 1F, 2F);
                                    cancel();
                                }
                            }.runTaskTimer(plugin, 10L, 1L);
                        }
                        catch (IllegalArgumentException ignored) {
                        }



                        event.setCancelled(true);
                    } else {
                        BaseComponent text = new TextComponent(ChatColor.YELLOW + "남은 쿨타임: " + (((10000 - System.currentTimeMillis() + coolTime.get(player)) / 10) / 100.0) + "초");
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, text);
                    }
                }
                time.put(player, System.currentTimeMillis());
            }
        }
    }
}
