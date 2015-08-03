package org.complexsystems;

import java.util.ArrayList;

import org.complexsystems.interfaces.Retriever;
import org.complexsystems.tools.Pair;

import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;

public class WikiDataSPARQLRetriever implements Retriever {

	private static final String RDFSCHEMAPREFIX = 
			"<http://www.w3.org/2000/01/rdf-schema#>";
	private static final String WDPREFIX ="<http://www.wikidata.org/entity/>";
	private static final String WDTPREFIX ="<http://www.wikidata.org/entity/>";
	private static final String WIKIBASEPEFIX ="<http://wikiba.se/ontology#>";
	private static final String SPARQLSERVICE = "http://wdqs-beta.wmflabs.org/bigdata/namespace/wdq/sparql/";

	public WikiDataSPARQLRetriever() {
	}

	@Override
	public ArrayList<Pair<String, String>> getAllPairs(String searchString) {
		// ArrayList dove salvare le pair
		ArrayList<Pair<String, String>> list = 
				new ArrayList<Pair<String, String>>();

		ParameterizedSparqlString qs = new ParameterizedSparqlString(""
				+ "prefix rdfs:" + RDFSCHEMAPREFIX + "\n"
				+ "prefix wd:" + WDPREFIX + "\n"
				+ "prefix wdt:" + WDTPREFIX + "\n"
				+ "prefix wikibase:" + WIKIBASEPEFIX + "\n"
				+ "SELECT ?property ?statementURI ?statQual ?statQualLabel ?object ?objectLabel WHERE { wd:Q42 ?p ?statementURI ."
				+ "?property ?ref ?p ."
				+ "FILTER regex(str(?statementURI), \"statement\") ."
				+ "?statementURI ?statQual ?object . ?object rdfs:label ?objectLabel ."
				+ "FILTER (lang(?objectLabel) = \"en\") ."
				+ " OPTIONAL {"
				+ "      ?ak ?bk ?statQual ."
				+ "      ?ak rdfs:label ?statQualLabel ."
				+ "      FILTER (lang(?statQualLabel) = \"en\") .\n"
				+ "}}");

		System.out.println(qs.asQuery());
		
		QueryExecution exec = QueryExecutionFactory.sparqlService(
				SPARQLSERVICE, qs.asQuery());
		ResultSet results = ResultSetFactory.copyResults(exec.execSelect());

		while (results.hasNext()) {
						
			QuerySolution node = results.next();
			RDFNode propNode = node.get("property");
			RDFNode objectNode = node.get("object");			
			RDFNode objectLabelNode = node.get("objectLabel");
			RDFNode statementURINode = node.get("statementURI");
			RDFNode statQualNode = node.get("statQual");
			RDFNode statQualLabelNode = node.get("statQualLabel");
			
			String prop = propNode.toString();
			String object = objectNode.toString();
			String statQualLabel = statQualLabelNode.toString();
			String objectLabel = objectLabelNode.toString();
			
			
			Pair<String, String> pair = new Pair<String, String>(statQualLabel, objectLabel);
			pair.setUriProperty(prop);
			pair.setUriObject(object);
			list.add(pair);
		}
		return list;

	}

	@Override
	public String getDescription(String text) {
		// TODO Auto-generated method stub
		return "Miao";
	}
	
	public static void main(String [] args) {
		WikiDataSPARQLRetriever w = new WikiDataSPARQLRetriever();
		System.out.println(w.getAllPairs("belo"));
	}

}
