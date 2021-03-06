package parser.last;

import java.util.List;
import java.util.Optional;
import java.util.function.*;

import static parser.last.Functions.foldLeft;
import static parser.last.Functions.let;


public interface Parser<T> {
	ParseResult<T> invoke(String input);
	default Parser<T> or(Parser<T> rhs) {
		return new Or<>(this, rhs);
	}
	default <U> Parser<Tuple2<T, U>> cat(Parser<U> rhs) {
		return new Cat<>(this, rhs);
	}
	default <U> Parser<U> map(Function<T, U> fn) {
	    return new MapParser<>(this, fn);
	}
	default <U> Parser<U> flatMap(Function<T, Parser<U>> fn) {
		return new FlatMapParser<>(this, fn);
	}
	default Parser<Optional<T>> option() { return new OptionParser<T>(this); }
	default Parser<List<T>> many() {
		return new ManyParser<T>(this);
	}
	default Parser<List<T>> many1() {
		return new Many1Parser<T>(this);
	}
	default Parser<T> chain(Parser<BiFunction<T, T, T>> q) {
		return this.cat(q.cat(this).many()).map(t -> t.extract((init, list) -> {
			return foldLeft(list, init, ts -> {
				T a = ts.item1();
				return let(ts.item2().item1(), f -> {
					return let(ts.item2().item2(), b -> {
						return f.apply(a, b);
					});
				});
			});
		}));
	}
	default Parser<T> not() { return new Not<>(this); }
	default Parser<T> and() { return new And<>(this); }
	static Parser<String> any() { return new AnyParser(); }
	static Parser<String> string(String literal) {
	    return new StringParser(literal);
	}
	static <T> Rule<T> rule(Supplier<Parser<T>> body) {
		return new Rule<T>(body);
	}
	static Parser<String> eof() {
		return new EOFParser();
	}
	static RangeParser range(char from, char to) {
		return new RangeParser(from, to);
	}
	static SetParser set(char... characters) {
		java.util.Set<Character> set = new java.util.HashSet<>();
		for(char ch:characters) {
		    set.add(ch);
		}
		return new SetParser(set);
	}
	static Parser<String> alphabet() {
		return range('a', 'z').or(range('A', 'Z'));
	}
	static Parser<String> digit() {
		return range('0', '9');
	}
}
