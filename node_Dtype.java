import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

//Node Dtype
public class node_Dtype {

  //angle corresponding to the node location x,y
	int nodeid;
	int N;
	int K;
	double theta;
	
	List<node_Dtype> neighbors = new ArrayList<node_Dtype>();
	
	//Node constructor
	public node_Dtype(int nodeid, int N, int K, double theta)
	{
		this.nodeid = nodeid;
		this.N = N;
		this.K = K;
		this.theta = theta;
	}
	
	//distance calculation
	public int distance(int sourceid, int destid)
	{		
		if((sourceid <= K && sourceid >= 1) && ((N-K <= destid) && (destid<=N)))
				return 1;
		else
			return Math.abs(sourceid-destid);
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
	public List<node_Dtype> exchange(List<node_Dtype> inpNeigh)
	{
		List<node_Dtype> toSend = new ArrayList<node_Dtype>();;
		
		toSend.addAll(neighbors);
		toSend.add(this);
		
		neighbors.addAll(inpNeigh);
		HashSet<node_Dtype> hs = new HashSet<node_Dtype>();
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
		node_Dtype pivot = quickselect(neighbors, K);
		List<node_Dtype> update = new ArrayList<node_Dtype>(); 
		
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
	private node_Dtype quickselect(List<node_Dtype> A, int k) {

		node_Dtype pivot = A.get(A.size()/2);
		
		List<node_Dtype> A1 = new ArrayList<node_Dtype>();
		List<node_Dtype> A2 = new ArrayList<node_Dtype>();
		
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
