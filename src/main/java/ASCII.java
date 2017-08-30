public class ASCII {
    public static String asciiToString(String value)
    {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(" ");
        for (int i = 0; i < chars.length; i++) {
            chars[i]=not(chars[i]);
            System.out.println(chars[i]);
            System.out.println(Integer.parseInt(chars[i],2));
            char chari=(char)Integer.parseInt(chars[i],2);
            System.out.println(chari);
            sbu.append(chari);

        }
        return sbu.toString();
    }

    public static String not(String s){
        StringBuffer sbu = new StringBuffer();
        for (int i = 0; i <s.length() ; i++) {
            if (s.charAt(i)=='0') sbu.append('1');
            else sbu.append('0');
        }
        return sbu.toString();
    }
    public static void main(String[] args) {
        String out=asciiToString("10010111 10001010 0011110 10010011 10010110 11111111 " +
                "11110111 " +"11111100 11111111");
        System.out.println(out);
    }
}
