public class Video7_StringBuilderClass {
    public static void main(String[] args) {

      String myString = "";
      for (char c='a';c<='z';c++){
        myString+=c; //every iteration it will create a new String Object
        }
        System.out.println(myString);

      ///////////with String builder
        StringBuilder sb = new StringBuilder(); //create new instance of StringBuilder object

        for (char c='a';c<='z';c++){
            sb.append(c);
        }
        System.out.println(sb);
        System.out.println();
        //////important for exam
        StringBuilder builder = new StringBuilder("start");
        builder.append("-middle"); //start-middle
        StringBuilder anotherBuilder = builder.append("-end");

        System.out.println(builder);
        System.out.println(anotherBuilder);

        System.out.println(builder == anotherBuilder);
        System.out.println(System.identityHashCode(builder));
        System.out.println(System.identityHashCode(anotherBuilder));

        /////////////////
        System.out.println();
        StringBuilder myBuilder = new StringBuilder();
        System.out.println("size= "+myBuilder.length()); //0
        System.out.println("capacity= "+myBuilder.capacity()); //16

        System.out.println();
        myBuilder = new StringBuilder(100);
        System.out.println("size= "+myBuilder.length()); //0
        System.out.println("capacity= "+myBuilder.capacity()); //100

        System.out.println();
        myBuilder.append("MyNewTest");
        System.out.println("size= "+myBuilder.length());//9
        System.out.println("capacity= "+myBuilder.capacity());//100

        ///////////////////
        System.out.println();
        //Here two variables are pointing towards the same object refference
        StringBuilder a = new StringBuilder("This ");
        StringBuilder b = a.append("Java ");
        b= b.append("is").append("  so  ").append("Cool");
        System.out.println(a);
        System.out.println(b);
    }
}
