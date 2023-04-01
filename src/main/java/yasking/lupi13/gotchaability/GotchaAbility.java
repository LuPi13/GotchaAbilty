package yasking.lupi13.gotchaability;

import org.bukkit.plugin.java.JavaPlugin;
import yasking.lupi13.gotchaability.abilities.active.Chronobreak;
import yasking.lupi13.gotchaability.abilities.active.OverBalance;
import yasking.lupi13.gotchaability.abilities.active.TimeLeaper;
import yasking.lupi13.gotchaability.abilities.active.TimeLeaperPlus;
import yasking.lupi13.gotchaability.abilities.passive.*;
import yasking.lupi13.gotchaability.commands.Gotcha;
import yasking.lupi13.gotchaability.commands.GotchaTab;
import yasking.lupi13.gotchaability.events.*;

public final class GotchaAbility extends JavaPlugin {

    @Override
    public void onEnable() {
        //파일 셋업
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        FileManager.setupAll();
        FileManager.saveAll();

        //능력 등록
        getServer().getPluginManager().registerEvents(new LittleEat(this), this);
        getServer().getPluginManager().registerEvents(new SnowRock(this), this);
        getServer().getPluginManager().registerEvents(new FriendlyUndead(this), this);
        getServer().getPluginManager().registerEvents(new DoubleExp(this), this);
        getServer().getPluginManager().registerEvents(new TerraBurning(this), this);
        getServer().getPluginManager().registerEvents(new UpperCut(this), this);
        getServer().getPluginManager().registerEvents(new PullingBow(this), this);
        getServer().getPluginManager().registerEvents(new LinearBow(this), this);
        getServer().getPluginManager().registerEvents(new FriendlyCreeper(this), this);
        getServer().getPluginManager().registerEvents(new LetsBoom(this), this);
        getServer().getPluginManager().registerEvents(new AquaAffinity(this), this);
        getServer().getPluginManager().registerEvents(new ProjSword(this), this);
        getServer().getPluginManager().registerEvents(new SafetyFirst(this), this);
        getServer().getPluginManager().registerEvents(new Berserker(this), this);
        getServer().getPluginManager().registerEvents(new CookieRun(this), this);
        getServer().getPluginManager().registerEvents(new EmeraldDefense(this), this);
        getServer().getPluginManager().registerEvents(new BookPunch(this), this);
        getServer().getPluginManager().registerEvents(new Alchemy(this), this);
        getServer().getPluginManager().registerEvents(new AlchemyPlus(this), this);
        getServer().getPluginManager().registerEvents(new HotPickaxe(this), this);
        getServer().getPluginManager().registerEvents(new EmeraldFix(this), this);
        getServer().getPluginManager().registerEvents(new MetalSmith(this), this);
        getServer().getPluginManager().registerEvents(new SugarRush(this), this);
        getServer().getPluginManager().registerEvents(new NewtonLaw(this), this);
        getServer().getPluginManager().registerEvents(new LongThrow(this), this);
        getServer().getPluginManager().registerEvents(new BlockThrower(this), this);
        getServer().getPluginManager().registerEvents(new LavaJump(this), this);
        getServer().getPluginManager().registerEvents(new HeavyEnchant(this), this);
        getServer().getPluginManager().registerEvents(new Electrocute(this), this);
        getServer().getPluginManager().registerEvents(new Dodge(this), this);
        getServer().getPluginManager().registerEvents(new DodgePlus(this), this);
        getServer().getPluginManager().registerEvents(new EndDodge(this), this);
        getServer().getPluginManager().registerEvents(new LightReflex(this), this);
        getServer().getPluginManager().registerEvents(new InvisibleDodge(this), this);
        getServer().getPluginManager().registerEvents(new BlightRanger(this), this);
        getServer().getPluginManager().registerEvents(new CriticalDodge(this), this);
        getServer().getPluginManager().registerEvents(new WinterShroud(this), this);
        getServer().getPluginManager().registerEvents(new Counter(this), this);
        getServer().getPluginManager().registerEvents(new BackStab(this), this);
        getServer().getPluginManager().registerEvents(new StealthBackStab(this), this);
        getServer().getPluginManager().registerEvents(new ShieldStrike(this), this);
        getServer().getPluginManager().registerEvents(new ShieldStrikePlus(this), this);
        getServer().getPluginManager().registerEvents(new RoaringFlame(this), this);
        getServer().getPluginManager().registerEvents(new HawkEye(this), this);
        getServer().getPluginManager().registerEvents(new HawkEyePlus(this), this);
        getServer().getPluginManager().registerEvents(new FocusBow(this), this);
        getServer().getPluginManager().registerEvents(new DetailedStrike(this), this);
        getServer().getPluginManager().registerEvents(new PrinceRupertsDrop(this), this);
        getServer().getPluginManager().registerEvents(new TimeLeaper(this), this);
        getServer().getPluginManager().registerEvents(new TimeLeaperPlus(this), this);
        getServer().getPluginManager().registerEvents(new Chronobreak(this), this);
        getServer().getPluginManager().registerEvents(new OverBalance(this), this);
        getServer().getPluginManager().registerEvents(new DefenseAllIn(this), this);
        getServer().getPluginManager().registerEvents(new AdaptiveDefense(this), this);
        getServer().getPluginManager().registerEvents(new KneePropel(this), this);
        getServer().getPluginManager().registerEvents(new Illusion(this), this);

        //기타 이벤트 등록
        getServer().getPluginManager().registerEvents(new Functions(this), this);
        getServer().getPluginManager().registerEvents(new JoinAndQuit(), this);
        getServer().getPluginManager().registerEvents(new DropNewbieBook(), this);
        getServer().getPluginManager().registerEvents(new SoftDenying(this), this);
        getServer().getPluginManager().registerEvents(new HardDenying(), this);
        getServer().getPluginManager().registerEvents(new ShopGUI(this), this);
        getServer().getPluginManager().registerEvents(new GotchaGUI(this), this);
        getServer().getPluginManager().registerEvents(new SelectGUI(this), this);
        getServer().getPluginManager().registerEvents(new DictionaryGUI(this), this);
        getServer().getPluginManager().registerEvents(new QuestManager(this), this);
        ItemManager.init();
        PickUpRotation.PickUp();
        ATTRunnable.init();

        //커맨드 등록
        getCommand("gotcha").setExecutor(new Gotcha(this));
        getCommand("gotcha").setTabCompleter(new GotchaTab());


        System.out.println("\u001B[36m             .                      .           .\u001B[0m\n" +
                           "\u001B[36m         .   .     +                ;       *                          +\u001B[0m\n" +
                           "\u001B[36m             :                  - --+- -                 |\u001B[0m\n" +
                           "\u001B[36m     |       !           .          !                   -+-        .   .\u001B[0m\n" +
                           "\u001B[36m    -+-      |        .             .           +        |                   +\u001B[0m\n" +
                           "\u001B[36m     !      _|_         +   \u001B[33m _____     _       _          _____ _   _ _ _ _       \u001B[0m\n" +
                           "\u001B[36m          ,  | `.           \u001B[33m|   __|___| |_ ___| |_ ___   |  _  | |_|_| |_| |_ _ _ \u001B[0m\n" +
                           "\u001B[36m--  --- --+-<#>-+- ---  --  \u001B[33m|  |  | . |  _|  _|   | .'|  |     | . | | | |  _| | |\u001B[0m\n" +
                           "\u001B[36m  .       `._|_,'           \u001B[33m|_____|___|_| |___|_|_|__,|  |__|__|___|_|_|_|_| |_  |\u001B[0m\n" +
                           "\u001B[36m             T      .       \u001B[33m                                                 |___|\u001B[0m\n" +
                           "\u001B[36m         *.  |                   !       *            \u001B[0m.       .     .-.. . --.\u001B[0m\n" +
                           "\u001B[36m             !   +              -+-        .          \u001B[0m|-.. .  |  . .|-'.'| --|\u001B[0m\n" +
                           "\u001B[36m     .       :         . :       i             -+-    \u001B[0m`-''-|  '-''-''  ''-'--'\u001B[0m\n" +
                           "\u001B[36m             .       *             *         .        \u001B[0m   `-'           ver 0.2.0\u001B[0m\n");
    }

    @Override
    public void onDisable() {
        saveConfig();

        System.out.println("GotchaAbility: saved all. Good Bye!");
    }
}
