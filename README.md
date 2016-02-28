# Directed-Undirected-graph

This is an implementation of Directed and Undirected graphs using Sparse Matrix. It computes the rank of an Directed graph after
'k' iterations. The command is 

    java GraphMainClass DirectedGraph.txt k     //the DirectedGraph.txt is prvoded in the repository and k is the number of iterations
        

Sample output when k = 3

Computing 3'th PageRank!																										
0 2.0555555555555554									                                                              
1 1.8333333333333333																					
4 0.9722222222222223																			
3 0.08333333333333333																				
2 0.05555555555555555																									
END




It also computes the maximal clique and (k,l) soft clique for the Undirectd Graph, where k is the minimum number of edges  and
l is the allowed number of missing edges before it makes a clique


The command is

    java GraphMainClass UndirectedGraph.txt k l    //for maximal ckique input k and l as 0
    
Sample output when k = 2 and l = 1

Computing (2-1) maximal soft cliques!																															
0 1 3 4 																															
0 2 3 																						
1 2 																																	
2 3 4 																										
END
