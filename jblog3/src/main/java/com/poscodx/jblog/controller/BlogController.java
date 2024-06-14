package com.poscodx.jblog.controller;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.CategoryService;
import com.poscodx.jblog.service.FileUploadService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {

	@Autowired
	private BlogService blogService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private FileUploadService fileUploadService;

	// @RequestMapping({"", "/{categoryNo}", "/{categoryNo}/{postNo}"})
	@RequestMapping(value = { "", "/{categoryNo:^(?!admin$).*$}", "/{categoryNo:^(?!admin$).*$}/{postNo}" })
	public String index(@PathVariable("id") String id,
			@PathVariable(name = "categoryNo", required = false) Long categoryNo,
			@PathVariable(name = "postNo", required = false) Long postNo) {

		// optional 변수에 default값 설정하기
		return "blog/main";
	}

	// @Auth
	@RequestMapping(value = "/admin/basic", method = RequestMethod.GET)
	public String adminBasic(@PathVariable("id") String id, Model model) {
		BlogVo vo = blogService.getBlog(id);
		model.addAttribute("vo", vo);
		return "blog/admin-basic";
	}

	@RequestMapping(value = "/admin/updateBasic", method = RequestMethod.POST)
	public String updateBasic(BlogVo vo, MultipartFile file) {
		String logo = fileUploadService.restore(file);
		if (logo != null) {
			vo.setLogo(logo);
		}

		blogService.updateBasic(vo);
		servletContext.setAttribute("blogvo", vo);

		return "redirect:/" + vo.getId() + "/admin/basic";
	}

	// @Auth
	@RequestMapping("/admin/category")
	public String adminCategory(@PathVariable("id") String id, Model model) {
//		if(!id.equals(authUser.getId())) { // 인증된 사람 아니면 - admin Interceptor
//			
//		}
		List<CategoryVo> list = categoryService.getList(id);
		model.addAttribute("list", list);
		return "blog/admin-category";
	}

	// @Auth
	@RequestMapping("/admin/write")
	public String adminWrite(@PathVariable("id") String id, Model model) {
		return "blog/admin-write";
	}

}
