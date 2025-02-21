package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.Admin.user.RoleRepository;
import com.shopme.common.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class RoleRepositoryTest {
	@Autowired
	private RoleRepository repo;
	@Test
	public void testCreateFirstRole() {
		Role roleAdmin=new Role("Admin","Manage Everything");
		Role saveRole=repo.save(roleAdmin);
		assertThat(saveRole.getId()).isGreaterThan(0);
		}
	@Test
	public void testCreateRestRole() {
		Role roleSalesperson=new Role("Salesperson","Manage product price,"+"customer,shoping orders,sales reports");
		Role roleEditor = new Role("Editor" ,"categories, bands,"+"products,Articles and Menus");
		Role roleShipper = new Role("Shipper" ,"View products, View Orders,"+"and Update Order Status");
		Role roleAssistant = new Role("Assistant" ,"Manage Questions and Reviwes");
		repo.saveAll(List.of(roleSalesperson, roleEditor, roleShipper, roleAssistant));
		
		
		
	}

}
