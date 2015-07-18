package org.complexsystems;

import java.util.ArrayList;

import org.complexsystems.interfaces.Retriever;
import org.wikidata.wdtk.datamodel.interfaces.Claim;

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


	public DBpediaRetriever() {
	}

	@Override
	public ArrayList<Pair<String, String>> getAllPairs(String searchString) {
		// ArrayList dove salvare le pair
		ArrayList<Pair<String, String>> list = new ArrayList<Pair<String, String>>();

		ParameterizedSparqlString qs = new ParameterizedSparqlString(""
				+ "prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "\n" + "select ?prop ?obj where {\n" + "  " + searchString
				+ " ?prop ?obj\n" + " }");


		QueryExecution exec = QueryExecutionFactory.sparqlService(
				"http://dbpedia.org/sparql", qs.asQuery());

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

	public static void main(String[] args) {

	}

}
