package academy.learnprogramming;

public class MainMethod {
    //signature of main method
    //here all items are String type
    //entry point of our application
    /*
    * This is main method
    *
    * */
    public static void main(String[] args) {
    System.out.println("args-size  = "+args.length);

    /*
    Java doc comments:
    * printing arguments
    * another line
    * */

    for (int i=0; i<args.length;i++){
        System.out.println("args["+i+"]= "+args[i]);
    }
    }

/*
* */
public static int sum(int a, int b){
    return a+b;
}
}
