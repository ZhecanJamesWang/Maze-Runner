//**************************************************************
//  AdjMatGraphPlus.java       Zhecan Wang
//
//  An extended Adjacency Matrix graph implementation
//**************************************************************

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.ArrayDeque;
import java.util.Arrays;

/**
 * An extended Adjacency Matrix graph implementation.  Complete this template for the exam.
 */
public class AdjMatGraphPlus<T> extends AdjMatGraph<T> implements GraphPlus<T> {
    // Variable instances
    private boolean[] marked; //boolean list for bfs and dfs
    private LinkedList<T> successorsList;
    private ArrayDeque<T> queue; // queue for bfs and dfs
    private LinkedList<T> pathList;
    private LinkedList<T> predecessors;
    private LinkedList<T> successors;
    private LinkedList<T> markedList;
    private Iterator<T> iter; // iterator of the graph vertices
    public T[] listVertices; // array for storing all the vertices
    private int count;
    private T vertex;
    private int preCount;
    private int index;
    private int totalNum; //total number of vertices
    /** DO NOT MODIFY THE FIRST THREE METHODS **************************
     * The methods you will implement follow below.
     */

    /**
     * Construct an empty graph.
     */
    public AdjMatGraphPlus() {
        super();
    }

    /**
     * Construct a graph with the same vertices and edges as the given original.
     *
     * @param original
     */
    public AdjMatGraphPlus(AdjMatGraph<T> original) {
        super(original);
    }

    /**
     * Read a TGF file and create an AdjMatGraphPlus<String> from it.
     *
     * @param tgfFile - the TGF file to read
     * @return a graph created from the TGF file
     * @throws FileNotFoundException if TGF file is not found.
     */
    public static AdjMatGraphPlus<String> fromTGF(String tgfFile) throws FileNotFoundException {
        AdjMatGraphPlus<String> g = new AdjMatGraphPlus<String>();
        AdjMatGraph.loadTGF(tgfFile, g);
        return g;
    }


    /**** IMPLEMENT THE METHODS BELOW *********************************
     * Replace "throw new UnsupportedOperationException();" with
     * a working implementation.
     ******************************************************************/


    /******************************************************************
     * Creates a new graph that has all the same vertices
     * and arcs as the original.
     *
     * @return the new graph.
     *****************************************************************/
    public GraphPlus<T> clone() {
        AdjMatGraphPlus newG = new AdjMatGraphPlus(this);
        return newG;
    }

    /******************************************************************
     * Checks if a vertex is a sink (points to no other vertex)
     *
     * @param vertex: the potential sink vertex
     * @return true if the vertex is a sink, false if it is not.
     * @throws IllegalArgumentException if given vertex is not in graph
     ******************************************************************/
    public boolean isSink(T vertex) {
        // throw new UnsupportedOperationException();
        if (!containsVertex(vertex)) {
            throw new IllegalArgumentException("This vertex is not in the graph"); // vertex is not in the graph
        } else {
            successors = getSuccessors(vertex);
            if (successors.size() == 0) {// check if the vertex has successors
                return true;
            }
        }
        return false;
    }


    /******************************************************************
     * Retrieves all vertices that are sinks.
     *
     * @return all the sinks in a linked list
     ******************************************************************/
    public LinkedList<T> allSinks() {
        Iterator<T> iter = iterator();
        LinkedList<T> sinks = new LinkedList<T>();
        while (iter.hasNext()) {
            T vertex = iter.next();
            if (isSink(vertex)) {
                sinks.add(vertex);
            }
        }
        return sinks;
    }

    /******************************************************************
     * Checks if a vertex is a source (no vertex points to it)
     *
     * @param vertex: the potential source vertex
     * @return true if the vertex is a source, false if it is not
     * @throws IllegalArgumentException if given vertex is not in graph
     ******************************************************************/
    public boolean isSource(T vertex) {
        if (!containsVertex(vertex)) {
            throw new IllegalArgumentException("This vertex is not in the graph");
        } else {
            predecessors = getPredecessors(vertex);
            if (predecessors.size() == 0) { // check if the vertex has predecessors
                return true;
            }
        }
        return false;
    }

    /******************************************************************
     * Retrieves all vertices that are sources.
     *
     * @return all the sources in a linked list
     ******************************************************************/
    public LinkedList<T> allSources() {
        // throw new UnsupportedOperationException();
        Iterator<T> iter = iterator();
        LinkedList<T> sources = new LinkedList<T>();
        while (iter.hasNext()) {
            T vertex = iter.next();
            if (isSource(vertex)) {
                sources.add(vertex);
            }
        }
        return sources;
    }

    /******************************************************************
     * Checks if a vertex is a isolated, i.e., no vertices
     * point to it and it points to no vertices.
     *
     * @param vertex: the vertex to check for isolation
     * @return true if the vertex is isolated, false if it is not
     * @throws IllegalArgumentException if given vertex is not in graph
     ******************************************************************/
    public boolean isIsolated(T vertex) {
        if (!containsVertex(vertex)) {
            throw new IllegalArgumentException("This vertex is not in the graph");
        } else {
            predecessors = getPredecessors(vertex);  // check if the vertex has predecessors or successors
            successors = getSuccessors(vertex);
            if ((predecessors.size() == 0) && (successors.size() == 0)) {
                return true;
            }
        }
        return false;
    }

    /******************************************************************
     * Returns a list of vertices in a directed acyclic graph (DAG)
     * such that for any vertices x and y, if there is a directed
     * edge from x to y in the graph then x comes before y in the
     * returned list. There may be multiple lists that satisfy the
     * abovementioned constraints. This method returns one such list.
     * If the input graph is not a DAG, a GraphCycleException is thrown.
     *
     * @return the vertices in a linked list satisfying the order described above.
     * @throws GraphCycleException if called on a non-DAG
     ******************************************************************/
    public LinkedList<T> listByPriority() throws GraphCycleException {
        markedList = new LinkedList<T>(); // linked list for storing the vertices in the right order
        iter = iterator();
        listVertices = (T[]) new Object[n()];
        count = 0;
        while (iter.hasNext()) {
            vertex = iter.next();     // create an array to store all the vertices
            listVertices[count] = vertex;
            count++;
        }

        count = 0;
        totalNum = n();
        preCount = 0;
        while (count < totalNum) {
            preCount = count;
            for (int i = 0; i < listVertices.length; i++) {  // in every while loop only pick vertices  which doesnt have predecessors
                if (listVertices[i] != null) {
                    vertex = listVertices[i];
                    predecessors = getPredecessors(vertex);
                    successors = getPredecessors(vertex);
                    if (predecessors.size() == 0) {
                        removeVertex(vertex); // remove the vertex out from the matrix
                        markedList.add(vertex); // add to the output list
                        listVertices[i] = null; // remove it from the vertex list
                        count++;
                    }
                }
            }
            if (preCount == count) { // when preCount == count, the function cannot find more vertices inthe loop that
                throw new GraphCycleException(); // dont have predecessors
            }
        }
        return markedList;
    }


    public LinkedList<T> dfsTraversal(T startVertex) {
        queue = new ArrayDeque<T>(); // initialize queue for storing vertices
        marked = new boolean[n()]; // initialize boolean array for marking past vertices
        successorsList = new LinkedList<T>();
        pathList = new LinkedList<T>(); // output list for storing path

        queue.add(startVertex); // store the first vertex into the queue

        while (!queue.isEmpty()) {
            vertex = queue.pollFirst(); // get the first out of the queue
            index = getIndex(vertex);
            if (marked[index] != true && !pathList.contains(vertex)) { // check if we alreay check this vertex or not
                pathList.add(vertex);                                   // and if it is already in output list
            }
            marked[index] = true; // mark the vertex


            successorsList = getSuccessors(vertex);  // find this vertex's sucessors
            for (int i = 0; i < successorsList.size(); i++) {
                vertex = successorsList.get(i);
                index = getIndex(vertex);

                if (marked[index] != true) {
                    queue.addFirst(vertex); // add its sucessors into the front of the queue
                }
            }
        }
        return pathList;
    }

    public LinkedList<T> bfsTraversal(T startVertex) {
        queue = new ArrayDeque<T>(); // initialize queue for storing vertices
        marked = new boolean[n()]; // initialize boolean array for marking past vertices
        successorsList = new LinkedList<T>();
        pathList = new LinkedList<T>(); // output list for storing path

        queue.add(startVertex); // store the first vertex into the queue

        while (!queue.isEmpty()) {
            vertex = queue.pollFirst(); // get the first out of the queue
            index = getIndex(vertex);
            if (marked[index] != true && !pathList.contains(vertex)) { // check if we alreay check this vertex or not
                // and if it is already in output list
                pathList.add(vertex);
            }
            marked[index] = true;   // mark the vertex

            successorsList = getSuccessors(vertex); // find this vertex's sucessors
            for (int i = 0; i < successorsList.size(); i++) {
                vertex = successorsList.get(i);
                index = getIndex(vertex);

                if (marked[index] != true) {
                    queue.add(vertex); // add its sucessors to the end of the queue
                }
            }
        }
        return pathList;
    }

    public static void main(String[] args) {
        System.out.println("NORMAL OPERATIONS");
        System.out.println("_________________");
        AdjMatGraphPlus<String> G = new AdjMatGraphPlus<String>();
        System.out.println("New graph is empty  (true): \t" + G);
        System.out.println("Empty=> undirected  (true): \t" + G.isUndirected());
        System.out.println("Empty graph no vertices(0): \t" + G.n());
        System.out.println("Empty graph no arcs    (0): \t" + G.m());
        G.addVertex("A");
        G.addVertex("B");
        G.addVertex("C");
        G.addVertex("D");
        G.addVertex("E");
        G.addVertex("F");

        int count = 0;
        String[] vertices = Arrays.stream(G.vertices).toArray(String[]::new);
        // System.out.println(((String)G.vertices[count]));
        // System.out.println(G.vertices[count]);
        // String vertex = G.vertices[count];
        // count += 1;
        // System.out.println(vertex);
        // while(count != G.vertices.length){
        //   vertex = G.vertices[count];
        //   count += 1;
        //   System.out.println(vertex);
        // }

        // System.out.println("After adding 6 vert.   (6): \t " + G.n());
        // System.out.println("After adding no arcs   (0): \t" + G.m());
        // System.out.println("Still is undirected (true): \t" + G.isUndirected());
        // System.out.println("vertex A is a sink: \t" + G.isSink("A"));
        // G.addEdge("A", "B");
        // G.addArc("C", "D");
        // System.out.println("All sink vertices: \t" + G.allSinks());
        // System.out.println("All source vertices: \t" + G.allSources());
        // System.out.println("vertex E is isolated: \t" + G.isIsolated("E"));
        // G.addEdge("B", "C");
        // G.addEdge("F", "A");
        // G.addEdge("A", "D");
        // G.addArc("F", "E");
        // System.out.println(G);
        // System.out.println(G.dfsTraversal("A"));
        // System.out.println(G.bfsTraversal("A"));
        // System.out.println(G.clone());

        // AdjMatGraphPlus<String> E = new AdjMatGraphPlus<String>();
        // E.addVertex("A");
        // E.addVertex("B"); E.addVertex("C");
        // E.addVertex("D"); E.addVertex("E");
        // E.addArc("A", "C");
        // E.addArc("A", "B");
        // E.addArc("C", "B");
        // E.addArc("A", "D");
        // E.addArc("D", "E");
        // E.addArc("E", "C");
        // System.out.println(E);
        // try{
        //  System.out.println(E.listByPriority());
        // }
        // catch (GraphCycleException e){
        //  System.out.println(e);
        // }
    }
}