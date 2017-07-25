package parser.last;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Many1Parser<T> implements Parser<List<T>> {
	private Parser<T> parser;
	public Many1Parser(Parser<T> parser) {
	    this.parser = parser;
	}

	@Override
	public ParseResult<List<T>> invoke(String input) {
		ParseResult<T> result;
		String next = input;
		List<T> values = new ArrayList<T>();
		String previous = next;
		result = parser.invoke(next);
		if(result instanceof ParseResult.Success<?>) {
			ParseResult.Success<T> success = (ParseResult.Success<T>)result;
			values.add(success.value);
			previous = next;
			next = success.next;
		} else {
			return new ParseResult.Failure<>("", previous);
		}
		while(true) {
			previous = next;
			result = parser.invoke(next);
			if(result instanceof ParseResult.Success<?>) {
				ParseResult.Success<T> success = (ParseResult.Success<T>)result;
				values.add(success.value);
				next = success.next;
			} else {
				next = null;
			}
			if(next == null) {
				return new ParseResult.Success<>(values, previous);
			}
		}
	}
}