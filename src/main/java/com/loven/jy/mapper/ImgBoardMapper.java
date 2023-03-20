package com.loven.jy.mapper;

import java.util.HashMap;
import java.util.List;

import com.loven.jy.entity.Boast_like;
import org.apache.ibatis.annotations.Mapper;

import com.loven.jy.entity.Boast;

@Mapper
public interface ImgBoardMapper {
	public List<Boast> getLists();
	public void imgBoardInsert(Boast vo);
	public void imgFileInsert(Boast vo);
	public Boast imgBoardView(int seq);
	public void cntPlus(int seq);
	public void imgBoardUpdate(Boast vo);
	public void imgBoardDelete(int seq);

	public List<Boast> searchTitle(HashMap<String, Object> map);
	public List<Boast> searchContent(HashMap<String, Object> map);
	public int imglikeSelect(int seq);
	public int imglikeWho(HashMap<String,Object> map);
	public void imglikeUp(HashMap<String,Object> map);
	public void imglikeDel(HashMap<String,Object> map);
}
