package parser.ex3;

/**
 * This parser represent ordered choice like
 * `lhs / rhs`.  Note that `lhs / rhs` is not
 * equal to `rhs / lhs`
 * @param <X> the type of result value
 */
public class Or<X> implements Parser<X> {
	private Parser<X> lhs;
	private Parser<X> rhs;
	public Or(Parser<X> lhs, Parser<X> rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public ParseResult<X> invoke(String input) {
		ParseResult<X> lresult = lhs.invoke(input);
		if(lresult instanceof ParseResult.Failure<?>) {
		    return rhs.invoke(input);
		} else {
			return lresult;
		}
	}
}