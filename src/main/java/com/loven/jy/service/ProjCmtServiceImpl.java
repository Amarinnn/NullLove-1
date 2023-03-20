package com.loven.jy.service;

import com.loven.jy.entity.Proj_cmt;
import com.loven.jy.mapper.ProjCmtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjCmtServiceImpl implements ProjCmtService  {
	
	@Autowired
	ProjCmtMapper mapper;

	@Override
	public void projCmtInsert(Proj_cmt cmt){
		mapper.ProjCmtInsert(cmt);
	}

	@Override
	public void projCmtUpdate(Proj_cmt cmt) {
		mapper.ProjCmtUpdate(cmt);
	}

	@Override
	public void projCmtDelete(int cmt_seq) {
		mapper.ProjCmtDelete(cmt_seq);
	}
	@Override
	public List<Proj_cmt> projCmtList(int seq) {
		return mapper.ProjCmtList(seq);
	}



}
