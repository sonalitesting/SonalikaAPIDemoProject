import Files.E2EPayload;
import Files.Login;
import Files.LoginResponse;
import Files.Order;
import Files.OrderDetail;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

public class EcommerceE2E {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RequestSpecification req=new  RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").
		setContentType(ContentType.JSON).build();
		Login l=new Login();
		l.setUserEmail("sonalika12@gmail.com");
		l.setUserPassword("Niji@1223");
		
		RequestSpecification loginreq=given().spec(req).body(l);
		LoginResponse loginres=loginreq.when().post("api/ecom/auth/login").then()
				.extract().as(LoginResponse.class);
		String token=loginres.getToken();
		System.out.println(token);
		String UserId=loginres.getUserId();
		System.out.println(UserId);
		
		//create product
		RequestSpecification CreateProductreq=new  RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").
				addHeader("Authorization", token).build();
		RequestSpecification CreateProduct=given().log().all().spec(CreateProductreq).param("productName", "qwertyy")
				.param("productAddedBy", UserId).param("productCategory", "fashion").param("productSubCategory", "shirts")
				.param("productPrice", "11500").param("productDescription", "Addias Originals").param("productFor", "women")
				.multiPart("productImage",new File("C:\\Users\\Hp\\Desktop\\REST API_files\\images.jpg"));
		String productres=CreateProduct.when().post("api/ecom/product/add-product").then().extract().response().asString();
		JsonPath js=new JsonPath(productres);
		String ProductId=js.getString("productId");
		String message=js.getString("message");
		System.out.println(ProductId);
		System.out.println(message);
		
		
		//create order
		RequestSpecification createorderReq=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/")
				                            .addHeader("authorization", token).setContentType(ContentType.JSON).build();
		OrderDetail or=new OrderDetail();
		or.setCountry("india");
		or.setProductOrderId(ProductId);
		
		List<OrderDetail> OrderDetails=new ArrayList<OrderDetail>();
		OrderDetails.add(or);
		
		Order order=new Order();
		order.setOrder(OrderDetails);
		
		RequestSpecification createOrderres=given().log().all().spec(createorderReq);
		String res=createOrderres.when().post("api/ecom/order/create-order").then().extract().asString();
		System.out.println(res);
		
		//delete product
		RequestSpecification deletereq=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").
				addHeader("authorization", token).setContentType(ContentType.JSON).build();
      
		RequestSpecification orderDeletereq=given().relaxedHTTPSValidation().log().all().spec(deletereq).pathParam("productId", ProductId);
		String deletemessage=orderDeletereq.when().delete("api/ecom/product/delete-product/{productId}").then().extract().response().asString();
		System.out.println(deletemessage);
	}
}
