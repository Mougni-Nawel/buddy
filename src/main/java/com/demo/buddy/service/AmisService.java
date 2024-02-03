package com.demo.buddy.service;

import com.demo.buddy.controller.exception.UserException;
import com.demo.buddy.entity.Amis;
import com.demo.buddy.entity.User;
import com.demo.buddy.repository.AmisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * methods related to ami that are implemented from amiservice interface.
 * @author Mougni
 *
 */
@Service
public class AmisService implements IAmisService{

    @Autowired
    AmisRepository amisRepository;


    @Autowired
    IUserService userService;

    /**
     * this method get a list of friend of the user connected.
     * @return amisInfos that represents a list of all the friends of the user connected.
     */
    public List<Optional<User>> findAmi(){

        int userId = userService.getUserId();
        if(userId == 0){
            return null;
        }


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

    /**
     * this method check if the user put in parameter is a friend of the user logged.
     * @param amiId represents the user that we want to check if is already or not a friend.
     * @return a boolean.
     * @throws UserException if the user is alread a friend of the user logged.
     */
    public Boolean checkIsAmis(int amiId, int userId) throws UserException {
        try {

            Amis contact = amisRepository.findContactByIdPerson(amiId, userId);

            if (contact != null) {
                return false;
            }else{
                return true;
            }
        } catch (Exception e){
            throw new UserException("Cet utilisateur est déja un ami");
        }
    }

    /**
     * this method add a user to be a friend.
     * @param userFind represents the user that we want to add as a friend.
     * @param userId represents the id of the user logged.
     * @return a boolean.
     * @throws UserException if the user that we want to add as a friend is the same user logged and if the
     * user is already a friend of the user logged.
     */
    public boolean addFriend(User userFind, User userId) throws UserException {

        System.out.println("LOL : "+ userFind.getUserid());
        //System.out.println("AMIS GITHUB : " + (userFind != null) + "  | " + (!Objects.equals(userFind.getUserid(), userId.getUserid())) + " || " + this.checkIsAmis(userFind.getUserid()), userId.getUserid());
        if(userFind != null && !Objects.equals(userFind.getUserid(), userId.getUserid()) && this.checkIsAmis(userFind.getUserid(), userId.getUserid())){

            Amis newAmi = new Amis();
            newAmi.setIdUser(userId.getUserid());
            newAmi.setIdAmis(userFind.getUserid());
            amisRepository.save(newAmi);

            Amis newAmi2 = new Amis();
            newAmi2.setIdAmis(userId.getUserid());
            newAmi2.setIdUser(userFind.getUserid());
            amisRepository.save(newAmi2);


            return true;
        }else{
            if(Objects.equals(userFind.getUserid(), userId.getUserid())){
                throw new UserException("Vous ne pouvez pas vous ajouter en ami");
            }
            assert userFind != null;
            System.out.println("Friend : "+ this.checkIsAmis(userFind.getUserid(), userId.getUserid()));

            throw new UserException("Cet utilisateur est déja un ami");
        }
        //return false;
    }

    /**
     * this method get all the friends from a user id that is put in parameter.
     * @param userId represents the id of the user.
     * @return amisInfos is of type Amis.
     */
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
