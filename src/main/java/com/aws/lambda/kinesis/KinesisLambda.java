package com.aws.lambda.kinesis;

import java.nio.charset.StandardCharsets;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.KinesisEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class KinesisLambda {

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	
	public void handle(KinesisEvent kinesisEvent, Context context) {
		
		final LambdaLogger logger = context.getLogger();
		logger.log("Lambda Kinesis event received");
		
		for(KinesisEvent.KinesisEventRecord record : kinesisEvent.getRecords()) {
			
			//200 records each record type would be like this.
			/*	{
					"orderId":1234456,
					"product":"jeans", this data is available in record 
					"quantity":5
				}
			 */
			String orderData = StandardCharsets.UTF_8.decode(record.getKinesis().getData()).toString();
			logger.log("Order Data : "+orderData);
			
			final Order order = GSON.fromJson(orderData, Order.class);
			
			if(order.getQueantity() > 15) {
				throw new RuntimeException("Quantity is large possible fraud");
			}
		}
	}
}
