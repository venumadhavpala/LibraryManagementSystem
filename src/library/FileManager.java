package library;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class FileManager {
    private static final String MEMBER_FILE = "data/members.txt";
    private static final String BOOK_FILE = "data/books.txt";
    private static final ArrayList<String[]> pendingIssuedBooks = new ArrayList<>();

    public static void saveMember(Member member) {

        try (FileWriter writer = new FileWriter(MEMBER_FILE, true)) {

            writer.write(
                member.getMemberId() + "," +
                member.getName() + "," +
                member.getEmail() + "\n"
            );

        } 
        catch (IOException e) {
            System.out.println("Error saving member.");
        }
    }
    public static void loadMembers(Library library) {

        try (Scanner scanner = new Scanner(new File(MEMBER_FILE))) {

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

                String[] data = line.split(",");

                int id = Integer.parseInt(data[0]);
                String name = data[1];
                String email = data[2];

                Member member = new Member(id, name, email);

                library.addMemberWithoutSaving(member);
            }

        } 
        catch (FileNotFoundException e) {
            System.out.println("No member data found.");
        }
    }
    public static void saveAllBooks(ArrayList<Book> books) {

        try (FileWriter writer = new FileWriter(BOOK_FILE)) {

            for (Book book : books) {

                String issuedTo = "-";

                if (book.getIssuedTo() != null) {
                    issuedTo = String.valueOf(book.getIssuedTo().getMemberId());
                }

                writer.write(
                        book.getBookId() + "," +
                        book.getTitle() + "," +
                        book.getAuthor() + "," +
                        book.isAvailable() + "," +
                        issuedTo + "," +
                        book.getRackNumber() + "," +
                        book.getRowNumber() + "," +
                        book.getPositionNumber() + "\n");
            }

        } catch (IOException e) {
            System.out.println("Error saving books.");
        }
    }
    public static void loadBooks(Library library) {

        try (Scanner scanner = new Scanner(new File(BOOK_FILE))) {

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();
                String[] data = line.split(",");

                Book book = new Book(data[0], data[1], data[2]);

                book.setAvailable(Boolean.parseBoolean(data[3]));

                book.setRackNumber(Integer.parseInt(data[5]));
                book.setRowNumber(Integer.parseInt(data[6]));
                book.setPositionNumber(Integer.parseInt(data[7]));
                if (!data[4].equals("-")) {
                    pendingIssuedBooks.add(new String[]{data[0], data[4]});
                }

                library.addBookWithoutSaving(book);
            }

        } catch (FileNotFoundException e) {
            System.out.println("No book data found.");
        }
    }
    public static void restoreIssuedBooks(Library library) {

        for (String[] item : pendingIssuedBooks) {

            String bookId = item[0];
            int memberId = Integer.parseInt(item[1]);

            library.restoreIssuedBook(bookId, memberId);
        }

        pendingIssuedBooks.clear();
    }
    public static void saveAllMembers(ArrayList<Member> members) {

        try (FileWriter writer = new FileWriter(MEMBER_FILE)) {

            for (Member member : members) {

                writer.write(
                        member.getMemberId() + "," +
                        member.getName() + "," +
                        member.getEmail() + "\n");
            }

        } catch (IOException e) {
            System.out.println("Error saving members.");
        }
    }
    public static void exportReport(ArrayList<Book> books, ArrayList<Member> members) {

        try (FileWriter writer = new FileWriter("library_report.txt")) {

            writer.write("=========================================\n");
            writer.write("        LIBRARY REPORT\n");
            writer.write("=========================================\n\n");

            writer.write("Total Books: " + books.size() + "\n");
            writer.write("Total Members: " + members.size() + "\n\n");

            writer.write("========== BOOKS ==========\n");

            for (Book book : books) {

                writer.write("\nBook ID: " + book.getBookId() + "\n");
                writer.write("Title: " + book.getTitle() + "\n");
                writer.write("Author: " + book.getAuthor() + "\n");

                if (book.isAvailable()) {
                    writer.write("Status: Available\n");
                } else {
                    writer.write("Status: Issued\n");
                    writer.write("Issued To: " + book.getIssuedTo().getName() + "\n");
                }

                char rack = (char) ('A' + book.getRackNumber() - 1);

                writer.write("Location: Rack-" + rack +
                        " | Row-" + String.format("%02d", book.getRowNumber()) +
                        " | Slot-" + String.format("%02d", book.getPositionNumber()) + "\n");

                writer.write("-------------------------\n");
            }

            writer.write("\n========== MEMBERS ==========\n");

            for (Member member : members) {

                writer.write("\nMember ID: " + member.getMemberId() + "\n");
                writer.write("Name: " + member.getName() + "\n");
                writer.write("Email: " + member.getEmail() + "\n");
                writer.write("-------------------------\n");
            }

            System.out.println("Library report exported successfully.");

        } catch (IOException e) {
            System.out.println("Error exporting report.");
        }
    }
}