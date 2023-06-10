package yasking.lupi13.gotchaability.abilities.passive;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import yasking.lupi13.gotchaability.*;

import java.util.*;

public class Cloak implements Listener {
    private GotchaAbility plugin;

    String name = "은폐";
    String codename = "Cloak";
    String grade = "S";
    Material material = Material.MUSIC_DISC_CAT;
    String[] strings = {ChatColor.WHITE + "빠르게 두 번 웅크리면 투명화를 획득/해제합니다.", ChatColor.WHITE + "갑옷이 자동으로 탈/장착 됩니다.",
            ChatColor.WHITE + "투명화 진입 시 은폐 게이지 25를 소모하며,", ChatColor.WHITE + "매 초 1씩 추가로 소모합니다.",
            ChatColor.WHITE + "비투명화 상태에서 게이지가 1초에 1씩 회복됩니다.", ChatColor.WHITE + "" + ChatColor.ITALIC + "from. " + ChatColor.BOLD + "StarCraft",
            ChatColor.WHITE + "" + ChatColor.ITALIC + "이 능력은 특정 조건을 통해" , ChatColor.WHITE + "" + ChatColor.ITALIC + "다른 능력을 해금할 수 있습니다.",
            QuestManager.getQuests(codename).get(0).toPlainText()};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public Cloak(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }




    public static Map<Player, Long> time = new HashMap<>();
    public static Map<Player, Integer> timer = new HashMap<>();
    public static Map<Player, Boolean> toggle = new HashMap<>();
    public static Map<Player, Integer> gauge = new HashMap<>();
    public static Map<Player, List<ItemStack>> armors = new HashMap<>();


    @EventHandler
    public void onDoubleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            if (event.isSneaking()) {
                if ((time.get(player) != null) && ((System.currentTimeMillis() - time.get(player)) <= 300)) {

                    if (toggle.get(player) != null && toggle.get(player)) {
                        toggle.put(player, false);
                        timer.put(player, 0);
                        return;
                    }
                    if ((toggle.get(player) == null || !toggle.get(player)) && gauge.get(player) >= 500) {
                        gauge.put(player, gauge.get(player) - 500);
                        toggle.put(player, true);

                        List<ItemStack> armor = new ArrayList<>();

                        for (int i = 36; i <= 39; i++) {
                            if (player.getInventory().getItem(i) != null) {
                                armor.add(player.getInventory().getItem(i));
                            }
                            else {
                                armor.add(new ItemStack(Material.JIGSAW, 1));
                            }
                            player.getInventory().setItem(i, null);
                        }
                        armors.put(player, armor);

                        player.playSound(player.getLocation(), Sound.ENTITY_ALLAY_AMBIENT_WITHOUT_ITEM, 1.0F, 2.0F);

                        timer.put(player, 1);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 2, 0, true, false, true));
                                if ((timer.get(player) % 20) == 1) {
                                    for (Entity entity : player.getNearbyEntities(32, 16, 32)) {
                                        if (entity instanceof Mob) {
                                            ((Mob) entity).setTarget(null);
                                        }
                                    }
                                }

                                if (player.isDead()) {
                                    for (ItemStack item : armors.get(player)) {
                                        if (!item.getType().equals(Material.JIGSAW)) {
                                            player.getWorld().dropItemNaturally(player.getLocation(), item);
                                        }
                                    }
                                    toggle.put(player, false);
                                    timer.put(player, 0);
                                    cancel();
                                }

                                if (!toggle.get(player)) {
                                    for (int i = 36; i <= 39; i++) {
                                        if (!armors.get(player).get(i - 36).getType().equals(Material.JIGSAW)) {
                                            player.getInventory().setItem(i, armors.get(player).get(i - 36));
                                        }
                                        if (i == 39) {
                                            player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_GOLD, 1F, 1F);
                                        }
                                    }
                                    timer.put(player, 0);
                                    cancel();
                                }

                                //Quest
                                if (timer.get(player) >= 3499) {
                                    QuestManager.clearQuest(player, "MoebiusReactor", "Cloak", "SuperCloak", 0);
                                }
                                timer.put(player, timer.get(player) + 1);
                            }
                        }.runTaskTimer(plugin, 0L, 1L);
                    }


                }
                time.put(player, System.currentTimeMillis());
            }
        }
    }
}
