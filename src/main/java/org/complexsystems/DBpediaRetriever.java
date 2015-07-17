package org.complexsystems;

import java.util.ArrayList;

import org.complexsystems.interfaces.Retriever;
import org.wikidata.wdtk.datamodel.interfaces.Claim;

/**
 * Classe che implementa l'interfaccia Retriever, per recuperare i dati
 * dalla base di conoscenza DBpedia.
 */
public class DBpediaRetriever implements Retriever {

	public DBpediaRetriever() {}

	@Override
	public ArrayList<Pair<String, String>> getAllPairs(String searchString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProperty(Claim claim) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getObject(Claim claim) {
		// TODO Auto-generated method stub
		return null;
	}
		
}
