package org.complexsystems.interfaces;

import java.util.ArrayList;

import org.complexsystems.Pair;
import org.wikidata.wdtk.datamodel.interfaces.Claim;

public interface Retriever {
	/**
	 * Metodo per ottenere l'elenco di coppie proprietà-valore di una
	 * determinata entità definita dalla stringa in input
	 * 
	 * @param searchString
	 * @return un array di elementi Pair (proprietù-valore)
	 */
	ArrayList<Pair<String, String>> getAllPairs(String searchString);
	
	/**
	 * Metodo per ottenere la proprietà, data la tripla
	 * 
	 * @param claim
	 * @return la proprietà della tripla
	 */
	String getProperty(Claim claim);
	
	/**
	 * Metodo per ottenere l'oggetto, data la tripla
	 * 
	 * @param claim
	 * @return il valore dell'oggetto della tripla (direttamente il valore
	 * se è un literal, il nome dell'entità se è un item)
	 */
	String getObject(Claim claim);
	
}
