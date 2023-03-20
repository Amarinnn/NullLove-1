package com.loven.jy.mapper;

import com.loven.jy.entity.Project;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ProjBoardMapper {
	public List<Project> getLists();
	public void projBoardInsert(Project vo);
	public void projFileInsert(Project vo);
	public Project projBoardView(int seq);
	public void cntPlus(int seq);
	public void projBoardUpdate(Project vo);
	public void projBoardDelete(int seq);

	public List<Project> searchTitle(HashMap<String, Object> map);
	public List<Project> searchContent(HashMap<String, Object> map);
	public int projlikeSelect(int seq);
	public int projlikeWho(HashMap<String,Object> map);
	public void projlikeUp(HashMap<String,Object> map);
	public void projlikeDel(HashMap<String,Object> map);
	public void projUpfile(Project vo);
	public void projUpnotice(int seq);
	public void projDelnotice(int seq);

	public int total();
}
