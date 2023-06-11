package org.example.BLL;

import java.util.ArrayList;

public class Group {
    String nom;
    int id;
    ArrayList <Integer> contacts = new ArrayList<>();

    public Group(int id,String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Group(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<Integer> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Integer> contacts) {
        this.contacts = contacts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
