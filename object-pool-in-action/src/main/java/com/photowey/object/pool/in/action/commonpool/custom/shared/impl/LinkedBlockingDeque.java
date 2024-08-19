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
package com.photowey.object.pool.in.action.commonpool.custom.shared.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * An optionally-bounded {@linkplain java.util.concurrent.BlockingDeque blocking deque} based on linked nodes.
 *
 * @since 2.0
 */
final class LinkedBlockingDeque<E> extends AbstractQueue<E> implements Deque<E>, Serializable {

    private abstract class AbstractItr implements Iterator<E> {
        Node<E> next;

        E nextItem;

        private Node<E> lastRet;

        AbstractItr() {
            // set to initial position
            lock.lock();
            try {
                next = firstNode();
                nextItem = next == null ? null : next.item;
            } finally {
                lock.unlock();
            }
        }

        void advance() {
            lock.lock();
            try {
                // assert next != null;
                next = succ(next);
                nextItem = next == null ? null : next.item;
            } finally {
                lock.unlock();
            }
        }

        abstract Node<E> firstNode();

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public E next() {
            if (next == null) {
                throw new NoSuchElementException();
            }
            lastRet = next;
            final E x = nextItem;
            advance();
            return x;
        }

        abstract Node<E> nextNode(Node<E> n);

        @Override
        public void remove() {
            final Node<E> n = lastRet;
            if (n == null) {
                throw new IllegalStateException();
            }
            lastRet = null;
            lock.lock();
            try {
                if (n.item != null) {
                    unlink(n);
                }
            } finally {
                lock.unlock();
            }
        }

        private Node<E> succ(Node<E> n) {
            // Chains of deleted nodes ending in null or self-links
            // are possible if multiple interior nodes are removed.
            for (; ; ) {
                final Node<E> s = nextNode(n);
                if (s == null) {
                    return null;
                }
                if (s.item != null) {
                    return s;
                }
                if (s == n) {
                    return firstNode();
                }
                n = s;
            }
        }
    }

    private final class DescendingItr extends AbstractItr {
        @Override
        Node<E> firstNode() {return last;}

        @Override
        Node<E> nextNode(final Node<E> n) {return n.prev;}
    }

    private final class Itr extends AbstractItr {
        @Override
        Node<E> firstNode() {return first;}

        @Override
        Node<E> nextNode(final Node<E> n) {return n.next;}
    }

    private static final class Node<E> {

        E item;

        Node<E> prev;

        Node<E> next;

        Node(final E x, final Node<E> p, final Node<E> n) {
            item = x;
            prev = p;
            next = n;
        }
    }

    private static final long serialVersionUID = -387911632671998426L;

    private transient Node<E> first;

    private transient Node<E> last;

    private transient int count;

    private final int capacity;

    private final InterruptibleReentrantLock lock;

    private final Condition notEmpty;

    private final Condition notFull;

    public LinkedBlockingDeque() {
        this(Integer.MAX_VALUE);
    }

    public LinkedBlockingDeque(final boolean fairness) {
        this(Integer.MAX_VALUE, fairness);
    }

    // Basic linking and unlinking operations, called only while holding lock

    public LinkedBlockingDeque(final Collection<? extends E> c) {
        this(Integer.MAX_VALUE);
        lock.lock(); // Never contended, but necessary for visibility
        try {
            for (final E e : c) {
                Objects.requireNonNull(e);
                if (!linkLast(e)) {
                    throw new IllegalStateException("Deque full");
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public LinkedBlockingDeque(final int capacity) {
        this(capacity, false);
    }

    public LinkedBlockingDeque(final int capacity, final boolean fairness) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
        lock = new InterruptibleReentrantLock(fairness);
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
    }

    @Override
    public boolean add(final E e) {
        addLast(e);
        return true;
    }

    @Override
    public void addFirst(final E e) {
        if (!offerFirst(e)) {
            throw new IllegalStateException("Deque full");
        }
    }

    // BlockingDeque methods

    @Override
    public void addLast(final E e) {
        if (!offerLast(e)) {
            throw new IllegalStateException("Deque full");
        }
    }

    @Override
    public void clear() {
        lock.lock();
        try {
            for (Node<E> f = first; f != null; ) {
                f.item = null;
                final Node<E> n = f.next;
                f.prev = null;
                f.next = null;
                f = n;
            }
            first = last = null;
            count = 0;
            notFull.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean contains(final Object o) {
        if (o == null) {
            return false;
        }
        lock.lock();
        try {
            for (Node<E> p = first; p != null; p = p.next) {
                if (o.equals(p.item)) {
                    return true;
                }
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new DescendingItr();
    }

    public int drainTo(final Collection<? super E> c) {
        return drainTo(c, Integer.MAX_VALUE);
    }

    public int drainTo(final Collection<? super E> collection, final int maxElements) {
        Objects.requireNonNull(collection, "c");
        if (collection == this) {
            throw new IllegalArgumentException();
        }
        lock.lock();
        try {
            final int n = Math.min(maxElements, count);
            for (int i = 0; i < n; i++) {
                collection.add(first.item); // In this order, in case add() throws.
                unlinkFirst();
            }
            return n;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public E element() {
        return getFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E getFirst() {
        final E x = peekFirst();
        if (x == null) {
            throw new NoSuchElementException();
        }
        return x;
    }

    @Override
    public E getLast() {
        final E x = peekLast();
        if (x == null) {
            throw new NoSuchElementException();
        }
        return x;
    }

    public int getTakeQueueLength() {
        lock.lock();
        try {
            return lock.getWaitQueueLength(notEmpty);
        } finally {
            lock.unlock();
        }
    }

    public boolean hasTakeWaiters() {
        lock.lock();
        try {
            return lock.hasWaiters(notEmpty);
        } finally {
            lock.unlock();
        }
    }

    public void interuptTakeWaiters() {
        lock.lock();
        try {
            lock.interruptWaiters(notEmpty);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    private boolean linkFirst(final E e) {
        // assert lock.isHeldByCurrentThread();
        if (count >= capacity) {
            return false;
        }
        final Node<E> f = first;
        final Node<E> x = new Node<>(e, null, f);
        first = x;
        if (last == null) {
            last = x;
        } else {
            f.prev = x;
        }
        ++count;
        notEmpty.signal();
        return true;
    }

    private boolean linkLast(final E e) {
        // assert lock.isHeldByCurrentThread();
        if (count >= capacity) {
            return false;
        }
        final Node<E> l = last;
        final Node<E> x = new Node<>(e, l, null);
        last = x;
        if (first == null) {
            first = x;
        } else {
            l.next = x;
        }
        ++count;
        notEmpty.signal();
        return true;
    }

    @Override
    public boolean offer(final E e) {
        return offerLast(e);
    }

    boolean offer(final E e, final Duration timeout) throws InterruptedException {
        return offerLast(e, timeout);
    }

    public boolean offer(final E e, final long timeout, final TimeUnit unit) throws InterruptedException {
        return offerLast(e, timeout, unit);
    }

    @Override
    public boolean offerFirst(final E e) {
        Objects.requireNonNull(e, "e");
        lock.lock();
        try {
            return linkFirst(e);
        } finally {
            lock.unlock();
        }
    }

    public boolean offerFirst(final E e, final Duration timeout) throws InterruptedException {
        Objects.requireNonNull(e, "e");
        long nanos = timeout.toNanos();
        lock.lockInterruptibly();
        try {
            while (!linkFirst(e)) {
                if (nanos <= 0) {
                    return false;
                }
                nanos = notFull.awaitNanos(nanos);
            }
            return true;
        } finally {
            lock.unlock();
        }
    }

    public boolean offerFirst(final E e, final long timeout, final TimeUnit unit) throws InterruptedException {
        return offerFirst(e, PoolImplUtils.toDuration(timeout, unit));
    }

    @Override
    public boolean offerLast(final E e) {
        Objects.requireNonNull(e, "e");
        lock.lock();
        try {
            return linkLast(e);
        } finally {
            lock.unlock();
        }
    }

    boolean offerLast(final E e, final Duration timeout) throws InterruptedException {
        Objects.requireNonNull(e, "e");
        long nanos = timeout.toNanos();
        lock.lockInterruptibly();
        try {
            while (!linkLast(e)) {
                if (nanos <= 0) {
                    return false;
                }
                nanos = notFull.awaitNanos(nanos);
            }
            return true;
        } finally {
            lock.unlock();
        }
    }

    public boolean offerLast(final E e, final long timeout, final TimeUnit unit) throws InterruptedException {
        return offerLast(e, PoolImplUtils.toDuration(timeout, unit));
    }

    @Override
    public E peek() {
        return peekFirst();
    }

    // BlockingQueue methods

    @Override
    public E peekFirst() {
        lock.lock();
        try {
            return first == null ? null : first.item;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public E peekLast() {
        lock.lock();
        try {
            return last == null ? null : last.item;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    E poll(final Duration timeout) throws InterruptedException {
        return pollFirst(timeout);
    }

    public E poll(final long timeout, final TimeUnit unit) throws InterruptedException {
        return pollFirst(timeout, unit);
    }

    @Override
    public E pollFirst() {
        lock.lock();
        try {
            return unlinkFirst();
        } finally {
            lock.unlock();
        }
    }

    E pollFirst(final Duration timeout) throws InterruptedException {
        long nanos = timeout.toNanos();
        lock.lockInterruptibly();
        try {
            E x;
            while ((x = unlinkFirst()) == null) {
                if (nanos <= 0) {
                    return null;
                }
                nanos = notEmpty.awaitNanos(nanos);
            }
            return x;
        } finally {
            lock.unlock();
        }
    }

    public E pollFirst(final long timeout, final TimeUnit unit) throws InterruptedException {
        return pollFirst(PoolImplUtils.toDuration(timeout, unit));
    }

    @Override
    public E pollLast() {
        lock.lock();
        try {
            return unlinkLast();
        } finally {
            lock.unlock();
        }
    }

    public E pollLast(final Duration timeout)
            throws InterruptedException {
        long nanos = timeout.toNanos();
        lock.lockInterruptibly();
        try {
            E x;
            while ((x = unlinkLast()) == null) {
                if (nanos <= 0) {
                    return null;
                }
                nanos = notEmpty.awaitNanos(nanos);
            }
            return x;
        } finally {
            lock.unlock();
        }
    }

    public E pollLast(final long timeout, final TimeUnit unit)
            throws InterruptedException {
        return pollLast(PoolImplUtils.toDuration(timeout, unit));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public void push(final E e) {
        addFirst(e);
    }

    public void put(final E e) throws InterruptedException {
        putLast(e);
    }

    public void putFirst(final E e) throws InterruptedException {
        Objects.requireNonNull(e, "e");
        lock.lock();
        try {
            while (!linkFirst(e)) {
                notFull.await();
            }
        } finally {
            lock.unlock();
        }
    }

    public void putLast(final E e) throws InterruptedException {
        Objects.requireNonNull(e, "e");
        lock.lock();
        try {
            while (!linkLast(e)) {
                notFull.await();
            }
        } finally {
            lock.unlock();
        }
    }

    // Stack methods

    private void readObject(final ObjectInputStream s)
            throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        count = 0;
        first = null;
        last = null;
        // Read in all elements and place in queue
        for (; ; ) {
            @SuppressWarnings("unchecked") final E item = (E) s.readObject();
            if (item == null) {
                break;
            }
            add(item);
        }
    }

    public int remainingCapacity() {
        lock.lock();
        try {
            return capacity - count;
        } finally {
            lock.unlock();
        }
    }

    // Collection methods

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public boolean remove(final Object o) {
        return removeFirstOccurrence(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E removeFirst() {
        final E x = pollFirst();
        if (x == null) {
            throw new NoSuchElementException();
        }
        return x;
    }

    @Override
    public boolean removeFirstOccurrence(final Object o) {
        if (o == null) {
            return false;
        }
        lock.lock();
        try {
            for (Node<E> p = first; p != null; p = p.next) {
                if (o.equals(p.item)) {
                    unlink(p);
                    return true;
                }
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E removeLast() {
        final E x = pollLast();
        if (x == null) {
            throw new NoSuchElementException();
        }
        return x;
    }

    @Override
    public boolean removeLastOccurrence(final Object o) {
        if (o == null) {
            return false;
        }
        lock.lock();
        try {
            for (Node<E> p = last; p != null; p = p.prev) {
                if (o.equals(p.item)) {
                    unlink(p);
                    return true;
                }
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int size() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }

    public E take() throws InterruptedException {
        return takeFirst();
    }

    public E takeFirst() throws InterruptedException {
        lock.lock();
        try {
            E x;
            while ((x = unlinkFirst()) == null) {
                notEmpty.await();
            }
            return x;
        } finally {
            lock.unlock();
        }
    }

    public E takeLast() throws InterruptedException {
        lock.lock();
        try {
            E x;
            while ((x = unlinkLast()) == null) {
                notEmpty.await();
            }
            return x;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Object[] toArray() {
        lock.lock();
        try {
            final Object[] a = new Object[count];
            int k = 0;
            for (Node<E> p = first; p != null; p = p.next) {
                a[k++] = p.item;
            }
            return a;
        } finally {
            lock.unlock();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        lock.lock();
        try {
            if (a.length < count) {
                a = (T[]) java.lang.reflect.Array.newInstance
                        (a.getClass().getComponentType(), count);
            }
            int k = 0;
            for (Node<E> p = first; p != null; p = p.next) {
                a[k++] = (T) p.item;
            }
            if (a.length > k) {
                a[k] = null;
            }
            return a;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        lock.lock();
        try {
            return super.toString();
        } finally {
            lock.unlock();
        }
    }

    private void unlink(final Node<E> x) {
        // assert lock.isHeldByCurrentThread();
        final Node<E> p = x.prev;
        final Node<E> n = x.next;
        if (p == null) {
            unlinkFirst();
        } else if (n == null) {
            unlinkLast();
        } else {
            p.next = n;
            n.prev = p;
            x.item = null;
            // Don't mess with x's links.  They may still be in use by
            // an iterator.
            --count;
            notFull.signal();
        }
    }

    // Monitoring methods

    private E unlinkFirst() {
        // assert lock.isHeldByCurrentThread();
        final Node<E> f = first;
        if (f == null) {
            return null;
        }
        final Node<E> n = f.next;
        final E item = f.item;
        f.item = null;
        f.next = f; // help GC
        first = n;
        if (n == null) {
            last = null;
        } else {
            n.prev = null;
        }
        --count;
        notFull.signal();
        return item;
    }

    private E unlinkLast() {
        // assert lock.isHeldByCurrentThread();
        final Node<E> l = last;
        if (l == null) {
            return null;
        }
        final Node<E> p = l.prev;
        final E item = l.item;
        l.item = null;
        l.prev = l; // help GC
        last = p;
        if (p == null) {
            first = null;
        } else {
            p.next = null;
        }
        --count;
        notFull.signal();
        return item;
    }

    private void writeObject(final java.io.ObjectOutputStream s) throws IOException {
        lock.lock();
        try {
            // Write out capacity and any hidden stuff
            s.defaultWriteObject();
            // Write out all elements in the proper order.
            for (Node<E> p = first; p != null; p = p.next) {
                s.writeObject(p.item);
            }
            // Use trailing null as sentinel
            s.writeObject(null);
        } finally {
            lock.unlock();
        }
    }
}
