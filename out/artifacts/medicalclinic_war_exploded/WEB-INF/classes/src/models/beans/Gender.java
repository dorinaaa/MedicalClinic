package models.beans;

public class Gender {
    private int id;
    private String gender;

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGender(){
        return this.gender;
    }


}
