package com.ISMIS.dto;

public class Response<T> {
	private int key;
	private T value;
	private String message;
	
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Response [key=" + key + ", value=" + value + ", message=" + message + "]";
	}
}
