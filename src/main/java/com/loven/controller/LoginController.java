package com.loven.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loven.entity.OAuthToken;
import com.loven.service.BoardServiceImpl;
import com.loven.service.MemberService;
import com.loven.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loven.entity.Company;
import com.loven.entity.User;
import com.loven.service.BoardService;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Controller
public class LoginController {
	@Autowired
	BoardService service;

	@Autowired
	OAuthService oAuthService;

	@Autowired
	BoardServiceImpl boardService;

	@Autowired
	MemberService memberService;

	/*
	 * // 회원가입 폼
	 * 
	 * @GetMapping("/signup") public String joinForm() {
	 * 
	 * return "signup"; }
	 */

//회원가입
	@PostMapping("/signup")
	public String join(HttpSession session, User vo) {
		service.joinInsert(vo);
		session.setAttribute("vo", vo);
		User mvo = service.loginCheck(vo);
		if (mvo != null) {
			session.setAttribute("mvo", mvo);
		}

		return "redirect:/login.do";
	}

//아이디 중복체크
	@PostMapping("/idCheck")
	@ResponseBody
	public int idCheck(@RequestParam("id") String id) {

		int cnt = service.idCheck(id);
		return cnt;
	}

	// 로그인
	@PostMapping("/login.do")
	public String loginCheck(String check,User vo, HttpSession session) {
		
		User mvo = service.loginCheck(vo);
		if (mvo != null) {
			if(vo.getLogin_type().equals("C")) {
			
				Company cvo =service.companyCheck(vo);
			
				session.setAttribute("cvo", cvo);
			}else if(vo.getLogin_type().equals("a")) { // 관리자인 경우
				mvo = service.loginAdmin(vo); // 관리자용 로그인
				session.setAttribute("mvo", mvo);
				
			}else {
			session.setAttribute("mvo", mvo);	
		}
			return "redirect:/main";
		}else {
		return "login";
			}	
		}
	@RequestMapping("/logout.do")
	public String loginOut(HttpSession session) {
		session.invalidate(); //무효화
		return "redirect:/main";
	}

	// 로그인 폼
	@GetMapping("/login.do")
	public String loginForm() {

		return "login";
	}

	@RequestMapping("main")
	public String main() {
		
		return "main";
	}
	// 마이페이지 이동
		@GetMapping("/myPage.do")
		public String myPage() {

			return "myPage";
		}
		@PostMapping
		public String memberUpdate(User vo, HttpSession session){
			
			service.memberUpdate(vo);
			
			session.invalidate();
			
			return "redirect:main";
	}

	
	  @GetMapping("login") public String login(Model model) {
	  model.addAttribute("data", "hello"); return "login"; }
	

	//---------------------------------여기서부터 카카오 login Api--------------------------------------------
//	@GetMapping("/auth/kakao/callback")
//	@ResponseBody
//	public void kakaoCallback(String code) { // Data를 리턴해주는 컨트롤러 함수
//
//		// POST방식으로 key=value 데이터를 요청(카카오쪽으로)
//		//Restrofit2
//		//OkHttp
//		//RestTemplate
//
//		RestTemplate rt = new RestTemplate();
//
//		//HttpHeader 오브젝트 생성
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//		//HttpBody 오브젝트 생성
//		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//		params.add("grant_type", "authorization_code");
//		params.add("client_id", "2e5066b51786f5af82c6c4222bed1517");
//		params.add("redirect_uri", "http://localhost:8088/auth/kakao/callback");
//		params.add("code", code);
//
//		//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
//		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
//				new HttpEntity<>(params, headers);
//
//		//Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
//		ResponseEntity<String> response = rt.exchange(
//				"https://kauth.kakao.com/oauth/token",
//				HttpMethod.POST,
//				kakaoTokenRequest,
//				String.class
//		);
//		// Gson, Json Simple, ObjectMapper
//		ObjectMapper objectMapper = new ObjectMapper();
//		OAuthToken oauthToken = null;
//
//		try {
//			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
////            System.out.println("카카오 엑세스 토큰 : " + oauthToken.getAccess_token());
//
//			String ac_token = oauthToken.getAccess_token();
//			System.out.println("카카오 엑세스 토큰 : " + oauthToken.getAccess_token());
//			//return response.getBody();
//
//			HashMap<String, String> info = oAuthService.getUserInfo(ac_token);
//
//			String id = info.get("id");
//			String name = info.get("nickname");
//			String email = info.get("email");
//
//			//
//
//			if (info == null){
//				System.out.println(id);
//				System.out.println(name);
//				System.out.println(email);
//
//			}
//
//			User vo = new User();
//			vo.setId(id);
//			if(email!=null){
//				vo.setEmail(email);
//			}else{
//				vo.setEmail(id);
//			}
//			vo.setNick(name);
//			vo.setName(name);
//			memberService.kakaoLogin(vo);
//
//			} catch (JsonProcessingException e) {
//			throw new RuntimeException(e);
//		}
// >>>  밑부터 성민씨 코드 이상하게 수정해논상태  >>>>


	@RequestMapping(value = "/klogin")
	public String login(@RequestParam(value = "code", required = false) String code, HttpSession session, HttpServletResponse response) throws Exception{

		System.out.println("loginController!!");

		System.out.println("####code#####" + code);

		String access_Token = oAuthService.getAccessToken(code);
		System.out.println("###access_Token#### : " + access_Token);

		Cookie token = new Cookie("authorize-access-token", access_Token);
		token.setPath("/");
		response.addCookie(token);

		//*
		HashMap<String, String> userInfo = oAuthService.getUserInfo(access_Token);
		System.out.println("###user_info### : " + userInfo);

		String mem_id = userInfo.get("id");
		String mem_pw = userInfo.get("id");
		String mem_name = userInfo.get("nickname");
		String mem_email = userInfo.get("email");

		int check = boardService.idCheck(mem_id);

		if ( check != 1 ){
			System.out.println(mem_id);
			System.out.println(mem_name);
			System.out.println(mem_email);

			User vo = new User();
			vo.setId(mem_id);
			vo.setPw(mem_id);
			vo.setName(mem_name);
			vo.setEmail(mem_email);
			memberService.kakaoLogin(vo);

			return "redirect:/";
		} else {

			User vo = new User();
			vo.setId(mem_id);
			vo.setPw(mem_pw);
			vo.setLogin_type("k");
			User mvo = boardService.loginCheck(vo);

			System.out.println(mvo);
			session.setAttribute("type","k");
			session.setAttribute("mvo", mvo);
			return "redirect:/";

	}
}}
