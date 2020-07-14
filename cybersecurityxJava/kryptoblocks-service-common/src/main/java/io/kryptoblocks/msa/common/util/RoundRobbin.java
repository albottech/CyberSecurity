package io.kryptoblocks.msa.common.util;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multiset;

// TODO: Auto-generated Javadoc
/**
 * The Class RoundRobbin.
 *
 * @param <T> the generic type
 */
public class RoundRobbin<T>  {

    /** The counter set. */
    private final Multiset<T> counterSet;

    /** The element iterator. */
    private final Iterator<T> elementIterator;    
     

    /**
     * Instantiates a new round robbin.
     *
     * @param elements the elements
     */
    public RoundRobbin(final List<T> elements) {
        this.counterSet = HashMultiset.create();
        this.elementIterator = Iterables.cycle(elements).iterator();
    }

    /**
     * Gets the one.
     *
     * @return the one
     */
    public T getOne() {
        final T element = this.elementIterator.next();
        this.counterSet.add(element);
        return element;
    }

    /**
     * Gets the count.
     *
     * @param element the element
     * @return the count
     */
    public int getCount(final T element) {
        return this.counterSet.count(element);
    } 
} 