package com.icai.practicas.controller;

//import com.icai.practicas.controller.ProcessController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProcessControllerTest {
    
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void given_app_when_login_using_right_credencials_then_Ok() throws Exception{
        
        String address = "http://localhost:"+port+"/api/v1/process-step1";

        //nombre, dni y telefono 
        ProcessController.DataRequest dataPrueba = new ProcessController.DataRequest("Mencia Sanchez", "05958850B", "659416578");
        //Request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProcessController.DataRequest> request = new HttpEntity<>(dataPrueba, headers);
        //Response
        ResponseEntity<String> result = this.restTemplate.postForEntity(address, request, String.class);
		
        then(result.getBody()).isEqualTo("{\"result\":\"OK\"}");
        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        }

    @Test
    public void given_app_when_login_using_right_credencials_then_ko() throws Exception{
        
        String address = "http://localhost:"+port+"/api/v1/process-step1";

        //Request con Error en DNI
        ProcessController.DataRequest dataDNIError = new ProcessController.DataRequest("Mencia Sanchez", "00000000F", "659416578");
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProcessController.DataRequest> requestDNIError = new HttpEntity<>(dataDNIError, headers);

        //Response
		ResponseEntity<String> resultErrorDNI = this.restTemplate.postForEntity(address, requestDNIError, String.class);
        then(resultErrorDNI.getBody()).isEqualTo("{\"result\":\"KO\"}");
        then(resultErrorDNI.getStatusCode()).isEqualTo(HttpStatus.OK);

        //Request Error en Telefono
        ProcessController.DataRequest dataTelfError = new ProcessController.DataRequest("Mencia Sanchez", "05958850B", "659415");

        HttpEntity<ProcessController.DataRequest> requestTelfError = new HttpEntity<>(dataTelfError, headers);

        //Response
        ResponseEntity<String> resultTelfError = this.restTemplate.postForEntity(address, requestTelfError, String.class);
        then(resultTelfError.getBody()).isEqualTo("{\"result\":\"KO\"}");
        then(resultTelfError.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void given_app_when_login_using_right_credentials_then_ok_legacy() throws Exception{
            
            
            String address = "http://localhost:" + port + "/api/v1/process-step1-legacy";

            //Correctos 
            MultiValueMap<String, String> datosValidos = new LinkedMultiValueMap<>();
            datosValidos.add("fullName", "Mencia Sanchez");
            datosValidos.add("dni", "05958850B");
            datosValidos.add("telefono", "659416578");
            
            //Request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> requestValido = new HttpEntity<>(datosValidos, headers);


             //Response
            ResponseEntity<String> resultValido = this.restTemplate.postForEntity(address, requestValido, String.class);
            
            then(resultValido.getBody()).contains("Gracias, los datos han sido recibidos");

            then(resultValido.getStatusCode()).isEqualTo(HttpStatus.OK);
            
    }

    @Test
    public void given_app_when_login_using_right_credentials_then_ko_legacy() throws Exception{
            
            
            String address = "http://localhost:" + port + "/api/v1/process-step1-legacy";

            //El nombre vacío
            MultiValueMap<String, String> datosNombreVacio = new LinkedMultiValueMap<>();
            datosNombreVacio.add("fullName", "");
            datosNombreVacio.add("dni", "05958850B");
            datosNombreVacio.add("telefono", "659416578");

            //Dni No valido
            MultiValueMap<String, String> datosDNIInvalido = new LinkedMultiValueMap<>();
            datosDNIInvalido.add("fullName", "Mencia Sanchez");
            datosDNIInvalido.add("dni", "");
            datosDNIInvalido.add("telefono", "659416578");

             //Telefono No valido
             MultiValueMap<String, String> datosTelfInvalido = new LinkedMultiValueMap<>();
             datosTelfInvalido.add("fullName", "Mencia Sanchez");
             datosTelfInvalido.add("dni", "05958850B");
             datosTelfInvalido.add("telefono", "6154627A");


            //Request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> requestNombreVacio = new HttpEntity<>(datosNombreVacio, headers);
            HttpEntity<MultiValueMap<String, String>> requestErrorDNI = new HttpEntity<>(datosDNIInvalido, headers);
            HttpEntity<MultiValueMap<String, String>> requestErrorTelefono = new HttpEntity<>(datosTelfInvalido, headers);
            //Response
            ResponseEntity<String> resultNombreVacio = this.restTemplate.postForEntity(address, requestNombreVacio, String.class);
            ResponseEntity<String> resultErrorDNI = this.restTemplate.postForEntity(address, requestErrorDNI, String.class);
            ResponseEntity<String> resultErrorTelefono = this.restTemplate.postForEntity(address, requestErrorTelefono, String.class);
            
            then(resultErrorDNI.getBody()).contains("Ha habido un problema en su solicitud");
            then(resultErrorTelefono.getBody()).contains("Ha habido un problema en su solicitud");
            
            then(resultNombreVacio.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            then(resultErrorDNI.getStatusCode()).isEqualTo(HttpStatus.OK);
            then(resultErrorTelefono.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}