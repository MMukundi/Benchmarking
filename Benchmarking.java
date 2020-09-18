import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//https://en.wikipedia.org/wiki/ANSI_escape_code
//ANSI escape codes were used here for debugging and printing progress

class Benchmarking{
    public static void main(String[] args) {
        for(String fileName:args){
            try{
            Scanner fileScanner = new Scanner(new File(fileName));
            LinkedList<Integer> list = new LinkedList<Integer>();
            System.out.printf("Inserting the values from %s\n\033[s",fileName);
            while(fileScanner.hasNextInt()){
                int toInsert = fileScanner.nextInt();
                int insertionIndex=0;
                for(int otherValue: list){
                    if(otherValue<toInsert)break;
                    insertionIndex++;
                }
                list.insert(toInsert,insertionIndex);
                System.out.printf("\033[u\033[s%d values inserted",list.length);
            } 
            System.out.println("")           

            }catch(FileNotFoundException fnf){
                System.out.printf("File %s not found\n",fileName);
            }
        }
    }
}