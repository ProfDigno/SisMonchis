/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.Utilitario;

import java.text.SimpleDateFormat;
import java.util.Random;

/**
 *
 * @author Digno
 */
public class EvenUtil {
     public String getString_salto_de_linea(String descripcion) {
        String texto_saltado = "";
        for (int caracter = 1; caracter <= descripcion.length(); caracter++) {
            texto_saltado = texto_saltado + descripcion.substring((caracter - 1), caracter);
            if (descripcion.length() > 30) {
                if (caracter % 30 == 0) {
                    texto_saltado = texto_saltado + "\n";
                }
            }
        }
//         System.out.println(texto_saltado);
        return texto_saltado;
    }
     public String getString_crear_senha(int cantidad){
        String senha="";
        char[] caracteres;
        caracteres = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        try {
            int repet = cantidad;
            for (int i = 0; i < repet; i++) {
                senha += caracteres[new Random().nextInt(34)];
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace(System.out);
        }
        return senha;
    }
     public String getString_crear_indice(){
        String indice="";
         java.util.Date utilDate = new java.util.Date(); //fecha actual
        SimpleDateFormat sdf_hora = new SimpleDateFormat("HH");
        int hora = Integer.parseInt(sdf_hora.format(utilDate));
        SimpleDateFormat sdf_min = new SimpleDateFormat("mm");
        int min = Integer.parseInt(sdf_min.format(utilDate));
        SimpleDateFormat sdf_seg = new SimpleDateFormat("ss");
        int seg = Integer.parseInt(sdf_seg.format(utilDate));
        int resul=((hora*3600)+(min*60)+seg);
        String tiempoSegundo=String.valueOf(resul);
        indice=tiempoSegundo+"-"+getString_crear_senha(5);
        return indice;
     }
     
}
