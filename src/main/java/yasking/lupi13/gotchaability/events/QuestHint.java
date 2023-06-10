package yasking.lupi13.gotchaability.events;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import yasking.lupi13.gotchaability.ItemManager;
import yasking.lupi13.gotchaability.QuestManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class QuestHint implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getItem() != null && event.getItem().isSimilar(ItemManager.QuestHint)) {
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                List<BaseComponent> quests = new ArrayList<>(QuestManager.QuestDetail.keySet());
                Collections.shuffle(quests);

                BaseComponent random = quests.get(0);

                ItemStack questBook = new ItemStack(Material.PAPER, 1);
                ItemMeta meta = questBook.getItemMeta();
                assert meta != null;
                meta.setDisplayName(random.toPlainText());
                meta.addEnchant(Enchantment.LUCK, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.WHITE + QuestManager.QuestDetail.get(random));
                lore.set(0, lore.get(0).substring(0, lore.get(0).length() - 3) + ChatColor.WHITE + ")");
                meta.setLore(lore);
                questBook.setItemMeta(meta);

                event.setCancelled(true);
                event.getItem().setAmount(event.getItem().getAmount() - 1);
                player.getWorld().dropItemNaturally(player.getEyeLocation(), questBook);
                player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 2F, 0.5F);
            }
        }
    }
}
