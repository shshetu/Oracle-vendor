package academy.learnprogramming;

import java.util.Random;

public class ImportExample {
    public static void main(String[] args){
        /*Create a random number*/
        Random random = new Random();
        //nubmer will be within 0 - 5
        System.out.println(random.nextInt(5));
    }
}
