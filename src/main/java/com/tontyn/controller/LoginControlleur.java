/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tontyn.controller;

import com.tontyn.model.AppRole;
import com.tontyn.model.AppUser;
import com.tontyn.model.LienRecuperation;
import com.tontyn.model.UserRole;
import com.tontyn.model.Utilisateur;
import com.tontyn.repository.AppUserRepository;
import com.tontyn.repository.UserRoleRepository;
import com.tontyn.service.LienRecuperationRepositoryService;
import com.tontyn.service.UtilisateurRepositoryService;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @Autowired
    public LienRecuperationRepositoryService lienRecuperationRepositoryService;
    
    private int N = 45; //taille de la chaîne aléatoire produite pour le lien de récupération du mot de passe
    
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
    
    //requete pour la création d'un nouveau compte
    //traite les informations recues une fois le formulaire envoyé
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
    
    //requête qui traite la récupération du mot de passe une fois l'adresse mail renseignée
    @RequestMapping(value = "/mot_de_passe_oublie/traitement", method = RequestMethod.POST)
    public ModelAndView motDePasseOublieTraitement(@RequestParam("email") String email) throws MessagingException{
        ModelAndView mv = new ModelAndView();
        String statut = ""; //indique si la requête a réussi ou pas
        
        //recherche de l'utilisateur dans la base de données
        Optional<Utilisateur> tmp = utilisateurRepositoryService.getUtilisateurRepository().findByEmail(email);
        
        if(tmp.isPresent()){//l'utilisateur a été trouvé
            Utilisateur utilisateur = tmp.get();
            System.out.println("utilisateur trouvé");
            
            //**création de l'url à envoyer
            
            String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            String randomStr = ""; //chaîne aléatoire à insérer dans le lien de récupération
            boolean repeat = false;
            
            do{
                 StringBuilder s = new StringBuilder(N);
            
                for(int i = 0; i < N; i++){
                    int index = (int)(str.length() * Math.random());
                    s.append(str.charAt(index));
                }
                
                randomStr = s.toString();
                
                System.out.println("randomStirng: " + randomStr);
                
                //vérification que cette chaîne n'existe pas encore dans la base de données
                Optional<LienRecuperation> tmp2 = lienRecuperationRepositoryService.getLienRecuperationRepository().findByLien(randomStr);
                
                if(tmp2.isPresent()){//la chaîne a déjà été utilisée
                    repeat = true;
                    
                }else{//la chaîne n'a pas encore été utilisée
                    repeat = false;
                    
                    //**création d'un lien de récupération
                    LienRecuperation lr = new LienRecuperation();
                    
                    lr.setLien(randomStr);
                    lr.setId_utilisateur(utilisateur.getIdUtilisateur());
                        
                    //création des dates de début et de fin de validité du lien
                    
                    Date date_debut = new Date();
                    
                    Long tempsValidite = date_debut.getTime()+(1000*60*60);
                    
                    Date date_fin = new Date(tempsValidite);

                    lr.setDate_debut(date_debut);
                    lr.setDate_fin(date_fin);
                    
                    
                    lienRecuperationRepositoryService.getLienRecuperationRepository().save(lr);
                }
                
            }while(repeat);
            
            //url à envoyer à l'utilisateur pour la réinitialisation de son mot de passe
            String url = "http://192.168.43.233:8080/tontyn/reinitialiserMotDePasse/?lien=" + randomStr;
           
            System.out.println("url: " + url);
            
            //configuration du mail à envoyer
            MimeMessage mimeMessage = this.emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            String htmlMsg = "Rendez-vous à l'adresse <br><a href=\"" + url + "\">" + url + "</a> <br> pour modifier votre mot de passe.";

            //mimeMessage.setContent(htmlMsg, "text/html"); /** Use this or below line **/
            helper.setText(htmlMsg, true); // Use this or above line.
            helper.setTo(email);
            helper.setSubject("Réinitialisation du mot de passe");
            helper.setFrom(com.tontyn.configuration.MyConstants.MY_EMAIL);
            
            //envoi du mail
            //this.emailSender.send(mimeMessage);
            
            statut = "succes";
            
        }else{//l'utilisateur n'a pas été trouvé
            
            statut = "echec";
            
        }
        
        mv.setViewName("reportReinitialisationMotDePasse");

        mv.addObject("statut", statut);
            
        return mv;
    }
    
    //traitement de la requête du lien de modification du mot de passe envoyé à l'uitilsateur
    //renvoie le formulaire pour la modification du mot de passe si le lien est valide
    @RequestMapping(value="/reinitialiserMotDePasse", method = RequestMethod.GET)
    public ModelAndView reinitailsationMotDePasse(Model model, HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        String lien = request.getParameter("lien");
        
        //recherche du lien de connexion dans la base de données
        Optional<LienRecuperation> tmpLienRecuperation = lienRecuperationRepositoryService.getLienRecuperationRepository().findByLien(lien);
        
        if(tmpLienRecuperation.isPresent()){//si le lien de récupération est trouvé dans la base de données
            LienRecuperation lienRecuperation = tmpLienRecuperation.get();
            Long idUtilisateur = lienRecuperation.getId_utilisateur();
            
            //vérification de la date de validité
            Date now = new Date();
            Date dateDebut = lienRecuperation.getDate_debut();
            Date dateFin = lienRecuperation.getDate_fin();
            
            if(now.after(dateDebut) && now.before(dateFin)){
                mv.setViewName("formulaireReinitialisation");
                
                mv.addObject("id", idUtilisateur);
            }
            
        }else{//le lien de récupération n'existe pas dans la base de données
            
        }
        
        return mv;
    }
    
    //requete permettant de traiter les mots de passes recus lors de la modification du mot de passe
    @RequestMapping(value="/reinitialiserMotDePasse/traitement/{id}", method = RequestMethod.POST)
    public ModelAndView reinitialiserMotDePasseTraitement(@PathVariable("id") Long id, @RequestParam("motDePasse1") String motDePasse1, @RequestParam("motDePasse2") String motDePasse2){
        ModelAndView mv = new ModelAndView();
        String message = "Les deux mots de passe ne sont pas indentiques.";
        String statut = "";
        
        if(!motDePasse1.equals(motDePasse2)){//les deux mots de passe sont différents
            mv.setViewName("formulaireReinitialisation");
                
            mv.addObject("id", id);
            mv.addObject("message", message);
            
        }else{//les deux mots de passe sont identiques
            mv.setViewName("reportReinitialisationMotDePasse");
            
            //on cherche l'utilisateur dans la base de données
            Utilisateur utilisateur = utilisateurRepositoryService.getUtilisateurRepository().findOneByIdUtilisateur(id);
            AppUser appUser = appUserRepository.findOneByUserName(utilisateur.getEmail());
            
            utilisateur.setMotDePasse(encrytePassword(motDePasse1));
            appUser.setEncrytedPassword(encrytePassword(motDePasse1));
            
            utilisateurRepositoryService.update(utilisateur);
            appUserRepository.save(appUser);
            
            statut = "modifie";
            
            mv.addObject("statut", statut);
        }
        
        return mv;
    }
    
    //fonction permettant de crypter le mot de passe
    public static String encrytePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
    
}
