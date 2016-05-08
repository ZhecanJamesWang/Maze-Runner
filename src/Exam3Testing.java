/* Testing program for EXAM3 - Implementation of GraphPlus
*/
import java.io.*;
public class Exam3Testing {
  
  
  
  public static void main(String[] args) throws FileNotFoundException {
    
   // Set up simple graph.
    GraphPlus<String> G = new AdjMatGraphPlus<String>();
    //add some vertices
    G.addVertex("A"); G.addVertex("B"); G.addVertex("C");
    G.addVertex("D"); G.addVertex("E"); G.addVertex("F");
    
    //add some edges
    G.addEdge("A", "B"); G.addEdge("B", "C"); G.addEdge("C", "D");
    G.addEdge("F", "A"); G.addEdge("A", "D");
    
    G.addArc("A", "C"); // adding an arc, makes it directed
    G.removeArc("A", "C"); // removing an arc
    
    //test remove vertex
    G.removeVertex("A");
    
    System.out.println("\n*****************************************************\n");
    System.out.println("************* Sample Testing for Exam 3 *************");
    System.out.println("*****************************************************");
    
    System.out.println("Notation: (expected result: )  [actual result]\n");
    
    System.out.println(G);
    
    System.out.println("\n*** Testing DFS and BFS ***");
    try {
     System.out.print("DFS from B [B,C,D] \t: ");
     System.out.println(G.dfsTraversal("B")); 
    } catch (UnsupportedOperationException e) {
     System.out.println("UNIMPLEMENTED");
    }
    try {
    System.out.print("DFS from F     [F] \t: ");
   System.out.println(G.dfsTraversal("F")); 
    } catch (UnsupportedOperationException e) {
     System.out.println("UNIMPLEMENTED");
    }
    try {
    System.out.print("BFS from B [B,C,D] \t: ");
   System.out.println(G.bfsTraversal("B")); 
    } catch (UnsupportedOperationException e) {
     System.out.println("UNIMPLEMENTED");
    }
    try {
    System.out.print("BFS from F     [F] \t: ");
   System.out.println(G.bfsTraversal("F")); 
    } catch (UnsupportedOperationException e) {
     System.out.println("UNIMPLEMENTED");
    }
    
    System.out.println("\n*** Testing clone() ***");
    try {
    System.out.println("CLONED GRAPH (diamond): ");
   System.out.println(G.clone());
    } catch (UnsupportedOperationException e) {
     System.out.println("UNIMPLEMENTED");
    }
    
    System.out.println("\n************* tests on the CS-COURSES graph **************");
    GraphPlus<String> C = AdjMatGraphPlus.fromTGF("cs-courses.tgf");
    System.out.println(C);
    try {
    System.out.println("CS graph in DFS: \n(CS111, CS230, CS232, CS231, CS235, CS251, CS242, CS315, CS240):");
   System.out.println(C.dfsTraversal("CS111")); 
    } catch (UnsupportedOperationException e) {
     System.out.println("UNIMPLEMENTED");
    }
    try {
    System.out.println("\nCS graph in BFS: \n(CS111, CS230, CS240, CS232, CS231, CS235, CS251, CS242, CS315):");
   System.out.println(C.bfsTraversal("CS111")); 
    } catch (UnsupportedOperationException e) {
     System.out.println("UNIMPLEMENTED");
    }
    try {
    System.out.println("\nCS-courses with no pre-requisites : \n(MATH225, CS110, CS114, CS111):");
   System.out.println(C.allSources());
    } catch (UnsupportedOperationException e) {
     System.out.println("UNIMPLEMENTED");
    }
    try {
    System.out.println("\nNo followup courses:      \n(CS232, CS231, CS240, CS251, CS242, CS315):");
   System.out.println(C.allSinks());
    } catch (UnsupportedOperationException e) {
     System.out.println("UNIMPLEMENTED");
    }
    System.out.println("\n--------> ONE WAY to satisfy pre-req:");
    try {
   System.out.println("(MATH225, CS110, CS114, CS215, CS111, CS230, CS232, CS231, CS235, CS240, CS251, CS242, CS315):");
     System.out.println(C.listByPriority());
  } catch (GraphCycleException e) {
   System.out.println("  FAILURE:  Stuck in the major forever!  (You erroneously reported a cycle where there is none.)");
    } catch (UnsupportedOperationException e) {
     System.out.println("UNIMPLEMENTED");
   }
    

    System.out.println("\n************* tests on the withA graph **************");
    GraphPlus<String> D = AdjMatGraphPlus.fromTGF("withA.tgf");
    System.out.println(D);
    try {
    System.out.println("graph in DFS: \n(B, C, D, A, F):");
   System.out.println(D.dfsTraversal("B")); 
    } catch (UnsupportedOperationException e) {
     System.out.println("UNIMPLEMENTED");
    }
    try {
    System.out.println("\n graph in BFS: \n(B, A, C, D, F):");
   System.out.println(D.bfsTraversal("B")); 
    } catch (UnsupportedOperationException e) {
     System.out.println("UNIMPLEMENTED");
    }
    try {
    System.out.println("\n with no pre-requisites : \n(E):");
   System.out.println(D.allSources());
    } catch (UnsupportedOperationException e) {
     System.out.println("UNIMPLEMENTED");
    }
    try {
    System.out.println("\nNo followup:      \n(E):");
   System.out.println(D.allSinks());
    } catch (UnsupportedOperationException e) {
     System.out.println("UNIMPLEMENTED");
    }
    System.out.println("\n--------> ONE WAY to satisfy pre-req:");
    try {
   System.out.println("(The graph is not a DAG):");
     System.out.println(D.listByPriority());
  } catch (GraphCycleException e) {
   System.out.println(" The graph is not a DAG ");
    } catch (UnsupportedOperationException e) {
     System.out.println("UNIMPLEMENTED");
   }
    System.out.println("\nSAMPLE TESTING COMPLETE");

    System.out.println("\n************* tests on the number graph **************");
    GraphPlus<String> E = AdjMatGraphPlus.fromTGF("number.tgf");
    System.out.println(E);
    try {
    System.out.println("graph in DFS: \n(1, 4, 5, 3, 2):");
   System.out.println(E.dfsTraversal("1")); 
    } catch (UnsupportedOperationException e) {
     System.out.println("UNIMPLEMENTED");
    }
    try {
    System.out.println("\n graph in BFS: \n(1, 2, 3, 4, 5):");
   System.out.println(E.bfsTraversal("1")); 
    } catch (UnsupportedOperationException e) {
     System.out.println("UNIMPLEMENTED");
    }
    try {
    System.out.println("\n with no pre-requisites : \n(1, 6):");
   System.out.println(E.allSources());
    } catch (UnsupportedOperationException e) {
     System.out.println("UNIMPLEMENTED");
    }
    try {
    System.out.println("\nNo followup:      \n(5):");
   System.out.println(E.allSinks());
    } catch (UnsupportedOperationException e) {
     System.out.println("UNIMPLEMENTED");
    }
    System.out.println("\n--------> ONE WAY to satisfy pre-req:");
    try {
   System.out.println("(1, 2, 3, 4, 6, 5):");
     System.out.println(E.listByPriority());
  } catch (GraphCycleException e) {
   System.out.println(" The graph is not a DAG ");
    } catch (UnsupportedOperationException e) {
     System.out.println("UNIMPLEMENTED");
   }
    System.out.println("\nSAMPLE TESTING COMPLETE");  
  }
}