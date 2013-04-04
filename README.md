TMAN
====

Gossip based TMAN Algorithm


In this project, I have written a code in Java to implement a method for “Topology Management of Overlay Networks”      describe 2.2.2 of Andrew S.Tanenbaum’s Distributed Systems book and in attached reading supplement 1(The paper “T-Man: Fast Gossip-based Constructions of Large-Scale Overlay Topologies” byMark Jelasity and Ozalp Babaoglu). This algorithm is also known as Jelasity and Babaoglu’s algorithm. The gist of this algorithm is discussed in the next paragraph (use the above references for the detailed descriptions). 


In this algorithm, every node in the network maintains a list of neighbors. During the network - initialization phase, each node randomly selects k neighbors and places them into its neighbor list. During the network - evolution phase, in each cycle of the iterative algorithm, every node randomly selects one of its neighbors, and then sends a list consisting of the identifiers of its neighbors and of itself to that neighbor. The selected neighbor also sends its neighbors list back to the node which initiated the action. Upon receiving the new neighbor list, the nodes select the nearest
k nodes from both the new and old lists as their neighbors and discard all the others.
