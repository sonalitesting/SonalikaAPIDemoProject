import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Files.Payload;
import Files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class LibraryApi {
	
	String uri="http://216.10.245.166";
	String bookId;

	@Test(dataProvider = "BookData")
	public void addBook(String isbn, String aisle) {
		
		RestAssured.baseURI=uri;
		String response=given().header("Content-Type","application/json")
				        .body(Payload.addBook(isbn, aisle))
				        .when().post("/Library/Addbook.php")
				        .then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js= ReusableMethods.covertToJson(response);
		 bookId=js.get("ID");
		
		System.out.println(response);
		System.out.println(bookId);
		
		// delete book
		String response1=given().header("Content-Type","application/json")
		        .body(Payload.deleteBook(bookId))
		        .when().post("/Library/DeleteBook.php")
		        .then().assertThat().statusCode(200).extract().response().asString();

             JsonPath js1=ReusableMethods.covertToJson(response1);
             String msg=js1.getString("msg");
             System.out.println(msg);
	}
	
	/*@Test
	public void deleteBook() {
		RestAssured.baseURI=uri;
		String response=given().header("Content-Type","application/json")
				        .body(Payload.deleteBook(bookId))
				        .when().post("/Library/DeleteBook.php")
				        .then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js=ReusableMethods.covertToJson(response);
		String msg=js.getString("msg");
		System.out.println(bookId);
		System.out.println(msg);
	}
	*/
	@DataProvider(name="BookData")
	public  Object[][] getData() {
		return new Object[][] {{"selenium","101"},{"api","102"},{"tosca","103"}};
	}
	

}
