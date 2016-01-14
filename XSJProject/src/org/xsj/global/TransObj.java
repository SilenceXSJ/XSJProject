package org.xsj.global;

import java.io.Serializable;

public class TransObj  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String head;
	
	private Object obj;

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
}
