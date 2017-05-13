
/* ShortestPath.java
   CSC 226 - Spring 2017
      
   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
	java ShortestPath
	
   To conveniently test the algorithm with a large input, create a text file
   containing one or more test graphs (in the format described below) and run
   the program with
   java ShortestPath file.txt
   where file.txt is replaced by the name of the text file.
   
   The input consists of a series of graphs in the following format:
   
    <number of vertices>
	<adjacency matrix row 1>
	...
	<adjacency matrix row n>
	
   Entry A[i][j] of the adjacency matrix gives the weight of the edge from 
   vertex i to vertex j (if A[i][j] is 0, then the edge does not exist).
   Note that since the graph is undirected, it is assumed that A[i][j]
   is always equal to A[j][i].
	
   An input file can contain an unlimited number of graphs; each will be 
   processed separately.


   B. Bird - 08/02/2014
 */ 
 
 /* Modified on Mars 11 2017 by Mamoutou Sangare */ 

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Stack;


public class ShortestPath{
	
	public static int numVerts;
	public static int distanceArray[];
	public static int parentArray[];
	
	private static int minDistance(int dist[], Boolean sptSet[],int n) {
		// Initialize min value
		int min = Integer.MAX_VALUE, min_index = -1;

		for (int v = 0; v < n; v++)
			if (sptSet[v] == false && dist[v] <= min) {
				min = dist[v];
				min_index = v;
			}

		return min_index;
	}

	private static void printPath(int parent[], int j) {
		if (parent[j] == -1)
			return;
		printPath(parent, parent[j]);
		//System.out.printf(" %d --> ",j);
        System.out.print(" --> " + j);
       
	}

	//  constructing distance array
	private static void printSolution(int dist[], int n, int[] parent) {
		int scr = 0;
		for (int i = 1; i < n; i++) {
			System.out.printf("\nThe path from %d to %d is: %d", scr,i,scr); 
			printPath(parent, i);
			System.out.printf(" and the total distance is: %d",distanceArray[i]);
		}
	}
    
	public static void ShortestPath(int graph[][], int src) {
		numVerts = graph[0].length;
		distanceArray = new int[numVerts]; 
		Boolean sptSet[] = new Boolean[numVerts];
        parentArray = new int[numVerts];

		// Initialize all distances as INFINITE and stpSet[] as false
		for (int i = 0; i < numVerts; i++) {
			parentArray[0] = -1;
			distanceArray[i] = Integer.MAX_VALUE;
			sptSet[i] = false;
		}
		distanceArray[src] = 0;

		// Find shortest path for all vertices
		for (int count = 0; count < numVerts - 1; count++) {
			int u = minDistance(distanceArray, sptSet,numVerts);
			sptSet[u] = true;

			// Update dist value of the adjacent vertices of the
			// picked vertex
			for (int v = 1; v < numVerts; v++)

				if (!sptSet[v] && graph[u][v] != 0 && distanceArray[u] != Integer.MAX_VALUE && 
                        distanceArray[u] + graph[u][v] < distanceArray[v]) {
                            
					parentArray[v] = u;
					distanceArray[v] = distanceArray[u] + graph[u][v];
				}
		}
	}  
    
	public static void PrintPaths(int source){
		 System.out.printf("The path from %d to %d is: %d and the total distance is : %d",source,source,source,source);
		 printSolution(distanceArray, numVerts, parentArray);
         System.out.println();
      }

    public static void main(String[] args) throws FileNotFoundException{
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}
		
		int graphNum = 0;
		double totalTimeSeconds = 0;
		
		//Read graphs until EOF is encountered (or an error occurs)
		while(true){
			graphNum++;
			if(graphNum != 1 && !s.hasNextInt())
				break;
			System.out.printf("Reading graph %d\n",graphNum);
			int n = s.nextInt();
			int[][] G = new int[n][n];
			int valuesRead = 0;
			for (int i = 0; i < n && s.hasNextInt(); i++){
				for (int j = 0; j < n && s.hasNextInt(); j++){
					G[i][j] = s.nextInt();
					valuesRead++;
				}
			}
			if (valuesRead < n*n){
				System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
				break;
			}
			long startTime = System.currentTimeMillis();
			
			ShortestPath(G, 0);
            PrintPaths(0);
			long endTime = System.currentTimeMillis();
			totalTimeSeconds += (endTime-startTime)/1000.0;
			
			//System.out.printf("Graph %d: Minimum weight of a 0-1 path is %d\n",graphNum,totalWeight);
		}
		graphNum--;
		System.out.printf("Processed %d graph%s.\nAverage Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>0)?totalTimeSeconds/graphNum:0);
	}
}
