import org.testng.annotations.Test;

import Files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class OAuth {
@Test
	
	public void clientCred() {
	
	String response= given().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
			                .formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParam("grant_type", "client_credentials")
			                .formParam("scope", "trust")
			                .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
			                .then().assertThat().statusCode(200).extract().asString();
	
	JsonPath js= new JsonPath(response);
	String accessToken=js.getString("access_token");
	
	System.out.println(accessToken);
	
	String courseDetail=given().queryParam("access_token",accessToken)
			            .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails?access_token=zZT/kao4SrSyrbNqhbjFCQ==")
			            .then().assertThat().statusCode(401).extract().asString();
	
	System.out.println(courseDetail);			
		
	}
}
