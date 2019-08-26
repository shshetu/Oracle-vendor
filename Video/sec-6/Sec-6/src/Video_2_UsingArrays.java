import java.util.Arrays;

public class Video_2_UsingArrays {
    public static void main(String[] args) {
        String[] pets = {"parrot","cat","dog"}; //anonymous array
        System.out.println(pets[0]);
        System.out.println(pets[1]);
        System.out.println(pets[2]);
//        System.out.println(pets[3]); //thorw ArrayIndexOutOfBounds since we only have 3 elements

            ////////////////////
        pets[0] = "bird"; //it will replace the elements
        System.out.println(Arrays.toString(pets));

        for (int i =0 ; i<= pets.length-1;i++){
            System.out.println(pets[i]);
        }
        System.out.println();
        System.out.println(pets); //hashcode

        ////////////////////////////
        int[] numbers = new int[5];
        System.out.println(Arrays.toString(numbers));

        ///////////////////////
        String[] newPets = new String[5];
        System.out.println(Arrays.toString(newPets));

        for (int i=0; i<numbers.length;i++){
            numbers[i] = i+10; //10 11 12 13 14
        }

        for (int i = 0;i< numbers.length;i++){
            System.out.print(numbers[i]+" ");
        }
        System.out.println();
        System.out.println(numbers);
        System.out.println(Arrays.toString(numbers));

        //////////////////////////////
        Integer[] nums = new Integer[4];
        System.out.println(Arrays.toString(nums)); //[null, null, null, null]

        //////////////////////
        for (int i =0;i<nums.length;i++){
            nums[i] = i%3; //0 1 2 0
        }
        System.out.println(Arrays.toString(nums));

    }
}
