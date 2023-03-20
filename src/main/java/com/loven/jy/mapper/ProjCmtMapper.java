package com.loven.jy.mapper;

import com.loven.jy.entity.Proj_cmt;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjCmtMapper {
	public void ProjCmtInsert(Proj_cmt cmt);

	public void ProjCmtUpdate(Proj_cmt cmt);

	public void ProjCmtDelete(int cmt_seq);
	
	public List<Proj_cmt> ProjCmtList(int seq);

}
