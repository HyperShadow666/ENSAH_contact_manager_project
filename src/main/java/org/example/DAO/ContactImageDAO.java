package org.example.DAO;

import org.apache.log4j.Logger;
import org.example.BLL.ContactImage;
import org.example.DAO.BackEnd.DBConnection;
import org.example.DAO.Exceptions.DataBaseException;
import org.example.DAO.Exceptions.ImageLoadingException;

import java.sql.*;

public class ContactImageDAO {
    private Logger logger = Logger.getLogger(getClass());

    public void delete(ContactImage img) throws DataBaseException, ImageLoadingException {


        try {
            //Récupérer la connexion à la base de données
            Connection c = DBConnection.getInstance();

            //instruction SQl avec un paramètre
            String sqlDelete = "delete from contact_image where contact_id = ?";
            //créer l'objet PreparedStatement
            PreparedStatement stm = c.prepareStatement(sqlDelete);
            //définir la valeur du paramètre de la l'instruction SQL
            stm.setInt(1, img.getId());


            stm.executeUpdate();

        } catch (SQLException ex) {
            //tracer l'erreur
            logger.error("Erreur à cause de : ", ex);
            //remonter l'erreur
            throw new ImageLoadingException(ex);
        }
    }

    public void add(ContactImage img) throws DataBaseException, ImageLoadingException {
        try {
            //Récupérer la connexion à la base de données
            Connection c = DBConnection.getInstance();

            //instruction SQl avec un paramètre
            String sqlDelete = "insert into contact_image values (?,?)";
            //créer l'objet PreparedStatement
            PreparedStatement stm = c.prepareStatement(sqlDelete);
            //définir la valeur du paramètre de la l'instruction SQL
            stm.setInt(1, img.getId());
            stm.setBytes(2, img.getImg());


            stm.executeUpdate();

        } catch (SQLException ex) {
            //tracer l'erreur
            logger.error("Erreur à cause de : ", ex);
            //remonter l'erreur
            throw new ImageLoadingException(ex);

        }
    }

    public void update(ContactImage img) throws DataBaseException, ImageLoadingException {
        try {
            //Récupérer la connexion à la base de données
            Connection c = DBConnection.getInstance();

            String sqlDelete1 = "SELECT * from contact_image where contact_id = ?";
            PreparedStatement stm1 = c.prepareStatement(sqlDelete1);
            stm1.setInt(1, img.getId());
            ResultSet rs = stm1.executeQuery();
            if(rs.next()){
                //instruction SQl avec un paramètre
                String sqlDelete = "update contact_image set image = ? where contact_id = ?";
                //créer l'objet PreparedStatement
                PreparedStatement stm = c.prepareStatement(sqlDelete);
                //définir la valeur du paramètre de la l'instruction SQL
                stm.setBytes(1, img.getImg());
                stm.setInt(2, img.getId());

                stm.executeUpdate();
            }
            else{
                ContactImageDAO cidao = new ContactImageDAO();
                cidao.add(img);
            }



        } catch (SQLException ex) {
            //tracer l'erreur
            logger.error("Erreur à cause de : ", ex);
            //remonter l'erreur
            throw new ImageLoadingException(ex);

        }
    }

    public byte[] getImage(int id) throws DataBaseException, ImageLoadingException {
        byte[] img= null;
        try {
            //Récupérer la connexion à la base de données
            Connection c = DBConnection.getInstance();

            //instruction SQl avec un paramètre
            String sqlDelete = "select image from contact_image where contact_id = ?";
            //créer l'objet PreparedStatement
            PreparedStatement stm = c.prepareStatement(sqlDelete);
            //définir la valeur du paramètre de la l'instruction SQL
            stm.setInt(1, id);

            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                img = rs.getBytes("image");
            }

        } catch (SQLException ex) {
            //tracer l'erreur
            logger.error("Erreur à cause de : ", ex);
            //remonter l'erreur
            throw new ImageLoadingException(ex);

        }
        return img;

    }

}
