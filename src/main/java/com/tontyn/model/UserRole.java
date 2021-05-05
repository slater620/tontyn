/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tontyn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;

/**
 *
 * @author tanankem
 */
@Data
@Entity
@Table(name = "User_Role", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "USER_ROLE_UK", columnNames = { "User_Id", "Role_Id" }) })
public class UserRole {
 
    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false)
    private Long id;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id", nullable = false)
    private AppUser appUser;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Role_Id", nullable = false)
    private AppRole appRole;
}
