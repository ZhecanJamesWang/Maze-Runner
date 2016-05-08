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
  
  public Search(){ 
    mz = new Maze("level1.txt");
    G = new AdjMatGraphPlus<Integer>();
    startVertex = -1;
  }
  
  
  
  private int getTupleIndex(Tuple tuple) {
    for (int i = 0; i < tuples.size(); i++) {
      if (tuples.get(i).equals(tuple)) {
        return i;
      }
    }
    return NOT_FOUND;
  }
  
  private Tuple getTuple(int index){
    return tuples.get(index);
  }
  
  
  public void setStartPoint(int row, int col){
    Tuple start = new Tuple(row, col);
    int startVertex = getTupleIndex(start);
  }
  
  public int[][] getArray(){
    int [][] matrix = mz.getMap();
    uniqueMatrix = new int[matrix.length][matrix[0].length];
    return matrix;
  }
  
  
  public void uniqueMatrix(){
    int count = 1;
    int [][] matrix = getArray();
    for ( int row = 0; row < matrix.length; row++ )
    {
      for ( int col = 0; col < matrix[row].length; col++ )
      {
        if(matrix[row][col] != 1){
          if (matrix[row][col] == 2) {
            uniqueMatrix[row][col] = -1;
          }else if(matrix[row][col] == 3){
            uniqueMatrix[row][col] = -2;
          }else{
            uniqueMatrix[row][col] = count;
            count += 1;
          }
        }else{
          uniqueMatrix[row][col] = 0;
        }
        // System.out.print(matrix[row][col]);
      } 
      // System.out.println();
    }
    
    
    for ( int row = 0; row < matrix.length; row++ )
    {
      for ( int col = 0; col < matrix[row].length; col++ )
      {
        if (uniqueMatrix[row][col] > 9){
          System.out.print(uniqueMatrix[row][col]);
          System.out.print(" ");
        }else{
          System.out.print(uniqueMatrix[row][col]);
          System.out.print("  ");     
        }
      } 
      System.out.println();
    }
  }
  
  
  public void findNeighbors(int value, int row, int col){
    for (int r = -1; r <= 1; r ++){
      try{
        if (uniqueMatrix[row+r][col] != 0){
          if(G.containsVertex(uniqueMatrix[row+r][col])){
            G.addVertex(uniqueMatrix[row+r][col]);
          }   
          G.addEdge(value, uniqueMatrix[row+r][col]);
        } 
      }catch (ArrayIndexOutOfBoundsException e) {}
    }
    for (int c = -1; c <= 1; c++){
      if (uniqueMatrix[row][col+c] != 0){
        if(G.containsVertex(uniqueMatrix[row][col+c])){
          G.addVertex(uniqueMatrix[row][col+c]);
        }   
        G.addEdge(value, uniqueMatrix[row][col+c]);
      }
    }
  }
  
  public void arraytoGraph(){
    Tuple tuple = new Tuple(0,0);
    for ( int row = 0; row < uniqueMatrix.length; row++ )
    {
      for ( int col = 0; col < uniqueMatrix[row].length; col++ )
      {
        int value = uniqueMatrix[row][col];
        if ( value != 0 )
        {
          G.addVertex(value);
          tuple = new Tuple(row, col);
          tuples.add(tuple);
          
          if ( value == -1 ){
            startNode = value;    
          }
          else if ( value == -2 ){
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
    while(value != -2){
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
    while(value != -2){
      value = pathList.removeFirst();
      newPathList.add(value);
    }
    return newPathList;
  }
  
  public Tuple traceBack(int count1, ArrayList<Tuple> path, ArrayList<Tuple> steps, Tuple tmpStep1, Tuple tmpStep2){
    Tuple tupe;
    Tuple tmpStep3;
    int count2 = 0;
    
    count2 = count1;
	 count2 -= 2;
    
    while(true){
      count2 -= 1;
      System.out.println("count2");
      System.out.println(count2);
      
      tmpStep3 = path.get(count2);
      System.out.println("tmpStep3");
      System.out.println(tmpStep3);
      
      if((tmpStep3.x == tmpStep2.x) || (tmpStep3.y == tmpStep2.y)){
        System.out.println("add 2");
        tupe = new Tuple((tmpStep3.x - tmpStep1.x), (tmpStep3.y - tmpStep1.y));
        steps.add(tupe);
        System.out.println("Second matched");
        break;
      }else{
        System.out.println("add 2");
        tupe = new Tuple((tmpStep3.x - tmpStep1.x), (tmpStep3.y - tmpStep1.y));
        steps.add(tupe);
      }
  tmpStep1 = tmpStep3;
  System.out.println("tmpStep1");
  System.out.println(tmpStep1);
    }
    return tmpStep3;
  }
  
  public ArrayList<Tuple> pathFinding(String method, Boolean needRelativeSteps){
    LinkedList<Integer> pathList = new LinkedList<Integer>();
    ArrayList<Tuple> path = new ArrayList<Tuple>();  
    ArrayList<Tuple> steps = new ArrayList<Tuple>();
    if(method.equals("bfs")){
      pathList = bfsTraversal();
    }else if(method.equals("dfs")){
      pathList = dfsTraversal();
    }else{
      System.out.println("Method not found");
    }
    
    while(pathList.size() != 0){
      int vertex = pathList.removeFirst();
      int index = G.getIndex(vertex);
      Tuple tuple = getTuple(index);
      path.add(tuple);
    }
    
    if(needRelativeSteps){
      Tuple tupe;
      Tuple tmpStep1;
      Tuple tmpStep2;
      Tuple tmpStep3;
      int count1 = 0;
      int count2 = 0;
      
      tmpStep1 = path.get(count1);
      System.out.println("tmpStep1");
      System.out.println(tmpStep1);
      count1 += 1;
      
      
      
      while(path.size() != count1){
        
        System.out.println("count1");
        System.out.println(count1);
        
        tmpStep2 = path.get(count1);
        System.out.println("tmpStep2");
        System.out.println(tmpStep2);
        
        count1 += 1;
        System.out.println("size of the steps");
        System.out.println(steps.size() );
        
        if ((tmpStep1.x == tmpStep2.x) || (tmpStep1.y == tmpStep2.y)){
          System.out.println("matched");
          tupe = new Tuple((tmpStep2.x - tmpStep1.x), (tmpStep2.y - tmpStep1.y));
          steps.add(tupe);
        }else{
   System.out.println("NOT matched");
          tmpStep1 = traceBack(count1, path, steps, tmpStep1, tmpStep2);
          tupe = new Tuple((tmpStep2.x - tmpStep1.x), (tmpStep2.y - tmpStep1.y));
          steps.add(tupe);
        }
        tmpStep1 = tmpStep2;
        System.out.println("tmpStep1");
        System.out.println(tmpStep1);
        
      }
      return steps;
    }else{
      return path;
    }
  }
  
  
  
  public static void main(String[] args) {
    Search search = new Search();
    search.uniqueMatrix();
    search.arraytoGraph();
    search.setStartPoint(0, 1);
    System.out.println(search.dfsTraversal());
    System.out.println(search.pathFinding("dfs", false));
    System.out.println(search.pathFinding("dfs", true));
    // System.out.println(search.pathFinding("dfs", true).size());
    
    
    
    // System.out.println(search.pathFinding("dfs", false);
    
    // System.out.println(search.bfsTraversal(-1));
    // System.out.println(search.G);
    // System.out.println(search.tuples);
    
  }
  
}


