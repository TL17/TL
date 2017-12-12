package entity.service;

/**
 * <h1>Person Class</h1>
 * This class has the information about the Person(students and teachers).
 * The getters and setters can be called for necessary information.
 *
 * @version 1.0
 * @since 2017/12/06
 *
 * */
public class Person {
    private String name;
    private String info;
    private String contact;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
