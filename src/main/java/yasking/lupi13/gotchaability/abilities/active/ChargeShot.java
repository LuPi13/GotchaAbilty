package yasking.lupi13.gotchaability.abilities.active;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import yasking.lupi13.gotchaability.*;
import yasking.lupi13.gotchaability.events.ATTRunnable;

import java.util.*;

public class ChargeShot implements Listener {
    private GotchaAbility plugin;

    String name = "차지샷";
    String codename = "ChargeShot";
    String grade = "B*";
    Material material = Material.LIGHT_BLUE_DYE;
    String[] strings = {ChatColor.WHITE + "왼손이 록 버스터로 고정됩니다.", ChatColor.WHITE + "F키를 눌러 충전을 시작하고,", ChatColor.WHITE + "다시 눌러 발사합니다.",
            ChatColor.WHITE + "최대 2초간 충전하여 10의 피해를 줍니다.", ChatColor.WHITE + "충전 중에는 이동 속도가 느려집니다.",
            ChatColor.WHITE + "from. " + ChatColor.AQUA + "" + ChatColor.BOLD + "ROCKMAN"
            //,ChatColor.WHITE + "" + ChatColor.ITALIC + "이 능력은 특정 조건을 통해" , ChatColor.WHITE + "" + ChatColor.ITALIC + "다른 능력을 해금할 수 있습니다.",
            //QuestManager.getQuests(codename).get(0).toPlainText()
    };
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    String activeName = "록 버스터";
    String[] activeStrings = {ChatColor.WHITE + "F키를 눌러 충전, 사격하세요."};
    ItemStack active = Functions.makeActiveItem(material, activeName, Arrays.asList(activeStrings));

    public ChargeShot(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.activeList.add(item);
        ItemManager.hardDenyList.add(active);
        ItemManager.codenameMap.put(item, codename);
        ItemManager.activeMap.put(item, active);
    }


    public static Map<Player, Integer> timer = new HashMap<>();
    public static Map<Player, Boolean> toggle = new HashMap<>();
    public static Map<Interaction, Integer> bulletTimer = new HashMap<>();
    public static Map<Interaction, Vector> bulletVector = new HashMap<>();

    @EventHandler
    public void onSwapHand(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            if (event.getMainHandItem() != null && event.getMainHandItem().equals(active)) {
                event.setCancelled(true);
                if ((toggle.get(player) == null) || !toggle.get(player)) {
                    timer.put(player, 1);
                    toggle.put(player, true);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (timer.get(player) == 1) {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 0.667420F - 0.167420F);
                            }
                            if (timer.get(player) == 3) {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 0.83371F - 0.167420F);
                            }
                            if (timer.get(player) == 4) {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 0.916855F - 0.167420F);
                            }
                            if (timer.get(player) == 5) {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 1F - 0.167420F);
                            }
                            if (timer.get(player) == 7) {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 0.707017F - 0.167420F);
                            }
                            if (timer.get(player) == 9) {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 0.9147395F - 0.167420F);
                            }
                            if (timer.get(player) == 10) {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 1.01860075F - 0.167420F);
                            }
                            if (timer.get(player) == 11) {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 1.122462F - 0.167420F);
                            }
                            if (timer.get(player) == 13) {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 0.749154F - 0.167420F);
                            }
                            if (timer.get(player) == 15) {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 1.0045375F - 0.167420F);
                            }
                            if (timer.get(player) == 16) {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 1.13222925F - 0.167420F);
                            }
                            if (timer.get(player) == 17) {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 1.259921F - 0.167420F);
                            }
                            if (timer.get(player) == 19) {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 0.793071F - 0.167420F);
                            }
                            if (timer.get(player) == 21) {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 1.0639555F - 0.167420F);
                            }
                            if (timer.get(player) == 22) {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 1.19939775F - 0.167420F);
                            }
                            if (timer.get(player) == 23) {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 1.33484F - 0.167420F);
                            }
                            if (timer.get(player) == 25) {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 0.840896F - 0.167420F);
                            }
                            if (timer.get(player) == 27) {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 1.127555F - 0.167420F);
                            }
                            if (timer.get(player) == 28) {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 1.2708845F - 0.167420F);
                            }
                            if (timer.get(player) == 29) {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 1.414214F - 0.167420F);
                            }
                            if (timer.get(player) == 31) {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 0.943874F - 0.167420F);
                            }
                            if (timer.get(player) == 33) {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 1.2656375F - 0.167420F);
                            }
                            if (timer.get(player) == 34) {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 1.42651925F - 0.167420F);
                            }
                            if (timer.get(player) == 35) {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 1.587401F - 0.167420F);
                            }
                            if ((timer.get(player) >= 36) && (timer.get(player) % 6 == 1)) {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, (float) (1F - (((double) timer.get(player) - 40.0) / 60.0)), 1F - 0.167420F);
                            }
                            if ((timer.get(player) >= 36) && (timer.get(player) % 6 == 3)) {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, (float) (1F - (((double) timer.get(player) - 40.0) / 60.0)), 1.3408965F - 0.167420F);
                            }
                            if ((timer.get(player) >= 36) && (timer.get(player) % 6 == 4)) {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, (float) (1F - (((double) timer.get(player) - 40.0) / 60.0)), 1.51134475F - 0.167420F);
                            }
                            if ((timer.get(player) >= 36) && (timer.get(player) % 6 == 5)) {
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, (float) (1F - (((double) timer.get(player) - 40.0) / 60.0)), 1.681793F - 0.167420F);
                            }



                            int amp = Math.min(timer.get(player), 40);

                            StringBuilder gauge = new StringBuilder("CHARGE 【");

                            for (int i = 1; i <= amp; i++) {
                                gauge.append("┃");
                            }
                            for (int j = 40; j > amp; j--) {
                                gauge.append("·");
                            }
                            gauge.append("】 ").append(amp);

                            String color = null;

                            if (amp <= 3) {
                                color = String.valueOf(ChatColor.GRAY);
                            }
                            else if (amp <= 7) {
                                color = String.valueOf(ChatColor.DARK_PURPLE);
                            }
                            else if (amp <= 11) {
                                color = String.valueOf(ChatColor.LIGHT_PURPLE);
                            }
                            else if (amp <= 15) {
                                color = String.valueOf(ChatColor.BLUE);
                            }
                            else if (amp <= 19) {
                                color = String.valueOf(ChatColor.AQUA);
                            }
                            else if (amp <= 23) {
                                color = String.valueOf(ChatColor.DARK_GREEN);
                            }
                            else if (amp <= 27) {
                                color = String.valueOf(ChatColor.GREEN);
                            }
                            else if (amp <= 31) {
                                color = String.valueOf(ChatColor.YELLOW);
                            }
                            else if (amp <= 35) {
                                color = String.valueOf(ChatColor.GOLD);
                            }
                            else if (amp <= 39) {
                                color = String.valueOf(ChatColor.RED);
                            }
                            else {
                                color = String.valueOf(ChatColor.DARK_RED);
                            }

                            BaseComponent text = new TextComponent(color + "" + ChatColor.BOLD + gauge);
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, text);

                            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2, 2, false, false, false));


                            Vector horizontalUnit = player.getLocation().getDirection().crossProduct(new Vector(0, 1, 0)).normalize(); //right is +
                            Vector verticalUnit = horizontalUnit.clone().crossProduct(player.getLocation().getDirection()).normalize(); //up is +

                            Location offHand = player.getEyeLocation().add(player.getLocation().getDirection().multiply(0.2)).add(horizontalUnit.clone().multiply(-0.2)).add(verticalUnit.clone().multiply(-0.1));

                            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(Math.min(40, timer.get(player)) + 125 + (int) (Math.random() * 60), 245 + (int) (Math.random() * 10), 245 + (int) (Math.random() * 10)), Math.min(40, timer.get(player)) * 0.01F);
                            player.getWorld().spawnParticle(Particle.REDSTONE, offHand, Math.min(10, (timer.get(player) / 4) + 1), 0.05, 0.05, 0.05, 0.01, dustOptions, true);

                            if (!toggle.get(player) || player.isDead()) {
                                double damage = Math.min(10, (timer.get(player) / 4));
                                Interaction bullet = player.getWorld().spawn(offHand.clone().subtract(verticalUnit.clone().multiply((float) (damage / 20.0) + 0.5F)).add(horizontalUnit.clone().multiply(-0.4)).add(verticalUnit.clone().multiply(0.2)), Interaction.class);
                                bulletTimer.put(bullet, 1);
                                bulletVector.put(bullet, player.getLocation().getDirection().multiply((damage / 20.0) + 0.5));
                                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1F, 1.5F);

                                bullet.setInvulnerable(true);
                                bullet.setGravity(false);
                                bullet.setPersistent(true);
                                bullet.setInteractionWidth((float) (damage / 20.0) + 0.5F);
                                bullet.setInteractionHeight((float) (damage / 20.0) + 0.5F);
                                bullet.setCustomName(player.getDisplayName() + "@" + damage);
                                bullet.setCustomNameVisible(false);



                                toggle.put(player, false);
                                timer.put(player, -1);
                                cancel();
                            }

                            timer.put(player, timer.get(player) + 1);
                        }
                    }.runTaskTimer(plugin, 0L, 1L);
                }
                else {
                    toggle.put(player, false);
                }
            }
        }
    }
}
