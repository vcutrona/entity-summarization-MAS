package org.complexsystems;

import java.util.HashMap;
import java.util.Iterator;

import org.wikidata.wdtk.datamodel.interfaces.Claim;
import org.wikidata.wdtk.datamodel.interfaces.EntityDocument;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.datamodel.interfaces.ItemIdValue;
import org.wikidata.wdtk.datamodel.interfaces.PropertyDocument;
import org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue;
import org.wikidata.wdtk.datamodel.interfaces.Statement;
import org.wikidata.wdtk.datamodel.interfaces.Value;
import org.wikidata.wdtk.datamodel.interfaces.ValueSnak;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher;

public class WikiDataRetriever {

	private WikibaseDataFetcher wbdf;

	public WikiDataRetriever() {
		this.wbdf = new WikibaseDataFetcher();
	}

	public ArrayList<String, String> getAll(String searchString) {
		EntityDocument document = wbdf.getEntityDocumentByTitle("enwiki",
				searchString);
		HashMap <String, String> map  = new HashMap();

		if (document instanceof ItemDocument) {

			Iterator<Statement> it = ((ItemDocument) document)
					.getAllStatements();
			while (it.hasNext()) {
				Statement st = it.next();
				Claim claim = st.getClaim();
				map.put(arg0, arg1)
			}
		}

		return null;
	}

	public String getProperty(Claim claim) {

		ValueSnak snk = (ValueSnak) claim.getMainSnak();

		PropertyIdValue prp = snk.getPropertyId();

		PropertyDocument property = (PropertyDocument) wbdf
				.getEntityDocument(prp.getId());
		String propertyText = property.getLabels().get("en").getText();
		return (propertyText);

	}

	public String getObject(Claim claim) {

		ValueSnak snk = (ValueSnak) claim.getMainSnak();
		Value v = snk.getValue();
		String returned = "";

		// "Q1731" is "Dresden" on Wikidata
		if (v instanceof ItemIdValue) {
			EntityDocument et = wbdf.getEntityDocument(((ItemIdValue) v)
					.getId());
			returned = (((ItemDocument) et).getLabels().get("en").getText());
		} else {
			returned = v.toString();
		}

		return returned;

	}

}
