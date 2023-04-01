package yasking.lupi13.gotchaability.abilities.passive;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KneePropel implements Listener {
    private GotchaAbility plugin;

    String name = "무릎을 꿇은 이유";
    String codename = "KneePropel";
    String grade = "C";
    Material material = Material.PISTON;
    String[] strings = {ChatColor.WHITE + "웅크렸다가 점프하면 추진력을 얻어", ChatColor.WHITE + "더 높이 도약합니다."};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public KneePropel(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }



    public static Map<Player, Integer> timer = new HashMap<>();

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            if (event.isSneaking() && ((LivingEntity) player).isOnGround()) {
                timer.put(player, 1);

                new BukkitRunnable() {
                    @Override
                    public void run() {

                        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 2, Math.min((timer.get(player) / 20) - 1, 9), true, false, false));

                        int amp = Math.min(timer.get(player) / 20, 10);
                        int gaugeTimer = timer.get(player) >= 200 ? 9 : (timer.get(player) % 20) / 2;

                        StringBuilder gauge = new StringBuilder("Jump Power 【");
                        for (int i = 1; i <= gaugeTimer; i++) {
                            gauge.append("■");
                        }
                        for (int j = 9; j > gaugeTimer; j--) {
                            gauge.append("□");
                        }
                        gauge.append("】 ");
                        gauge.append(amp);

                        String color = null;

                        switch (amp) {
                            case 0:
                                color = String.valueOf(ChatColor.GRAY);
                                break;
                            case 1:
                                color = String.valueOf(ChatColor.DARK_PURPLE);
                                break;
                            case 2:
                                color = String.valueOf(ChatColor.LIGHT_PURPLE);
                                break;
                            case 3:
                                color = String.valueOf(ChatColor.BLUE);
                                break;
                            case 4:
                                color = String.valueOf(ChatColor.AQUA);
                                break;
                            case 5:
                                color = String.valueOf(ChatColor.DARK_GREEN);
                                break;
                            case 6:
                                color = String.valueOf(ChatColor.GREEN);
                                break;
                            case 7:
                                color = String.valueOf(ChatColor.YELLOW);
                                break;
                            case 8:
                                color = String.valueOf(ChatColor.GOLD);
                                break;
                            case 9:
                                color = String.valueOf(ChatColor.RED);
                                break;
                            case 10:
                                color = String.valueOf(ChatColor.DARK_RED);
                                break;
                        }

                        BaseComponent text = new TextComponent( color + "" + ChatColor.BOLD + gauge);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, text);

                        if (timer.get(player) != -1) {
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, (float) (timer.get(player) / 400.0 + 0.3), (float) (timer.get(player) * (3.0 / 400.0) + 0.5F));
                        }

                        if (timer.get(player) == -1) {
                            if (!((LivingEntity) player).isOnGround() && (player.getPotionEffect(PotionEffectType.JUMP).getAmplifier() >= 0)) {
                                player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 0.8F, 2F);
                                player.playSound(player.getLocation(), Sound.ITEM_TRIDENT_THROW, 0.8F, 0.7F);
                                player.playSound(player.getLocation(), Sound.ITEM_TRIDENT_RIPTIDE_3, 0.8F, 2F);
                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "JUMP!!!"));
                            }
                            else {
                                player.playSound(player.getLocation(), Sound.ENTITY_VEX_HURT, 1F, 0.5F);
                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "" + ChatColor.BOLD + "Cancelled."));
                            }
                            timer.put(player, -1);
                            cancel();
                        }
                        timer.put(player, timer.get(player) + 1);
                    }
                }.runTaskTimer(plugin, 0L, 1L);
            }
            else {
                timer.put(player, -1);
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            if (!((LivingEntity) player).isOnGround()) {
                timer.put(player, -1);
            }
        }
    }
}
