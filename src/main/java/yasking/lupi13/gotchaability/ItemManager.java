package yasking.lupi13.gotchaability;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemManager {
    private static GotchaAbility plugin;

    public static List<ItemStack> cList = new ArrayList<>();
    public static List<ItemStack> csList = new ArrayList<>();
    public static List<ItemStack> bList = new ArrayList<>();
    public static List<ItemStack> bsList = new ArrayList<>();
    public static List<ItemStack> aList = new ArrayList<>();
    public static List<ItemStack> asList = new ArrayList<>();
    public static List<ItemStack> sList = new ArrayList<>();
    public static List<ItemStack> ssList = new ArrayList<>();
    public static List<ItemStack> sssList = new ArrayList<>();
    public static List<ItemStack> activeList = new ArrayList<>();
    public static List<ItemStack> softDenyList = new ArrayList<>(); //조합, 상호작용 불가
    public static List<ItemStack> hardDenyList = new ArrayList<>(); //왼손 아이템

    public static Map<ItemStack, String> codenameMap = new HashMap<>();
    public static Map<ItemStack, ItemStack> activeMap = new HashMap<>();

    public static String PossessionName = ChatColor.AQUA + "" + ChatColor.BOLD + "POSSESSION";
    public static ItemStack Possession;
    public static String WillName ="" + ChatColor.BOLD + "WILL";
    public static ItemStack WillC;
    public static ItemStack WillB;
    public static ItemStack WillA;
    public static ItemStack WillS;
    public static ItemStack WillSS;
    public static ItemStack CoverC;
    public static ItemStack CoverB;
    public static ItemStack CoverA;
    public static ItemStack CoverS;
    public static ItemStack NewbieBook;
    public static ItemStack Run;
    //public static ItemStack Close;
    public static ItemStack GotchaDummy;
    public static ItemStack NextPage;
    public static ItemStack PrevPage;
    public static ItemStack QuestHint;

    public static void createPossession() {
        ItemStack item = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(PossessionName);
        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "/gotcha run 에서 해당 아이템 1개를");
        lore.add(ChatColor.WHITE + "소모하여 능력 하나를 뽑습니다.");
        meta.setLore(lore);
        item.setItemMeta(meta);
        softDenyList.add(item);
        Possession = item;
    }

    public static void createWills() {
        String[] grades = {"C", "B", "A", "S", "SS"};
        Material[] materials = {Material.CYAN_CONCRETE, Material.GREEN_CONCRETE, Material.YELLOW_CONCRETE, Material.RED_CONCRETE, Material.PURPLE_CONCRETE};

        for (int i = 0; i <= 4; i++) {
            ItemStack item = new ItemStack(materials[i], 1);
            ItemMeta meta = item.getItemMeta();
            assert meta != null;

            meta.addEnchant(Enchantment.LUCK, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

            List<String> lore = new ArrayList<>();

            String color = Functions.getColorGrade(grades[i]);
            meta.setDisplayName(color + WillName + " (" + grades[i] + ")");
            lore.add(color + "" + ChatColor.BOLD + grades[i] + ChatColor.RESET + "" + ChatColor.WHITE + "등급의 능력을 선택하는 데에 필요한 아이템입니다.");
            meta.setLore(lore);
            item.setItemMeta(meta);

            switch (i) {
                case 0:
                    WillC = item;
                    softDenyList.add(item);
                    break;
                case 1:
                    WillB = item;
                    softDenyList.add(item);
                    break;
                case 2:
                    WillA = item;
                    softDenyList.add(item);
                    break;
                case 3:
                    WillS = item;
                    softDenyList.add(item);
                    break;
                case 4:
                    WillSS = item;
                    softDenyList.add(item);
                    break;
            }
        }
    }

    public static void createCovers() {
        String[] grades = {"C", "B", "A", "S"};
        Material[] materials = {Material.CYAN_CONCRETE_POWDER, Material.GREEN_CONCRETE_POWDER, Material.YELLOW_CONCRETE_POWDER, Material.RED_CONCRETE_POWDER};

        for (int i = 0; i <= 3; i++) {
            ItemStack item = new ItemStack(materials[i], 1);
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            meta.setDisplayName(Functions.getColorGrade(grades[i]) + "" + ChatColor.BOLD + grades[i]);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            item.setItemMeta(meta);

            switch (i) {
                case 0:
                    CoverC = item;
                    break;
                case 1:
                    CoverB = item;
                    break;
                case 2:
                    CoverA = item;
                    break;
                case 3:
                    CoverS = item;
                    break;
            }
        }
    }

    public static void createNewbieBook() {
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK, 1);
        BookMeta meta = (BookMeta) item.getItemMeta();
        assert meta != null;
        meta.setTitle("가챠 능력자 도움말");
        meta.setAuthor(ChatColor.AQUA + "" + ChatColor.BOLD + "LuPi13");
        meta.addPage(ChatColor.AQUA + "\n환영합니다!" +
                ChatColor.RESET + "\n\n 이 플러그인은 제작자가 원신하다가 빡쳐서 만들었습니다. 너네도 당해보시기 바랍니다." +
                "\n\n이 플러그인은 당신에게 추가적인 능력을 제공해 줍니다.");
        meta.addPage("'/gotcha shop' 명령어로, 에메랄드를 이용하여 가챠티켓(" + PossessionName + ChatColor.RESET + "), 능력선택티켓(" + ChatColor.LIGHT_PURPLE + WillName + ChatColor.RESET + ")을 구입할 수 있습니다." +
                "\n\n'/gotcha run' 명령어로, " + PossessionName + ChatColor.RESET + "을 이용하여 능력을 뽑습니다.");
        meta.addPage("'/gotcha select' 명령어로, " + ChatColor.LIGHT_PURPLE + WillName + ChatColor.RESET + "을 이용하여 능력을 정합니다. 좌클릭하여 능력을 적용하고, 쉬프트 좌클릭하여 능력에 있는 업적들을 채팅창에 표시합니다." +
                "\n\n'/gotcha prob' 명령어로, 가챠 확률을 상세히 볼 수 있습니다.");
        meta.addPage("이하는 추가 명령어 설명입니다." +
                "\n /gotcha dictionary: 모든 능력을 봅니다." +
                "\n /gotcha update: 최근 업데이트 정보를 봅니다.");
        meta.addPage("당신의 앞날을 기원하며, " + PossessionName + ChatColor.RESET + " 10개와, A, B, C등급 " + ChatColor.LIGHT_PURPLE + WillName + ChatColor.RESET + "을 1개씩 드립니다." +
                "\n 이 책은 버리면 즉시 사라지며, '/gotcha help'로 다시 볼 수 있습니다.");
        meta.setDisplayName("가챠 능력자 도움말");
        item.setItemMeta(meta);
        NewbieBook = item;
    }

    public static void createRun() {
        ItemStack item = new ItemStack(Material.END_CRYSTAL, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "GOTCHA!");
        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "클릭하여 " + PossessionName + ChatColor.RESET + "" + ChatColor.WHITE + "을");
        lore.add(ChatColor.WHITE + "하나 소모하여 능력을 뽑습니다.");
        lore.add(ChatColor.WHITE + "쉬프트를 누른 상태로 클릭하면");
        lore.add(ChatColor.WHITE + "10연속 뽑기를 합니다.");
        meta.setLore(lore);
        item.setItemMeta(meta);
        Run = item;
    }

    /*
    public static void createClose() {
        ItemStack item = new ItemStack(Material.BARRIER, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "CLOSE");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        Close = item;
    }
     */

    public static void createGotchaDummy() {
        ItemStack item = new ItemStack(Material.DIAMOND_BLOCK, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(" ");
        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        GotchaDummy = item;
    }

    public static void createNextPage() {
        ItemStack item = new ItemStack(Material.LIME_DYE, 1);
        ItemMeta meta = item.getItemMeta();
        assert  meta != null;
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "NEXT");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        NextPage = item;
    }

    public static void createPrevPage() {
        ItemStack item = new ItemStack(Material.PINK_DYE, 1);
        ItemMeta meta = item.getItemMeta();
        assert  meta != null;
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "PREVIOUS");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        PrevPage = item;
    }

    public static void createQuestHint() {
        ItemStack item = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = item.getItemMeta();
        assert  meta != null;
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "마법의 종이");
        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "우클릭하여 아무 업적 하나를 알아냅니다.");
        meta.setLore(lore);

        item.setItemMeta(meta);
        QuestHint = item;
    }


    public static List<Material> getRestricted() {
        List<Material> materials = new ArrayList<>();
        materials.add(Material.SPAWNER);
        materials.add(Material.COMMAND_BLOCK);
        materials.add(Material.CHAIN_COMMAND_BLOCK);
        materials.add(Material.REPEATING_COMMAND_BLOCK);
        materials.add(Material.COMMAND_BLOCK_MINECART);
        materials.add(Material.STRUCTURE_BLOCK);
        materials.add(Material.STRUCTURE_VOID);
        materials.add(Material.JIGSAW);
        materials.add(Material.BARRIER);
        materials.add(Material.DEBUG_STICK);
        materials.add(Material.LIGHT);
        materials.add(Material.AIR);
        materials.add(Material.CAVE_AIR);
        materials.add(Material.VOID_AIR);
        materials.add(Material.WATER);
        materials.add(Material.BUBBLE_COLUMN);
        materials.add(Material.FROSTED_ICE);
        materials.add(Material.LAVA);
        materials.add(Material.FIRE);
        materials.add(Material.SOUL_FIRE);
        materials.add(Material.POWDER_SNOW);
        materials.add(Material.NETHER_PORTAL);
        materials.add(Material.END_PORTAL);
        materials.add(Material.END_GATEWAY);

        return materials;
    }


    public static void init() {
        createPossession();
        createNewbieBook();
        createWills();
        createCovers();
        createRun();
        //createClose();
        createGotchaDummy();
        createNextPage();
        createPrevPage();
        createQuestHint();
    }

    public static void setPlugin(GotchaAbility plugin) {
        ItemManager.plugin = plugin;
    }
}
