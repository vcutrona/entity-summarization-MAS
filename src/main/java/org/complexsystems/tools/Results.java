package org.complexsystems.tools;

import java.util.ArrayList;

public class Results {
	private String wikidataDescription;
	
	private String dbpediaDescription;
	
	private ArrayList<Pair<ArrayList<Pair<String, String>>, ArrayList<Pair<String, String>>>> pairs;
	
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
	public ArrayList<Pair<ArrayList<Pair<String, String>>, ArrayList<Pair<String, String>>>> getPairs() {
		return pairs;
	}
	public void setPairs(
			ArrayList<Pair<ArrayList<Pair<String, String>>, ArrayList<Pair<String, String>>>> pairs) {
		this.pairs = pairs;
	}

	
	
}
