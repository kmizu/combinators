package parser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import parser.last.Functions;
import parser.last.Parser;
import parser.last.Rule;

import java.util.function.BiFunction;

@RunWith(JUnit4.class)
public class ChainlTest {
	private Rule<Integer> expression() {
	    return Parser.rule(() ->
			additive().cat(Parser.eof()).map(t -> t.extract((result, __) -> result))
		);
	}
	private Rule<Integer> additive() {
		return Parser.rule(() -> {
			final Parser<BiFunction<Integer, Integer, Integer>> Q = Parser.string("+").map(op -> (Integer lhs, Integer rhs) -> lhs + rhs);
			final Parser<BiFunction<Integer, Integer, Integer>> R = Parser.string("-").map(op -> (Integer lhs, Integer rhs) -> lhs - rhs);
			return multitive().chain(Q.or(R));
		});
	}
	private Rule<Integer> multitive() {
		return Parser.rule(() -> {
			final Parser<BiFunction<Integer, Integer, Integer>> Q = Parser.string("*").map(op -> (Integer lhs, Integer rhs) -> lhs * rhs);
			final Parser<BiFunction<Integer, Integer, Integer>> R = Parser.string("/").map(op -> (Integer lhs, Integer rhs) -> lhs / rhs);
			return primary().chain(Q.or(R));
		});
	}

	private final Rule<Integer> primary() {
		return Parser.rule(() ->
			number().or((Parser.string("(").cat(expression())).cat(Parser.string(")")).map(t -> t.item1().item2()))
		);
	}

	private final Rule<Integer> number() {
		return Parser.rule(() ->
			Parser.digit().many1().map(digits -> Integer.parseInt(Functions.join(digits, "")))
		);
	}

	@Test
    public void testExpression() {
	    Parser<Integer> arithmetic = expression();
    }
}
