package org.example.DAO.Exceptions;

public class DataBaseException extends  Exception{
    public DataBaseException(){
        super("Erreur base de données");
    }
    public DataBaseException(Throwable ex){
        super(ex);
    }
}
