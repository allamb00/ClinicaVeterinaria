/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clinicaveterinaria;

/**
 *
 * @author andre
 */
public class DNI {
    final static String[] letrasValidas = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};
    
    
    public static boolean validar(String dni){
        return comprobarEstructura(dni) && hashLetra(dni);
    }
    
    private static boolean comprobarEstructura(String dni) {
        return dni.length() == 9 && Character.isLetter(dni.charAt(8)) == true;
    }
    
    private static boolean hashLetra(String dni) {
        int numDNI = Integer.parseInt(dni.substring(0, 8));
        int resto = numDNI % letrasValidas.length;
        
        return dni.substring(8).toUpperCase().equals(letrasValidas[resto]);
    }
}
