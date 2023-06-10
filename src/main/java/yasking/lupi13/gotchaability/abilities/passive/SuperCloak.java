package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import yasking.lupi13.gotchaability.*;

import java.util.*;

public class SuperCloak implements Listener {
    private GotchaAbility plugin;

    String name = "초은폐";
    String codename = "SuperCloak";
    String grade = "SS*";
    Material material = Material.MUSIC_DISC_CAT;
    String[] strings = {Functions.getItemStackFromMap("Cloak").getItemMeta().getDisplayName() + ChatColor.WHITE + " 능력을 계승합니다.",
            ChatColor.WHITE + "투명화 중에 받는 피해가 20% 감소합니다.", ChatColor.WHITE + "투명화 중에 움직이지 않으면 게이지를 소모하지 않습니다.", ChatColor.WHITE + "게이지 최대치가 50만큼 증가합니다.",
            ChatColor.WHITE + "비투명화 상태에서 회복하는 게이지가 1.5배 증가합니다.", ChatColor.WHITE + "적 처치 시 게이지 10을 회복합니다."};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public SuperCloak(GotchaAbility plugin) {
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

                            }
                        }.runTaskTimer(plugin, 0L, 1L);
                    }


                }
                time.put(player, System.currentTimeMillis());
            }
        }
    }


    @EventHandler
    public void onKill(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = ((Player) event.getDamager()).getPlayer();
            List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
            if (abilities.contains(codename)) {
                if ((event.getEntity() instanceof LivingEntity)) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (event.getEntity().isDead()) {
                                gauge.put(player, Math.min(5000, gauge.get(player) + 200));
                            }
                        }
                    }.runTaskLater(plugin, 1L);
                }
            }
        }
    }


    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = ((Player) event.getEntity()).getPlayer();
            List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
            if (abilities.contains(codename)) {
                if ((toggle.get(player) != null) && toggle.get(player)) {
                    event.setDamage(event.getDamage() * 0.8);
                }
            }
        }
    }
}
