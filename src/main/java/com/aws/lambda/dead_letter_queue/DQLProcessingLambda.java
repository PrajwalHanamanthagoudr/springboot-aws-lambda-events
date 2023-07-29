package com.aws.lambda.dead_letter_queue;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;

public class DQLProcessingLambda implements RequestHandler<SNSEvent, String> {

	@Override
	public String handleRequest(SNSEvent snsEvent, Context context) {
		final LambdaLogger logger = context.getLogger();

		logger.log("Received SNS Event");

		snsEvent.getRecords().forEach(record -> logger.log("\nDLQ Event:" + record.toString()));
		
		return "Failure";
	}

}
/** we can write like this also to handle the S3 failure issue
public class DQLProcessingLambda {

	public void handleRequest(SNSEvent snsEvent, Context context) {
		final LambdaLogger logger = context.getLogger();

		logger.log("Received SNS Event");

		snsEvent.getRecords().forEach(record -> logger.log("\nDLQ Event:" + record.toString()));
	}

}**/