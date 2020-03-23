package com.rnv.srs.utility;

import java.util.List;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class SRSException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private String messageId;

	private Object[] params;

	private List<Message> messages;

	public SRSException(List<Message> messages) {
		super();
		this.messages = messages;
	}

	public SRSException(String message, Throwable cause) {
		super(cause);
		this.messageId = message;
	}

	public SRSException(String message) {
		super();
		this.messageId = message;
	}

	public String getMessageId() {
		return messageId;
	}

	public SRSException(String messageId, Object... params) {
		super();
		this.messageId = messageId;
		this.params = params;
	}

	public Object[] getParams() {
		return params;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public static class Message {
		private String messageId;
		private Object[] params;

		public Message() {
		}

		public Message(String messageId, Object ... params) {
			super();
			this.messageId = messageId;
			this.params = params;
		}

		public String getMessageId() {
			return messageId;
		}

		public void setMessageId(String messageId) {
			this.messageId = messageId;
		}

		public Object[] getParams() {
			return params;
		}

		public void setParams(Object[] params) {
			this.params = params;
		}

	}
}
