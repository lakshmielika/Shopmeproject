package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncodetest {
	@Test
	public void testEncodePassword() {
		BCryptPasswordEncoder pe=new  BCryptPasswordEncoder();
		String rawpassword="lak20224";
		String encoderpassword=pe.encode(rawpassword);
		System.out.println(encoderpassword);
		 Boolean m=pe.matches(rawpassword, encoderpassword);
		 assertThat(m).isTrue();
		
	}

}
