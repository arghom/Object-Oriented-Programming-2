package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * <P>This class represents a general "directed graph", which could 
 * be used for any purpose.  The graph is viewed as a collection 
 * of vertices, which are sometimes connected by weighted, directed
 * edges.</P> 
 * 
 * <P>This graph will never store duplicate vertices.</P>
 * 
 * <P>The weights will always be non-negative integers.</P>
 * 
 * <P>The WeightedGraph will be capable of performing three algorithms:
 * Depth-First-Search, Breadth-First-Search, and Djikatra's.</P>
 * 
 * <P>The Weighted Graph will maintain a collection of 
 * "GraphAlgorithmObservers", which will be notified during the
 * performance of the graph algorithms to update the observers
 * on how the algorithms are progressing.</P>
 */
public class WeightedGraph<V> {

	/* STUDENTS:  You decide what data structure(s) to use to
	 * implement this class.
	 * 
	 * You may use any data structures you like, and any Java 
	 * collections that we learned about this semester.  Remember 
	 * that you are implementing a weighted, directed graph.
	 */
	
	/**
	 * Map that contains the FROM vertex as V
	 * TO vertex as the inner Map's V
	 * WEIGHT between those to as the Integer value 
	 */
	Map<V, Map<V,Integer>> WeightedGraph;
	
	
	
	/* Collection of observers.  Be sure to initialize this list
	 * in the constructor.  The method "addObserver" will be
	 * called to populate this collection.  Your graph algorithms 
	 * (DFS, BFS, and Dijkstra) will notify these observers to let 
	 * them know how the algorithms are progressing.  
	 */
	private Collection<GraphAlgorithmObserver<V>> observerList;
	

	/** Initialize the data structures to "empty", including
	 * the collection of GraphAlgorithmObservers (observerList).
	 */
	public WeightedGraph() { 
		WeightedGraph = new HashMap<V, Map<V,Integer>>();
		//initializes observerList as a new empty ArrayList
		observerList = new ArrayList<>();
	}

	/** Add a GraphAlgorithmObserver to the collection maintained
	 * by this graph (observerList).
	 * 
	 * @param observer
	 */
	public void addObserver(GraphAlgorithmObserver<V> observer) {
		observerList.add(observer);
	}

	/** Add a vertex to the graph.  If the vertex is already in the
	 * graph, throw an IllegalArgumentException.
	 * 
	 * @param vertex vertex to be added to the graph
	 * @throws IllegalArgumentException if the vertex is already in
	 * the graph
	 */
	public void addVertex(V vertex) {
		//if the vertex is already in WeightedGraph as a key that means it has already been placed in the graph
		if(WeightedGraph.containsKey(vertex)) {
			throw new IllegalArgumentException();
		}
		//add the vertex as a key for the WeightedGraph and initialize the HashMap for that key 
		WeightedGraph.put(vertex, new HashMap<V,Integer>());
		return;
	}
	
	/** Searches for a given vertex.
	 * 
	 * @param vertex the vertex we are looking for
	 * @return true if the vertex is in the graph, false otherwise.
	 */
	public boolean containsVertex(V vertex) {
		//if the vertex is in the WeightedGraph as a key then the vertex is in the graph
		if(WeightedGraph.containsKey(vertex)) {
			return true;
		}
		return false;
	}

	/** 
	 * <P>Add an edge from one vertex of the graph to another, with
	 * the weight specified.</P>
	 * 
	 * <P>The two vertices must already be present in the graph.</P>
	 * 
	 * <P>This method throws an IllegalArgumentExeption in three
	 * cases:</P>
	 * <P>1. The "from" vertex is not already in the graph.</P>
	 * <P>2. The "to" vertex is not already in the graph.</P>
	 * <P>3. The weight is less than 0.</P>
	 * 
	 * @param from the vertex the edge leads from
	 * @param to the vertex the edge leads to
	 * @param weight the (non-negative) weight of this edge
	 * @throws IllegalArgumentException when either vertex
	 * is not in the graph, or the weight is negative.
	 */
	public void addEdge(V from, V to, Integer weight) {
		//makes sure weight is not negative and that both the from and to verticies are in the graph
		if(weight < 0 || !WeightedGraph.containsKey(from) || !WeightedGraph.containsKey(to)) {
			throw new IllegalArgumentException();
		}
		//otherwise get the FROM vertex and insert the TO and the WEIGHT into its map
		WeightedGraph.get(from).put(to, weight);
	}

	/** 
	 * <P>Returns weight of the edge connecting one vertex
	 * to another.  Returns null if the edge does not
	 * exist.</P>
	 * 
	 * <P>Throws an IllegalArgumentException if either
	 * of the vertices specified are not in the graph.</P>
	 * 
	 * @param from vertex where edge begins
	 * @param to vertex where edge terminates
	 * @return weight of the edge, or null if there is
	 * no edge connecting these vertices
	 * @throws IllegalArgumentException if either of
	 * the vertices specified are not in the graph.
	 */
	public Integer getWeight(V from, V to) {
		//make sure that the from and to verticies are in the graph
		if(!WeightedGraph.containsKey(from) || !WeightedGraph.containsKey(to)) {
			throw new IllegalArgumentException();
		}
		//if the pair does not exist in the graph then the edge doesn't exist so return null
		if(!WeightedGraph.get(from).containsKey(to)) {
			return null;
		}
		//return the weight of the from and to pair of verticies
		return WeightedGraph.get(from).get(to);
	}

	/** 
	 * <P>This method will perform a Breadth-First-Search on the graph.
	 * The search will begin at the "start" vertex and conclude once
	 * the "end" vertex has been reached.</P>
	 * 
	 * <P>Before the search begins, this method will go through the
	 * collection of Observers, calling notifyBFSHasBegun on each
	 * one.</P>
	 * 
	 * <P>Just after a particular vertex is visited, this method will
	 * go through the collection of observers calling notifyVisit
	 * on each one (passing in the vertex being visited as the
	 * argument.)</P>
	 * 
	 * <P>After the "end" vertex has been visited, this method will
	 * go through the collection of observers calling 
	 * notifySearchIsOver on each one, after which the method 
	 * should terminate immediately, without processing further 
	 * vertices.</P> 
	 * 
	 * @param start vertex where search begins
	 * @param end the algorithm terminates just after this vertex
	 * is visited
	 */
	public void DoBFS(V start, V end) {
		//goes through collection of observers and notifies BFS begun
		for(GraphAlgorithmObserver<V> observer : observerList) {
			observer.notifyBFSHasBegun();
		}
		//create the visitedSet and the vertexQ queue 
		Set<V> visitedSet = new HashSet<V>();
		Queue<V> vertexQ = new LinkedList<V>();
		//add the first vertex to the queue
		vertexQ.add(start);
		//continues until the queue is empty so it will keep going until it hits the target
		while(!vertexQ.isEmpty()) {
			//take the vertex out of the queue and set it to tempVertex to be used for this iteration 
			V tempVertex = vertexQ.remove();
			//if the tempVertex is already in the visitedSet then it has already been processed so need to make sure
			//its not
			if(!visitedSet.contains(tempVertex)) {
				//if the tempVertex is the target or end vertex
				if(tempVertex.equals(end)) {
					//notify all the observers that the search is over
					for(GraphAlgorithmObserver<V> observer : observerList) {
						observer.notifySearchIsOver();
					}
					return;
				}
				//if code gets to this part then more searching needs to be done but this vertex is done
				//being processed
				visitedSet.add(tempVertex);
				//for every vertex that this vertex is connected to 
				for(V neighborVertex : WeightedGraph.get(tempVertex).keySet()) {
					//if the current connected vertex is already in the visited set we don't need to process it
					if(!visitedSet.contains(neighborVertex)) {
						//if a new vertex is discovered then we need to add it to the vertexQ to be processed
						vertexQ.add(neighborVertex);
					}
				}
				//notify the observers that this tempVertex has been visitde and processed
				for(GraphAlgorithmObserver<V> observer : observerList) {
					observer.notifyVisit(tempVertex);
				}
			}
		}
	}
	
	/** 
	 * <P>This method will perform a Depth-First-Search on the graph.
	 * The search will begin at the "start" vertex and conclude once
	 * the "end" vertex has been reached.</P>
	 * 
	 * <P>Before the search begins, this method will go through the
	 * collection of Observers, calling notifyDFSHasBegun on each
	 * one.</P>
	 * 
	 * <P>Just after a particular vertex is visited, this method will
	 * go through the collection of observers calling notifyVisit
	 * on each one (passing in the vertex being visited as the
	 * argument.)</P>
	 * 
	 * <P>After the "end" vertex has been visited, this method will
	 * go through the collection of observers calling 
	 * notifySearchIsOver on each one, after which the method 
	 * should terminate immediately, without visiting further 
	 * vertices.</P> 
	 * 
	 * @param start vertex where search begins
	 * @param end the algorithm terminates just after this vertex
	 * is visited
	 */
	public void DoDFS(V start, V end) {
		//goes through collection of observers and notifies DFS begun
		for(GraphAlgorithmObserver<V> observer : observerList) {
			observer.notifyDFSHasBegun();
		}
		//creates a visitedSet set: holds the processed verticies
		Set<V> visitedSet = new HashSet<V>();
		//creates a vertexStack stack: holds the verticies that need to be processed  
		Stack<V> vertexStack = new Stack<V>();
		//starts by adding the first start vertex to the vertexStacj
		vertexStack.push(start);
		//if the vertexStack is empty then there is nothing left to process so make sure its not
		while(!vertexStack.isEmpty()) {
			//vertexStack pops and gets stored in tempVertex where it is held to be processed
			V tempVertex = vertexStack.pop();
			//make sure that tempVertex is not in visitedSet since it would have already been processed
			if(!visitedSet.contains(tempVertex)) {
				//if the tempVertex is the target or end vertex
				if(tempVertex.equals(end)) {
					//notify all the observers search is over 
					for(GraphAlgorithmObserver<V> observer : observerList) {
						observer.notifySearchIsOver();
					}
					return;
				}
				//add tempVertex to the visitedSet since it has been processed
				visitedSet.add(tempVertex);
				//for every vertex this vertex is connected to 
				for(V neighborVertex : WeightedGraph.get(tempVertex).keySet()) {
					//if the current connected vertex is already in the visitedSet
					if(!visitedSet.contains(neighborVertex)) {
						//push it onto the stack since it needs to be processed
						vertexStack.push(neighborVertex);
					}
				}
				//notify the observers that the vertex has been processed 
				for(GraphAlgorithmObserver<V> observer : observerList) {
					observer.notifyVisit(tempVertex);
				}
			}
		}
	}
	
	/** 
	 * <P>Perform Dijkstra's algorithm, beginning at the "start"
	 * vertex.</P>
	 * 
	 * <P>The algorithm DOES NOT terminate when the "end" vertex
	 * is reached.  It will continue until EVERY vertex in the
	 * graph has been added to the finished set.</P>
	 * 
	 * <P>Before the algorithm begins, this method goes through 
	 * the collection of Observers, calling notifyDijkstraHasBegun 
	 * on each Observer.</P>
	 * 
	 * <P>Each time a vertex is added to the "finished set", this 
	 * method goes through the collection of Observers, calling 
	 * notifyDijkstraVertexFinished on each one (passing the vertex
	 * that was just added to the finished set as the first argument,
	 * and the optimal "cost" of the path leading to that vertex as
	 * the second argument.)</P>
	 * 
	 * <P>After all of the vertices have been added to the finished
	 * set, the algorithm will calculate the "least cost" path
	 * of vertices leading from the starting vertex to the ending
	 * vertex.  Next, it will go through the collection 
	 * of observers, calling notifyDijkstraIsOver on each one, 
	 * passing in as the argument the "lowest cost" sequence of 
	 * vertices that leads from start to end (I.e. the first vertex
	 * in the list will be the "start" vertex, and the last vertex
	 * in the list will be the "end" vertex.)</P>
	 * 
	 * @param start vertex where algorithm will start
	 * @param end special vertex used as the end of the path 
	 * reported to observers via the notifyDijkstraIsOver method.
	 */
	public void DoDijsktra(V start, V end) {
		//goes through collection of observers and notifies that Dijsktra has begun
		for(GraphAlgorithmObserver<V> observer : observerList) {
			observer.notifyDijkstraHasBegun();
		}
		//finishedSet set: holds the verticies whom the shortest path has been found for 
		Set<V> finishedSet = new HashSet<V>();
		//fromChart has Key: the target (TO) value , Value: the FROM vertex 
		Map<V,V> fromChart = new HashMap<V,V>();
		//costChart has Key: the target (TO) value, Value: the cost of the FROM vertex
		Map<V,Integer> costChart = new HashMap<V, Integer>();
		//sets the FROM to null in fromChart & sets the cost to infinite for all 
		//verticies and adds all the verticies to the map
		for(V vertex : WeightedGraph.keySet()) {
			costChart.put(vertex, Integer.MAX_VALUE);
			fromChart.put(vertex, null);
		}
		//sets the costChart start value cost to 0 
		costChart.put(start, 0);
		//while the finished set does not have all the verticies the algorithm is not done running
		while(!finishedSet.equals(costChart.keySet())) {
			//set maxCost to the max possible integer so it will be helpful later in finding the min cost vertex
			int maxCost = Integer.MAX_VALUE;
			//tempVertex to be used for processing
			V tempVertex = null;
			//finds the vertex with the smallest cost excluding the verticies in the finished set 
			for(V vertex : costChart.keySet()) {
				//if the cost of the current vertex is <= maxCost and not already in the finished set
				if(costChart.get(vertex) <= maxCost && !finishedSet.contains(vertex)) {
					tempVertex = vertex;
					maxCost = costChart.get(vertex);
				}
			}
			//add tempVertex to the finished set
			finishedSet.add(tempVertex);
			//getting the neighbors of the current vertex
			for(V neighborVertex : WeightedGraph.get(tempVertex).keySet()) {
				//making sure the current vertex is not in the finished set
				if (!finishedSet.contains(neighborVertex)) {
					//if the cost to get to tempVertex + the cost to get to the connected vertex
					//is less than the cost to get to the neighbor vertex
					if (costChart.get(tempVertex) + WeightedGraph.get(tempVertex).get(neighborVertex) < costChart
							.get(neighborVertex)) {
						//change the cost of the neighbor vertex to the cost to get to tempVertex + 
						//the cost to get to the connected vertex
						costChart.put(neighborVertex,
								costChart.get(tempVertex) + WeightedGraph.get(tempVertex).get(neighborVertex));
						//change the from for the connected vertex to tempVertex
						fromChart.put(neighborVertex, tempVertex);
					}
				}
			}
			//notifying the observer that the vertex is done being processed and pass in its lowest from cost  
			for(GraphAlgorithmObserver<V> observer : observerList) {
				observer.notifyDijkstraVertexFinished(tempVertex, costChart.get(tempVertex));
			}
			
		}
		//now the path needs to be created
		//make a curr which will keep going until its at the start vertex
		V curr = end;
		//a list to be returned with the order of the shortest path
		LinkedList<V> retList = new LinkedList<V>();
		//keep looping until curr has no more further to go
		while(curr != null) {
			//add curr to the head of the list working the way from end to front 
			retList.addFirst(curr);
			//set curr to the vertex that curr comes from
			curr = fromChart.get(curr);
		};
		//notify the observer that Dijkstra is over and return retList aka the order of the shortest path
		for(GraphAlgorithmObserver<V> observer : observerList) {
			observer.notifyDijkstraIsOver(retList);
		}
		
	}
	
}
