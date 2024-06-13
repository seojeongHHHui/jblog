package com.poscodx.jblog.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
	
	@RequestMapping({"", "/{categoryNo}", "/{categoryNo}/{postNo}"})
	public String index(
			@PathVariable("id") String id,
			@PathVariable("categoryNo") Optional<Long> categoryNo,
			@PathVariable("postNo") Optional<Long> postNo) {
		
		// optional 변수에 default값 설정하기
		return "blog/main";
	}
	
	//@Auth
	@RequestMapping("/admin/basic")
	public String adminBasic(@PathVariable("id") String id) {
		return "blog/admin-basic";
	}
	
	//@Auth
	@RequestMapping("/admin/category")
	public String adminCategory(@PathVariable("id") String id) {
//		if(!id.equals(authUser.getId())) { // 인증된 사람 아니면 - admin Interceptor
//			
//		}
		return "blog/admin-category";
	}
	
	//@Auth
	@RequestMapping("/admin/write")
	public String adminWrite(@PathVariable("id") String id) {
		return "blog/admin-write";
	}
	
}
