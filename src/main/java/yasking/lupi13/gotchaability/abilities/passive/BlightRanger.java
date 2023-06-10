package yasking.lupi13.gotchaability.abilities.passive;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import yasking.lupi13.gotchaability.*;

import java.util.*;

public class BlightRanger implements Listener {
    private GotchaAbility plugin;

    String name = "어둠그림자 돌격대";
    String codename = "BlightRanger";
    String grade = "SS*";
    Material material = Material.WITHER_SKELETON_SKULL;
    String[] strings = {Functions.getItemStackFromMap("InvisibleDodge").getItemMeta().getDisplayName() + ChatColor.WHITE + " 능력을 계승합니다.", ChatColor.WHITE + "은신 지속시간이 7초로 늘어납니다.", ChatColor.WHITE + "회피하면 은신과 함께 연막을 뿌리고, ", ChatColor.WHITE + "이동속도 증가를 얻습니다. 연막에", ChatColor.WHITE + "맞은 적은 약화됩니다. 또, 어둠 속에서",
            ChatColor.WHITE + "7명의 분신이 생겨납니다."};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public BlightRanger(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }




    public static Map<Player, Long> time = new HashMap<>();
    public static Map<Player, Long> coolTime = new HashMap<>();

    public static Map<Player, Integer> timer = new HashMap<>();
    public static Map<Player, List<ItemStack>> armors = new HashMap<>();
    public static Map<Player, Location> center = new HashMap<>();
    public static Map<Player, List<Entity>> nearby = new HashMap<>();

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
                            center.put(player, player.getLocation().add(0, 1, 0));

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
                            player.getWorld().playSound(center.get(player), Sound.ENTITY_ZOMBIE_INFECT, SoundCategory.PLAYERS, 1F, 0.5F);
                            nearby.put(player, player.getNearbyEntities(30, 30, 30));


                            timer.put(player, 1);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if ((timer.get(player) >= 1) && (timer.get(player) <= 140)) {
                                        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB((int) Math.round(Math.random() * 10), (int) Math.round(Math.random() * 10), (int) Math.round(Math.random() * 10)), 4F);
                                        player.getWorld().spawnParticle(Particle.REDSTONE, center.get(player), 100, 3, 0.6, 3, 0, dustOptions, true);
                                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2, 2,true, false, true));


                                        Location centerLine = player.getLocation().subtract(center.get(player));
                                        double angle = Math.toRadians(45);
                                        for (int i = 0; i <= 7; i++) {
                                            double newZ = (centerLine.getZ() * Math.cos(angle * i)) - (centerLine.getX() * Math.sin(angle * i));
                                            double newX = (centerLine.getZ() * Math.sin(angle * i)) + (centerLine.getX() * Math.cos(angle * i));
                                            Location dust = new Location(player.getWorld(), center.get(player).getX() + newX, center.get(player).getY() + centerLine.getY() + 0.9, center.get(player).getZ() + newZ);

                                            player.getWorld().spawnParticle(Particle.SMOKE_LARGE, dust, 10, 0.2, 0.4, 0.2, 0, null, true);

                                            Block block = player.getLocation().subtract(0, 0.1, 0).getBlock();

                                            if ((player.isSprinting()) && !block.getBlockData().getMaterial().equals(Material.AIR)) {
                                                player.getWorld().spawnParticle(Particle.BLOCK_DUST, dust.getX(), dust.getY() - 0.9, dust.getZ(), 1, 0.1, 0.01, 0.1, block.getBlockData());
                                            }
                                            if (!player.isSneaking() && !block.getBlockData().getMaterial().equals(Material.AIR) && Functions.velocity.get(player).length() >= 0.1) {
                                                if (timer.get(player) % 10 == 0) {
                                                    player.getWorld().playSound(dust, block.getBlockData().getSoundGroup().getStepSound(), SoundCategory.PLAYERS, 0.3F, 1F);
                                                }
                                            }
                                        }

                                        for (Entity entity : player.getNearbyEntities(20, 20, 20)) {
                                            if (entity instanceof Mob) {
                                                ((Mob) entity).setTarget(null);
                                            }
                                        }

                                        for (Entity entity : nearby.get(player)) {
                                            if ((entity instanceof LivingEntity) && !(entity.equals(player))) {
                                                if (entity.getLocation().distance(center.get(player)) <= 9) {
                                                    Functions.entityWeaken(((LivingEntity) entity), 60);
                                                }
                                            }
                                        }

                                        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 2, 0, true, false, true));
                                    }

                                    if (timer.get(player) >= 140) {
                                        for (int i = 36; i <= 39; i++) {
                                            if (!armors.get(player).get(i - 36).getType().equals(Material.JIGSAW)) {
                                                player.getInventory().setItem(i, armors.get(player).get(i - 36));
                                            }
                                        }
                                        timer.put(player, 0);
                                        cancel();
                                    }
                                    if (player.isDead()) {
                                        for (ItemStack item : armors.get(player)) {
                                            if (!item.getType().equals(Material.JIGSAW)) {
                                                player.getWorld().dropItemNaturally(player.getLocation(), item);
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

                        player.playSound(player.getLocation(), Sound.ENTITY_ALLAY_AMBIENT_WITHOUT_ITEM, 1F, 2F);
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
                if ((timer.get(player) != null) && (timer.get(player) >= 1) && (timer.get(player) <= 140)) {
                    timer.put(player, 140);
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
                if ((timer.get(player) != null) && (timer.get(player) >= 1) && (timer.get(player) <= 140)) {
                    timer.put(player, 140);
                }
            }
        }
    }
}
