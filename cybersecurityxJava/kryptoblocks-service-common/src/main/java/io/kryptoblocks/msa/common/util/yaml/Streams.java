package io.kryptoblocks.msa.common.util.yaml;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// TODO: Auto-generated Javadoc
/**
 * The Class Streams.
 */
public class Streams {

	/**
	 * Returns the element in the stream, if the stream has exactly one element.
	 * Otherwise returns null
	 *
	 * @param <T> the generic type
	 * @param stream the stream
	 * @return the single
	 */
	public static <T> T getSingle(Stream<T> stream) {
		ArrayList<T> els = stream
				.limit(2) //Don't need more than 2 to know there is more than 1
				.collect(Collectors.toCollection(() -> new ArrayList<>(2)));
		return els.size()==1 ? els.get(0) : null;
	}

	/**
	 * Like java.util.Stream.of but returns Stream.empty if the element is null
	 *
	 * @param <T> the generic type
	 * @param e the e
	 * @return the stream
	 */
	public static <T> Stream<T> fromNullable(T e) {
		return e==null ? Stream.empty() : Stream.of(e);
	}

}
