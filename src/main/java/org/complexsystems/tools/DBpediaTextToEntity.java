package org.complexsystems.tools;

import org.complexsystems.interfaces.TextToEntity;

public class DBpediaTextToEntity implements TextToEntity{

	private String string;
	
	public DBpediaTextToEntity(String text)
	{
		this.setString(text);
	}
	
	@Override
	public String getEntity() {
		return stringToResource(this.string);
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}
	
	private static String stringToResource (String searchString) {
		String resource = "<http://dbpedia.org/resource/"
				+ searchString.replaceAll(" ", "_")
				+ ">";
		return resource;
	}

}
