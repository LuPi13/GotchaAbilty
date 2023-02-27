package yasking.lupi13.gotchaability.abilities.passive;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import yasking.lupi13.gotchaability.*;

import java.util.Arrays;
import java.util.List;

public class Alchemy implements Listener {
    private GotchaAbility plugin;

    String name = "연금술";
    String codename = "Alchemy";
    String grade = "B";
    Material material = Material.RAW_GOLD;
    String[] strings = {ChatColor.WHITE + "구리 주괴를 우클릭하면 10개를 소모하여", ChatColor.WHITE + "다른 자원으로 바꿉니다.", ChatColor.WHITE + "확률 정보: '/gotcha alchemy'",
            ChatColor.WHITE + "" + ChatColor.ITALIC + "이 능력은 특정 조건을 통해" , ChatColor.WHITE + "" + ChatColor.ITALIC + "다른 능력을 해금할 수 있습니다.",
            QuestManager.getQuests(codename).get(0).toPlainText(), QuestManager.getQuests(codename).get(1).toPlainText()};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public Alchemy(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }




    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            if (event.getItem() != null && event.getItem().getType().equals(Material.COPPER_INGOT) && event.getItem().getAmount() >= 10) {
                if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    event.getItem().setAmount(event.getItem().getAmount() - 10);
                    double random = Math.random();
                    if (random < 0.2) {
                        player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.COAL, 1));
                        player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 0.4F, 1.7F);
                    }
                    else if (random < 0.4) {
                        player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.IRON_NUGGET, 1));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_TOOLSMITH, 1.0F, 1.5F);
                    }
                    else if (random < 0.5) {
                        player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.IRON_INGOT, 1));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_TOOLSMITH, 1.0F, 1.5F);
                    }
                    else if (random < 0.7) {
                        player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.GOLD_NUGGET, 1));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_TOOLSMITH, 1.0F, 1.5F);
                    }
                    else if (random < 0.8) {
                        player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.GOLD_INGOT, 1));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_TOOLSMITH, 1.0F, 1.5F);
                    }
                    else if (random < 0.9) {
                        player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.REDSTONE, 1));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_TOOLSMITH, 1.0F, 1.5F);
                    }
                    else if (random < 0.95) {
                        player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.LAPIS_LAZULI, 1));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_TOOLSMITH, 1.0F, 1.5F);
                    }
                    else if (random < 0.99) {
                        player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.EMERALD, 1));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_TOOLSMITH, 1.0F, 1.5F);
                    }
                    else if (random < 0.999) {
                        player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.DIAMOND, 1));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_TOOLSMITH, 1.0F, 1.5F);
                        player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 2.0F, 1.0F);
                    }
                    else {
                        player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.NETHERITE_INGOT, 1));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_TOOLSMITH, 1.0F, 1.5F);
                        player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 2.0F, 1.0F);

                        QuestManager.clearQuest(player, "Lotto", "Alchemy", "AlchemyPlus", 0);
                    }
                }
            }
        }
    }
}
