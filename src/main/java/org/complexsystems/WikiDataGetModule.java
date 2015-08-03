package org.complexsystems;

import java.util.ArrayList;

import org.complexsystems.tools.Entity;
import org.complexsystems.tools.Pair;
import org.complexsystems.tools.WikiDataTextToEntity;

public class WikiDataGetModule {
	
	/**
	 * Metodo per restituire al chiamante i dati trovati
	 * @return
	 */
	public Entity getData(String query)
	{
		WikiDataSPARQLRetriever wdRetriever = new WikiDataSPARQLRetriever();
	
		String serachString = new WikiDataTextToEntity(query).getEntity();

		ArrayList<Pair<String, String>> wdPairs = wdRetriever
				.getAllPairs(serachString);
		String description = wdRetriever.getDescription(serachString);
		Entity ent = new Entity(description, "", wdPairs);
		
		return ent;
	}

}
