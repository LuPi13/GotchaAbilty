package yasking.lupi13.gotchaability.abilities.passive;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import yasking.lupi13.gotchaability.FileManager;
import yasking.lupi13.gotchaability.Functions;
import yasking.lupi13.gotchaability.GotchaAbility;
import yasking.lupi13.gotchaability.ItemManager;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HeavyEnchant implements Listener {
    private GotchaAbility plugin;

    String name = "진보된 지식";
    String codename = "HeavyEnchant";
    String grade = "S";
    Material material = Material.ENCHANTED_BOOK;
    String[] strings = {ChatColor.WHITE + "마법부여대에서 부여받은 모든 마법부여의 등급이", ChatColor.WHITE + "강제로 1 높게 부여됩니다. 또, 붙일 수 없는",
            ChatColor.WHITE + "마법부여를 모루에서 강제로 붙입니다.", ChatColor.YELLOW + "" + ChatColor.BOLD + "주의!" + ChatColor.RESET + "" + ChatColor.YELLOW + " 모루 합성이 완벽히 작동하지 않을 수 있습니다."};
    String displayName = Functions.makeDisplayName(name, grade);
    ItemStack item = Functions.makeDisplayItem(material, displayName, Arrays.asList(strings));

    public HeavyEnchant(GotchaAbility plugin) {
        this.plugin = plugin;
        Functions.putList(item, grade);
        ItemManager.codenameMap.put(item, codename);
    }




    @EventHandler
    public void onEnchant(EnchantItemEvent event) {
        Player player = event.getEnchanter();
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            Map<Enchantment, Integer> enchants = event.getEnchantsToAdd();
            for (Enchantment enchantment : enchants.keySet()) {
                enchants.put(enchantment, enchants.get(enchantment) + 1);
            }
        }
    }

    @EventHandler
    public void onAnvil(PrepareAnvilEvent event) {
        Player player = ((Player) event.getViewers().get(0));
        List<String> abilities = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@ability");
        if (abilities.contains(codename)) {
            ItemStack result = event.getResult();
            if (event.getInventory().getItem(0) != null) {
                result.addUnsafeEnchantments(event.getInventory().getItem(0).getEnchantments());
            }
            if (event.getInventory().getItem(1) != null) {
                result.addUnsafeEnchantments(event.getInventory().getItem(1).getEnchantments());
            }
            event.setResult(result);
        }
    }
}
