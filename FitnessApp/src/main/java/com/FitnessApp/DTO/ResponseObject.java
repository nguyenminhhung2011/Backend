package com.FitnessApp.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ResponseObject {

	private String status;
	private String message;
	private Object data;

	public ResponseObject() {
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

//	private Object data2;

	public ResponseObject(String status, String message, Object data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

//	public ResponseObject(String status, String message, Object data, Object data2) {
//		this.status = status;
//		this.message = message;
//		this.data = data;
//		this.data2 = data2;
//	}

}
