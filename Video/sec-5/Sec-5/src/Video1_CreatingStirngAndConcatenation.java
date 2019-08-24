public class Video1_CreatingStirngAndConcatenation {
    public static void main(String[] args) {
        //these two variables have different references
        String text = "hello";
        String antoherText = new String("hello"); //redundant

        //+
        System.out.println(2+3); //5
        System.out.println("a"+"b"); //ab
        System.out.println(1+2+"d"); //3d

        ////
        int three  = 3;
        String four = "4";
        System.out.println(1+2+three+four); //64

        ///////////////////
        int number = 10;
        int anothrNumber = 20;
        System.out.println("result= "+number+anothrNumber);
        System.out.println("result= "+(number+anothrNumber));

        /////////////////////
        String str = "";
        for (int i = 0;i<10;i++){
            str+=i+" ";
        }
        System.out.println(str);

        //////////////////////////////////
        String hello = "hello";
        String world = "world";
//        hello+world; //does not compile
        System.out.println(hello);
    }
}
