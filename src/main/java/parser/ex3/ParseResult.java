package parser.ex3;

import java.util.function.Function;


public interface ParseResult<T> {
	public static class Success<T> implements ParseResult<T> {
		public final T value;
		public final String next;
		Success(T value, String next) {
			this.value = value;
			this.next = next;
		}

		@Override
		public <U> ParseResult<U> map(Function<T, U> fn) {
			return new Success<U>(fn.apply(value), next);
		}

		@Override
		public String toString() {
			return "Success(" + value + ", " + next + ")";
		}
	}
	
	public static class Failure<T> implements ParseResult<T> {
		public final String message;
		public final String next;
		public Failure(String message, String next) {
			this.message = message;
			this.next = next;
		}

		@Override
		public <U> ParseResult<U> map(Function<T, U> fn) {
			return (ParseResult<U>)this;
		}

		@Override
		public String toString() {
			return "Failure(" + message + ", " + next;
		}
	}
	
	<U> ParseResult<U> map(Function<T, U> fn);
}
