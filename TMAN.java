import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TMAN {

  static List<node_ring> RNodes = new ArrayList<node_ring>();
	static List<node_Dtype> DNodes = new ArrayList<node_Dtype>();
	static List<node_Stype> SNodes = new ArrayList<node_Stype>();
	static int N;
	static int K;
	static int Cycles = 50;
	static char nodetype;
	
	/**
	 * @param args Main program calls one of the three topologies after argument assignment
	 */
	public static void main(String[] args) {
	
		if (args.length > 2) {
		    try {
		        N = Integer.parseInt(args[0]);
		        K = Integer.parseInt(args[1]);
		        nodetype = args[2].charAt(0);
		    } catch (NumberFormatException e) {
		        System.err.println("Argument" + " must be an integer");
		        System.exit(1);
		    }
		}
		
		
		if(nodetype == 'D')
			DTop();
		if(nodetype == 'O')
			RingTop();
		if(nodetype == 'S')
			STop();
		
	}
	
	// For D topology. Creates nodes and uses iterations to randomise and use cycles to perform the algorithm.	
	public static void DTop()
	{
		List<node_Dtype> Temp = new ArrayList<node_Dtype>();

		
		for(int i = 0; i < N; i++)
		{
			DNodes.add(new node_Dtype(i, N, K, (Math.PI/2 - (i)*(Math.PI/(N-1)))));
		}
		
		Random r = new Random();
	
		for(int i = 0; i < N; i++)
		{
			int random = 0;
			
			for(int k = 0; k < K; k++)
			{
				random = r.nextInt(N);
				DNodes.get(i).neighbors.add(DNodes.get(random == i?r.nextInt(N):random));
			}
			
		}
		
		
		/*
		System.out.println(RNodes.size());
		for(int i = 0; i < N; i++)
		{
			RNodes.get(i).neighbors.addAll(RNodes);			
		}
		*/
		
		double total_distance = 0;
		for(int i = 0; i<DNodes.size(); i++)
		{
			total_distance = total_distance + DNodes.get(i).distance_all();
		}
		
		//System.out.println(total_distance);

		Random rand = new Random();
		String builder = new String("");
		
		for(int i=0; i<Cycles; i++)
		{
			int  n = rand.nextInt(K);
			for(int j=0; j<N; j++)
			{
				//System.out.println(j + " " + n + " " + RNodes.get(j).neighbors.size());
				Temp.addAll(DNodes.get(j).exchange(DNodes.get(j).neighbors.get(n).neighbors));
				DNodes.get(j).neighbors.get(n).exchange(Temp);
				Temp.clear();
			}
			
			total_distance = 0;
			
			for(int j = 0; j<DNodes.size(); j++)
			{
				total_distance = total_distance + DNodes.get(i).distance_all();
			}
			
			builder = builder + String.valueOf(total_distance) + ":";
						
			if(i == 0 || i == 4 || i == 9 || i == 14 )
			{
				imager("D_N" + N +"_k"+ K + "_" + (i+1));
				

				PrintWriter out;
				try {
					//System.out.println(builder);
					out = new PrintWriter(new FileWriter("D_N" + N +"_k"+ K + "_" + (i+1) + ".txt"));
					for(int k=0 ;k < DNodes.size();k++)
					{	
						out.println("Node at " + DNodes.get(k).nodeid);
						out.println("Neighbors: ");
						for(int l=0; l<DNodes.get(k).neighbors.size(); l++)
							out.print(DNodes.get(k).neighbors.get(l).nodeid + " ");
						out.println();
						out.println();
					}
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
	        
		}
		
		//for(int i=0; i<N; i++)
        	//System.out.println((int)(600 + 300*Math.sin(RNodes.get(i).theta)) + " " + (int)(600 + 300*Math.cos(RNodes.get(i).theta)));
		
		PrintWriter out;
		try {
			//System.out.println(builder);
			out = new PrintWriter(new FileWriter("D_N" + N +"_k"+ K + ".txt"));
			String[] dists = builder.split(":");
			for(int i=0 ;i < dists.length;i++)
					out.println(dists[i]);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		
	}
	
	// For Ring topology. Creates nodes and uses iterations to randomise and use cycles to perform the algorithm.
	public static void RingTop()
	{
		List<node_ring> Temp = new ArrayList<node_ring>();
		
		for(int i = 0; i < N; i++)
		{
			RNodes.add(new node_ring(i, N, K, (Math.PI*2 - (i)*(2*Math.PI/(N-1)))));
		}
		
	
		Random r = new Random();
		
		for(int i = 0; i < N; i++)
		{
			int random = 0;
			
			for(int k = 0; k < K; k++)
			{
				random = r.nextInt(N);
				RNodes.get(i).neighbors.add(RNodes.get(random == i?r.nextInt(N):random));
			}
			
		}
		
		
		/*
		System.out.println(RNodes.size());
		for(int i = 0; i < N; i++)
		{
			RNodes.get(i).neighbors.addAll(RNodes);			
		}
		*/
		
		double total_distance = 0;
		for(int i = 0; i<RNodes.size(); i++)
		{
			total_distance = total_distance + RNodes.get(i).distance_all();
		}
		
		//System.out.println(total_distance);

		Random rand = new Random();
		String builder = new String("");
		
		for(int i=0; i<Cycles; i++)
		{
			int  n = rand.nextInt(K);
			for(int j=0; j<N; j++)
			{
				//System.out.println(j + " " + n + " " + RNodes.get(j).neighbors.size());
				Temp.addAll(RNodes.get(j).exchange(RNodes.get(j).neighbors.get(n).neighbors));
				RNodes.get(j).neighbors.get(n).exchange(Temp);
				Temp.clear();
			}
			
			total_distance = 0;
			for(int j = 0; j<RNodes.size(); j++)
			{
				total_distance = total_distance + RNodes.get(i).distance_all();
			}
			
			builder = builder + String.valueOf(total_distance) + ":";
			
			if(i == 0 || i == 4 || i == 9 || i == 14 )
			{
				imager("O_N" + N +"_k"+ K + "_" + (i+1));
				

				PrintWriter out;
				try {
					//System.out.println(builder);
					out = new PrintWriter(new FileWriter("O_N" + N +"_k"+ K + "_" + (i+1) + ".txt"));
					for(int k=0 ;k < RNodes.size();k++)
					{	
						out.println("Node at " + RNodes.get(k).nodeid);
						out.println("Neighbors: ");
						for(int l=0; l<RNodes.get(k).neighbors.size(); l++)
							out.print(RNodes.get(k).neighbors.get(l).nodeid + " ");
						out.println();
						out.println();
					}
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//System.out.println(total_distance);
	        
		}
		
		//for(int i=0; i<N; i++)
        	//System.out.println((int)(600 + 300*Math.sin(RNodes.get(i).theta)) + " " + (int)(600 + 300*Math.cos(RNodes.get(i).theta)));
		
		
				
		PrintWriter out;
		try {
			//System.out.println(builder);
			out = new PrintWriter(new FileWriter("O_N" + N +"_k"+ K + ".txt"));
			String[] dists = builder.split(":");
			for(int i=0 ;i < dists.length;i++)
					out.println(dists[i]);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	// For Smiley topology. Creates nodes and uses iterations to randomise and use cycles to perform the algorithm.
	public static void STop()
	{
		List<node_Stype> Temp = new ArrayList<node_Stype>();
		
		for(int i = 0; i < N/2; i++)
		{
			SNodes.add(new node_Stype(i, N, K, (Math.PI*2 - (i)*(2*Math.PI/(N/2-1)))));
		}
		for(int i = N/2; i < 5*N/8; i++)
		{
			SNodes.add(new node_Stype(i, N, K, (-1*(i-N/2)*(Math.PI/((N/8)-1)))));
		}
		for(int i = 5*N/8; i < 3*N/4; i++)
		{
			SNodes.add(new node_Stype(i, N, K, (-1*(i-5*N/8)*(Math.PI/((N/8)-1)))));
		}
		for(int i = 3*N/4; i < N; i++)
		{
			SNodes.add(new node_Stype(i, N, K, (Math.PI - (i-3*N/4-1)*(Math.PI/(N/4-1)))));
		}
		
		/*
		for(int i = 0; i < N; i++)
		{
			SNodes.get(i).neighbors.addAll(SNodes);			
		}*/
		
		Random r = new Random();
		
		for(int i = 0; i < N; i++)
		{
			int random = 0;
			
			if(i < N/2)
				for(int k = 0; k < K; k++)
				{
					random = r.nextInt(N/2);
					SNodes.get(i).neighbors.add(SNodes.get(random == i?r.nextInt(N/2):random));
				}
			else if(i < 5*N/8)
				for(int k = 0; k < K; k++)
				{
					random = r.nextInt(N/8) + N/2;
					SNodes.get(i).neighbors.add(SNodes.get(random == i? (r.nextInt(N/8) + N/2):random));
				}
			else if(i < 3*N/4)
				for(int k = 0; k < K; k++)
				{
					random = (r.nextInt(N/8) + N*5/8);
					SNodes.get(i).neighbors.add(SNodes.get((random == i? (r.nextInt(N/8) + N*5/8):random)));
				}
			else if(i < N)
				for(int k = 0; k < K; k++)
				{
					random = r.nextInt(N/4) + N*3/4;
					SNodes.get(i).neighbors.add(SNodes.get((random == i? (r.nextInt(N/4) + N*3/4):random)));					
				}
		}
		
		
		Random rand = new Random();
		String builder = new String("");
		double total_distance = 0;
		
		for(int i=0; i<Cycles; i++)
		{
			int  n = rand.nextInt(K);
			for(int j=0; j<N; j++)
			{
				//System.out.println(j + " " + n + " " + SNodes.get(j).neighbors.size());
				Temp.addAll(SNodes.get(j).exchange(SNodes.get(j).neighbors.get(n).neighbors));
				SNodes.get(j).neighbors.get(n).exchange(Temp);
				Temp.clear();
			}
						
			if(i == 0 || i == 4 || i == 9 || i == 14 )
			{
				imager("S_N" + N +"_k"+ K + "_" + (i+1));
				
				PrintWriter out;
				try {
					//System.out.println(builder);
					out = new PrintWriter(new FileWriter("S_N" + N +"_k"+ K + "_" + (i+1) + ".txt"));
					for(int k=0 ;k < SNodes.size();k++)
					{	
						out.println("Node at " + SNodes.get(k).nodeid);
						out.println("Neighbors: ");
						for(int l=0; l<SNodes.get(k).neighbors.size(); l++)
							out.print(SNodes.get(k).neighbors.get(l).nodeid + " ");
						out.println();
						out.println();
					}
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			total_distance = 0;
			for(int j = 0; j<RNodes.size(); j++)
			{
				total_distance = total_distance + RNodes.get(i).distance_all();
			}
			
			builder = builder + String.valueOf(total_distance) + ":";
						
		}
		
		PrintWriter out;
		try {
			//System.out.println(builder);
			out = new PrintWriter(new FileWriter("S_N" + N +"_k"+ K + ".txt"));
			String[] dists = builder.split(":");
			for(int i=0 ;i < dists.length;i++)
					out.println(dists[i]);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	//Image drawing functions
	public static void imager(String img)
	{
		BufferedImage image = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);   
		Graphics g = image.getGraphics();   
        //g.drawOval(40, 40, 60, 60); //FOR CIRCLE
        //g.drawRect(80, 30, 200, 200); // FOR SQUARE
        //g.drawRect(200, 100, 100, 200); // FOR RECT
			
		//g.drawString("Helloworld", 10, 20)
		
    	if(nodetype == 'S')	    	 
	    {
    		for(int i=0; i<SNodes.size(); i++)
	    		for(int j=0; j<SNodes.get(i).neighbors.size(); j++)
	    		{
	    			if(i < 500)
	    			{
	    				g.setColor(Color.red);
	    				g.drawLine((int)(600 + 300*Math.cos(SNodes.get(i).theta)), (int)(600 + 300*Math.sin(SNodes.get(i).theta)), (int)(600 + 300*Math.cos(SNodes.get(i).neighbors.get(j).theta)), (int)(600 + 300*Math.sin(SNodes.get(i).neighbors.get(j).theta)));
	    			}
	    			else if(i < 625)
	    			{
	    				g.setColor(Color.green);
	    				g.drawLine((int)(450 + 75*Math.cos(SNodes.get(i).theta)), (int)(550 + 75*Math.sin(SNodes.get(i).theta)), (int)(450 + 75*Math.cos(SNodes.get(i).neighbors.get(j).theta)), (int)(550 + 75*Math.sin(SNodes.get(i).neighbors.get(j).theta)));
	    			}
	    			else if(i < 750)
	    			{
	    				g.setColor(Color.green);
	    				g.drawLine((int)(725 + 75*Math.cos(SNodes.get(i).theta)), (int)(550 + 75*Math.sin(SNodes.get(i).theta)), (int)(725 + 75*Math.cos(SNodes.get(i).neighbors.get(j).theta)), (int)(550 + 75*Math.sin(SNodes.get(i).neighbors.get(j).theta)));
	    			}
	    			else if(i < 1000)
	    			{
	    				g.setColor(Color.blue);
	    				g.drawLine((int)(600 + 150*Math.cos(SNodes.get(i).theta)), (int)(700 + 75*Math.sin(SNodes.get(i).theta)), (int)(600 + 150*Math.cos(SNodes.get(i).neighbors.get(j).theta)), (int)(700 + 75*Math.sin(SNodes.get(i).neighbors.get(j).theta)));
	    			}
	    			
	    			//System.out.println(i + " " + j);
	    		}
	    }
	    if(nodetype == 'D')	    	 
	    {
	    	for(int i=0; i<N; i++)
	    		for(int j=0; j<DNodes.get(i).neighbors.size(); j++)
	    		{
	    			g.drawLine((int)(600 + 300*Math.cos(DNodes.get(i).theta)), (int)(600+ 300*Math.sin(DNodes.get(i).theta)), (int)(600 + 300*Math.cos(DNodes.get(i).neighbors.get(j).theta)), (int)(600 + 300*Math.sin(DNodes.get(i).neighbors.get(j).theta)));
	       			//System.out.println(i + " " + j);
	    		}
	    }
	    if(nodetype == 'O')	    	 
	    {
	    	for(int i=0; i<N; i++)
	    		for(int j=0; j<RNodes.get(i).neighbors.size(); j++)
	    		{
	    			g.drawLine((int)(600 + 300*Math.cos(RNodes.get(i).theta)), (int)(600+ 300*Math.sin(RNodes.get(i).theta)), (int)(600 + 300*Math.cos(RNodes.get(i).neighbors.get(j).theta)), (int)(600 + 300*Math.sin(RNodes.get(i).neighbors.get(j).theta)));
	       			//System.out.println(i + " " + j);
	    		}
	    }
	 		     
	    try 
	    {    
	      ImageIO.write(image, "jpg", new File(img + ".jpg"));   
	    } 
	    catch (IOException e) 
	    {    
	    	e.printStackTrace();   
	    }  
	}

     
     	
     
}
