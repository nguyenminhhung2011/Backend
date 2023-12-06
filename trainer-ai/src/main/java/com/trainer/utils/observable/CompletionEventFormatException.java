package com.trainer.utils.observable;

/**
 * Exception indicating a SSE format error
 */
public class CompletionEventFormatException extends Throwable{
	public CompletionEventFormatException(String msg){
		super(msg);
	}
}