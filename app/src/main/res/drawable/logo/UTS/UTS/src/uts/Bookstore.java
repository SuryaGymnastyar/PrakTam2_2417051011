package uts;

import java.util.*;

public class Bookstore extends Book {

    public Bookstore(ArrayList<String> name, ArrayList<String> author, ArrayList<Double> price, ArrayList<Integer> stock) {
        super(name, author, price, stock);
    }
    
    void tambahBuku(String name, String author, double price, int stock) {
        ArrayList<String> tempName = getName();
        ArrayList<String> tempAuthor = getAuthor();
        ArrayList<Double> tempPrice = getPrice();
        ArrayList<Integer> tempStock = getStock();
        
        tempName.add(name);
        tempAuthor.add(author);
        tempPrice.add(price);
        tempStock.add(stock);
        
        setName(tempName);
        setAuthor(tempAuthor);
        setPrice(tempPrice);
        setStock(tempStock);
    }
    
    void tambahStok(String name, int stock) {
        ArrayList<String> tempName = getName();
        ArrayList<String> tempAuthor = getAuthor();
        ArrayList<Double> tempPrice = getPrice();
        ArrayList<Integer> tempStock = getStock();
        
        boolean found = false;
        int indeks = tempName.indexOf(name);
        
        if (indeks >= 0) {
            tempStock.set(indeks, tempStock.get(indeks) + stock);
        } else {
            System.out.println("Nama buku tidak ada");
        }
    }
    
    void kurangStok(String name, int stock) {
        ArrayList<String> tempName = getName();
        ArrayList<String> tempAuthor = getAuthor();
        ArrayList<Double> tempPrice = getPrice();
        ArrayList<Integer> tempStock = getStock();
        
        boolean found = false;
        int indeks = tempName.indexOf(name);
        
        if (indeks >= 0) {
            tempStock.set(indeks, tempStock.get(indeks) - stock);
        } else {
            System.out.println("Buku dengan nama " + name + " tidak ditemukan");
        }
    }
    
    void tampilInfo() {
        ArrayList<String> tempName = getName();
        ArrayList<String> tempAuthor = getAuthor();
        ArrayList<Double> tempPrice = getPrice();
        ArrayList<Integer> tempStock = getStock();
        
        if (tempName.isEmpty()) {
            System.out.println("Belum ada buku yang tersedia");
        } else {
            System.out.println("Tampilkan semua buku:");
            for (int i = 0; i < tempName.size(); i++) {
                System.out.println((i + 1) + ". " + tempName.get(i) + ", " + tempAuthor.get(i) + ", " + tempPrice.get(i) + ", Stock: " + tempStock.get(i));
            }
        }
        
    }
    
    void cariBuku(String name) {
        ArrayList<String> tempName = getName();
        ArrayList<String> tempAuthor = getAuthor();
        ArrayList<Double> tempPrice = getPrice();
        ArrayList<Integer> tempStock = getStock();
    
        int indeks = tempName.indexOf(name);
        if (indeks >= 0) {
            System.out.println("Data buku " + name);
            System.out.println("Nama Buku: " + tempName.get(indeks));
            System.out.println("Penulis: " + tempAuthor.get(indeks));
            System.out.println("Harga Buku: " + tempPrice.get(indeks));
            System.out.println("Stok Buku: " + tempStock.get(indeks));
        } else {
            System.out.println("Data buku dengan nama " + name + " tidak ada");
        }
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Bookstore tokoBubub = new Bookstore(new ArrayList<String>(), new ArrayList<String>(), new ArrayList<Double>(), new ArrayList<>());

        char pil;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Tambah Buku");
            System.out.println("2. Tambah Stok");
            System.out.println("3. Kurangi Stok");
            System.out.println("4. Tampilkan Semua Buku");
            System.out.println("5. Cari Buku");
            System.out.println("6. Keluar");
            System.out.print("Pilih menu: ");
            
            pil = input.next().charAt(0);
            input.nextLine();
            
            if (pil == '1') {
                
                System.out.print("Masukkan nama buku: ");
                String nama = input.nextLine();
                
                System.out.print("Masukkan pengarang buku: ");
                String penulis = input.nextLine();
                
                System.out.print("Masukkan harga buku: ");
                double harga = input.nextDouble();
                input.nextLine();
                while (harga < 0) {
                    System.out.println("\nHarga tidak boleh bernilai negatif!!");
                    System.out.print("Masukkan kembali harga buku: ");
                    harga = input.nextDouble();
                    input.nextLine();
                }
                
                System.out.print("Masukkan stok buku: ");
                int stok = input.nextInt();
                input.nextLine();
                while (stok <= 0) {
                    System.out.println("\nStok tidak boleh bernilai negatif atau nol!!");
                    System.out.print("Masukkan kembali stok buku: ");
                    stok = input.nextInt();
                    input.nextLine();
                }
                
                tokoBubub.tambahBuku(nama, penulis, harga, stok);
            } else if (pil == '2') {
                System.out.print("Masukkan nama buku: ");
                String nama = input.nextLine();
               
                System.out.print("Masukkan jumlah stok yang ingin ditambah: ");
                int stok = input.nextInt();
                input.nextLine();
                while (stok < 0) {
                    System.out.println("Stok yang ditambah tidak boleh bernilai negatif!");
                    System.out.print("Masukkan kembali jumlah stok yang ingin ditambah: ");
                    stok = input.nextInt();
                    input.nextLine();
                }
                
                tokoBubub.tambahStok(nama, stok);
            } else if (pil == '3') {
                System.out.print("Masukkan nama buku: ");
                String nama = input.nextLine();
               
                System.out.print("Masukkan jumlah stok yang ingin dikurang: ");
                int stok = input.nextInt();
                input.nextLine();
                while (stok < 0) {
                    System.out.println("Stok yang ditambah tidak boleh bernilai negatif!");
                    System.out.print("Masukkan kembali jumlah stok yang ingin dikurang: ");
                    stok = input.nextInt();
                    input.nextLine();
                }
                
                tokoBubub.kurangStok(nama, stok);
            } else if (pil == '4') {
                tokoBubub.tampilInfo();
            } else if (pil == '5') {
                System.out.print("Masukkan nama buku yang ingin dicari: ");
                String nama = input.nextLine();
                
                tokoBubub.cariBuku(nama);
            } else if (pil == '6') {
                System.out.println("Terima kasih telah berkujung... [>_<]");
            } else {
                System.out.println("Pilih menu yang sesuai!");
            }
            
        } while (pil != '6');
    }
}