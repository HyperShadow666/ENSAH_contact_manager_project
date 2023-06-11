package org.example.DAO.BackEnd;

import org.apache.log4j.Logger;
import org.example.DAO.Exceptions.DataBaseException;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

	private Logger logger = Logger.getLogger(getClass());


	private static String dbUrl;
	private static String login;
	private static String password;
	private static String driver;

	/** pour y stocker l'objet de connexion */
	private static Connection connection;

	/**
	 * Constructor
	 * 
	 * @throws DataBaseException
	 */

	private DBConnection() throws  DataBaseException {

		try{
			// Lire le fichier de configuration conf.propeties
			//Properties dbProperties = DbPropertiesLoader.loadPoperties("src/main/conf.properties");
			dbUrl = "jdbc:mysql://localhost:3306/contacts";
			login = "root";
			password = "";
			driver = "com.mysql.cj.jdbc.Driver";

			// charger le pilote
			Class.forName(driver);

			// Créer une connexion à la base de données
			connection = DriverManager.getConnection(dbUrl, login, password);

		}catch (Exception ex){
			//tracer cette erreur
			logger.error(ex);
			//raise the exception stack
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

			new DBConnection();

		}

		return connection;
	}

}
