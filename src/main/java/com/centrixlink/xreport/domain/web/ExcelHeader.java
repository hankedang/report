package com.centrixlink.xreport.domain.web;

import java.util.ArrayList;
import java.util.List;

public class ExcelHeader {

	private String id;
	private String name;
	List<ExcelHeader> subHeader;

	public ExcelHeader(){}
	
	public ExcelHeader(String id, String name){
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ExcelHeader> getSubHeader() {
		return subHeader;
	}

	public void setSubHeader(List<ExcelHeader> subHeader) {
		this.subHeader = subHeader;
	}
	
	public void addSubHeader(ExcelHeader header){
		if(subHeader == null){
			this.subHeader = new ArrayList<ExcelHeader>();
		}
		subHeader.add(header);
	}
}
