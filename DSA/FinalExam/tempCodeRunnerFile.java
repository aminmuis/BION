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

public class Lms {
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

    // Class utama
    public static void main(String[] args) {
        Lms lms = new Lms();

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

        Submission result = lms.searchSubmission("SUB002");

        if (result != null) {
            System.out.println("Submission found: " + result);
        } else {
            System.out.println("Submission not found.");
        }
    }

}
