package com.demo.buddy.controller;

import com.demo.buddy.controller.exception.UserException;
import com.demo.buddy.entity.User;
import com.demo.buddy.repository.UserRepository;
import com.demo.buddy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

/**
 * methods related to the controller of ami
 * @author Mougni
 *
 */
@Controller
public class AmisController {

    @Autowired
    IAmisService amisService;

    @Autowired
    IUserService userService;


    /**
     * this method get the view to add a friend
     * @return the view.
     */
    @GetMapping("/addFriend")
    public String getAddFriend(Model model){
        model.addAttribute("user", new User());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/addFriend");
        return "addFriend";


    }



    /**
     * this method add a friend from the route /addFriend
     * @param user represents the user that has to be added as a friend.
     * @return the view home.
     */
    @PostMapping("/addFriend")
    public String addFriend(@ModelAttribute User user) throws UserException {


        User userFind = userService.findUserByEmail(user.getEmail());
        amisService.addFriend(userFind, userService.findUser());

        return "home";

    }

    /**
     * this method get the view that shows all the friends by the route /getFriend
     * @param model is used has a parameter to pass to the view all the friends infos.
     * @return the view getFriend.
     */
    @GetMapping("/getFriend")
    public String getFriends(Model model){

        List<Optional<User>> amiInfos = amisService.findAmisByIdUser(userService.findIdUserLogged());

        model.addAttribute("ami", amiInfos);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/getFriend");
        return "getFriend";

    }

}
