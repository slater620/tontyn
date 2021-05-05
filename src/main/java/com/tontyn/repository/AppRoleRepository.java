/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tontyn.repository;

import com.tontyn.model.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author tanankem
 */
@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, Long>{
   
    
}
