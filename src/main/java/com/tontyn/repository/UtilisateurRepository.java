/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tontyn.repository;

import com.tontyn.model.Utilisateur;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author tanankem
 */
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, String>{
    
    Optional<Utilisateur> findByEmail(String email);
    
    Utilisateur findOneByEmail(String email);
}
