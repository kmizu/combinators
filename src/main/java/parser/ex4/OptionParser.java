package parser.ex4;

import java.util.Optional;

/**
 * This parser is like `parser+` in EBNF
 * The result value is `List&lt;T&gt;`.
 *
 * @param <T> the type of List element
 */
public class OptionParser<T> implements Parser<Optional<T>> {
	private Parser<T> parser;
	public OptionParser(Parser<T> parser) {
	    this.parser = parser;
	}

	@Override
	public ParseResult<Optional<T>> invoke(String input) {
		ParseResult<T> result = parser.invoke(input);
		if(result instanceof ParseResult.Failure<?>) {
			return new ParseResult.Success<>(Optional.ofNullable(null), input);
		} else {
		    ParseResult.Success<T> success = (ParseResult.Success<T>)result;
			return new ParseResult.Success<>(Optional.of(success.value), success.next);
		}
	}
}