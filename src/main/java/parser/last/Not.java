package parser.last;

/**
 * This parser represent not predicate like
 * `!e1 e2`.  Note that `!e1` doesn't consume
 * any input.
 * @param <X> the type of result value
 */
public class Not<X> implements Parser<X> {
	private Parser<X> target;
	public Not(Parser<X> target) {
	    this.target = target;
	}

	@Override
	public ParseResult<X> invoke(String input) {
		ParseResult<X> result = target.invoke(input);
		if(result instanceof ParseResult.Failure<?>) {
			return new ParseResult.Success<>(null, input);
		} else {
			return new ParseResult.Failure<>("shouuld not match", input);
		}
	}
}