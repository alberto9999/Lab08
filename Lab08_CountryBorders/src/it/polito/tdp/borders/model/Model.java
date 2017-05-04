package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	private		 Map <Integer, Country>mappaTotCountry ;
	private		ArrayList<Border>listaBordersAnno;
	private		UndirectedGraph <Country,Border> grafoConfini;

	public Model() {	
		BordersDAO bDAO = new BordersDAO();	
	    mappaTotCountry=  new HashMap<Integer,Country>( bDAO.loadAllCountries());
	}

	public String calcolaConfini(int anno) {	
		BordersDAO bDAO = new BordersDAO();
		listaBordersAnno=new ArrayList<Border>(bDAO.getCountryPairs(anno));
		generaGrafo(listaBordersAnno);
		
		String result="";
		for(Country c: grafoConfini.vertexSet()){
			result+= c+"         numero di confini:"+grafoConfini.degreeOf(c)+"\n";
		}
		result+= "\n\nNUMERO COMPONENTI CONNESSE : "+ numeroComponentiConnesse();
		result+= "\n\nNUMERO  DEI PAESI  NELLA MASSIMA COMPONENTE CONNESSA: "+ numeroMassimoConnessioni();
		return result;
	}




	

	private void generaGrafo(ArrayList<Border> listaBordersAnno) {
		 grafoConfini=new SimpleGraph<Country,Border>(Border.class);
		
		 
		 for(int i: mappaTotCountry.keySet()){
			 Country c= mappaTotCountry.get(i);
		   if(!grafoConfini.containsVertex(c)){
			   grafoConfini.addVertex(c);
		   }
		 }
		 
		 for(Border b : listaBordersAnno){
		Country c1= mappaTotCountry.get(b.getState1no());
	    Country c2= mappaTotCountry.get(b.getState2no());	
			 if(!grafoConfini.containsEdge(b)){	 
			     grafoConfini.addEdge(c1,c2,b);
			 
			 }
		 } 
	}

	private int numeroMassimoConnessioni() {
		int result=0;
		ConnectivityInspector<Country, Border> ci = new ConnectivityInspector<Country, Border>(grafoConfini);
		List <Set<Country>>list= new ArrayList<Set<Country>>(ci.connectedSets());
		for(Set<Country> set : list){
			if(set.size()>result){
			result=set.size();}
		}
		return result;
	}
	private int numeroComponentiConnesse() {
		ConnectivityInspector<Country, Border> ci = new ConnectivityInspector<Country, Border>(grafoConfini);
		List <Set<Country>>list= new ArrayList<Set<Country>>(ci.connectedSets());
		return list.size();
	}
	
	
}



