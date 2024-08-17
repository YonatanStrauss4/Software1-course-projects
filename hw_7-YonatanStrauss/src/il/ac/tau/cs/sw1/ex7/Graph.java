package il.ac.tau.cs.sw1.ex7;
import java.util.*;

import il.ac.tau.cs.sw1.ex7.FractionalKnapSack.Item;


public class Graph implements Greedy<Graph.Edge>{
    List<Edge> lst; //Graph is represented in Edge-List. It is undirected. Assumed to be connected.
    int n; //nodes are in [0,..., n]

    Graph(int n1, List<Edge> lst1){
        lst = lst1;
        n = n1;
    }

    public static class Edge{
        int node1, node2;
        double weight;

        Edge(int n1, int n2, double w) {
            node1 = n1;
            node2 = n2;
            weight = w;
        }

        @Override
        public String toString() {
            return "{" + "(" + node1 + "," + node2 + "), weight=" + weight + '}';
        }
    }

    @Override
    public Iterator<Edge> selection() {
    	 Collections.sort(this.lst, new Comparator<Edge>() {
             @Override
             public int compare(Edge edge1, Edge edge2) {
            	 double weight1 = edge1.weight;
                 double weight2 = edge2.weight;
                 if (weight1 > weight2)
                     return 1;
                 else if (weight1 < weight2)
                     return -1;
                 else
                	 if(edge1.node1 > edge2.node1)
                		 return 1;
                	 else if(edge1.node1 < edge2.node1)
                		 return -1;
                	 else if(edge1.node2 > edge2.node2)
                		 return 1;
                	 else if(edge1.node2 < edge2.node2)
                		 return -1; 
                     return 0;
             }
         });
    	 return lst.iterator();
    }
    
    
    public int[][] matrixMultiplication(int[][] mat1, int[][] mat2){
    	int[][] finalMat = new int[this.n+1][this.n+1];
    	for(int i=0; i<this.n+1; i++) {
    		for(int j=0; j<this.n+1; j++)
    			for(int k=0; k<this.n+1; k++) {
    				finalMat[i][j] += mat1[i][k]*mat2[k][j];
    			}
    	}
    	return finalMat;
    }

    @Override
    public boolean feasibility(List<Edge> candidates_lst, Edge element) {
    	int[][] matrix = new int[this.n+1][this.n+1];
    	for(Edge element1: candidates_lst) {
    		matrix[element1.node1][element1.node2]=1;
    		matrix[element1.node2][element1.node1]=1;
    	}

    	int[][] original_matrix = Arrays.copyOf(matrix, matrix.length);
    			
    	for(int i=0; i<(this.n+1)*(this.n+1); i++) {
    		if(matrix[element.node1][element.node2]==1)
    			return false;
    		else
    		{
    			matrix = matrixMultiplication(matrix, original_matrix);
    		}

    	}
    						
    	return true;
    				
    }



    @Override
    public void assign(List<Edge> candidates_lst, Edge element) {
    	candidates_lst.add(element);
        return;
    }

    @Override
    public boolean solution(List<Edge> candidates_lst) {
    	if(candidates_lst.size() == this.n)
    		return true;
        return false;
    }
}
