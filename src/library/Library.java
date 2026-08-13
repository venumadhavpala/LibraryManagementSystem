package library;
import java.util.ArrayList;
import java.util.Comparator;
public class Library {
    private ArrayList<Book> books;
    private ArrayList<Member> members;
    public void addMemberWithoutSaving(Member member) {
        members.add(member);
    }
    public void addBookWithoutSaving(Book book) {
        books.add(book);
    }
    public Library() {
        books = new ArrayList<>(); 
        members = new ArrayList<>();
    }
    public void addBook(Book book) {
        books.add(book);
        sortBooks();
        updateLocations();
        FileManager.saveAllBooks(books);
    }

    public void sortBooks() {
        books.sort(Comparator.comparing(Book::getTitle));
    }
    public ArrayList<Book> getBooks() {
        return books;
    }
    public ArrayList<Member> getMembers() {
        return members;
    }   
    public Book findBookById(String bookId) {
        for (Book book : books) {
            if (book.getBookId().equals(bookId)) {
                return book;
            }
        }
        return null;
    }
    public String issueBook(String bookId, int memberId) {
        Book book = findBookById(bookId);
        Member member = findMemberById(memberId);

        if (book == null) {
            return "Book not found.";
        }

        if (member == null) {
            return "Member not found.";
        }

        if (!book.isAvailable()) {
            return "Book is already issued.";
        }

        book.setAvailable(false);
        book.setIssuedTo(member);

        return "Book issued successfully.";
    }   
    public boolean returnBook(String bookId) {
        Book book = findBookById(bookId);

        if (book == null) {
            return false;
        }

        book.setAvailable(true);
        book.setIssuedTo(null);
        return true;
    }
    public boolean addMember(Member member) {

        if (findMemberById(member.getMemberId()) != null) {
            return false;
        }

        members.add(member);
        FileManager.saveMember(member);
        return true;
    }
    public Member findMemberById(int memberId) {
        for (Member member : members) {
            if (member.getMemberId() == memberId) {
                return member;
            }
        }
        return null;
    }
    public void restoreIssuedBook(String bookId, int memberId) {

        Book book = findBookById(bookId);
        Member member = findMemberById(memberId);

        if (book != null && member != null) {
            book.setAvailable(false);
            book.setIssuedTo(member);
        }
    }
    public ArrayList<Book> findBooksByMainId(String mainId) {

        ArrayList<Book> result = new ArrayList<>();

        for (Book book : books) {
            String bookId = book.getBookId();

            if (bookId.startsWith(mainId + ".")) {
                result.add(book);
            }
        }

        return result;
    }
    public String generateCopyId(String mainId) {

        int copy = 1;

        while (true) {

            String candidate = mainId + "." + copy;

            if (findBookById(candidate) == null) {
                return candidate;
            }

            copy++;
        }
    }
    
    public void assignLocation(Book book) {

        int bookNumber = books.size();

        int rack = ((bookNumber - 1) / 100) + 1;
        int row = ((bookNumber - 1) % 100) / 10 + 1;
        int position = ((bookNumber - 1) % 10) + 1;

        book.setRackNumber(rack);
        book.setRowNumber(row);
        book.setPositionNumber(position);
    }
    public void updateLocations() {

        for (int i = 0; i < books.size(); i++) {

            Book book = books.get(i);

            int bookNumber = i + 1;

            int rack = ((bookNumber - 1) / 100) + 1;
            int row = ((bookNumber - 1) % 100) / 10 + 1;
            int position = ((bookNumber - 1) % 10) + 1;

            book.setRackNumber(rack);
            book.setRowNumber(row);
            book.setPositionNumber(position);
        }
    }
    public void organizeLibrary() {
        sortBooks();
        updateLocations();
        FileManager.saveAllBooks(books);
    }
    public int getTotalBooks() {
        return books.size();
    }
    public int getAvailableBooks() {

        int count = 0;

        for (Book book : books) {
            if (book.isAvailable()) {
                count++;
            }
        }

        return count;
    }
    public int getIssuedBooks() {

        int count = 0;

        for (Book book : books) {
            if (!book.isAvailable()) {
                count++;
            }
        }

        return count;
    }
    public boolean copyExists(String copyId) {
        return findBookById(copyId) != null;
    }
    public String deleteBook(String bookId) {

        Book book = findBookById(bookId);

        if (book == null) {
            return "Book not found.";
        }

        if (!book.isAvailable()) {
            return "Book is currently issued. Return it before deleting.";
        }

        books.remove(book);
        organizeLibrary();
        return "Book deleted successfully.";
    }
    public String deleteMember(int memberId) {

        Member member = findMemberById(memberId);

        if (member == null) {
            return "Member not found.";
        }

        for (Book book : books) {
            if (book.getIssuedTo() != null &&
                book.getIssuedTo().getMemberId() == memberId) {

                return "Member has issued books. Return them before deleting.";
            }
        }

        members.remove(member);
        FileManager.saveAllMembers(members);

        return "Member deleted successfully.";
    }
            
    
    
}
