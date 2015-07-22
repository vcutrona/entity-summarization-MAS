package org.complexsystems;

import java.util.ArrayList;

import org.complexsystems.interfaces.Retriever;
import org.complexsystems.tools.DBpediaTextToEntity;
import org.complexsystems.tools.Entity;
import org.complexsystems.tools.Pair;

/**
 * Modulo che effettua l'estrazione dei dati da DBpedia
 * @author vinid
 */
public class DBpediaGetModule {

	public static void main(String[] args) {


	}
	
	public Entity getData(String query)
	{
		
		String searchString = new DBpediaTextToEntity(query).getEntity();

		
		DBpediaRetriever dbRetriever = new DBpediaRetriever();
		ArrayList<Pair<String, String>> dbPairs = dbRetriever
				.getAllPairs(searchString);
		String description = dbRetriever.getDescription(searchString);
		
		Entity ent = new Entity(description, dbPairs);

		return ent;
	}

	private static void printData(ArrayList<Pair<String, String>> pairs) {
		for (Pair<String, String> pair : pairs) {
			System.out.println(pair);
		}
	}
	
}
