package com.demo.buddy.service;

import com.demo.buddy.controller.exception.UserException;
import com.demo.buddy.entity.Compte;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements UserDetailsService, IUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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
            newUser.setRole("USER");
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

    public User updateUser(User updateUserInfo){
        User actualUserInfo = findUser();
        //User actualUserInfo = findUser();

        // creer d'abord un compte lors de la création de compte
        //Optional<Compte> compte = compteRepository.findById(1);
        //System.out.println(compte.get());
        if((actualUserInfo.getCompteBancaire().getCoordonneesBancaire() == null) ||(actualUserInfo.getCompteBancaire().getCoordonneesBancaire().isEmpty() || (actualUserInfo.getCompteBancaire().getCoordonneesBancaire() != updateUserInfo.getCompteBancaire().getCoordonneesBancaire()))){
            actualUserInfo.getCompteBancaire().setCoordonneesBancaire(updateUserInfo.getCompteBancaire().getCoordonneesBancaire());
        }

        //compteRepository.save(compte.get());


        if (actualUserInfo.getEmail() != updateUserInfo.getEmail()) {
            actualUserInfo.setEmail(updateUserInfo.getEmail());
        }


        return userRepository.save(actualUserInfo);
    }

    public void updateAccount(User account){
        User user = findUser();

        double montant = user.getCompteBancaire().getMontant() + account.getCompteBancaire().getMontant();
        user.getCompteBancaire().setMontant(montant);

        userRepository.save(user);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);

        if(user == null){
            System.out.println("PAS TROUVE");
            throw new UsernameNotFoundException("pas trouve");
        }
        System.out.println("TROUVE");
        return user;

    }

    public int findIdUserLogged() {
        SecurityContext context= SecurityContextHolder.getContext();
        Authentication authentication=context.getAuthentication();
        int userId = ((User) authentication.getPrincipal()).getUserid();

        return userId;
    }

    public User findUser() {

        // todo return exception
        return userRepository.findById(findIdUserLogged());

    }

    public Optional<User> findById(int userid){
        return Optional.ofNullable(userRepository.findById(userid));
    }

    public User findUserByEmail(String email) {

        return userRepository.findByEmail(email);

    }

}
