package testing_trello;

import static io.restassured.RestAssured.given;
import java.util.Random;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class SimpleBooks {
	
	// Static : when i add static i can/use this variable in any other class without creating a object
	public static String BaseURL ="https://simple-books-api.glitch.me";
	public static String Token;
	public static String ID;
	
	//Generate Random Name / Email
	public static String[] names = {"Shashidhar1", "Shashidhar2", "Shashidhar3", "Shashidhar4"};
	public static String[] domains = {"example.com", "example.org", "test.com", "test.org"};
	public static Random Rand = new Random();
	public static String name = names[Rand.nextInt(names.length)];
	public static String email = name.toLowerCase() + "@" + domains[Rand.nextInt(names.length)];
	
	@Test(priority=0, enabled = true)	
	public void Authenticate()
	{
		RestAssured.baseURI = BaseURL;
		
		// Using Random Name / Email
		String requestbody = "{\n"
				+ "   \"clientName\": \""+name+"\",\n"
				+ "   \"clientEmail\": \""+email+"\"\n"
				+ "}" ;
		
		Response response = given()
		.header("Content-Type","application/json")
		.body(requestbody)
		
		.when()
		.post("/api-clients/")
		
		.then()
		.assertThat().statusCode(201)
		.extract().response();
		
		String jsonresponse = response.asString();
		System.out.println(jsonresponse);
		
		JsonPath Js = new JsonPath(jsonresponse);
		Token = Js.get("accessToken");
	}
	
	@Test (priority = 1)
	public void createOrder()
	{
		RestAssured.baseURI = BaseURL;
		
		String requestbody = "{\r\n"
				+ "  \"bookId\": 6,\r\n"
				+ "  \"customerName\": \"Johnnys\"\r\n"
				+ "}" ;
		
		Response response = given()	
		.header("Content-Type","application/json")
		.body(requestbody)
		.header("Authorization", "Bearer " +Token)
		
		.when()
		.post("/orders/")	
		
		.then()
		.assertThat().statusCode(201)
		.extract().response();
		
		String jsonresponse = response.asString();
		System.out.println(jsonresponse);
		
		JsonPath Js = new JsonPath(jsonresponse);		
		ID = Js.get("orderId");		
	}
	
	@Test (priority = 2)
	public void DeleteOrder()
	{
		RestAssured.baseURI = BaseURL;
		
		Response response = given()	
		.header("Content-Type","application/json")	
		.header("Authorization", "Bearer " +Token)
		
		.when()
		.delete("/orders/"+ID)	
		
		.then()
		.assertThat().statusCode(204)
		.extract().response();
		
		String jsonresponse = response.asString();
		System.out.println(jsonresponse);	
		System.out.println("Done");
	}
}
