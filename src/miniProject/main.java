package miniProject;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean menu = true;
        while (menu) {
            System.out.println("------------");
            System.out.println("1. 회원가입");
            System.out.println("2. 예매");
            System.out.println("3. 취소");
            System.out.println("4. 좌석 확인");
            System.out.println("5. 종료");
            System.out.println("------------");

            int num = sc.nextInt();
            sc.nextLine();
            switch (num) {
                case 1:
                    user();
                    break;
                case 2:
                    reserve();
                    break;
                case 3:
                    cancel();
                    break;
                case 4:
                    confirm();
                    break;
                case 5:
                    menu = false;
                    System.out.println("시스템을 종료하였습니다.");
                    break;
                default:
                    System.out.println("다시 입력해주세요.");

            }
        }
    }
    public static void reserve() {
        Scanner sc = new Scanner(System.in);
        List<Movie> movieList = DbCon2.selectMovieInfo();
        for (Movie movie : movieList) {
            System.out.println("no: " + movie.getM_no() + ", 제목:" + movie.getM_name());
        }
        System.out.println("관람하실 영화를 선택하세요.");
        int m_no = sc.nextInt();
        sc.nextLine(); // 개행 문자 소비

        System.out.println("아이디를 입력하세요.");
        String user_id = sc.nextLine();
        System.out.println("비밀번호를 입력하세요.");
        String password = sc.nextLine();
        validation();
        System.out.println("좌석을 선택하세요. ex)A1");
        String seat = sc.nextLine();

        Reservation reservation = new Reservation(user_id, password, m_no, seat);
        DbCon2.reserv(reservation);
    }

    public static void user () {
        Scanner sc = new Scanner(System.in);
        System.out.println("생성 할 ID 를입력하세요. ");
        String user_id = sc.nextLine();
        System.out.println("생성 할 PASSWORD 를입력하세요. ");
        String password = sc.nextLine();
        System.out.println("이름을 입력하세요.");
        String name = sc.nextLine();
        System.out.println("이메일을 입력하세요.");
        String email = sc.nextLine();
        System.out.println("휴대폰 번호를 입력하세요.");
        String phone = sc.nextLine();

        MemberShip memberShip = new MemberShip(user_id, password, name, email, phone);
        DbCon.memberShip(memberShip);
    }

    public static void validation() {
        String[][] seats = {
                {"1", "2", "3", "4", "5", "6"},
                {"1", "2", "3", "4", "5", "6"},
                {"1", "2", "3", "4", "5", "6"},
                {"1", "2", "3", "4", "5", "6"},
                {"1", "2", "3", "4", "5", "6"}
        };
        System.out.println("\tSCREEN");
        for (int i = 0; i < seats.length; i++) {
            System.out.print((char)('A' + i) + ": ");
            for (int j = 0; j < seats[i].length; j++) {
                System.out.print(seats[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static void cancel() {
        Scanner sc = new Scanner(System.in);
        System.out.println("아이디를 입력하세요.");
        String user_id = sc.nextLine();
        System.out.println("비밀번호를 입력하세요.");
        String password = sc.nextLine();
        System.out.println("영화를 선택해주세요.");
        System.out.println("1. 범죄도시1 | 2. 범죄도시2 | 3. 범죄도시3");
        int m_no = sc.nextInt();
        sc.nextLine();
        System.out.println("취소할 좌석을 선택하세요.");
        String seat = sc.nextLine();

        Reservation reservation = new Reservation(user_id, password, m_no, seat);
        DbCon2.cancel(reservation);
    }

    public static void confirm() {
        Scanner sc = new Scanner(System.in);
        System.out.println("아이디를 입력하세요.");
        String user_id = sc.nextLine();
        System.out.println("비밀번호를 입력하세요.");
        String password = sc.nextLine();
        Reservation reservation = new Reservation(user_id, password);
        List<Reservation> reservationList = DbCon2.reservationConfirm(reservation);
        for (Reservation reservation1 : reservationList) {
            System.out.println("영화번호 :" + reservation1.getM_no() + " | 좌석 :"+reservation1.getSeat());
        }


    }
}

