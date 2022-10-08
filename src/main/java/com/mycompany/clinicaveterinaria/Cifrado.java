/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clinicaveterinaria;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 *
 * @author andre
 */
public class Cifrado {
    
    
    
    public static String cifrar(String... args){
        String claro = "";
        for(String s : args) {
            claro += s;
        }
        
	String cifrado = null;
	try {
	    MessageDigest digest = MessageDigest.getInstance("SHA-256");
	    digest.reset();
	    digest.update(claro.getBytes("utf8"));
	    cifrado = String.format("%064x", new BigInteger(1, digest.digest()));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
	return cifrado;
    }
}
