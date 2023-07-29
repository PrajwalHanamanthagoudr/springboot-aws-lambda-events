package com.aws.lambda.errorhandling;

public class Response<T> {

	private int httpStatusCode;
	private String status;
	private T responseBoody;
	private Error error;
	
	public Response(int httpStatusCode, String status, T responseBoody, Error error) {
		super();
		this.httpStatusCode = httpStatusCode;
		this.status = status;
		this.responseBoody = responseBoody;
		this.error = error;
	}
	
	public int getHttpStatusCode() {
		return httpStatusCode;
	}
	
	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public T getResponseBoody() {
		return responseBoody;
	}
	
	public void setResponseBoody(T responseBoody) {
		this.responseBoody = responseBoody;
	}
	
	public Error getError() {
		return error;
	}
	
	public void setError(Error error) {
		this.error = error;
	}
	
	@Override
	public String toString() {
		return "Response {" +
				" httpStatusCode='"+httpStatusCode +'\''+
				", responseBoody=" + responseBoody + 
				", error=" + error + 
				'}';
	}
}
