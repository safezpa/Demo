package mytest.toutiao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by safe on 2017-03-31.
 */
public class StreamAPI {

    public static List<String> readWords( BufferedReader in ) throws IOException
    {
        String oneLine;
        List<String> lst = new ArrayList<>( );

        while( ( oneLine = in.readLine( ) ) != null )
            lst.add( oneLine );

        return lst;
    }


    public static void main(String[] args) throws IOException {

        FileReader fin = new FileReader("input.txt" );
        BufferedReader bin = new BufferedReader( fin );
        Stream lines = Files.lines(Paths.get("input.txt"), Charset.defaultCharset());
        List<String> words = readWords( bin );


       // words.stream().map(line -> line.split(" ")).map()




        System.out.println(lines);

        List<String> list = new ArrayList<String>();
        list.add("I am a boy");
        list.add("I love the girl");
        list.add("But the girl loves another girl");

        //分完词之后，每个元素变成了一个String[]数组。
        list.stream().map(line -> line.split(" "));

        //将每个String[]变成流：

        list.stream()
                .map(line -> line.split(" "))
                .map(Arrays::stream);
        //此时一个大流里面包含了一个个小流，我们需要将这些小流合并成一个流。

        //将小流合并成一个大流：
        //用flagmap替换刚才的map

        list.stream()
                .map(line -> line.split(" ")).flatMap(Arrays::stream);

        Stream.generate(() -> Math.random());

        Stream.generate(Math::random);

        //去重

        List ll = list.stream()
                .map(line -> line.split(" "))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(toList());

        Map<String, List<String>> w = words
                .stream()
                .map(line -> line.split(" "))
                .flatMap(Arrays::stream)
                .map(String::toLowerCase)
                .skip(2).sorted()
                //.filter(word->word.length()>4)
                .peek(e -> System.out.println("filtered value " + e))
                .collect(Collectors.groupingBy(String::toLowerCase));
                //.collect(toList());

        Iterator it = w.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, List<String>> word = (Map.Entry) it.next();

            System.out.printf("%20s",word.getKey() + " = ");
            System.out.print(word.getValue().size()+ "\n");
        }

        System.out.println();

    }
}
