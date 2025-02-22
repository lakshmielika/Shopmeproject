package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;


import com.shopme.Admin.user.UserRepository;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest(showSql=false)
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
	@Autowired
	private UserRepository repo;
	@Autowired
	private TestEntityManager entitymanager;
	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin=entitymanager.find(Role.class, 1);
		User userNamHM= new User("nam@codejava.net","nam2020","Nam","Ha minh");
		userNamHM.addRole(roleAdmin);
		User savedUser=repo.save(userNamHM);
		assertThat(savedUser.getId()).isGreaterThan(0);

	}
	@Test
	public void testCreateNewUserWithTwoRoles() {
		User userRavi=new User("ravi@gmail.com","R@123","Ravi","Kumar");
		Role roleEditor=new Role(3);
		Role roleAssistant =new Role(5);
		userRavi.addRole(roleEditor);
		userRavi.addRole(roleAssistant);
		User savedUser=repo.save(userRavi);
		assertThat(savedUser.getId()).isGreaterThan(0);
}
	@Test
	public void testListAllUsers() {
		Iterable<User> Listusers=repo.findAll();
		Listusers.forEach(user->System.out.println(user));
		}
	@Test
	public void testGetUserById() {
		User userNam=repo.findById(1).get();
		System.out.println(userNam);
		assertThat(userNam).isNotNull();
		}
	@Test
	public void testUpdateUserDetails() {
		User userNam=repo.findById(1).get();
		userNam.setEnabled(true);
		userNam.setEmail("nam@gmail.com");
		repo.save(userNam);
	}
	@Test
	public void testUpdateUserRoles() {
		User userRavi=repo.findById(2).get();
		Role roleEditor=new Role(3);
		Role roleSalesperson=new Role(2);
		userRavi.getRole().remove(roleEditor);
		userRavi.addRole(roleSalesperson);
		repo.save(userRavi);
		}
	@Test
	public void testDeleteUser() {
		Integer userId=21;
		repo.deleteById(userId);
		}
	@Test
	public void testGetUserByEmail() {
		String email="ravi@gmail.com";
		User user=repo.getUserByEmail(email);
		assertThat(user).isNotNull();
		}
	@Test
	public void testCountById() {
		Integer id=12;
		 Long countById=repo.countById(id);
		 assertThat(countById).isNotNull().isGreaterThan(0);
	}
	@Test
	public void testDisabledStatus() {
		Integer id=1;
		repo.updateEanbledStatus(id, false);
	}
	
	@Test
	public void testEnabledStatus() {
		Integer id=42;
		repo.updateEanbledStatus(id, true);
	}
	@Test
	public void testListFirstPage() {
		int PageNumber=0;
		int PageSize=4;
		
		Pageable pageable= PageRequest.of(PageNumber, PageSize);
		Page<User> page=repo.findAll(pageable);
		 List<User> listUsers=page.getContent();
		 listUsers.forEach(user->System.out.println(user));
		 assertThat(listUsers.size()).isEqualTo(PageSize);
		 
	}
	@Test
	public void testSearchUser() {
		String Keyword="bruce";
		int PageNumber=0;
		int PageSize=4;
		
		Pageable pageable= PageRequest.of(PageNumber, PageSize);
		Page<User> page=repo.findAll(Keyword,pageable);
		 List<User> listUsers=page.getContent();
		 listUsers.forEach(user->System.out.println(user));
		 assertThat(listUsers.size()).isGreaterThan(0);
		
	}
	
	

}
