package com.loven.jy.service;

import com.loven.jy.entity.Proj_cmt;

import java.util.List;


public interface ProjCmtService {

	public void projCmtInsert(Proj_cmt cmt) ;

	public void projCmtUpdate(Proj_cmt cmt);

	public void projCmtDelete(int cmt_seq);
	public List<Proj_cmt> projCmtList(int seq);

}
