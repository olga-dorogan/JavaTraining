package com.custom.set;

import java.util.*;

/**
 * This class implements <tt>Set</tt> interface, backed by an array.
 * The class implements all optional operations, permits all no duplicate
 * elements and at most one <tt>null</tt> element.
 *
 * <p>The <tt>size</tt>, <tt>isEmpty</tt> and <tt>iterator</tt> operations
 * run in constant time; the <tt>contains</tt>, <tt>add</tt>, <tt>remove</tt>
 * and <tt>clean</tt> operations run in linear time and the run time of the bulk operations
 * (<tt>addAll</tt>, <tt>removeAll</tt>, <tt>retainAll</tt> and <tt>containsAll</tt>)
 * is proportional to product of specified collection and ArraySet's sizes.</p>
 *
 * <p>Each <tt>ArraySet</tt> instance has a capacity, value of which can be set in
 * the constructor (otherwise, it has default value of 16). The capacity is the size of the array
 * used to store the elements in the set. It is always at least as large as the set size.
 * When elements are added to an ArraySet and its capacity is not big enough,
 * it is doubled automatically.</p>
 *
 * <p><strong>This class is not synchronized.</strong> If multiple threads access
 * an <tt>ArraySet</tt> instance concurrently, and at least one of the threads adds or removes
 * at least one element to/from the set, it must be synchronized externally.</p>
 *
 * <p>The iterators, returned by {@link #iterator() iterator} method, are fail-fast.
 * So, if the set is modified (at least one of its elements is removed or added)
 * at any time after the iterator is created, in any way except through the iterator's own
 * methods, the iterator will throw <tt>ConcurrentModificationException</tt>.
 *
 * @author Olga Dorogan
 * @version 1.0
 * @see java.util.Collection
 * @see java.util.Set
 */
public class ArraySet<E> implements Set<E> {
    /**
     * Default initial capacity of the ArraySet.
     */
    private static final int DEFAULT_CAPACITY = 16;
    /**
     * Real capacity of the ArraySet (the number of elements it can contain
     * without the backing array resizing).
     */
    private int capacity;
    /**
     * The number of times of the external calls of the ArraySet's methods,
     * which potentially can change its size(add, remove, addAll, removeAll,
     * retainAll, clear).
     *
     * <p>Used by the iterator returned by the <tt>iterator</tt> method.
     * If the value of this field changes unexpectedly, the iterator will throw
     * a <tt>ConcurrentModificationException</tt> in response to the
     * <tt>next</tt> and <tt>remove</tt> operations.This provides fail-fast
     * behavior, rather than non-deterministic behavior in the face of
     * concurrent modification during iteration.</p>
     */
    private int modCount;
    /**
     * The size of the ArraySet (the number of elements it contains).
     */
    private int size;
    /**
     * The array buffer into which the elements of the ArraySet are stored.
     * The size of the ArraySet is the length of this array buffer.
     */
    private Object[] backingArray;

    /**
     * Constructs an empty ArraySet with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the ArraySet
     * @throws java.lang.IllegalArgumentException if the specified initial
     *                                            capacity is negative
     */
    public ArraySet(int initialCapacity) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                    initialCapacity);
        this.capacity = initialCapacity;
        this.backingArray = new Object[capacity];
        this.size = 0;
        this.modCount = 0;
    }

    /**
     * Constructs an empty ArraySet with initial capacity of 16.
     */
    public ArraySet() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructs an ArraySet containing all non-equal elements of the specified
     * collection.
     *
     * @param c the collection whose elements are to be placed into this ArraySet
     * @throws java.lang.NullPointerException if the specified collection is null
     */
    public ArraySet(Collection<? extends E> c) {
        this(c.size());
        for (E collectionElement : c)
            this.add(collectionElement);
    }

    /**
     * Compares the specified Object with this ArraySet for equality.
     * Returns <tt>true</tt> only if the compared Object implements
     * <tt>Set</tt>, its size is the same as this ArraySet's size and
     * every member of the compared Object is contained in this ArraySet
     * (or, equivalently, every member of this ArraySet is contained in
     * the specified object).
     *
     * @param o object to be compared for equality with this ArraySet
     * @return <tt>true</tt> if the specified object is equal to this ArraySet
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Set)) return false;

        Set comparedSet = (Set) o;

        if (size != comparedSet.size()) return false;

        for (int i = 0; i < size; i++)
            if (!comparedSet.contains(backingArray[i])) return false;

        return true;
    }


    /**
     * Returns the hash code value of the ArraySet defined as the hash code
     * of the backing array.
     *
     * @return the hash code value of this ArraySet or 0 if the backing
     * array is null
     */
    @Override
    public int hashCode() {
        return backingArray != null ? Arrays.hashCode(backingArray) : 0;
    }

    /**
     * Returns the string containing comma-separated string representations
     * of the elements of the ArrayList.
     *
     * @return the string representation of this ArraySet
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ArraySet { ");
        for (int i = 0; i < size; i++) {
            if (i != 0) sb.append(", ");
            sb.append(backingArray[i]);
        }
        sb.append(" }");
        return sb.toString();
    }

    /**
     * Returns the number of elements in this ArraySet.
     *
     * @return the number of elements in this ArraySet
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns <tt>true</tt> if this ArraySet contains no elements.
     *
     * @return <tt>true</tt> if this ArraySet contains no elements
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns <tt>true</tt> if this ArraySet contains the specified object
     * (object is equal to one of the ArrayList's elements).
     *
     * @param o object whose presence is this ArraySet is to be tested
     * @return <tt>true</tt> if this ArraySet contains the specified object
     */
    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++)
            if (o == null ? backingArray[i] == null : o.equals(backingArray[i]))
                return true;
        return false;
    }

    /**
     * Adds the specified element to this ArraySet if it is not already present
     * in this ArraySet. If this ArraySet already contains the element, the call returns
     * <tt>false</tt> and leaves this ArraySet unchanged.
     *
     * @param e element to be added to this ArraySet (can be null)
     * @return <tt>true</tt> if this ArraySet did not contain the specified element
     * (in other words, if this ArraySet was changed during the call)
     * @see #contains(Object)
     */
    @Override
    public boolean add(E e) {
        if (this.contains(e))
            return false;
        else if (size == capacity)
            this.resize();
        backingArray[size++] = e;
        modCount++;
        return true;
    }

    /**
     * Changes the length of the backing array by creating a new double-length array
     * and filling it by previous content.
     */
    private void resize() {
        if (capacity == 0)
            capacity = 1;
        capacity <<= 1;
        Object[] newBackingArray = new Object[capacity];
        System.arraycopy(backingArray, 0, newBackingArray, 0, backingArray.length);
        Arrays.fill(newBackingArray, backingArray.length, capacity, null);
        backingArray = newBackingArray;
    }

    /**
     * Removes the specified object from this ArraySet if it is present.
     * Returns <tt>true</tt> only if this ArraySet contained it (in other words,
     * if this ArraySet was changed during the call).
     *
     * @param o object to be removed from this ArraySet
     * @return <tt>true</tt> if this ArraySet contained the specified object
     * (in other words, if this ArraySet was changed during the call)
     */
    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (o == null ? backingArray[i] == null : o.equals(backingArray[i])) {
                System.arraycopy(backingArray, i + 1, backingArray, i, size - 1 - i);
                size--;
                backingArray[size] = null;
                modCount++;
                return true;
            }
        }
        return false;
    }

    /**
     * Removes all of the elements from this ArraySet.
     * The size of the ArraySet is not changed during the call.
     */
    @Override
    public void clear() {
        for (int i = 0; i < size; i++)
            backingArray[i] = null;
        size = 0;
        modCount++;
    }

    /**
     * Returns <tt>true</tt> if this ArraySet contains all of the
     * non-equivalent elements from the specified collection.
     *
     * @param c collection to be checked for containment in this ArraySet
     * @return <tt>true</tt> if this ArraySet contains all of the elements
     * from the specified collection
     * @see #contains(Object)
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null) throw new NullPointerException();
        for (Object collectionElement : c)
            if (!this.contains(collectionElement))
                return false;
        return true;
    }

    /**
     * Adds all of the non-equivalent elements of the specified collection
     * to this ArraySet if they are not already present. Returns <tt>true</tt> if
     * the ArraySet did not contain any of these elements.
     *
     * @param c collection containing elements to be added to this ArraySet
     * @return <tt>true</tt> if this ArraySet was changed during the call
     * @see #add(Object)
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null) throw new NullPointerException();
        boolean wasChanged = false;
        for (E collectionElement : c)
            if (this.add(collectionElement))
                wasChanged = true;
        return wasChanged;
    }

    /**
     * Removes all of the elements of the specified collection from this
     * ArraySet if they are present. Returns <tt>true</tt> if the ArraySet
     * contained at least one of the elements from the specified collection.
     *
     * @param c collection containing elements to be removed from this ArraySet
     * @return <tt>true</tt> if this ArraySet was changed during the call
     * @see #remove(Object)
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null)
            throw new NullPointerException();
        if (this.isEmpty())
            return false;
        boolean wasChanged = false;
        for (Object collectionElement : c)
            if (this.remove(collectionElement))
                wasChanged = true;
        return wasChanged;
    }

    /**
     * Removes from this ArraySet all of the elements which are not
     * contained in the specified collection.
     *
     * @param c collection containing elements to be retained in this ArraySet
     * @return <tt>true</tt> if this ArraySet was changed during the call
     * @throws java.lang.NullPointerException if the specified collection is null
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null)
            throw new NullPointerException();
        if (this.isEmpty())
            return false;
        boolean wasChanged = false;
        for (int i = 0; i < size; i++)
            if (!c.contains(backingArray[i])) {
                this.remove(backingArray[i]);
                wasChanged = true;
            }
        return wasChanged;
    }

    /**
     * Returns an array containing all of the elements of this ArraySet in
     * no particular order. Returned array is the copy of backing array,
     * so the caller is free to modify it.
     *
     * @return an array containing all of the elements of this ArraySet
     */
    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        System.arraycopy(backingArray, 0, arr, 0, size);
        return arr;
    }

    /**
     * Returns an array containing all of the elements of this ArraySet in
     * no particular order; the runtime type of the returned array is the same as
     * the type of the specified array. If the length of the specified array is equal
     * or more than the size of this ArraySet, the specified array is returned.
     * Otherwise, a new array is allocated with the runtime type of the specified
     * array and the size of this ArraySet.
     *
     * The returned array is filled by copies of the elements of this ArraySet.
     * If the specified array has more elements than the size of this ArraySet,
     * all of the elements following the copies are set to <tt>null</tt>.
     *
     * @param a the array into which the elements of this ArraySet are to be stored,
     *          if it is big enough; otherwise, a new array of the same runtime type
     *          is allocated for this purpose
     * @return the array containing all of the elements of this ArraySet
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            return (T[]) Arrays.copyOf(backingArray, size, a.getClass());
        System.arraycopy(backingArray, 0, a, 0, size);
        Arrays.fill(a, size, a.length, null);
        return a;
    }

    /**
     * Returns a fail-fast iterator over the elements in this ArraySet
     * in no particular order.
     *
     * @return an iterator over the elements of this ArraySet
     */
    @Override
    public Iterator<E> iterator() {
        return new ArraySetIterator<E>();
    }

    private class ArraySetIterator<E> implements Iterator<E> {
        private int currentIndex;
        private int expectedModCount;
        private final Object DUMMY_OBJECT = new Object();
        private Object lastReturnedObject = DUMMY_OBJECT;

        public ArraySetIterator() {
            currentIndex = 0;
            expectedModCount = modCount;
        }

        @Override
        public boolean hasNext() {
            return (currentIndex < size);
        }

        @SuppressWarnings("unchecked")
        @Override
        public E next() {
            checkForComodification();
            if (currentIndex >= size)
                throw new NoSuchElementException();
            lastReturnedObject = backingArray[currentIndex];
            return (E) backingArray[currentIndex++];
        }

        @Override
        public void remove() {
            if (lastReturnedObject == DUMMY_OBJECT)
                throw new IllegalStateException();
            checkForComodification();
            ArraySet.this.remove(lastReturnedObject);
            currentIndex--;
            expectedModCount = modCount;
            lastReturnedObject = DUMMY_OBJECT;
        }


        private void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }
}
