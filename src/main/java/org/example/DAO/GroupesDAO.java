package org.example.DAO;

import org.apache.log4j.Logger;
import org.example.BLL.Contact;
import org.example.DAO.BackEnd.DBConnection;
import org.example.BLL.Group;
import org.example.DAO.Exceptions.DataBaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupesDAO {

    Connection c = null;
    PreparedStatement stm = null;
    private Logger logger = Logger.getLogger(getClass());


    public void delete(Group group) throws DataBaseException {

        try {
            //Récupérer la connexion à la base de données
            Connection c = DBConnection.getInstance();

            Group_ContactDAO gcdao = new Group_ContactDAO();
            gcdao.delete(group);

            //instruction SQl avec un paramètre
            String sqlDelete = "delete from groupes where id_group = ?";
            //créer l'objet PreparedStatement
            stm = c.prepareStatement(sqlDelete);
            //définir la valeur du paramètre de la l'instruction SQL
            stm.setInt(1, group.getId());

            stm.executeUpdate();
        } catch (SQLException ex) {
            //tracer l'erreur
            logger.error("Erreur à cause de : ", ex);
            //remonter l'erreur
            throw new DataBaseException(ex);

        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void update(Group group, String name) throws DataBaseException {

        int generatedId = -1;
        try {
            //Récupérer la connexion à la base de données
            Connection c = DBConnection.getInstance();


            String sqlDelete = "update groupes set group_name = ? where id_group = ?";

            stm = c.prepareStatement(sqlDelete);

            stm.setString(1, name);
            stm.setInt(2, group.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            //tracer l'erreur
            logger.error("Erreur à cause de : ", ex);
            //remonter l'erreur
            throw new DataBaseException(ex);

        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void create(Group group) throws DataBaseException {
        int generatedId = -1;


        try {
            //Récupérer la connexion à la base de données
            Connection c = DBConnection.getInstance();

            //instruction SQl avec un paramètre
            String sqlInsert = "insert into groupes(group_name) values(?)";
            //créer l'objet PreparedStatement
            stm = c.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            //définir la valeur du paramètre de la l'instruction SQL
            stm.setString(1, group.getNom());

            //Executer l'instruction SQL
            stm.executeUpdate();

            ResultSet generatedKeys = stm.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
                group.setId(generatedId);
            }
        } catch (SQLException ex) {
            //tracer l'erreur
            logger.error("Erreur à cause de : ", ex);
            //remonter l'erreur
            throw new DataBaseException(ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    public List<Contact> showGroupContacts(Group group) throws DataBaseException {
        int generatedId = -1;
        int id;
        String nom;
        String prenom;
        String telephone1;
        String adress;
        String emailPro;
        String genre;
        String telephone2;
        String emailPerso;
        List<Contact> list = new ArrayList<>();

        try {

            //Récupérer la connexion à la base de données
            Connection c = ContactDAO.DBManager.getInstance();


            stm = c.prepareStatement("SELECT contact_id from group_contact where group_id = ?");
            stm.setInt(1, group.getId());

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                PreparedStatement stm2 = c.prepareStatement("SELECT * from contact where id = ?");

                //generatedId = rs.getInt(1);
                stm2.setInt(1, rs.getInt(1));

                ResultSet rs2 = stm2.executeQuery();

                if (rs2.next()) {
                    // Retrieve values from the current row
                    id = rs2.getInt("id");
                    nom = rs2.getString("nom");
                    prenom = rs2.getString("prenom");
                    telephone1 = rs2.getString("telephone1");
                    adress = rs2.getString("adress");
                    emailPro = rs2.getString("emailPro");
                    genre = rs2.getString("genre");
                    telephone2 = rs2.getString("telephone2");
                    emailPerso = rs2.getString("emailPerso");

                    list.add(new Contact(id, nom, prenom, telephone1, adress, emailPro, genre, telephone2, emailPerso));
                }
                rs2.close();
                stm2.close();

            }

            rs.close();
        } catch (SQLException ex) {
            //tracer l'erreur
            logger.error("Erreur à cause de : ", ex);
            //remonter l'erreur
            throw new DataBaseException(ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        }

        if (list.isEmpty()) {
            return null;
        }

        return list;


    }

    public List<Group> showAllGroups() throws DataBaseException {
        int generatedId = -1;
        int id;
        String nom;

        List<Group> list = new ArrayList<>();

        try {

            //Récupérer la connexion à la base de données
            Connection c = ContactDAO.DBManager.getInstance();

            stm = c.prepareStatement("SELECT id_group, group_name from groupes");


            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                id = rs.getInt("id_group");
                nom = rs.getString("group_name");
                list.add(new Group(id, nom));

            }
            rs.close();
        } catch (SQLException ex) {
            //tracer l'erreur
            logger.error("Erreur à cause de : ", ex);
            //remonter l'erreur
            throw new DataBaseException(ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (list.isEmpty()) {
            return null;
        }

        return list;

    }
    public boolean verifyGroupName(String nom) throws DataBaseException {
        int id;

        List<Group> list = new ArrayList<>();

        try {

            //Récupérer la connexion à la base de données
            Connection c = ContactDAO.DBManager.getInstance();

            stm = c.prepareStatement("SELECT * from groupes where group_name = ?");

            stm.setString(1, nom);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {

                return true;

            }
            rs.close();
            return false;
        } catch (SQLException ex) {
            //tracer l'erreur
            logger.error("Erreur à cause de : ", ex);
            //remonter l'erreur
            throw new DataBaseException(ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}

