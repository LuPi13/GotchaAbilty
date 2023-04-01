package yasking.lupi13.gotchaability.abilities.passive;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import yasking.lupi13.gotchaability.*;

import java.util.*;

public class Dodge implements Listener {
    private GotchaAbility plugin;

    String name = "회피";
    String codename = "Dodge";
    String grade = "C";
    Material material = Material.RABBIT_FOOT;
    String[] strings = {ChatColor.WHITE + "지상에서 빠르게 두 번 웅크려", ChatColor.WHITE + "이동방향으로 회피합니다. (쿨타임: 10초)",
            ChatColor.WHITE + "" + ChatColor.ITALIC + "이 능력은 특정 조건을 통해" , ChatColor.WHITE + "" + ChatColor.ITALIC + "다른 능력을 해금할 수 있습니다.",
            QuestManager.getQuests(codename).get(0).toPlainText(), QuestManager.getQuests(codename).get(1).toPlainText(), QuestManager.getQuests(codename).get(2).toPlainText(),
            QuestManager.getQuests(codename).get(3).toPlainText(), QuestManager.getQuests(codename).get(4).toPlainText(), QuestManager.getQuests(codename).get(5).toPlainText()};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public Dodge(GotchaAbility plugin) {
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
            if (event.isSneaking() && ((LivingEntity) player).isOnGround()) {
                if ((time.get(player) != null) && ((System.currentTimeMillis() - time.get(player)) <= 300)) {
                    if ((coolTime.get(player) == null) || ((coolTime.get(player) != null) && (System.currentTimeMillis() - coolTime.get(player) >= 10000))) {
                        coolTime.put(player, System.currentTimeMillis());

                        Vector velocity = Functions.velocity.get(player).setY(0);
                        Vector newVel;

                        try {
                            newVel = (velocity.length() >= 2) ? velocity.multiply(3) : velocity.normalize().multiply(3);
                        } catch (NullPointerException ignored) {
                            newVel = player.getLocation().getDirection().setY(0).normalize().multiply(3);
                        }
                        try {
                            player.setVelocity(newVel);
                        }
                        catch (IllegalArgumentException ignored) {
                        }
                        player.getWorld().playSound(player.getLocation(), Sound.ITEM_BUNDLE_DROP_CONTENTS, 1F, 1F);


                        //업적
                        FileConfiguration playerQuest = FileManager.getQuestConfig();
                        if (!playerQuest.contains(player.getDisplayName() + "@dodgeCount")) {
                            playerQuest.set(player.getDisplayName() + "@dodgeCount", 1);
                        } else {
                            playerQuest.set(player.getDisplayName() + "@dodgeCount", playerQuest.getInt(player.getDisplayName() + "@dodgeCount") + 1);
                        }

                        if (player.getLocation().getBlock().getTemperature() <= 0.15) {
                            if (!playerQuest.contains(player.getDisplayName() + "@coldDodgeCount")) {
                                playerQuest.set(player.getDisplayName() + "@coldDodgeCount", 1);
                            } else {
                                playerQuest.set(player.getDisplayName() + "@coldDodgeCount", playerQuest.getInt(player.getDisplayName() + "@coldDodgeCount") + 1);
                            }
                        }
                        FileManager.saveQuest();


                        //DodgeMaster
                        int dodgeCount = playerQuest.getInt(player.getDisplayName() + "@dodgeCount");
                        if (dodgeCount >= 100) {
                            QuestManager.clearQuest(player, "DodgeMaster", "Dodge", "DodgePlus", 0);
                        }

                        //ColdDodge
                        int coldDodgeCount = playerQuest.getInt(player.getDisplayName() + "@coldDodgeCount");
                        if (coldDodgeCount >= 100) {
                            QuestManager.clearQuest(player, "ColdDodge", "Dodge", "WinterShroud", 5);
                        }


                        //YourDodge
                        List<Entity> entities = player.getNearbyEntities(5, 5, 5);
                        int count = 0;
                        for (Entity entity : entities) {
                            if (entity.getType().equals(EntityType.ENDERMAN)) {
                                count += 1;
                            }
                        }
                        if (count >= 10) {
                            QuestManager.clearQuest(player, "YourDodge", "Dodge","EndDodge", 1);
                        }


                        //ShouldDodge
                        if (player.getPotionEffect(PotionEffectType.INVISIBILITY) != null) {
                            QuestManager.clearQuest(player, "ShouldDodge", "Dodge","InvisibleDodge", 3);
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


    //EnoughDodge
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = ((Player) event.getEntity());

            List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
            if (abilities.contains(codename)) {
                if (event.getCause().equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) || event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)) {
                    if ((coolTime.get(player) != null) && (System.currentTimeMillis() - coolTime.get(player) <= 300)) {
                        if (player.getHealth() >= event.getDamage()) {
                            QuestManager.clearQuest(player, "EnoughDodge", "Dodge","LightReflex", 2);
                        }
                    }
                }
            }
        }
    }


    //DodgeCliche
    @EventHandler
    public void onPunch(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = ((Player) event.getDamager());
            List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
            if (abilities.contains(codename)) {
                if ((coolTime.get(player) != null) && (System.currentTimeMillis() - coolTime.get(player) <= 500)) {
                    if (event.getEntity() instanceof LivingEntity) {
                        if (event.getDamage() >= ((LivingEntity) event.getEntity()).getHealth()) {
                            QuestManager.clearQuest(player, "DodgeCliche", "Dodge", "CriticalDodge", 4);
                        }
                    }
                }
            }
        }
    }
}
