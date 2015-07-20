package org.complexsystems.agents;

import org.complexsystems.WikiDataGetModule;

import jade.core.Agent;

public class Winky  extends Agent {

	private static final long serialVersionUID = 1L;

	@Override
	protected void setup()  {
        System.out.println("Winky alla ricerca dei dati di wikidata");
        WikiDataGetModule wd = new WikiDataGetModule();
        

    }
}
