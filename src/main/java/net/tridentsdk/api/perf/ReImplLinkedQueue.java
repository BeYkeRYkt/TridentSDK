/*
 *     TridentSDK - A Minecraft Server API
 *     Copyright (C) 2014, The TridentSDK Team
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.tridentsdk.api.perf;

import sun.misc.Unsafe;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

public class ReImplLinkedQueue<E> implements AddTakeQueue<E> {
    private Node<E> head = new Node<>(null);
    private Node<E> tail = head;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Condition condition = new Condition() {
        private final Unsafe unsafe = Performance.getUnsafe();
        private final ConcurrentLinkedQueue<Thread> queue = new ConcurrentLinkedQueue<>();

        @Override
        public void await() throws InterruptedException {
            unsafe.park(false, 0L);
            queue.add(Thread.currentThread());
        }

        @Override
        public void awaitUninterruptibly() {
            throw new UnsupportedOperationException();
        }

        @Override
        public long awaitNanos(long l) throws InterruptedException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean await(long l, TimeUnit timeUnit) throws InterruptedException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean awaitUntil(Date date) throws InterruptedException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void signal() {
            Thread thread = queue.poll();
            unsafe.unpark(thread);
        }

        @Override
        public void signalAll() {
            for (Thread thread : queue) {
                unsafe.unpark(thread);
            }
        }
    };

    @Override
    public boolean add(E e) {
        Node<E> node = new Node<>(e);
        Lock lock = this.lock.writeLock();
        lock.lock();

        try {
            this.tail = this.tail.next = node;
            condition.signal();
        } finally {
            lock.unlock();
        }

        return true;
    }

    @Override
    public E take() throws InterruptedException {
        Lock lock = this.lock.writeLock();
        lock.lockInterruptibly();

        E item;
        try {
            Node<E> h = this.head;
            Node<E> first = h.next;

            this.head = first;
            while (first == null)
                this.condition.await();
            item = first.getItem();
            first.item = null;
        } finally {
            lock.unlock();
        }

        return item;
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;

        public Node(E item) {
            this.item = item;
        }

        public void setItem(E item) {
            this.item = item;
        }

        public E getItem() {
            return item;
        }

        public Node<E> getNext() {
            return next;
        }
    }
}
