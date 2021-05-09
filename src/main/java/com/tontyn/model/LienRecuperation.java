/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tontyn.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.annotation.Id;
import lombok.Data;

/**
 *
 * @author tanankem
 * Classe permettant de représenter les liens de récupération ainsi que leur période de validité
 */
@Data
@Entity
public class LienRecuperation {
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="idliens_recuperation")
    private Long idliens_recuperation;
    
    @Id
    @javax.persistence.Id
    private String lien;
    
    @Temporal(TemporalType.TIMESTAMP)
    Date date_debut;
    
    @Temporal(TemporalType.TIMESTAMP)
    Date date_fin;
    
    private Long id_utilisateur;
}
