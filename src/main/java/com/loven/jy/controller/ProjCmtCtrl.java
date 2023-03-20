package com.loven.jy.controller;

import com.loven.jy.entity.Proj_cmt;
import com.loven.jy.service.ProjCmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProjCmtCtrl {
	
	@Autowired
	ProjCmtService service;
	
	
	@RequestMapping("/projCmtInsert") // 댓글작성
    private String ProjCmtInsert(@ModelAttribute Proj_cmt cmt) throws Exception {
		System.out.println(cmt);
        service.projCmtInsert(cmt);

        return "redirect:/projBoardView?seq="+cmt.getSeq();

    }
	
	@RequestMapping("/projCmtUpdate") // 댓글수정
	@ResponseBody
    private void ProjCmtUpdate(@ModelAttribute Proj_cmt cmt) throws Exception{

		service.projCmtUpdate(cmt);

    }
	
	
    @RequestMapping("/projCmtDelete") // 댓글삭제
    private String ProjCmtDelete(@ModelAttribute Proj_cmt cmt) throws Exception{
        service.projCmtDelete(cmt.getCmt_seq());
        
        return "redirect:/projBoardView?seq="+cmt.getSeq();
    }
	
}
