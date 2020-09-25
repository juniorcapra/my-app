package com.mycompany.app.myapp.utils;

public class ValidadorUtils {


    public static boolean ehCpfValido(String cpf){
        String cpfAtualizado = cpf.replaceAll("\\D", "");
        return cpfAtualizado.length() == 11;
    }
}
