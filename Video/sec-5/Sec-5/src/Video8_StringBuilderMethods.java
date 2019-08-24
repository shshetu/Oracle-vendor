public class Video8_StringBuilderMethods {
    public static void main(String[] args) {
        ///charAt, indexOf,length(),substring()
        // 0 1 2 3 4 5 6 7 8 9 10
        // p r o g r a m m i n g
        StringBuilder sb = new StringBuilder("Programming");
        String sub = sb.substring(sb.indexOf("g"),sb.indexOf("mi")); //gram
        int len = sb.length(); //11
        char ch = sb.charAt(5); // a
        System.out.println(sub+" "+len+" "+ch); //gram 11 a

        ///append()
        StringBuilder b = new StringBuilder().append(1).append("L");
        b.append("-").append(true);
        System.out.println(b);//1L-true

        //insert()
        StringBuilder builder = new StringBuilder("programming");
        builder.insert(7,"-");
        builder.insert(2,2);
        System.out.println(builder); //pr2gram-ing

        //delete(), deleteCharAt()
        builder.delete(4,9);
        System.out.println(builder); //progr

    }
}
