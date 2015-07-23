package org.complexsystems.tools;

import java.util.ArrayList;

public class Results {
	private String wikidataDescription;
	
	private String dbpediaDescription;
	
	private ArrayList<Row> pairs;
	
	public String getWikidataDescription() {
		return wikidataDescription;
	}
	public void setWikidataDescription(String wikidataDescription) {
		this.wikidataDescription = wikidataDescription;
	}
	public String getDbpediaDescription() {
		return dbpediaDescription;
	}
	public void setDbpediaDescription(String dbpediaDescription) {
		this.dbpediaDescription = dbpediaDescription;
	}
	public ArrayList<Row> getPairs() {
		return pairs;
	}
	public void setPairs(ArrayList<Row> pairs) {
		this.pairs = pairs;
	}
}
