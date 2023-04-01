package yasking.lupi13.gotchaability;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import yasking.lupi13.gotchaability.events.PickUpRotation;
import yasking.lupi13.gotchaability.events.SelectGUI;

import java.util.*;

public class Functions implements Listener {
    private GotchaAbility plugin;
    public Functions(GotchaAbility plugin) {
        this.plugin = plugin;
    }

    //능력 등급 색깔
    public static String getColorGrade(String grade) {
        String color = String.valueOf(ChatColor.WHITE); //default
        if (grade.contains("*")) {
            grade = grade.substring(0, grade.indexOf("*"));
        }
        switch (grade) {
            case "C":
                color = String.valueOf(ChatColor.DARK_AQUA);
                break;
            case "B":
                color = String.valueOf(ChatColor.DARK_GREEN);
                break;
            case "A":
                color = String.valueOf(ChatColor.YELLOW);
                break;
            case "S":
                color = String.valueOf(ChatColor.RED);
                break;
            case "SS":
                color = String.valueOf(ChatColor.DARK_PURPLE);
                break;
        }
        return color;
    }


    //능력의 표기 이름 제작 함수
    public static String makeDisplayName(String name, String grade) {
        String color = getColorGrade(grade);
        return ChatColor.WHITE + "" + ChatColor.BOLD + name + " " + color + "" + ChatColor.BOLD + "(" + grade + ")";
    }


    //능력의 표기 아이템 제작, 등록 함수
    public static ItemStack makeDisplayItem(Material material, String displayName, List<String> lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(displayName);
        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }


    //왼손아이템 제작, 등록
    public static ItemStack makeActiveItem(Material material, String displayName, List<String> lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(displayName);
        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }


    //등급리스트에 자동 추가
    public static void putList(ItemStack item, String grade) {
        switch (grade) {
            case "C":
                ItemManager.cList.add(item);
                break;
            case "C*":
                ItemManager.csList.add(item);
                break;
            case "B":
                ItemManager.bList.add(item);
                break;
            case "B*":
                ItemManager.bsList.add(item);
                break;
            case "A":
                ItemManager.aList.add(item);
                break;
            case "A*":
                ItemManager.asList.add(item);
                break;
            case "S":
                ItemManager.sList.add(item);
                break;
            case "S*":
                ItemManager.ssList.add(item);
                break;
            case "SS*":
                ItemManager.sssList.add(item);
                break;
        }
    }


    //인벤토리 내의 아이템 개수
    public static int countItems(Player player, ItemStack itemStack) {
        ItemStack[] items = player.getInventory().getContents();
        int count = 0;

        for (ItemStack item : items) {
            if ((item != null) && (item.isSimilar(itemStack)) && (item.getAmount() > 0)) {
                count += item.getAmount();
            }
        }

        return count;
    }
    public static int countItems(Player player, Material material) {
        ItemStack[] items = player.getInventory().getContents();
        int count = 0;

        for (ItemStack item : items) {
            if ((item != null) && (item.getType().equals(material)) && (item.getAmount() > 0)) {
                count += item.getAmount();
            }
        }

        return count;
    }


    //인벤토리 내의 아이템 제거
    public static void removeItems(Player player, ItemStack itemStack, int count) {
        Map<Integer, ItemStack> counter = new HashMap<>();
        Inventory inventory = player.getInventory();
        for (int i = 0; i <= 40; i++) {
            if ((inventory.getItem(i) != null) && (inventory.getItem(i).isSimilar(itemStack))) {
                counter.put(i, inventory.getItem(i));
            }
        }
        int found = 0;
        for (ItemStack stack : counter.values()) {
            found += stack.getAmount();
        }

        if (found >= count) {
            for (int index : counter.keySet()) {
                ItemStack stack = counter.get(index);

                int removed = Math.min(count, stack.getAmount());

                count -= removed;

                if (stack.getAmount() == removed) {
                    player.getInventory().setItem(index, null);
                }
                else {
                    stack.setAmount(stack.getAmount() - removed);
                }

                if (count <= 0) {
                    break;
                }
            }

            player.updateInventory();
        }
        else {
            throw new IllegalArgumentException("'count' cannot be smaller than total of items");
        }
    }
    public static void removeItems(Player player, Material material, int count) {
        Map<Integer, ? extends ItemStack> counter = player.getInventory().all(material);
        int found = 0;
        for (ItemStack stack : counter.values()) {
            found += stack.getAmount();
        }

        if (found >= count) {
            for (int index : counter.keySet()) {
                ItemStack stack = counter.get(index);

                int removed = Math.min(count, stack.getAmount());

                count -= removed;

                if (stack.getAmount() == removed) {
                    player.getInventory().setItem(index, null);
                }
                else {
                    stack.setAmount(stack.getAmount() - removed);
                }

                if (count <= 0) {
                    break;
                }
            }

            player.updateInventory();
        }
        else {
            throw new IllegalArgumentException("'count' cannot be smaller than total of items");
        }
    }


    //cover 돌리기
    public static ItemStack rollCover() {
        double ran = Math.random();
        ItemStack item;
        if (ran <= 0.1) {
            item = ItemManager.CoverS;
        }
        else if (ran <= 0.3) {
            item = ItemManager.CoverA;
        }
        else if (ran <= 0.6) {
            item = ItemManager.CoverB;
        }
        else {
            item = ItemManager.CoverC;
        }
        return item;
    }


    //cover 넣기
    public static void coverPut(Player player, Inventory inventory, ItemStack item, int index) {
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 0.5f, 0.8f);
        inventory.setItem(index, item);
    }


    //만악의 근원
    public static ItemStack randomCover(Player player) {
        int sCeiling = FileManager.getMiscConfig().getInt(player.getDisplayName() + "@sCeiling");
        int aCeiling = FileManager.getMiscConfig().getInt(player.getDisplayName() + "@aCeiling");
        double sProb = (sCeiling <= 80) ? 0.005 : (0.005 + ((sCeiling - 80) * 0.05));
        double aProb = (aCeiling <= 6) ? 0.03 : (0.03 + ((aCeiling - 6) * 0.3));
        double remainder = 1 - sProb - aProb;

        ItemStack item;
        double ran = Math.random();
        if (ran <= sProb) {
            FileManager.getMiscConfig().set(player.getDisplayName() + "@sCeiling", 0);
            FileManager.getMiscConfig().set(player.getDisplayName() + "@aCeiling", FileManager.getMiscConfig().getInt(player.getDisplayName() + "@aCeiling") + 1);
            FileManager.saveMisc();
            item = ItemManager.CoverS;
        }
        else if (ran <= (sProb + aProb)) {
            FileManager.getMiscConfig().set(player.getDisplayName() + "@sCeiling", FileManager.getMiscConfig().getInt(player.getDisplayName() + "@sCeiling") + 1);
            FileManager.getMiscConfig().set(player.getDisplayName() + "@aCeiling", 0);
            FileManager.saveMisc();
            item = ItemManager.CoverA;
        }
        else if (ran <= (sProb + aProb + (remainder / 4))) {
            FileManager.getMiscConfig().set(player.getDisplayName() + "@sCeiling", FileManager.getMiscConfig().getInt(player.getDisplayName() + "@sCeiling") + 1);
            FileManager.getMiscConfig().set(player.getDisplayName() + "@aCeiling", FileManager.getMiscConfig().getInt(player.getDisplayName() + "@aCeiling") + 1);
            FileManager.saveMisc();
            item = ItemManager.CoverB;
        }
        else {
            FileManager.getMiscConfig().set(player.getDisplayName() + "@sCeiling", FileManager.getMiscConfig().getInt(player.getDisplayName() + "@sCeiling") + 1);
            FileManager.getMiscConfig().set(player.getDisplayName() + "@aCeiling", FileManager.getMiscConfig().getInt(player.getDisplayName() + "@aCeiling") + 1);
            FileManager.saveMisc();
            item = ItemManager.CoverC;
        }
        return item;
    }


    //add unlocked and cover to ability
    public static void addUnlocked(List<ItemStack> result, Player player, Inventory inventory, int index) {

        //a 픽업
        if (result.equals(ItemManager.aList)) {
            if (FileManager.getMiscConfig().getBoolean(player.getDisplayName() + "@aHalf") || (Math.random() <= 0.5)) {
                FileManager.getMiscConfig().set(player.getDisplayName() + "@aHalf", false);
                List<ItemStack> aPick = PickUpRotation.getPickUpList(0).subList(1, 4);
                Collections.shuffle(aPick);
                List<String> unlocked = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@unlocked");
                if (!unlocked.contains(ItemManager.codenameMap.get(aPick.get(0)))) {
                    unlocked.add(ItemManager.codenameMap.get(aPick.get(0)));
                    FileManager.getAbilityConfig().set(player.getDisplayName() + "@unlocked", unlocked);

                    //업적
                    List<String> quests = FileManager.getQuestConfig().getStringList(player.getDisplayName() + "@quest");
                    if (unlocked.contains("HotPickaxe") && unlocked.contains("Alchemy") && unlocked.contains("EmeraldFix") && !quests.contains("MasterSmith")) {
                        QuestManager.clearQuest(player, "MasterSmith", "Alchemy", "MetalSmith", 1);
                    }
                }
                FileManager.saveAll();
                inventory.setItem(index, aPick.get(0));
                return;
            }
            else {
                FileManager.getMiscConfig().set(player.getDisplayName() + "@aHalf", true);
                FileManager.saveMisc();
            }
        }

        //s 픽업
        if (result.equals(ItemManager.sList)) {
            if (FileManager.getMiscConfig().getBoolean(player.getDisplayName() + "@sHalf") || (Math.random() <= 0.5)) {
                FileManager.getMiscConfig().set(player.getDisplayName() + "@sHalf", false);
                ItemStack sPick = PickUpRotation.getPickUpList(0).get(0);
                List<String> unlocked = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@unlocked");
                if (!unlocked.contains(ItemManager.codenameMap.get(sPick))) {
                    unlocked.add(ItemManager.codenameMap.get(sPick));
                    FileManager.getAbilityConfig().set(player.getDisplayName() + "@unlocked", unlocked);
                }
                FileManager.saveAll();
                inventory.setItem(index, sPick);
                return;
            }
            else {
                FileManager.getMiscConfig().set(player.getDisplayName() + "@sHalf", true);
                FileManager.saveMisc();
            }
        }

        if (result.equals(ItemManager.aList)) {
            for (int i = 0; i <= 2; i++) {
                result.remove(Functions.getItemStackFromMap(FileManager.getMiscConfig().getStringList("APickUp").get(i)));
            }
        }
        if (result.equals(ItemManager.sList)) {
            result.remove(Functions.getItemStackFromMap(FileManager.getMiscConfig().getStringList("SPickUp").get(0)));
        }

        Collections.shuffle(result);
        List<String> unlocked = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@unlocked");
        if (!unlocked.contains(ItemManager.codenameMap.get(result.get(0)))) {
            unlocked.add(ItemManager.codenameMap.get(result.get(0)));
            FileManager.getAbilityConfig().set(player.getDisplayName() + "@unlocked", unlocked);

            //업적
            List<String> quests = FileManager.getQuestConfig().getStringList(player.getDisplayName() + "@quest");
            if (unlocked.contains("HotPickaxe") && unlocked.contains("Alchemy") && unlocked.contains("EmeraldFix") && !quests.contains("MasterSmith")) {
                QuestManager.clearQuest(player, "MasterSmith", "Alchemy", "MetalSmith", 1);
            }
            FileManager.saveAll();
        }
        inventory.setItem(index, result.get(0));
    }
    public static void coverToAbility(Player player, Inventory inventory, int index) {
        if (inventory.getItem(index).equals(ItemManager.CoverC)) {
            List<ItemStack> result = ItemManager.cList;
            addUnlocked(result, player, inventory, index);
        }
        if (inventory.getItem(index).equals(ItemManager.CoverB)) {
            List<ItemStack> result = ItemManager.bList;
            addUnlocked(result, player, inventory, index);
        }
        if (inventory.getItem(index).equals(ItemManager.CoverA)) {
            List<ItemStack> result = ItemManager.aList;
            addUnlocked(result, player, inventory, index);
        }
        if (inventory.getItem(index).equals(ItemManager.CoverS)) {
            List<ItemStack> result = ItemManager.sList;
            addUnlocked(result, player, inventory, index);
        }
    }


    //codenameMap 역추적(String to ItemStack)
    public static ItemStack getItemStackFromMap(String codename) {
        for (ItemStack itemStack : ItemManager.codenameMap.keySet()) {
            if (ItemManager.codenameMap.get(itemStack).equals(codename)) {
                return itemStack;
            }
        }
        return null;
    }


    //unlocked 아이템 리스트
    public static List<ItemStack> getUnlockedItemStacks(Player player) {
        List<ItemStack> unlockedItems = new ArrayList<>();
        for (String unlocked : FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@unlocked")) {
            unlockedItems.add(Functions.getItemStackFromMap(unlocked));
        }
        return unlockedItems;
    }


    //SelectGUI 제작
    public static Inventory makeSelectGUI(Player player, int page) {
        Inventory inventory = Bukkit.createInventory(player, 54, SelectGUI.SelectName);
        List<ItemStack> unlockedItems = getUnlockedItemStacks(player);
        int count = unlockedItems.size();
        int maxPage = (count % 45 == 0) ? count / 45 : (count / 45) + 1;
        //inventory.setItem(45, ItemManager.Close);

        if (page == maxPage) {
            for (int i = 0; i <= (count - (45 * (page - 1)) - 1); i++) {
                inventory.setItem(i, unlockedItems.get((45 * (page - 1)) + i));
            }
        }
        else {
            for (int i = 0; i <= 44; i++) {
                inventory.setItem(i, unlockedItems.get((45 * (page - 1)) + i));
            }
            inventory.setItem(50, ItemManager.NextPage);
        }
        if (page != 1) {
            inventory.setItem(48, ItemManager.PrevPage);
        }
        return inventory;
    }


    //item to grades
    public static String getGrade(ItemStack item) {
        String grade = null;

        if (ItemManager.cList.contains(item)) {
            grade = "C";
        }
        else if (ItemManager.csList.contains(item)) {
            grade = "C*";
        }
        else if (ItemManager.bList.contains(item)) {
            grade = "B";
        }
        else if (ItemManager.bsList.contains(item)) {
            grade = "B*";
        }
        else if (ItemManager.aList.contains(item)) {
            grade = "A";
        }
        else if (ItemManager.asList.contains(item)) {
            grade = "A*";
        }
        else if (ItemManager.sList.contains(item)) {
            grade = "S";
        }
        else if (ItemManager.ssList.contains(item)) {
            grade = "S*";
        }
        else if (ItemManager.sssList.contains(item)) {
            grade = "SS*";
        }

        return grade;
    }


    //속도 측정
    public static HashMap<Player, Vector> velocity = new HashMap<>();
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Vector from = event.getFrom().toVector();
        Vector to;
        try {
            to = event.getTo().toVector();
        }
        catch (NullPointerException ignored) {
            to = from;
        }
        velocity.put(player, to.subtract(from));
    }


    //약화
    public static void entityWeaken(LivingEntity entity, int duration) {
        entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, duration, 0, true, true, true));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, duration, 0, true, true, true));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, duration, 0, true, true, true));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, duration, 0, true, true, true));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, duration, 0, true, true, true));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, duration, 0, true, true, true));
    }
}
