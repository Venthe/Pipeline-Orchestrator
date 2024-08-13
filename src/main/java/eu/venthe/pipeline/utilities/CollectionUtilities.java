package eu.venthe.pipeline.utilities;

import lombok.experimental.UtilityClass;

import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@UtilityClass
public class CollectionUtilities {

    public static <T> Stream<T> iteratorToStream(Iterator<T> elements) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(elements, Spliterator.ORDERED), true);
    }

    public static <U, T> Collector<Map.Entry<U, T>, ?, Map<U, T>> toMap() {
        return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue);
    }

    public static <T, R, U> Function<Map.Entry<U, T>, Map.Entry<U, R>> sameKey(Function<? super T, ? extends R> mapper) {
        return e -> Map.entry(e.getKey(), mapper.apply(e.getValue()));
    }
}
