/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tontyn.service;

import com.tontyn.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tontyn.repository.UtilisateurRepository;
import java.util.Optional;
import lombok.Data;

/**
 *
 * @author tanankem
 */
@Data
@Service
public class UtilisateurRepositoryService{
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    
    //fonction permettant de sauvegarder un utilisateur
    /*cette fonction est utilisée pour sauvegarder un nouvel utilisateur afin d'éviter d'écraser
    un utilisateur déjà existant au profit du nouvel utilisateur*/
    public void save(Utilisateur utilisateur) throws Exception{
        
        Optional<Utilisateur> tmp = utilisateurRepository.findByEmail(utilisateur.getEmail());
       
        if(tmp.isPresent()){
            throw new Exception();
        }else{
            utilisateurRepository.save(utilisateur);
        }
    }
    
    /*fonction permettant de sauvegarder les modifications effectuées sur un utilisateur lors des mises 
    à jour*/
    public void update(Utilisateur utilisateur){
        utilisateurRepository.save(utilisateur);
    }
   
    public Utilisateur checkConnexion(String email, String motDePasse) throws Exception{
       //check if the user exits
       Optional<Utilisateur> tmp = utilisateurRepository.findByEmail(email);
       Utilisateur utilisateur = new Utilisateur();
       
       if(tmp.isPresent()){
           utilisateur = tmp.get();
           
           //vérification du mot de passe
           if(motDePasse.equals(utilisateur.getMotDePasse())){
               
               return utilisateur;
               
           }else{
               throw new Exception("mot_de_passe_incorrect");
           }
           
       }else{
           throw new Exception("utilisateur_non_trouve");
       }
    }
    
}
