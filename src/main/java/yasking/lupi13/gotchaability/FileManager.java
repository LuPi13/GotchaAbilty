package yasking.lupi13.gotchaability;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class FileManager {

    static Plugin plugin = GotchaAbility.getPlugin(GotchaAbility.class);

    private static File AbilityFile;
    private static FileConfiguration AbilityConfig;
    private static File QuestFile;
    private static FileConfiguration QuestConfig;
    private static File MiscFile;
    private static FileConfiguration MiscConfig;

    public static void setupAbility() {
        AbilityFile = new File(Bukkit.getServer().getPluginManager().getPlugin("GotchaAbility").getDataFolder(), "ability.yml");
        if (!AbilityFile.exists()) {
            try {
                AbilityFile.createNewFile();
            }
            catch (IOException exception) {
                System.out.println("\u001B[31m" + "Can't setup Ability file!\n" + ChatColor.RESET + exception);
            }
        }
        AbilityConfig = YamlConfiguration.loadConfiguration(AbilityFile);
    }
    public static FileConfiguration getAbilityConfig() {
        return AbilityConfig;
    }
    public static void saveAbility() {
        try {
            AbilityConfig.save(AbilityFile);
        }
        catch (IOException exception) {
            System.out.println("\u001B[31m" + "Can't save Ability file!\n" + ChatColor.RESET + exception);
        }
    }


    public static void setupQuest() {
        QuestFile = new File(Bukkit.getServer().getPluginManager().getPlugin("GotchaAbility").getDataFolder(), "quest.yml");
        if (!QuestFile.exists()) {
            try {
                QuestFile.createNewFile();
            }
            catch (IOException exception) {
                System.out.println("\u001B[31m" + "Can't setup Quest file!\n" + ChatColor.RESET + exception);
            }
        }
        QuestConfig = YamlConfiguration.loadConfiguration(QuestFile);
    }
    public static FileConfiguration getQuestConfig() {
        return QuestConfig;
    }
    public static void saveQuest() {
        try {
            QuestConfig.save(QuestFile);
        }
        catch (IOException exception) {
            System.out.println("\u001B[31m" + "Can't save Quest file!\n" + ChatColor.RESET + exception);
        }
    }


    public static void setupMisc() {
        MiscFile = new File(Bukkit.getServer().getPluginManager().getPlugin("GotchaAbility").getDataFolder(), "misc.yml");
        if (!MiscFile.exists()) {
            try {
                MiscFile.createNewFile();
            }
            catch (IOException exception) {
                System.out.println("\u001B[31m" + "Can't setup Misc file!\n" + ChatColor.RESET + exception);
            }
        }
        MiscConfig = YamlConfiguration.loadConfiguration(MiscFile);
    }
    public static FileConfiguration getMiscConfig() {
        return MiscConfig;
    }
    public static void saveMisc() {
        try {
            MiscConfig.save(MiscFile);
        }
        catch (IOException exception) {
            System.out.println("\u001B[31m" + "Can't save Misc file!\n" + ChatColor.RESET + exception);
        }
    }


    public static void setupAll() {
        setupAbility();
        setupQuest();
        setupMisc();
    }
    public static void saveAll() {
        saveAbility();
        saveQuest();
        saveMisc();
    }
    public static void reloadAll() {
        AbilityConfig = YamlConfiguration.loadConfiguration(AbilityFile);
        QuestConfig = YamlConfiguration.loadConfiguration(QuestFile);
        MiscConfig = YamlConfiguration.loadConfiguration(MiscFile);
    }
}
