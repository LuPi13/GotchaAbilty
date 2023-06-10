package yasking.lupi13.gotchaability.abilities.active;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import yasking.lupi13.gotchaability.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeLeaperPlus implements Listener {
    private GotchaAbility plugin;

    String name = "시간 역행+";
    String codename = "TimeLeaperPlus";
    String grade = "A*";
    Material material = Material.CLOCK;
    String[] strings = {Functions.getItemStackFromMap("TimeLeaper").getItemMeta().getDisplayName() + ChatColor.WHITE + " 능력을 계승합니다.", ChatColor.WHITE + "시간 역행 시 회복하는 체력이 1.5배 증가합니다.", ChatColor.WHITE + "쿨타임이 20초로 감소합니다."};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    String activeName = "시간여행장치+";
    String[] activeStrings = {ChatColor.WHITE + "F키를 누르면 3초 전의 위치와 체력으로 돌아갑니다.", ChatColor.WHITE + "(쿨타임: 20초)"};
    ItemStack active = Functions.makeActiveItem(material, activeName, Arrays.asList(activeStrings));

    public TimeLeaperPlus(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.activeList.add(item);
        ItemManager.hardDenyList.add(active);
        ItemManager.codenameMap.put(item, codename);
        ItemManager.activeMap.put(item, active);
    }



    public static Map<Player, List<Location>> locations = new HashMap<>();
    public static Map<Player, List<Double>> healths = new HashMap<>();
    public static Map<Player, Long> coolTime = new HashMap<>();
    public static Map<Player, Integer> timer = new HashMap<>();

    @EventHandler
    public void onSwapHand(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            if (event.getMainHandItem() != null && event.getMainHandItem().equals(active)) {
                event.setCancelled(true);
                if ((coolTime.get(player) == null) || ((coolTime.get(player) != null) && (System.currentTimeMillis() - coolTime.get(player) >= 20000))) {
                    timer.put(player, locations.get(player).size() - 1);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            try {
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_SWIM, 0.2F, 2F);
                                player.setVelocity(new Vector().zero());
                                if (timer.get(player) <= 0) {
                                    player.teleport(locations.get(player).get(0));
                                }
                                else {
                                    player.teleport(locations.get(player).get(timer.get(player)));
                                }
                            }
                            catch (NullPointerException exception) {
                                player.sendMessage("이동 기록이 없습니다!");
                                cancel();
                            }

                            if (timer.get(player) <= 0) {
                                //업적
                                double dHealth = healths.get(player).get(0) - player.getHealth();

                                player.setHealth(player.getHealth() + (dHealth * 1.5));
                                player.playSound(player.getLocation(), Sound.ENTITY_DOLPHIN_JUMP, 1F, 2F);
                                timer.put(player, -10);
                                coolTime.put(player, System.currentTimeMillis());
                                cancel();
                            }
                            timer.put(player, timer.get(player) - 2);
                        }
                    }.runTaskTimer(plugin, 0L, 1L);
                }
                else {
                    BaseComponent text = new TextComponent(ChatColor.YELLOW + "남은 쿨타임: " + (((20000 - System.currentTimeMillis() + coolTime.get(player)) / 10) / 100.0) + "초");
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, text);
                }
            }
        }
    }


    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = ((Player) event.getEntity());
            List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
            if (abilities.contains(codename)) {
                if (timer.get(player) != null && timer.get(player) != -10) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
