package org.complexsystems;

import java.util.ArrayList;

import org.complexsystems.interfaces.Retriever;

import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;

/**
 * Classe che implementa l'interfaccia Retriever, per recuperare i dati dalla
 * base di conoscenza DBpedia.
 */
public class DBpediaRetriever implements Retriever {

	private static final String RDFSCHEMAPREFIX = 
			"<http://www.w3.org/2000/01/rdf-schema#>";
	private static final String SPARQLSERVICE = "http://dbpedia.org/sparql";

	public DBpediaRetriever() {
	}

	@Override
	public ArrayList<Pair<String, String>> getAllPairs(String searchString) {
		// ArrayList dove salvare le pair
		ArrayList<Pair<String, String>> list = 
				new ArrayList<Pair<String, String>>();

		ParameterizedSparqlString qs = new ParameterizedSparqlString(""
				+ "prefix rdfs:" + RDFSCHEMAPREFIX + "\n\n"
				+ "select ?prop ?obj where {\n" 
				+ "  " + stringToResource(searchString)
				+ " ?prop ?obj\n"
				+ " }");

		QueryExecution exec = QueryExecutionFactory.sparqlService(
				SPARQLSERVICE, qs.asQuery());

		ResultSet results = ResultSetFactory.copyResults(exec.execSelect());

		while (results.hasNext()) {
			RDFNode propNode = results.next().get("prop");
			RDFNode objNode = results.next().get("obj");

			String prop = propNode.toString();
			String obj = objNode.toString();

			Pair<String, String> pair = new Pair<String, String>(prop, obj);
			list.add(pair);
		}
		return list;

	}

	private static String stringToResource (String searchString) {
		String resource = "<http://dbpedia.org/resource/"
				+ searchString.replaceAll(" ", "_")
				+ ">";
		return resource;
	}
	
	public static void main(String[] args) {

	}
	
}
