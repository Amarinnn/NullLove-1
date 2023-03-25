package com.loven.jy.controller;

import com.loven.entity.User;
import com.loven.jy.entity.Project;
import com.loven.jy.entity.Proj_cmt;
import com.loven.jy.service.ProjBoardService;
import com.loven.jy.service.ProjCmtService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Controller
public class ProjBoardCtrl {
	
	@Autowired
	ProjBoardService service;
	@Autowired
	ProjCmtService cmtService;
	
	@RequestMapping("/projBoardList")
	public String list(Model model) {
		List<Project> list=service.getLists();
		model.addAttribute("list",list);
		int total = service.total();
		model.addAttribute("total",total);
		return "jy/projBoardList"; //templates / board / list.html
	}
	// 글쓰기 폼
	@RequestMapping("/projBoardForm")
	public String projBoardForm() {
		return "jy/projBoardWrite";
	}
	
	//글쓰기
	@ResponseBody
	@RequestMapping(value = "/projBoardWrite", method = RequestMethod.POST)
	public String fileUpload(
			@RequestParam("article_file") List<MultipartFile> multipartFile
			, HttpServletRequest request, Project vo) {
		
		String strResult = "{ \"result\":\"FAIL\" }";
		String contextRoot = new HttpServletRequestWrapper(request).getRealPath("/");
		String projectPath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\pdf\\";
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
							vo.setFile(savedFileName);
						}
						
					} catch (Exception e) {
						//파일삭제
						FileUtils.deleteQuietly(targetFile);	//저장된 현재 파일 삭제
						e.printStackTrace();
						break;
					}
				}
				service.projBoardInsert(vo);
//				service.projFileInsert(vo);
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
	@RequestMapping("/projBoardView")
	public String projBoardView(@RequestParam("seq") int seq, Model model, HttpServletRequest request) {
		Project vo = service.projBoardView(seq);
		List<Proj_cmt> cmt = cmtService.projCmtList(seq);
		model.addAttribute("vo",vo);
		model.addAttribute("cmt",cmt);
		service.cntPlus(seq);
		int vol= service.projlikeSelect(seq);
		model.addAttribute("vol",vol);
//		//like 조회
		HttpSession session = request.getSession();
		User mvo = (User) session.getAttribute("mvo");
		String id = mvo.getId();
		System.out.println(id);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("seq",seq);
		map.put("id",id);
		int color = service.projlikeWho(map);
		System.out.println(color);
		model.addAttribute("color",color);
		return "jy/projBoardView";
	}
	// 게시판 수정 폼
	@RequestMapping("/projBoardUpdateForm")
	public String projBoardUpdateForm(Model model,@RequestParam("seq") int seq) {
		System.out.println(seq);
		Project vo = service.projBoardView(seq);
		model.addAttribute("vo", vo);
		return "jy/projBoardUpdate";
	}
	
	//게시판수정
	@ResponseBody
	@RequestMapping(value = "/projBoardUpdate", method = RequestMethod.POST)
	public String projBoardUpdate(
			@RequestParam(value="article_file", required = false) List<MultipartFile> multipartFile
			, HttpServletRequest request, Project vo) {

		String strResult = "{ \"result\":\"FAIL\" }";
		String contextRoot = new HttpServletRequestWrapper(request).getRealPath("/");
		String projectPath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\pdf\\";
		String fileRoot;
		int cnt=1;
		try {
			// 파일이 있을때 탄다.
			if (multipartFile==null){
				service.projBoardUpdate(vo);
				strResult = "{ \"result\":\"OK\" }";
			}
			else if(multipartFile.size() > 0 && !multipartFile.get(0).getOriginalFilename().equals("")) {

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
							vo.setFile(savedFileName);
						}

					} catch (Exception e) {
						//파일삭제
						FileUtils.deleteQuietly(targetFile);	//저장된 현재 파일 삭제
						e.printStackTrace();
						break;
					}
				}
				service.projBoardUpdate(vo);
				service.projUpfile(vo);
				strResult = "{ \"result\":\"OK\" }";
			}
			// 파일 아무것도 첨부 안했을때 탄다.(게시판일때, 업로드 없이 글을 등록하는경우)

		}catch(Exception e){
			e.printStackTrace();
		}
		return strResult;

	}
	
	// 게시판 삭제
	@RequestMapping("/projBoardDelete")
	public String projBoardDelete(@RequestParam("seq") int seq) {
		service.projBoardDelete(seq);
		return "redirect:/projBoardList";
	}
	//게시판 검색
	@GetMapping("/projSearch")
	public String searchList(@RequestParam("search") String search, @RequestParam("option") String option, Model model){
		if(search==null) {
			search="";
		}
		if(option==null) {
			option = "1";
		}
		List<Project> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("search", search);
		if(search == "") {
				list = service.getLists();
				model.addAttribute("list", list);
				int total = service.total();
				model.addAttribute("total", total);
				return "redirect:/projBoardList";
			}else {
				if(option.equals("1")) {
					list = service.searchTitle(map);
					int total = list.size();
					model.addAttribute("total",total);
				}else {
					list = service.searchContent(map);
					int total = list.size();
					model.addAttribute("total",total);
				}
		}
		model.addAttribute("list", list);

		return "jy/projSearch";

	}
	//게시판 좋아요
	@RequestMapping("/projlikeUp")
	@ResponseBody
	private void projlikeUp(@RequestParam("seq") int seq,@RequestParam("id") String id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("seq",seq);
		map.put("id",id);
		service.projlikeUp(map);
	}
	@RequestMapping("/projlikeDel")
	@ResponseBody
	private void projlikeDel(@RequestParam("seq") int seq,@RequestParam("id") String id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("seq",seq);
		map.put("id",id);
		service.projlikeDel(map);
	}
	// 게시판 공지사항등록
	@RequestMapping("/projUpnotice")
	public String projUpnotice(@RequestParam("seq") int seq) {
		service.projUpnotice(seq);
		return "redirect:/projBoardList";
	}
	// 게시판 공지사항해제
	@RequestMapping("/projDelnotice")
	public String projDelnotice(@RequestParam("seq") int seq) {
		service.projDelnotice(seq);
		return "redirect:/projBoardList";
	}
}
	
