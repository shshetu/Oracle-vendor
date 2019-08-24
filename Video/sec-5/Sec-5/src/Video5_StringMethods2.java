public class Video5_StringMethods2 {
    public static void main(String[] args) {
    String str = "java";
    String str2 = "Java";
    String str3 = "ja";
        System.out.println(str.equals(str2)); //false
        System.out.println(str.equalsIgnoreCase(str2)); //true
        System.out.println("abc".equalsIgnoreCase("ABC")); ///true
        System.out.println();
        //startsWith(), endsWith()
        System.out.println(str.startsWith("j")); //true
        System.out.println(str.startsWith(str3)); //true
        System.out.println(str.startsWith("J")); //false
        System.out.println(str.startsWith("J".toLowerCase())); //true
        System.out.println();

        System.out.println(str.endsWith("va")); //true
        System.out.println();

        //contains
        System.out.println(str.contains(str3)); //true
        System.out.println(str.contains("av")); //true
        System.out.println("Java".contains("j")); //false
        System.out.println("Java".contains("j".toUpperCase())); //true

        //replace
        String myString = "Java "+" "+"is"+" "+"cool";
        System.out.println(myString.replace('a','A')); // JAvA is cool
        System.out.println(myString.replace(" ","_")); //Java_is_cool
        System.out.println(myString.replace("is","_")); // Java _ cool
        System.out.println(myString);
        System.out.println();
        //trim = removes white space from starting and ending
        System.out.println("Java".trim());
        System.out.println(" Java is cool ");
        System.out.println(" Java is cool ".trim()); //removes the starting space and ending space
    }
}
