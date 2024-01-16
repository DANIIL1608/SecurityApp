package ru.kata.spring.boot_security.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.RoleService;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {
	public static RoleService roleService;
	public SpringBootSecurityDemoApplication(RoleService roleService) {
		this.roleService = roleService;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
		//Данное действие совершенно, чтобы вручную не забивать в таблицу роли. Я понимаю, что в реальном проекте
		//будет spring.jpa.properties.hibernate.hbm2ddl.auto=update и приложение не будет каждый раз стирать свою базу
		//данных и по-новой заносить туда роли
		roleService.save(new Role("ROLE_USER"));
		roleService.save(new Role("ROLE_ADMIN"));
	}
}
