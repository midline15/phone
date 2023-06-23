package ch3;

/*
 * 전화번호 관리 프로그램 구현 프로젝트
 * Version 0.3
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

class PhoneBookManager {
    final int MAX_CNT=100;
    PhoneInfo[] infoStorage = new PhoneInfo[MAX_CNT];
    int curCnt = 0;

    public void inputData() {
        System.out.println("데이터 입력을 시작합니다..");

        System.out.println("이름 : ");
        String name = MenuViewer.keyboard.nextLine();
        System.out.println("전화번호 : ");
        String phone = MenuViewer.keyboard.nextLine();
        System.out.println("생년월일 : ");
        String birth = MenuViewer.keyboard.nextLine();

        PhoneInfo info = new PhoneInfo(name, phone, birth);
        System.out.println("데이터 입력이 완료되었습니다. \n");
    }

    public void searchData() {
        System.out.println("데이터 검색을 시작합니다..");

        System.out.println("이름 : ");
        String name = MenuViewer.keyboard.nextLine();

        int dataIdx = search(name);
        if (dataIdx < 0) {
            System.out.println("해당하는 데이터가 존재하지 않습니다. \n");
        } else {
            infoStorage[dataIdx].showPhoneInfo();
            System.out.println("데이터 검색이 완료되었습니다. \n");
        }
    }

    public void deleteData() {
        System.out.println("데이터 삭제를 시작합니다..");

        System.out.println("이름 : ");
        String name = MenuViewer.keyboard.nextLine();

        int dataIdx = search(name);
        if (dataIdx < 0) {
            System.out.println("해당하는 데이터가 존재하지 않습니다. \n");
        } else {
            for (int idx = dataIdx; idx < curCnt - 1; idx++) {
                infoStorage[idx] = infoStorage[idx + 1];
            }
            curCnt--;
            System.out.println("데이터 삭제가 완료되었습니다. \n");
        }
    }

    private int search(String name) {
        for (int idx = 0; idx < curCnt; idx++) {
            PhoneInfo curInfo = infoStorage[idx];
            if (name.compareTo(curInfo.name) == 0) {
                return idx;
            }
        }
        return -1;
    }
}

class MenuViewer {
    public static Scanner keyboard = new Scanner(System.in);

    public static void showMenu() {
        System.out.println("선택하세요...");
        System.out.println("1. 데이터 입력");
        System.out.println("2. 데이터 검색");
        System.out.println("3. 데이터 삭제");
        System.out.println("4. 프로그램 종료");
        System.out.println("선택 : ");
    }
}

class PhoneBookVer03 {
    public static void main(String[] args) {
        PhoneBookManager manager = new PhoneBookManager();
        int choice;

        while (true) {
            MenuViewer.showMenu();
            choice = MenuViewer.keyboard.nextInt();
            MenuViewer.keyboard.nextLine();    // 아래에서 설명!

            switch (choice) {
                case 1:
                    manager.inputData();
                    break;
                case 2:
                    manager.searchData();
                    break;
                case 3:
                    manager.deleteData();
                    break;
                case 4:
                    System.out.println("프로그램을 종료합니다.");
                    return;
            }
        }
    }
}
