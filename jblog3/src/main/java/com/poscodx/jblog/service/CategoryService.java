package com.poscodx.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poscodx.jblog.repository.CategoryRepository;
import com.poscodx.jblog.repository.PostRepository;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private PostRepository postRepository;
	
	public List<CategoryVo> getList(String id) {
		return categoryRepository.findAll(id);
	}
	
	public Long getDefault(String id) {
		return categoryRepository.getDefault(id);
	}
	
	@Transactional
	public void deleteByNo(String id, Long no) {
		Long defaultCategoryNo = categoryRepository.getDefault(id);
		postRepository.updateCategoryToDefault(no, defaultCategoryNo);
		categoryRepository.deleteByNo(no);
	}

	public void addCategory(CategoryVo vo) {
		categoryRepository.addCategory(vo);
		
	}
}
