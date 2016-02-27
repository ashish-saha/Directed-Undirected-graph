import java.util.ArrayList;

public class UndirectedGraph {
	int size;
	SLLSparseM M;
	ArrayList <Integer> myList;
	ArrayList <ArrayList<Integer>> powerSet = new ArrayList <ArrayList<Integer>> ();
	
	ArrayList <ArrayList<Integer>> softClique = new ArrayList <ArrayList<Integer>> ();  			
	ArrayList <ArrayList<Integer>> advanceSoftClique = new ArrayList <ArrayList<Integer>> ();
	
	// constructor initialize an undirected graph, n is the number of nodes
	public UndirectedGraph(int n){
		size =n;
		myList = new ArrayList<Integer> ();
		
		for (int i=0; i<n; i++)
			myList.add(i);
		M = new SLLSparseM ();
		
		ArrayList<Integer> tempList = new ArrayList <Integer> ();
		getAllSets (myList, size, tempList, 0);
		selectionSort (powerSet, powerSet.size());

	}
	
	// check if the given node id is out of bounds
	private boolean outOfBounds(int nidx){
		return (nidx < 0 || nidx >= size);
	}

	// set an edge (n1,n2).
	// Since this is an undirected graph, (n2,n1) is also set to one
	public void setEdge(int n1, int n2){
		
		if (outOfBounds (n1) || outOfBounds (n2))			return;
		M.setElement(n1, n2);
		M.setElement(n2, n1);
		
	}

	// print an output soft clique in one line
	 public void printClique(ArrayList<Integer> nlist){
	   for(int i = 0; i < nlist.size(); ++i)
	     System.out.print(nlist.get(i) + " ");
	   System.out.println("");
	 }

	 // compute maximal soft clique
	 // cliquesize_lower_bd: k
	 // num_missing_edges: l
	 public void findMaxSoftClique(int cliquesize_lower_bd, int num_missing_edges){
		
		for (ArrayList <Integer> iter : powerSet)	{
								
			if (iter.size() < cliquesize_lower_bd)	{					// cliquesize_lower_bd: k
				continue;
			}

			if(isClique(iter, num_missing_edges))		{
			
				if (softClique.size() == 0)	{							// if the initial list is empty insert the first element
					softClique.add(iter);
					printClique(iter);
					continue;
				}
							
				else	{												// check if "iter" is a subset of some bigger clique 
					for (int k=0; k<softClique.size(); k++){
						if (softClique.get(k).containsAll(iter))	{	// if iter is a subset of some bigger clique continue with the next "iter"
							break;
						}
						else if (k==softClique.size()-1){				// if the loop finishes, it means it not a subset of any bigger clique
							softClique.add(iter);
							printClique(iter);
						}
					}
				}
			}
		}
	 }
	  
	 int index =0 ;
	 public void getAllSets (ArrayList<Integer> myList,int length,ArrayList<Integer> tempList, int allowedAfter)	{
		 	
		if (allowedAfter == length)					return;
		else	{
			for (int i=allowedAfter; i<length; i++)	{
				tempList.add(myList.get(i));
				powerSet.add(new ArrayList <Integer> ());
				powerSet.get(index).addAll(tempList);
				index++;

				if (allowedAfter != length)
				getAllSets(myList, length, tempList, i+1);
				tempList.remove(tempList.size()-1);
			}
		}
	 }
		
		
	 public void selectionSort (ArrayList <ArrayList<Integer>> arrayR, int length) { 
	      for ( int i = 0; i < length - 1; i++ ) { 
	         int indexLowest = i; 
	         for ( int j = i + 1; j < length; j++ ) 
	            if ( arrayR.get(j).size() > arrayR.get(indexLowest).size() ) 
	            	indexLowest = j;
		     if ( arrayR.get(indexLowest) != arrayR.get(i) ) { 
		         ArrayList<Integer> temp = new ArrayList <Integer> ();
		         temp.addAll(arrayR.get(indexLowest));
		         arrayR.get(indexLowest).clear();
		         arrayR.get(indexLowest).addAll (arrayR.get(i)); 
		         arrayR.get(i).clear();
		         arrayR.get(i).addAll(temp); 
		                 
		      }  
		    }  
	 }

	 // compute maximal soft clique by using recursion 
	 // to compute all (k,l) soft cliques using recursion
	 // you should check the partial subset during generation 
	 // rather than checking the whole subset
	 // cliquesize_lower_bd: k
	 // num_missing_edges: l
	 public void findMaxSoftCliqueAdvanced(int cliquesize_lower_bd, int num_missing_edges){
			
		ArrayList<Integer> tempList = new ArrayList <Integer> ();
		getAdvanceSets (myList, size, tempList, 0, num_missing_edges);
						
		for (int x = 0; x < advanceSoftClique.size(); x++ )	{
			for (int y = 0; y < advanceSoftClique.size(); y++ )	{
				
				if (x==y)	
					continue;

				if (advanceSoftClique.get(y).containsAll(advanceSoftClique.get(x)))			// if x is subset of some bigger Clique continue to the next x 
					break;
						
				if (y == (advanceSoftClique.size()-1))	{									// x has iterated through all the sets and its not a subset of anything
					if (advanceSoftClique.get(x).size()<cliquesize_lower_bd)		continue;
					printClique(advanceSoftClique.get(x));
				}
			}
		}		
	 }
	 
	 
	 int advanceIndex = 0;
	 public void getAdvanceSets (ArrayList<Integer> myList,int length,ArrayList<Integer> tempList, int allowedAfter, int num_missing_edges)	{
		 	
		if(allowedAfter == length)	return;											//base case
		else	{
			for (int i=allowedAfter; i<length; i++)	{
				tempList.add(myList.get(i));

				if (isClique(tempList, num_missing_edges))	{
					advanceSoftClique.add(new ArrayList <Integer> ());
					advanceSoftClique.get(advanceIndex).addAll(tempList);
					advanceIndex++;
				}
				else if (!isClique(tempList, num_missing_edges))	{				//if its not a clique increment the last element
					int temp = tempList.get(tempList.size()-1);
					tempList.remove(tempList.size()-1);
					tempList.add(temp+1);
					i=i+1;															//increment the allowedAfter as well
					if (isClique(tempList, num_missing_edges))	{					//since templist is changed, check if the new tempList is a Clique
						advanceSoftClique.add(new ArrayList <Integer> ());
						advanceSoftClique.get(advanceIndex).addAll(tempList);
						advanceIndex++;
					}
				}
				
				getAdvanceSets(myList, length, tempList, i+1, num_missing_edges);		
				tempList.remove(tempList.size()-1);
				
			}
		}
	 }
	 
	
	 public  boolean isClique(ArrayList <Integer> list, int num_missing_edges)	{
		 
		 int missing_edge = 0;							
		 for (int x: list)	{
			for (int y: list)	{
				if (x==y) continue;
				else if (M.belongsTo(y, x))					// if row y has indegree from x
					continue;
				else missing_edge++;						// increment the missing edge
			}
		}
		
		if ((missing_edge/2) <= num_missing_edges)	{
			return true;
		}
		else
			return false;
	 }
	 

}
