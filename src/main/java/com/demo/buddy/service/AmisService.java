package com.demo.buddy.service;

import com.demo.buddy.entity.Amis;
import com.demo.buddy.entity.Contact;
import com.demo.buddy.entity.User;
import com.demo.buddy.repository.AmisRepository;
import com.demo.buddy.repository.ContactRepository;
import com.demo.buddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AmisService implements IAmisService{

    @Autowired
    AmisRepository amisRepository;

    @Autowired
    IContactService contactService;

    @Autowired
    IUserService userService;

    public List<Optional<User>> findAmi(){

        SecurityContext context= SecurityContextHolder.getContext();
        Authentication authentication=context.getAuthentication();
        int userId = ((User) authentication.getPrincipal()).getUserid();

        List<Amis> amis = amisRepository.findAmiByIdUser(userId);

        List<Optional<User>> amisInfos = new ArrayList<>();

        for(Amis user : amis){
            Optional<User> i = userService.findById(user.getIdAmis());
            if(i.isPresent()){
                amisInfos.add(i);
            }
        }

        return amisInfos;

    }

    public Optional<Amis> findAmiById(int id){

        return amisRepository.findById(id);

    }

    public boolean addFriend(User userFind, User userId){
        System.out.println("Ami is contact : "+ contactService.checkIsContact(userId.getUserid()));
        if(userFind != null && !Objects.equals(userFind.getUserid(), userId.getUserid()) && contactService.checkIsContact(userId.getUserid())){

            Amis newAmi = new Amis();
            newAmi.setIdUser(userId.getUserid());
            newAmi.setIdAmis(userFind.getUserid());
            amisRepository.save(newAmi);
            return true;
        }
        return false;
    }

    public List<Optional<User>> findAmisByIdUser(int userId) {
        List<Amis> ami = amisRepository.findAmiByIdUser(userId);
        List<Optional<User>> amiInfos = new ArrayList<>();

        for(Amis user : ami){
            Optional<User> i = userService.findById(user.getIdAmis());
            if(i.isPresent()){
                amiInfos.add(i);
            }
        }
        return amiInfos;
    }

}
