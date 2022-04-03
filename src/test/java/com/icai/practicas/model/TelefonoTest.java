package com.icai.practicas.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

//import org.junit.jupiter.api.*;


public class TelefonoTest {
    @Test
    public void testTelefono(){

        //Vamos a probar los diferentes casos 
        //1=> Correcto
        Telefono telefonoValido = new Telefono("659416578");
        assertEquals(true,telefonoValido.validar());
        //2=> Longitud erronea 
        Telefono telefonoNoValido2 = new Telefono("659415");
        assertEquals(false,telefonoNoValido2.validar());
        //3=> Longitud erronea 
        Telefono telefonoNovalido3 = new Telefono("659416578883");
        assertEquals(false,telefonoNovalido3.validar());
        //4=> No valido por letra 
        Telefono telefonoNoValido1 = new Telefono("65941657B");
        assertEquals(false,telefonoNoValido1.validar());
        //5=>Con prefijo si que es valido
        Telefono telefonoPrefijo = new Telefono("+34659416578");
        assertEquals(true,telefonoPrefijo.validar());
        //6=>No valido
        Telefono telefonoNoValido4 = new Telefono ("7862661626");
        assertEquals(false, telefonoNoValido4.validar());
    }
}
