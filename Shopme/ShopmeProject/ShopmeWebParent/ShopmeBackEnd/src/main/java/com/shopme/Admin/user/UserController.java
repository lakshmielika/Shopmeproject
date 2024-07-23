package com.shopme.Admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
@Controller
public class UserController {
   @Autowired
	private UserService Service;
   @GetMapping("/users")
   public String listAll(Model model) {
	   List<User>listUsers=Service.listAll();
	   model.addAttribute("ListUsers", listUsers);
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
   public String saveUser(User users ,RedirectAttributes redirect) {
	   System.out.println(users);
	   Service.save(users);
	   redirect.addFlashAttribute("message","The user have been saved sucessfully" );
	   return "redirect:/users";
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
