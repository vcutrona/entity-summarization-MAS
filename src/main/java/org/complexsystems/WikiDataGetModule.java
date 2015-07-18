package org.complexsystems;

import java.util.ArrayList;

import org.complexsystems.interfaces.Retriever;

public class WikiDataGetModule {


	public static void main(String[] args) {

		WikiDataRetriever wdRetriever = new WikiDataRetriever();
		ArrayList<Pair<String, String>> wdPairs = wdRetriever
				.getAllPairs(Retriever.SEARCH);

		printData(wdPairs);
	}
	

	private static void printData(ArrayList<Pair<String, String>> pairs) {
		for (Pair<String, String> pair : pairs) {
			System.out.println(pair);
		}
	}
}
