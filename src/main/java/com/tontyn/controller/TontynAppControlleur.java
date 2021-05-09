/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tontyn.controller;

import com.tontyn.model.Utilisateur;
import com.tontyn.service.UtilisateurRepositoryService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author tanankem
 */
@Controller
public class TontynAppControlleur {
    
    @Autowired
    UtilisateurRepositoryService utilisateurRepositoryService;
    
    @RequestMapping("/tontynapp")
    public ModelAndView tontynApp(Principal principal){
        User user = (User)((Authentication)principal).getPrincipal();
        String email = user.getUsername();
        
        Utilisateur utilisateur = utilisateurRepositoryService.getUtilisateurRepository().findOneByEmail(email);
        
        ModelAndView mv = new ModelAndView("tontynapp");
        
        mv.addObject("utilisateur", utilisateur);
       
        return mv;
    }
    
}
