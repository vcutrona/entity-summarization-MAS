package org.complexsystems.tools;

import java.util.ArrayList;

public class Entity {
	private String description;
	private ArrayList<Pair<String, String>> listOfPropertiesAndPairs;
	
	public Entity(String description, ArrayList <Pair<String, String>> list) {
		this.description = description;
		this.listOfPropertiesAndPairs = list;
	}
	
	public ArrayList<Pair<String, String>> getListOfPropertiesAndPairs() {
		return listOfPropertiesAndPairs;
	}
	public void setListOfPropertiesAndPairs(ArrayList<Pair<String, String>> listOfPropertiesAndPairs) {
		this.listOfPropertiesAndPairs = listOfPropertiesAndPairs;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
