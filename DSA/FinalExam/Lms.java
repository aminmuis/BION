import java.time.LocalDateTime;
import java.util.*;

class Submission {
    private String submissionID;
    private String studentId;
    private String assignmentID;
    private LocalDateTime timestamp;
    private String jawaban;

    public Submission(String submissionID, String studentId, String assignmentID, LocalDateTime timestamp,
            String jawaban) {
        this.submissionID = submissionID;
        this.studentId = studentId;
        this.assignmentID = assignmentID;
        this.timestamp = timestamp;
        this.jawaban = jawaban;
    }

    public String getSubmissionID() {
        return submissionID;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getAssignmentID() {
        return assignmentID;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getJawaban() {
        return jawaban;
    }

    @Override
    public String toString() {
        return submissionID + " - " + studentId + " - " + assignmentID + " - " + timestamp + " - " + jawaban;
    }
}

class SubmissionManager {
    // Queue untuk memproses submission berdasarkan kedatangan paling awal (FIFO)
    private Queue<Submission> submissionQueue = new LinkedList<>();

    // Stack untuk membatalkan submission terakhir yang masuk (LIFO)
    private Stack<Submission> submissionStack = new Stack<>();

    // HashMap untuk pencarian berdasarkan submissionID
    private HashMap<String, Submission> submissionMap = new HashMap<>();

    // Menambahkan submission baru
    public void addSubmission(Submission submission) {
        submissionQueue.add(submission);
        submissionStack.push(submission);
        submissionMap.put(submission.getSubmissionID(), submission);
    }

    // Membatalkan submission terakhir
    public void cancelLastSubmission() {
        if (submissionStack.isEmpty()) {
            System.out.println("Tidak ada submission untuk dibatalkan.");
            return;
        }

        Submission lastSubmission = submissionStack.pop();
        submissionQueue.remove(lastSubmission);
        submissionMap.remove(lastSubmission.getSubmissionID());

        System.out.println("Membatalkan submission: " + lastSubmission);
    }

    // Memproses submission berdasarkan kedatangan paling awal (FIFO)
    public void processSubmissions() {
        if (submissionQueue.isEmpty()) {
            System.out.println("Tidak ada submission untuk diproses.");
            return;
        }

        Submission submission = submissionQueue.poll();

        System.out.println("Memproses submission: " + submission);
    }

    // Mencari submission berdasarkan submissionID
    public Submission searchSubmission(String submissionID) {
        return submissionMap.get(submissionID);
    }
}

class ExpressionEvaluator {
    public static String infixToPostfix(String expression) {

        Stack<Character> stack = new Stack<>();
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {

            char c = expression.charAt(i);

            // Jika angka, masukkan ke output
            if (Character.isDigit(c)) {

                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    output.append(expression.charAt(i));
                    i++;
                }

                output.append(" ");
                i--;
            }
            // Jika kurung buka
            else if (c == '(') {
                stack.push(c);
            }

            // Jika kurung tutup
            else if (c == ')') {

                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.append(stack.pop());
                    output.append(" ");
                }

                if (!stack.isEmpty()) {
                    stack.pop(); // hapus '('
                }
            }

            // Jika operator
            else if (c == '+' || c == '-' || c == '*' || c == '/') {

                while (!stack.isEmpty()
                        && stack.peek() != '('
                        && precedence(stack.peek()) >= precedence(c)) {

                    output.append(stack.pop());
                    output.append(" ");
                }

                stack.push(c);
            }
        }

        // Keluarkan semua operator yang masih ada
        while (!stack.isEmpty()) {
            output.append(stack.pop());
            output.append(" ");

        }
        return output.toString().trim();

    }

    // Menentukan prioritas operator
    private static int precedence(char operator) {

        if (operator == '+' || operator == '-') {
            return 1;
        }

        if (operator == '*' || operator == '/') {
            return 2;
        }

        return 0;
    }

    // Mengevaluasi expression postfix
    public static int evaluatePostfix(String postfix) {

        Stack<Integer> stack = new Stack<>();

        String[] tokens = postfix.split(" ");

        for (String token : tokens) {

            // Jika angka
            if (token.matches("\\d+")) {

                stack.push(Integer.parseInt(token));
            }
            // Jika operator
            else {

                int b = stack.pop();
                int a = stack.pop();

                int result = 0;

                switch (token.charAt(0)) {

                    case '+':
                        result = a + b;
                        break;

                    case '-':
                        result = a - b;
                        break;

                    case '*':
                        result = a * b;
                        break;

                    case '/':
                        result = a / b;
                        break;
                }

                stack.push(result);
            }
        }

        return stack.pop();
    }
}

// Class utama
public class Lms {
    public static void main(String[] args) {
        // Modul 1
        SubmissionManager lms = new SubmissionManager();

        lms.addSubmission(new Submission("SUB001", "STU001", "A001", LocalDateTime.now(), "Jawaban 1"));
        lms.addSubmission(new Submission("SUB002", "STU002", "A002", LocalDateTime.now(), "Jawaban 2"));
        lms.addSubmission(new Submission("SUB003", "STU003", "A003", LocalDateTime.now(), "Jawaban 3"));
        lms.addSubmission(new Submission("SUB004", "STU004", "A004", LocalDateTime.now(), "Jawaban 4"));
        lms.addSubmission(new Submission("SUB005", "STU005", "A005", LocalDateTime.now(), "Jawaban 5"));

        System.out.println();

        lms.processSubmissions();

        System.out.println();

        lms.cancelLastSubmission();

        System.out.println();

        Submission searchResult = lms.searchSubmission("SUB002");

        if (searchResult != null) {
            System.out.println("Submission found: " + searchResult);
        } else {
            System.out.println("Submission not found.");
        }

        // Modul 2
        String expression = "42/(7-5)";

        String postfix = ExpressionEvaluator.infixToPostfix(expression);

        int result = ExpressionEvaluator.evaluatePostfix(postfix);

        System.out.println("Infix   : " + expression);
        System.out.println("Postfix : " + postfix);
        System.out.println("Result  : " + result);
    }

}
