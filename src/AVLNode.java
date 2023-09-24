class AVLNode {
    int isbn;
    String title;
    String volume;
    String publisher;
    String library;
    String author;
    String authorBiography;
    AVLNode left;
    AVLNode right;
    int height;

    AVLNode(int isbn, String title, String volume, String publisher, String library, String author, String authorBiography) {
        this.isbn = isbn;
        this.title = title;
        this.volume = volume;
        this.publisher = publisher;
        this.library = library;
        this.author = author;
        this.authorBiography = authorBiography;
        this.left = null;
        this.right = null;
        this.height = 1;
    }
}