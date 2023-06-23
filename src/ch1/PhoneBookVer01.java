package ch1;

/*
 * 전화번호 관리 프로그램 구현 프로젝트
 * Version 0.1
 */

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

class PhoneBookVer01 {
    public static void main(String[] args) {
        PhoneInfo pInfo1 = new PhoneInfo("이정훈", "323-1111", "92,09,12");
        PhoneInfo pInfo2 = new PhoneInfo("김효준", "321-2222");
        pInfo1.showPhoneInfo();
        pInfo2.showPhoneInfo();
    }
}
