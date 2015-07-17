package org.complexsystems;


import java.util.Iterator;

import org.wikidata.wdtk.datamodel.implementation.PropertyDocumentImpl;
import org.wikidata.wdtk.datamodel.interfaces.Claim;
import org.wikidata.wdtk.datamodel.interfaces.EntityDocument;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.datamodel.interfaces.ItemIdValue;
import org.wikidata.wdtk.datamodel.interfaces.PropertyDocument;
import org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue;
import org.wikidata.wdtk.datamodel.interfaces.Reference;
import org.wikidata.wdtk.datamodel.interfaces.Snak;
import org.wikidata.wdtk.datamodel.interfaces.Statement;
import org.wikidata.wdtk.datamodel.interfaces.Value;
import org.wikidata.wdtk.datamodel.interfaces.ValueSnak;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher;
import  org.wikidata.wdtk.datamodel.json.jackson.JacksonPropertyDocument;


public class WikiDataGetModule {

	static final String SEARCH = "Alessadro Del Piero";
	
public static void main(String[] args) {
		

		WikibaseDataFetcher wbdf = new WikibaseDataFetcher();

		EntityDocument q42 = wbdf.getEntityDocumentByTitle("enwiki", "Alessandro Del Piero");
		if (q42 instanceof ItemDocument) {
			
			//Recupero l'iteratore con tutti gli statement
			Iterator <Statement> it = ((ItemDocument) q42).getAllStatements();
			
			while (it.hasNext())
			{
				//Estraggo lo statement
				Statement st = it.next();
				//Ora estraggo i reference di ogni statement
				
				Claim claim = st.getClaim();
				ValueSnak snk = (ValueSnak) claim.getMainSnak();
				Value v  = snk.getValue();
				

				
				PropertyIdValue prp = snk.getPropertyId();
				
				PropertyDocument property = (PropertyDocument) wbdf.getEntityDocument(prp.getId());
				String propertyText = property.getLabels().get("en").getText();
				System.out.print(propertyText);
				

				System.out.print(" -> ");
				
				// "Q1731" is "Dresden" on Wikidata
				if (v instanceof ItemIdValue) {
					EntityDocument et  = wbdf.getEntityDocument(((ItemIdValue) v).getId());
					System.out.println(((ItemDocument) et).getLabels().get("en").getText());
				} else
				{
					System.out.println(v);
				}
			}
		}
		
}
}
