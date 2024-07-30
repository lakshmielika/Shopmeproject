package com.shopme.Admin.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.Admin.FileUploadUtil;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
@Controller
public class UserController {
   @Autowired
	private UserService Service;
   @GetMapping("/users")
   public String listFirstPage(Model model) {
	   return listByPage(1,model,"firstname","asc",null);
   }
   @GetMapping("/users/page/{pageNum}")
   public String listByPage(@PathVariable(name="pageNum") int pageNum, Model model,@Param("sortField") String sortField,
		   @Param("sortDir") String sortDir, @Param("keyword") String keyword ) {
	   System.out.println("sortField " + sortField);
	   System.out.println("sortOrder " + sortDir);
	   //System.out.println("Keyword: " + Keyword); 
	   
	
      Page<User>page=Service.listBypage(pageNum,sortField,sortDir,keyword);
      List<User> listUsers=page.getContent();
      long currentPage=pageNum;
      long startCount = (pageNum-1) * UserService.User_Per_Page + 1;
      long endCount = startCount + UserService.User_Per_Page - 1 ;
      if(endCount>page.getTotalElements()) {
    	  endCount=page.getTotalElements();
      }
      String reverseSortDir=sortDir.equals("asc") ? "desc" : "asc";
      model.addAttribute("currentPage", currentPage);
      model.addAttribute("totalPages", page.getTotalPages());
      model.addAttribute("startCount", startCount);
      model.addAttribute("endCount", endCount);
      model.addAttribute("totalItems", page.getTotalElements());
      model.addAttribute("ListUsers", listUsers);
      model.addAttribute("sortField", sortField);
      model.addAttribute("sortDir", sortDir);
      model.addAttribute("reverseSortDir", reverseSortDir);
      model.addAttribute("keyword", keyword);
	  return "users";
   }
   @GetMapping("/users/new")
   public String newUser(Model model) {
	    List<Role> listroles=Service.listRoles();
	   User user= new User();
	   user.setEnabled(true);
	   model.addAttribute("User", user);
	   model.addAttribute("Listroles", listroles);
	   model.addAttribute("pageTitle", "Create New User");
	   
	   return "user-form";
   }
   @PostMapping("/users/save")
   public String saveUser(User users ,RedirectAttributes redirect, @RequestParam("image") MultipartFile multipartFile) throws IOException {
	   if(!multipartFile.isEmpty()) {
		   String fileName= StringUtils.cleanPath(multipartFile.getOriginalFilename());
		   users.setPhotos(fileName);
		   User savedUser= Service.save(users);
		   String uploadDir ="user-photos/" +savedUser.getId();
		   FileUploadUtil.cleanDir(uploadDir);
		   FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		   }
	   else {
		   if(users.getPhotos().isEmpty()) users.setPhotos(null);
		   Service.save(users);
	   }
	   
	   //Service.save(users);
	   redirect.addFlashAttribute("message","The user have been saved sucessfully" );
	   return getRedirectURLtoAffectedUser(users);
	   }
private String getRedirectURLtoAffectedUser(User users) {
	String firstPartOfEmail=users.getEmail().split("@")[0];
	   return "redirect:/users/page/1?sortField=id&sortDir=asc&keyword="+firstPartOfEmail;
}
   @GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable(name="id") Integer id,
			Model model,RedirectAttributes redirectAttributes) {
		try {
			User user=Service.get(id);
			List<Role> listroles=Service.listRoles();
			model.addAttribute("User",user);
			model.addAttribute("pageTitle", "Edit  User(ID:"+id+")");
			model.addAttribute("Listroles", listroles);
			
			return "user-form";
		}catch(UserNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message",ex.getMessage());
		return "redirect:/users";
		}
		
	}
   @GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable(name="id") Integer id,
			RedirectAttributes redirectAttributes) {
	   try {
			Service.delete(id);
			redirectAttributes.addFlashAttribute("message","The User id "+ id +" has been deleted suucessfully");
			
		}catch(UserNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message",ex.getMessage());
			
		}
	   return "redirect:/users";
	   }
   @GetMapping("/users/{id}/enabled/{status}")
   public String updateUserEnabledStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled,RedirectAttributes redirectAttributes) {
	   Service.updateUserEnabledStatus(id, enabled);
	   String status= enabled ? "enabled" : "disabled";
	   String message=" The User Id " +id+" has been " + status;
	   redirectAttributes.addFlashAttribute("message",message);
	   return "redirect:/users";
	   
   }
}
