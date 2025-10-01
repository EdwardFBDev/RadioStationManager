package radio.model;

import java.util.Objects;

/**
 * class Announcer 
    - String id
    - String fullName
    - String email
    - String phoneNumber
  
 * 
 * @author funes
 */
public class Announcer {
    private String id;
    private String fullName;
    private String email;
    private String phoneNumber;

    private static String req(String v, String field) {
        if (v == null || v.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " is required");
        }
        return v.trim();
    }

    /**
     * Crea un locutor con todos los datos obligatorios.
     */
    public Announcer(String id, String fullName, String email, String phoneNumber) {
        this.id = req(id, "id");
        this.fullName = req(fullName, "fullName");
        this.email = req(email, "email");
        this.phoneNumber = req(phoneNumber, "phoneNumber");
    }

    public String getId() { return id; }

    public String getFullName() { return fullName; }

    public void setFullName(String fullName) {
        this.fullName = req(fullName, "fullName");
    }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = req(email, "email");
    }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = req(phoneNumber, "phoneNumber");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Announcer)) return false;
        Announcer that = (Announcer) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() { return id + " - " + fullName; }
}
