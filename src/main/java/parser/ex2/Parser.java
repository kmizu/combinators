package parser.ex2;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static parser.last.Functions.foldLeft;
import static parser.last.Functions.let;


public interface Parser<T> {
	ParseResult<T> invoke(String input);
	static Parser<String> string(String literal) {
	    return new StringParser(literal);
	}
	default <U> Parser<Tuple2<T, U>> cat(Parser<U> that) {
		return new Cat(this, that);
	}
}
