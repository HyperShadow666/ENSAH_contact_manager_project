package org.example.BLL;

public class Contact {

    //essential stuff
    private int id;
    private String nom;
    private String prenom;
    private String telephone1;
    private String adress;
    private String emailPro;
    private String genre;
    private String telephone2;
    private String emailPerso;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id; }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone1() {
        return telephone1;
    }

    public void setTelephone1(String telephone1) {
        this.telephone1 = telephone1;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getEmailPro() {
        return emailPro;
    }

    public void setEmailPro(String emailPro) {
        this.emailPro = emailPro;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTelephone2() {
        return telephone2;
    }

    public void setTelephone2(String telephone2) {
        this.telephone2 = telephone2;
    }

    public String getEmailPerso() {
        return emailPerso;
    }

    public void setEmailPerso(String emailPerso) {
        this.emailPerso = emailPerso;
    }

    public Contact( String nom, String prenom, String telephone1, String adress, String emailPro, String genre, String telephone2, String emailPerso) {

        this.nom = nom;
        this.prenom = prenom;
        this.telephone1 = telephone1;
        this.adress = adress;
        this.emailPro = emailPro;
        this.genre = genre;
        this.telephone2 = telephone2;
        this.emailPerso = emailPerso;
    }

    public Contact(int id, String nom, String prenom, String telephone1, String adress, String emailPro, String genre, String telephone2, String emailPerso) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone1 = telephone1;
        this.adress = adress;
        this.emailPro = emailPro;
        this.genre = genre;
        this.telephone2 = telephone2;
        this.emailPerso = emailPerso;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", telephone1='" + telephone1 + '\'' +
                ", adress='" + adress + '\'' +
                ", emailPro='" + emailPro + '\'' +
                ", genre='" + genre + '\'' +
                ", telephone2='" + telephone2 + '\'' +
                ", emailPerso='" + emailPerso + '\'' +
                '}'+"\n##############################\n";
    }
}