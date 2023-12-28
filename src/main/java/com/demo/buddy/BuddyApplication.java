package com.demo.buddy;

import com.demo.buddy.entity.Amis;
import com.demo.buddy.entity.Operation;
import com.demo.buddy.entity.User;
import com.demo.buddy.repository.OperationRepository;
import com.demo.buddy.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;



@SpringBootApplication()
public class BuddyApplication {


	public static void main(String[] args) {
		SpringApplication.run(BuddyApplication.class, args);
	}


}
