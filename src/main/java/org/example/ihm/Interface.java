package org.example.ihm;

import org.example.BLL.Contact;
import org.example.BLL.ContactImage;
import org.example.BLL.Group;
import org.example.DAO.ContactDAO;
import org.example.DAO.ContactImageDAO;
import org.example.DAO.GroupesDAO;
import org.example.DAO.Exceptions.DataBaseException;
import org.example.DAO.Exceptions.ImageLoadingException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Interface extends JFrame {
    private JPanel MainTab;
    private JPanel header;
    private JPanel LeftPanel;
    private JPanel rightPanel;
    private JPanel body;
    private JPanel tableHolder;
    private JLabel nomLabel;
    private JLabel prenomLabel;
    private JTextField nomField;
    private JTextField prenomField;
    private JPanel searchBar;
    private JButton addContact;
    private JTextField searchField;
    private JButton searchButton;
    private DefaultTableModel tableModel;
    private JTable table1;
    private JLabel num1Label;
    private JLabel num2Label;
    private JTextField num1Field;
    private JTextField num2Field;
    private JLabel emailProLabel;
    private JTextField emailProField;
    private JLabel emailPersoLabel;
    private JLabel adresseLabel;
    private JTextField emailPersoField;
    private JTextField adresseField;
    private JPanel sexCheck;
    private JRadioButton hommeRadioButton;
    private JRadioButton femmeRadioButton;
    private JPanel searchPanel;
    private JPanel buttomPanel;
    private JLabel contactLabel;
    private JButton modifyButton;
    private JButton deleteButton;
    private JLabel groupsLabel;
    private JPanel listHolder;
    private JTree tree1;
    private JScrollBar scrollBar1;
    private JScrollBar scrollBar2;
    private JLabel info;
    private JButton display;
    private JButton addGroupButton;
    private JLabel imageL;
    private JLabel imageL2;
    private JLabel imageL3;
    private JButton addPictureOptionalButton;
    private JLabel userPicL;
    private ButtonGroup radioButtonGroup;

    private byte[] imageData;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Interface() {


        setTitle("Contact Manager");
        ImageIcon logo = new ImageIcon("logo ENSAH.png");
        setIconImage(logo.getImage());

        radioButtonGroup = new ButtonGroup();

        radioButtonGroup.add(hommeRadioButton);
        radioButtonGroup.add(femmeRadioButton);

        ImageIcon img = new ImageIcon("space.png");
        // Get the original image from the ImageIcon
        Image originalImage = img.getImage();

        // Define the desired width and height
        int newWidth = 200;
        int newHeight = 60;

        // Resize the image using the getScaledInstance() method
        Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        // Create a new ImageIcon with the resized image
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        imageL.setIcon(resizedIcon);
        imageL2.setIcon(resizedIcon);
        imageL3.setIcon(resizedIcon);



        // Create the table model with column names and initial data
        tableModel = new DefaultTableModel(
                new Object[][]{

                },
                new String[]{"id","nom", "prenom", "telephone1", "adress","email professionnel","genre","telephone2", "email personnel"}) {

            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells non-editable
                return false;
            }
        };

        // Create the JTable with the table model
        table1 = new JTable(tableModel);


        // Create a scroll pane and add the JTable to it
        JScrollPane scrollPane = new JScrollPane(table1);

        // Set the layout manager for the tableHolder panel
        tableHolder.setLayout(new BorderLayout());

        // Add the scroll pane to the tableHolder panel
        tableHolder.add(scrollPane, BorderLayout.CENTER);

        addContact.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = nomField.getText();
                String prenom = prenomField.getText();
                String num1 = num1Field.getText();
                String num2 = num2Field.getText();
                String emailPro = emailProField.getText();
                String emailPerso = emailPersoField.getText();
                String adresse = adresseField.getText();
                //String genre ;   (hommeRadioButton.isSelected()) ? "Male" : "Female";

                String regexEmailperso = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
                String regexEmailpro = "^[a-zA-Z]+\\.[a-zA-Z]+@etu\\.uae\\.ac\\.ma$";


                if(nom.equals("") || prenom.equals("") || num1.equals("") || num2.equals("") || emailPro.equals("") || emailPerso.equals("") || adresse.equals("")){
                    JOptionPane.showMessageDialog(null, "Erreur de saisie, tout les cases sont obligatoires", "Error", JOptionPane.ERROR_MESSAGE);
                }else if(!hommeRadioButton.isSelected() && !femmeRadioButton.isSelected()){
                    JOptionPane.showMessageDialog(null, "Erreur de saisie, tout les cases sont obligatoires", "Error", JOptionPane.ERROR_MESSAGE);
                }else if (!nom.matches("[a-zA-Z]+") || !prenom.matches("[a-zA-Z]+")) {
                        JOptionPane.showMessageDialog(null,"Le nom ne doit contenir que des lettres.");

                }else if(!num1.matches("^(?:\\+212|0)[5-7]\\d{8}$") || !num1.matches("^(?:\\+212|0)[5-7]\\d{8}$")){
                        JOptionPane.showMessageDialog(null,"verifiez le syntax de votre numero de telephone.");
                }else if(!emailPro.matches(regexEmailpro)){
                        JOptionPane.showMessageDialog(null,"verifiez le syntax de votre email professionnel (prenom.nom@etu.uae.ac.ma).");
                }else if(!emailPerso.matches(regexEmailperso)){
                        JOptionPane.showMessageDialog(null,"verifiez le syntax de votre email personnel.");
                }
                else{
                    Contact contact = new Contact(nom,prenom,num1,adresse,emailPro,hommeRadioButton.isSelected() ? "Homme" : "Femme",num2, emailPerso);
                    ContactDAO contactData = new ContactDAO();
                    try {
                        contactData.create(contact);
                        ContactImage img = new ContactImage(contact.getId(),imageData);
                        ContactImageDAO dao = new ContactImageDAO();
                        dao.add(img);
                        JOptionPane.showMessageDialog(null, "contact successfully added");
                        nomField.setText("");
                        prenomField.setText("");
                        num1Field.setText("");
                        num2Field.setText("");
                        emailProField.setText("");
                        emailPersoField.setText("");
                        adresseField.setText("");
                        userPicL.setIcon(null);
                        imageData = null;
                    } catch (DataBaseException ex) {
                        JOptionPane.showMessageDialog(null, "Erreur lié à la connexion à la base de donnéee. merci de ressayer ulterieurement", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (ImageLoadingException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(searchField.getText().equals("")){
                    ContactDAO dao = new ContactDAO();
                    List<Contact> contacts = new ArrayList<>();
                    try {
                        contacts = dao.rechercherToutContact();
                    } catch (DataBaseException ex) {
                        JOptionPane.showMessageDialog(null, "Erreur lié à la connexion à la base de donnéee. merci de ressayer ulterieurement", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    if(contacts==null){
                        JOptionPane.showMessageDialog(null, "y'a aucun contact dans votre liste de contact. il faut creer des connaissances quand meme -_-", "Error", JOptionPane.ERROR_MESSAGE);
                    }else{

                        tableModel.setRowCount(0); // Clear existing table data

                        for (Contact contact : contacts) {
                            // Extract the required data from the 'contact' object
                            setId(contact.getId());
                            String nom = contact.getNom();
                            String prenom = contact.getPrenom();
                            String num1 = contact.getTelephone1();
                            String adress = contact.getAdress();
                            String emailPro = contact.getEmailPro();
                            String genre = contact.getGenre();
                            String num2 = contact.getTelephone2();
                            String emailPerso = contact.getEmailPerso();

                            // Add a new row to the table model
                            tableModel.addRow(new Object[]{id, nom, prenom, num1, adress, emailPro, genre, num2, emailPerso});
                        }
                    }
                }
                else{
                    ContactDAO dao = new ContactDAO();
                    List<Contact> contacts = new ArrayList<>();
                    try {

                        contacts = dao.rechercherContactParNom(searchField.getText());

                    } catch (DataBaseException ex) {
                        JOptionPane.showMessageDialog(null, "Erreur lié à la connexion à la base de donnéee. merci de ressayer ulterieurement", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    if(contacts!=null){

                        tableModel.setRowCount(0); // Clear existing table data

                        for (Contact contact : contacts) {
                            // Extract the required data from the 'contact' object
                            setId(contact.getId());
                            String nom = contact.getNom();
                            String prenom = contact.getPrenom();
                            String num1 = contact.getTelephone1();
                            String adress = contact.getAdress();
                            String emailPro = contact.getEmailPro();
                            String genre = contact.getGenre();
                            String num2 = contact.getTelephone2();
                            String emailPerso = contact.getEmailPerso();

                            // Add a new row to the table model
                            tableModel.addRow(new Object[]{id, nom, prenom, num1, adress, emailPro, genre, num2, emailPerso});
                        }
                    }else {
                        ContactDAO dao2 = new ContactDAO();
                        List<Contact> contacts2 = new ArrayList<>();
                        try {
                            contacts2 = dao2.rechercherContactParNum(searchField.getText());
                        } catch (DataBaseException ex) {
                            JOptionPane.showMessageDialog(null, "Erreur lié à la connexion à la base de donnéee. merci de ressayer ulterieurement", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        if (contacts2 == null) {
                            JOptionPane.showMessageDialog(null, "AUCUN CONTACT DANS VOTRE LIST EXIST AVEC TELL NOM OU NUMERO", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {


                            tableModel.setRowCount(0); // Clear existing table data

                            for (Contact contact : contacts2) {
                                // Extract the required data from the 'contact' object
                                setId(contact.getId());
                                String nom = contact.getNom();
                                String prenom = contact.getPrenom();
                                String num1 = contact.getTelephone1();
                                String adress = contact.getAdress();
                                String emailPro = contact.getEmailPro();
                                String genre = contact.getGenre();
                                String num2 = contact.getTelephone2();
                                String emailPerso = contact.getEmailPerso();

                                // Add a new row to the table model
                                tableModel.addRow(new Object[]{id, nom, prenom, num1, adress, emailPro, genre, num2, emailPerso});
                            }
                        }
                    }
                }
            }



        });




        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) { // Check if right-click event
                    // Perform actions for right-click
                    int selectedRow = table1.getSelectedRow();
                    int selectedColumn = table1.getSelectedColumn();
                    if (selectedRow != -1 && selectedColumn != -1) {
                        // Access the value of the selected cell
                        Object cellValue = table1.getValueAt(selectedRow, selectedColumn);
                        System.out.println("Selected Cell Value: " + cellValue.toString());

                        // Perform additional actions with the selected row and column
                        ContactDisplay c = new ContactDisplay();
                        c.getNomL().setText((String) table1.getValueAt(selectedRow, 1));
                        c.getPrenomL().setText((String) table1.getValueAt(selectedRow, 2));
                        c.getNum1L().setText((String) table1.getValueAt(selectedRow, 3));
                        c.getAdressL().setText((String) table1.getValueAt(selectedRow, 4));
                        c.getEmailProL().setText((String) table1.getValueAt(selectedRow, 5));
                        c.getGenreL().setText((String) table1.getValueAt(selectedRow, 6));
                        c.getNum2L().setText((String) table1.getValueAt(selectedRow, 7));
                        c.getEmailPersoL().setText((String) table1.getValueAt(selectedRow, 8));
                        c.getIdL().setText( table1.getValueAt(selectedRow, 0).toString());

                        ContactImageDAO dao = new ContactImageDAO();
                        try {
                            byte[] contactImg = dao.getImage((int) table1.getValueAt(selectedRow, 0));
                            if(contactImg != null){
                                ImageIcon imageIcon = new ImageIcon(contactImg);
                                Image originalImage = imageIcon.getImage();
                                Image resizedIcon = originalImage.getScaledInstance(300,300, Image.SCALE_SMOOTH);
                                ImageIcon contactIcon = new ImageIcon(resizedIcon);
                                c.getImage().setIcon(contactIcon);
                            }
                        } catch (DataBaseException ex) {
                            throw new RuntimeException(ex);
                        } catch (ImageLoadingException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
                }
            }



        });


        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() ==-1){}
                if(e.getClickCount() == 2){
                    System.out.println(table1.getValueAt(table1.getSelectedRow(),table1.getSelectedColumn()));

                    nomField.setText((String)table1.getValueAt(table1.getSelectedRow(),1));
                    prenomField.setText((String)table1.getValueAt(table1.getSelectedRow(),2));
                    num1Field.setText((String)table1.getValueAt(table1.getSelectedRow(),3));
                    adresseField.setText((String)table1.getValueAt(table1.getSelectedRow(),4));
                    emailProField.setText((String)table1.getValueAt(table1.getSelectedRow(),5));
                    if ((table1.getValueAt(table1.getSelectedRow(), 6)).toString().equals("Homme")) {
                        hommeRadioButton.setSelected(true);
                    } else {
                        femmeRadioButton.setSelected(true);
                    }
                    num2Field.setText((String)table1.getValueAt(table1.getSelectedRow(),7));
                    emailPersoField.setText((String)table1.getValueAt(table1.getSelectedRow(),8));

                    int id = (Integer) table1.getValueAt(table1.getSelectedRow(), 0);


                }
            }
        });
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nom = nomField.getText();
                String prenom = prenomField.getText();
                String num1 = num1Field.getText();
                String num2 = num2Field.getText();
                String emailPro = emailProField.getText();
                String emailPerso = emailPersoField.getText();
                String adresse = adresseField.getText();
                int id = (Integer) table1.getValueAt(table1.getSelectedRow(), 0);
                //String genre ;   (hommeRadioButton.isSelected()) ? "Male" : "Female";

                if(nom.equals("") || prenom.equals("") || num1.equals("") || num2.equals("") || emailPro.equals("") || emailPerso.equals("") || adresse.equals("")){
                    JOptionPane.showMessageDialog(null, "Erreur de saisie, tout les cases sont obligatoires", "Error", JOptionPane.ERROR_MESSAGE);
                }else if(!hommeRadioButton.isSelected() && !femmeRadioButton.isSelected()){
                    JOptionPane.showMessageDialog(null, "Erreur de saisie, tout les cases sont obligatoires", "Error", JOptionPane.ERROR_MESSAGE);
                }else if (!nom.matches("[a-zA-Z]+") || !prenom.matches("[a-zA-Z]+")) {
                    JOptionPane.showMessageDialog(null,"Le nom ne doit contenir que des lettres.");

                }else if(!num1.matches("^(?:\\+212|0)[5-7]\\d{8}$") || !num1.matches("^(?:\\+212|0)[5-7]\\d{8}$")){
                    JOptionPane.showMessageDialog(null,"verifiez le syntax de votre numero de telephone.");
                }else if(!emailPro.matches("^[a-zA-Z]+\\.[a-zA-Z]+@etu\\.uae\\.ac\\.ma$")){
                    JOptionPane.showMessageDialog(null,"verifiez le syntax de votre email professionnel(prenom.nom@etu.uae.ac.ma).");
                }else if(!emailPerso.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")){
                    JOptionPane.showMessageDialog(null,"verifiez le syntax de votre email personnel.");
                }
                else{
                    Contact contact = new Contact(id,nom,prenom,num1,adresse,emailPro,hommeRadioButton.isSelected() ? "Homme" : "Femme",num2, emailPerso);
                    ContactDAO contactData = new ContactDAO();
                    ContactImage img = new ContactImage(id, imageData);
                    ContactImageDAO dao = new ContactImageDAO();
                    try {
                        dao.update(img);
                        contactData.update(contact);
                        JOptionPane.showMessageDialog(null, "contact successfully updated");
                        nomField.setText("");
                        prenomField.setText("");
                        num1Field.setText("");
                        num2Field.setText("");
                        emailProField.setText("");
                        emailPersoField.setText("");
                        adresseField.setText("");
                        userPicL.setIcon(null);
                        imageData = null;
                    } catch (DataBaseException ex) {
                        JOptionPane.showMessageDialog(null, "Erreur lié à la connexion à la base de donnéee. merci de ressayer ulterieurement", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (ImageLoadingException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = nomField.getText();
                String prenom = prenomField.getText();
                String num1 = num1Field.getText();
                String num2 = num2Field.getText();
                String emailPro = emailProField.getText();
                String emailPerso = emailPersoField.getText();
                String adresse = adresseField.getText();
                int id = (Integer) table1.getValueAt(table1.getSelectedRow(), 0);
                //String genre ;   (hommeRadioButton.isSelected()) ? "Male" : "Female";

                if(nom.equals("") || prenom.equals("") || num1.equals("") || num2.equals("") || emailPro.equals("") || emailPerso.equals("") || adresse.equals("")){
                    JOptionPane.showMessageDialog(null, "Erreur de saisie, tout les cases sont obligatoires", "Error", JOptionPane.ERROR_MESSAGE);
                }else if(!hommeRadioButton.isSelected() && !femmeRadioButton.isSelected()){
                    JOptionPane.showMessageDialog(null, "Erreur de saisie, tout les cases sont obligatoires", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    Contact contact = new Contact(id,nom,prenom,num1,adresse,emailPro,hommeRadioButton.isSelected() ? "Homme" : "Femme",num2, emailPerso);
                    ContactDAO contactData = new ContactDAO();
                    ContactImage img = new ContactImage(id, imageData);
                    ContactImageDAO dao = new ContactImageDAO();
                    int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete "+(hommeRadioButton.isSelected()? "M. ":"Ms ")+nom+ " "+prenom);
                    if(choice == JOptionPane.YES_OPTION){
                        try {
                            dao.delete(img);
                            contactData.delete(contact);
                            JOptionPane.showMessageDialog(null, "contact successfully deleted");
                            nomField.setText("");
                            prenomField.setText("");
                            num1Field.setText("");
                            num2Field.setText("");
                            emailProField.setText("");
                            emailPersoField.setText("");
                            adresseField.setText("");
                            userPicL.setIcon(null);
                            imageData = null;
                        } catch (DataBaseException ex) {
                            JOptionPane.showMessageDialog(null, "Erreur lié à la connexion à la base de donnéee. merci de ressayer ulterieurement", "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (ImageLoadingException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }

        });


        DefaultTreeModel treeModel = (DefaultTreeModel) tree1.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();
        root.removeAllChildren();
        treeModel.reload();

        // Set the new name for the root node
        root.setUserObject("Groups");

        // Update the tree view
        treeModel.nodeChanged(root);




        display.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                List<Group> groups = new ArrayList<>();
                GroupesDAO dao = new GroupesDAO();
                try {
                    root.removeAllChildren();
                    treeModel.reload();
                    groups = dao.showAllGroups();
                } catch (DataBaseException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur lié à la connexion à la base de donnéee. merci de ressayer ulterieurement", "Error", JOptionPane.ERROR_MESSAGE);
                }
                for(Group group: groups){
                    // Create a new child node
                    DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(group.getId()+" "+group.getNom());
                    // Add the child node to the root node
                    treeModel.insertNodeInto(childNode, root, root.getChildCount());

                    // Update the tree view
                    treeModel.reload();

                    List<Contact> contacts = new ArrayList<>();
                    GroupesDAO dao2 = new GroupesDAO();
                    try {
                        String[] parts = childNode.getUserObject().toString().split(" ");
                        contacts = dao2.showGroupContacts(new Group(Integer.parseInt(parts[0]), parts[1]));
                    } catch (DataBaseException ex) {
                        JOptionPane.showMessageDialog(null, "Erreur lié à la connexion à la base de donnéee. merci de ressayer ulterieurement", "Error", JOptionPane.ERROR_MESSAGE);
                    }if(contacts!=null){

                        for(Contact contact: contacts){
                            // Create a new child node
                            DefaultMutableTreeNode grandchildNode = new DefaultMutableTreeNode(contact.getNom()+" "+contact.getPrenom());
                            // Add the child node to the root node
                            treeModel.insertNodeInto(grandchildNode, childNode, childNode.getChildCount());

                            // Update the tree view
                            treeModel.reload();

                        }
                    }
                }

            }


        });
        addGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("saisir le nom du nouveau group");
                if (name == null) {
                } else {
                    try {
                        GroupesDAO dao = new GroupesDAO();
                        boolean exist = dao.verifyGroupName(name);
                        if(exist){
                            JOptionPane.showMessageDialog(null, "nom du groupe exist deja", "Error", JOptionPane.ERROR_MESSAGE);
                        }else{
                            GroupesDAO dao2 = new GroupesDAO();
                            dao2.create(new Group(name));
                            JOptionPane.showMessageDialog(null, "groupe ajouté avec succes, cliquez sur la boutton 'display' pour voir votre nouveau groupe", "success", JOptionPane.INFORMATION_MESSAGE);

                        }

                    } catch (DataBaseException ex) {
                        JOptionPane.showMessageDialog(null, "Erreur lié à la connexion à la base de donnéee. merci de ressayer ulterieurement", "Error", JOptionPane.ERROR_MESSAGE);
                        throw new RuntimeException(ex);
                    }
                }
            }


        });

        // Create a JPopupMenu
        JPopupMenu popupMenu = new JPopupMenu();

        // Create menu items
        JMenuItem item1 = new JMenuItem("Delete");
        JMenuItem item2 = new JMenuItem("change name");

        // Add menu items to the popup menu
        popupMenu.add(item1);
        popupMenu.add(item2);

        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                TreePath selectedPath = tree1.getSelectionPath();
                if (selectedPath != null) {
                    Object selectedNode = selectedPath.getLastPathComponent();
                    System.out.println("Selected Node: " + selectedNode.toString());
                    GroupesDAO dao = new GroupesDAO();

                    try {
                        dao.delete(new Group(Integer.parseInt(selectedNode.toString().split(" ")[0]),selectedNode.toString().split(" ")[1]));
                        JOptionPane.showMessageDialog(null,"group deleted");
                    } catch (DataBaseException ex) {
                        JOptionPane.showMessageDialog(null, "Erreur lié à la connexion à la base de donnéee. merci de ressayer ulterieurement", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    // No selection made
                    JOptionPane.showMessageDialog(null,"No item selected");
                }
            }
        });

        item2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath selectedPath = tree1.getSelectionPath();


                String name = JOptionPane.showInputDialog("saisir le vouveau nom du group");
                if (name == null) {
                } else {
                    try {
                        GroupesDAO dao = new GroupesDAO();
                        boolean exist = dao.verifyGroupName(name);
                        if(exist){
                            JOptionPane.showMessageDialog(null, "ce nom exist deja", "Error", JOptionPane.ERROR_MESSAGE);
                        }else{
                            if(selectedPath!=null){
                            Object selectedNode = selectedPath.getLastPathComponent();
                            GroupesDAO dao2 = new GroupesDAO();
                            dao2.update(new Group(Integer.parseInt(selectedNode.toString().split(" ")[0]),selectedNode.toString().split(" ")[1]), name);
                            JOptionPane.showMessageDialog(null, "groupe modifié avec succes, cliquez sur la boutton 'display' pour voir votre nouveau groupe", "success", JOptionPane.INFORMATION_MESSAGE);
                            }else{
                                JOptionPane.showMessageDialog(null,"No item selected");
                            }

                        }

                    } catch (DataBaseException ex) {
                        JOptionPane.showMessageDialog(null, "Erreur lié à la connexion à la base de donnéee. merci de ressayer ulterieurement", "Error", JOptionPane.ERROR_MESSAGE);
                        throw new RuntimeException(ex);
                    }
                }
            }


        });


        tree1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    // Show the popup menu at the mouse's location
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        addPictureOptionalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser fileChooser = new JFileChooser();

                // Create a file filter for image files
                FileFilter imageFilter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png");

                // Set the file filter to the file chooser
                fileChooser.setFileFilter(imageFilter);

                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    // Get the selected file
                    File file = fileChooser.getSelectedFile();
                    // Resize the image and read the resized image contents into a byte array
                    imageData = resizeImageAndConvertToByteArray(file);
                    // Store the image in the database
                    //storeImageInDatabase(imageData);
                    //display the picture
                    File selectedFile = fileChooser.getSelectedFile();
                    ImageIcon img = new ImageIcon(selectedFile.getAbsolutePath());
                    // Get the original image from the ImageIcon
                    Image originalImage = img.getImage();
                    // Define the desired width and height
                    int newWidth = 60;
                    int newHeight = 60;
                    // Resize the image using the getScaledInstance() method
                    Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                    // Create a new ImageIcon with the resized image
                    ImageIcon resizedIcon = new ImageIcon(resizedImage);
                    userPicL.setIcon(resizedIcon);

                }
            }

            private byte[] resizeImageAndConvertToByteArray(File file) {
                try {
                    // Read the original image
                    BufferedImage originalImage = ImageIO.read(file);

                    // Define the desired image dimensions
                    int desiredWidth = 600;
                    int desiredHeight = 600;

                    // Create a new image with the desired dimensions
                    BufferedImage resizedImage = new BufferedImage(desiredWidth, desiredHeight, BufferedImage.TYPE_INT_RGB);
                    Graphics2D g2d = resizedImage.createGraphics();

                    // Perform the resizing operation
                    g2d.drawImage(originalImage, 0, 0, desiredWidth, desiredHeight, null);
                    g2d.dispose();

                    // Convert the resized image to a byte array
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(resizedImage, "jpg", baos);
                    return baos.toByteArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

//    private void storeImageInDatabase(byte[] imageData) {
//
//        String sql = "INSERT INTO images (id_image, image_data) VALUES (?,?)";
//        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setBytes(1, imageData);
//            statement.executeUpdate();
//            System.out.println("Image stored in the database.");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }





    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Interface gui = new Interface();

                gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gui.setContentPane(gui.MainTab);
                gui.setVisible(true);
                gui.setSize(650,700);



            }
        });
    }
}
