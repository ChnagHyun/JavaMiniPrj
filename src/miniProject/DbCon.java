package miniProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbCon {

    final public static String DB_URL           = "jdbc:oracle:thin:@localhost:1521:xe";
    final public static String DB_DRIVER_NAME   = "oracle.jdbc.driver.OracleDriver";
    final public static String DB_USER          = "adam";
    final public static String DB_PW            = "1234";

    public static int memberShip(MemberShip memberShip){
        Connection conn         = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result              = -1;

        try{
            Class.forName(DB_DRIVER_NAME);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
            System.out.println("DB 연결 성공");

            String checkIdSql = "SELECT COUNT(*) FROM MEMBERSHIP WHERE USER_ID = ?";
            pstmt = conn.prepareStatement(checkIdSql);
            pstmt.setString(1, memberShip.getUser_id());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    // 이미 존재하는 아이디인 경우
                    System.out.println("이미 존재하는 아이디입니다. 다른 아이디를 선택해주세요.");
                    return result;
                }
            }

            String sql = "INSERT INTO MEMBERSHIP(USER_ID, PASSWORD, NAME, EMAIL, PHONENUMBER) " +
                    "VALUES (?, ?, ?, ?, ?) ";     //직접 입력이 필요할경우 ?로 나타낸다.

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberShip.getUser_id());
            pstmt.setString(2, memberShip.getPassword());
            pstmt.setString(3, memberShip.getName());
            pstmt.setString(4, memberShip.getEmail());
            pstmt.setString(5, memberShip.getPhoneNumber());

            result = pstmt.executeUpdate();

        }catch (Exception e) {
            e.printStackTrace();

        }finally {
            try{
                if(pstmt!=null) pstmt.close();
                if(conn!=null) conn.close();

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("회원가입이 완료 되었습니다.");
        return result;
    }
}