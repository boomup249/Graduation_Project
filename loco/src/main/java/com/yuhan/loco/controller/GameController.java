package com.yuhan.loco.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuhan.loco.game.GameDTO;

import java.util.ArrayList;
import java.util.List;

//게임 페이지 관련 컨트롤러
//(현재) 테이블 여러개에서 정보를 join해서 불러와야 함 -> 1안) 디비별로 레포지토리 다 만들어서, dto에 정리해서 넣고 dto 활용하기 
//												※단점: 페이징 처리가 빡셈, entity 및 레포지토리 다 만들어야 함, 전체 데이터를 다루게 돼서 pageable 사용 이점이 별로 없음
//													장점(?): 어차피 2안도 정렬을 위해 불가피하게 전체 데이터를 스프링에서 처리할 가능성이 높음(먼저하냐 나중에 하냐 차이일 뿐) 
//											2안) mysql에서 뷰 만들어서 뷰로 레포지토리 만들어서 활용하기
//												※단점: 스프링에서 후가공이 필요하다면 1안과 별로 차이가 없음, 디비에 뷰를 만들어야해서 수고스러움
//													장점: entity 레포지토리 하나씩만 필요, 스프링 후가공에서 dto를 쓰지않고 정렬이 가능하다면 훨씬 깔끔한 페이징 가능
// + 생각해야 할 것 : 이후에 로그인 유저가 선호 & 비선호 하는 장르에 따라 정렬(?)을 해야 함
//					-> 1안 로직) (spring) 선호 장르, 비선호 장르 체크 - 비선호 맨 아래로, 선호 위로(이때 여러개 선택 시 어떻게 섞을 지 회의) - 정리한걸 dto에 넣음 - 그대로 페이징 함
//					-> 2안 로직) (spring) 선호 장르, 비선호 장르 체크 - 선호도에 따라 view 정렬을 고침(가능?) - view 활용
//		일단 디비 확정되면 정하기 -> 디비 크롤링 팀이랑 맞춰야 함(현재 크롤링 항목과 디비가 다름)

//2023.08.26 -> dto 방식으로 초안 작업
@Controller
public class GameController {
	//전역
	String page = "";
	
	//서비스 나중에 만들고 바꾸기
	/*
	private final UserService userService;
	private final PreferService preferService;

	public GameController(UserService userService, PreferService preferService) {
        this.userService = userService;
        this.preferService = preferService;
    }
    */
	
	//게임 목록 작성
	private List<GameDTO> createFullGameList() {
		//지금은 테스트용
				//steam
				GameDTO g1 = new GameDTO(1, "PUBG: BATTLEGROUNDS", "무료 플레이", "할인 X", "할인 X", "https://cdn.cloudflare.steamstatic.com/steam/apps/578080/capsule_231x87.jpg?t=1689138385");
				GameDTO g2 = new GameDTO(2, "Apex 레전드™", "무료 플레이", "할인 X", "할인 X", "https://cdn.cloudflare.steamstatic.com/steam/apps/1172470/capsule_231x87.jpg?t=1689459756");
				GameDTO g3 = new GameDTO(3, "Grand Theft Auto V: 프리미엄 에디션", "₩ 44,000", "₩ 16,500", "-63%", "https://cdn.cloudflare.steamstatic.com/steam/bundles/5699/qipqf90z2z7h4x3i/capsule_231x87_koreana.jpg?t=1678931390");
				GameDTO g4 = new GameDTO(4, "데이브 더 다이버", "₩ 24,000", "할인 X", "할인 X", "https://cdn.cloudflare.steamstatic.com/steam/apps/1868140/capsule_231x87.jpg?t=1689558218");
				GameDTO g5 = new GameDTO(5,	"데스티니 가디언즈", "무료 플레이", "할인 X", "할인 X", "https://cdn.cloudflare.steamstatic.com/steam/apps/1085660/capsule_231x87_koreana.jpg?t=1684966156");
				GameDTO g6 = new GameDTO(6, "Counter-Strike: Global Offensive", "무료 플레이", "할인 X", "할인 X", "https://cdn.cloudflare.steamstatic.com/steam/apps/730/capsule_231x87.jpg?t=1683566799");
				GameDTO g7 = new GameDTO(7, "Grand Theft Auto V", "₩ 44,000", "₩ 16,500", "-63%", "https://cdn.cloudflare.steamstatic.com/steam/apps/271590/capsule_231x87.jpg?t=1678296348");
				GameDTO g8 = new GameDTO(8, "Limbus Company", "무료 플레이", "할인 X", "할인 X", "https://cdn.cloudflare.steamstatic.com/steam/apps/1973530/capsule_231x87.jpg?t=1685082822");
				GameDTO g9 = new GameDTO(9, "BattleBit Remastered", "₩ 18,860", "할인 X", "할인 X", "https://cdn.cloudflare.steamstatic.com/steam/apps/671860/capsule_231x87.jpg?t=1686877598");
				GameDTO g10 = new GameDTO(10, "Yu-Gi-Oh! Master Duel", "무료 플레이", "할인 X", "할인 X", "https://cdn.cloudflare.steamstatic.com/steam/apps/1449850/capsule_231x87.jpg?t=1688458743");
				
				GameDTO g11 = new GameDTO(11, "War Thunder", "무료 플레이", "할인 X", "할인 X", "https://cdn.cloudflare.steamstatic.com/steam/apps/236390/capsule_231x87.jpg?t=1689324181");
				GameDTO g12 = new GameDTO(12, "Undawn", "무료 플레이", "할인 X", "할인 X", "https://cdn.cloudflare.steamstatic.com/steam/apps/1881700/capsule_231x87.jpg?t=1688028875");
				GameDTO g13 = new GameDTO(13, "Yet Another Zombie Survivors", "₩ 11,000", "₩ 8,800", "-20%", "https://cdn.cloudflare.steamstatic.com/steam/apps/2163330/capsule_231x87.jpg?t=1689267965");
				GameDTO g14 = new GameDTO(14, "Tom Clancy's Rainbow Six® Siege", "₩ 22,000", "할인 X", "할인 X", "https://cdn.cloudflare.steamstatic.com/steam/apps/359550/capsule_231x87.jpg?t=1685724234");
				GameDTO g15 = new GameDTO(15, "F1® 23", "₩ 99,000", "₩ 59,400", "-40%", "https://cdn.cloudflare.steamstatic.com/steam/apps/2108330/capsule_231x87.jpg?t=1688987889");
				GameDTO g16 = new GameDTO(16, "Kairosoft Bundle", "₩ 577,000", "₩ 446,240", "-23%", "https://cdn.cloudflare.steamstatic.com/steam/bundles/25263/jj8i6f14a5595drk/capsule_231x87.jpg?t=1662033809");
				GameDTO g17 = new GameDTO(17, "원피스 해적무쌍 4", "₩ 64,800", "₩ 9,720", "-85%", "https://cdn.cloudflare.steamstatic.com/steam/apps/1089090/capsule_231x87_koreana.jpg?t=1678851805");
				GameDTO g18 = new GameDTO(18, "Sid Meier’s Civilization® VI", "₩ 65,000", "₩ 6,500", "-90%", "https://cdn.cloudflare.steamstatic.com/steam/apps/289070/capsule_231x87.jpg?t=1680898825");
				GameDTO g19 = new GameDTO(19, "데이브 더 다이버 Deluxe Edition", "₩ 34,000", "₩ 30,600", "-10%", "https://cdn.cloudflare.steamstatic.com/steam/bundles/32958/sv4mk97vuyb8kgft/capsule_231x87.jpg?t=1687943001");
				GameDTO g20 = new GameDTO(20, "Xenonauts 2", "₩ 39,900", "₩ 29,920", "-25%", "https://cdn.cloudflare.steamstatic.com/steam/apps/538030/capsule_231x87.jpg?t=1689741073");
				
				GameDTO g21 = new GameDTO(21, "Baldur's Gate 3", "₩ 66,000", "할인 X", "할인 X", "https://cdn.cloudflare.steamstatic.com/steam/apps/1086940/capsule_231x87.jpg?t=1689348322");
				GameDTO g22 = new GameDTO(22, "GrandChase", "무료 플레이", "할인 X", "할인 X", "https://cdn.cloudflare.steamstatic.com/steam/apps/985810/capsule_231x87.jpg?t=1688538435");
				GameDTO g23 = new GameDTO(23, "It Takes Two", "₩ 44,000", "₩ 17,600", "-60%", "https://cdn.cloudflare.steamstatic.com/steam/apps/1426210/capsule_231x87.jpg?t=1679951279");
				GameDTO g24 = new GameDTO(24, "Super Jigsaw Puzzle: Generations - Full Pack", "₩ 803,600", "₩ 400,910", "-50%", "https://cdn.cloudflare.steamstatic.com/steam/bundles/9964/116qikbwbb45orz3/capsule_231x87.jpg?t=1556091136");
				GameDTO g25 = new GameDTO(25, "Ready or Not", "₩ 43,000", "₩ 34,400", "-20%", "https://cdn.cloudflare.steamstatic.com/steam/apps/1144200/capsule_231x87.jpg?t=1688390724");
				GameDTO g26 = new GameDTO(26, "Sid Meier’s Civilization® VI Anthology", "₩ 232,200", "₩ 33,210", "-86%", "https://cdn.cloudflare.steamstatic.com/steam/bundles/21432/djvtj4go7ymkt5lv/capsule_231x87.jpg?t=1669073178");
				GameDTO g27 = new GameDTO(27, "Street Fighter 6", "₩ 72,700", "할인 X", "할인 X", "https://cdn.cloudflare.steamstatic.com/steam/apps/1364780/capsule_231x87.jpg?t=1686291121");
				GameDTO g28 = new GameDTO(28, "옥토패스 트래블러 II", "₩ 69,800", "₩ 52,350",	"-25%", "https://cdn.cloudflare.steamstatic.com/steam/apps/1971650/capsule_231x87.jpg?t=1688650936");
				GameDTO g29 = new GameDTO(29, "Dead by Daylight", "₩ 21,000", "할인 X	", "할인 X", "https://cdn.cloudflare.steamstatic.com/steam/apps/381210/capsule_231x87.jpg?t=1687878531");
				GameDTO g30 = new GameDTO(30, "ELDEN RING", "₩ 64,800", "할인 X", "할인 X", "https://cdn.cloudflare.steamstatic.com/steam/apps/1245620/capsule_231x87.jpg?t=1683618443");
				
				GameDTO g31 = new GameDTO(31, "SHMUP All Stars", "₩ 333,500", "₩ 300,150", "-10%", "https://cdn.cloudflare.steamstatic.com/steam/bundles/18371/a9n0fut2ua4bd3kv/capsule_231x87.jpg?t=1640152686");
				GameDTO g32 = new GameDTO(32, "소울워커", "무료 플레이", "할인 X", "할인 X", "https://cdn.cloudflare.steamstatic.com/steam/apps/1825750/capsule_231x87_koreana.jpg?t=1681854232");
				GameDTO g33 = new GameDTO(33, "Rebellion Anthology", "₩ 452,300", "₩ 370,890", "-18%", "https://cdn.cloudflare.steamstatic.com/steam/bundles/646/y6u8ejuci6htztk5/capsule_231x87.jpg?t=1501602620");
				GameDTO g34 = new GameDTO(34, "Human: Fall Flat", "₩ 20,500", "₩ 6,150", "-70%", "https://cdn.cloudflare.steamstatic.com/steam/apps/477160/capsule_231x87_alt_assets_1.jpg?t=1689672396");
				GameDTO g35 = new GameDTO(35, "Remnant II", "에약 구매", "할인 X", "할인 X	", "https://cdn.cloudflare.steamstatic.com/steam/apps/1282100/capsule_231x87.jpg?t=1689706767");
				GameDTO g36 = new GameDTO(36, "Ember Knights", "₩ 21,500", "₩ 18,270", "-15%", "https://cdn.cloudflare.steamstatic.com/steam/apps/1135230/capsule_231x87_alt_assets_6_koreana.jpg?t=1689683633");
				GameDTO g37 = new GameDTO(37, "V Rising", "₩ 20,500", "₩ 16,400", "-20%", "https://cdn.cloudflare.steamstatic.com/steam/apps/1604030/capsule_231x87.jpg?t=1684394834");
				GameDTO g38 = new GameDTO(38, "AI＊Shoujo/AI＊少女", "₩ 74,400", "할인 X", "할인 X	", "https://cdn.cloudflare.steamstatic.com/steam/apps/1250650/capsule_231x87.jpg?t=1666085923");
				GameDTO g39 = new GameDTO(39, "Stardew Valley", "₩ 16,000", "할인 X", "할인 X", "https://cdn.cloudflare.steamstatic.com/steam/apps/413150/capsule_231x87.jpg?t=1666917466");
				GameDTO g40 = new GameDTO(40, "콜 오브 듀티®: 모던 워페어 II | 워존", "₩ 84,500", "할인 X", "할인 X", "https://cdn.cloudflare.steamstatic.com/steam/apps/1938090/capsule_231x87_koreana.jpg?t=1687571108");
				
				GameDTO g41 = new GameDTO(41, "Team Fortress 2", "무료 플레이", "할인 X", "할인 X", "https://cdn.cloudflare.steamstatic.com/steam/apps/440/capsule_231x87.jpg?t=1682961494");
				GameDTO g42 = new GameDTO(42, "Rust", "₩ 46,000", "할인 X", "할인 X", "https://cdn.cloudflare.steamstatic.com/steam/apps/252490/capsule_231x87.jpg?t=1678981332");
				GameDTO g43 = new GameDTO(43, "Halls of Torment", "₩ 6,000", "할인 X", "할인 X", "https://cdn.cloudflare.steamstatic.com/steam/apps/2218750/capsule_231x87.jpg?t=1684937598");
				GameDTO g44 = new GameDTO(44, "Only Up!", "₩ 8,900", "할인 X", "할인 X", "https://cdn.cloudflare.steamstatic.com/steam/apps/2381590/capsule_231x87.jpg?t=1689443414");
				GameDTO g45 = new GameDTO(45, "Raft", "₩ 21,000", "할인 X", "할인 X", "https://cdn.cloudflare.steamstatic.com/steam/apps/648800/capsule_231x87.jpg?t=1655744208");
				GameDTO g46 = new GameDTO(46, "Viewfinder", "₩ 27,000", "할인 X", "할인 X", "https://cdn.cloudflare.steamstatic.com/steam/apps/1382070/capsule_231x87.jpg?t=1689686419");
				GameDTO g47 = new GameDTO(47, "Project Zomboid", "₩ 21,500", "할인 X", "할인 X", "https://cdn.cloudflare.steamstatic.com/steam/apps/108600/capsule_231x87.jpg?t=1679306018");
				GameDTO g48 = new GameDTO(48, "NARAKA: BLADEPOINT", "무료 플레이", "할인 X", "할인 X", "https://cdn.cloudflare.steamstatic.com/steam/apps/1203220/capsule_231x87.jpg?t=1689749054");
				GameDTO g49 = new GameDTO(49, "컨커러스 블레이드", "무료 플레이", "할인 X", "할인 X", "https://cdn.cloudflare.steamstatic.com/steam/apps/835570/capsule_231x87.jpg?t=1688970179");
				GameDTO g50 = new GameDTO(50, "Maneater", "₩ 41,000", "₩ 14,350", "-65%", "https://cdn.cloudflare.steamstatic.com/steam/apps/629820/capsule_231x87.jpg?t=1649877867");
				
				
				//epic
				GameDTO ag1 = new GameDTO(1, "Fortnite", "무료", "X", "X", "https://cdn1.epicgames.com/offer/fn/25BR_S25_EGS_Launcher_Blade_1200x1600_1200x1600-1127a3b880c3b307cbd13d9fd3dd8495?h=480&quality=medium&resize=1&w=360");
				GameDTO ag2 = new GameDTO(2, "Grand Theft Auto V: Premium Edition", "₩33,000", "X", "X", "https://cdn1.epicgames.com/0584d2013f0149a791e7b9bad0eec102/offer/GTAV_EGS_Artwork_1200x1600_Portrait%20Store%20Banner-1200x1600-382243057711adf80322ed2aeea42191.jpg?h=480&quality=medium&resize=1&w=360");
				GameDTO ag3 = new GameDTO(3, "VALORANT", "무료", "X", "X", "https://cdn1.epicgames.com/offer/cbd5b3d310a54b12bf3fe8c41994174f/EGS_VALORANT_RiotGames_S2_1200x1600-b911781672bac23a556586fb92c42983?h=480&quality=medium&resize=1&w=360");
				GameDTO ag4 = new GameDTO(4, "NARAKA: BLADEPOINT", "무료", "X", "X", "https://cdn1.epicgames.com/offer/0c6aee83b9b64372bf44a043001325f2/EGS_NARAKABLADEPOINT_24Entertainment_S2_1200x1600-0828f82ab96ff2be38169b992ddef860?h=480&quality=medium&resize=1&w=360");
				GameDTO ag5 = new GameDTO(5, "Genshin Impact", "무료", "X", "X", "https://cdn1.epicgames.com/salesEvent/salesEvent/EGS_GenshinImpact_miHoYoLimited_S2_1200x1600-c12cdcc2cac330df2185aa58c508e820?h=480&quality=medium&resize=1&w=360");
				GameDTO ag6 = new GameDTO(6, "Fall Guys", "무료", "X", "X", "https://cdn1.epicgames.com/offer/50118b7f954e450f8823df1614b24e80/EGS_FallGuys_Mediatonic_S2_1200x1600-f8dd257a8537df053a64c4e5063e62ea?h=480&quality=medium&resize=1&w=360");
				GameDTO ag7 = new GameDTO(7, "Honkai: Star Rail", "무료", "X", "X", "https://cdn1.epicgames.com/offer/a2dcbb9e34204bda9da8415f97b3f4ea/v1_1200x1600-fc41913532d45172cbc5fdd866eabf58?h=480&quality=medium&resize=1&w=360");
				GameDTO ag8 = new GameDTO(8, "Sid Meier’s Civilization® VI", "₩65,000", "X", "X", "https://cdn1.epicgames.com/cd14dcaa4f3443f19f7169a980559c62/offer/EGS_SidMeiersCivilizationVI_FiraxisGames_S2-860x1148-bffad83909595b7c5c60489a17056a59.jpg?h=480&quality=medium&resize=1&w=360");
				GameDTO ag9 = new GameDTO(9, "The Sims™ 4", "무료", "X", "X", "https://cdn1.epicgames.com/offer/2a14cf8a83b149919a2399504e5686a6/EGS_TheSims4_ElectronicArts_S2_1200x1600-ceadc3bd1e6f885ad64d9f115f51f5c0?h=480&quality=medium&resize=1&w=360");
				GameDTO ag10 = new GameDTO(10, "EA SPORTS™ FIFA 23 Standard Edition", "₩77,000", "X", "X", "https://cdn1.epicgames.com/offer/f5deacee017b4b109476933f7dd2edbd/EGS_EASPORTSFIFA23StandardEdition_EACanada_S2_1200x1600-c806355d9cc8b35ebe392f2a7db03075?h=480&quality=medium&resize=1&w=360");
				
				GameDTO ag11 = new GameDTO(11, "Train Valley 2", "₩15,300", "무료", "-100%", "https://cdn1.epicgames.com/spt-assets/6d6a89f661f74d70bdc8be636c577056/download-train-valley-2-offer-1n4tc.jpg?h=480&quality=medium&resize=1&w=360");
				GameDTO ag12 = new GameDTO(12, "Dying Light Enhanced Edition", "₩34,800", "X", "X", "https://cdn1.epicgames.com/offer/2c42520d342a46d7a6e0cfa77b4715de/StoreVertical1200x16001_1200x1600-c3984f399a98d62bc56770577508b890?h=480&quality=medium&resize=1&w=360");
				GameDTO ag13 = new GameDTO(13, "Red Dead Redemption 2", "₩66,000", "X", "X", "https://cdn1.epicgames.com/epic/offer/RDR2PC1227_Epic%20Games_860x1148-860x1148-b4c2210ee0c3c3b843a8de399bfe7f5c.jpg?h=480&quality=medium&resize=1&w=360");
				GameDTO ag14 = new GameDTO(14, "Dead by Daylight", "₩21,000", "X", "X", "https://cdn1.epicgames.com/offer/611482b8586142cda48a0786eb8a127c/EGS_DeadbyDaylight_BehaviourInteractive_S2_1200x1600-a1c30209e3b9fb63144daa9b5670f503?h=480&quality=medium&resize=1&w=360");
				GameDTO ag15 = new GameDTO(15, "ARK: Survival Evolved", "₩20,000", "X", "X", "https://cdn1.epicgames.com/ark/offer/EGS_ARKSurvivalEvolved_StudioWildcard_S2-1200x1600-5b58fdefea9f885c7426e894a1034921.jpg?h=480&quality=medium&resize=1&w=360");
				GameDTO ag16 = new GameDTO(16, "Fallout: New Vegas", "₩11,000", "X", "X", "https://cdn1.epicgames.com/offer/3428aaab2c674c98b3acb789dcfaa548/EGS_FalloutNewVegas_ObsidianEntertainment_S2_1200x1600-866fe8b8f56e2e7bb862c49bf0627b9a?h=480&quality=medium&resize=1&w=360");
				GameDTO ag17 = new GameDTO(17, "theHunter: Call of the Wild™", "₩20,500", "X", "X", "https://cdn1.epicgames.com/salesEvent/salesEvent/EGS_theHunterCalloftheWild_ExpansiveWorlds_S2_1200x1600-1e9b46aaabc33fe0a08cf5b418e76ba2?h=480&quality=medium&resize=1&w=360");
				GameDTO ag18 = new GameDTO(18, "Borderlands 3", "₩64,900", "X", "X", "https://cdn1.epicgames.com/offer/catnip/EGS_Borderlands3_Takeover_Mobile-560x760-58be14c3e086e3a85bedcc21c08ab5cb-560x760-58be14c3e086e3a85bedcc21c08ab5cbjpg_560x760-58be14c3e086e3a85bedcc21c08ab5cb?h=480&quality=medium&resize=1&w=360");
				GameDTO ag19 = new GameDTO(19, "PAYDAY 2", "₩10,500", "X", "X", "https://cdn1.epicgames.com/spt-assets/14eb3477a6084940b49de5aa73c60f98/mammoth-tfbxg.jpg?h=480&quality=medium&resize=1&w=360");
				GameDTO ag20 = new GameDTO(20, "League of Legends", "무료", "X", "X", "https://cdn1.epicgames.com/offer/24b9b5e323bc40eea252a10cdd3b2f10/LoL_1200x1600-15ad6c981af8d98f50e833eac7843986?h=480&quality=medium&resize=1&w=360");
				
				GameDTO ag21 = new GameDTO(21, "Bloons TD 6", "₩14,000", "X", "X", "https://cdn1.epicgames.com/spt-assets/764b2d57552c436590f50318bd7587f9/download-bloons-td-6-offer-100fo.jpg?h=480&quality=medium&resize=1&w=360");
				GameDTO ag22 = new GameDTO(22, "GRIME", "₩25,000", "X", "X", "https://cdn1.epicgames.com/spt-assets/2a14cec8a1fc4ab9a74dd46bf147c9e6/grime-1xh9t.jpg?h=480&quality=medium&resize=1&w=360");
				GameDTO ag23 = new GameDTO(23, "Among Us", "₩5,000", "X", "X", "https://cdn1.epicgames.com/salesEvent/salesEvent/amogusportrait_1200x1600-66ad0e4d363e1c92f9f8aae67a96dd31?h=480&quality=medium&resize=1&w=360");
				GameDTO ag24 = new GameDTO(24, "Cyberpunk 2077", "₩66,000", "X", "X", "https://cdn1.epicgames.com/offer/77f2b98e2cef40c8a7437518bf420e47/EGS_Cyberpunk2077_CDPROJEKTRED_S2_03_1200x1600-b1847981214ac013383111fc457eb9c5?h=480&quality=medium&resize=1&w=360");
				GameDTO ag25 = new GameDTO(25, "Cities: Skylines", "₩32,000", "X", "X", "https://cdn1.epicgames.com/6009be9994c2409099588cde6f3a5ed0/offer/EGS_CitiesSkylines_ColossalOrder_S2-1200x1600-753257537706de74735d2bc3eb656b67.jpg?h=480&quality=medium&resize=1&w=360");
				GameDTO ag26 = new GameDTO(26, "Satisfactory", "₩31,000", "X", "X", "https://cdn1.epicgames.com/offer/crab/U7_Epic_1200x1600_Logo_1200x1600-ed7f1a9f8a59acbfaba61af7accc0a4b?h=480&quality=medium&resize=1&w=360");
				GameDTO ag27 = new GameDTO(27, "HITMAN World of Assassination", "₩71,600", "X", "X", "https://cdn1.epicgames.com/offer/ed55aa5edc5941de92fd7f64de415793/EGS_HITMANWorldofAssassination_IOInteractiveAS_Bundles_S2_1200x1600-1f5eb0ca3e8855875b9f0757748337b4?h=480&quality=medium&resize=1&w=360");
				GameDTO ag28 = new GameDTO(28, "PUBG: BATTLEGROUNDS", "무료", "X", "X", "https://cdn1.epicgames.com/spt-assets/53ec4985296b4facbe3a8d8d019afba9/pubg-battlegrounds-15qal.jpg?h=480&quality=medium&resize=1&w=360");
				GameDTO ag29 = new GameDTO(29, "Idle Champions of the Forgotten Realms", "무료", "X", "X", "https://cdn1.epicgames.com/offer/7e508f543b05465abe3a935960eb70ac/IdleChampions_FreeGamesPromo_1200x16001_1200x1600-764258f78394b9f79815bbd9010e8454?h=480&quality=medium&resize=1&w=360");
				GameDTO ag30 = new GameDTO(30, "Destiny 2", "무료", "X", "X", "https://cdn1.epicgames.com/offer/428115def4ca4deea9d69c99c5a5a99e/FR_Bungie_Destiny2_S2_1200x1600_1200x1600-c04030c570b63cdced320be0f88a9f89?h=480&quality=medium&resize=1&w=360");
				
				GameDTO ag31 = new GameDTO(31, "SnowRunner", "₩31,800", "X", "X", "https://cdn1.epicgames.com/2744acda6a2e493e9894b389b6564df7/offer/Diesel_productv2_snowrunner_home_SnowRunner_EpicGames_PortraitImage_860x1148-860x1148-bdf591a23e5a348671dc2a77465d0ba6c5871d68-860x1148-5a5adeae2140a3f0b5586750f70bf8e2-860x1148-5a5adeae2140a3f0b5586750f70bf8e2.jpg?h=480&quality=medium&resize=1&w=360");
				GameDTO ag32 = new GameDTO(32, "Tom Clancy's Rainbow Six® Siege", "₩22,000", "X", "X", "https://cdn1.epicgames.com/offer/carnation/Carousel_BoxArt_1200x1600_1200x1600-6888b9d57181d8fcfb3472a7f70ecc49?h=480&quality=medium&resize=1&w=360");
				GameDTO ag33 = new GameDTO(33, "Shadow of the Tomb Raider: Definitive Edition", "₩44,400", "X", "X", "https://cdn1.epicgames.com/offer/4b5461ca8d1c488787b5200b420de066/egs-shadowofthetombraiderdefinitiveedition-eidosmontralcrystaldynamicsnixxessoftware-s4-1200x1600-7ee40d6fa744_1200x1600-950cdb624cc75d04fe3c8c0b62ce98de?h=480&quality=medium&resize=1&w=360");
				GameDTO ag34 = new GameDTO(34, "STAR WARS™ Battlefront™ II: Celebration Edition", "₩47,000", "X", "X", "https://cdn1.epicgames.com/b156c3365a5b4cb9a01a5e1108b4e3f4/offer/EGS_STARWARSBattlefrontIICelebrationEdition_DICE_S2-1200x1600-11d040719a8457bbf36cabbe89b200db.jpg?h=480&quality=medium&resize=1&w=360");
				GameDTO ag35 = new GameDTO(35, "Football Manager 2023", "₩59,000", "X", "X", "https://cdn1.epicgames.com/offer/5c7a78e0c4d640898d690c5e38c0392f/EGS_FootballManager2023_SportsInteractive_S2_1200x1600-f3675b8335f3ebe5837d7d525764f4ab?h=480&quality=medium&resize=1&w=360");
				GameDTO ag36 = new GameDTO(36, "World War Z Aftermath", "₩41,000", "X", "X", "https://cdn1.epicgames.com/salesEvent/salesEvent/wwzAfterTall_1200x1600-b7b0af3e21961a57059ec4a3019e57fb?h=480&quality=medium&resize=1&w=360");
				GameDTO ag37 = new GameDTO(37, "Farming Simulator 19", "₩19,800", "X", "X", "https://cdn1.epicgames.com/epic/offer/Diesel_productv2_farming-simulator-19_home_EGS-GIANTS-FS19-860x1148_Standard-860x1148-a464360aa738f506399aff4c95d0a9c7021dee5f-860x1148-71df4a510f82c42cebe1b1861d2f77a3.jpg?h=480&quality=medium&resize=1&w=360");
				GameDTO ag38 = new GameDTO(38, "Dead Island 2", "₩65,000", "X", "X", "https://cdn1.epicgames.com/offer/236c74b4cd2e4e3099cbe2ebdc9686fd/EGS_DeadIsland2_DeepSilverDambusterStudios_S2_1200x1600-efc5201842cf642eb45f73227cd0789b?h=480&quality=medium&resize=1&w=360");
				GameDTO ag39 = new GameDTO(39, "Remnant: From the Ashes", "₩41,000", "X", "X", "https://cdn1.epicgames.com/663e521f2a444199be58152fd93fa66e/offer/EGS_RemnantFromtheAshes_GunfireGames_S2-1200x1600-394c6f196cf496047862762b3cc0c2a9.jpg?h=480&quality=medium&resize=1&w=360");
				GameDTO ag40 = new GameDTO(40, "Warframe", "무료", "X", "X", "https://cdn1.epicgames.com/offer/244aaaa06bfa49d088205b13b9d2d115/EGS_Warframe_DigitalExtremes_S2_1200x1600-99f3631ae6224e7a4b41f87fe9758504?h=480&quality=medium&resize=1&w=360");
				
				GameDTO ag41 = new GameDTO(41, "Battlefield™ 2042", "₩66,000", "X", "X", "https://cdn1.epicgames.com/offer/52f57f57120c440fad9bfa0e6c279317/EGS_Battlefield2042_DICE_S2_1200x1600-ed71f5e6188d8c4c411947f4b1fc59bc?h=480&quality=medium&resize=1&w=360");
				GameDTO ag42 = new GameDTO(42, "Europa Universalis IV", "₩43,000", "X", "X", "https://cdn1.epicgames.com/salesEvent/salesEvent/EGS_EuropaUniversalisIV_ParadoxDevelopmentStudioParadoxTinto_S4_1200x1600-381fe4bad2c0951566b465ab1a46726d?h=480&quality=medium&resize=1&w=360");
				GameDTO ag43 = new GameDTO(43, "Overcooked! 2", "₩26,000", "X", "X", "https://cdn1.epicgames.com/salesEvent/salesEvent/egs-overcooked2-tall_1200x1600-fbae89fad70c05cd1979316f620e64a9?h=480&quality=medium&resize=1&w=360");
				GameDTO ag44 = new GameDTO(44, "Trackmania", "무료", "X", "X", "https://cdn1.epicgames.com/salesEvent/salesEvent/Trackmania_Royal_KeyArt_1200x1600_1200x1600-baf0e1c64be7ae2fefa18ba9e845f4c2?h=480&quality=medium&resize=1&w=360");
				GameDTO ag45 = new GameDTO(45, "Chivalry 2", "₩41,000", "X", "X", "https://cdn1.epicgames.com/offer/bd46d4ce259349e5bd8b3ded20274737/EGS_Chivalry2_TornBannerStudios_S2_1200x1600-ba6ddba52b502bf668935c8a86adf402?h=480&quality=medium&resize=1&w=360");
				GameDTO ag46 = new GameDTO(46, "Farming Simulator 22", "₩44,000", "X", "X", "https://cdn1.epicgames.com/salesEvent/salesEvent/725818d8-9768-4a6a-a723-3039aaee1e23_1200x1600-3b6116d3fc1b566b71251d04bce27938?h=480&quality=medium&resize=1&w=360");
				GameDTO ag47 = new GameDTO(47, "Rise of the Tomb Raider: 20 Year Celebration", "₩59,900", "X", "X", "https://cdn1.epicgames.com/salesEvent/salesEvent/EGS_RiseoftheTombRaider20YearCelebration_CrystalDynamics_S2_1200x1600-a73f93bb560142e08d56db4c2bfdb03c?h=480&quality=medium&resize=1&w=360");
				GameDTO ag48 = new GameDTO(48, "Hogwarts Legacy", "₩68,800", "X", "X", "https://cdn1.epicgames.com/offer/e97659b501af4e3981d5430dad170911/EGS_HogwartsLegacy_AvalancheSoftware_S2_1200x1600-2bb94423bf1c7e2fca10577d9f2878b9?h=480&quality=medium&resize=1&w=360");
				GameDTO ag49 = new GameDTO(49, "The Witcher 3: Wild Hunt – Complete Edition", "₩49,900", "X", "X", "https://cdn1.epicgames.com/offer/14ee004dadc142faaaece5a6270fb628/EGS_TheWitcher3WildHuntCompleteEdition_CDPROJEKTRED_S2_1200x1600-53a8fb2c0201cd8aea410f2a049aba3f?h=480&quality=medium&resize=1&w=360");
				GameDTO ag50 = new GameDTO(50, "Subnautica", "₩31,000", "X", "X", "https://cdn1.epicgames.com/offer/jaguar/SN_EpicPortrait_1200x1800-f5ebad930a2f6eb715579a056807033f?h=480&quality=medium&resize=1&w=360");
				
				
				List<GameDTO> gList = new ArrayList<>();
				
				gList.add(g1); gList.add(g2); gList.add(g3); gList.add(g4); gList.add(g5); gList.add(g6); gList.add(g7); gList.add(g8); gList.add(g9); gList.add(g10);
				gList.add(g11); gList.add(g12); gList.add(g13); gList.add(g14); gList.add(g15); gList.add(g16); gList.add(g17); gList.add(g18); gList.add(g19); gList.add(g20);
				gList.add(g21); gList.add(g22); gList.add(g23); gList.add(g24); gList.add(g25); gList.add(g26); gList.add(g27); gList.add(g28); gList.add(g29); gList.add(g30);
				gList.add(g31); gList.add(g32); gList.add(g33); gList.add(g34); gList.add(g35); gList.add(g36); gList.add(g37); gList.add(g38); gList.add(g39); gList.add(g40);
				gList.add(g41); gList.add(g42); gList.add(g43); gList.add(g44); gList.add(g45); gList.add(g46); gList.add(g47); gList.add(g48); gList.add(g49); gList.add(g50);
				gList.add(g1); gList.add(g2); gList.add(g3); gList.add(g4); gList.add(g5); gList.add(g6); gList.add(g7); gList.add(g8); gList.add(g9); gList.add(g10);
				gList.add(g11); gList.add(g12); gList.add(g13); gList.add(g14); gList.add(g15); gList.add(g16); gList.add(g17); gList.add(g18); gList.add(g19); gList.add(g20);
				gList.add(g21); gList.add(g22); gList.add(g23); gList.add(g24); gList.add(g25); gList.add(g26); gList.add(g27); gList.add(g28); gList.add(g29); gList.add(g30);
				gList.add(g31); gList.add(g32); gList.add(g33); gList.add(g34); gList.add(g35); gList.add(g36); gList.add(g37); gList.add(g38); gList.add(g39); gList.add(g40);
				gList.add(g41); gList.add(g42); gList.add(g43); gList.add(g44); gList.add(g45); gList.add(g46); gList.add(g47); gList.add(g48); gList.add(g49); gList.add(g50);
				
				
				gList.add(ag1); gList.add(ag2); gList.add(ag3); gList.add(ag4); gList.add(ag5); gList.add(ag6); gList.add(ag7); gList.add(ag8); gList.add(ag9); gList.add(ag10);
				gList.add(ag11); gList.add(ag12); gList.add(ag13); gList.add(ag14); gList.add(ag15); gList.add(ag16); gList.add(ag17); gList.add(ag18); gList.add(ag19); gList.add(ag20);
				gList.add(ag21); gList.add(ag22); gList.add(ag23); gList.add(ag24); gList.add(ag25); gList.add(ag26); gList.add(ag27); gList.add(ag28); gList.add(ag29); gList.add(ag30);
				gList.add(ag31); gList.add(ag32); gList.add(ag33); gList.add(ag34); gList.add(ag35); gList.add(ag36); gList.add(ag37); gList.add(ag38); gList.add(ag39); gList.add(ag40);
				gList.add(ag41); gList.add(ag42); gList.add(ag43); gList.add(ag44); gList.add(ag45); gList.add(ag46); gList.add(ag47); gList.add(ag48); gList.add(ag49); gList.add(ag50);
				gList.add(ag1); gList.add(ag2); gList.add(ag3); gList.add(ag4); gList.add(ag5); gList.add(ag6); gList.add(ag7); gList.add(ag8); gList.add(ag9); gList.add(ag10);
				gList.add(ag11); gList.add(ag12); gList.add(ag13); gList.add(ag14); gList.add(ag15); gList.add(ag16); gList.add(ag17); gList.add(ag18); gList.add(ag19); gList.add(ag20);
				gList.add(ag21); gList.add(ag22); gList.add(ag23); gList.add(ag24); gList.add(ag25); gList.add(ag26); gList.add(ag27); gList.add(ag28); gList.add(ag29); gList.add(ag30);
				gList.add(ag31); gList.add(ag32); gList.add(ag33); gList.add(ag34); gList.add(ag35); gList.add(ag36); gList.add(ag37); gList.add(ag38); gList.add(ag39); gList.add(ag40);
				gList.add(ag41); gList.add(ag42); gList.add(ag43); gList.add(ag44); gList.add(ag45); gList.add(ag46); gList.add(ag47); gList.add(ag48); gList.add(ag49); gList.add(ag50);
				
				
				//리스트 넘기기
				return gList;
	}
	
	//게임 목록 페이지에 맞게 가공
	private Page<GameDTO> createPageableList(List<GameDTO> fullList, int page, int pageSize) {
	    int start = page * pageSize; //0부터 시작하므로, 곱하면 시작 배열 index 나옴
	    int end = Math.min(start + pageSize, fullList.size()); //남은 페이지 항목이 10개 미만일 수 있으므로

	    List<GameDTO> sublist = fullList.subList(start, end); //해당 페이지에 들어갈 리스트

	    return new PageImpl<>(sublist, PageRequest.of(page, pageSize), fullList.size());
	}
	
	
	//연결
	//console
	@GetMapping("/pc")
	public String pc(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
		//(1안 기준)디비 확정되면 디비에 맞춰 바꾸기 -> 디비에서 받아와서 가공해서 dto에 차례로 넣고 dtoList 넘기기
		
		//페이징용 page, pageable은 0부터 시작함 -> -1로 가공해주기, html에서도 가공 필요
		page -= 1;
		
		//dto리스트 전체 불러오기
		List<GameDTO> fullList = createFullGameList();
		
		// 전체 데이터 리스트에서 해당 페이지의 데이터만 추출하여 페이징
		int pageSize = 10; //한 페이지에 나올 행 수
		Page<GameDTO> gamePage = createPageableList(fullList, page, pageSize); //페이징 끝
		
		//페이지네이션 정보 가공: 시작 페이지 번호, 현재 페이지 번호, 끝 페이지 번호
		int currentPage = page + 1; //현재 페이지 번호
		int calcEnd = (int)(Math.ceil(currentPage / 10.0) * 10); //현재 페이지를 10으로 나눈 후 올리고 10을 곱하면 끝번호가 나옴(ex. 3-> 0.3 - 1 - 10, 23-> 2.3 - 3 - 30)
		int startPage = calcEnd - 9; //시작 페이지 번호
		
		//끝 페이지 번호 확정
		int endPage = Math.min(calcEnd, gamePage.getTotalPages());
		
		//페이징 객체 model에 넣기
		model.addAttribute("gamePage", gamePage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", gamePage.getTotalPages());
		
		return "/game/pc";
	}
	
	
	
	
}
