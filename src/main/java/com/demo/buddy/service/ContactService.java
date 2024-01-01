package com.demo.buddy.service;

import com.demo.buddy.controller.exception.UserException;
import com.demo.buddy.entity.Amis;
import com.demo.buddy.entity.Contact;
import com.demo.buddy.entity.User;
import com.demo.buddy.repository.ContactRepository;
import com.demo.buddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Service
public class ContactService implements IContactService{

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    UserService userService;

    public List<Optional<User>> getContact(int userId) {
        List<Contact> contacts = contactRepository.findContactByIdUser(userId);

        List<Optional<User>> contactInfos = new ArrayList<>();

        for(Contact user : contacts){
            //System.out.println("SERVICE : "+ user.getId()+" et email : "+user.);
            Optional<User> i = userService.findById(user.getAmiID());

            if(i.isPresent()){
                contactInfos.add(i);
            }
        }
        return contactInfos;
    }

    public Boolean checkIsContact(int userId) {
        Contact contact = contactRepository.findContactByIdPerson(userId);
        if(contact != null) {
            System.out.println("VOILA LE CONTACT : "+contact.getUser());


            if (contact.getUser().getUserid() == userService.findIdUserLogged()) {
                System.out.println("VOILA LE CONTACT : " + contact.getUser());
                return false;
            }

            if(contact.getAmiID() == userId){
                return true;
            }
        }
        return contact != null;
    }

    public void addContact(User userFind, User userId) throws UserException {

        if(userFind != null && !Objects.equals(userFind.getUserid(), userId) && !checkIsContact(userFind.getUserid())){

            Contact newAmi = new Contact();
            newAmi.setUser(userId);
            newAmi.setAmiID(userFind.getUserid());

            Contact newContactForAmi = new Contact();
            newContactForAmi.setUser(userFind);
            newContactForAmi.setAmiID(userId.getUserid());

            contactRepository.save(newAmi);
            contactRepository.save(newContactForAmi);
        }else{
            // todo else avant quand user est deha en contact throw an exception
            //todo ajouter un message d'erreur et une exception pour user pas trouve
            //redirectAttributes.addFlashAttribute("errorMessage", "User not found");
            throw new UserException("User not found");
        }
    }

    }


