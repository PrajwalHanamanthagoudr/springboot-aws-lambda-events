package com.aws.lambda.kinesis;

public class Order {

	int orderId;
	String product;
	int queantity;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public int getQueantity() {
		return queantity;
	}

	public void setQueantity(int queantity) {
		this.queantity = queantity;
	}
}
