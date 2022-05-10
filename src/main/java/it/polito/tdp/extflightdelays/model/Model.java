package it.polito.tdp.extflightdelays.model;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {

	private ExtFlightDelaysDAO dao= new ExtFlightDelaysDAO ();
	private Graph <Airport, DefaultWeightedEdge> grafo;
	List <Airport> aereoporti= dao.loadAllAirports();
	Map <Integer, Airport> map;
	
	public Model() {
		
		this.map = new HashMap <Integer, Airport>();
				for(Airport a: aereoporti) {
					map.put(a.getId(), a);
				}
		
	}





	public String creaGrafo(Integer distance) {
		this.grafo= new SimpleWeightedGraph<Airport, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		List <CoppiaId > coppie= dao.getAllMedieTratte(distance);
		Graphs.addAllVertices(this.grafo, aereoporti);
		System.out.println(this.grafo);
		String s1= "Vertici= " +this.grafo.vertexSet().size();
		
		for(Airport partenza: aereoporti) {
			for(Airport arrivo: aereoporti) {
				for(CoppiaId ci:coppie) {
					if(ci.getIdPartenza()==partenza.getId() && ci.getIdArrivo()==arrivo.getId()) {
				
						this.grafo.addEdge(partenza, arrivo);
						this.grafo.setEdgeWeight(partenza, arrivo, ci.getMedia());
					
					}
			}
		}
		
		}
		String s2="Archi= " +this.grafo.edgeSet().size();
		String s3=null;
		for(CoppiaId c: coppie) {
			s3+="Partenza: "+map.get(c.getIdPartenza()).getAirportName()+ "Arrivo : "+ map.get(c.getIdArrivo()).getAirportName()+c.getMedia()+" \n";
			
		}
		return s1+s2+s3;
		
	}
	
	}	
	
	
	


