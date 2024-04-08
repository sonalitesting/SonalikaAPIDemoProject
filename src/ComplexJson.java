import org.testng.Assert;

import Files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJson {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
  
		JsonPath js= new JsonPath(Payload.CoursePrice());
		int count=js.getInt("courses.size()");
		System.out.println(count);
		int purchaseamount=js.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseamount);
		String firstcoursetitle=js.getString("courses[0].title");
		System.out.println("First course is " + firstcoursetitle);
		
		//print all courses
		int totalAmount=0;
		for(int i=0;i<count;i++) {
			String coursetitle=js.get("courses["+i+"].title");
			int courseprice=js.get("courses["+i+"].price");
			int coursecopy=js.get("courses["+i+"].copies");
			System.out.println("the course title is " +coursetitle +" and the respective price is " +courseprice); 
			if(coursetitle.equalsIgnoreCase("RPA")) {
				System.out.println("no of copies sold by RPA Course " + js.get("courses["+i+"].copies").toString());
				//break;
			}
			totalAmount+=coursecopy*courseprice;
			
		}
		System.out.println(totalAmount);
		//Assert.assertEquals(totalAmount, purchaseamount);
	}

}
