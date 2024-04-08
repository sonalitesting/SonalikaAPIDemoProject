import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import Files.Payload;
import Files.ReusableMethods;



public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//set URI
		RestAssured.baseURI="https://rahulshettyacademy.com"; 
		//GIVEN,WHEN,THEN
		String response=given().queryParam("Key", "qaclick123").header("Content-Type","application/json")
		.body(Payload.AddPlace())
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope",equalTo("APP"))
		.header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		
		System.out.println(response);
		//JSON Coversion
		
		JsonPath js=new JsonPath(response);
		String place_id=js.getString("place_id");
		System.out.println(place_id);
		
		//Update address
		
		String newAdd="sector 14 gurgaon";
		
		given().queryParam("Key", "qaclick123").header("Content-Type","application/json")
		.body(Payload.updatePlace(place_id, newAdd))
		.when().put("maps/api/place/update/json")
		.then().assertThat().statusCode(200)/*.log().all()*/.body("msg", equalTo("Address successfully updated"));
		
		//get place
		
		String getResponse=given().queryParam("key", "qaclick123").queryParam("place_id",place_id)
                		        .when().get("maps/api/place/get/json")
		        .then()/*.log().all()*/.assertThat().statusCode(200).extract().response().asString();
		JsonPath js1=ReusableMethods.covertToJson(getResponse);
		String add=js1.getString("address");
		System.out.println(add);
		Assert.assertEquals(newAdd, add);

		 
		
		
		
	}

}
