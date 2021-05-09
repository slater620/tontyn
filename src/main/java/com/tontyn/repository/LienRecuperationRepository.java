/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tontyn.repository;

import com.tontyn.model.LienRecuperation;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tanankem
 */
public interface LienRecuperationRepository extends JpaRepository<LienRecuperation, String>{
    
    Optional<LienRecuperation> findByLien(String lien);
}
