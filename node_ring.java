import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

//Node type ring
public class node_ring {

  //angle corresponding to the node location x,y
	int nodeid;
	int N;
	int K;
	double theta;
	
	List<node_ring> neighbors = new ArrayList<node_ring>();
	
	//constructor
	public node_ring(int nodeid, int N, int K, double theta)
	{
		this.nodeid = nodeid;
		this.N = N;
		this.K = K;
		this.theta = theta;
	}
	
	//distance calculation
	public int distance(int sourceid, int destid)
	{
		int dist; 
		
		dist = Math.min(N-Math.abs(destid-sourceid),Math.abs(destid-sourceid));
				
		return dist;
	}
	
	//sum of distances
	public double distance_all() 
	{
		double totalDist=0;
		
		for(int i=0; i < neighbors.size(); i++)
		{
			totalDist = totalDist + distance(nodeid, neighbors.get(i).nodeid);
		}
		
		return totalDist;
	}
	
	//exchanges neighbors
	public List<node_ring> exchange(List<node_ring> inpNeigh)
	{
		List<node_ring> toSend = new ArrayList<node_ring>();;
		
		toSend.addAll(neighbors);
		toSend.add(this);
		
		neighbors.addAll(inpNeigh);
		HashSet<node_ring> hs = new HashSet<node_ring>();
		hs.addAll(neighbors);
		neighbors.clear();
		neighbors.addAll(hs);
		quicksort();
		
		return toSend;
	}

	//sorts the first k elements
	private void quicksort()
	{
		Collections.shuffle(neighbors);
		node_ring pivot = quickselect(neighbors, K);
		List<node_ring> update = new ArrayList<node_ring>(); 
		
		int max = K;
		
		for(int i=0; i<neighbors.size(); i++)
		{
			if(neighbors.get(i).distance(nodeid, neighbors.get(i).nodeid) < pivot.distance(nodeid, pivot.nodeid))
			{
				update.add(neighbors.get(i));
				max--;
			}
		}
		
		neighbors.removeAll(update);
		
		for(int i=0; i<neighbors.size(); i++)
		{
			if(neighbors.get(i).distance(nodeid, neighbors.get(i).nodeid) == pivot.distance(nodeid, pivot.nodeid))
			{
				if(max == 0)
				{
					break;
				}
				update.add(neighbors.get(i));
				max--;
			}
		}
		
		neighbors.clear();
		neighbors.addAll(update);
				
	}

	//Implements quick key algorithm
	private node_ring quickselect(List<node_ring> A, int k) {

		node_ring pivot = A.get(A.size()/2);
		
		List<node_ring> A1 = new ArrayList<node_ring>();
		List<node_ring> A2 = new ArrayList<node_ring>();
		
		for(int i=0; i<A.size(); i++)
		{
			if(A.get(i).distance(nodeid, A.get(i).nodeid) < pivot.distance(nodeid, pivot.nodeid))
				A1.add(A.get(i));
			if(A.get(i).distance(nodeid, A.get(i).nodeid) > pivot.distance(nodeid, pivot.nodeid))
				A2.add(A.get(i));
		}
		
		if(k <= A1.size() )
			return quickselect(A1,k);
		
		if(k > A.size()-A2.size() )
			return quickselect(A2,k-(A.size()-A2.size()));
		
		return pivot;
	}

}
