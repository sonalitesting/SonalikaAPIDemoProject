import org.testng.annotations.Test;

import Files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;



public class JiraApi {
	
	SessionFilter cookie= new SessionFilter();
	@Test
	public void jiaLogin() {
		RestAssured.baseURI="http://localhost:8080/";
		
		
		String session=given().log().all().header("content-type","application/json")
				.body("{ \"username\": \"sonalika\", \"password\": \"Niji@1223\" }").filter(cookie)
				.when().post("rest/auth/1/session")
				.then().log().all().assertThat().statusCode(200).extract().asString();
		
		JsonPath js=ReusableMethods.covertToJson(session);
		String session_name=js.getString("session.name");
		String value=js.getString("session.value");
		System.out.println(session_name + "and "+ value);
		
		//Add Comment
		
		/*given().pathParam("key", "10002").header("content-type","application/json")
		.body("{\r\n"
				+ "    \"body\": \"Adding Third comment through APICODE.\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(cookie).when().post("rest/api/2/issue/{key}/comment").then()
		.log().all().assertThat().statusCode(201);*/
		
		// add attachment
		
		/*given().pathParam("key", "10002").header("content-type","multipart/form-data")
		.header("X-Atlassian-Token", "no-check")
		.filter(cookie).multiPart("file",new File("Jira.txt")).when()
		.post("rest/api/2/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);*/
		
		//get defect
		
		given().pathParam("key", "10002").queryParam("fields", "comment")
		.filter(cookie).header("content-type","application/json")
		.when().get("/rest/api/2/issue/{key}").then().log().all().assertThat().statusCode(200);
		
	} 
	
	
	

}

