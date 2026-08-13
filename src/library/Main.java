package library;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        Library library = new Library();

        FileManager.loadBooks(library);
        FileManager.loadMembers(library);
        FileManager.restoreIssuedBooks(library);

        library.organizeLibrary();
        
             
        
        while (true) {
            System.out.println("\n=========================================");
            System.out.println("      LIBRARY MANAGEMENT SYSTEM");
            System.out.println("=========================================");

            System.out.println("Books in Library : " + library.getTotalBooks());
            System.out.println("Available Books  : " + library.getAvailableBooks());
            System.out.println("Issued Books     : " + library.getIssuedBooks());
            System.out.println("Registered Users : " + library.getMembers().size());

            System.out.println("=========================================");
            
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Search Book");
            System.out.println("4. Add Member");
            System.out.println("5. Memebers");
            System.out.println("6. Issue Book");
            System.out.println("7. Return Book");
            System.out.println("8. Delete Book");
            System.out.println("9. Delete Member");
            System.out.println("10. Export Library report ");
            System.out.println("11. Exit");

            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    scanner.nextLine();

                    System.out.print("Enter main book ID: ");
                    String mainBookId = scanner.nextLine();

                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();

                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();

                    String copyId = library.generateCopyId(mainBookId);
                    if (library.copyExists(copyId)) {
                        System.out.println("Error: Copy ID already exists.");
                        break;
                    }

                    Book newBook = new Book(copyId, title, author);
                    library.addBook(newBook);

                    System.out.println("Book added successfully.");
                    System.out.println("Generated Copy ID: " + copyId);
                    break;
                case 2:
                    ArrayList<Book> bookList = library.getBooks();

                    System.out.println("\n===== ALL BOOKS =====");
                    System.out.println("Total Books: " + bookList.size());

                    int availableCount = 0;
                    int issuedCount = 0;

                    for (Book book : bookList) {
                        if (book.isAvailable()) {
                            availableCount++;
                        } else {
                            issuedCount++;
                        }
                    }

                    System.out.println("Available Books: " + availableCount);
                    System.out.println("Issued Books: " + issuedCount);
                    System.out.println("--------------------");

                    for (Book book : bookList) {
                        System.out.println("Book ID: " + book.getBookId());
                        System.out.println("Title: " + book.getTitle());
                        System.out.println("Author: " + book.getAuthor());

                        if (book.isAvailable()) {
                            System.out.println("Status: Available");
                        } else {
                            System.out.println("Status: Issued");
                            System.out.println("Issued To: " + book.getIssuedTo().getName());
                        }

                        char rackLetter = (char) ('A' + book.getRackNumber() - 1);

                        System.out.println(
                            "Location: Rack-" + rackLetter +
                            " | Row-" + String.format("%02d", book.getRowNumber()) +
                            " | Slot-" + String.format("%02d", book.getPositionNumber())
                        );
                    }
                    break;
                case 3:
                    scanner.nextLine();

                    System.out.print("Enter main book ID to search: ");
                    String mainId = scanner.nextLine();

                    ArrayList<Book> matchingBooks = library.findBooksByMainId(mainId);

                    if (matchingBooks.isEmpty()) {
                        System.out.println("Book not found.");
                    } else {
                        int totalCopies = matchingBooks.size();
                        int availableCopies = 0;
                        int issuedCopies = 0;

                        System.out.println("\n===== SEARCH RESULT =====");

                        for (Book book : matchingBooks) {
                            if (book.isAvailable()) {
                                availableCopies++;
                            } else {
                                issuedCopies++;
                            }
                        }

                        System.out.println("Total Copies: " + totalCopies);
                        System.out.println("Available Copies: " + availableCopies);
                        System.out.println("Issued Copies: " + issuedCopies);
                        System.out.println("--------------------");

                        for (Book book : matchingBooks) {
                            System.out.println("Book ID: " + book.getBookId());
                            System.out.println("Title: " + book.getTitle());
                            System.out.println("Author: " + book.getAuthor());
                            System.out.println("Rack: " + book.getRackNumber());
                            System.out.println("Row: " + book.getRowNumber());
                            System.out.println("Position: " + book.getPositionNumber());

                            if (book.isAvailable()) {
                                System.out.println("Status: Available");
                            } else {
                                System.out.println("Status: Issued");
                                System.out.println("Issued To: " + book.getIssuedTo().getName());
                            }

                            System.out.println("--------------------");
                        }
                    }
                    break;
                case 4:
                    System.out.print("Enter member ID: ");
                    int memberId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter member name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter member email: ");
                    String email = scanner.nextLine();

                    Member newMember = new Member(memberId, name, email);
                    library.addMember(newMember);

                    boolean added = library.addMember(newMember);

                    if (added) {
                        System.out.println("Member added successfully.");
                    } else {
                        System.out.println("Member ID already exists.");
                    }
                    break;
                case 5:
                    ArrayList<Member> memberList = library.getMembers();

                    System.out.println("Total Members: " + memberList.size());

                    for (Member member : memberList) {
                        System.out.println("Member ID: " + member.getMemberId());
                        System.out.println("Name: " + member.getName());
                        System.out.println("Email: " + member.getEmail());
                        System.out.println("--------------------");
                    }
                    break;
                case 6: {
                    scanner.nextLine();

                    System.out.print("Enter Main Book ID (e.g., 102): ");
                    String issueMainId = scanner.nextLine();

                    ArrayList<Book> copies = library.findBooksByMainId(issueMainId);

                    if (copies.isEmpty()) {
                        System.out.println("Book not found.");
                        break;
                    }

                    System.out.println("\nAvailable Copies:");

                    for (Book book : copies) {
                        if (book.isAvailable()) {
                            System.out.println(book.getBookId());
                        }
                    }

                    System.out.print("Select Copy ID: ");
                    String issueCopyId = scanner.nextLine();

                    System.out.print("Enter Member ID: ");
                    int issueMemberId = scanner.nextInt();

                    String result = library.issueBook(issueCopyId, issueMemberId);

                    System.out.println(result);
                    break;
                }
                case 7:
                    System.out.print("Enter book ID to return: ");
                    String returnBookId = scanner.nextLine();

                    boolean returned = library.returnBook(returnBookId);

                    if (returned) {
                        System.out.println("Book returned successfully.");
                    } else {
                        System.out.println("Book could not be returned.");
                    }
                    break;
                case 8: {
                    scanner.nextLine();

                    System.out.print("Enter Copy ID to delete (e.g., 102.2): ");
                    String deleteBookId = scanner.nextLine();

                    System.out.println(library.deleteBook(deleteBookId));
                    break;
                }              
                case 9: {
                    System.out.print("Enter Member ID to delete: ");
                    int deleteMemberId = scanner.nextInt();

                    System.out.println(library.deleteMember(deleteMemberId));
                    break;
                }
                case 10:
                    FileManager.exportReport(library.getBooks(), library.getMembers());
                    break;
                case 11:
                    System.out.println("Thank you for using the Library Management System.");
                    scanner.close();
                    return;
                    
                default:
                    System.out.println("Invalid choice.");
            }
        }
        
        

    }
}
