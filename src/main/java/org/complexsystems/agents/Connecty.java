package org.complexsystems.agents;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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

import jade.core.Agent;

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

	@Override
	protected void setup() {

		//try {
			
			/*
			ServerSocket serverSocket = new ServerSocket(62666);

		      Socket clientSocket = null; 
		       try {
		            clientSocket = serverSocket.accept();

		            if(clientSocket != null)                
		                System.out.println("Connected");

		       } catch (IOException e) {
		             System.err.println("Accept failed.");
		             System.exit(1);
		      }

		     PrintWriter out = new PrintWriter(clientSocket.getOutputStream());


		    out.println("HTTP/1.1 200 OK");
		    out.println("Content-Type: text/html");
		    out.println("\r\n");
		    out.println("<p> Hello world </p>");
		    out.flush();
			*/

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

			System.out.println("End");
			
		    //out.close();
	
		//} catch (IOException e) {
		//	e.printStackTrace();
		//}

	}

	private Entity askWikiDataEntity() {
		WikiDataGetModule wd = new WikiDataGetModule();
		Entity wiki = wd.getData();

		return wiki;
	}

	private Entity askDBpediaEntity() {
		DBpediaGetModule dbd = new DBpediaGetModule();
		Entity db = dbd.getData();

		return db;
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

		for (Pair<ArrayList<Pair<String, String>>, ArrayList<Pair<String, String>>> pair : agg
				.getData()) {
			System.out.println(pair);
		}

	}
}
