import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainView {
    private JFrame frame;
    private JComboBox<String> branchComboBox;
    private JComboBox<String> campusComboBox;
    private AVLTree avlTree;
    private List<AVLNode> storedBooks;

    public MainView() {
        frame = new JFrame("Biblioteca universidad");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);

        avlTree = new AVLTree();
        storedBooks = new ArrayList<>();

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        JButton storeBookButton = new JButton("Almacenar Libros");
        JButton searchBooksButton = new JButton("Buscar Libros");
        JButton deleteBookButton = new JButton("Borrar Libros");

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(storeBookButton, constraints);

        constraints.gridy = 1;
        panel.add(searchBooksButton, constraints);

        constraints.gridy = 2;
        panel.add(deleteBookButton, constraints);

        storeBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openStoreWindow();
            }
        });

        searchBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSearchWindow();
            }
        });

        deleteBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDeleteWindow();
            }
        });

        JButton listAllBooksButton = new JButton("Listar todos los libros");
        constraints.gridy = 4;
        panel.add(listAllBooksButton, constraints);

        listAllBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listAllBooks();
            }
        });
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private void openStoreWindow() {
        JFrame storeFrame = new JFrame("Almacenar Libro");
        storeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        storeFrame.setSize(1000, 1000);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);
        JLabel branchLabel = new JLabel("Seleccione sede:");

        branchComboBox = new JComboBox<>(new String[]{"Tunja", "Duitama"});

        JLabel campusLabel = new JLabel("Seleccione Campus:");

        campusComboBox = new JComboBox<>(new String[]{""});

        branchComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedBranch = (String) branchComboBox.getSelectedItem();
                updateAvailableCampuses(campusComboBox, selectedBranch);
            }
        });

        JLabel titleLabel = new JLabel("Titulo:");
        JLabel isbnLabel = new JLabel("ISBN:");
        JLabel authorLabel = new JLabel("Autor:");
        JLabel publisherLabel = new JLabel("Editorial:");
        JLabel volumeLabel = new JLabel("Volumen:");
        JLabel authorBiographyLabel = new JLabel("Biografia de Autor:");

        JTextField titleField = new JTextField();
        JTextField isbnField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField publisherField = new JTextField();
        JTextField volumeField = new JTextField();
        JTextField authorBiographyField = new JTextField();

        JButton storeButton = new JButton("Almacenar libros");

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(branchLabel, constraints);

        constraints.gridx = 1;
        panel.add(branchComboBox, constraints);

        constraints.gridy = 1;
        constraints.gridx = 0;
        panel.add(campusLabel, constraints);

        constraints.gridx = 1;
        panel.add(campusComboBox, constraints);

        constraints.gridy = 2;
        constraints.gridx = 0;
        panel.add(titleLabel, constraints);

        constraints.gridx = 1;
        panel.add(titleField, constraints);

        constraints.gridy = 3;
        constraints.gridx = 0;
        panel.add(isbnLabel, constraints);

        constraints.gridx = 1;
        panel.add(isbnField, constraints);

        constraints.gridy = 4;
        constraints.gridx = 0;
        panel.add(authorLabel, constraints);

        constraints.gridx = 1;
        panel.add(authorField, constraints);

        constraints.gridy = 5;
        constraints.gridx = 0;
        panel.add(publisherLabel, constraints);

        constraints.gridx = 1;
        panel.add(publisherField, constraints);

        constraints.gridy = 6;
        constraints.gridx = 0;
        panel.add(volumeLabel, constraints);

        constraints.gridx = 1;
        panel.add(volumeField, constraints);

        constraints.gridy = 7;
        constraints.gridx = 0;
        panel.add(authorBiographyLabel, constraints);

        constraints.gridx = 1;
        panel.add(authorBiographyField, constraints);

        storeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedBranch = (String) branchComboBox.getSelectedItem();
                String selectedCampus = (String) campusComboBox.getSelectedItem();
                String title = titleField.getText();
                String isbnText = isbnField.getText();
                String author = authorField.getText();
                String publisher = publisherField.getText();
                String volumeText = volumeField.getText();
                String authorBiography = authorBiographyField.getText();

                if (isNumeric(isbnText) && isNumeric(volumeText)) {
                    int isbn = Integer.parseInt(isbnText);
                    int volume = Integer.parseInt(volumeText);

                    avlTree.insert(isbn, title, String.valueOf(volume), publisher, selectedCampus, author, authorBiography);
                    AVLNode newBook = new AVLNode(isbn, title, String.valueOf(volume), publisher, selectedCampus, author, authorBiography);
                    storedBooks.add(newBook);
                    JOptionPane.showMessageDialog(storeFrame, "Libro almacenado en:'" + selectedBranch + "' - '" + selectedCampus + "'.");
                } else {
                    JOptionPane.showMessageDialog(storeFrame, "ISBN y Volumen deben de ser un valor numerico.");
                }
            }
        });
        constraints.gridy = 8;
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        
        panel.add(storeButton, constraints);
        storeFrame.getContentPane().add(panel);
        storeFrame.setVisible(true);
    }

    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    private void openSearchWindow() {
        JFrame searchFrame = new JFrame("Buscar Libros");
        searchFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        searchFrame.setSize(1000, 1000);
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel branchLabelVer = new JLabel("Seleccione la sede:");
        JComboBox<String> branchComboBoxVer = new JComboBox<>(new String[]{"Tunja", "Duitama"});

        JLabel campusLabelVer = new JLabel("Seleccione campus:");
        JComboBox<String> campusComboBoxVer = new JComboBox<>(new String[]{""});

        branchComboBoxVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedBranch = (String) branchComboBoxVer.getSelectedItem();
                updateAvailableCampuses(campusComboBoxVer, selectedBranch);
            }
        });

        JButton searchButton = new JButton("Buscar Libros");

        JTextArea resultsTextArea = new JTextArea(20, 60);
        resultsTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultsTextArea);

        JLabel copiesLabel = new JLabel("Numero de copias:");
        JComboBox<Integer> copiesComboBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedBranch = (String) branchComboBoxVer.getSelectedItem();
                String selectedCampus = (String) campusComboBoxVer.getSelectedItem();
                int selectedCopies = (int) copiesComboBox.getSelectedItem();
                showBooksByBranchAndCampus(selectedBranch, selectedCampus, selectedCopies, resultsTextArea);
            }
        });

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(branchLabelVer, constraints);

        constraints.gridx = 1;
        panel.add(branchComboBoxVer, constraints);

        constraints.gridy = 1;
        constraints.gridx = 0;
        panel.add(campusLabelVer, constraints);

        constraints.gridx = 1;
        panel.add(campusComboBoxVer, constraints);

        constraints.gridy = 2;
        constraints.gridx = 0;
        panel.add(copiesLabel, constraints);

        constraints.gridx = 1;
        panel.add(copiesComboBox, constraints);

        constraints.gridy = 3;
        constraints.gridx = 0;
        panel.add(searchButton, constraints);

        constraints.gridy = 4;
        constraints.gridx = 0;
        panel.add(scrollPane, constraints);

        searchFrame.getContentPane().add(panel);
        searchFrame.setVisible(true);
    }

    private void showBooksByBranchAndCampus(String branch, String campus, int selectedCopies, JTextArea resultsTextArea) {
        resultsTextArea.setText("");
        for (AVLNode book : storedBooks) {
            if (book.library.equals(campus) && avlTree.getCopiesByISBN(book.isbn) >= selectedCopies) {
                resultsTextArea.append("Titulo: " + book.title + "\n");
                resultsTextArea.append("ISBN: " + book.isbn + "\n");
                resultsTextArea.append("Autor: " + book.author + "\n");
                resultsTextArea.append("Editorial: " + book.publisher + "\n\n");
            }
        }
    }

    private void updateAvailableCampuses(JComboBox<String> campusComboBox, String selectedBranch) {
        campusComboBox.removeAllItems();
        if (selectedBranch != null) {
            if (selectedBranch.equals("Tunja")) {
                campusComboBox.addItem("Facultad De medicina");
                campusComboBox.addItem("Edificio central - FESAD");
                campusComboBox.addItem("Facultad de estudios a Distancia");
            } else if (selectedBranch.equals("Duitama")) {
                campusComboBox.addItem("Regional Centro");
            }
        }
    }

    private void openDeleteWindow() {
        JFrame deleteFrame = new JFrame("Borrar Libro");
        deleteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        deleteFrame.setSize(1000, 1000);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel isbnLabel = new JLabel("ISBN del libro a Eliminar:");
        JTextField isbnField = new JTextField();
        isbnField.setColumns(15);

        JButton deleteButton = new JButton("Borrar Libro");

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(isbnLabel, constraints);

        constraints.gridx = 1;
        panel.add(isbnField, constraints);

        constraints.gridy = 1;
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        panel.add(deleteButton, constraints);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int isbn = Integer.parseInt(isbnField.getText());

                    AVLNode bookToDelete = searchBookByISBN(isbn);

                    if (bookToDelete != null) {
                        avlTree.delete(isbn);
                        storedBooks.remove(bookToDelete);
                        JOptionPane.showMessageDialog(deleteFrame, "LIbro borradon con exito.");
                    } else {
                        JOptionPane.showMessageDialog(deleteFrame, "No hay libro con ese ISBN. ");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(deleteFrame, "Ingrese un ISBN Valido.");
                }
            }
        });
        deleteFrame.getContentPane().add(panel);
        deleteFrame.setVisible(true);
    }

    private AVLNode searchBookByISBN(int isbn) {
        for (AVLNode book : storedBooks) {
            if (book.isbn == isbn) {
                return book;
            }
        }
        return null;
    }

    private void listAllBooks() {
        JFrame listBooksFrame = new JFrame("Listar libros");
        listBooksFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        listBooksFrame.setSize(1000, 1000);

        JTextArea resultsTextArea = new JTextArea(20, 60);
        resultsTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultsTextArea);

        for (AVLNode book : storedBooks) {
            resultsTextArea.append("Facultad: " + book.library + "\n");
            resultsTextArea.append("Titulo: " + book.title + "\n");
            resultsTextArea.append("ISBN: " + book.isbn + "\n");
            resultsTextArea.append("Autor: " + book.author + "\n");
            resultsTextArea.append("Editorial: " + book.publisher + "\n");
            resultsTextArea.append("Volumen: " + book.volume + "\n");
            resultsTextArea.append("Biografía del Autor: " + book.authorBiography + "\n\n");
        }
        listBooksFrame.getContentPane().add(scrollPane);
        listBooksFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainView();
            }
        });
    }
}