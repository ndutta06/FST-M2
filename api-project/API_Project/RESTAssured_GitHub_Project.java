package API_Project;

import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RESTAssured_GitHub_Project {

	// Declare request specification
		RequestSpecification requestSpec;
		// Declare response specification
		ResponseSpecification responseSpec;
		// Global properties
		String sshKey;
		int sshKeyId;
		@BeforeClass
		public void setUp() {
			// Create request specification
			requestSpec = new RequestSpecBuilder()
					// Set content type
					.setContentType(ContentType.JSON)
					.addHeader("Authorization", "token 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")
					// Set base URL
					.setBaseUri("https://api.github.com")
					// Build request specification
					.build();
			sshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDLPhRQH5Nyvt2oC2RvUeLw/uSfabxkz5o9Lvo1Rgrs5fdIKsLTFwsWrmthmnqDLMLFpCc8CyY4ABkVcXF1DklHH9sICHDxPpEV6pSDT5z3K0WQM0sAsc1dMQVatKWqTQa3JJ08YJrD1GCQNOQNVf3swvManzX659HhAY84hY0GgUAGBFOlkNPyAFi5Qf5PMFcOOiVy3eNG5RfSJ0iI6+ZCQm7PYWRVSjbb8aJubHghxXr/jTr4w3JpzUuvufb8ocxGDzhTonj+/vQAynxTBZPOC2aSdu/rIiM/wqKhumuKBCS9EihP3ChrOp7bdoHT3TDH6T1aQ9sl/AQH1/xYdJuX";
		}
		@Test(priority = 1)
		// Test case using a DataProvider
		public void addKeys() {
			String reqBody = "{\"title\": \"TestKey\", \"key\": \"" + sshKey + "\" }";
			Response response = given().spec(requestSpec) // Use requestSpec
					.body(reqBody) // Send request body
					.when().post("/user/keys"); // Send POST request
			String resBody = response.getBody().asPrettyString();
			System.out.println(resBody);
			sshKeyId = response.then().extract().path("id");
			// Assertions
			response.then().statusCode(201);
		}
		@Test(priority = 2)
		// Test case using a DataProvider
		public void getKeys() {
			Response response = given().spec(requestSpec) // Use requestSpec
					.when().get("/user/keys"); // Send GET Request
			String resBody = response.getBody().asPrettyString();
			System.out.println(resBody);
			// Assertions
			response.then().statusCode(200);
		}
		@Test(priority = 3)
		// Test case using a DataProvider
		public void deleteKeys() {
			Response response = given().spec(requestSpec) // Use requestSpec
					.pathParam("keyId", sshKeyId).when().delete("/user/keys/{keyId}"); // Send GET Request
			String resBody = response.getBody().asPrettyString();
			System.out.println(resBody);
			// Assertions
			response.then().statusCode(204);
		}

}
