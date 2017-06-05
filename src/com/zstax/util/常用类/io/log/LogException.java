package com.zstax.util.常用类.io.log;

/**
 * Log���������е��쳣
 */
public class LogException extends Exception {
	public LogException() {
		super();
	}

	public LogException(String msg) {
		super(msg);
	}

	public LogException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public LogException(Throwable cause) {
		super(cause);
	}
}
