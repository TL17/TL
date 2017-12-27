package entity.service;
/**
 * <h1>Course Class</h1>
 * This class has the information about the courses.
 * The getters and setters can be called for necessary information.
 *
 * @version 1.0
 * @since 2017/12/06
 *
 * */
public class Course {
    private int courseID;
    private String courseName;
    private String courseInfo;
    private String coursePlan;
    private String teacherID;

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(String courseInfo) {
        this.courseInfo = courseInfo;
    }

    public String getCoursePlan() {
        return coursePlan;
    }

    public void setCoursePlan(String coursePlan) {
        this.coursePlan = coursePlan;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }
}
