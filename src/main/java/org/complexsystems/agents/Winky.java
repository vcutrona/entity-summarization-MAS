package org.complexsystems.agents;

import java.io.IOException;

import org.complexsystems.WikiDataGetModule;
import org.complexsystems.tools.Entity;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;

public class Winky extends Agent {

	private static final long serialVersionUID = 1L;

	@Override
	protected void setup() {
		
		//aggiungo una behavior
		addBehaviour(new CyclicBehaviour(this) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void action() {
				ACLMessage msg = receive();
				if (msg != null) {
					System.out.println(" - " + myAgent.getLocalName() + " <- "
							+ "ha ricevuto un messaggio");
										
				    ACLMessage reply = new ACLMessage( ACLMessage.INFORM );
				    try {
						reply.setContentObject( getWikiDataEntity(msg.getContent())  );
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    reply.addReceiver( msg.getSender() );
				    send(reply);
					
				}
				block();
			}
		});
	}
	
	private Entity getWikiDataEntity(String query) {
		WikiDataGetModule wd = new WikiDataGetModule();
		Entity wiki = wd.getData(query);
		
		return wiki;
	}
}
