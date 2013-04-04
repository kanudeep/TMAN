import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

//Node Stype
public class node_Stype {

  //angle corresponding to the node location x,y
	int nodeid;
	int N;
	int K;
	double theta;
	
	List<node_Stype> neighbors = new ArrayList<node_Stype>();
	
	//Node constructor
	public node_Stype(int nodeid, int N, int K, double theta)
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
		
		if(sourceid < N/2 && destid < N/2)
			dist = Math.min(N/2-Math.abs(destid-sourceid),Math.abs(destid-sourceid));
		else if((sourceid < 5*N/8 && destid < 5*N/8) && (sourceid >= N/2 && destid >= N/2))
			dist = Math.min(Math.abs(5*N/8-Math.abs(destid-sourceid)-9*N/16),Math.abs(Math.abs(destid-sourceid)-9*N/16));
		else if((sourceid < 3*N/4 && destid < 3*N/4) && (sourceid >= 5*N/8 && destid >= 5*N/8))
			dist = Math.min(Math.abs(3*N/4-Math.abs(destid-sourceid)-11*N/16),Math.abs(Math.abs(destid-sourceid)-11*N/16));
		else if((sourceid < N && destid < N) && (sourceid >= 3*N/4 && destid >= 3*N/4))
			dist = Math.min(N-Math.abs(destid-sourceid),Math.abs(destid-sourceid));
		else
			dist = N;
		
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
	public List<node_Stype> exchange(List<node_Stype> inpNeigh)
	{
		List<node_Stype> toSend = new ArrayList<node_Stype>();;
		
		toSend.addAll(neighbors);
		toSend.add(this);
		
		neighbors.addAll(inpNeigh);
		HashSet<node_Stype> hs = new HashSet<node_Stype>();
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
		node_Stype pivot = quickselect(neighbors, K);
		List<node_Stype> update = new ArrayList<node_Stype>(); 
		
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
	private node_Stype quickselect(List<node_Stype> A, int k) {

		node_Stype pivot = A.get(A.size()/2);
		
		List<node_Stype> A1 = new ArrayList<node_Stype>();
		List<node_Stype> A2 = new ArrayList<node_Stype>();
		
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
