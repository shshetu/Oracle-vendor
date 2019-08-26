public class Video3_StringPoolAndStringEquality {
    public static void main(String[] args) {

        String  name = "John";
        String anotherName = "John";
        String john = "Jo"+"hn";
        String newName = new String("John"); //new operator is forcing String class to create an object in heap space



        //equality ==
        //better practice is to always use double quotes
        System.out.println("name == anotherName -> "+(name == anotherName));
        System.out.println("name == john -> "+(name == john));
        System.out.println("name == newName -> "+(name == newName));

        ////////////////////////
        System.out.println(name.equals(anotherName));
        System.out.println(name.equals(john));
        System.out.println(name.equals(newName));

        /////////////////////////////
        System.out.println(System.identityHashCode(name));
        System.out.println(System.identityHashCode(anotherName));
        System.out.println(System.identityHashCode(john));
        System.out.println(System.identityHashCode(newName));

        ////////////////
        String str1 = "abc";
        String str2 ="ab";
        String str3 = str2+"c";
        System.out.println();
        ///Hashcode identification
        System.out.println(System.identityHashCode(str1));
        System.out.println(System.identityHashCode(str2));
        System.out.println(System.identityHashCode(str3));
    //////////////////////
        System.out.println(str1 == str2+" "); //false
        System.out.println(str1 == str3); //false
    }
}
