public class Video_3_CommonArrayProblems {
    public static void main(String[] args) {
        int[] numbers = {1,2,3,4,5};

        for (int i = 1;i< numbers.length;i++){
            System.out.println(i+" - "+numbers[i]); // 1-2 2-3 3-4 4-5
        }

        /////////////////////////////// index out of bound exception
        System.out.println();
        /*for (int i =1;i<= numbers.length;i++){
            System.out.println(i+" - "+numbers[i]); //index out of bound exception
        }*/
        for (int i =1;i<= numbers.length-1;i++){
            System.out.println(i+" - "+numbers[i]); //index out of bound exception
        }
        //////////////////////////////////////////////////
//        int[20] nums; //does not compile
        int[] nums = new int[20]; //size only at initialization
        int size = numbers.length;





    }
}
