package list;

public class CircularArrayQueue<T> {
    private int head;
    private int tail;
    private int size;
    private T[] queue;

    public CircularArrayQueue(int s) {
        if (s < 0) {
            throw new QueueException("queue size can not be negative!");
        }

        size = s;
        queue = (T[]) new Object[size];
        head = tail = -1;
    }

    public void enqueue(T element) {
        if (length() == size) {
            throw new QueueException("queue is full!");
        }


        if (++tail == size) {
            tail = 0;
        }
        if (head == -1) {
            head = 0;
        }
        queue[tail] = element;
    }

    public T dequeue() {
        if (length() == 0) {
            throw new QueueException("queue is empty!");
        }

        T element = queue[head];

        if (head == tail) {
            head = tail = -1;
        } else if (++head == size) {
            head = 0;
        }

        return element;
    }

    public int length() {
        int len;
        if (tail > head) {
            len = tail - head + 1;
        } else if (head > tail) {
            len = size - (head - tail -1);
        } else if (head == tail && tail != -1) {
            len = 1;
        } else {
            len = 0;
        }

        return len;
    }


    public static class QueueException extends RuntimeException {
        public QueueException(String message) {
            super(message);
        }
    }
}