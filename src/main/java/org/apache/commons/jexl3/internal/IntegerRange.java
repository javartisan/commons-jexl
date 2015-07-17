/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.jexl3.internal;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A range of integers.
 */
public class IntegerRange implements Collection<Integer> {
    /** The lower boundary. */
    private final int min;
    /** The upper boundary. */
    private final int max;

    /**
     * Creates a new range.
     * @param from the lower inclusive boundary
     * @param to  the higher inclusive boundary
     */
    public IntegerRange(int from, int to) {
        if (from > to) {
            max = from;
            min = to;
        } else {
            min = from;
            max = to;
        }
    }

    /**
     * Gets the interval minimum value.
     * @return the low boundary
     */
    public int getMin() {
        return min;
    }

    /**
     * Gets the interval maximum value.
     * @return the high boundary
     */
    public int getMax() {
        return max;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + this.min;
        hash = 13 * hash + this.max;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IntegerRange other = (IntegerRange) obj;
        if (this.min != other.min) {
            return false;
        }
        if (this.max != other.max) {
            return false;
        }
        return true;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new IntegerIterator(min, max);
    }

    @Override
    public int size() {
        return max - min + 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        if (o instanceof Number) {
            long v = ((Number) o).intValue();
            return min <= v && v <= max;
        } else {
            return false;
        }
    }

    @Override
    public Object[] toArray() {
        final int size = size();
        Object[] array = new Object[size];
        for(int a = 0; a < size; ++a) {
            array[a] = min + a;
        }
        return array;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] array) {
        final Class<?> ct = array.getClass().getComponentType();
        final int length = size();
        T[] copy = array;
        if (ct.isAssignableFrom(Integer.class)) {
            if (array.length < length) {
                copy = ct == Object.class
                       ? (T[]) new Object[length]
                       : (T[]) Array.newInstance(ct, length);
            }
            for (int a = 0; a < length; ++a) {
                Array.set(copy, a, min + a);
            }
            if (length < array.length) {
                array[length] = null;
            }
            return copy;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for(Object cc : c) {
            if (!contains(cc)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean add(Integer e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends Integer> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
}

/**
 * An iterator on an integer range.
 */
class IntegerIterator implements Iterator<Integer> {
    /** The lower boundary. */
    private final int min;
    /** The upper boundary. */
    private final int max;
    /** The current value. */
    private int cursor;
    /**
     * Creates a iterator on the range.
     * @param l low boundary
     * @param h high boundary
     */
    public IntegerIterator(int l, int h) {
        min = l;
        max = h;
        cursor = min;
    }

    @Override
    public boolean hasNext() {
        return cursor <= max;
    }

    @Override
    public Integer next() {
        if (cursor <= max) {
            return cursor++;
        }
        throw new NoSuchElementException();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported.");
    }
}
