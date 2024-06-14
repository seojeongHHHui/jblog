package com.poscodx.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.UserVo;

@Repository
public class BlogRepository {
	private SqlSession sqlSession;
	
	public BlogRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public int insertDefault(UserVo vo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", vo.getId());
		map.put("title", vo.getName()+"의 블로그");
		return sqlSession.insert("blog.insertDefault", map);
	}

	public List<BlogVo> findAll() {
		return sqlSession.selectList("blog.findAll");
	}

	public BlogVo findBlog(String id) {
		return sqlSession.selectOne("blog.findBlog", id);
	}

	public int updateBasic(BlogVo vo) {
		return sqlSession.update("blog.updateBasic", vo);
	}
}
