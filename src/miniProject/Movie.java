package miniProject;

public class Movie {
        private int m_no;
        private String m_name;
        private String regdate;

        public Movie(int m_no, String m_name, String regdate) {
            this.m_no = m_no;
            this.m_name = m_name;
            this.regdate = regdate;
        }

        public Movie(String m_name) {
            this.m_name = m_name;
        }

        public int getM_no() {
            return m_no;
        }

        public void setM_no(int m_no) {
            this.m_no = m_no;
        }

        public String getM_name() {
            return m_name;
        }

        public void setM_name(String m_name) {
            this.m_name = m_name;
        }

        public String getRegdate() {
            return regdate;
        }

        public void setRegdate(String regdate) {
            this.regdate = regdate;
        }
    }

