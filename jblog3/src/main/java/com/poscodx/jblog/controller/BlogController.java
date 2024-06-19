package com.poscodx.jblog.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.jblog.security.Auth;
import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.CategoryService;
import com.poscodx.jblog.service.FileUploadService;
import com.poscodx.jblog.service.PostService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;
import com.poscodx.jblog.vo.UserVo;

@Controller
@RequestMapping("/{id:(?!assets|images).*}")
public class BlogController {

	@Autowired
	private BlogService blogService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private PostService postService;
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private FileUploadService fileUploadService;
	
	//@RequestMapping(value = { "", "/{categoryNo:^(?!admin$).*$}", "/{categoryNo:^(?!admin$).*$}/{postNo}" })
	@RequestMapping({"", "/{pathNo1}", "/{pathNo1}/{pathNo2}"})
	public String index(@PathVariable("id") String id,
			@PathVariable("pathNo1") Optional<Long> pathNo1,
			@PathVariable("pathNo2") Optional<Long> pathNo2,
			Model model) {
		
		// 카테고리 리스트
		List<CategoryVo> categoryList = categoryService.getList(id);
		
		// 게시물, 카테고리의 게시물 리스트
		PostVo postVo = null;
		List<PostVo> postList = null;
		
		// categoryNo, postNo 세팅
		Long categoryNo = 0L;
		Long postNo = 0L;
		
		if(pathNo1.isPresent()) {
			categoryNo = pathNo1.get();
		} else {
			categoryNo = categoryService.getDefault(id);
		}
		
		if(pathNo2.isPresent()) {
			postNo = pathNo2.get();
		} else {
			postNo = postService.getDefault(categoryNo);
		}
		
		// post가 하나도 없을 경우 - postVo, postList가 null
		
		// post가 존재할 경우
		if(postService.getCount(categoryNo)>0) {
			postList = postService.getList(categoryNo);
			postVo = postService.getPost(postNo);
		}
		
		BlogVo blogVo = blogService.getBlog(id);
		
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("selectedCategoryNo", categoryNo);
		model.addAttribute("postList", postList);
		model.addAttribute("postVo", postVo);
		model.addAttribute("blogVo", blogVo);
		return "blog/main";
	}

	@Auth
	@RequestMapping(value = "/admin/basic", method = RequestMethod.GET)
	public String adminBasic(@PathVariable("id") String id, HttpSession session, Model model) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if((authUser==null) || !(authUser.getId().equals(id))) {
			return "redirect:/";
		}
		
		BlogVo vo = blogService.getBlog(id);
		model.addAttribute("vo", vo);
		return "blog/admin-basic";
	}
	
	@Auth
	@RequestMapping(value = "/admin/updateBasic", method = RequestMethod.POST)
	public String updateBasic(BlogVo vo, MultipartFile file, HttpSession session) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if((authUser==null) || !(authUser.getId().equals(vo.getId()))) {
			return "redirect:/";
		}
		
		String logo = fileUploadService.restore(file);
		if (logo != null) {
			vo.setLogo(logo);
		}

		blogService.updateBasic(vo);
		servletContext.setAttribute("blogvo", vo);
		
		return "redirect:/" + vo.getId() + "/admin/basic";
	}

	@Auth
	@RequestMapping("/admin/category")
	public String adminCategory(@PathVariable("id") String id, HttpSession session, Model model) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if((authUser==null) || !(authUser.getId().equals(id))) {
			return "redirect:/";
		}
		
		List<CategoryVo> list = categoryService.getList(id);
		model.addAttribute("list", list);
		model.addAttribute("id", id);
		return "blog/admin-category";
	}
	
	@Auth
	@RequestMapping("/admin/category/delete/{no}")
	public String adminCategoryDelete(@PathVariable("id") String id, @PathVariable Long no, HttpSession session, Model model) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if((authUser==null) || !(authUser.getId().equals(id))) {
			return "redirect:/";
		}
		
		categoryService.deleteByNo(id, no);
		return "redirect:/" + id + "/admin/category";
	}
	
	@Auth
	@RequestMapping(value="/admin/category/add", method=RequestMethod.POST)
	public String adminCategoryAdd(@PathVariable("id") String id, CategoryVo vo, HttpSession session, Model model) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if((authUser==null) || !(authUser.getId().equals(id))) {
			return "redirect:/";
		}
		
		categoryService.addCategory(vo);
		return "redirect:/" + id + "/admin/category";
	}

	@Auth
	@RequestMapping("/admin/write")
	public String adminWrite(@PathVariable("id") String id, HttpSession session, Model model) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if((authUser==null) || !(authUser.getId().equals(id))) {
			return "redirect:/";
		}
		
		List<CategoryVo> categoryList = categoryService.getList(id);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("id", id);
		return "blog/admin-write";
	}
	
	@Auth
	@RequestMapping(value="/admin/write", method=RequestMethod.POST)
	public String adminWrite(@PathVariable("id") String id, PostVo vo, HttpSession session, Model model) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if((authUser==null) || !(authUser.getId().equals(id))) {
			return "redirect:/";
		}
		
		postService.addPost(vo);
		return "redirect:/" + id + "/" + vo.getCategoryNo() + "/" + vo.getNo();
	}

}
