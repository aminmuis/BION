import java.util.Scanner;

public class SistemKasirToko {
    public static void main(String[] args) {

        // Membuat objek Scanner untuk menerima input pengguna
        Scanner input = new Scanner(System.in);

        // Membuat objek Queue untuk antrian dan Stack untuk riwayat transaksi
        Queue queue = new Queue();
        Stack stack = new Stack();

        // Data awal antrian pelanggan
        queue.enqueue("A001", "Amin", 100000);
        queue.enqueue("A002", "Budi", 98000);
        queue.enqueue("A003", "Citra", 210000);
        queue.enqueue("A004", "Dea", 175000);
        queue.enqueue("A005", "Eko", 125000);

        int pilihan;

        // Menu utama program
        do {

            System.out.println("\n===== SISTEM KASIR TOKO =====");
            System.out.println("1. Tambah Antrian");
            System.out.println("2. Layani Pelanggan");
            System.out.println("3. Tampilkan Antrian");
            System.out.println("4. Tampilkan Riwayat Transaksi");
            System.out.println("5. Keluar");
            System.out.print("Pilih menu : ");

            pilihan = input.nextInt();
            input.nextLine();

            switch (pilihan) {

                case 1:
                    System.out.print("Masukkan Nomor Antrian : ");
                    String nomorAntrian = input.nextLine();

                    System.out.print("Masukkan Nama Pelanggan : ");
                    String namaPelanggan = input.nextLine();

                    System.out.print("Masukkan Total Belanja : ");
                    double totalBelanja = input.nextDouble();
                    input.nextLine();

                    queue.enqueue(nomorAntrian, namaPelanggan, totalBelanja);
                    break;

                case 2:
                    Node pelanggan = queue.dequeue();

                    if (pelanggan != null) {
                        stack.push(pelanggan);
                        System.out.println("Pelanggan " + pelanggan.namaPelanggan + " berhasil dilayani.");
                    }
                    break;

                case 3:
                    queue.display();
                    break;

                case 4:
                    stack.display();
                    break;

                case 5:
                    System.out.println("Terima kasih.");
                    break;

                default:
                    System.out.println("Pilihan tidak valid.");
            }

        } while (pilihan != 5);

        input.close();
    }
}

// Class Node untuk menyimpan data setiap pelanggan
class Node {
    String nomorAntrian;
    String namaPelanggan;
    double totalBelanja;
    Node next;

    public Node(String nomorAntrian, String namaPelanggan, double totalBelanja) {
        this.nomorAntrian = nomorAntrian;
        this.namaPelanggan = namaPelanggan;
        this.totalBelanja = totalBelanja;
        this.next = null;
    }
}

// Class Queue untuk mengelola antrian pelanggan
class Queue {

    Node front;
    Node rear;

    public Queue() {
        front = null;
        rear = null;
    }

    // Menambahkan pelanggan ke bagian belakang antrian
    public void enqueue(String nomorAntrian, String namaPelanggan, double totalBelanja) {

        // Menghilangkan spasi di awal dan akhir input
        nomorAntrian = nomorAntrian.trim();

        // Validasi nomor antrian
        if (nomorAntrian.isEmpty()) {
            System.out.println("Nomor antrian tidak boleh kosong.");
            return;
        }

        if (nomorAntrian.length() > 5) {
            System.out.println("Nomor antrian maksimal 5 karakter.");
            return;
        }

        if (totalBelanja <= 0) {
            System.out.println("Total belanja harus lebih dari 0.");
            return;
        }

        // Memastikan nomor antrian belum pernah digunakan
        Node current = front;
        while (current != null) {
            if (current.nomorAntrian.equalsIgnoreCase(nomorAntrian)) {
                System.out.println("Nomor antrian sudah ada.");
                return;
            }
            current = current.next;
        }

        Node newNode = new Node(nomorAntrian, namaPelanggan, totalBelanja);

        // Jika antrian masih kosong
        if (front == null) {
            front = newNode;
            rear = newNode;
        } else {
            // Menambahkan pelanggan di bagian belakang antrian
            rear.next = newNode;
            rear = newNode;
        }

        System.out.println("Pelanggan " + nomorAntrian + " berhasil ditambahkan ke antrian.");
    }

    // Mengeluarkan pelanggan paling depan dari antrian
    public Node dequeue() {
        Node temp;
        if (front == null) {
            System.out.println("tidak ada antrian");
            return null;
        }

        temp = front;
        front = front.next;

        if (front == null) {
            rear = null;
        }
        temp.next = null;
        return temp;

    }

    // Menampilkan seluruh data antrian
    public void display() {

        if (front == null) {
            System.out.println("Antrian kosong.");
            return;
        }

        System.out.println("===== DAFTAR ANTRIAN =====");

        Node current = front;

        while (current != null) {
            System.out.println("Nomor Antrian : " + current.nomorAntrian);
            System.out.println("Nama Pelanggan: " + current.namaPelanggan);
            System.out.println("Total Belanja : " + current.totalBelanja);
            System.out.println();

            current = current.next;
        }
    }
}

// Class Stack untuk menyimpan riwayat transaksi
class Stack {

    Node top;

    public Stack() {
        top = null;
    }

    // Menambahkan transaksi ke bagian atas stack
    public void push(Node newNode) {

        newNode.next = top;
        top = newNode;

    }

    // Menghapus transaksi paling atas dari stack
    public Node pop() {

        if (top == null) {
            System.out.println("Riwayat transaksi kosong.");
            return null;
        }

        Node temp = top;
        top = top.next;

        temp.next = null;

        return temp;
    }

    // Melihat transaksi paling atas tanpa menghapusnya
    public Node peek() {

        if (top == null) {
            return null;
        }

        return top;
    }

    // Menampilkan seluruh riwayat transaksi
    public void display() {
        Node current = top;

        if (top == null) {
            System.out.println("Riwayat transaksi kosong.");
            return;
        }
        System.out.println("===== RIWAYAT TRANSAKSI =====");
        while (current != null) {
            System.out.println("Nomor antrian: " + current.nomorAntrian);
            System.out.println("Nama pelanggan: " + current.namaPelanggan);
            System.out.println("Total belanja: " + current.totalBelanja);
            current = current.next;
        }
    }
}