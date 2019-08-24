public class Video4_StringMethods {
    public static void main(String[] args) {
        ////length(), charAt(), indexOf(), substring(),toUpperCase(),toLowerCase()
        // 0 1 2 3 4 5 6 7 8 9 10
        // J a v a   i s   f u n
        String str = "Java is fun";

        //length()
        System.out.println("length= "+str.length());

        //charAt()
        System.out.println(str.charAt(0));
        System.out.println(str.charAt(2));
        System.out.println(str.charAt(6));
        System.out.println(str.charAt(10));

        //indexOf
        System.out.println(str.indexOf('a')); //1
        System.out.println(str.indexOf('a',2)); //3
        System.out.println(str.indexOf("fun")); //8
        System.out.println(str.indexOf("fun",10)); //-1

        //substring
        System.out.println(str.substring(8));//fun
        System.out.println(str.substring(0,5)); //Java : 5 is exclusive
        System.out.println(str.substring(4,4)); //empty string
//        System.out.println(str.substring(4,2)); //throw index out of bound exception
//        System.out.println(str.substring(4,14)); //throw index out of bound exception

        //////////////
        System.out.println("AbCd".toLowerCase());
        System.out.println(str.toUpperCase());
        System.out.println(str);

        ////////////////////////
        String dog = "Lucky";
        dog.toUpperCase();
        System.out.println(dog);

    }
}
