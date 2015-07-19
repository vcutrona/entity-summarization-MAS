package org.complexsystems;

import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFactory;

/**
 * Classe che effettua i collegamenti tra DBpedia e WikiData
 * in generale verrà usata per trovare i sameAs.
 * @author vinid
 *
 */
public class DBpediaWikiDataConnector {

	public static String findSameAsDBpediaToWikiData(String dbProp)
	{
		ParameterizedSparqlString qs = new ParameterizedSparqlString();

		QueryExecution exec = QueryExecutionFactory.sparqlService(
				"http://dbpedia.org/sparql", qs.asQuery());

		ResultSet results = ResultSetFactory.copyResults(exec.execSelect());

		while (results.hasNext()) {

		}
		
		return null;
	}
	
}
