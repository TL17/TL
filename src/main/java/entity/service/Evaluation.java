package entity.service;
/**
 * <h1>Evaluation Class</h1>
 * This class has the information about the course evaluation.
 * The getters and setters can be called for necessary information.
 * Mainly user can give scores and comment by calling the methods provided in this class.
 *
 * @version 1.0
 * @since 2017/12/06
 *
 * */

public class Evaluation {
    private String score;
    private String comment;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
