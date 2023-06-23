package ch2;

/*
 * 전화번호 관리 프로그램 구현 프로젝트
 * Version 0.2
 */

import java.util.Scanner;

class PhoneInfo {
    String name;
    String phoneNumber;
    String birth;

    public PhoneInfo(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birth = null;
    }

    public PhoneInfo(String name, String phoneNumber, String birth) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
    }

    public void showPhoneInfo() {
        System.out.println("name : " + name);
        System.out.println("phone : " + phoneNumber);
        if (birth != null) {
            System.out.println("birth : " + birth);
        }
        System.out.println(""); // 출력되는 데이터의 구분을 위해
    }
}

class PhoneBookVer02 {
    static Scanner keyboard = new Scanner(System.in);

    public static void showMenu() {
        System.out.println("선택하세요...");
        System.out.println("1. 데이터 입력");
        System.out.println("2. 프로그램 종료");
        System.out.println("선택 : ");
    }

    public static void readData() {
        System.out.println("이름 : ");
        String name = keyboard.nextLine();
        System.out.println("전화번호 : ");
        String phone = keyboard.nextLine();
        System.out.println("생년월일 : ");
        String birth = keyboard.nextLine();

        PhoneInfo info = new PhoneInfo(name, phone, birth);
        System.out.println("\n입력된 정보 출력...");
        info.showPhoneInfo();
    }

    public static void main(String[] args) {
        int choice;
        while (true) {
            showMenu();
            choice = keyboard.nextInt();
            keyboard.nextLine();    // 아래에서 설명!

            switch (choice) {
                case 1:
                    readData();
                    break;
                case 2:
                    System.out.println("프로그램을 종료합니다.");
                    return;
            }
        }
    }
}
