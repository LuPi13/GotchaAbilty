package yasking.lupi13.gotchaability.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JoinAndQuit implements Listener {
    Plugin plugin = GotchaAbility.getPlugin(GotchaAbility.class);

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()) {
            FileManager.getAbilityConfig().set(player.getDisplayName() + "@ability", new ArrayList<String>());
            FileManager.getAbilityConfig().set(player.getDisplayName() + "@unlocked", new ArrayList<String>());

            FileManager.getQuestConfig().set(player.getDisplayName() + "@quest", new ArrayList<String>());

            FileManager.getMiscConfig().set(player.getDisplayName() + "@aCeiling", 0);
            FileManager.getMiscConfig().set(player.getDisplayName() + "@sCeiling", 0);
            FileManager.getMiscConfig().set(player.getDisplayName() + "@aHalf", false);
            FileManager.getMiscConfig().set(player.getDisplayName() + "@sHalf", false);

            FileManager.saveAll();


            player.getInventory().addItem(ItemManager.NewbieBook);
            for (int i = 0; i <= 9; i++) {
                player.getInventory().addItem(ItemManager.Possession);
            }
            player.getInventory().addItem(ItemManager.WillC);
            player.getInventory().addItem(ItemManager.WillB);
            player.getInventory().addItem(ItemManager.WillA);
        }
    }

    @EventHandler
    public void onExit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
    }
}
