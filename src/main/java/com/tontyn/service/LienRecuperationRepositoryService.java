/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tontyn.service;

import com.tontyn.model.LienRecuperation;
import com.tontyn.repository.LienRecuperationRepository;
import java.util.Optional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tanankem
 */
@Data
@Service
public class LienRecuperationRepositoryService {
    
    @Autowired
    private LienRecuperationRepository lienRecuperationRepository;
    
    //fonction permettant de sauvegarder un lien de recupération dans la base de données ainsi que sa période de validité
    public void save(LienRecuperation lienRecuperation) throws Exception{
        
        Optional<LienRecuperation> tmp = lienRecuperationRepository.findByLien(lienRecuperation.getLien());
       
        if(tmp.isPresent()){
            throw new Exception();
            
        }else{
            lienRecuperationRepository.save(lienRecuperation);
        }
    }
}
