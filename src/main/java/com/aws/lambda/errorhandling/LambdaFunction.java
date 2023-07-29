package com.aws.lambda.errorhandling;

import org.apache.http.HttpStatus;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LambdaFunction implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
	
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent requestEvent, Context context) {
		
		final LambdaLogger logger = context.getLogger();
		
		logger.log("API full event: "+ requestEvent.toString());
		
		//get user details from POST call saved that to DB
		
		//Fetch the records from the body
		String body = requestEvent.getBody();
		
		final User user = gson.fromJson(body, User.class);
		
		//client check - if username and id are not null
		if(user == null || StringUtils.isNullOrEmpty(user.getUsername()) || user.getId() == null) {
			//throw new RuntimeException("Details are not valid"); this is not correct way to write a code
			//Sanitize the error
			return returnApiResponse(HttpStatus.SC_BAD_REQUEST, "Failure", "Request body not valid", 
					Constants.CLIENT_ERROR_MESSAGE, Constants.CLIENT_ERROR_CODE, logger);
		}
		
		//Server error
		try {
			performOperation(user);
		}catch (Exception e) {
			return returnApiResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Failure", "DB called failed", 
					Constants.SERVER_ERROR_CODE, Constants.SERVER_ERROR_MESSAGE, logger);
		}
		
		// success response
		APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent()
				.withStatusCode(HttpStatus.SC_OK)
				.withBody(gson.toJson(new Response<User>(HttpStatus.SC_OK, "Success", user, null)));
		
		logger.log("\n"+ responseEvent.toString());
		return responseEvent;
	}
	
	public void performOperation(User user) {
		//DB operation or calling a new API
		if(user.getId() == 101) {
			//error
			throw new RuntimeException("User Id is not found");
		}
	}
	
	public APIGatewayProxyResponseEvent returnApiResponse(int statusCode,String status, 
			String responseBody, String errorMessage, String errorCode, LambdaLogger logger) {
		
		final Error error = new Error();
		if(!StringUtils.isNullOrEmpty(errorCode)) {
			error.setErrorCode(errorCode);
			error.setErrorMessage(errorMessage);
		}
		
		APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent()
				.withStatusCode(statusCode)
				.withBody(gson.toJson(new Response<String>(statusCode, status, responseBody, error)));
		
		logger.log("\n" +responseEvent.toString());
		return responseEvent;
	}

}
