package yasking.lupi13.gotchaability.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.StringUtil;
import yasking.lupi13.gotchaability.GotchaAbility;

import java.util.ArrayList;
import java.util.List;

public class GotchaTab implements TabCompleter {
    Plugin plugin = GotchaAbility.getPlugin(GotchaAbility.class);

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        try {
            if (args.length == 1) {
                List<String> list = new ArrayList<>();
                list.add("help");
                list.add("update");
                list.add("shop");
                list.add("run");
                list.add("select");
                list.add("prob");
                list.add("pickup");
                list.add("alchemy");
                list.add("dictionary");

                StringUtil.copyPartialMatches(args[0], list, completions);
            }
        }
        catch (IndexOutOfBoundsException ignored) {
        }

        return completions;
    }
}
