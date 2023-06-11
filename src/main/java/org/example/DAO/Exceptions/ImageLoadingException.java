package org.example.DAO.Exceptions;

public class ImageLoadingException extends Exception{
    public ImageLoadingException(){
        super("Erreur de chargement de l'image");
    }
    public ImageLoadingException(Throwable ex){
        super(ex);
    }
}
