package org.complexsystems.agents;

import java.util.ArrayList;

import org.complexsystems.WikiDataGetModule;
import org.complexsystems.tools.Pair;

import jade.core.Agent;

public class Winky  extends Agent {

	private static final long serialVersionUID = 1L;

	@Override
	protected void setup()  {
        System.out.println("Winky alla ricerca dei dati di wikidata");
        WikiDataGetModule wd = new WikiDataGetModule();
        

    }
}
