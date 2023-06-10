package yasking.lupi13.gotchaability.abilities.passive;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import yasking.lupi13.gotchaability.*;

import java.util.*;

public class HawkEye implements Listener {
    private GotchaAbility plugin;

    String name = "매의 눈";
    String codename = "HawkEye";
    String grade = "B";
    Material material = Material.MUSIC_DISC_13;
    String[] strings = {ChatColor.WHITE + "검을 우클릭하면 주변 무작위 1명의 적에게", ChatColor.WHITE + "발광을 부여합니다. 발광이 부여된 적을 처치하면", ChatColor.WHITE + "전리품이 2배로 증가하며, 랜덤한",
            ChatColor.WHITE + "아이템 하나를 추가로 드랍합니다. (쿨타임: 20초)", ChatColor.YELLOW + "주의! 플레이어의 전리품은 2배로 증가하지 않습니다.",
            ChatColor.WHITE + "" + ChatColor.ITALIC + "이 능력은 특정 조건을 통해" , ChatColor.WHITE + "" + ChatColor.ITALIC + "다른 능력을 해금할 수 있습니다.",
            QuestManager.getQuests(codename).get(0).toPlainText()};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public HawkEye(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }



    public static Map<Player, LivingEntity> target = new HashMap<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            Material[] swords = {Material.WOODEN_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLDEN_SWORD, Material.DIAMOND_SWORD, Material.NETHERITE_SWORD};
            if (event.getItem() != null) {
                for (Material material : swords) {
                    if (event.getItem().getType().equals(material)) {
                        if (player.getCooldown(event.getItem().getType()) == 0) {
                            List<Entity> entities = player.getNearbyEntities(16, 8, 16);
                            if (entities.size() != 0) {
                                for (Material material1 : swords) {
                                    player.setCooldown(material1, 400);
                                }
                                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 0.5F);
                                Collections.shuffle(entities);
                                for (Entity entity : entities) {
                                    if (entity instanceof LivingEntity) {
                                        target.put(player, ((LivingEntity) entity));
                                        ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 200, 0, true, false, false));
                                        break;
                                    }
                                }
                            }
                            else {
                                BaseComponent text = new TextComponent(ChatColor.YELLOW + "주변에 적이 없습니다!");
                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, text);
                            }
                        }
                        break;
                    }
                }
            }
        }
    }


    @EventHandler
    public void onKill(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.getKiller() != null && target.get(entity.getKiller()) != null && target.get(entity.getKiller()).equals(entity)) {
            if (entity.getPotionEffect(PotionEffectType.GLOWING) != null) {
                List<Material> materials = Arrays.asList(Material.values());
                Collections.shuffle(materials);

                for (Material material : materials) {
                    if (!ItemManager.getRestricted().contains(material) && material.isItem()) {
                        event.setDroppedExp(event.getDroppedExp() * 2);
                        if (!(entity instanceof Player)) {
                            for (ItemStack itemStack : event.getDrops()) {
                                itemStack.setAmount(itemStack.getAmount() * 2);
                            }
                        }
                        event.getDrops().add(new ItemStack(material, 1));

                        entity.getKiller().playSound(entity.getKiller().getLocation(), Sound.ITEM_ARMOR_EQUIP_LEATHER, 1F, 0.5F);

                        FileConfiguration playerQuest = FileManager.getQuestConfig();
                        if (!playerQuest.contains(entity.getKiller().getDisplayName() + "@hawkEyeCount")) {
                            playerQuest.set(entity.getKiller().getDisplayName() + "@hawkEyeCount", 1);
                        } else {
                            playerQuest.set(entity.getKiller().getDisplayName() + "@hawkEyeCount", playerQuest.getInt(entity.getKiller().getDisplayName() + "@hawkEyeCount") + 1);
                        }
                        FileManager.saveQuest();

                        int dodgeCount = playerQuest.getInt(entity.getKiller().getDisplayName() + "@hawkEyeCount");
                        if (dodgeCount >= 100) {
                            QuestManager.clearQuest(entity.getKiller(), "GiveAll", "HawkEye", "HawkEyePlus", 0);
                        }

                        break;
                    }
                }
            }
            target.remove(entity.getKiller());
        }
    }
}
