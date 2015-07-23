package org.complexsystems.tools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Row {
	
	private Pair<ArrayList<Pair<String, String>>, ArrayList<Pair<String, String>>> properties;
	private Double similarity;
	private String metric;
	
	public Row(Pair<ArrayList<Pair<String, String>>, ArrayList<Pair<String, String>>> pair, double s, String metric) {
		this.properties = pair;
		this.similarity = s;
		this.metric = metric;
	}
	
	
	
	public String getMetric() {
		return metric;
	}



	public void setMetric(String metric) {
		this.metric = metric;
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


	@Override
	public String toString() {
		return (this.properties.toString());
	}


	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.properties.equals( ((Row) obj).getProperties()) && this.similarity == ((Row) obj).getSimilarity();
	}	
	
	public static void main (String args[])
	{
		
		
	}


    public int hashCode() {
    	int hashFirst = this.properties != null ? this.properties.hashCode() : 0;
    	int hashSecond = this.similarity != null ? this.similarity.hashCode() : 0;

    	return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }
}
