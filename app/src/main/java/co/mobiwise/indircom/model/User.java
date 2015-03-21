package co.mobiwise.indircom.model;

/**
 * Created by mertsimsek on 20/03/15.
 */
public class User {

    private String name;
    private String surname;
    private int auth_id;

    public User(String name, String surname, int auth_id) {
        this.name = name;
        this.surname = surname;
        this.auth_id = auth_id;
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

    public int getAuth_id() {
        return auth_id;
    }

    public void setAuth_id(int auth_id) {
        this.auth_id = auth_id;
    }

    @Override
    public String toString() {
        return "Name : " + name + "\n" +
                "Surname : " + surname + "\n" +
                "Auth ID : " + auth_id ;
    }
}
