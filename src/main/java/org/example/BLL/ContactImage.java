package org.example.BLL;

public class ContactImage {
    private int id;
    private byte[] img;
    public ContactImage(int id, byte[] img){
        this.id = id;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public void setId(int id) {
        this.id = id;
    }
}
