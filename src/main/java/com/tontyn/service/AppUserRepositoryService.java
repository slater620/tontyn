/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tontyn.service;

import com.tontyn.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author tanankem
 */
public class AppUserRepositoryService {
    
    @Autowired
    private AppUserRepository appUserRepository;
}
