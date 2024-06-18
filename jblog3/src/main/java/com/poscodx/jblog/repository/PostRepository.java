package com.poscodx.jblog.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.PostVo;

@Repository
public class PostRepository {
	private SqlSession sqlSession;
	
	public PostRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public Long getDefault(Long categoryNo) {
		return sqlSession.selectOne("post.getDefault", categoryNo);
	}

	public int getCount(Long categoryNo) {
		return sqlSession.selectOne("post.getCount", categoryNo);
	}

	public List<PostVo> findAll(Long categoryNo) {
		return sqlSession.selectList("post.findAll", categoryNo);
	}

	public PostVo getPost(Long postNo) {
		return sqlSession.selectOne("post.getPost", postNo);
	}

	public int addPost(PostVo vo) {
		return sqlSession.insert("post.addPost", vo);
	}

	public int updateCategoryToDefault(Long no, Long defaultCategoryNo) {
		return sqlSession.update("post.updateCategoryToDefault", Map.of("no", no, "defaultCategoryNo", defaultCategoryNo));
	}
	

}
