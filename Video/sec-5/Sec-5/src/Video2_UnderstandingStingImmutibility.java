public class Video2_UnderstandingStingImmutibility {
    public static void main(String[] args) {
        String hello = "hello";
        String  hi = hello+"world";
        hi = hello; //hi = hello
        System.out.println(hi+hello);

        //////////////////////////
        hello.toUpperCase(); //returns a new String
        System.out.println(hello); //hello

        hello=hello.toUpperCase();
        System.out.println(hello); //HELLO

        /////////////////////////concat
        String s1 = "1";
        String s2 = s1.concat("2"); //12
        //immutable = we can not change the created string
        s2.concat("3");
        System.out.println(s1);
        System.out.println(s2);

    }
}
