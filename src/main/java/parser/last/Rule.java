package parser.last;

import java.util.function.Supplier;

public class Rule<T> implements Parser<T> {
	public final Supplier<Parser<T>> body;
	public Rule(Supplier<Parser<T>> body) {
		this.body = body;
	}
	
	@Override
	public ParseResult<T> invoke(String input) {
		return body.get().invoke(input);
	}
	
}
