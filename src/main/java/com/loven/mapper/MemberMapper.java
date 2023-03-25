package com.loven.mapper;

import com.loven.entity.BlindVO;
import com.loven.entity.Comment;
import com.loven.entity.Criteria;
import com.loven.entity.PostVO;
import com.loven.entity.User;

import java.util.List;

import com.loven.jy.entity.Boast;
import com.loven.jy.entity.Boast_cmt;
import com.loven.jy.entity.Proj_cmt;
import com.loven.jy.entity.Project;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
	public void memberJoin(User user);

	public void kakaoLogin(User vo);

	public List<User> userList(Criteria cri); // 유저 리스트 불러오기

	public int countUser(Criteria cri);

	public List<User> searchList1(String id); // 유저 검색(아이디)

	public List<User> searchList2(String email); // 이메일

	public List<User> searchList3(String company_name); // 기업명

	public void userDelete(String id); // 유저 삭제

	public void disableFk();

	public void enableFk();

	public void userPostDelete(String id); // 유저 게시글 삭제

	public void userCommentDelete(String id); // 유저 댓글 삭제

	List<BlindVO> postList(String id); // 마이페이지 게시글 리스트

	public void update_vo(User vo); // 마이페이지 유저수정

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
