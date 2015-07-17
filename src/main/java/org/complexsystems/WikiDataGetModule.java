package org.complexsystems;

import java.util.ArrayList;

public class WikiDataGetModule {

	static final String SEARCH = "Cristiano Ronaldo";

	public static void main(String[] args) {

		WikiDataRetriever wdRetriever = new WikiDataRetriever();
		ArrayList<Pair<String, String>> wdPairs = wdRetriever
				.getAllPairs(SEARCH);

		printData(wdPairs);
	}
	

	private static void printData(ArrayList<Pair<String, String>> pairs) {
		for (Pair<String, String> pair : pairs) {
			System.out.println(pair);
		}
	}
}
