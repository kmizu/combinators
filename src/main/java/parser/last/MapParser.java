package parser.last;

import java.util.function.Function;

/**
 * This parser translate the result value `T` to `R`
 * applying `Function&lt;T, R&gt;`.
 * @param <T> the value type before translation
 * @param <R> the value type after translation
 */
public class MapParser<T, R> implements Parser<R> {
    private final Parser<T> parser;
    private final Function<T, R> fn;

    public MapParser(Parser<T> parser, Function<T, R> fn) {
        this.parser = parser;
        this.fn = fn;
    }
    @Override
    public ParseResult<R> invoke(String input) {
        return parser.invoke(input).map(fn);
    }
}