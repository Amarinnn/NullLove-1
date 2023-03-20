package com.loven.jy.controller;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import com.loven.entity.BlindVO;
import com.loven.entity.Criteria;
import com.loven.entity.PageMaker;
import com.loven.entity.User;
import com.loven.jy.entity.Boast_like;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.loven.jy.entity.Boast;
import com.loven.jy.entity.Boast_cmt;
import com.loven.jy.mapper.ImgBoardMapper;
import com.loven.jy.service.ImgBoardService;
import com.loven.jy.service.ImgCmtService;

@Controller
public class ImgBoardCtrl {
	
	@Autowired
	ImgBoardService service;
	@Autowired
	ImgCmtService cmtService;
	
	@RequestMapping("/imgBoardList")
	public String list(Model model) {
		List<Boast> list=service.getLists();
		model.addAttribute("list",list);
		return "jy/imgBoardList"; //templates / board / list.html
	}
	// 글쓰기 폼
	@RequestMapping("/imgBoardForm")
	public String imgBoardForm() {
		return "jy/imgBoardWrite";
	}
	
	//글쓰기
	@ResponseBody
	@RequestMapping(value = "/imgBoardWrite", method = RequestMethod.POST)
	public String fileUpload(
			@RequestParam("article_file") List<MultipartFile> multipartFile
			, HttpServletRequest request, Boast vo) {
		
		String strResult = "{ \"result\":\"FAIL\" }";
		String contextRoot = new HttpServletRequestWrapper(request).getRealPath("/");
		String projectPath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\uploadImg\\";
		String fileRoot;
		int cnt=1;
		try {
			// 파일이 있을때 탄다.
			if(multipartFile.size() > 0 && !multipartFile.get(0).getOriginalFilename().equals("")) {
				
				for(MultipartFile file:multipartFile) {
//					fileRoot = contextRoot + "resources/upload/";
					fileRoot = projectPath;
					System.out.println(fileRoot);
					
					String originalFileName = file.getOriginalFilename();	//오리지날 파일명
					String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자
					String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명
					
					File targetFile = new File(fileRoot + savedFileName);	
					try {
						InputStream fileStream = file.getInputStream();
						FileUtils.copyInputStreamToFile(fileStream, targetFile); //파일 저장
						if(cnt==1) {
							vo.setFile1(savedFileName);
						}if(cnt==2) {
							vo.setFile2(savedFileName);
						}if(cnt==3) {
							vo.setFile3(savedFileName);
						}if(cnt==4) {
							vo.setFile4(savedFileName);
						}if(cnt==5) {
							vo.setFile5(savedFileName);
						}
						cnt++;
						
					} catch (Exception e) {
						//파일삭제
						FileUtils.deleteQuietly(targetFile);	//저장된 현재 파일 삭제
						e.printStackTrace();
						break;
					}
				}
				service.imgBoardInsert(vo);
				service.imgFileInsert(vo);
				System.out.println(vo);
				strResult = "{ \"result\":\"OK\" }";
			}
			// 파일 아무것도 첨부 안했을때 탄다.(게시판일때, 업로드 없이 글을 등록하는경우)
			else
				strResult = "{ \"result\":\"OK\" }";
		}catch(Exception e){
			e.printStackTrace();
		}
		return strResult;
//		return "reidrect:imgBoardList";
				
	}
	
	//상세view
	@RequestMapping("/imgBoardView")
	public String imgBoardView(@RequestParam("seq") int seq, Model model, HttpServletRequest request) {
		Boast vo = service.imgBoardView(seq);
		List<Boast_cmt> cmt = cmtService.imgCmtList(seq);
		model.addAttribute("vo",vo);
		model.addAttribute("cmt",cmt);
		service.cntPlus(seq);
		int vol= service.imglikeSelect(seq);
		model.addAttribute("vol",vol);
//		//like 조회
		HttpSession session = request.getSession();
		User mvo = (User) session.getAttribute("mvo");
		String id = mvo.getId();
		System.out.println(id);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("seq",seq);
		map.put("id",id);
		int color = service.imglikeWho(map);
		System.out.println(color);
		model.addAttribute("color",color);
		return "jy/imgBoardView";
	}
	// 게시판 수정 폼
	@RequestMapping("/imgBoardUpdateForm")
	public String imgBoardUpdateForm(Model model,@RequestParam("seq") int seq) {
		System.out.println(seq);
		Boast vo = service.imgBoardView(seq);
		model.addAttribute("vo", vo);
		return "jy/imgBoardUpdate";
	}
	
	//게시판 수정
	@PostMapping("/imgBoardUpdate")
	public String imgBoardUpdate(Boast vo) {
		System.out.println(vo);
		service.imgBoardUpdate(vo);
		
		return "redirect:imgBoardList";
	}
	
	// 게시판 삭제
	@RequestMapping("/imgBoardDelete")
	public String imgBoardDelete(@RequestParam("seq") int seq) {
		service.imgBoardDelete(seq);
		return "redirect:/imgBoardList";
	}
	//게시판 검색
	@GetMapping("/imgSearch")
	public String searchList(@RequestParam("search") String search, @RequestParam("option") String option, Model model){
		if(search==null) {
			search="";
		}
		if(option==null) {
			option = "1";
		}
		List<Boast> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("search", search);
		if(search == "") {
			list = service.getLists();
			model.addAttribute("list", list);
			return "redirect:/imgBoardList";
		}else {
			if(option.equals("1")) {
				list = service.searchTitle(map);
			}else {
				list = service.searchContent(map);
			}
		}
		model.addAttribute("list", list);

		return "jy/imgSearch";

	}
	//게시판 좋아요
	@RequestMapping("/imglikeUp")
	@ResponseBody
	private void imglikeUp(@RequestParam("seq") int seq,@RequestParam("id") String id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("seq",seq);
		map.put("id",id);
		service.imglikeUp(map);
	}
	@RequestMapping("/imglikeDel")
	@ResponseBody
	private void imglikeDel(@RequestParam("seq") int seq,@RequestParam("id") String id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("seq",seq);
		map.put("id",id);
		service.imglikeDel(map);
	}
}
	
