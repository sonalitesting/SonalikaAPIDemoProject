import org.testng.annotations.Test;

import Files.Payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {
	
	@Test
	public void sumValidation() {
		JsonPath js= new JsonPath(Payload.CoursePrice());
		int count=js.getInt("courses.size()");
		System.out.println(count);
		int amount=0;
		for(int i=0;i<count;i++) {
			int price=js.get("courses["+i+"].price");
			int copy=js.getInt("courses["+i+"].copies");
			 amount+=price*copy;
			
		}
		System.out.println(amount);

	}

}
