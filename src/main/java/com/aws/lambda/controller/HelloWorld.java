package com.aws.lambda.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification.RequestParametersEntity;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification.ResponseElementsEntity;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification.S3BucketEntity;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification.S3Entity;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification.S3ObjectEntity;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification.UserIdentityEntity;
import com.aws.lambda.pojo.Employee;

public class HelloWorld {

	public Map<String, Integer> handlerMap(Map<String, Integer> inputMap) {
		final Map<String, Integer> salaryMap = new HashMap<>();
		inputMap.forEach((key, value) -> salaryMap.put(key, value + 500));
		return salaryMap;
	}
	
	public List<Employee> handlerPojo(List<Employee> employees){
		return employees.stream()
				.filter(emp -> emp.getName().startsWith("A"))
				.collect(Collectors.toList());
	}
	
	public String handlerEvent(S3Event s3Event) {
		String bucketName = "";
		if(!s3Event.getRecords().isEmpty()) {
			bucketName = s3Event.getRecords().get(0).getS3().getBucket().getName();
		}
		return bucketName;
	}
	
	@SuppressWarnings("deprecation")
	public List<S3EventNotificationRecord> handlerEvent(S3Event s3Event,Context context) {
		
		LambdaLogger lambdaLogger = context.getLogger();
		lambdaLogger.log("<----------Inside the handlerEvent methos------->");
		lambdaLogger.log("S3Event : "+ s3Event.getRecords());
		String awsRigion = "ap-south-1";
		String eventVersion = "2.0";
		String eventName = "";
		String eventSource = "";
		String eventTime = "";
		String sourceIPAddress = "";
		String xAmzId2 = "";
		String xAmzRequestId = "";
		String configurationId = "";
		String name = "";
		String principalId = "";
		String arn = "";
		String key = "";
		Integer size = 100;
		String eTag = "";
		String sequencer = "";
		String s3SchemaVersion = "";
		RequestParametersEntity requestParameters = new RequestParametersEntity(sourceIPAddress);
		ResponseElementsEntity responseElements = new ResponseElementsEntity(xAmzId2, xAmzRequestId);
		UserIdentityEntity identityEntity = new UserIdentityEntity(principalId); 
		S3BucketEntity bucket = new S3BucketEntity(name, identityEntity, arn);
		S3ObjectEntity objectEntity = new S3ObjectEntity(key, size, eTag, sequencer);
		S3Entity s3 = new S3Entity(configurationId, bucket, objectEntity, s3SchemaVersion);
		
		S3EventNotificationRecord s3ENR = new S3EventNotificationRecord(awsRigion, eventName, eventSource, eventTime, eventVersion, requestParameters, responseElements, s3, identityEntity);
		
		List<S3EventNotificationRecord> notificationRecords = new ArrayList<>();
		notificationRecords.add(s3ENR);
		
		lambdaLogger.log("List Of Records : "+ notificationRecords);
		return notificationRecords;
	}
}
