package org.complexsystems;

public class Pair<A, B> {
    private A property;
    private B object;

    public Pair(A property, B object) {
    	super();
    	this.property = property;
    	this.object = object;
    }

    public int hashCode() {
    	int hashFirst = property != null ? property.hashCode() : 0;
    	int hashSecond = object != null ? object.hashCode() : 0;

    	return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }

    public boolean equals(Object other) {
    	if (other instanceof Pair) {
    		Pair otherPair = (Pair) other;
    		return 
    		((  this.property == otherPair.property ||
    			( this.property != null && otherPair.property != null &&
    			  this.property.equals(otherPair.property))) &&
    		 (	this.object == otherPair.object ||
    			( this.object != null && otherPair.object != null &&
    			  this.object.equals(otherPair.object))) );
    	}

    	return false;
    }

    public String toString()
    { 
           return "(" + property + ", " + object + ")"; 
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
}
