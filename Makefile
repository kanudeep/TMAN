#

#
JCC = javac
GR = jar

#
JFLAGS = -g
RFLAGS = cvfm

#
default: node_Dtype.class node_Stype.class node_ring.class TMAN.class

#
node_Dtype.class: node_Dtype.java
  $(JCC) $(JFLAGS) node_Dtype.java

node_ring.class: node_ring.java
	$(JCC) $(JFLAGS) node_ring.java

node_Stype.class: node_Stype.java
	$(JCC) $(JFLAGS) node_Stype.java

TMAN.class: TMAN.java
	$(JCC) $(JFLAGS) TMAN.java

	echo Main-Class: TMAN >manifest.txt

	$(GR) $(RFLAGS) TMAN.jar  manifest.txt *.class

#
clean: 
	$(RM) *.class
	$(RM) *.jar
	$(RM) *.txt
