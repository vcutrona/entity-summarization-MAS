package org.complexsystems.agents;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

import org.complexsystems.DBpediaGetModule;
import org.complexsystems.DBpediaWikiDataConnector;
import org.complexsystems.WikiDataGetModule;
import org.complexsystems.tools.Entity;
import org.complexsystems.tools.Pair;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.util.Set;

public class Connecty extends Agent {

	private class Aggregator {
		private String wikiDataDescription;
		private String DBpediaDescription;

		private ArrayList<Pair<String, String>> wdProp;
		private ArrayList<Pair<String, String>> dbProp;

		private ArrayList<Pair<ArrayList<Pair<String, String>>, ArrayList<Pair<String, String>>>> data;

		public Aggregator() {
			setWdProp(new ArrayList<Pair<String, String>>());
			setDbProp(new ArrayList<Pair<String, String>>());
			setData(new ArrayList<Pair<ArrayList<Pair<String, String>>, ArrayList<Pair<String, String>>>>());
		}

		public String getWikiDataDescription() {
			return wikiDataDescription;
		}

		public void setWikiDataDescription(String wikiDataDescription) {
			this.wikiDataDescription = wikiDataDescription;
		}

		public String getDBpediaDescription() {
			return DBpediaDescription;
		}

		public void setDBpediaDescription(String dBpediaDescription) {
			DBpediaDescription = dBpediaDescription;
		}

		public ArrayList<Pair<String, String>> getWdProp() {
			return wdProp;
		}

		public void setWdProp(ArrayList<Pair<String, String>> wdProp) {
			this.wdProp = wdProp;
		}

		public ArrayList<Pair<String, String>> getDbProp() {
			return dbProp;
		}

		public void setDbProp(ArrayList<Pair<String, String>> dbProp) {
			this.dbProp = dbProp;
		}

		public ArrayList<Pair<ArrayList<Pair<String, String>>, ArrayList<Pair<String, String>>>> getData() {
			return data;
		}

		public void setData(
				ArrayList<Pair<ArrayList<Pair<String, String>>, ArrayList<Pair<String, String>>>> data) {
			this.data = data;
		}

	}

	private static final long serialVersionUID = 1L;

	private Aggregator agg;
	String inputQuery;
	DataOutputStream response;

	@Override
	protected void setup() {

		agg = new Aggregator();
		
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(4309);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Socket clientSocket = null;
		
	    try {
	    	clientSocket = serverSocket.accept();

	        if(clientSocket != null)                
	        	System.out.println("Connected");
	        
	        InputStreamReader inputStream = new InputStreamReader(clientSocket.getInputStream());
            this.response = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader input = new BufferedReader(inputStream);
            
            this.inputQuery = input.readLine();
            System.out.println("The input query is: " + this.inputQuery);
     
        	} catch (IOException e) {
	    	System.out.println("Accept failed.");
	    }
		
		//Ci dovrebbe essere un modo per contattare più agenti insieme
		//Nota: il messaggio sarà la stringa di query
		
		//Contatto winky
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.addReceiver(new AID("Winky", AID.ISLOCALNAME));
		msg.setLanguage("English");
		msg.setOntology("Weather-Forecast-Ontology");
		msg.setContent(this.inputQuery);
		send(msg);
		
		//Contatto Debby
		msg = new ACLMessage(ACLMessage.INFORM);
		msg.addReceiver(new AID("Debby", AID.ISLOCALNAME));
		msg.setLanguage("English");
		msg.setOntology("Weather-Forecast-Ontology");
		msg.setContent(this.inputQuery);
		send(msg);
		
		addBehaviour(new CyclicBehaviour(this) 
        {
             public void action() 
             {
            	//ricevo il messaggio
                ACLMessage msg= receive();
                if (msg!=null)
					try {
						System.out.println( " - " +
								   myAgent.getLocalName() + " <- ha ricevuto un messaggio" );
						String sender = msg.getSender().getName();
						//Chiaramente questa cosa va sistemata con il corretto metodo per 
						//fare dispatch tra gli agenti...metodo che non trovo
						if(sender.contains("Winky"))
						{
							Entity wiki = ((Entity) msg.getContentObject());
							agg.setWikiDataDescription(wiki.getDescription());
							agg.setWdProp(wiki.getListOfPropertiesAndPairs());

						}
						else
						{
							Entity db = ((Entity) msg.getContentObject());

							agg.setDBpediaDescription(db.getDescription());
							agg.setDbProp(db.getListOfPropertiesAndPairs());
						}
						if(agg.getDbProp().size() != 0 && agg.getWdProp().size() != 0)
						{
							System.out.println("Check SameAs links");
							checkSameAsProperties();
					
							System.out.println("Check CustomSameAs links");
							checkCustomSameAsProperties();
							
				            String outputJson = "json";
				            
				            ObjectMapper mapper = new ObjectMapper();
				    		String jsonString = "";
				    		String jsonPrettyString = "";
				    		try {
				    			jsonString = mapper.writeValueAsString(agg.getData());
				    			jsonPrettyString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(agg.getData());
				    		} catch (JsonGenerationException e) {
				    	         e.printStackTrace();
				    		} catch (JsonMappingException e) {
				    	         e.printStackTrace();
				    	    } catch (IOException e) {
				    	         e.printStackTrace();
				    	    }
				            
				    		response.writeBytes(jsonString);
				    		response.flush();
				    		response.close();
							
						}
						
					} catch (UnreadableException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                block();
             }
        });
		
		/*
		try {
			
			ServerSocket serverSocket = new ServerSocket(4309);
			Socket clientSocket = null;
			
		    try {
		    	clientSocket = serverSocket.accept();
	
		        if(clientSocket != null)                
		        	System.out.println("Connected");
		        
		        InputStreamReader inputStream = new InputStreamReader(clientSocket.getInputStream());
                DataOutputStream response = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader input = new BufferedReader(inputStream);
                
                String inputQuery = input.readLine();
                System.out.println("The input query is: " + inputQuery);
                
                String outputJson = "json";
                response.writeBytes(outputJson);
                response.flush();
                response.close();
                
	
		    } catch (IOException e) {
		    	System.out.println("Accept failed.");
		    }

/*
		    out.println("HTTP/1.1 200 OK");
		    out.println("Content-Type: text/html");
		    out.println("\r\n");
		    out.println("<p> Hello world </p>");
		    out.flush();
*/

		/*
			System.out.println("Ask entities");
			Entity wiki = askWikiDataEntity();
			Entity db = askDBpediaEntity();

			agg = new Aggregator();
			agg.setDBpediaDescription(db.getDescription());
			agg.setWikiDataDescription(wiki.getDescription());
			agg.setWdProp(wiki.getListOfPropertiesAndPairs());
			agg.setDbProp(db.getListOfPropertiesAndPairs());

			System.out.println("Check SameAs links");
			checkSameAsProperties();
	
			System.out.println("Check CustomSameAs links");
			checkCustomSameAsProperties();
			
			System.out.println("End");
			*/
			
		    //out.close();
	/*
		} catch (IOException e) {
			System.out.println("Fail!: " + e.toString());
		}
		*/

	}

	private void checkSameAsProperties() {
		DBpediaWikiDataConnector c = new DBpediaWikiDataConnector();

		for (Pair<String, String> pair : agg.dbProp) {
			if (pair.getProperty().isEmpty())
				continue;

			String wdPropertyUri = c.findSameAsDBpediaToWikiData(pair
					.getProperty());

			if (wdPropertyUri == null)
				continue;

			for (Pair<String, String> pair2 : agg.wdProp) {
				if (pair2.getUri().equals(wdPropertyUri)) {

					ArrayList<Pair<String, String>> tempWd = new ArrayList<Pair<String, String>>();
					for (Pair<String, String> pair3 : agg.wdProp) {
						if (pair3.getUri().equals(wdPropertyUri)) {
							tempWd.add(pair3);
							// removalPair2.add(pair3);
							// agg.wdProp.remove(pair3);
						}
					}

					ArrayList<Pair<String, String>> tempDb = new ArrayList<Pair<String, String>>();
					for (Pair<String, String> pair3 : agg.dbProp) {
						if (pair3.getProperty().equals(pair.getProperty())) {
							tempDb.add(pair3);
							// removalPair3.add(pair3);
							// agg.dbProp.remove(pair3);
						}
					}

					Pair<ArrayList<Pair<String, String>>, ArrayList<Pair<String, String>>> newInsert = new Pair<ArrayList<Pair<String, String>>, ArrayList<Pair<String, String>>>(
							tempWd, tempDb);
					agg.getData().add(newInsert);
				}
			}
		}

		// Rimozione dei duplicati
		Set<Pair<ArrayList<Pair<String, String>>, ArrayList<Pair<String, String>>>> hs = new HashSet<Pair<ArrayList<Pair<String, String>>, ArrayList<Pair<String, String>>>>();
		hs.addAll(agg.getData());
		agg.getData().clear();
		agg.getData().addAll(hs);

		for (Pair<ArrayList<Pair<String, String>>, ArrayList<Pair<String, String>>> pair : agg.getData()) {
			System.out.println(pair);
		}

	}
	
	private void checkCustomSameAsProperties() {
		DBpediaWikiDataConnector c = new DBpediaWikiDataConnector();
		
		for (Pair<String, String> wdPair : agg.wdProp) {
			for (Pair<String, String> dbPair : agg.dbProp) {
				String wdProperty = wdPair.getProperty();
				String dbProperty = cleanDBpediaProperty(dbPair.getProperty());
				
				//System.out.println("-----");
				//System.out.println(wdProperty + " <-> " + dbProperty);
				
				if (c.customSameAs(wdProperty, dbProperty) == 1) {//ho le stesse parole, magari in ordine diverso
										
					ArrayList<Pair<String, String>> tempWd = new ArrayList<Pair<String, String>>();
					for (Pair<String, String> pair : agg.wdProp) {
						if (pair.getProperty().equals(wdProperty)) {
							tempWd.add(pair);
						}
					}

					ArrayList<Pair<String, String>> tempDb = new ArrayList<Pair<String, String>>();
					for (Pair<String, String> pair : agg.dbProp) {
						if (cleanDBpediaProperty(pair.getProperty()).equals(dbProperty)) {
							tempDb.add(pair);
						}
					}

					Pair<ArrayList<Pair<String, String>>, ArrayList<Pair<String, String>>> newInsert = new Pair<ArrayList<Pair<String, String>>, ArrayList<Pair<String, String>>>(
							tempWd, tempDb);
					agg.getData().add(newInsert);
				}
			}
		}
		
		// Rimozione dei duplicati
		Set<Pair<ArrayList<Pair<String, String>>, ArrayList<Pair<String, String>>>> hs = new HashSet<Pair<ArrayList<Pair<String, String>>, ArrayList<Pair<String, String>>>>();
		hs.addAll(agg.getData());
		agg.getData().clear();
		agg.getData().addAll(hs);
		
		for (Pair<ArrayList<Pair<String, String>>, ArrayList<Pair<String, String>>> pair : agg.getData()) {
			System.out.println(pair);
		}
		
		/**
		 * Genero il json
		 */
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "";
		String jsonPrettyString = "";
		try {
			jsonString = mapper.writeValueAsString(agg.getData());
			jsonPrettyString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(agg.getData());
		} catch (JsonGenerationException e) {
	         e.printStackTrace();
		} catch (JsonMappingException e) {
	         e.printStackTrace();
	    } catch (IOException e) {
	         e.printStackTrace();
	    }
		System.out.println(jsonString);
		System.out.println(jsonPrettyString);
	}
	
	private String cleanDBpediaProperty(String property){
		if (property.contains("/")) //es. http://dbpedia.org/ontology/team
			property = property.substring(property.lastIndexOf("/")+1); //ottengo team
		if (property.contains("#")) //es. http://www.w3.org/1999/02/22-rdf-syntax-ns#type
			property = property.substring(property.lastIndexOf("#")+1); //ottengo type
		return property;
	}
}
