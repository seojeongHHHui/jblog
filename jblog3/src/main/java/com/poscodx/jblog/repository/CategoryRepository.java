package com.poscodx.jblog.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.UserVo;

@Repository
public class CategoryRepository {
	private SqlSession sqlSession;
	
	public CategoryRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public List<CategoryVo> findAll(String id) {
		return sqlSession.selectList("category.findAll", id);
	}
	
	public int insertDefault(UserVo vo) {
		return sqlSession.insert("category.insertDefault", vo.getId());
	}

	public Long getDefault(String id) {
		return sqlSession.selectOne("category.getDefault", id);
	}

	public int deleteByNo(Long no) {
		return sqlSession.delete("category.deleteByNo", no);
	}
	
	public int addCategory(CategoryVo vo) {
		return sqlSession.insert("category.addCategory", vo);
	}

}
