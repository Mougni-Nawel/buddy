package com.demo.buddy.service;

import com.demo.buddy.controller.exception.UserException;
import com.demo.buddy.entity.Compte;
import com.demo.buddy.entity.Role;
import com.demo.buddy.entity.User;
import com.demo.buddy.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * methods related to user that are implemented from user interface.
 * @author Mougni
 *
 */
@Service
@Slf4j
public class UserService implements UserDetailsService, IUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * this method save in db the user that is put in parameter.
     * @param newUser represents the user that has to be saved.
     * @throws UserException if the user already exist.
     */
    public void saveUser(User newUser) throws UserException {
        log.debug("EMAIL : "+newUser.getEmail());
        User existingUser = userRepository.findByEmail(newUser.getEmail());
        if (existingUser == null) {
            List<User> userLists = userRepository.findAll();
            for (User user : userLists) {
                if (user.getFirstname().equals(newUser.getFirstname()) && user.getLastname().equals(newUser.getLastname())) {
                    throw new UserException("This user already exists");
                }
            }

            newUser.setMdp(passwordEncoder.encode(newUser.getMdp()));
            newUser.setRole(Role.USER);
            Compte compte = new Compte();
            compte.setUser(newUser);
            newUser.setCompteBancaire(compte);
            userRepository.save(newUser);
        } else {
            throw new UserException("This user already exists");
        }




    }

    public List<User> getAllUser(){

       return userRepository.findAll();

    }

    /**
     * this method modify the user info.
     * @param updateUserInfo represents the user new infos.
     * @return user udpated.
     */
    public User updateUser(User updateUserInfo){

        User actualUserInfo = findUser();
        if((actualUserInfo.getCompteBancaire().getCoordonneesBancaire() == null) ||(actualUserInfo.getCompteBancaire().getCoordonneesBancaire().isEmpty() || (actualUserInfo.getCompteBancaire().getCoordonneesBancaire() != updateUserInfo.getCompteBancaire().getCoordonneesBancaire()))){
            actualUserInfo.getCompteBancaire().setCoordonneesBancaire(updateUserInfo.getCompteBancaire().getCoordonneesBancaire());
        }

        if (actualUserInfo.getEmail() != updateUserInfo.getEmail()) {
            actualUserInfo.setEmail(updateUserInfo.getEmail());
        }

        if(actualUserInfo.getRole() == Role.USER_GITHUB && (actualUserInfo.getFirstname() == null) || (actualUserInfo.getLastname() == null)) {
            if(updateUserInfo.getFirstname() != null){
                actualUserInfo.setFirstname(updateUserInfo.getFirstname());
            }

            if(updateUserInfo.getLastname() != null){
                actualUserInfo.setLastname(updateUserInfo.getLastname());
            }

        }



        return userRepository.save(actualUserInfo);
    }


    /**
     * this method modify the user account info.
     * @param account represents the user account new infos.
     */
    public void updateAccount(User account){
        User user = findUser();

        double montant = user.getCompteBancaire().getMontant() + account.getCompteBancaire().getMontant();
        user.getCompteBancaire().setMontant(montant);


        userRepository.save(user);
    }

    /**
     * this method get qa user by email that is put in parameter.
     * @param username represents the email of a user.
     * @return user that is of type {@link User}.
     * @throws UsernameNotFoundException if any user is found with this email.
     */
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);

        if(user == null){
            throw new UsernameNotFoundException("Pas trouve");
        }
        return user;

    }

    /**
     * this method get the user id that is logged.
     * @return userId that is of type {@link Integer}.
     */
    public int findIdUserLogged(){
        SecurityContext context= SecurityContextHolder.getContext();
        Object authentication=context.getAuthentication().getPrincipal();
        int userId = 0;
        if (authentication instanceof User) {

            userId = ((User) authentication).getUserid();
        }else if(authentication instanceof DefaultOAuth2User){
            Object username = ((DefaultOAuth2User)  authentication).getAttributes().get("login");
            User userGit = this.findUserGit((String) username);
            userId = userGit.getUserid();
        }
        return userId;
    }

    /**
     * this method get a user.
     * @return user that is of type {@link User}.
     */
    public User findUser() {

        // todo return exception
        return userRepository.findById(findIdUserLogged());

    }

    /**
     * this method get a user from an id.
     * @param userid is the id of the user that we search.
     * @return user.
     */
    public Optional<User> findById(int userid){
        //System.out.println("User login : "+Optional.ofNullable(userRepository.findById(userid)));
        return Optional.ofNullable(userRepository.findById(userid));
    }

    /**
     * this method get a user from an email.
     * @param email is the eamil of the user that we search.
     * @return user.
     */
    public User findUserByEmail(String email) {

        return userRepository.findByEmail(email);

    }

    // test github user connected
    public User findUserGit(String username){

        return userRepository.findByUsername(username);
    }


    public User saveUserGit(String username) {
        User userGithub = this.findUserGit((String) username);
        if (userGithub == null) {

            User newUserGitHub = new User();

            newUserGitHub.setRole(Role.USER_GITHUB);
            newUserGitHub.setUsername(username);
            Compte compte = new Compte();
            compte.setUser(newUserGitHub);
            newUserGitHub.setCompteBancaire(compte);

            userRepository.save(newUserGitHub);

        }

        return userGithub;
    }

    @Override
    public int getUserId(){
        SecurityContext context= SecurityContextHolder.getContext();
        Authentication authentication=context.getAuthentication();
        int userId = 0;
        if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
            DefaultOAuth2User oauth2User = (DefaultOAuth2User) authentication.getPrincipal();

            String username = (String) oauth2User.getAttributes().get("login");
            User userGit = findUserGit(username.toString());

            User user = saveUserGit(username);

            if(user == null){
                return 0;
            }

            userId = user.getUserid();

        }else if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            userId = ((User) authentication.getPrincipal()).getUserid();
            System.out.println("User  : "+user.getEmail());
        }
        return userId;
    }


}
