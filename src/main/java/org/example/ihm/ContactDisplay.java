package org.example.ihm;

import org.example.DAO.Group_ContactDAO;
import org.example.DAO.Exceptions.DataBaseException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContactDisplay extends JFrame{
    private JButton deleteFromGroupButton;
    private JLabel idL;
    private JLabel nomL;
    private JLabel prenomL;

    public JLabel getIdL() {
        return idL;
    }

    private JLabel emailProL;
    private JLabel emailPersoL;
    private JLabel num1L;
    private JLabel num2L;
    private JLabel adressL;
    private JLabel genreL;
    private JPanel main;

    public JLabel getNomL() {
        return nomL;
    }

    public JLabel getPrenomL() {
        return prenomL;
    }

    public JLabel getEmailProL() {
        return emailProL;
    }

    public JLabel getEmailPersoL() {
        return emailPersoL;
    }

    public JLabel getNum1L() {
        return num1L;
    }

    public JLabel getNum2L() {
        return num2L;
    }

    public JLabel getAdressL() {
        return adressL;
    }

    public JLabel getGenreL() {
        return genreL;
    }

    private JPanel imageHolder;
    private JLabel image;

    public JLabel getImage() {
        return image;
    }

    public void setImage(JLabel image) {
        this.image = image;
    }

    private JButton addToGroupButton;

    ContactDisplay(){
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(main);
        setVisible(true);
        setSize(1100,470);
        setTitle("Contact information");
        ImageIcon logo = new ImageIcon("logo ENSAH.png");
        setIconImage(logo.getImage());

        image.setIcon(new ImageIcon("img.jpg"));

        addToGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String response = JOptionPane.showInputDialog("entrer l'identifiant du group (le nombre qui precede le nom du group dans l'arbre)");
                if (response == null) {}
                else if (response.matches("\\d+")) {
                    // The input contains only numbers
                    int contId = Integer.parseInt(idL.getText());
                    System.out.println(contId);
                    int groupId = Integer.parseInt(response);

                    Group_ContactDAO dao = new Group_ContactDAO();
                    try {
                        dao.add(contId,groupId);
                    } catch (DataBaseException ex) {
                        JOptionPane.showMessageDialog(null, "erreur, merci de verifier l'ID du group", "Error", JOptionPane.ERROR_MESSAGE);
                        throw new RuntimeException(ex);
                    }finally {
                        dispose();
                    }
                } else {
                    // The input contains other characters or is empty
                    JOptionPane.showMessageDialog(null,"Invalid input. Please enter only numbers.");
                }
            }

        });
        deleteFromGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String response = JOptionPane.showInputDialog("entrer l'identifiant du group (le nombre qui precede le nom du group dans l'arbre)");
                if (response == null) {}
                else if (response.matches("\\d+")) {
                    // The input contains only numbers
                    int contId = Integer.parseInt(idL.getText());
                    System.out.println(contId);
                    int groupId = Integer.parseInt(response);

                    Group_ContactDAO dao = new Group_ContactDAO();
                    try {
                        dao.deleteContactFromGroup(contId,groupId);
                    } catch (DataBaseException ex) {
                        JOptionPane.showMessageDialog(null, "erreur, merci de verifier l'ID du group", "Error", JOptionPane.ERROR_MESSAGE);
                        throw new RuntimeException(ex);
                    }finally {
                        dispose();
                    }
                } else {
                    // The input contains other characters or is empty
                    JOptionPane.showMessageDialog(null,"Invalid input. Please enter only numbers.");
                }
            }



        });
    }

    public static void main(String[] args) {
        ContactDisplay ok = new ContactDisplay();
    }
}
