package com.loven.jy.service;

import com.loven.jy.entity.Project;
import com.loven.jy.mapper.ProjBoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ProjBoardServiceImpl implements ProjBoardService{
	@Autowired
	ProjBoardMapper mapper;

	@Override
	public List<Project> getLists() {
		List<Project> list=mapper.getLists();
		return list;
	}

	@Override
	public void projBoardInsert(Project vo) {
		mapper.projBoardInsert(vo);
	}

	@Override
	public Project projBoardView(int seq) {
		Project vo = mapper.projBoardView(seq);
		return vo;
	}

	@Override
	public void cntPlus(int seq) {
		mapper.cntPlus(seq);
	}

	@Override
	public void projBoardUpdate(Project vo) {
		mapper.projBoardUpdate(vo);
	}

	@Override
	public void projBoardDelete(int seq) {
		mapper.projBoardDelete(seq);
	}

	@Override
	public void projFileInsert(Project vo) {
		mapper.projFileInsert(vo);
	}

	@Override
	public List<Project> searchTitle(HashMap<String, Object> map) {

		return mapper.searchTitle(map);
	}
	// 게시글 검색(내용)
	@Override
	public List<Project> searchContent(HashMap<String, Object> map){

		return mapper.searchContent(map);
	}

	@Override
	public int projlikeSelect(int seq) {
		return mapper.projlikeSelect(seq);
	}

	@Override
	public int projlikeWho(HashMap<String, Object> map) {
		return mapper.projlikeWho(map);
	}

	@Override
	public void projlikeUp(HashMap<String, Object> map) {
		mapper.projlikeUp(map);
	}
	@Override
	public void projlikeDel(HashMap<String, Object> map) {
		mapper.projlikeDel(map);
	}

	@Override
	public void projUpfile(Project vo) {
		mapper.projUpfile(vo);}

	@Override
	public void projUpnotice(int seq) {
		mapper.projUpnotice(seq);
	}

	@Override
	public void projDelnotice(int seq) {
		mapper.projDelnotice(seq);
	}

	@Override
	public int total() {
		return mapper.total();
	}

}	
