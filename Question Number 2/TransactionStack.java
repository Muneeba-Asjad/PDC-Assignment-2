
public class TransactionStack {
    private char[] stack;
    private int top;

    public TransactionStack(int capacity) {
        stack = new char[capacity];
        top = -1;
    }

    public void push(char c) {
        if (top == stack.length - 1) {
            System.out.println("Stack is full!");
            return;
        }
        stack[++top] = c;
    }

    public char pop() {
        if (isEmpty()) {
            return '\0';
        }
        return stack[top--];
    }

    public char peek() {
        if (isEmpty()) {
            return '\0';
        }
        return stack[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }
}
