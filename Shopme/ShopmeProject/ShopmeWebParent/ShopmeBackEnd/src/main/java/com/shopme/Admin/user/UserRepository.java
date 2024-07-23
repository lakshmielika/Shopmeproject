package com.shopme.Admin.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.shopme.common.entity.User;
//@Repository
public interface UserRepository extends CrudRepository<User,Integer>{
	@Query("select u from User u where u.email=:email")
	public User getUserByEmail(@Param("email")String email);
	public long countById(Integer id);
       @Query("update User u set u.enabled = ?2  where u.id = ?1" )
	@Modifying
	public void updateEanbledStatus(Integer id,boolean enabled);


}
