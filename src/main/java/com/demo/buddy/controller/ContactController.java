package com.demo.buddy.controller;

import com.demo.buddy.controller.exception.UserException;
import com.demo.buddy.entity.User;
import com.demo.buddy.service.ContactService;
import com.demo.buddy.service.IContactService;
import com.demo.buddy.service.IUserService;
import com.demo.buddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class ContactController {

    @Autowired
    IUserService userService;

    @Autowired
    IContactService contactService;


    @GetMapping("/getContacts")
    public String getContacts(Model model){

        List<Optional<User>> contacts = contactService.getContact(userService.findIdUserLogged());
        System.out.println("CONATCTS : "+ contacts);

        model.addAttribute("ami", contacts);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/getContact");
        return "getContact";


    }

    @PostMapping("/addPersonToContact")
    public String addFriend(@ModelAttribute User user, Model model, RedirectAttributes redirect) throws UserException {


        User userFind = userService.findUserByEmail(user.getEmail());
        //Optional<Amis> findAmi =  amisService.findAmiById(userFind.getUserid());
        contactService.addContact(userFind, userService.findUser(), redirect);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/home");
        return "home";

    }

    @GetMapping("/addPersonToContact")
    public String getAddFriend(Model model){
        model.addAttribute("user", new User());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/addContact");
        return "addContact";


    }
//    @PostMapping("/addPersonToContact")
//    public String addFriend(@ModelAttribute User user, Model model, RedirectAttributes redirect){
//
//
//        User userFind = userService.findUserByEmail(user.getEmail());
//        //Optional<Amis> findAmi =  amisService.findAmiById(userFind.getUserid());
//        contactService.addFriend(userFind, userService.findIdUserLogged());
//
//        return "home";
//    }

}
