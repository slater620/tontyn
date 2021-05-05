/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tontyn.controller;

import com.tontyn.model.AppRole;
import com.tontyn.model.AppUser;
import com.tontyn.model.UserRole;
import com.tontyn.model.Utilisateur;
import com.tontyn.repository.AppUserRepository;
import com.tontyn.repository.UserRoleRepository;
import com.tontyn.service.UtilisateurRepositoryService;
import java.security.Principal;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author tanankem
 */

@Controller
public class LoginControlleur {
    @Autowired
    private UtilisateurRepositoryService utilisateurRepositoryService;
    
    @Autowired
    private AppUserRepository appUserRepository;
    
    @Autowired
    private UserRoleRepository userRoleRepository;
    
    @Autowired
    public JavaMailSender emailSender;
    
    @RequestMapping("/")
    public String accueil(Model model, Principal principal){
        User loginedUser = (User)((Authentication)principal).getPrincipal();
        
        
        System.out.println(loginedUser.getUsername());
        return "tontynapp";
    }
    
    @RequestMapping("/creerCompte")
    public String creerCompte(){
        return "creerCompte";
    }
    
    @RequestMapping("/creerCompte/traitement")
    public ModelAndView enregistrerCompte(Utilisateur utilisateur){
        ModelAndView mv = new ModelAndView();
        String statut;
        
        try {
            //saving the user registration
            String encryptedPassword = encrytePassword(utilisateur.getMotDePasse());
            utilisateur.setMotDePasse(encryptedPassword);
            utilisateurRepositoryService.save(utilisateur);
            
            //création d'un objet AppRole
            AppRole appRole = new AppRole();
            appRole.setRoleId(1L);
            appRole.setRoleName("ROLE_USER");
            
            //création du profil dans AppUser
            AppUser newUser = new AppUser();
            newUser.setUserName(utilisateur.getEmail());
            newUser.setEncrytedPassword(encryptedPassword);
            newUser.setUserId(utilisateur.getIdUtilisateur());
            newUser.setEnabled(true);
            
            appUserRepository.save(newUser);
            
            //création du profil dans UserRole
            UserRole newUserRole = new UserRole();
            newUserRole.setId(utilisateur.getIdUtilisateur());
            newUserRole.setAppUser(newUser);
            newUserRole.setAppRole(appRole);
            
            userRoleRepository.save(newUserRole);
            
            statut = "success";
            
            mv.setViewName("creerCompte");
            mv.addObject("statut", statut);
            
        } catch (Exception ex) {
            statut = "failed";
            ModelMap model = new ModelMap();
            
            model.addAttribute("nom", utilisateur.getNom());
            model.addAttribute("prenom", utilisateur.getPrenom());
            model.addAttribute("email", utilisateur.getEmail());
            
            mv.setViewName("creerCompte");
            mv.addObject("statut", statut);
            mv.addObject("utilisateur", model);
        }
        
        return mv;
    }
    
    @RequestMapping(value = "/connexion", method = RequestMethod.GET)
    public ModelAndView connexion(Model model, HttpServletRequest request, Principal principal){
        String error = request.getParameter("error");
        ModelAndView mv = new ModelAndView("connexion");
        
        mv.addObject("error", error);
        
        return mv;
    }
    
    @RequestMapping(value = "/mot_de_passe_oublie")
    public String motDePasseOublie(){
        return "recupererMotDePasse";
    }
    
    @RequestMapping(value = "/mot_de_passe_oublie/traitement", method = RequestMethod.POST)
    public ModelAndView motDePasseOublieTraitement(@RequestParam("email") String email){
        ModelAndView mv = new ModelAndView();
        
        //recherche de l'utilisateur dans la base de données
        Optional<Utilisateur> tmp = utilisateurRepositoryService.getUtilisateurRepository().findByEmail(email);
        
        if(tmp.isPresent()){
            //l'utilisateur a été trouvé on envoie le mail de réinitialisation du mot de passe
            SimpleMailMessage message = new SimpleMailMessage();
            Utilisateur utilisateur = tmp.get();
            String url = "192.168.43.233:8080/tontyn/reinitialiserMotDePasse/?id=" + String.valueOf(utilisateur.getIdUtilisateur());
            message.setTo(email);
            message.setSubject("Réinitialisation du mot de passe");
            message.setText("Rendez-vous à l'adresse " + url + "modifier votre mot de passe");

            // Send email
            //this.emailSender.send(message);
            
            mv.setViewName("formulaireReinitialisation");
            
        }else{
            
        }
        
        return mv;
    }
        
    //modifier le mot de passe
    @RequestMapping(value="/reinitialiserMotDePasse", method = RequestMethod.GET)
    public ModelAndView reinitailsationMotDePasse(Model model, HttpServletRequest request){
        ModelAndView mv = new ModelAndView("formulaireReinitialisation");
        
        Long id = Long.valueOf(request.getParameter("id"));
        
        mv.addObject("id", id);
        
        return mv;
    }
    
    @RequestMapping(value="/reinitialiserMotDePasse/traitement", method = RequestMethod.POST)
    public ModelAndView reinitialiserMotDePasseTraitement(){
        ModelAndView mv = new ModelAndView();
        
        return mv;
    }
    
    //fonction permettant de crypter le mot de passe
    public static String encrytePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
    
}
