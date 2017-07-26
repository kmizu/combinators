package parser.ex3;

public class Main {
    public static void main(String[] args) {
        Parser<String> helloOrWorld = Parser.string("Hello").or(Parser.string("World"));
        Parser<Tuple2<String, String>> hw = helloOrWorld.cat(helloOrWorld);
        System.out.println(hw.invoke("HelloWorld"));
        System.out.println(hw.invoke("HelloHello"));
        System.out.println(hw.invoke("WorldHello"));
        System.out.println(hw.invoke("WorldWorld"));
    }
}
