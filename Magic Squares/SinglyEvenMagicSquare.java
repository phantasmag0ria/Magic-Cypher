public class SinglyEvenMagicSquare extends MagicSquare{
    
    int[][] upperLeftSquare;
    int[][] lowerLeftSquare; 
    int[][] tempSquare; 

    public static void main(String[] args) {
        
        SinglyEvenMagicSquare square = new SinglyEvenMagicSquare();

        // steps that must be followed in constructor===> I guess this is not called a constructor

        // step 1) 
        // invoke setOrder but N must be singlyEven:
        square.setOrder(10);               
        
        // step 2) call generateSquare Method:
        square.getMagicConstant();
        square.generateSinglyEvenSquare();

        // now you have these other methods available:
        square.printSquare();

        // note that this wont work unless getMagicConstant is invoked
        // @TODO need tot fix this. Maybe setOrder should also set magic constant
        // or getMagicConstant should be renamed to setMagicConstant
        if(square.isMagic()){
            System.out.println("Wahooo! you made it here singly even is the most complex!");
        }



    }

    

    public void generateSinglyEvenSquare(){

        int N = this.order;  
        

        OddMagicSquare tempSquare = new OddMagicSquare(); 

        // strategy:

        // break the square up into 4 odd order magic squares
        // and fill them up with numbers 1 through N^2 using 
        // the odd magic square pattern

           
        // create temp odd magic squares 
        this.tempSquare = tempSquare.generateOddMagicSquare(N/2);


        int[][] singlyEvenSquare = new int[N][N];

        for(int i = 0 ; i < N ; i ++){

            for( int j = 0 ; j <N ;j++){
                
                if(i<N/2 && j<N/2){
                    // upper left square... fill upper left square with numbers 1 through N^2/4
                    singlyEvenSquare[i][j] = this.tempSquare[i][j];
                }
                else
                if( i>=N/2 && j>= N/2){
                    // lower right square...                         adjust numbers by 2*N^2/4
                    singlyEvenSquare[i][j] = this.tempSquare[i%(N/2)][j%(N/2)] + (N/2)*(N/2) ; 
                }else                
                if(i<N/2 && j>=N/2 ){ 
                    // upper right square...                          adjust numbers by N^2/4
                    singlyEvenSquare[i][j] = this.tempSquare[i%(N/2)][j%(N/2)] + 2*(N/2)*(N/2) ;
                
                }else              
                if(i>=N/2 && j<N/2){
                    // lower left square...                          adjust numbers by 3*N^2/4
                    singlyEvenSquare[i][j] = this.tempSquare[i%(N/2)][j%(N/2)] + 3*(N/2)*(N/2); 

                }


            }
        }

        // left side row swaping

        // where N is singly even:
        // f(N) = (N+2)/4 => f(6) = 1 ; f(10) = 2 ; f(14) = 3; f(18) = 4 ; ...etc

        int tempLeft;  // initialize temp var for left side swaping

        for(int j = 0 ; j < (N+2)/4 -1; j++){
            // columns

            for(int  i = 0 ; i < N/2 ;i++){
                // rows               

                if(i== (int) Math.floor(N/4) ){
                // special case shift over one column

                // shift over one column when row is in the midle of
                // the upper left square

                // swap M[i][j+1] with M[i+N/2][j+1]

                tempLeft = singlyEvenSquare[i][j+1];

                singlyEvenSquare[i][j+1] = singlyEvenSquare[i+N/2][j+1];
                
                singlyEvenSquare[i+N/2][j+1] = tempLeft;

                }else{
                // swap M[i][j] with M[i+N/2][j]

                tempLeft = singlyEvenSquare[i][j];

                singlyEvenSquare[i][j] = singlyEvenSquare[i+N/2][j];
                
                singlyEvenSquare[i+N/2][j] = tempLeft;

                }

            }

        }

        // right side row swaping swaping

        // where N is singly even:
        // f(N) = (N+2)/4-2 => f(6) = 0 ; f(10) = 1 ; f(14) = 2; f(18) = 3 ; ...etc

        int tempRight;

        for(int j = 0 ; j < (N+2)/4 -2 ; j++){
            // columns

            for(int i = 0 ; i < N/2 ; i++){
                // rows

                // swap upper right rows in the (N-j-1) column with the corresponding (i+N/2) lower right row

                tempRight = singlyEvenSquare[i][N-j-1];

                singlyEvenSquare[i][N-j-1] = singlyEvenSquare[i+N/2][N-j-1];

                singlyEvenSquare[i+N/2][N-j-1] = tempRight;

               
            }
        }   

        // ... and finally invoke setSquare method inherited from MagicSquares Class
      
        this.setSquare(singlyEvenSquare);

    }
}
