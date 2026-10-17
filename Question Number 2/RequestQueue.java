
public class RequestQueue {
    private char[] queue;
    private int front;
    private int rear;
    private int size;

    public RequestQueue(int capacity) {
        queue = new char[capacity];
        front = 0;
        rear = -1;
        size = 0;
    }

    public void enqueue(char c) {
        if (size == queue.length) {
            System.out.println("Queue is full!");
            return;
        }
        rear = (rear + 1) % queue.length;
        queue[rear] = c;
        size++;
    }

    public char dequeue() {
        if (isEmpty()) {
            return '\0';
        }
        char c = queue[front];
        front = (front + 1) % queue.length;
        size--;
        return c;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
