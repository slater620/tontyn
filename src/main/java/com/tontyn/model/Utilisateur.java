package com.tontyn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.Data;
import org.springframework.data.annotation.Id;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tanankem
 */
@Data
@Entity
public class Utilisateur {
    
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="idUtilisateur")
    private Long idUtilisateur;
    private String nom;
    private String prenom;
    private String adresse;
    
    @Id
    @javax.persistence.Id
    private String email;
    
    private String telephone;
    private String dateNaissance;
    private String profession;
    private String motDePasse;
}
