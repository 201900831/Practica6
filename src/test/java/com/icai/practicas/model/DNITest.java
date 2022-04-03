package com.icai.practicas.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import com.icai.practicas.model.*;

import org.junit.jupiter.api.Test;

//import org.junit.jupiter.api.*;


public class DNITest {
    @Test
    public void testDNI(){

        //vamos a probar los diferentes casos 
        //1=> Correcto
        DNI dniValido = new DNI("05958850B");
        assertEquals(true,dniValido.validar());
        //2z=> No valido 
        DNI dniNoValido1 = new DNI("00000000F");
        assertEquals(false,dniNoValido1.validar());
        //3=> No valido
        DNI dniNoValido2 = new DNI("00000001R");
        assertEquals(false,dniNoValido2.validar());
        //4=> No valido
        DNI dniNoValido3 = new DNI("99999999R");
        assertEquals(false,dniNoValido3.validar());
        //5=> Erroneo (falta letra)
        DNI dniErroneo = new DNI("012345678");
        assertEquals(false,dniErroneo.validar());
        //6=> Erroneo (longitud erronea )
        DNI dniErroneo2 = new DNI("012345");
        assertEquals(false,dniErroneo2.validar());
    
    }
    
}