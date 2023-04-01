package yasking.lupi13.gotchaability.abilities.passive;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import yasking.lupi13.gotchaability.*;

import java.util.*;

public class InvisibleDodge implements Listener {
    private GotchaAbility plugin;

    String name = "소멸의 걸음";
    String codename = "InvisibleDodge";
    String grade = "A*";
    Material material = Material.FROGSPAWN;
    String[] strings = {ChatColor.WHITE + "회피 후 은신합니다. 5초가 지나거나, 공격하거나,", ChatColor.WHITE + "피해를 입으면 투명상태에서 벗어납니다.",
            ChatColor.WHITE + "은신 시전/해제 시 자동으로 갑옷이 탈착/장착됩니다.", ChatColor.WHITE + "" + ChatColor.ITALIC + "from. " + ChatColor.BOLD + "DESTINY 2",
            ChatColor.WHITE + "" + ChatColor.ITALIC + "이 능력은 특정 조건을 통해" , ChatColor.WHITE + "" + ChatColor.ITALIC + "다른 능력을 해금할 수 있습니다.",
            QuestManager.getQuests(codename).get(0).toPlainText()};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public InvisibleDodge(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }




    public static Map<Player, Long> time = new HashMap<>();
    public static Map<Player, Long> coolTime = new HashMap<>();

    public static Map<Player, Integer> timer = new HashMap<>();
    public static Map<Player, List<ItemStack>> armors = new HashMap<>();

    @EventHandler
    public void onDoubleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            if (event.isSneaking() && ((LivingEntity) player).isOnGround()) {
                if ((time.get(player) != null) && ((System.currentTimeMillis() - time.get(player)) <= 300)) {
                    if ((coolTime.get(player) == null) || ((coolTime.get(player) != null) && (System.currentTimeMillis() - coolTime.get(player) >= 10000))) {
                        coolTime.put(player, System.currentTimeMillis());

                        Vector velocity = Functions.velocity.get(player).setY(0).normalize();
                        Vector newVel;

                        try {
                            newVel = (velocity.length() >= 2) ? velocity.multiply(3) : velocity.normalize().multiply(3);
                        } catch (NullPointerException ignored) {
                            newVel = player.getLocation().getDirection().setY(0).normalize().multiply(3);
                        }
                        try {
                            player.setVelocity(newVel);


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

                            //IntoTheDark
                            int count = 0;
                            for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
                                if (entity instanceof Monster) {
                                    count += 1;
                                }
                            }
                            if (count >= 10) {
                                QuestManager.clearQuest(player, "IntoTheDark", "InvisibleDodge", "BlightRanger", 0);
                            }

                            timer.put(player, 1);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if ((timer.get(player) >= 1) && (timer.get(player) <= 100)) {
                                        for (Entity entity : player.getNearbyEntities(20, 20, 20)) {
                                            if (entity instanceof Mob) {
                                                ((Mob) entity).setTarget(null);
                                            }
                                        }
                                        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 2, 0, true, false, true));
                                    }

                                    if (timer.get(player) >= 100) {
                                        for (int i = 36; i <= 39; i++) {
                                            if (!armors.get(player).get(i - 36).getType().equals(Material.JIGSAW)) {
                                                player.getInventory().setItem(i, armors.get(player).get(i - 36));
                                            }
                                        }
                                        timer.put(player, 0);
                                        cancel();
                                    }
                                    timer.put(player, timer.get(player) + 1);
                                }
                            }.runTaskTimer(plugin, 0L, 1L);
                        }
                        catch (IllegalArgumentException ignored) {
                        }

                        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ALLAY_AMBIENT_WITHOUT_ITEM, 1F, 2F);
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


    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = ((Player) event.getEntity());
            List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
            if (abilities.contains(codename)) {
                if ((timer.get(player) != null) && (timer.get(player) >= 1) && (timer.get(player) <= 100)) {
                    timer.put(player, 100);
                }
            }
        }
    }

    @EventHandler
    public void onPunch(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = ((Player) event.getDamager());
            List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
            if (abilities.contains(codename)) {
                if ((timer.get(player) != null) && (timer.get(player) >= 1) && (timer.get(player) <= 100)) {
                    timer.put(player, 100);
                }
            }
        }
    }
}
