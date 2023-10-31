package com.FitnessApp.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Getter
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ResponseObject {

	private String status;
	private String message;
	private Object data;

	public ResponseObject() {
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setMessage(String message) {
		this.message = message;
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

//		this.status = status;
//		this.message = message;
//		this.data = data;
//		this.data2 = data2;
//	}
//	public ResponseObject(String status, String message, Object data, Object data2) {

}
