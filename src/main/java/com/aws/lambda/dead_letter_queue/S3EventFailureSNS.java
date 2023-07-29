package com.aws.lambda.dead_letter_queue;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;

public class S3EventFailureSNS implements RequestHandler<S3Event, String>{
	
	//private static final AmazonS3 s3Clinet = AmazonS3Client.builder()
	//		.withCredentials(new DefaultAWSCredentialsProviderChain()).build();

	@Override
	public String handleRequest(S3Event input, Context context) {
		
		final LambdaLogger logger = context.getLogger();
		
		logger.log("Recived the S3 Event");
		
		throw new RuntimeException("Something went wrong "+ input.getRecords().toString());
	}
	
}
