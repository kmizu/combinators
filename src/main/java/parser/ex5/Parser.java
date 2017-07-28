package parser.ex5;

import java.util.List;

public interface Parser<T> {
	ParseResult<T> invoke(String input);
	static Parser<String> string(String literal) {
		return new StringParser(literal);
	}
	default Parser<T> or(Parser<T> rhs) {
		return new Or<>(this, rhs);
	}
	default <U> Parser<Tuple2<T, U>> cat(Parser<U> rhs) {
		return new Cat<>(this, rhs);
	}
	default Parser<List<T>> many() {
		return new ManyParser<T>(this);
	}
	static Parser<String> EOF() {
	    return new EOFParser();
    }
}
