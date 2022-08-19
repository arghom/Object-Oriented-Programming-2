package graph;
import graph.WeightedGraph;
import maze.Juncture;
import maze.Maze;

/** 
 * <P>The MazeGraph is an extension of WeightedGraph.  
 * The constructor converts a Maze into a graph.</P>
 */
public class MazeGraph extends WeightedGraph<Juncture> {

	/* STUDENTS:  SEE THE PROJECT DESCRIPTION FOR A MUCH
	 * MORE DETAILED EXPLANATION ABOUT HOW TO WRITE
	 * THIS CONSTRUCTOR
	 */
	
	/** 
	 * <P>Construct the MazeGraph using the "maze" contained
	 * in the parameter to specify the vertices (Junctures)
	 * and weighted edges.</P>
	 * 
	 * <P>The Maze is a rectangular grid of "junctures", each
	 * defined by its X and Y coordinates, using the usual
	 * convention of (0, 0) being the upper left corner.</P>
	 * 
	 * <P>Each juncture in the maze should be added as a
	 * vertex to this graph.</P>
	 * 
	 * <P>For every pair of adjacent junctures (A and B) which
	 * are not blocked by a wall, two edges should be added:  
	 * One from A to B, and another from B to A.  The weight
	 * to be used for these edges is provided by the Maze. 
	 * (The Maze methods getMazeWidth and getMazeHeight can
	 * be used to determine the number of Junctures in the
	 * maze. The Maze methods called "isWallAbove", "isWallToRight",
	 * etc. can be used to detect whether or not there
	 * is a wall between any two adjacent junctures.  The 
	 * Maze methods called "getWeightAbove", "getWeightToRight",
	 * etc. should be used to obtain the weights.)</P>
	 * 
	 * @param maze to be used as the source of information for
	 * adding vertices and edges to this MazeGraph.
	 */
	public MazeGraph(Maze maze) {
		//iterates through the height of the maze
		for(int yCoord = 0; yCoord < maze.getMazeHeight(); yCoord++) {
			//iterates through the width of the maze
			for(int xCoord = 0; xCoord < maze.getMazeWidth(); xCoord++) {
				//creates a new juncture for the current coordinate
				Juncture tempJuncture = new Juncture(xCoord,yCoord);
				//adds the juncture to the MazeGraph
				this.addVertex(tempJuncture);
				//now, to check adjacent junctures need to make sure that there is no wall above and that above is in
				//bounds
				if(yCoord > 0 && !maze.isWallAbove(tempJuncture)){
					//create a tempJunctureAbove so you can refer to it to add the edges
					Juncture tempJunctureAbove = new Juncture(xCoord,yCoord-1);
					//add the edges for both from A to B & B to A
					this.addEdge(tempJuncture, tempJunctureAbove , maze.getWeightAbove(tempJuncture));
					this.addEdge(tempJunctureAbove, tempJuncture , maze.getWeightAbove(tempJuncture));
				}
				//now, to check adjacent junctures need to make sure that there is no wall to the left and that 
				//left is in bounds
				if(xCoord > 0 && !maze.isWallToLeft(tempJuncture)){
					//create a tempJunctureLeft so you can refer to it to add the edges
					Juncture tempJunctureLeft = new Juncture(xCoord-1,yCoord);
					//add the edges for both from A to B & B to A
					this.addEdge(tempJuncture, tempJunctureLeft , maze.getWeightToLeft(tempJuncture));
					this.addEdge(tempJunctureLeft, tempJuncture , maze.getWeightToLeft(tempJuncture));
				}
				
			}
		}
		
		
	}
}
