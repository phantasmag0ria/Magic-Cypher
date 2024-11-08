public class OddMagicSquare extends MagicSquare {



    public static void main(String[] args) {

        OddMagicSquare oddSquare = new OddMagicSquare();
        
        // step 1) invoke set order... order N must be odd:
        oddSquare.setOrder(9);

        // now magic constant can return something meanginful
        oddSquare.getMagicConstant();

        // note that generateOddMagicSquare returns a 2D array 
        // but the other magic square child classes do not.
        // this is because odd magic square sub class is needed
        // in singly even magic squares class to construct singly even
        // magic square and a return value is needed in the construction
        int[][] OddMagicSquare = oddSquare.generateOddMagicSquare(9);

        // changing parent class square/matrix property/field
        // not that this is necessary because I cannot setSquare in 
        // generation method or it will mess up singly Even square construction
        // when it is called there. 
        oddSquare.setSquare(OddMagicSquare);
        
        // now these other methods are available because we have called setSquare
        oddSquare.printSquare();

        oddSquare.isMagic();
        
    }

    public int[][] generateOddMagicSquare(int order){

        if(order%2!=1){
            System.out.println("Error: Order is not odd");
        }

        int N = order; 

        int[][] square = new int[N][N];


        int middleColumn = (int) Math.floor(N/2) ;

        int i = 0;             // i starts in the first row
        int j = middleColumn;  // j starts in the middle column    
        int n = 1;             // numbers that fill the square with
        
    
        while(  n<=N*N ){

            square[i][j] = n;

            if (i - 1 < 0 && j + 1 < N) {

                // rule 1
                // if incrementing up and to the right results in
                // row being out of bound and column is still in bounds
                // let row = bottom row 
          
                i = N - 1;
                j++;
          
              }else
              if (i - 1 >= 0 && j + 1 >= N) {
                // rule 2
                // if incremenitng up and the right results in column being out of bounds
                // but row is still in bounds, let column = the first column ;
          
                j = 0;
                i--;
          
              }else         
              if (i - 1 < 0 && j + 1 >= N) {
                // rule 3
                // if incrementing up and to the right results 
                // in both i and j being out of bounds, go down one row 
                // in the same column
          
                i++;
          
                // note that Rule 3 must go before rule 4 because it catches 
                // rule 4 from creating error out of bounds
              }else   
              if (square[i-1][j+1] != 0 && i+1<N) {
          
                // rule 4
                // cell is occupied go down one row
               
                i++;
              }
              else{
                // No cases match increment going up and to the right
                i--;
                j++;
              }

            n++;
            

        }

        // this.setSquare(square);
        // @TODO for sake of consistency I should probaly have all the 
        // genertion methods of the squares return int[][] for clairity 

        return square;
    }
    
}
