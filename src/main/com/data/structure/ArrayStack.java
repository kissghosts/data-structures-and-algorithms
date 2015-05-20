package com.data.structure;

public class ArrayStack<T> {
    private int topOfStack;
    private T[] stack;
    private int size;

    public ArrayStack(int s) {
        if (s < 0) {
            throw new StackException("stack size is negative!");
        }

        topOfStack = -1;
        size = s;
        stack = (T[])new Object[size];
    }

    public void push(T element) {
        if (size == topOfStack + 1) {
            throw new StackException("stack is full!");
        }
        stack[++topOfStack] = element;
    }

    public T pop() {
        if (isEmpty()) {
            throw new StackException("stack is empty!");
        }
        return stack[topOfStack--];
    }

    public boolean isEmpty() {
        return topOfStack == -1;
    }

    public T top() {
        return stack[topOfStack];
    }


    public static class StackException extends RuntimeException {
        public StackException(String message) {
              super(message);
        }
    }
}