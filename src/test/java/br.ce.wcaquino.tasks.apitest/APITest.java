package br.ce.wcaquino.tasks.apitest;

import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;

import org.hamcrest.CoreMatchers;

import io.restassured.http.*;

public class APITest {

	@BeforeClass
	public static void setup(){
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}

    @Test
    public void teste(){
        RestAssured
                //Dado, algumas pré condições
                .given()
                //Quando executar determinada ação
                .when()
                    .get("/todo")
                //Então, faz as assertivas
                .then()
                    .statusCode(200)//Espera o StatusCode 200 
        ;
    }

	@Test
	public void deveAdicionarTarefaComSucesso(){
		RestAssured.given()
			.body("{ \"task\": \"Teste de inserção via API (RestAssured)\", \"dueDate\": \"2025-12-05\"    }")//Objeto Json
			.contentType(ContentType.JSON)//Convertendo o texto plano em JSON
		.when()
			.post("/todo")
		.then()
			.statusCode(201) //Created
		;
	}

	@Test
	public void naoDeveAdicionarTarefaInvalida(){
		RestAssured
		.given()
			.body("{ \"task\": \"Teste com erro de data via API (RestAssured)\", \"dueDate\": \"2010-12-30\" }")//Data anterior a data atual
			.contentType(ContentType.JSON)//Convertendo para JSON
		.when()
			.post("/todo")
		.then()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"))//Comparando a mensagem recebida pelo erro com a esperada
		;
	}
}

