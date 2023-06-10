package yasking.lupi13.gotchaability.abilities.active;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import yasking.lupi13.gotchaability.*;
import yasking.lupi13.gotchaability.events.ATTRunnable;

import java.util.*;

public class HandCannon implements Listener {
    private GotchaAbility plugin;

    String name = "핸드캐논";
    String codename = "HandCannon";
    String grade = "C";
    Material material = Material.INK_SAC;
    String[] strings = {ChatColor.WHITE + "왼손이 핸드캐논으로 고정됩니다.", ChatColor.WHITE + "F키를 누르면 1의 피해를 주는 작은 탄환을 발사합니다.",
            ChatColor.WHITE + "" + ChatColor.ITALIC + "이 능력은 특정 조건을 통해" , ChatColor.WHITE + "" + ChatColor.ITALIC + "다른 능력을 해금할 수 있습니다.",
            QuestManager.getQuests(codename).get(0).toPlainText(), QuestManager.getQuests(codename).get(1).toPlainText(), QuestManager.getQuests(codename).get(2).toPlainText(),
            QuestManager.getQuests(codename).get(3).toPlainText(), QuestManager.getQuests(codename).get(4).toPlainText()};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    String activeName = "핸드캐논";
    String[] activeStrings = {ChatColor.WHITE + "F키를 누르면 작은 탄환을 발사합니다."};
    ItemStack active = Functions.makeActiveItem(material, activeName, Arrays.asList(activeStrings));

    public HandCannon(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.activeList.add(item);
        ItemManager.hardDenyList.add(active);
        ItemManager.codenameMap.put(item, codename);
        ItemManager.activeMap.put(item, active);
    }



    @EventHandler
    public void onSwapHand(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            if (event.getMainHandItem() != null && event.getMainHandItem().equals(active)) {
                event.setCancelled(true);

                Snowball bullet = player.getWorld().spawn(player.getEyeLocation().add(player.getLocation().getDirection()), Snowball.class);
                bullet.setGravity(false);
                bullet.setInvulnerable(true);
                bullet.setPersistent(true);
                bullet.setCustomName("handcannon");
                bullet.setCustomNameVisible(false);
                bullet.setItem(new ItemStack(Material.STONE_BUTTON));
                bullet.setVelocity(player.getLocation().getDirection().multiply(3));
                bullet.setShooter(player);
                ATTRunnable.timer.put(bullet, 1);

                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.5F, (float) ((Math.random() * 1.5) + 0.5));

                FileConfiguration playerQuest = FileManager.getQuestConfig();
                if (!playerQuest.contains(player.getDisplayName() + "@handCannonCount")) {
                    playerQuest.set(player.getDisplayName() + "@handCannonCount", 1);
                } else {
                    playerQuest.set(player.getDisplayName() + "@handCannonCount", playerQuest.getInt(player.getDisplayName() + "@handCannonCount") + 1);
                }
                FileManager.saveQuest();

                int handCannonCount = playerQuest.getInt(player.getDisplayName() + "@handCannonCount");
                if (handCannonCount >= 3000) {
                    QuestManager.clearQuest(player, "NoHurt", "HandCannon", "ChargeShot", 2);
                }

                int count = 0;
                for (Snowball snowball : ATTRunnable.timer.keySet()) {
                    if (Objects.equals(snowball.getShooter(), player)) {
                        count += 1;
                    }
                }
                if (count >= 20) {
                    QuestManager.clearQuest(player, "TriggerFinger", "HandCannon", "PulseCannon", 1);
                }
            }
        }
    }
    
    @EventHandler
    public void onHit(ProjectileHitEvent event) {
        if ((event.getEntity() instanceof Snowball) && (Objects.equals(event.getEntity().getCustomName(), "handcannon"))) {
            Projectile bullet = event.getEntity();
            if (event.getHitEntity() instanceof LivingEntity) {
                ((LivingEntity) event.getHitEntity()).damage(1.0, ((Entity) bullet.getShooter()));
                if (event.getHitEntity().isDead()) {
                    Player player = ((Player) bullet.getShooter());
                    FileConfiguration playerQuest = FileManager.getQuestConfig();
                    if (!playerQuest.contains(player.getDisplayName() + "@handCannonKillCount")) {
                        playerQuest.set(player.getDisplayName() + "@handCannonKillCount", 1);
                    } else {
                        playerQuest.set(player.getDisplayName() + "@handCannonKillCount", playerQuest.getInt(player.getDisplayName() + "@handCannonKillCount") + 1);
                    }
                    if ((event.getHitEntity() instanceof Creeper) && ((Creeper) event.getHitEntity()).isPowered()) {
                        QuestManager.clearQuest(player, "BoldForBoom", "HandCannon", "RealHandCannon", 3);
                    }
                    FileManager.saveQuest();

                    int handCannonKillCount = playerQuest.getInt(player.getDisplayName() + "@handCannonKillCount");
                    if (handCannonKillCount >= 100) {
                        QuestManager.clearQuest(player, "NogadaKing", "HandCannon", "ExplosionMove", 0);
                    }
                }
            }
        }
    }
}
