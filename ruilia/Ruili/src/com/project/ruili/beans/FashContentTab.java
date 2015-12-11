package com.project.ruili.beans;

import java.io.Serializable;

public class FashContentTab implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int id;
	public String name;
	public String cid;
	public FashContentTab(int id, String name,String cid) {
		super();
		this.id = id;
		this.name = name;
		this.cid=cid;
		
	}
	
}
