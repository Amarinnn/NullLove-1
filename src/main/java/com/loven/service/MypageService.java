package com.loven.service;

import com.loven.entity.BlindVO;
import com.loven.entity.Comment;
import com.loven.entity.PostVO;
import com.loven.entity.User;
import com.loven.jy.entity.Boast;
import com.loven.jy.entity.Boast_cmt;
import com.loven.jy.entity.Proj_cmt;
import com.loven.jy.entity.Project;

import java.util.List;

public interface MypageService {

	public void userDelete(String id);

	public List<BlindVO> postList(String id);
	/* public List<BlindVO> cmtList(String id); */

	public void update_vo(User vo);

	public void mypageDelete(String seq);

	public List<Comment> cmtList(String id);

	public void mypagecmtDelete(String cmt_seq);

	public List<Project> projList(String id);

	public List<Boast> boastList(String id);

	public void mypageProjDelete(int seq);

	public void mypageBoastDelete(int seq);

	public List<Boast_cmt> boastcmtList(String id);

	public List<Proj_cmt> projcmtList(String id);

	public void mypageprojcmtDelete(int seq);

	public void mypageboastcmtDelete(int seq);
}
