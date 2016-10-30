import java.util.Scanner;
import java.util.Vector;
import java.util.ArrayList;
import java.io.File;

public class GraphMainClass {

    static Vector <String> myVector;

	// read and process a graph
	// for an undirected graph, compute (k-l) maximal soft cliques
	// for a directed graph, compute page rank using k iterations
	// parameters.get(0) is k
	// parameters.get(1) is l
	public static void ProcessingGraph(String file_name, ArrayList<Integer> parameters){
	
		Scanner sc = null;
	    String tmps;
	    int graph_size = 0;				// number of nodes
	    boolean isUndirected = true;	// whether the graph is undirected or directed
       
	    try {
	        sc = new Scanner(new File(file_name));
  		    tmps = sc.next();
  		    if(tmps.equals("UNDIRECTED")){
  		    	isUndirected = true;
  		    	tmps = sc.next();
  		    	graph_size = sc.nextInt();
         }else if(tmps.equals("DIRECTED")){
		    	isUndirected = false;
		    	tmps = sc.next();
		    	graph_size = sc.nextInt();
         }else{
           throw new Exception();
         }
	    } catch (Exception e) {
         System.out.println("Error Reading the Graph Data!");
	        return;
	    }

     UndirectedGraph UG;
     DirectedGraph DG;

     if( isUndirected ){  
     // undirected graph
       UG = new UndirectedGraph(graph_size);
       myVector = new Vector <String> (graph_size, 10);
       try {
	     while (sc.hasNext()) {
	    	 tmps = sc.next();
	 		 if(tmps.equals("Friend")){
	 			 // set an edge
	 			 String temp1 = sc.next();
	 			 String temp2 = sc.next();
	 			 
	 			 if (!myVector.contains(temp1))		myVector.add(temp1);
	 			 if (!myVector.contains(temp2))		myVector.add(temp2);
	 			 
	 			 int nid1 = myVector.indexOf(temp1); // row index
	 	 		 int nid2 = myVector.indexOf(temp2); // col index
	 	 		 UG.setEdge(nid1, nid2);
	 	 	 }else if(tmps.equals("END")){
	 	 		 // finished, return the matrix
	 	 		 sc.close();
	 	         break;
	 	 	 }else{	    
	 	 	     sc.close();
	 	         throw new Exception();
	 	     }		    	
	     }
	   } catch (Exception e) {
	     System.out.println("Error Reading the Graph Data!");
	 	 return;
	   }

       int k = parameters.get(0);
       int l = parameters.get(1);

       System.out.println("Computing (" + k + "-" + l + ") maximal soft cliques!");

       UG.findMaxSoftClique(k, l);


     }else{
     // directed graph
       DG = new DirectedGraph(graph_size);

	   try {
	 		    while (sc.hasNext()) {
	 		    	tmps = sc.next();
	 		    	 if(tmps.equals("EDGE")){
	 	 		    		// set an edge
	 		    		 	
	 	 		    		int nid1 = sc.nextInt(); // row index
	 	 		    		int nid2 = sc.nextInt(); // col index
	 	 		    		DG.setEdge(nid1, nid2);
	 	 	    	}else if(tmps.equals("END")){
	 	 		    		// finished, return the matrix
	 	 		    		sc.close();
	 	 		    		break;
	 	 	    	}else{
	 	 	    		sc.close();
	 	 	    		throw new Exception();
	 	 	    	}		    	
	 		    }
	 	} catch (Exception e) {
	 			System.out.println("Error Reading the Graph Data!");
	 	        return;
	 	}
       int k = parameters.get(0);
       System.out.println("Computing " + k + "'th PageRank!");
       DG.computePageRank(k);
     }
     System.out.println("END");
	}
	
	public static void main(String[] args) {
	
		String fname;
		int k=0,l=0;
 
		fname = "";
	  try{ 
	    fname = args[0];
		   k = Integer.parseInt(args[1]);
		   l = 0;
		   if(args.length>2)
		     l = Integer.parseInt(args[2]);
	  } catch (Exception e){
	    System.err.println("Error Parsing Arguments!");
	  }
	  ArrayList<Integer> params = new ArrayList<Integer>();
	  params.add(k);
	  params.add(l);

	  ProcessingGraph(fname,params);	
		
	}
}
