import java.util.Scanner;

public class PacketSymmetryAuditor {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter packet payload: ");
        String payload = sc.nextLine();
        TransactionStack stack = new TransactionStack(payload.length());
        RequestQueue queue = new RequestQueue(payload.length());
        for (int i = 0; i < payload.length(); i++) {
            char c = payload.charAt(i);
            stack.push(c);
            queue.enqueue(c);
        }
        boolean palindrome = true;
        while (!stack.isEmpty() && !queue.isEmpty()) {
            char fromStack = stack.pop();
            char fromQueue = queue.dequeue();

            if (fromStack != fromQueue) {
                palindrome = false;
                break;
            }
        }
        if (palindrome) {
            System.out.println("Packet symmetry verified: PALINDROME");
        } else {
            System.out.println("Packet symmetry failed: NOT PALINDROME");
        }
        sc.close();
    }
}