package service;

import util.db.DBConnect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class Widgets {
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[\\d]+$");
        return pattern.matcher(str).matches();
    }

    public static String getStudentIdByAccount(DBConnect dbConnect, String account) throws SQLException {
        String stringFormat = "SELECT id FROM user WHERE account=\'%s\'";
        String sql = String.format(stringFormat, account);
        ResultSet rs = dbConnect.executeQuery(sql);
        String studentId = "";

        while (rs.next()) {
            studentId = rs.getString(1);
            break;// should return only one record
        }

        return studentId;
    }

}
