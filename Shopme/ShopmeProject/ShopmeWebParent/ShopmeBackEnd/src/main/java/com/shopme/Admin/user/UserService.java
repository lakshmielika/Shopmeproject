package com.shopme.Admin.user;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
	public static final  int User_Per_Page = 4;
	@Autowired
  private UserRepository userrepo;
	@Autowired
	private RoleRepository rolerepo;
	@Autowired
	private PasswordEncoder pe;
	public List<User> listAll(){
		return (List<User>) userrepo.findAll(Sort.by("firstname").ascending());
		}
	public Page<User> listBypage(int pageNum,String sortField,String sortDir,String keyword){
		Sort sort= Sort.by(sortField);
		sort=sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable=PageRequest.of(pageNum-1,  User_Per_Page,sort);
		if(keyword != null) {
			return userrepo.findAll(keyword, pageable);
		}
		
		return userrepo.findAll(pageable);
		
		
	}
	public List<Role>listRoles(){
		return (List<Role>) rolerepo.findAll();
		
	}
	public User save(User users) {
		boolean isUpdatingUser=(users.getId()!=null);
		if(isUpdatingUser) {
			User existingUser=userrepo.findById(users.getId()).get();
			if(users.getPassword().isEmpty()) {
				users.setPassword(existingUser.getPassword());
			}else {
				encoderpassword(users);
			}
		}else {
			encoderpassword(users);
		}
		
		 return userrepo.save(users);
		
		}
	public void encoderpassword(User user) {
		String encoderpassword=pe.encode(user.getPassword());
		user.setPassword(encoderpassword);
		}
	public boolean isEmailUnique( Integer id,String email) {
		User userByEmail=userrepo.getUserByEmail(email);
		if(userByEmail==null) return true;
		  boolean isCreatingNew=(id==null);
		  if(isCreatingNew) {
			  if(userByEmail!=null) return false;
		  }else {
			  if(userByEmail.getId()!=id) {
				  return false;
			  }
		  }
		  return true;
		
	}
	 public  User get(Integer id) throws UserNotFoundException {
		  try {
			  return userrepo.findById(id).get();
		  }catch(NoSuchElementException ex) {
			  throw new UserNotFoundException("could not find any user with ID"+id);
		  }
	  }
	 public void delete(Integer id) throws UserNotFoundException {
		      Long countById=userrepo.countById(id);
		      if(countById == null || countById == 0) {
		    	  throw new UserNotFoundException("could not find any user with ID"+id);
		    	  }
		      userrepo.deleteById(id);
	 }
	 
	public void updateUserEnabledStatus(Integer id, boolean enabled) {
		userrepo.updateEanbledStatus(id, enabled);
		
	}
	
	
}
