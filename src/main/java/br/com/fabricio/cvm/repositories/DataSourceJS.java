package br.com.fabricio.cvm.repositories;

import java.io.PrintWriter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DataSourceJS {
	private String fileName;
	private ObjectMapper mapper = new ObjectMapper();
	public DataSourceJS(String fileName) {
		super();
		this.fileName = fileName;
	}
	
	
	public void save(String dataSourceName, Object value) {
		try {
			PrintWriter pw = new PrintWriter(fileName);
			String data = mapper.writeValueAsString(value);
			pw.print("var "+dataSourceName +" = " + data);
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
