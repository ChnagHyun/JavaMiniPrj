package miniProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DbCon2 {
    final public static String DB_URL           = "jdbc:oracle:thin:@localhost:1521:xe";
    final public static String DB_DRIVER_NAME   = "oracle.jdbc.driver.OracleDriver";
    final public static String DB_USER          = "adam";
    final public static String DB_PW            = "1234";

    public static List<Movie> selectMovieInfo() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Movie> movieList = new ArrayList<>();  //배열을 이용해서 리스트를 구현

        try {
            Class.forName(DB_DRIVER_NAME);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
            System.out.println("DB 연결 성공");

            String sql ="SELECT * FROM MOVIE";      //DB에 있는 MOVIE를 읽어오는 것

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();      //행을 읽어 오는 것

            while (rs.next()){
                int m_no = rs.getInt("M_NO");
                String m_name = rs.getString("M_NAME");
                String regdate = rs.getString("REGDATE");

                movieList.add(
                        new Movie(m_no, m_name, regdate)
                );

            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if(rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return movieList;
    }

    public static int reserv(Reservation reservation) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = -1;

        try {
            Class.forName(DB_DRIVER_NAME);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
            System.out.println("DB 연결 성공");

            String user_id = reservation.getUser_id();
            String password = reservation.getPassword();
            int m_no = reservation.getM_no();
            String seat = reservation.getSeat();

            // 예약된 좌석인지 확인하는 쿼리
            String checkSql = "SELECT COUNT(*) FROM RESERVATION WHERE M_NO = ? AND SEAT = ?";
            pstmt = conn.prepareStatement(checkSql);
            pstmt.setInt(1, m_no);
            pstmt.setString(2, seat);
            rs = pstmt.executeQuery();

            // 좌석 예약 여부 확인
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    System.out.println("해당 좌석은 이미 예약되었습니다.");
                    return result; // 예약하지 않고 종료
                }
            }

            // 예약 수행
            String sql = "INSERT INTO RESERVATION(USER_ID, PASSWORD, M_NO, SEAT) " +
                    "(select M.USER_ID, M.PASSWORD, ?, ? " +
                    "from MEMBERSHIP M " +
                    "WHERE M.USER_ID = ? AND M.PASSWORD = ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, m_no);
            pstmt.setString(2, seat);
            pstmt.setString(3, user_id);
            pstmt.setString(4, password);

            result = pstmt.executeUpdate();

            if(result > 0) {
                System.out.println("예약 되었습니다.");
            }else {
                System.out.println("아이디 혹은 비밀번호를 다시 입력해주세요.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        System.out.println("예약이 되었습니다.");
        return result;
    }

    public static int cancel(Reservation reservation) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = -1;

        try {
            Class.forName(DB_DRIVER_NAME);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
            System.out.println("DB 연결 성공");

            String user_id = reservation.getUser_id();
            String password = reservation.getPassword();
            int m_no = reservation.getM_no();
            String seat = reservation.getSeat();

            String checkSql = "SELECT COUNT(*) FROM RESERVATION WHERE USER_ID =? AND PASSWORD =? AND M_NO =? AND SEAT =?";
            pstmt = conn.prepareStatement(checkSql);
            pstmt.setString(1, user_id);
            pstmt.setString(2, password);
            pstmt.setInt(3, m_no);
            pstmt.setString(4, seat);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                if (count == 0) {
                    System.out.println("예약된 좌석이 없습니다. 아이디 혹은 패스워드, 좌석을 다시 확인 해주세요.");
                    return result; // 예약된 좌석이 없으므로 취소하지 않고 종료
                }
            }

            String sql = "DELETE FROM RESERVATION WHERE USER_ID =? AND PASSWORD =? AND M_NO =? AND SEAT =?";     //직접 입력이 필요할경우 ?로 나타낸다.

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, reservation.getUser_id());
            pstmt.setString(2, reservation.getPassword());
            pstmt.setInt(3, reservation.getM_no());
            pstmt.setString(4, reservation.getSeat());

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
        } System.out.println("예약하신 좌석이 취소가 되었습니다.");
        return result;
    }

    public static List<Reservation> reservationConfirm(Reservation reservation) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Reservation> reservations = new ArrayList<>();

        try {
            Class.forName(DB_DRIVER_NAME);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
            System.out.println("DB 연결 성공");

            String sql = "SELECT * FROM RESERVATION WHERE USER_ID = ? AND PASSWORD = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, reservation.getUser_id());
            pstmt.setString(2, reservation.getPassword());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int m_no = rs.getInt("M_NO");
                String seat = rs.getString("SEAT");
                // 필요한 다른 정보들도 필요에 따라 가져올 수 있습니다.
                reservations.add(
                        new Reservation(m_no, seat));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return reservations;
    }
}
