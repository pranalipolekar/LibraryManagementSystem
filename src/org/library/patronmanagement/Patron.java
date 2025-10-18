package org.library.patronmanagement;

public class Patron {
    private String patronName;
    private String patronId;
    private String email;

    public Patron(String patronId, String patronName, String email) {
        this.patronId = patronId;
        this.patronName = patronName;
        this.email = email;
    }
    public String getPatronId() {
        return patronId;
    }
    public String getPatronName() {
        return patronName;
    }
    public void setPatronName(String patronName) {
        this.patronName = patronName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}
