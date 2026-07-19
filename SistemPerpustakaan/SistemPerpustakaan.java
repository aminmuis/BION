import java.util.Scanner;

public class SistemPerpustakaan {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        LinkedList daftarBuku = new LinkedList();

        // Data awal buku
        daftarBuku.tambahBuku("B001", "Laskar Pelangi", "Andrea Hirata");
        daftarBuku.tambahBuku("B002", "Pulang", "Tere Liye");
        daftarBuku.tambahBuku("B003", "Pergi", "Tere Liye");
        daftarBuku.tambahBuku("B004", "Atomic Habits", "James Clear");
        daftarBuku.tambahBuku("B005", "Hujan", "Tere Liye");

        int pilihan;

        // Menu utama program
        do {

            System.out.println("\n=== SISTEM PERPUSTAKAAN ===");
            System.out.println("1. Tambah Buku");
            System.out.println("2. Hapus Buku");
            System.out.println("3. Cari Buku");
            System.out.println("4. Tampilkan Semua Buku");
            System.out.println("5. Keluar");
            System.out.print("Pilih menu : ");

            pilihan = input.nextInt();
            input.nextLine();

            switch (pilihan) {
                case 1:

                    System.out.print("Kode Buku : ");
                    String kode = input.nextLine();

                    System.out.print("Judul Buku : ");
                    String judul = input.nextLine();

                    System.out.print("Penulis : ");
                    String penulis = input.nextLine();

                    daftarBuku.tambahBuku(kode, judul, penulis);

                    break;

                case 2:

                    daftarBuku.delete();

                    break;

                case 3:

                    System.out.print("Masukkan kode buku : ");
                    String keyword = input.nextLine();

                    daftarBuku.search(keyword);

                    break;

                case 4:

                    daftarBuku.display();

                    break;

                case 5:

                    System.out.println("Program selesai.");

                    break;

                default:

                    System.out.println("Menu tidak tersedia.");
            }

        } while (pilihan != 5);

        input.close();
    }
}

// Class Node untuk menyimpan data buku
class Node {
    String kodeBuku;
    String judul;
    String penulis;
    Node next;

    public Node(String kodeBuku, String judul, String penulis) {
        this.kodeBuku = kodeBuku;
        this.judul = judul;
        this.penulis = penulis;
        this.next = null;
    }
}

// Class LinkedList untuk mengelola data buku
class LinkedList {

    Node head;

    public LinkedList() {
        head = null;
    }

    // Menambahkan buku ke akhir linked list
    public void tambahBuku(String kode, String judul, String penulis) {
        // menghilangkan spasi
        kode = kode.trim();

        // validasi kode tidak boleh kosong
        if (kode.isEmpty()) {
            System.out.println("Kode buku tidak boleh kosong.");
            return;
        }

        // validasi jumlah maksimal karakter kode
        if (kode.length() > 5) {
            System.out.println("Kode buku maksimal 5 karakter.");
            return;
        }

        Node current = head;

        // validasi tidak ada kode yang sama dalam data tersimpan
        while (current != null) {

            if (current.kodeBuku.equalsIgnoreCase(kode)) {
                System.out.println("Kode buku sudah terdaftar");
                return;
            }

            current = current.next;
        }

        Node newNode = new Node(kode, judul, penulis);

        // jika linked list kosong
        if (head == null) {
            head = newNode;
            System.out.println("Buku " + kode + " berhasil ditambahkan.");
            return;
        }

        // cari node terakhir
        current = head;

        while (current.next != null) {
            current = current.next;
        }

        // sambungkan node baru
        current.next = newNode;
        System.out.println("Buku " + kode + " berhasil ditambahkan.");
    }

    // Menghapus buku terakhir dari linked list
    public void delete() {

        // data kosong
        if (head == null) {
            System.out.println("Tidak ada data untuk dihapus.");
            return;
        }

        // hanya satu node
        if (head.next == null) {
            System.out.println("Buku terakhir (" + head.kodeBuku + ") berhasil dihapus.");
            head = null;
            return;
        }

        Node current = head;

        while (current.next.next != null) {
            current = current.next;
        }

        System.out.println("Buku terakhir (" + current.next.kodeBuku + ") berhasil dihapus.");
        current.next = null;
    }

    // Mencari buku berdasarkan kode buku
    public void search(String kode) {

        Node current = head;

        while (current != null) {

            if (current.kodeBuku.equalsIgnoreCase(kode)) {

                System.out.println("Buku ditemukan");
                System.out.println("Kode    : " + current.kodeBuku);
                System.out.println("Judul   : " + current.judul);
                System.out.println("Penulis : " + current.penulis);

                return;
            }

            current = current.next;
        }

        System.out.println("Buku tidak ditemukan.");
    }

    // Menampilkan seluruh data buku
    public void display() {

        if (head == null) {
            System.out.println("Daftar buku kosong.");
            return;
        }

        Node current = head;
        int jumlah = 0;

        while (current != null) {
            System.out.println("Kode    : " + current.kodeBuku);
            System.out.println("Judul   : " + current.judul);
            System.out.println("Penulis : " + current.penulis);
            System.out.println("--------------------------");

            // Menghitung jumlah buku
            jumlah++;
            current = current.next;
        }

        System.out.println("Total buku : " + jumlah);
    }

}