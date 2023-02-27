package yasking.lupi13.gotchaability;

import org.bukkit.plugin.java.JavaPlugin;
import yasking.lupi13.gotchaability.abilities.passive.*;
import yasking.lupi13.gotchaability.commands.Gotcha;
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

        //기타 이벤트 등록
        getServer().getPluginManager().registerEvents(new Functions(this), this);
        getServer().getPluginManager().registerEvents(new JoinAndQuit(), this);
        getServer().getPluginManager().registerEvents(new DropNewbieBook(), this);
        getServer().getPluginManager().registerEvents(new softDenying(this), this);
        getServer().getPluginManager().registerEvents(new ShopGUI(this), this);
        getServer().getPluginManager().registerEvents(new GotchaGUI(this), this);
        getServer().getPluginManager().registerEvents(new SelectGUI(this), this);
        getServer().getPluginManager().registerEvents(new QuestManager(this), this);
        ItemManager.init();
        PickUpRotation.PickUp();

        //커맨드 등록
        getCommand("gotcha").setExecutor(new Gotcha(this));


        System.out.println("Welcome to HELL");
    }

    @Override
    public void onDisable() {
        saveConfig();

        System.out.println("Welcome to HELL");
    }
}
