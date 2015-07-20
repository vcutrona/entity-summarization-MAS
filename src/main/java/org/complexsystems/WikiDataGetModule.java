package org.complexsystems;

import java.util.ArrayList;

import org.complexsystems.interfaces.Retriever;
import org.complexsystems.tools.Entity;
import org.complexsystems.tools.Pair;
import org.complexsystems.tools.WikiDataTextToEntity;

public class WikiDataGetModule {


	public static void main(String[] args) {

		WikiDataRetriever wdRetriever = new WikiDataRetriever();
		
		String serachString = new WikiDataTextToEntity(Retriever.SEARCH).getEntity();
		
		ArrayList<Pair<String, String>> wdPairs = wdRetriever.getAllPairs(serachString);
		
		String description = wdRetriever.getDescription(serachString);
		Entity ent = new Entity(description, wdPairs);
		
		printData(wdPairs);
	}
	
	/**
	 * Metodo per restituire al chiamante i dati trovati
	 * @return
	 */
	public Entity getData()
	{
		WikiDataRetriever wdRetriever = new WikiDataRetriever();
	
		String serachString = new WikiDataTextToEntity(Retriever.SEARCH).getEntity();

		ArrayList<Pair<String, String>> wdPairs = wdRetriever
				.getAllPairs(serachString);
		String description = wdRetriever.getDescription(serachString);
		Entity ent = new Entity(description, wdPairs);
		
		return ent;
	}
	

	private static void printData(ArrayList<Pair<String, String>> pairs) {
		for (Pair<String, String> pair : pairs) {
			System.out.println(pair);
		}
	}
}
