import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MagicCypher {

    // order of magic square
    protected int order;
    // ciphered message
    protected String message;

    // keep track of where we've added random char for decryption
    private ArrayList<Integer> indicesOfWhiteSpace = new ArrayList<>();

    // array list (rows) of maps containing key values of an integer (column) and a
    // map
    // containing key values of an Integer (index of char in message) and a string
    // (the char in the message)

    public ArrayList<ArrayList<Map<Integer, String>>> square = new ArrayList<>();

    // a map of all the string charaters in the message and thier index posiition

    public ArrayList<Map<Integer, String>> charMapList = new ArrayList<>();

    // ledger of what's been added to our orignal message we are trying to encrypt.
    protected Map<Integer,String> ledgerMap = new HashMap<>();

    // ===============main method testing=============

    public static void main(String[] args) {
        // MagicCypher myCypher = new MagicCypher();
        // myCypher.encryptMessage("Hello My");
        // System.out.print(myCypher.message);

        File file = new File("message.txt");

        MagicCypher cipher2 = new  MagicCypher();
        cipher2.encryptMessage(file);
        System.out.println("\n"+cipher2.message +"\n");    
    }

    // =============== setters ========================

    private void setMessage(String message) {
        this.message = message;
    }

    // =============== meat and potatoes ======================

    private String encryptMessage(String message) {

        // 1) remove any leading or trailing white space
        message = message.trim(); 

        // 2) determine the order of a square matrix that can fit the message
        this.order = calculateOrder(message.length());

        // 3) sanitize the message for encryption
        String scrubedMessage = sanitizeMessage(message);
        setMessage(scrubedMessage);

        // 4) create a character map containng the values of each char in the message
        // and their index
        generateCharMap(scrubedMessage);

        // 5) create a matrix with empty maps in each cell
        createEmptyMatrix(order);

        // 6) determine approriate child class to handle encryption:
        determineCypherAlgorithm();

        return scrubedMessage;
    }

    // same as above but for files
    private String encryptMessage(File file) {

        // 1) read the message
        String readMessage = readFile(file);

        // 2 remove any leading or trailing white space
        readMessage = readMessage.trim();

        // 3) calculate the order of a square matrix that can fit our message
        this.order = calculateOrder(readMessage.length());

        // 4) sanitize the message to get ready for encryption
        String scrubedMessage = sanitizeMessage(readMessage);
        setMessage(scrubedMessage); 

        // 5) create a character map containng the values of each char in the message
        // and their index
        generateCharMap(scrubedMessage);

        // 6) create an matrix with empty maps in each cell
        createEmptyMatrix(order);

        // 7) determine approriate child class to handle encryption:
        determineCypherAlgorithm();

        return scrubedMessage;
    }

    // ==============================helper methods==================

    // calculatMagicConstant;
    protected double calculateMagicConstant(double order) {

        double n = order;

        return n * (n * n + 1) / 2;

    }

    // test's children classes encryption algorithms to make sure they have magic
    // properties
    protected boolean isMagic(ArrayList<ArrayList<Map<Integer, String>>> magicSquare) {
        
        // this method loops through each row and column of the array list
        // and iterates over every key in the list and adds them up to see if they equal the magic constant
        // then it checks the two diagonals to see if they equal the magic constant
        // if all tests pass then it returns true.
        // if one of the sums don't equal the magic constant then it thows an illegal state exception;


        Map<Integer, String> cellInColumn = new HashMap<>();
        Map<Integer, String> cellInRow = new HashMap<>();

        int sumRow;
        int sumColumn;
        double magicConstant = calculateMagicConstant(magicSquare.size());
        for (int i = 0; i < magicSquare.size(); i++) {

            sumRow = 0;
            sumColumn = 0; 

            for (int j = 0; j < magicSquare.size(); j++) {

                // rows
                cellInRow = magicSquare.get(i).get(j); // returns a map

                // columns
                cellInColumn = magicSquare.get(j).get(i); // returns a map

                // add up all the keys in each row

                // get the key in each cell @TODO can this be faster if I know there is always
                // only one map in each column
                for (Integer key : cellInRow.keySet()) {
                    // the keys represnt the index of the char in the string
                    // because they are zero indexed we need to add 1 to each key
                    sumRow += key + 1;
                }

                // add up all the keys in each column
                for (Integer key : cellInColumn.keySet()) {
                    // the keys represnt the index of the char in the string
                    // because they are zero indexed we need to add 1 to each key
                    sumColumn += key + 1;

                }

            }

            if( sumRow != magicConstant){
                // if the sum in a row is not equal to the magic constant then the algorithm was not performed 
                // correctly 

                throw new IllegalStateException("Row: "+ (i+1) +" does not equal the magic constant " );
            
            }
            if( sumColumn != magicConstant){
                // if the sum in a column is not equal to the magic constant then the algorithm was not performed 
                // correctly 

                throw new IllegalStateException("column: "+ (i+1) +" does not equal the magic constant " );

            }

            

        }
        if(!isDiagonalsMagic(magicSquare, magicConstant)){
    
            throw new IllegalStateException("one of the diagonals does not equal the magic constant " );
        }

        System.out.println("magic cypher algorithm performed correctly!");

        
        // if all these tests passed then the encryption algorithm was performed correctly
        return true;
    }

    private boolean isDiagonalsMagic(ArrayList<ArrayList<Map<Integer, String>>> magicSquare,double magicConstant){

        //tests to see if the 2 diagonals of the square sum to the magic constant
        // if they do it returns true, otherwise it return false;

        // row
        int i = 0;

        // column
        int j = 0; 

    
        Map<Integer,String> cellLeft = new HashMap<>();
        Map<Integer,String> cellRight = new HashMap<>();

        int sumLeftDiagonal = 0; 
        int sumRightDiagonal= 0;

        int N = magicSquare.size();

        while(i<magicSquare.size()){
            
            // move from top left to bottom right diagonally
            cellLeft = magicSquare.get(i).get(j);

            // move from top right to bottom left diagonally 
            cellRight = magicSquare.get(i).get(N-j-1);
            
            // sum up the key's in each cell
            for(Integer key: cellLeft.keySet()){

                // the keys represent the index of the char in the messge
                // bc they are zero indexed we need to adjust by + 1 to 
                // to check their magic properties            

                sumLeftDiagonal+=key+1;
            }

            for(Integer key: cellRight.keySet()){

                // the keys represent the index of the char in the messge
                // bc they are zero indexed we need to adjust by + 1 to 
                // to check their magic properties 

                sumRightDiagonal+=key+1;
            }

            i++;
            j++;
        }
        
        // if one of the diagonals does not equal the magic constant then return false
        if(sumLeftDiagonal!=magicConstant || sumRightDiagonal!= magicConstant){
        
            return false;
        }
        //otherwise 
        return true; 
    } 

    private String determineCypherAlgorithm() {

        if (order % 2 == 0) {

            if (order % 4 == 0) {
                System.out.println("that's a double even magic square ");
            } else {
                System.out.println("that's a singly even magic square");
            }
        } else if (order % 2 == 1) {
            OddMagicCypher oddCypher = new OddMagicCypher(order, charMapList, square);
            String cypheredText = oddCypher.generateCypher();

            // update the message field and overwrite original input with cipher
            setMessage(cypheredText);
            // System.out.println(cypheredText);
        }

        return message;

    }

    private int calculateOrder(int lengthOfMessage) {

        // Now we need to figure out what square will fit our message
        // example message = "12345678"

        // the string message contains 8 char's
        // So the smallest square that will fit 8 char's is
        // a 3x3 square which has room for 9 char's

        int order = (int) Math.ceil(Math.sqrt(lengthOfMessage));

        if (order < 3) {
            // we can not make magic squares of order less then 3
            // except the trivial magic square of order 1;
            return 3;
        } else {
            return order;
        }

    }

    private void generateCharMap(String message) {
        // updates charMap field
        // arraylist of maps containing:
        // key: index of char in message
        // value: char in message

        int index = 0;

        while (index < message.length()) {

            Map<Integer, String> charMap = new HashMap<>();

            charMap.put(index, "" + message.charAt(index));

            charMapList.add(charMap);

            index++;
        }

        // System.out.println(charMapList);
    }

    private void createEmptyMatrix(int order) {
        // create an empty matrix to be passed off to children
        // arrayList (row) of arrayList (column) of maps (cell)

        for (int i = 0; i < order; i++) {

            ArrayList<Map<Integer, String>> row = new ArrayList<>();

            for (int j = 0; j < order; j++) {

                // create an empty map in each cell
                // to use for comparison purposes in children classes
                // to determine occupancy

                Map<Integer, String> cell = new HashMap<>();

                row.add(cell);

            }

            square.add(row);

        }

    }



    private String sanitizeMessage(String message) {
        // clean up the message to get ready for encryption

        // @TODO: optimize so that it doesn't loop if there are no whitespaces
        // or maybe put that check before we even get here
        // maybe use regEx?

        String tempMessage = "";
        String comparisonString;
        int indexOfWhiteSpaceInMessage; // @TODO finish this
        for (int i = 0; i < message.length(); i++) {

            comparisonString = "" + message.charAt(i);

            if (comparisonString.equals(" ")) {

                // fill in spaces between words with random char
                tempMessage += "X";

                // @TODO this should actually fill it with a random char;
                // Jake you wanna tackle this one?

                // @TODO add the index of where in the string we've added a random char

            } else {
                tempMessage += comparisonString;

            }
        }

        // need to deal with when message length is less than N^2
        // ie continue adding random char's until it is length is n^2

        while (tempMessage.length() < order * order) {

            tempMessage += "Z";
            // @TODO this should actually fill it with a random char;
            // @TODO need to keep track of what we've added so that it
            // isn't included in decryted message.

        }
        return tempMessage;
    }

    // Helper method for MagicCypher with file input
    private String readFile(File file) {

        String message = "";
        String lineBreak;

        try {
            // Create a Scanner object to read the file
            Scanner scanner = new Scanner(file);

            // Read the file line by line
            while (scanner.hasNextLine()) {
                if (scanner.hasNextLine()) {
                    // adjust for line breaks
                    lineBreak = " ";
                } else {
                    lineBreak = "";
                }

                String line = scanner.nextLine();

                // add a white space between
                // lines other wise:
                // Hello
                // World
                // Becomes "HelloWorld" instead of "Hello World"

                // concatenate
                message += line + lineBreak;
            }

            // close the scanner
            scanner.close();

            // deal with not being able to find the file
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }

        return message;

    }

}