package com.yuhan.loco.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuhan.loco.game.ConsoleDB;
import com.yuhan.loco.game.GameDTO;
import com.yuhan.loco.game.PcDB;
import com.yuhan.loco.game.service.GameService;
import com.yuhan.loco.game.service.GameThService;
import com.yuhan.loco.prefer.PreferDB;
import com.yuhan.loco.prefer.PreferService;
import com.yuhan.loco.search.GameSearchDB;
import com.yuhan.loco.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
//2023.09.28 -> view 방식 + 페이징으로 작업
	//2안 선택 이유: 데이터가 방대해서 dto에 최대한 넣지 않아야 함, 정렬이 간소화된만큼 db 차원에서 해결 가능
		//정렬 로직:
		//인기순: 크롤링에서 인기순으로 받아오므로 num순임(steam_num or epic_num), steam, epic 필터를 걸면 각각 보여주고, 안 걸면 그냥 디비에 있는 순서대로 보여주면 됨
		//할인순: saleper 기준으로 정렬하면 됨, steam, epic 필터를 걸면 각각 보여주고, 안 걸면 steam_saleper 기준으로 보여주면 됨
		//추천순: 교수님과 면담할때 확정이 안된 부분(아예 이걸 빼는 걸 얘기하심, 하지만 회원가입의 의미가 없어져서 들어가야 함)
			//1안) 그냥 선호 선택한 장르만 죄다 select해다가 보여주기 -> 로직 간편, but 추천의 의미가 잘 안보일 수 있음, Page<db> 사용 가능(즉, 데이터가 많아도 과부하 x)
			//2안) 선호 선택한 장르 3-4개씩 뽑아서 보여주기 -> 로직 다소 불편, but 추천의 의미가 잘 나타남, dto를 얘만 써야 함(하지만 3-4개씩이면 규모가 작아서 상관없음. 선호 장르로 다 선택해도 60개이므로)

		//카테고리 로직:
		//장르 뷰 연결해서 repository에 select title from (view 이름) where genre = ? 수행하는 함수 만들기 -> title을 list(a)로 받아옴
		//게임 정보 들어있는 뷰(현재 game_steam_epic) repository에 아래와 같은 함수를 만듦(필요에 따라 변경 가능, 예시)
			//Page<PcDB> findByTitleIn(List<String> titles, Pageable pageable);
		//이러면 서비스에서 findByTitleIn의 인수로 list(a)와 pageable을 넣어주면 됨.

		//사이트 필터링 로직:
		//steam이면 SITEAVAILABILITY가 steam only, both인 항목 select
		//epic이면 epic only, both인 항목 select

@Controller
public class GameController {
	//전역
	String page = "";

	//
	private final GameService gameService;
	private final GameThService thService;
	private final UserService userService;
	private final PreferService preferService;

	public GameController(GameService gameService, GameThService thService,
			UserService userService, PreferService preferService) {
        this.gameService = gameService;
        this.thService = thService;
        this.userService = userService;
        this.preferService = preferService;
    }


	//연결
	//pc
	@GetMapping("/pc")
	public String pc(Model model,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "site", defaultValue = "0") String site,
			@RequestParam(value = "category", defaultValue = "0") String category,
			@RequestParam(value = "orderby", defaultValue = "popular") String order,
			HttpServletRequest req) {

		/*선호 장르 가져오기(로그인 시)*/

		//선호 정보 문자열
		String like = null;

		//로그인 했는지 확인
		HttpSession session = req.getSession(false);

		//로그인했다면 userdb 객체 받아오기
		if(session != null) { //로그인?
			if(session.getAttribute("user") != null) { //로그인!
				String id = (String)session.getAttribute("user");
				String rId = userService.findUserId(id);

				PreferDB UserP = preferService.findUser(rId); //유저 선호 정보 db
				like = UserP.getLove();
			}
		}

		/*선호 장르 가져오기(로그인 시) end*/


		/*크롤링 완료 시간 가져오기*/
		//크롤링 시간 가져오기(string)
		String cTime = gameService.getCrawlingTime();

		/*크롤링 완료 시간 가져오기 end*/


		/*Page<db> 객체 & 페이지 정보 가공*/

		//페이징용 page, pageable은 0부터 시작함 -> -1로 가공해주기, html에서도 가공 필요
		page -= 1;

		//페이징 리스트 받아오기
		Page<PcDB> paging = gameService.getPcPageByFilter(page, site, category, order, like);
		System.out.println(paging.getTotalElements());

		//페이지네이션 정보 가공: 시작 페이지 번호, 현재 페이지 번호, 끝 페이지 번호
		int currentPage = page + 1; //현재 페이지 번호
		int calcEnd = (int)(Math.ceil(currentPage / 10.0) * 10); //현재 페이지를 10으로 나눈 후 올리고 10을 곱하면 끝번호가 나옴(ex. 3-> 0.3 - 1 - 10, 23-> 2.3 - 3 - 30)
		int startPage = calcEnd - 9; //시작 페이지 번호

		//끝 페이지 번호 확정
		int endPage = Math.min(calcEnd, paging.getTotalPages());

		/*Page<db> 객체 & 페이지 정보 가공 end*/


		/*model에 넣기*/
		//페이징 객체 model에 넣기
		model.addAttribute("gamePage", paging);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", paging.getTotalPages());
		model.addAttribute("gameService", thService);

		//정렬
		model.addAttribute("siteF", site);
		model.addAttribute("categoryF", category);
		model.addAttribute("orderbyF", order);

		//크롤링 시간
		model.addAttribute("cTime", cTime);

		return "/game/pc";
		}

	//console
	@GetMapping("/console")
	public String console(Model model,
				@RequestParam(value = "page", defaultValue = "1") int page,
				@RequestParam(value = "site", defaultValue = "0") String site,
				@RequestParam(value = "category", defaultValue = "0") String category,
				@RequestParam(value = "orderby", defaultValue = "popular") String order,
				HttpServletRequest req) {

			/*선호 장르 가져오기(로그인 시)*/

		//선호 정보 문자열
		String like = null;

		//로그인 했는지 확인
		HttpSession session = req.getSession(false);

		//로그인했다면 userdb 객체 받아오기
		if(session != null) { //로그인?
			if(session.getAttribute("user") != null) { //로그인!
				String id = (String)session.getAttribute("user");
				String rId = userService.findUserId(id);

				PreferDB UserP = preferService.findUser(rId); //유저 선호 정보 db
				like = UserP.getLove();
			}
		}

			/*선호 장르 가져오기(로그인 시) end*/


			/*크롤링 완료 시간 가져오기*/
		//크롤링 시간 가져오기(string)
		String cTime = gameService.getCrawlingTime();

			/*크롤링 완료 시간 가져오기 end*/


			/*Page<db> 객체 & 페이지 정보 가공*/

		//페이징용 page, pageable은 0부터 시작함 -> -1로 가공해주기, html에서도 가공 필요
		page -= 1;

		//페이징 리스트 받아오기
		Page<ConsoleDB> paging = gameService.getConsolePageByFilter(page, site, category, order, like);
		System.out.println(paging.getTotalElements());

		//페이지네이션 정보 가공: 시작 페이지 번호, 현재 페이지 번호, 끝 페이지 번호
		int currentPage = page + 1; //현재 페이지 번호
		int calcEnd = (int)(Math.ceil(currentPage / 10.0) * 10); //현재 페이지를 10으로 나눈 후 올리고 10을 곱하면 끝번호가 나옴(ex. 3-> 0.3 - 1 - 10, 23-> 2.3 - 3 - 30)
		int startPage = calcEnd - 9; //시작 페이지 번호

		//끝 페이지 번호 확정
		int endPage = Math.min(calcEnd, paging.getTotalPages());

			/*Page<db> 객체 & 페이지 정보 가공 end*/

			/*model에 넣기*/
		//페이징 객체 model에 넣기
		model.addAttribute("gamePage", paging);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", paging.getTotalPages());
		model.addAttribute("gameService", thService);

		//정렬
		model.addAttribute("siteF", site);
		model.addAttribute("categoryF", category);
		model.addAttribute("orderbyF", order);

		//크롤링 시간
		model.addAttribute("cTime", cTime);


		return "/game/console";
	}


    // PC Detail
	@GetMapping("/pcDetail/{key}")
	public String detail_pc(@PathVariable String key, Model model) {
	    PcDB pcGameDetail = gameService.getPcByKey(key); // key에 해당하는 PC 게임 정보 가져오기
	    GameDTO pcGameDTO = gameService.createToDTO(key, pcGameDetail);

	    //List<PcDB> pcDB = gameService.getAllData();
	    model.addAttribute("pcGameDTO", pcGameDTO);
	    model.addAttribute("pcGameDetail", pcGameDetail);
	    model.addAttribute("gameService", thService);
	    return "/game/PcDetail";
	}

    // Console Detail
    @GetMapping("/consoleDetail/{num}")
    public String detail_console(@PathVariable int num, Model model) {
        ConsoleDB csGameDetail = gameService.getCsByNum(num);

        model.addAttribute("csGameDetail", csGameDetail);
        model.addAttribute("gameService", thService);
        return "/game/ConsoleDetail";
	}

    @GetMapping("/search")
    public String search(Model model,
    		@RequestParam("search") String searchKeyword,
			@RequestParam(value = "page", defaultValue = "1") int page) {

		//크롤링 시간 가져오기(string)
		String cTime = gameService.getCrawlingTime();

		//페이징용 page, pageable은 0부터 시작함 -> -1로 가공해주기, html에서도 가공 필요
		page -= 1;
		String Search = ".*" + searchKeyword + ".*";
		//페이징 리스트 받아오기
		Page<GameSearchDB> paging = gameService.getSearchPageByFilter(Search, page);
		System.out.println(paging.getTotalElements());

		//페이지네이션 정보 가공: 시작 페이지 번호, 현재 페이지 번호, 끝 페이지 번호
		int currentPage = page + 1; //현재 페이지 번호
		int calcEnd = (int)(Math.ceil(currentPage / 10.0) * 10); //현재 페이지를 10으로 나눈 후 올리고 10을 곱하면 끝번호가 나옴(ex. 3-> 0.3 - 1 - 10, 23-> 2.3 - 3 - 30)
		int startPage = calcEnd - 9; //시작 페이지 번호

		//끝 페이지 번호 확정
		int endPage = Math.min(calcEnd, paging.getTotalPages());

		//페이징 객체 model에 넣기
		model.addAttribute("gamePage", paging);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", paging.getTotalPages());
		model.addAttribute("gameService", thService);
		model.addAttribute("searchKeyword", searchKeyword);

		//크롤링 시간
		model.addAttribute("cTime", cTime);


		return "/game/search";
    }

}
