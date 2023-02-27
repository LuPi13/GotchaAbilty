package yasking.lupi13.gotchaability;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestManager implements Listener {
    private GotchaAbility plugin;

    public QuestManager(GotchaAbility plugin) {
        this.plugin = plugin;


    }

    //업적클리어, 능력해금
    public static void clearQuest(Player player, String quest, String codename, String unlockingCodename, int index) {
        if (!FileManager.getQuestConfig().getStringList(player.getDisplayName() + "@quest").contains(quest)) {
            List<String> questList = FileManager.getQuestConfig().getStringList(player.getDisplayName() + "@quest");
            questList.add(quest);
            FileManager.getQuestConfig().set(player.getDisplayName() + "@quest", questList);
            List<String> unlockedList = FileManager.getAbilityConfig().getStringList(player.getDisplayName() + "@unlocked");
            unlockedList.add(unlockingCodename);
            FileManager.getAbilityConfig().set(player.getDisplayName() + "@unlocked", unlockedList);
            FileManager.saveAll();
            player.sendMessage("업적 달성!: " + QuestManager.getQuests(codename).get(index).toPlainText());
            player.sendMessage(QuestManager.QuestDetail.get(QuestManager.getQuests(codename).get(index)));
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        }
    }

    public static Map<BaseComponent, String> QuestDetail = new HashMap<>();

    public static List<BaseComponent> getQuests(String codename) {
        List<BaseComponent> quests = new ArrayList<>();

        switch (codename) {
            case "Alchemy":
                BaseComponent Lotto = new TextComponent(ChatColor.GOLD + "복권 당첨!");
                String LottoDetail = "이 능력으로 네더라이트 주괴를 만드세요. (능력 해금: " + Functions.makeDisplayName("연금술+...?", "B*") + ChatColor.RESET + ")";
                Lotto.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(LottoDetail)));
                QuestDetail.put(Lotto, LottoDetail);

                BaseComponent MasterSmith = new TextComponent(ChatColor.AQUA + "마스터 대장장이");
                String MasterSmithDetail = Functions.makeDisplayName("연금술", "B") + ChatColor.RESET + "" + ChatColor.WHITE + ", "  + Functions.makeDisplayName("뜨거운 곡괭이", "B") + ChatColor.RESET + "" + ChatColor.WHITE + ", " + Functions.makeDisplayName("에메랄드 수리", "A") + ChatColor.RESET + "" + ChatColor.WHITE +
                        "를 모두 해금하세요. (능력 해금: " + Functions.makeDisplayName("광석 세공사", "A*") + ChatColor.RESET + ")";
                MasterSmith.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(MasterSmithDetail)));
                QuestDetail.put(MasterSmith, MasterSmithDetail);

                quests.add(Lotto);
                quests.add(MasterSmith);
                break;

            case "HotPickaxe":
            case "EmeraldFix":
                BaseComponent MasterSmith1 = new TextComponent(ChatColor.AQUA + "마스터 대장장이");
                String MasterSmithDetail1 = Functions.makeDisplayName("연금술", "B") + ChatColor.RESET + "" + ChatColor.WHITE + ", "  + Functions.makeDisplayName("뜨거운 곡괭이", "B") + ChatColor.RESET + "" + ChatColor.WHITE + ", " + Functions.makeDisplayName("에메랄드 수리", "A") + ChatColor.RESET + "" + ChatColor.WHITE +
                        "를 모두 해금하세요. (능력 해금: " + Functions.makeDisplayName("광석 세공사", "A*") + ChatColor.RESET + ")";
                MasterSmith1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(MasterSmithDetail1)));
                QuestDetail.put(MasterSmith1, MasterSmithDetail1);

                quests.add(MasterSmith1);
                break;

            case "Dodge":
                BaseComponent DodgeMaster = new TextComponent(ChatColor.AQUA + "회피의 달인");
                String DodgeMasterDetail = "회피를 100번 사용하세요. (능력 해금: " + Functions.makeDisplayName("회피+", "C*") + ChatColor.RESET + ")";
                DodgeMaster.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(DodgeMasterDetail)));
                QuestDetail.put(DodgeMaster, DodgeMasterDetail);

                BaseComponent YourDodge = new TextComponent(ChatColor.DARK_GREEN + "너의 회피는");
                String YourDodgeDetail = "주변에 10마리의 엔더맨을 두고 회피를 시전하세요. (능력 해금: " + Functions.makeDisplayName("엔드의 회피", "B*") + ChatColor.RESET + ")";
                YourDodge.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("???")));
                QuestDetail.put(YourDodge, YourDodgeDetail);

                BaseComponent EnoughDodge = new TextComponent(ChatColor.RED + "이거면 충분해");
                String EnoughDodgeDetail = "회피로 폭발에서 살아남으세요. (능력 해금: " + Functions.makeDisplayName("번개와 같은 반사신경", "B*") + ChatColor.RESET + ")";
                EnoughDodge.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("???")));
                QuestDetail.put(EnoughDodge, EnoughDodgeDetail);

                BaseComponent ShouldDodge = new TextComponent(ChatColor.YELLOW + "굳이 회피해야해?");
                String ShouldDodgeDetail = "투명상태에서 회피를 시전하세요. (능력 해금: " + Functions.makeDisplayName("소멸의 걸음", "A*") + ChatColor.RESET + ")";
                ShouldDodge.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("???")));
                QuestDetail.put(ShouldDodge, ShouldDodgeDetail);

                BaseComponent DodgeCliche = new TextComponent(ChatColor.DARK_PURPLE + "전형적인 회피 클리셰");
                String DodgeClicheDetail = "회피 직후 적을 일격에 처치하세요. (능력 해금: " + Functions.makeDisplayName("치명적인 회피", "A*") + ChatColor.RESET + ")";
                DodgeCliche.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("???")));
                QuestDetail.put(DodgeCliche, DodgeClicheDetail);

                BaseComponent ColdDodge = new TextComponent(ChatColor.DARK_AQUA + "바람처럼 차갑게");
                String ColdDodgeDetail = "추운 온도 속에서 회피를 100번 시전하세요. (능력 해금: " + Functions.makeDisplayName("겨울의 장막", "A*") + ChatColor.RESET + ")";
                ColdDodge.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("???")));
                QuestDetail.put(ColdDodge, ColdDodgeDetail);

                quests.add(DodgeMaster);
                quests.add(YourDodge);
                quests.add(EnoughDodge);
                quests.add(ShouldDodge);
                quests.add(DodgeCliche);
                quests.add(ColdDodge);
                break;

            case "InvisibleDodge":
                BaseComponent IntoTheDark = new TextComponent(ChatColor.BLACK + "어둠 속으로");
                String IntoTheDarkDetail = "10명 이상의 적에게 둘러싸인 상태에서 소멸의 걸음을 시전하세요. (능력 해금: " + Functions.makeDisplayName("어둠그림자 돌격대", "A*") + ChatColor.RESET + ")";
                IntoTheDark.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("???")));
                QuestDetail.put(IntoTheDark, IntoTheDarkDetail);

                quests.add(IntoTheDark);
                break;

            case "BackStab":
                BaseComponent GhostStab = new TextComponent(ChatColor.DARK_GRAY + "고스트 스탭");
                String GhostStabDetail = "백스탭을 이용하여 100킬하세요. (능력 해금: " + Functions.makeDisplayName("스텔스 백스탭", "B*") + ChatColor.RESET + ")";
                GhostStab.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("백스탭을 이용하여 100킬하세요. (능력 해금: " + Functions.makeDisplayName("스텔스 백스탭", "B*") + ChatColor.RESET + ")")));
                QuestDetail.put(GhostStab, GhostStabDetail);

                quests.add(GhostStab);
                break;

            case "ShieldStrike":
                BaseComponent PerfectDistance = new TextComponent(ChatColor.WHITE + "완벽한 거리조절");
                String PerfectDistanceDetail = "돌진이 끝나는 시점에 적과 부딪히세요. (능력 해금: " + Functions.makeDisplayName("방패강타+", "B*") + ChatColor.RESET + ")";
                PerfectDistance.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("돌진이 끝나는 시점에 적과 부딪히세요. (능력 해금: " + Functions.makeDisplayName("방패돌진+", "B*") + ChatColor.RESET + ")")));
                QuestDetail.put(PerfectDistance, PerfectDistanceDetail);

                BaseComponent FireStrike = new TextComponent(ChatColor.DARK_RED + "불타는 돌진");
                String FireStrikeDetail = "자신이 불타고 있을 때, 방패강타로 적을 처치하세요. (능력 해금: " + Functions.makeDisplayName("포효하는 불길", "A*") + ChatColor.RESET + ")";
                FireStrike.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("자신이 불타고 있을 때, 방패강타로 적을 처치하세요. (능력 해금: " + Functions.makeDisplayName("포효하는 불길", "A*") + ChatColor.RESET + ")")));
                QuestDetail.put(FireStrike, FireStrikeDetail);

                quests.add(PerfectDistance);
                quests.add(FireStrike);
                break;

            case "HawkEye":
                BaseComponent GiveAll = new TextComponent(ChatColor.WHITE + "주머니에 있는 거 다 꺼내!");
                String GiveAllDetail = "이 능력으로 100개의 추가 아이템을 획득하세요. (능력 해금: " + Functions.makeDisplayName("매의 눈+", "B*") + ChatColor.RESET + ")";
                GiveAll.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("이 능력을 300개의 추가 아이템을 획득하세요. (능력 해금: " + Functions.makeDisplayName("매의 눈+", "B*") + ChatColor.RESET + ")")));
                QuestDetail.put(GiveAll, GiveAllDetail);

                quests.add(GiveAll);
                break;
        }

        return quests;
    }
}
