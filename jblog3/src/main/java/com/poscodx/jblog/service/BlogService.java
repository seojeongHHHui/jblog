package com.poscodx.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.BlogRepository;
import com.poscodx.jblog.vo.BlogVo;

@Service
public class BlogService {
	
	@Autowired
	private BlogRepository blogRepository;

	public List<BlogVo> getList() {
		return blogRepository.findAll();
	}

	public BlogVo getBlog(String id) {
		return blogRepository.findBlog(id);
	}

	public void updateBasic(BlogVo vo) {
		blogRepository.updateBasic(vo);
	}
	
	
}
