package domain;

import java.io.Serializable;

public class Administrator implements Serializable {
    private String admin_id;
    private String password;

    public Administrator() {
        super();
    }

    public Administrator(String admin_id, String password) {
        super();
        this.admin_id = admin_id;
        this.password = password;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Administrator [admin_id=" + admin_id + ", password=" + password + "]";
    }
}
