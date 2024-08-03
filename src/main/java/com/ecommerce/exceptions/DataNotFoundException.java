package com.ecommerce.exceptions;


public class DataNotFoundException extends RuntimeException {


	private String responseCode;
	private Object responseMessage;
	private Object result;
	private Object data;
	private long id;

	public DataNotFoundException(String responseCode,  Object result,Object responseMessage, Object data) {
		super();
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
		this.result = result;
		this.data = data;
	}

	public DataNotFoundException() {
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public Object getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(Object responseMessage) {
		this.responseMessage = responseMessage;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public long getId() {
		return id;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ResponseMessageBean [responseCode=" + responseCode + ", responseMessage=" + responseMessage
				+ ", result=" + result + ", data=" + data + ", id=" + id + "]";
	}

	
	
}
