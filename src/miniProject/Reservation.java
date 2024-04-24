package miniProject;

public class Reservation {
    int no;
    String user_id;
    String password;
    int m_no;
    String seat;


    public Reservation(int no, String user_id, String password, int m_no, String seat) {
        this.no = no;
        this.user_id = user_id;
        this.password = password;
        this.m_no = m_no;
        this.seat = seat;

    }

    public Reservation(String user_id, String password, int m_no, String seat) {
        this.user_id = user_id;
        this.password = password;
        this.m_no = m_no;
        this.seat = seat;
    }

    public Reservation(String user_id, String password) {
        this.user_id = user_id;
        this.password = password;
    }

    public Reservation(int m_no, String seat) {
        this.m_no = m_no;
        this.seat = seat;
    }

    public int getNo() {
        return no;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getM_no() {
        return m_no;
    }

    public void setM_no(int m_no) {
        this.m_no = m_no;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat_x(String seat) {
        this.seat = seat;
    }

}
