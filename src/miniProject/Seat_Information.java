package miniProject;

public class Seat_Information {
    int m_no;
    String seat;

    public Seat_Information(int m_no, String seat) {
        this.m_no = m_no;
        this.seat = seat;
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

    public void setSeat(String seat) {
        this.seat = seat;
    }
}
