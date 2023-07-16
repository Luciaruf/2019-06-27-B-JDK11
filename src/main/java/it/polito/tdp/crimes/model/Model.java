package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	EventsDao dao;
	Graph<String, DefaultWeightedEdge> graph;

	public Model() {
		this.dao = new EventsDao();
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	}
	
	public List<String> getCategories(){
		return this.dao.getCategories();
	}
	
	
	public List<Integer> getMonths(){
		return this.dao.getMonths();
	}
	
	public Graph creaGrafo(String categoryID, Integer month) {
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.graph, this.dao.getVertices(categoryID, month));
		
		for(String s1 : this.graph.vertexSet()) {
			for(String s2 : this.graph.vertexSet()) {
				if(s1.compareTo(s2)!=0) {
					
					List<String> neig1 = this.dao.getNeigh(s1, month);
					List<String> neig2 = this.dao.getNeigh(s2, month);
					
					List<String> uguali = new ArrayList<>();
					
					for(String n1 : neig1) {
						for(String n2 : neig2) {
							if(n1.compareTo(n2)==0) {
								uguali.add(n1);
							}
						}
					}
					
					if(uguali.size()!=0) {
						Graphs.addEdge(this.graph, s1, s2, uguali.size());
					}
					
				}
			}
		}
		
		return this.graph;
		
		
	}
	
	
}
