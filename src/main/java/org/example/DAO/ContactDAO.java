package org.example.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.example.BLL.Contact;
import org.example.DAO.BackEnd.DBConnection;
import org.example.DAO.Exceptions.DataBaseException;


public class ContactDAO {

    Connection c = null;
    PreparedStatement stm = null;
    private Logger logger = Logger.getLogger(getClass());



    public void delete(Contact pContact) throws DataBaseException {

        Group_ContactDAO grpdao = new Group_ContactDAO();
        grpdao.delete(pContact);

        try {
            //Récupérer la connexion à la base de données
            Connection c = DBConnection.getInstance();

            //instruction SQl avec un paramètre
            String sqlDelete = "delete from Contact where id = ?";
            //créer l'objet PreparedStatement
            stm = c.prepareStatement(sqlDelete);
            //définir la valeur du paramètre de la l'instruction SQL
            stm.setInt(1, pContact.getId());


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
    public void update(Contact pContact) throws DataBaseException {

        int generatedId = -1;
            try {
                //Récupérer la connexion à la base de données
                Connection c = DBConnection.getInstance();

                //instruction SQl avec un paramètre
                String sqlDelete = "update contact set nom = ? ,  prenom= ? , telephone1= ? , adress= ? , emailPro = ? , genre= ? , telephone2= ? , emailPerso= ? where id = ?";
                //créer l'objet PreparedStatement
                stm = c.prepareStatement(sqlDelete);
                //définir la valeur du paramètre de la l'instruction SQL
                stm.setString(1, pContact.getNom());
                stm.setString(2, pContact.getPrenom());
                stm.setString(3, pContact.getTelephone1());
                stm.setString(4, pContact.getAdress());
                stm.setString(5, pContact.getEmailPro());
                stm.setString(6, pContact.getGenre());
                stm.setString(7, pContact.getTelephone2());
                stm.setString(8, pContact.getEmailPerso());
                stm.setInt(9,pContact.getId());

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

    public List<Contact> rechercherContactParNom(String pNom) throws  DataBaseException {
        List<Contact> list = new ArrayList<>();

        try {

            //Récupérer la connexion à la base de données
            Connection c = DBManager.getInstance();


            stm = c.prepareStatement("SELECT * FROM Contact WHERE upper(nom) LIKE ? OR upper(prenom) LIKE ?");
            stm.setString(1, "%" + pNom.toUpperCase() + "%");
            stm.setString(2, "%" + pNom.toUpperCase() + "%");
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                list.add(resultToContact(rs));
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

        public List<Contact> rechercherToutContact() throws  DataBaseException{
        List<Contact> list = new ArrayList<>();

        try {

            //Récupérer la connexion à la base de données
            Connection c = DBManager.getInstance();

            stm = c.prepareStatement("SELECT * from Contact order by nom");

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                list.add(resultToContact(rs));
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

    public List<Contact> rechercherContactParNum(String numero) throws  DataBaseException{
        List<Contact> list = new ArrayList<>();

        try {

            //Récupérer la connexion à la base de données
            Connection c = DBManager.getInstance();

            stm = c.prepareStatement("SELECT * from Contact where telephone1 like ? or telephone2 like ?");
            stm.setString(1,"%"+ numero +"%");
            stm.setString(2, "%"+ numero +"%");
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                list.add(resultToContact(rs));
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

    public void create(Contact pContact) throws DataBaseException{
        int generatedId = -1;
        try {
            //Récupérer la connexion à la base de données
            Connection c = DBConnection.getInstance();

            //instruction SQl avec un paramètre
            String sqlInsert = "insert into Contact(nom, prenom, telephone1, adress, emailPro, genre, telephone2, emailPerso) values(?,?,?,?,?,?,?,?)";
            //créer l'objet PreparedStatement
            stm = c.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            //définir la valeur du paramètre de la l'instruction SQL
            stm.setString(1, pContact.getNom());
            stm.setString(2, pContact.getPrenom());
            stm.setString(3, pContact.getTelephone1());
            stm.setString(4, pContact.getAdress());
            stm.setString(5, pContact.getEmailPro());
            stm.setString(6, pContact.getGenre());
            stm.setString(7, pContact.getTelephone2());
            stm.setString(8, pContact.getEmailPerso());

            //Executer l'instruction SQL
            stm.executeUpdate();

            ResultSet generatedKeys = stm.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
                pContact.setId(generatedId);
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

    private Contact resultToContact(ResultSet rs) throws SQLException {
        return new Contact(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("telephone1"), rs.getString("adress"), rs.getString("emailPro"), rs.getString("genre"), rs.getString("telephone2") , rs.getString("emailPerso"));
    }

    public static class DBManager {

        private Logger logger = Logger.getLogger(getClass());

        private static String dbUrl ;
        private static String login ;
        private static String password ;
        private static String driver;

        /** pour y stocker l'objet de connexion */
        private static Connection connection;

        /**
         * Constructor
         *
         * @throws DataBaseException
         */

        private DBManager() throws DataBaseException {

            try {
                // Lire le fichier de configuration conf.propeties
                //Properties dbProperties = DbPropertiesLoader.loadPoperties("conf.properties");
                dbUrl = "jdbc:mysql://localhost:3306/contacts";
                login = "root";
                password = "";
                driver = "com.mysql.cj.jdbc.Driver";

                // charger le pilote
                Class.forName(driver);

                // Créer une connexion à la base de données
                connection = DriverManager.getConnection(dbUrl, login, password);

            } catch (Exception ex) {
                // tracer cette erreur
                logger.error(ex);
                // raise the exception stack
                throw new DataBaseException(ex);
            }

        }

        /**
         * returns the unique instance of connection
         *
         * @return connection
         * @throws DataBaseException
         */
        public static Connection getInstance() throws DataBaseException {

            if (connection == null) {

                new DBManager();

            }

            return connection;
        }



//        public static void createTables() throws SQLException, DataBaseException {
//            Connection connection = null;
//            Statement statement = null;
//
//            try {
//                connection = DBManager.getInstance();
//                statement = connection.createStatement();
//
//                // Création de la table Player1
//                String createPlayer1TableSQL = "CREATE TABLE IF NOT EXISTS Player1 ("
//                        + "Id_Player1 INT PRIMARY KEY,"
//                        + "Scorep1 INT,"
//                        + "Timep1 VARCHAR(50),"
//                        + "PlayerType1 VARCHAR(10)"
//                        + ")";
//                statement.executeUpdate(createPlayer1TableSQL);
//
//                // Création de la table Player2
//                String createPlayer2TableSQL = "CREATE TABLE IF NOT EXISTS Player2 ("
//                        + "Id_Player2 INT PRIMARY KEY,"
//                        + "Scorep2 INT,"
//                        + "Timep2 VARCHAR(50),"
//                        + "PlayerType2 VARCHAR(10)"
//                        + ")";
//                statement.executeUpdate(createPlayer2TableSQL);
//                // Création de la table Board
//                String createBoardTableSQL = "CREATE TABLE IF NOT EXISTS Board ("
//                        + "Id_Board INT PRIMARY KEY,"
//                        + "IsFinished BOOLEAN,"
//                        + "MoveCount INT"
//                        + ")";
//                statement.executeUpdate(createBoardTableSQL);
//
//                // Création de la table Piece
//                String createPieceTableSQL = "CREATE TABLE IF NOT EXISTS Piece ("
//                        + "Id_Piece INT PRIMARY KEY,"
//                        + "Color VARCHAR(10),"
//                        + "X INT,"
//                        + "Y INT,"
//                        + "IsDead BOOLEAN,"
//                        + "Type VARCHAR(20)"
//                        + ")";
//                statement.executeUpdate(createPieceTableSQL);
//
//                System.out.println("Tables created successfully.");
//            } finally {
//                if (statement != null) {
//                    statement.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            }
//        }







        public static void main(String[] args) {
            try {
                Contact contact = new Contact(5, "Ahmed", "Mohsine", "0610838525", "adress", "chadi.mountassir@gmail.com", "male","0734746588","bad.mountassir@gmail.com");
                ContactDAO dao = new ContactDAO();
                //dao.create(contact);
                System.out.println(contact);
                //System.out.println(dao.rechercherContactParNom("Ahmed","Mohsine"));
                contact.setGenre("female");
                System.out.println(contact);
                dao.update(contact);

                //dao.delete(contact);
                // Fermez la connexion lorsque vous avez terminé

            } catch (DataBaseException ex) {
                ex.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
