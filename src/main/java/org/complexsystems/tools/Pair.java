package org.complexsystems.tools;

import java.util.ArrayList;

public class Pair<A, B> {
    private A property;
    private B object;
    private ArrayList<Pair<A, B>> qualifiers;
    private String uri;

    public Pair(A property, B object) {
    	super();
    	this.property = property;
    	this.object = object;
    	this.qualifiers = new ArrayList<Pair<A, B>>();
    }    

    public int hashCode() {
    	int hashFirst = property != null ? property.hashCode() : 0;
    	int hashSecond = object != null ? object.hashCode() : 0;

    	return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }

    public boolean equals(Object other) {
    	if (other instanceof Pair) {
			Pair<A, B> otherPair = ((Pair<A, B>) other);
    		return 
    		((  this.property == otherPair.property ||
    			( this.property != null && otherPair.property != null &&
    			  this.property.equals(otherPair.property))) &&
    		 (	this.object == otherPair.object ||
    			( this.object != null && otherPair.object != null &&
    			  this.object.equals(otherPair.object))) &&
    		 (	this.uri == otherPair.uri ||
    			( this.uri != null && otherPair.uri != null &&
    			  this.uri.equals(otherPair.uri))) &&
    		 (	this.qualifiers == otherPair.qualifiers ||
    			( this.qualifiers != null && otherPair.qualifiers != null &&
    			  this.qualifiers.equals(otherPair.qualifiers))));
    	}

    	return false;
    }

    public String toString()
    { 
    	String string = "";
    	if (!(this.property instanceof ArrayList)) {
	    	string += "(" + property + ", " + object + ")";
	    	if (!this.getQualifiers().isEmpty()) {
	    		string += "\n";
	    		string += "\t Qualifiers: \n";
	    		
	    		for (Pair<A, B> pair : qualifiers) {
					string += "\t " + pair + "\n";
				}
	    	}
    	} else {
    		string += "\t Iterazione \n";
    		for (Pair<String, String> pair : (ArrayList<Pair<String, String>>) this.property) {
    			string += "\t\t";
				string += pair.toString() + "\n";
			}
    		for (Pair<String, String> pair : (ArrayList<Pair<String, String>>) this.object) {
    			string += "\t\t";
				string += pair.toString() + "\n";
			}
    	}
        return string; 
    }

    public A getProperty() {
    	return property;
    }

    public void setProperty(A property) {
    	this.property = property;
    }

    public B getObject() {
    	return object;
    }

    public void setObject(B object) {
    	this.object = object;
    }
    
    public void addQualifier(Pair<A, B> pair){
    	this.qualifiers.add(pair);
    }
    
    public ArrayList<Pair<A, B>> getQualifiers(){
    	return qualifiers;
    }
    
    public void setQualifiers(ArrayList<Pair<A, B>> qualifiers){
    	this.qualifiers = qualifiers;
    }

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
}
