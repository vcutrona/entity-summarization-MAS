package org.complexsystems;

import java.util.ArrayList;

import org.complexsystems.interfaces.Retriever;
import org.complexsystems.tools.DBpediaTextToEntity;
import org.complexsystems.tools.Pair;

/**
 * Modulo che effettua l'estrazione dei dati da DBpedia
 * @author vinid
 */
public class DBpediaGetModule {

	public static void main(String[] args) {

		DBpediaRetriever dbRetriever = new DBpediaRetriever();
		
		String searchString = new DBpediaTextToEntity(Retriever.SEARCH).getEntity();
		
		ArrayList<Pair<String, String>> dbPairs = dbRetriever
				.getAllPairs(searchString);
		printData(dbPairs);
	}
	
	public ArrayList<Pair<String, String>> getData()
	{
		
		String searchString = new DBpediaTextToEntity(Retriever.SEARCH).getEntity();

		
		DBpediaRetriever dbRetriever = new DBpediaRetriever();
		ArrayList<Pair<String, String>> dbPairs = dbRetriever
				.getAllPairs(searchString);

		return dbPairs;
	}

	private static void printData(ArrayList<Pair<String, String>> pairs) {
		for (Pair<String, String> pair : pairs) {
			System.out.println(pair);
		}
	}
	
}
