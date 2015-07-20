package org.complexsystems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFactory;

/**
 * Classe che effettua i collegamenti tra le proprietà di DBpedia e WikiData
 * @author vinid
 */
public class DBpediaWikiDataConnector {

	/**
	 *  PREFIX owl: <http://www.w3.org/2002/07/owl#>
		PREFIX dbo: <http://dbpedia.org/ontology/>
		SELECT ?prop WHERE {
			dbo:team owl:equivalentProperty ?prop
		}
		risultato: http://www.wikidata.org/entity/P54	
	*/
	public String findSameAsDBpediaToWikiData(String dbProp)
	{
		try {
			ParameterizedSparqlString qs = new ParameterizedSparqlString("PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
			+ "PREFIX dbo: <http://dbpedia.org/ontology/>\n"
			+ "SELECT ?prop WHERE {"
			+ "dbo:" + dbProp.replaceAll("http://dbpedia.org/ontology/", "") + " owl:equivalentProperty ?prop "
			+ "FILTER regex(?prop, 'wikidata') } ");
	
	
			QueryExecution exec = QueryExecutionFactory.sparqlService(
					"http://dbpedia.org/sparql", qs.asQuery());
	
			ResultSet results = ResultSetFactory.copyResults(exec.execSelect());
	
			while (results.hasNext()) {
				return (results.next().get("prop").toString());
			}
		
		} catch (Exception e) {
			
		}
	
		return null;
	}
	
	public int minDistance(String word1, String word2) {
		int len1 = word1.length();
		int len2 = word2.length();
	 
		// len1+1, len2+1, because finally return dp[len1][len2]
		int[][] dp = new int[len1 + 1][len2 + 1];
	 
		for (int i = 0; i <= len1; i++) {
			dp[i][0] = i;
		}
	 
		for (int j = 0; j <= len2; j++) {
			dp[0][j] = j;
		}
	 
		//iterate though, and check last char
		for (int i = 0; i < len1; i++) {
			char c1 = word1.charAt(i);
			for (int j = 0; j < len2; j++) {
				char c2 = word2.charAt(j);
	 
				//if last two chars equal
				if (c1 == c2) {
					//update dp value for +1 length
					dp[i + 1][j + 1] = dp[i][j];
				} else {
					int replace = dp[i][j] + 1;
					int insert = dp[i][j + 1] + 1;
					int delete = dp[i + 1][j] + 1;
	 
					int min = replace > insert ? insert : replace;
					min = delete > min ? min : delete;
					dp[i + 1][j + 1] = min;
				}
			}
		}
	 
		return dp[len1][len2];
	}

	/*
	 * se ho placeOfBirth e birthPlace, voglio avere bassa edit distance
	 * Otteniamo una percentuale di parole uguali, da utilizzare in qualche
	 * modo sull'edit distance
	 */
	public double customSameAs(String s1, String s2) {
		s1 = splitCamelCase(s1);
		s2 = splitCamelCase(s2);
		
		List<String> w1 = Arrays
				.asList(StringUtils.splitPreserveAllTokens(s1, null, 0));
		List<String> w2 = Arrays
				.asList(StringUtils.splitPreserveAllTokens(s2, null, 0));
		
		List<String> common = new ArrayList<String>(w1);
		common.retainAll(w2);
		
		double result = 0;
		if (w1.size() > w2.size())
			result = (double) common.size() / w1.size();
		else
			result = (double) common.size() / w2.size();
		
		return result;
	}
	
	/**
	 * Metodo per creare una stringa con le parole separate, partendo
	 * da una stringa in camelCase
	 * @param s
	 * @return
	 */
	private String splitCamelCase(String s) {
		if (s.contains(" ")) //no camelCase word
			return s.toLowerCase().trim();
		
		return StringUtils.join(
				StringUtils.splitByCharacterTypeCamelCase(s), " ")
				.toLowerCase().trim();
	}
	
	public static void main(String[] args) {
		//http://www.w3.org/1999/02/22-rdf-syntax-ns#type
		//property= http://www.w3.org/2002/07/owl#sameAs
		//property= http://dbpedia.org/property/clubs
		//property= http://dbpedia.org/ontology/birthPlace
		//property= http://dbpedia.org/ontology/birthYear
		
		
		DBpediaWikiDataConnector c = new DBpediaWikiDataConnector();
		System.out.println(c.findSameAsDBpediaToWikiData("birthYear"));
	}
}
