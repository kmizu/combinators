package parser.last;

import static parser.last.ParseResult.*;
import java.util.function.*;

public class FlatMapParser<T, R> implements Parser<R> {
    private final Parser<T>  parser;
    private final Function<T, Parser<R>> fn;

    public FlatMapParser(Parser<T> parser, Function<T, Parser<R>> fn) {
        this.parser = parser;
        this.fn = fn;
    }
    @Override
    public ParseResult<R> invoke(String input) {
        ParseResult<T> result = parser.invoke(input);
        if(result instanceof ParseResult.Success<?>) {
            Success<T> success = (ParseResult.Success<T>)result;
            return fn.apply(success.value).invoke(success.next);
        } else {
            Failure<R> failure = (ParseResult.Failure<R>)result;
            return failure;
        }
    }
}