package testing_trello;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
public class api_test_trello {
	
	public static String Baseurl="https://api.trello.com";
	public static String Key ="2504943bb1fd1b8dd1328d0393817062";
	public static String Token="ATTA004f747d2bc286b8875ace7d55bb5b0d29ae1c124b47786197ddada3e65e71827C0AABA1";
	private static String ID;
	public static int rowcount;
	public static String createTable;
	public static XSSFSheet sh;

	
	
	@Test (priority = 0)
	public void beforetest() throws IOException  {
		File excel = new File("C:\\Users\\shash\\Documents\\workspace-spring-tool-suite-4-4.19.0.RELEASE\\Rest_Assured\\src\\test\\resources\\rest_Assured_test_data.xlsx");
		FileInputStream file = new FileInputStream(excel);	
		
		@SuppressWarnings("resource")
		XSSFWorkbook wb = new XSSFWorkbook(file);
		XSSFSheet sh=wb.getSheet("Sheet1");
		int rowcount = sh.getLastRowNum();
		for(int i=0;i<=rowcount;i++) {
			createTable=sh.getRow(i).getCell(0).getStringCellValue();
			System.out.println(createTable);
			create_board();
			get_board();
			delete_board();
		}
	}
	
	
	
	@Test (priority = 1)
	public void create_board() {
	RestAssured.baseURI=Baseurl;
	Response response=given()
	.queryParam("key", Key)
	.queryParam("token", Token)
	.queryParam("name", createTable)
	.header("Content-Type","application/json")
	
	.when()
	.post("/1/boards/")
		
	.then()
	.assertThat().statusCode(200).extract().response();
	//System.out.println(response.asString());
	String jsonresponse =response.asString();
	
	JsonPath js=new JsonPath(jsonresponse);
	ID = js.get("id");
	
	System.out.println(ID);
	}
	
	
	
	@Test (priority = 2)
	public void get_board() {
		RestAssured.baseURI=Baseurl;
		Response response=given()
		.queryParam("key", Key)
		.queryParam("token", Token)
		.header("Content-Type","application/json")
		
		.when()
		.get("/1/boards/"+ID)
			
		.then()
		.assertThat().statusCode(200).extract().response();
		System.out.println(response.asString());
		
	}
	@Test (priority = 3)
	public void delete_board() {
		RestAssured.baseURI=Baseurl;
		Response response=given()
		.queryParam("key", Key)
		.queryParam("token", Token)
		.header("Content-Type","application/json")
		
		.when()
		.delete("/1/boards/"+ID)
			
		.then()
		.assertThat().statusCode(200).extract().response();
		System.out.println(response.asString());
		
	}
}
