package parser.last;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class Functions {

    public static <T, U> U let(T value, Function<T, U> fn) {
        return fn.apply(value);
    }

    public static <T> void let(T value, Consumer<T> pr) {
        pr.accept(value);
    }
    
    public static <T, U> U foldLeft(List<T> list, U init, Function<Tuple2<U, T>, U> f) {
    	U result = init;
    	for(T t:list) {
    		result = f.apply(new Tuple2<>(result, t));
    	}
    	return result;
    }
    
    public static <T> String join(List<T> list, String separator) {
    	if(list.size() == 0) {
    		return "";
    	} else {
    		return let(new StringBuilder(), builder -> {
    		    T t = list.get(0);
    		    List<T> es = list.subList(1, list.size());
    			builder.append(t.toString());
    			for(T e:es) {
    				builder.append(separator);
    				builder.append(e.toString());
    			}
    			return new String(builder);
    		});
    	}
    }
}
