public class Video6_MethodChaining {
    public static void main(String[] args) {
       //method chaining
        String start = "  Java  ";
        String trimmed = start.trim();
        System.out.println("trimmed= "+trimmed); //Java

        String lowerCase = trimmed.toLowerCase();
        System.out.println("lowercase= "+lowerCase); //java

        String result = lowerCase.replace('j','J');
        System.out.println("result= "+result);//Java

        ////////////////////////////
        String antoherResult = "   Java  ".trim().toLowerCase().replace('j','J');
        System.out.println("anotherResult= "+antoherResult);
        System.out.println(result.equals(antoherResult));

        /////////////////
        System.out.println();
        String a = "abc";
        String b = a.toUpperCase();
        String c = b.replace('B','C').replace('C','c');
        System.out.println("a= "+a); //abc
        System.out.println("b= "+b); //ABC
        System.out.println("c= "+c); //Acc

        /////////////////////////////
        if(a.equalsIgnoreCase(b)){
            System.out.println("String are equal");
        }

        if(a.toLowerCase().trim().equals(b.toLowerCase().trim())){ //trim() method is redundant
            System.out.println("Equal");
        }
    }
}
