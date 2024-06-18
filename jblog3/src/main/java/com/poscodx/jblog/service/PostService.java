package com.poscodx.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.PostRepository;
import com.poscodx.jblog.vo.PostVo;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;

	public Long getDefault(Long categoryNo) {
		return postRepository.getDefault(categoryNo);
	}

	public int getCount(Long categoryNo) {
		return postRepository.getCount(categoryNo);
	}

	public List<PostVo> getList(Long categoryNo) {
		return postRepository.findAll(categoryNo);
	}

	public PostVo getPost(Long postNo) {
		return postRepository.getPost(postNo);
	}

	public void addPost(PostVo vo) {
		postRepository.addPost(vo);
	}

}
