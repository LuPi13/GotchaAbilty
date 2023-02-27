package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import yasking.lupi13.gotchaability.*;

import java.util.Arrays;
import java.util.List;

public class BackStab implements Listener {
    private GotchaAbility plugin;

    String name = "백스탭";
    String codename = "BackStab";
    String grade = "B";
    Material material = Material.GOLDEN_SWORD;
    String[] strings = {ChatColor.WHITE + "적의 뒤 120°에서 근접공격 시", ChatColor.WHITE + "1.5배의 피해를 줍니다.",
            ChatColor.WHITE + "" + ChatColor.ITALIC + "이 능력은 특정 조건을 통해" , ChatColor.WHITE + "" + ChatColor.ITALIC + "다른 능력을 해금할 수 있습니다.",
            QuestManager.getQuests(codename).get(0).toPlainText()};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public BackStab(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }




    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity) {
            Player player = ((Player) event.getDamager());
            LivingEntity target = ((LivingEntity) event.getEntity());
            List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
            if (abilities.contains(codename)) {
                Vector playervec = player.getLocation().getDirection().setY(0).normalize();
                Vector targetvec = target.getLocation().getDirection().setY(0).normalize();

                if (playervec.length() <= 0.01) {
                    float yaw = target.getLocation().getYaw();
                    double x = - Math.sin(yaw);
                    double z = Math.cos(yaw);
                    playervec = new Vector(x, 0, z).normalize();
                }

                if (targetvec.length() <= 0.01) {
                    float yaw = target.getLocation().getYaw();
                    double x = - Math.sin(yaw);
                    double z = Math.cos(yaw);
                    targetvec = new Vector(x, 0, z).normalize();
                }

                if (targetvec.dot(playervec) >= 0.5) {
                    if (event.getDamage() * 1.5 >= target.getHealth()) {
                        FileConfiguration playerQuest = FileManager.getQuestConfig();
                        if (!playerQuest.contains(player.getDisplayName() + "@backStabCount")) {
                            playerQuest.set(player.getDisplayName() + "@backStabCount", 1);
                        } else {
                            playerQuest.set(player.getDisplayName() + "@backStabCount", playerQuest.getInt(player.getDisplayName() + "@backStabCount") + 1);
                        }
                        FileManager.saveQuest();

                        int backStabCount = playerQuest.getInt(player.getDisplayName() + "@backStabCount");
                        if (backStabCount >= 100) {
                            QuestManager.clearQuest(player, "GhostStab", "BackStab", "StealthBackStab", 0);
                        }
                    }

                    event.setDamage(event.getDamage() * 1.5);
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_DOLPHIN_ATTACK, SoundCategory.PLAYERS, 1F, 2F);
                }
            }
        }
    }
}
