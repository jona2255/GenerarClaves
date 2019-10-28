package com.company;

import javax.crypto.BadPaddingException;
import javax.crypto.SecretKey;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import static jdk.internal.reflect.ConstantPool.Tag.UTF8;

public class Main {

    public static void main(String[] args) throws Exception {

        // Ejercicio 1 parte 1

        String texto = "Texto sin password";

        SecretKey sk = Claus.keygenKeyGeneration(192);
        byte[] cifrado = Claus.encryptData(sk, texto.getBytes());

        System.out.println(cifrado);
        System.out.println("El algoritmo usado es: " + sk.getAlgorithm());
        System.out.println("La longitud del codigo encriptado es: " + sk.getEncoded().length);
        System.out.println("El formato es: " + sk.getFormat());

        byte[] descifrado = Claus.decryptData(sk, cifrado);

        System.out.println(new String(descifrado));

        //Ejercicio 1 parte 2

        String textoPassword = "Texto con password";


        sk = ClausPassword.passwordKeyGeneration("tripaloski",192);

        try {

            byte[] cifradoPassword = ClausPassword.encryptData(sk, textoPassword.getBytes());

            byte[] descifradoPassword = Claus.decryptData(sk, cifradoPassword);

            System.out.println(new String(descifradoPassword ));


        }catch (BadPaddingException b){
            System.out.println();
        }



        // Ejercicio 2

        Path textAmagat = Paths.get("/home/dam2a/Baixades/textamagat");
        byte[] textenbytes = Files.readAllBytes(textAmagat);

        Path claus = Paths.get("/home/dam2a/Baixades/ClausA4.txt");
        List<String> clausenstring = Files.readAllLines(claus);

        int i = 0;

        boolean acierto = false;

        while (!acierto){

            try {
                if(clausenstring.size() < i) return;
                SecretKey cp = ClausPassword.passwordKeyGeneration(clausenstring.get(i),128);
                ClausPassword.decryptData(cp,textenbytes);
                acierto = true;
                System.out.println(clausenstring.get(i));
                System.out.println(new String(ClausPassword.decryptData(cp,textenbytes)));
            }catch (Exception ex){
                System.out.println("Contraseña incorrecta");
                i++;
            }



        }


    }
}
