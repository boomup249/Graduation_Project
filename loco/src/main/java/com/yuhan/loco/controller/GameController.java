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
				GameDTO g1 = new GameDTO(1, "PUBG: BATTLEGROUNDS", "https://cdn.cloudflare.steamstatic.com/steam/apps/578080/capsule_231x87.jpg?t=1689138385");
				GameDTO g2 = new GameDTO(2, "Apex 레전드™", "https://cdn.cloudflare.steamstatic.com/steam/apps/1172470/capsule_231x87.jpg?t=1689459756");
				GameDTO g3 = new GameDTO(3, "Grand Theft Auto V: 프리미엄 에디션", "https://cdn.cloudflare.steamstatic.com/steam/bundles/5699/qipqf90z2z7h4x3i/capsule_231x87_koreana.jpg?t=1678931390");
				GameDTO g4 = new GameDTO(4, "데이브 더 다이버", "https://cdn.cloudflare.steamstatic.com/steam/apps/1868140/capsule_231x87.jpg?t=1689558218");
				GameDTO g5 = new GameDTO(5, "데스티니 가디언즈", "https://cdn.cloudflare.steamstatic.com/steam/apps/1085660/capsule_231x87_koreana.jpg?t=1684966");
				GameDTO g6 = new GameDTO(6, "Counter-Strike: Global Offensive", "https://cdn.cloudflare.steamstatic.com/steam/apps/730/capsule_231x87.jpg?t=1683566799");
				GameDTO g7 = new GameDTO(7, "Grand Theft Auto V", "https://cdn.cloudflare.steamstatic.com/steam/apps/271590/capsule_231x87.jpg?t=1678296348");
				GameDTO g8 = new GameDTO(8, "Limbus Company", "https://cdn.cloudflare.steamstatic.com/steam/apps/1973530/capsule_231x87.jpg?t=1685082822");
				GameDTO g9 = new GameDTO(9, "BattleBit Remastered", "https://cdn.cloudflare.steamstatic.com/steam/apps/671860/capsule_231x87.jpg?t=1686877598");
				GameDTO g10 = new GameDTO(10, "Yu-Gi-Oh! Master Duel", "https://cdn.cloudflare.steamstatic.com/steam/apps/1449850/capsule_231x87.jpg?t=1688458743");
				GameDTO g11 = new GameDTO(11, "War Thunder", "https://cdn.cloudflare.steamstatic.com/steam/apps/236390/capsule_231x87.jpg?t=1689324181");
				GameDTO g12 = new GameDTO(12, "Undawn", "https://cdn.cloudflare.steamstatic.com/steam/apps/1881700/capsule_231x87.jpg?t=1688028875");
				GameDTO g13 = new GameDTO(13, "Yet Another Zombie Survivors", "https://cdn.cloudflare.steamstatic.com/steam/apps/2163330/capsule_231x87.jpg?t=1689267965");
				GameDTO g14 = new GameDTO(14, "Tom Clancy's Rainbow Six® Siege", "https://cdn.cloudflare.steamstatic.com/steam/apps/359550/capsule_231x87.jpg?t=1685724234");
				GameDTO g15 = new GameDTO(15, "F1® 23", "https://cdn.cloudflare.steamstatic.com/steam/apps/2108330/capsule_231x87.jpg?t=1688987889");
				GameDTO g16 = new GameDTO(16, "Kairosoft Bundle", "https://cdn.cloudflare.steamstatic.com/steam/bundles/25263/jj8i6f14a5595drk/capsule_231x87.jpg?t=1662033809");
				GameDTO g17 = new GameDTO(17, "원피스 해적무쌍 4", "https://cdn.cloudflare.steamstatic.com/steam/apps/1089090/capsule_231x87_koreana.jpg?t=1678851805");
				GameDTO g18 = new GameDTO(18, "Sid Meier’s Civilization® VI", "https://cdn.cloudflare.steamstatic.com/steam/apps/289070/capsule_231x87.jpg?t=1680898825");
				GameDTO g19 = new GameDTO(19, "데이브 더 다이버 Deluxe Edition", "https://cdn.cloudflare.steamstatic.com/steam/bundles/32958/sv4mk97vuyb8kgft/capsule_231x87.jpg?t=1687943001");
				GameDTO g20 = new GameDTO(20, "Xenonauts 2", "https://cdn.cloudflare.steamstatic.com/steam/apps/538030/capsule_231x87.jpg?t=1689741073");
				GameDTO g21 = new GameDTO(21, "Baldur's Gate 3", "https://cdn.cloudflare.steamstatic.com/steam/apps/1086940/capsule_231x87.jpg?t=1689348322");
				GameDTO g22 = new GameDTO(22, "GrandChase", "https://cdn.cloudflare.steamstatic.com/steam/apps/985810/capsule_231x87.jpg?t=1688538435");
				GameDTO g23 = new GameDTO(23, "It Takes Two", "https://cdn.cloudflare.steamstatic.com/steam/apps/1426210/capsule_231x87.jpg?t=1679951279");
				GameDTO g24 = new GameDTO(24, "Super Jigsaw Puzzle: Generations - Full Pack", "https://cdn.cloudflare.steamstatic.com/steam/bundles/9964/116qikbwbb45orz3/capsule_231x87.jpg?t=1556091136");
				GameDTO g25 = new GameDTO(25, "Ready or Not", "https://cdn.cloudflare.steamstatic.com/steam/apps/1144200/capsule_231x87.jpg?t=1688390724");
				GameDTO g26 = new GameDTO(26, "Sid Meier’s Civilization® VI Anthology", "https://cdn.cloudflare.steamstatic.com/steam/bundles/21432/djvtj4go7ymkt5lv/capsule_231x87.jpg?t=1669073178");
				GameDTO g27 = new GameDTO(27, "Street Fighter 6", "https://cdn.cloudflare.steamstatic.com/steam/apps/1364780/capsule_231x87.jpg?t=1686291121");
				GameDTO g28 = new GameDTO(28, "옥토패스 트래블러 II", "https://cdn.cloudflare.steamstatic.com/steam/apps/1971650/capsule_231x87.jpg?t=1688650936");
				GameDTO g29 = new GameDTO(29, "Dead by Daylight", "https://cdn.cloudflare.steamstatic.com/steam/apps/381210/capsule_231x87.jpg?t=1687878531");
				GameDTO g30 = new GameDTO(30, "ELDEN RING", "https://cdn.cloudflare.steamstatic.com/steam/apps/1245620/capsule_231x87.jpg?t=1683618443");
				GameDTO g31 = new GameDTO(31, "SHMUP All Stars", "https://cdn.cloudflare.steamstatic.com/steam/bundles/18371/a9n0fut2ua4bd3kv/capsule_231x87.jpg?t=1640152686");
				GameDTO g32 = new GameDTO(32, "소울워커", "https://cdn.cloudflare.steamstatic.com/steam/apps/1825750/capsule_231x87_koreana.jpg?t=1681854232");
				GameDTO g33 = new GameDTO(33, "Rebellion Anthology", "https://cdn.cloudflare.steamstatic.com/steam/bundles/646/y6u8ejuci6htztk5/capsule_231x87.jpg?t=1501602620");
				GameDTO g34 = new GameDTO(34, "Human: Fall Flat", "https://cdn.cloudflare.steamstatic.com/steam/apps/477160/capsule_231x87_alt_assets_1.jpg?t=1689672396");
				GameDTO g35 = new GameDTO(35, "Remnant II", "https://cdn.cloudflare.steamstatic.com/steam/apps/1282100/capsule_231x87.jpg?t=1689706767");
				GameDTO g36 = new GameDTO(36, "Ember Knights", "https://cdn.cloudflare.steamstatic.com/steam/apps/1135230/capsule_231x87_alt_assets_6_koreana.jpg?t=1689683633");
				GameDTO g37 = new GameDTO(37, "V Rising", "https://cdn.cloudflare.steamstatic.com/steam/apps/1604030/capsule_231x87.jpg?t=1684394834");
				
				GameDTO ag1 = new GameDTO(1, "PUBG: BATTLEGROUNDS", "https://cdn.cloudflare.steamstatic.com/steam/apps/578080/capsule_231x87.jpg?t=1689138385");
				GameDTO ag2 = new GameDTO(2, "Apex 레전드™", "https://cdn.cloudflare.steamstatic.com/steam/apps/1172470/capsule_231x87.jpg?t=1689459756");
				GameDTO ag3 = new GameDTO(3, "Grand Theft Auto V: 프리미엄 에디션", "https://cdn.cloudflare.steamstatic.com/steam/bundles/5699/qipqf90z2z7h4x3i/capsule_231x87_koreana.jpg?t=1678931390");
				GameDTO ag4 = new GameDTO(4, "데이브 더 다이버", "https://cdn.cloudflare.steamstatic.com/steam/apps/1868140/capsule_231x87.jpg?t=1689558218");
				GameDTO ag5 = new GameDTO(5, "데스티니 가디언즈", "https://cdn.cloudflare.steamstatic.com/steam/apps/1085660/capsule_231x87_koreana.jpg?t=1684966");
				GameDTO ag6 = new GameDTO(6, "Counter-Strike: Global Offensive", "https://cdn.cloudflare.steamstatic.com/steam/apps/730/capsule_231x87.jpg?t=1683566799");
				GameDTO ag7 = new GameDTO(7, "Grand Theft Auto V", "https://cdn.cloudflare.steamstatic.com/steam/apps/271590/capsule_231x87.jpg?t=1678296348");
				GameDTO ag8 = new GameDTO(8, "Limbus Company", "https://cdn.cloudflare.steamstatic.com/steam/apps/1973530/capsule_231x87.jpg?t=1685082822");
				GameDTO ag9 = new GameDTO(9, "BattleBit Remastered", "https://cdn.cloudflare.steamstatic.com/steam/apps/671860/capsule_231x87.jpg?t=1686877598");
				GameDTO ag10 = new GameDTO(10, "Yu-Gi-Oh! Master Duel", "https://cdn.cloudflare.steamstatic.com/steam/apps/1449850/capsule_231x87.jpg?t=1688458743");
				GameDTO ag11 = new GameDTO(11, "War Thunder", "https://cdn.cloudflare.steamstatic.com/steam/apps/236390/capsule_231x87.jpg?t=1689324181");
				GameDTO ag12 = new GameDTO(12, "Undawn", "https://cdn.cloudflare.steamstatic.com/steam/apps/1881700/capsule_231x87.jpg?t=1688028875");
				GameDTO ag13 = new GameDTO(13, "Yet Another Zombie Survivors", "https://cdn.cloudflare.steamstatic.com/steam/apps/2163330/capsule_231x87.jpg?t=1689267965");
				GameDTO ag14 = new GameDTO(14, "Tom Clancy's Rainbow Six® Siege", "https://cdn.cloudflare.steamstatic.com/steam/apps/359550/capsule_231x87.jpg?t=1685724234");
				GameDTO ag15 = new GameDTO(15, "F1® 23", "https://cdn.cloudflare.steamstatic.com/steam/apps/2108330/capsule_231x87.jpg?t=1688987889");
				GameDTO ag16 = new GameDTO(16, "Kairosoft Bundle", "https://cdn.cloudflare.steamstatic.com/steam/bundles/25263/jj8i6f14a5595drk/capsule_231x87.jpg?t=1662033809");
				GameDTO ag17 = new GameDTO(17, "원피스 해적무쌍 4", "https://cdn.cloudflare.steamstatic.com/steam/apps/1089090/capsule_231x87_koreana.jpg?t=1678851805");
				GameDTO ag18 = new GameDTO(18, "Sid Meier’s Civilization® VI", "https://cdn.cloudflare.steamstatic.com/steam/apps/289070/capsule_231x87.jpg?t=1680898825");
				GameDTO ag19 = new GameDTO(19, "데이브 더 다이버 Deluxe Edition", "https://cdn.cloudflare.steamstatic.com/steam/bundles/32958/sv4mk97vuyb8kgft/capsule_231x87.jpg?t=1687943001");
				GameDTO ag20 = new GameDTO(20, "Xenonauts 2", "https://cdn.cloudflare.steamstatic.com/steam/apps/538030/capsule_231x87.jpg?t=1689741073");
				GameDTO ag21 = new GameDTO(21, "Baldur's Gate 3", "https://cdn.cloudflare.steamstatic.com/steam/apps/1086940/capsule_231x87.jpg?t=1689348322");
				GameDTO ag22 = new GameDTO(22, "GrandChase", "https://cdn.cloudflare.steamstatic.com/steam/apps/985810/capsule_231x87.jpg?t=1688538435");
				GameDTO ag23 = new GameDTO(23, "It Takes Two", "https://cdn.cloudflare.steamstatic.com/steam/apps/1426210/capsule_231x87.jpg?t=1679951279");
				GameDTO ag24 = new GameDTO(24, "Super Jigsaw Puzzle: Generations - Full Pack", "https://cdn.cloudflare.steamstatic.com/steam/bundles/9964/116qikbwbb45orz3/capsule_231x87.jpg?t=1556091136");
				GameDTO ag25 = new GameDTO(25, "Ready or Not", "https://cdn.cloudflare.steamstatic.com/steam/apps/1144200/capsule_231x87.jpg?t=1688390724");
				GameDTO ag26 = new GameDTO(26, "Sid Meier’s Civilization® VI Anthology", "https://cdn.cloudflare.steamstatic.com/steam/bundles/21432/djvtj4go7ymkt5lv/capsule_231x87.jpg?t=1669073178");
				GameDTO ag27 = new GameDTO(27, "Street Fighter 6", "https://cdn.cloudflare.steamstatic.com/steam/apps/1364780/capsule_231x87.jpg?t=1686291121");
				GameDTO ag28 = new GameDTO(28, "옥토패스 트래블러 II", "https://cdn.cloudflare.steamstatic.com/steam/apps/1971650/capsule_231x87.jpg?t=1688650936");
				GameDTO ag29 = new GameDTO(29, "Dead by Daylight", "https://cdn.cloudflare.steamstatic.com/steam/apps/381210/capsule_231x87.jpg?t=1687878531");
				GameDTO ag30 = new GameDTO(30, "ELDEN RING", "https://cdn.cloudflare.steamstatic.com/steam/apps/1245620/capsule_231x87.jpg?t=1683618443");
				GameDTO ag31 = new GameDTO(31, "SHMUP All Stars", "https://cdn.cloudflare.steamstatic.com/steam/bundles/18371/a9n0fut2ua4bd3kv/capsule_231x87.jpg?t=1640152686");
				GameDTO ag32 = new GameDTO(32, "소울워커", "https://cdn.cloudflare.steamstatic.com/steam/apps/1825750/capsule_231x87_koreana.jpg?t=1681854232");
				GameDTO ag33 = new GameDTO(33, "Rebellion Anthology", "https://cdn.cloudflare.steamstatic.com/steam/bundles/646/y6u8ejuci6htztk5/capsule_231x87.jpg?t=1501602620");
				GameDTO ag34 = new GameDTO(34, "Human: Fall Flat", "https://cdn.cloudflare.steamstatic.com/steam/apps/477160/capsule_231x87_alt_assets_1.jpg?t=1689672396");
				GameDTO ag35 = new GameDTO(35, "Remnant II", "https://cdn.cloudflare.steamstatic.com/steam/apps/1282100/capsule_231x87.jpg?t=1689706767");
				GameDTO ag36 = new GameDTO(36, "Ember Knights", "https://cdn.cloudflare.steamstatic.com/steam/apps/1135230/capsule_231x87_alt_assets_6_koreana.jpg?t=1689683633");
				GameDTO ag37 = new GameDTO(37, "V Rising", "https://cdn.cloudflare.steamstatic.com/steam/apps/1604030/capsule_231x87.jpg?t=1684394834");
				
				
				GameDTO bg1 = new GameDTO(1, "PUBG: BATTLEGROUNDS", "https://cdn.cloudflare.steamstatic.com/steam/apps/578080/capsule_231x87.jpg?t=1689138385");
				GameDTO bg2 = new GameDTO(2, "Apex 레전드™", "https://cdn.cloudflare.steamstatic.com/steam/apps/1172470/capsule_231x87.jpg?t=1689459756");
				GameDTO bg3 = new GameDTO(3, "Grand Theft Auto V: 프리미엄 에디션", "https://cdn.cloudflare.steamstatic.com/steam/bundles/5699/qipqf90z2z7h4x3i/capsule_231x87_koreana.jpg?t=1678931390");
				GameDTO bg4 = new GameDTO(4, "데이브 더 다이버", "https://cdn.cloudflare.steamstatic.com/steam/apps/1868140/capsule_231x87.jpg?t=1689558218");
				GameDTO bg5 = new GameDTO(5, "데스티니 가디언즈", "https://cdn.cloudflare.steamstatic.com/steam/apps/1085660/capsule_231x87_koreana.jpg?t=1684966");
				GameDTO bg6 = new GameDTO(6, "Counter-Strike: Global Offensive", "https://cdn.cloudflare.steamstatic.com/steam/apps/730/capsule_231x87.jpg?t=1683566799");
				GameDTO bg7 = new GameDTO(7, "Grand Theft Auto V", "https://cdn.cloudflare.steamstatic.com/steam/apps/271590/capsule_231x87.jpg?t=1678296348");
				GameDTO bg8 = new GameDTO(8, "Limbus Company", "https://cdn.cloudflare.steamstatic.com/steam/apps/1973530/capsule_231x87.jpg?t=1685082822");
				GameDTO bg9 = new GameDTO(9, "BattleBit Remastered", "https://cdn.cloudflare.steamstatic.com/steam/apps/671860/capsule_231x87.jpg?t=1686877598");
				GameDTO bg10 = new GameDTO(10, "Yu-Gi-Oh! Master Duel", "https://cdn.cloudflare.steamstatic.com/steam/apps/1449850/capsule_231x87.jpg?t=1688458743");
				GameDTO bg11 = new GameDTO(11, "War Thunder", "https://cdn.cloudflare.steamstatic.com/steam/apps/236390/capsule_231x87.jpg?t=1689324181");
				GameDTO bg12 = new GameDTO(12, "Undawn", "https://cdn.cloudflare.steamstatic.com/steam/apps/1881700/capsule_231x87.jpg?t=1688028875");
				GameDTO bg13 = new GameDTO(13, "Yet Another Zombie Survivors", "https://cdn.cloudflare.steamstatic.com/steam/apps/2163330/capsule_231x87.jpg?t=1689267965");
				GameDTO bg14 = new GameDTO(14, "Tom Clancy's Rainbow Six® Siege", "https://cdn.cloudflare.steamstatic.com/steam/apps/359550/capsule_231x87.jpg?t=1685724234");
				GameDTO bg15 = new GameDTO(15, "F1® 23", "https://cdn.cloudflare.steamstatic.com/steam/apps/2108330/capsule_231x87.jpg?t=1688987889");
				GameDTO bg16 = new GameDTO(16, "Kairosoft Bundle", "https://cdn.cloudflare.steamstatic.com/steam/bundles/25263/jj8i6f14a5595drk/capsule_231x87.jpg?t=1662033809");
				GameDTO bg17 = new GameDTO(17, "원피스 해적무쌍 4", "https://cdn.cloudflare.steamstatic.com/steam/apps/1089090/capsule_231x87_koreana.jpg?t=1678851805");
				GameDTO bg18 = new GameDTO(18, "Sid Meier’s Civilization® VI", "https://cdn.cloudflare.steamstatic.com/steam/apps/289070/capsule_231x87.jpg?t=1680898825");
				GameDTO bg19 = new GameDTO(19, "데이브 더 다이버 Deluxe Edition", "https://cdn.cloudflare.steamstatic.com/steam/bundles/32958/sv4mk97vuyb8kgft/capsule_231x87.jpg?t=1687943001");
				GameDTO bg20 = new GameDTO(20, "Xenonauts 2", "https://cdn.cloudflare.steamstatic.com/steam/apps/538030/capsule_231x87.jpg?t=1689741073");
				GameDTO bg21 = new GameDTO(21, "Baldur's Gate 3", "https://cdn.cloudflare.steamstatic.com/steam/apps/1086940/capsule_231x87.jpg?t=1689348322");
				GameDTO bg22 = new GameDTO(22, "GrandChase", "https://cdn.cloudflare.steamstatic.com/steam/apps/985810/capsule_231x87.jpg?t=1688538435");
				GameDTO bg23 = new GameDTO(23, "It Takes Two", "https://cdn.cloudflare.steamstatic.com/steam/apps/1426210/capsule_231x87.jpg?t=1679951279");
				GameDTO bg24 = new GameDTO(24, "Super Jigsaw Puzzle: Generations - Full Pack", "https://cdn.cloudflare.steamstatic.com/steam/bundles/9964/116qikbwbb45orz3/capsule_231x87.jpg?t=1556091136");
				GameDTO bg25 = new GameDTO(25, "Ready or Not", "https://cdn.cloudflare.steamstatic.com/steam/apps/1144200/capsule_231x87.jpg?t=1688390724");
				GameDTO bg26 = new GameDTO(26, "Sid Meier’s Civilization® VI Anthology", "https://cdn.cloudflare.steamstatic.com/steam/bundles/21432/djvtj4go7ymkt5lv/capsule_231x87.jpg?t=1669073178");
				GameDTO bg27 = new GameDTO(27, "Street Fighter 6", "https://cdn.cloudflare.steamstatic.com/steam/apps/1364780/capsule_231x87.jpg?t=1686291121");
				GameDTO bg28 = new GameDTO(28, "옥토패스 트래블러 II", "https://cdn.cloudflare.steamstatic.com/steam/apps/1971650/capsule_231x87.jpg?t=1688650936");
				GameDTO bg29 = new GameDTO(29, "Dead by Daylight", "https://cdn.cloudflare.steamstatic.com/steam/apps/381210/capsule_231x87.jpg?t=1687878531");
				GameDTO bg30 = new GameDTO(30, "ELDEN RING", "https://cdn.cloudflare.steamstatic.com/steam/apps/1245620/capsule_231x87.jpg?t=1683618443");
				GameDTO bg31 = new GameDTO(31, "SHMUP All Stars", "https://cdn.cloudflare.steamstatic.com/steam/bundles/18371/a9n0fut2ua4bd3kv/capsule_231x87.jpg?t=1640152686");
				GameDTO bg32 = new GameDTO(32, "소울워커", "https://cdn.cloudflare.steamstatic.com/steam/apps/1825750/capsule_231x87_koreana.jpg?t=1681854232");
				GameDTO bg33 = new GameDTO(33, "Rebellion Anthology", "https://cdn.cloudflare.steamstatic.com/steam/bundles/646/y6u8ejuci6htztk5/capsule_231x87.jpg?t=1501602620");
				GameDTO bg34 = new GameDTO(34, "Human: Fall Flat", "https://cdn.cloudflare.steamstatic.com/steam/apps/477160/capsule_231x87_alt_assets_1.jpg?t=1689672396");
				GameDTO bg35 = new GameDTO(35, "Remnant II", "https://cdn.cloudflare.steamstatic.com/steam/apps/1282100/capsule_231x87.jpg?t=1689706767");
				GameDTO bg36 = new GameDTO(36, "Ember Knights", "https://cdn.cloudflare.steamstatic.com/steam/apps/1135230/capsule_231x87_alt_assets_6_koreana.jpg?t=1689683633");
				GameDTO bg37 = new GameDTO(37, "V Rising", "https://cdn.cloudflare.steamstatic.com/steam/apps/1604030/capsule_231x87.jpg?t=1684394834");
				
				List<GameDTO> gList = new ArrayList<>();
				
				gList.add(g1); gList.add(g2); gList.add(g3); gList.add(g4); gList.add(g5); gList.add(g6); gList.add(g7); gList.add(g8); gList.add(g9); gList.add(g10);
				gList.add(g11); gList.add(g12); gList.add(g13); gList.add(g14); gList.add(g15); gList.add(g16); gList.add(g17); gList.add(g18); gList.add(g19); gList.add(g20);
				gList.add(g21); gList.add(g22); gList.add(g23); gList.add(g24); gList.add(g25); gList.add(g26); gList.add(g27); gList.add(g28); gList.add(g29); gList.add(g30);
				gList.add(g31); gList.add(g32); gList.add(g33); gList.add(g34); gList.add(g35); gList.add(g36); gList.add(g37);
				
				gList.add(ag1); gList.add(ag2); gList.add(ag3); gList.add(ag4); gList.add(ag5); gList.add(ag6); gList.add(ag7); gList.add(ag8); gList.add(ag9); gList.add(ag10);
				gList.add(ag11); gList.add(ag12); gList.add(ag13); gList.add(ag14); gList.add(ag15); gList.add(ag16); gList.add(ag17); gList.add(ag18); gList.add(ag19); gList.add(ag20);
				gList.add(ag21); gList.add(ag22); gList.add(ag23); gList.add(ag24); gList.add(ag25); gList.add(ag26); gList.add(ag27); gList.add(ag28); gList.add(ag29); gList.add(ag30);
				gList.add(ag31); gList.add(ag32); gList.add(ag33); gList.add(ag34); gList.add(ag35); gList.add(ag36); gList.add(ag37);
				
				gList.add(bg1); gList.add(bg2); gList.add(bg3); gList.add(bg4); gList.add(bg5); gList.add(bg6); gList.add(bg7); gList.add(bg8); gList.add(bg9); gList.add(bg10);
				gList.add(bg11); gList.add(bg12); gList.add(bg13); gList.add(bg14); gList.add(bg15); gList.add(bg16); gList.add(bg17); gList.add(bg18); gList.add(bg19); gList.add(bg20);
				gList.add(bg21); gList.add(bg22); gList.add(bg23); gList.add(bg24); gList.add(bg25); gList.add(bg26); gList.add(bg27); gList.add(bg28); gList.add(bg29); gList.add(bg30);
				gList.add(bg31); gList.add(bg32); gList.add(bg33); gList.add(bg34); gList.add(bg35); gList.add(bg36); gList.add(bg37);
				
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
