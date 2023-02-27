package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import yasking.lupi13.gotchaability.*;

import java.util.*;

public class HawkEyePlus implements Listener {
    private GotchaAbility plugin;

    String name = "매의 눈+";
    String codename = "HawkEyePlus";
    String grade = "B*";
    Material material = Material.MUSIC_DISC_13;
    String[] strings = {ChatColor.WHITE + "쿨타임이 10초로 감소합니다. 추가 아이템을", ChatColor.WHITE + "하나 더 드랍합니다."};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public HawkEyePlus(GotchaAbility plugin) {
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
                            for (Material material1 : swords) {
                                player.setCooldown(material1, 200);
                            }
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 0.5F);
                            List<Entity> entities = player.getNearbyEntities(16, 8, 16);
                            if (entities.size() == 0) {
                                return;
                            }
                            Collections.shuffle(entities);
                            for (Entity entity : entities) {
                                if (entity instanceof LivingEntity) {
                                    target.put(player, ((LivingEntity) entity));
                                    ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 200, 0, true, false, false));
                                    break;
                                }
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

                for (ItemStack itemStack : event.getDrops()) {
                    itemStack.setAmount(itemStack.getAmount() * 2);
                }
                event.setDroppedExp(event.getDroppedExp() * 2);

                List<Material> materials = Arrays.asList(Material.values());
                Collections.shuffle(materials);

                int count = 0;
                for (Material material : materials) {
                    if (!ItemManager.getRestricted().contains(material) && material.isItem()) {
                        event.getDrops().add(new ItemStack(material, 1));
                        entity.getKiller().playSound(entity.getKiller().getLocation(), Sound.ITEM_DYE_USE, 1F, 0.5F);
                        count += 1;
                    }
                    if (count == 2) {
                        break;
                    }
                }
            }
            target.remove(entity.getKiller());
        }
    }
}
