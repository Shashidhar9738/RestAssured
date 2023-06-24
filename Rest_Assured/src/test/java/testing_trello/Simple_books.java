package testing_trello;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Simple_books {

	public static String Baseurl = "https://simple-books-api.glitch.me";
	public static String token;
	@Test(priority = 0)
	public void Authenticate() {
		RestAssured.baseURI = Baseurl;
		String requestbody ="{\n"
				+ "   \"clientName\": \"Mivi123456\",\n"
				+ "   \"clientEmail\": \"Mivi123456@example.com\"\n"
				+ "}";
		Response response = given()
				.header("Content=Type","application/json")
				.body(requestbody)

				.when()
				.post("/api-clients/")

				.then()
				.assertThat().statusCode(201)
				.extract().response();
		String jsonresponse = response.asString();
		System.out.println(jsonresponse);
		JsonPath js=new JsonPath(jsonresponse);
		token = js.get("accessToken");
		
	}
}
