package co.mobiwise.indircom.model;

public class User {

    private String name;
    private String surname;
    private String auth_id;
    private String token;
    private String email;

    public User() {
        token = "";
    }

    public User(String name, String surname, String auth_id, String token, String email) {
        this.name = name;
        this.surname = surname;
        this.auth_id = auth_id;
        this.token = token;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAuth_id() {
        return auth_id;
    }

    public void setAuth_id(String auth_id) {
        this.auth_id = auth_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Name : " + name + "\n" +
                "Surname : " + surname + "\n" +
                "Auth ID : " + auth_id +
                "Token : " + token;
    }
}
