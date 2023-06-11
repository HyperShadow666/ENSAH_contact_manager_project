package org.example.DAO;

import org.apache.log4j.Logger;
import org.example.BLL.Contact;
import org.example.DAO.BackEnd.DBConnection;
import org.example.BLL.Group;
import org.example.DAO.Exceptions.DataBaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Group_ContactDAO {

    Connection c = null;
    PreparedStatement stm = null;
    private Logger logger = Logger.getLogger(getClass());


    public void add(Contact pContact, Group group) throws DataBaseException {

        try {
            //Récupérer la connexion à la base de données
            Connection c = DBConnection.getInstance();

            //instruction SQl avec un paramètre
            String sqlDelete = "insert into Group_Contact(contact_id, group_id) values(?,?)";
            //créer l'objet PreparedStatement
            stm = c.prepareStatement(sqlDelete);
            //définir la valeur du paramètre de la l'instruction SQL
            stm.setInt(1, pContact.getId());
            stm.setInt(2, group.getId());

            //Executer l'instruction SQL
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
    }public void add(int contId, int groupId) throws DataBaseException {

        try {
            //Récupérer la connexion à la base de données
            Connection c = DBConnection.getInstance();

            //instruction SQl avec un paramètre
            String sqlDelete = "insert into Group_Contact(contact_id, group_id) values(?,?)";
            //créer l'objet PreparedStatement
            stm = c.prepareStatement(sqlDelete);
            //définir la valeur du paramètre de la l'instruction SQL
            stm.setInt(1, contId);
            stm.setInt(2, groupId);

            //Executer l'instruction SQL
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


    public void delete(Contact pContact) throws DataBaseException {

        try {
            //Récupérer la connexion à la base de données
            Connection c = DBConnection.getInstance();

            //instruction SQl avec un paramètre
            String sqlDelete = "delete from Group_Contact where contact_id = ? ";
            //créer l'objet PreparedStatement
            stm = c.prepareStatement(sqlDelete);
            //définir la valeur du paramètre de la l'instruction SQL
            stm.setInt(1, pContact.getId());

            //Executer l'instruction SQL
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
    public void deleteContactFromGroup(int contId, int grpId) throws DataBaseException {

        try {
            //Récupérer la connexion à la base de données
            Connection c = DBConnection.getInstance();

            //instruction SQl avec un paramètre
            String sqlDelete = "delete from Group_Contact where contact_id = ? and group_id = ? ";
            //créer l'objet PreparedStatement
            stm = c.prepareStatement(sqlDelete);
            //définir la valeur du paramètre de la l'instruction SQL
            stm.setInt(1, contId);
            stm.setInt(2, grpId);

            //Executer l'instruction SQL
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
    public void delete(Group group) throws DataBaseException {

        try {
            //Récupérer la connexion à la base de données
            Connection c = DBConnection.getInstance();

            //instruction SQl avec un paramètre
            String sqlDelete = "delete from Group_Contact where group_id = ? ";
            //créer l'objet PreparedStatement
            stm = c.prepareStatement(sqlDelete);
            //définir la valeur du paramètre de la l'instruction SQL
            stm.setInt(1, group.getId());

            //Executer l'instruction SQL
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

}

