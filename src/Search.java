import java.util.*;

public class Search {
    public static final int NOT_FOUND = -1;
    private Maze mz;
    private int[][] uniqueMatrix;
    public AdjMatGraphPlus<Integer> G;
    private int startNode;
    private int startVertex;
    private int endNode;
    public ArrayList<Tuple> tuples = new ArrayList<Tuple>();
    private int endVertex;

    public Search(Maze mz) {
        this.mz = mz;
        G = new AdjMatGraphPlus<Integer>();
        startVertex = -1;
        endVertex = -2;
    }

    private Tuple getTuple(int index) {
        return tuples.get(index);
    }

    public void setStartPoint(int row, int col) {
        Tuple start = new Tuple(row, col);
        int startIndex = getTupleIndex(start);
        startVertex = G.getVertex(startIndex);
    }

    public void setEndPoint(int row, int col) {
        Tuple end = new Tuple(row, col);
        int endIndex = getTupleIndex(end);
        endVertex = G.getVertex(endIndex);
    }

    private int getTupleIndex(Tuple tuple) {
        for (int i = 0; i < tuples.size(); i++) {
            if ((tuples.get(i).x == tuple.x) && (tuples.get(i).y == tuple.y)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    public int[][] getArray() {
        int[][] matrix = mz.getMap();
        uniqueMatrix = new int[matrix.length][matrix[0].length];
        return matrix;
    }

    public void uniqueMatrix() {
        int count = 1;
        int[][] matrix = getArray();
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (matrix[row][col] != 1) {
                    if (matrix[row][col] == 2) {
                        uniqueMatrix[row][col] = -1;
                    } else if (matrix[row][col] == 3) {
                        uniqueMatrix[row][col] = -2;
                    } else {
                        uniqueMatrix[row][col] = count;
                        count += 1;
                    }
                } else {
                    uniqueMatrix[row][col] = 0;
                }
            }
        }

//        Testing used for printing out the matrix path
//        for (int row = 0; row < matrix.length; row++) {
//            for (int col = 0; col < matrix[row].length; col++) {
//                if (uniqueMatrix[row][col] > 9) {
//                    System.out.print(uniqueMatrix[row][col]);
//                    System.out.print(" ");
//                } else {
//                    System.out.print(uniqueMatrix[row][col]);
//                    System.out.print("  ");
//                }
//            }
//            System.out.println();
//        }
    }


    public void findNeighbors(int value, int row, int col) {
        for (int r = -1; r <= 1; r++) {
            try {
                if (uniqueMatrix[row + r][col] != 0) {
                    if (G.containsVertex(uniqueMatrix[row + r][col])) {
                        G.addVertex(uniqueMatrix[row + r][col]);
                    }
                    G.addEdge(value, uniqueMatrix[row + r][col]);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }
        for (int c = -1; c <= 1; c++) {
            if (uniqueMatrix[row][col + c] != 0) {
                if (G.containsVertex(uniqueMatrix[row][col + c])) {
                    G.addVertex(uniqueMatrix[row][col + c]);
                }
                G.addEdge(value, uniqueMatrix[row][col + c]);
            }
        }
    }

    public void arraytoGraph() {
        Tuple tuple = new Tuple(0, 0);
        for (int row = 0; row < uniqueMatrix.length; row++) {
            for (int col = 0; col < uniqueMatrix[row].length; col++) {
                int value = uniqueMatrix[row][col];
                if (value != 0) {
                    G.addVertex(value);
                    tuple = new Tuple(row, col);
                    tuples.add(tuple);

                    if (value == -1) {
                        startNode = value;
                    } else if (value == -2) {
                        endNode = value;
                    }
                    findNeighbors(value, row, col); //assuming nodes and matrix variables are instance variables
                }
            }
        }
    }

    public LinkedList<Integer> dfsTraversal() {
        LinkedList<Integer> pathList = G.dfsTraversal(startVertex);
        LinkedList<Integer> newPathList = new LinkedList<Integer>();
        int value = pathList.removeFirst();
        newPathList.add(value);
        while (value != endVertex) {
            value = pathList.removeFirst();
            newPathList.add(value);
        }
        return newPathList;
    }

    public LinkedList<Integer> bfsTraversal() {
        LinkedList<Integer> pathList = G.bfsTraversal(startVertex);
        LinkedList<Integer> newPathList = new LinkedList<Integer>();
        int value = pathList.removeFirst();
        newPathList.add(value);
        while (value != endVertex) {
            value = pathList.removeFirst();
            newPathList.add(value);
        }
        return newPathList;
    }

    public ArrayList<Tuple> pathFinding(String method) {
        LinkedList<Integer> pathList = new LinkedList<Integer>();
        ArrayList<Tuple> path = new ArrayList<Tuple>();
        ArrayList<Tuple> steps = new ArrayList<Tuple>();

        if (method.equals("bfs")) {
            pathList = bfsTraversal();
        } else if (method.equals("dfs")) {
            pathList = dfsTraversal();
        } else {
            System.out.println("Method not found");
        }

        while (pathList.size() != 0) {
            int vertex = pathList.removeFirst();
            int index = G.getIndex(vertex);
            Tuple tuple = getTuple(index);
            path.add(tuple);
        }

        return path;
    }


    public static void main(String[] args) {
        Maze mz = new Maze("level1.txt");
        Search search = new Search(mz);
        search.uniqueMatrix();
        search.arraytoGraph();

        search.setStartPoint(13, 13);
        search.setEndPoint(1, 1);

        System.out.println(search.dfsTraversal());
        System.out.println(search.pathFinding("dfs"));


    }

}


