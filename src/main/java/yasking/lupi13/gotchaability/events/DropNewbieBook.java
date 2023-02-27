package yasking.lupi13.gotchaability.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import yasking.lupi13.gotchaability.ItemManager;


public class DropNewbieBook implements Listener {
    @EventHandler
    public void dropItemEvent(PlayerDropItemEvent event) {
        if (event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals("가챠 능력자 도움말")) {
            event.getItemDrop().remove();
        }
    }
}
