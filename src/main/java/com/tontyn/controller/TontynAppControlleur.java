/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tontyn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author tanankem
 */
@Controller
public class TontynAppControlleur {
    
    @RequestMapping("/tontynapp")
    public ModelAndView tontynApp(){
        
        ModelAndView mv = new ModelAndView("tontynapp");
        
        
       
        return mv;
    }
    
}
