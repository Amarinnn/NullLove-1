package com.loven.service;

import com.loven.entity.BlindVO;
import com.loven.entity.Comment;
import com.loven.entity.PostVO;
import com.loven.entity.User;
import com.loven.jy.entity.Boast;
import com.loven.jy.entity.Boast_cmt;
import com.loven.jy.entity.Proj_cmt;
import com.loven.jy.entity.Project;
import com.loven.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MypageServiceImpl implements MypageService{

    @Autowired
    MemberMapper mapper;

    @Override
    public void userDelete(String id) {
        mapper.disableFk();
        mapper.userDelete(id);
        mapper.enableFk();
    }

    @Override
    public List<BlindVO> postList(String id) {
        List<BlindVO> list = mapper.postList(id);
        return list;
    }

	@Override
	public void update_vo(User vo) {
		mapper.update_vo(vo);
		
	}
	@Override
	public void mypageDelete(String seq) {
		mapper.mypageDelete(seq);
		
	}

	@Override
	public List<Comment> cmtList(String id) {
		List<Comment> list = mapper.cmtList(id);
        return list;
	}

	@Override
	public void mypagecmtDelete(String cmt_seq) {
		mapper.mypagecmtDelete(cmt_seq);
		
	}

	@Override
	public List<Project> projList(String id) {
		List<Project> list = mapper.projList(id);
		return list;
	}

	@Override
	public List<Boast> boastList(String id) {
		List<Boast> list = mapper.boastList(id);
		return list;
	}

	@Override
	public void mypageProjDelete(int seq) {
			mapper.mypageProjDelete(seq);
	}

	@Override
	public void mypageBoastDelete(int seq) {
		mapper.mypageBoastDelete(seq);
	}

	@Override
	public List<Boast_cmt> boastcmtList(String id) {
		List<Boast_cmt> list = mapper.boastcmtList(id);
		return list;
	}

	@Override
	public List<Proj_cmt> projcmtList(String id) {
		List<Proj_cmt> list = mapper.projcmtList(id);
		return list;
	}

	@Override
	public void mypageprojcmtDelete(int seq) {
		mapper.mypageprojcmtDelete(seq);
	}

	@Override
	public void mypageboastcmtDelete(int seq) {
		mapper.mypageboastcmtDelete(seq);
	}


}
