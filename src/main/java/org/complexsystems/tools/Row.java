package org.complexsystems.tools;

import java.util.ArrayList;

public class Row {
	
	private Pair<ArrayList<Pair<String, String>>, ArrayList<Pair<String, String>>> properties;
	private double similarity;
	
	public Row(Pair<ArrayList<Pair<String, String>>, ArrayList<Pair<String, String>>> pair, double s) {
		this.properties = pair;
		this.similarity = s;
	}
	
	
	public Pair<ArrayList<Pair<String, String>>, ArrayList<Pair<String, String>>> getProperties() {
		return properties;
	}
	public void setProperties(
			Pair<ArrayList<Pair<String, String>>, ArrayList<Pair<String, String>>> properties) {
		this.properties = properties;
	}
	public double getSimilarity() {
		return similarity;
	}
	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}	
}
