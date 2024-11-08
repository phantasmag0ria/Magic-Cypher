public class MagicSquare {

    int order;
    int magicConstant;
    int[][] matrix; 

    public  void setOrder(int order){

        this.order = order;
        
    }

    public void setSquare(int[][] square){

      this.matrix = square; 
    }

    public void getMagicConstant(){

        
        int n = this.order; 
        
        int magicConstant = n*(n*n+1)/2;

        this.magicConstant = magicConstant;    
        
        System.out.println(this.magicConstant);

    }

    

   
// print magic square to the console

    public void printSquare(){

      if(this.matrix == null){
        System.out.println("null matrix detected");
      }

      int[][] square = this.matrix; 

      for(int i = 0 ; i < square.length ;i ++){

          System.out.println("\n");

          for( int j = 0 ; j < square.length ; j ++){
              
              System.out.print( " " + square[i][j] +" ");

          }
      }
      System.out.println("\n");
  }

    public boolean isMagic(){

      int magicConstant = this.magicConstant; 

      int[][] square = this.matrix;

      int N = this.order;

      System.out.println("Running test on magic square of order " + N + " ...");

      System.out.println("In order to be magic all rows, columns, and cross diagonals must equal " + magicConstant);

      System.out.println("Evaluating current square:  ");

      this.printSquare();

      int i; // row

      int j; // column

      // test each row and column     

      for(i = 0; i<N ; i++ ){

         int sumRows = 0;
         int sumColumns = 0;
        
        for(j = 0; j<N ;j++){
          
          sumRows+= square[i][j]; 

          sumColumns+= square[j][i];
          
          
          if(j==N-1 && (sumRows!=magicConstant || sumColumns!=magicConstant)){
            
            System.out.println("Error: one of the rows or columns does not equal the magic constant");
            return false;
          }

        }

      }
      
      System.out.println("Hurray all the rows and columns equal the magic constant " + magicConstant);
      
      // test each cross diagnol
      
      i = 0 ; 
      j = 0 ;

      int sumLeftDiagonal = 0; 

      int sumRightDiagonal = 0; 

      while( i < N && j< N){

        sumLeftDiagonal += square[i][j];          //  left to right diagonal

        sumRightDiagonal += square[N-1-i][N-1-j]; //  right to left diagonal

        i++;
        j++ ; 

      }

      if(sumLeftDiagonal == magicConstant && sumRightDiagonal == magicConstant){
        System.out.println("Hurray both cross diagonals equal the magic constant " + magicConstant + "\n");
        System.out.println("All tests passed, fantastic! That's a magic square");
      }else{
        System.out.println("Error: one of the cross diagonals do not equal the magic constant "+ magicConstant);
      }


      return sumLeftDiagonal == magicConstant && sumRightDiagonal == magicConstant;

    }
    
}
