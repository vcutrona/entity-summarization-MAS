package org.complexsystems;

import java.util.ArrayList;

import org.complexsystems.interfaces.Retriever;

/**
 * Modulo che effettua l'estrazione dei dati da DBpedia
 * @author vinid
 *
 */
public class DBpediaGetModule {

	public static void main(String[] args) {

		DBpediaRetriever dbRetriever = new DBpediaRetriever();
		ArrayList<Pair<String, String>> dbPairs = dbRetriever
				.getAllPairs("<http://dbpedia.org/resource/Cristiano_Ronaldo>");

		printData(dbPairs);
	}
	
	public ArrayList<Pair<String, String>> getData()
	{
		DBpediaRetriever dbRetriever = new DBpediaRetriever();
		ArrayList<Pair<String, String>> dbPairs = dbRetriever
				.getAllPairs("<http://dbpedia.org/resource/Cristiano_Ronaldo>");

		return dbPairs;
	}

	private static void printData(ArrayList<Pair<String, String>> pairs) {
		for (Pair<String, String> pair : pairs) {
			System.out.println(pair);
		}
	}
	
}
