package model;

public class Singer {
    private int singerId;
    private String dateOfBirth;
    private String name;
    private String sex;

    public int getSingerId() {
        return singerId;
    }

    public void setSingerId(int singerId) {
        this.singerId = singerId;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return String.format("Singer(%s, %s, %s, %s)", getSingerId(), getDateOfBirth(), getName(), getSex());
    }
}
