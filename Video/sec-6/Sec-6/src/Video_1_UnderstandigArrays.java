import java.util.Arrays;

public class Video_1_UnderstandigArrays {
    public static void main(String[] args) {

        int[] one = new int[3]; //aray with 3 elements


        int[] two = new int[]{10,11,12}; //array

//    double[] a  = new double[2.55]; //does not compile

        double[] b = new double[4*5/2]; //creates array with 10 elements

        ///////////////////////
        int x=4;
        int y = 4;
        double[] c = new double[x*y];


        /////////Anonymous Array
        int[] three = {10,11,12}; //anonymous array
        int[] four = {};

        ////All allowed
        int[] a1;
        int[] a2;
        int a3[];
        int a4[];

        //////////////////////creates multiple array in the same line
        int[] ids, types; //creates two array
        int ids2[], types2; //creates array and int, bad practice

        String[] animals = {"Parrot","Dog","Cat"};
        String[]  myAnimals = animals;
        String[] otherAnimals = {"Parrot","Dog","Cat"};

        System.out.println(animals.equals(myAnimals)); //true
        System.out.println(animals == myAnimals); //true
        System.out.println(Arrays.equals(animals,myAnimals)); //true
        System.out.println();
        System.out.println(animals.equals(otherAnimals)); //false
        System.out.println(animals == otherAnimals); //false
        System.out.println(Arrays.equals(animals,otherAnimals)); //true

        //////////////////
        System.out.println();
        System.out.println(animals); //hashcode
        System.out.println(animals.toString()); //hashcode
        System.out.println(Arrays.toString(animals)); //shows all the animals in array

    }
}




