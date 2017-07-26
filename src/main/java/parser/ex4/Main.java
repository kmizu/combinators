package parser.ex4;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Parser<List<String>> hellos = Parser.string("Hello").many();
        System.out.println(hellos.invoke(""));
        System.out.println(hellos.invoke("Hello"));
        System.out.println(hellos.invoke("HelloHello"));
        System.out.println(hellos.invoke("HelloHelloHello"));
    }
}
