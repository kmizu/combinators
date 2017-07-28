package parser.ex5;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Parser<Tuple2<List<String>, String>> hellos = Parser.string("Hello").many().cat(Parser.EOF());
        System.out.println(hellos.invoke("Hello"));
        System.out.println(hellos.invoke("Hello, World!"));
    }
}
