package com.demo.buddy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@AutoConfigureMockMvc
@EnableWebMvc
@SpringBootTest(properties = "spring.main.lazy-initialization=true",classes = OperationController.class)
@WithMockUser
//@Transactional
@Slf4j
public class CompteControllerTest {



}
