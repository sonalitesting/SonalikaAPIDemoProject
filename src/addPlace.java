import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import Files.addPlacePayload;
import Files.addPlaceResponsePayload;
import Files.locationPayload;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class addPlace {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RequestSpecification resSpec;
		//RequestSpecification res;
		resSpec= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).addQueryParam("key","qaclick123").build();
		
		//set address
		locationPayload lp=new locationPayload();
		lp.setLat("-38.383494");
		lp.setLng("33.427363");
		
		List<String> list=new ArrayList<String>();
		list.add("shoe park");
		list.add("shop");
		
		
		addPlacePayload ap=new addPlacePayload();
		ap.setLocation(lp);
		ap.setAccuracy("50");
		ap.setAddress("229,side layout cohen 09");
		ap.setLanguage("French-IN");
		ap.setName("Frontline house");
		ap.setPhone_number("(+91)983 893 3938");
		ap.setWebsite("https://google.com");
		ap.setTypes(list);
		
		
		
		addPlaceResponsePayload addPlaceResponse=given().spec(resSpec).body(ap).log().all().when()
				                          .post("/maps/api/place/add/json").then()
				                          .extract().as(addPlaceResponsePayload.class);
		
		addPlaceResponse.getId();
		System.out.println(addPlaceResponse.getStatus());
		System.out.println(addPlaceResponse.getPlace_id());
	}

}
