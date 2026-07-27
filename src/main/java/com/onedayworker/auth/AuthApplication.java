package com.onedayworker.auth;

import com.onedayworker.auth.entity.Role;
import com.onedayworker.auth.repository.IdentityRepository;
import com.onedayworker.auth.repository.RoleRepository;
import com.onedayworker.auth.service.IdentityRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AuthApplication  {


    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
//
//    @Bean
//    public CommandLineRunner runDatabaseTask(IdentityRoleService identityRoleService, RoleRepository roleRepository, IdentityRepository identityRepository) {
//        return args -> {
//            System.out.println("Executing database task on startup...");
//            identityRoleService.assignRole(   identityRepository.findByEmail("customer1@gmail.com").get().getId()  ,"CUSTOMER");
//
//        };
//    }

}
