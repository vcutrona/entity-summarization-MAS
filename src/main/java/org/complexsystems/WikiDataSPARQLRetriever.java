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
				+ "prefix rdfs:" + RDFSCHEMAPREFIX + "\n\n"
				+ "prefix wd:" + WDPREFIX + "\n\n"
				+ "prefix wdt:" + WDTPREFIX + "\n\n"
				+ "prefix wikibase:" + WIKIBASEPEFIX + "\n\n"
				+ " SELECT ?labelProperty ?property ?labelObject ?object WHERE { "
				+ "  wd:Q42 ?p ?object."
				+ "  ?property ?ref ?p ."
				+ "  ?property a wikibase:Property ."
				+ "  ?property rdfs:label ?labelProperty FILTER (lang(?labelProperty) = \"en\")"
				+ "  ?object rdfs:label ?labelObject FILTER (lang(?labelObject) = \"en\")"
				+ " } ");

		System.out.println(qs.asQuery());
		
		QueryExecution exec = QueryExecutionFactory.sparqlService(
				SPARQLSERVICE, qs.asQuery());
		ResultSet results = ResultSetFactory.copyResults(exec.execSelect());

		while (results.hasNext()) {
			
			QuerySolution node = results.next();
			RDFNode propNode = node.get("property");
			RDFNode objNode = node.get("object");			
			RDFNode propLabelNode = node.get("labelProperty");
			RDFNode objLabelNode = node.get("labelObject");
			
			
			
			String prop = propNode.toString();
			String obj = objNode.toString();
			String propLabel = propLabelNode.toString();
			String objLabel = objLabelNode.toString();
			
			
			Pair<String, String> pair = new Pair<String, String>(propLabel, objLabel);
			pair.setUriProperty(prop);
			pair.setUriObject(obj);
			list.add(pair);
		}
		return list;

	}

	@Override
	public String getDescription(String text) {
		// TODO Auto-generated method stub
		return "Miao";
	}

}
