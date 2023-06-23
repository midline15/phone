package ch9;

/*
 * 전화번호 관리 프로그램 구현 프로젝트
 * Version 0.9
 */

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

interface INIT_MENU{
    int INPUT=1,EXIT=2;
}

interface INPUT_SELECT {
    int NORMAL=1, UNIV=2, COMPANY=3;
}

class MenuChoiceException extends Exception {
    int wrongChoice;

    public MenuChoiceException(int wrongChoice) {
        super("잘못된 선택이 이뤄졌습니다.");
        this.wrongChoice = wrongChoice;
    }

    public void showWrongChoice() {
        System.out.println(wrongChoice + "에 해당하는 선택은 존재하지 않습니다.");
    }
}

class PhoneInfo implements Serializable {
    String name;
    String phoneNumber;

    public PhoneInfo(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public void showPhoneInfo() {
        System.out.println("name : " + name);
        System.out.println("phone : " + phoneNumber);
    }

    @Override
    public String toString() {
        return "name : " + name + '\n' + "phoneNumber : " + phoneNumber + '\n';
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        PhoneInfo cmp = (PhoneInfo) obj;
        if (name.compareTo(cmp.name) == 0) {
            return true;
        } else {
            return false;
        }
    }
}

class PhoneUnivInfo extends PhoneInfo {
    String major;
    int year;

    public PhoneUnivInfo(String name, String phoneNumber, String major, int year) {
        super(name, phoneNumber);
        this.major = major;
        this.year = year;
    }

    @Override
    public void showPhoneInfo() {
        super.showPhoneInfo();
        System.out.println("major : " + major);
        System.out.println("year : " + year);
    }

    @Override
    public String toString() {
        return super.toString()
                + "major : " + major + '\n' + "year : " + year + '\n';
    }
}

class PhoneCompanyInfo extends PhoneInfo {
    String company;

    public PhoneCompanyInfo(String name, String phoneNumber, String company) {
        super(name, phoneNumber);
        this.company = company;
    }

    @Override
    public void showPhoneInfo() {
        super.showPhoneInfo();
        System.out.println("company : " + company);
    }

    @Override
    public String toString() {
        return super.toString()
                + "company : " + company + '\n';
    }
}
class PhoneBookManager {
    private final File dataFile = new File("PhoneBook.dat");
    HashSet<PhoneInfo> infoStorage = new HashSet<PhoneInfo>();
    static PhoneBookManager inst=null;

    public static PhoneBookManager createManagerInst() {
        if (inst == null) {
            inst = new PhoneBookManager();
        }
        return inst;
    }

    private PhoneBookManager() {
        readFromFile();
    }
    private PhoneInfo readFriendInfo() {
        System.out.print("이름 : ");
        String name = MenuViewer.keyboard.nextLine();
        System.out.print("전화번호 : ");
        String phone = MenuViewer.keyboard.nextLine();

        return new PhoneInfo(name, phone);
    }

    private PhoneInfo readUnivFriedInfo() {
        System.out.print("이름 : ");
        String name = MenuViewer.keyboard.nextLine();
        System.out.print("전화번호 : ");
        String phone = MenuViewer.keyboard.nextLine();
        System.out.print("전공 : ");
        String major = MenuViewer.keyboard.nextLine();
        System.out.print("학년 : ");
        int year = MenuViewer.keyboard.nextInt();
        MenuViewer.keyboard.nextLine();

        return new PhoneUnivInfo(name, phone, major, year);
    }

    private PhoneInfo readCompanyFriedInfo() {
        System.out.print("이름 : ");
        String name = MenuViewer.keyboard.nextLine();
        System.out.print("전화번호 : ");
        String phone = MenuViewer.keyboard.nextLine();
        System.out.print("회사 : ");
        String company = MenuViewer.keyboard.nextLine();

        return new PhoneCompanyInfo(name, phone, company);
    }

    public void inputData() throws MenuChoiceException {
        System.out.println("데이터 입력을 시작합니다..");
        System.out.println("1. 일반, 2. 대학, 3. 회사");
        System.out.print("선택>> ");
        int choice = MenuViewer.keyboard.nextInt();
        MenuViewer.keyboard.nextLine();
        PhoneInfo info = null;

        if (choice < INPUT_SELECT.NORMAL || choice > INPUT_SELECT.COMPANY) {
            throw new MenuChoiceException(choice);
        }

        switch (choice) {
            case INPUT_SELECT.NORMAL:
                info = readFriendInfo();
                break;
            case INPUT_SELECT.UNIV:
                info = readUnivFriedInfo();
                break;
            case INPUT_SELECT.COMPANY:
                info = readCompanyFriedInfo();
                break;
        }

        //infoStorage[curCnt++] = info;
        boolean isAdded = infoStorage.add(info);
        if (isAdded) {
            System.out.println("데이터 입력이 완료되었습니다. \n");
        } else {
            System.out.println("이미 저장된 데이터 입니다. \n");
        }
    }

    public String searchData(String name) {
        PhoneInfo info = search(name);
        if (info == null) {
            return null;
        } else {
            return info.toString();
        }
    }

    public String friendList() {
        StringBuilder sb = new StringBuilder("===친구 목록===\n");

        Iterator<PhoneInfo> itr = infoStorage.iterator();
        while (itr.hasNext()) {
            sb.append(itr.next().name+'\n');
        }
        sb.append("==============\n");
        return sb.toString();
    }

    public boolean deleteData(String name) {
        Iterator<PhoneInfo> itr = infoStorage.iterator();
        while (itr.hasNext()) {
            PhoneInfo curInfo = itr.next();
            if (name.compareTo(curInfo.name) == 0) {
                itr.remove();
                return true;
            }
        }

        return false;
    }

    private PhoneInfo search(String name) {
        Iterator<PhoneInfo> itr = infoStorage.iterator();
        while (itr.hasNext()) {
            PhoneInfo curInfo = itr.next();
            if (name.compareTo(curInfo.name) == 0) {
                return curInfo;
            }
        }
        return null;
    }

    public void storeToFile() {
        try {
            FileOutputStream file = new FileOutputStream(dataFile);
            ObjectOutput out = new ObjectOutputStream(file);

            Iterator<PhoneInfo> itr = infoStorage.iterator();
            while (itr.hasNext()) {
                out.writeObject(itr.next());
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromFile() {
        if (!dataFile.exists()) {
            return;
        }
        try {
            FileInputStream file = new FileInputStream(dataFile);
            ObjectInputStream in = new ObjectInputStream(file);

            while(true){
                PhoneInfo info = (PhoneInfo) in.readObject();
                if (info == null) {
                    break;
                }
                infoStorage.add(info);
            }
            in.close();
        } catch (IOException e) {
            return;
        } catch (ClassNotFoundException e) {
            return;
        }
    }
}

class MenuViewer {
    public static Scanner keyboard = new Scanner(System.in);

    public static void showMenu() {
        System.out.println("선택하세요...");
        System.out.println("1. 데이터 입력");
        System.out.println("2. 프로그램 종료");
        System.out.print("선택 : ");
    }
}

class SearchEventHandler implements ActionListener {
    JTextField searchField;
    JTextArea textArea;
    PhoneBookManager manager = PhoneBookManager.createManagerInst();

    public SearchEventHandler(JTextField searchField, JTextArea textArea) {
        this.searchField = searchField;
        textArea.append(manager.friendList()+'\n');
        this.textArea = textArea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = searchField.getText();
        String searchResult = manager.searchData(name);
        if (searchResult == null) {
            textArea.append("해당하는 데이터가 존재하지 않습니다. \n\n");
        } else {
            textArea.append("찾으시는 정보를 알려드립니다. \n");
            textArea.append(searchResult);
            textArea.append("\n");
        }
    }
}

class DeleteEventHandler implements ActionListener {
    JTextField deleteField;
    JTextArea textArea;

    public DeleteEventHandler(JTextField deleteField, JTextArea textArea) {
        this.deleteField = deleteField;
        this.textArea = textArea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = deleteField.getText();
        PhoneBookManager manager = PhoneBookManager.createManagerInst();
        Boolean isDeleted = manager.deleteData(name);
        if (isDeleted) {
            textArea.append("데이터 삭제를 완료하였습니다. \n");
        } else {
            textArea.append("해당하는 데이터가 존재하지 않습니다. \n");
        }
    }
}

class SearchDelFrame extends JFrame {
    JTextField searchField = new JTextField(15);
    JButton searchBtn = new JButton("SEARCH");

    JTextField deleteField = new JTextField(15);
    JButton deleteBtn = new JButton("DEL");

    JTextArea textArea = new JTextArea(20, 25);

    public SearchDelFrame(String title) {
        super(title);
        setBounds(100, 200, 330, 450);
        setLayout(new BorderLayout());
        Border border = BorderFactory.createEtchedBorder();

        Border searchBorder = BorderFactory.createTitledBorder(border, "Search");
        JPanel searchPanel = new JPanel();
        searchPanel.setBorder(searchBorder);
        searchPanel.setLayout(new FlowLayout());
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);

        Border deleteBorder = BorderFactory.createTitledBorder(border, "Delete");
        JPanel deletePanel = new JPanel();
        deletePanel.setBorder(deleteBorder);
        deletePanel.setLayout(new FlowLayout());
        deletePanel.add(deleteField);
        deletePanel.add(deleteBtn);

        JScrollPane scrollTextArea = new JScrollPane(textArea);
        Border textBorder = BorderFactory.createTitledBorder(border, "Information Board");
        scrollTextArea.setBorder(textBorder);

        add(searchPanel, BorderLayout.NORTH);
        add(deletePanel, BorderLayout.SOUTH);
        add(scrollTextArea, BorderLayout.CENTER);

        searchBtn.addActionListener(new SearchEventHandler(searchField, textArea));
        deleteBtn.addActionListener(new DeleteEventHandler(deleteField, textArea));

        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}

class PhoneBookVer09 {
    public static void main(String[] args) {
        PhoneBookManager manager = PhoneBookManager.createManagerInst();
        SearchDelFrame winFrame = new SearchDelFrame("PhoneBookVer09");
        int choice;

        while (true) {
            try {
                MenuViewer.showMenu();
                choice = MenuViewer.keyboard.nextInt();
                MenuViewer.keyboard.nextLine();

                if (choice < INIT_MENU.INPUT || choice > INIT_MENU.EXIT) {
                    throw new MenuChoiceException(choice);
                }

                switch (choice) {
                    case INIT_MENU.INPUT:
                        manager.inputData();
                        manager.storeToFile();
                        break;
                    case INIT_MENU.EXIT:
                        manager.storeToFile();
                        System.out.println("프로그램을 종료합니다.");
                        System.exit(0);
                        return;
                }
            } catch (MenuChoiceException e) {
                e.showWrongChoice();
                System.out.println("메뉴 선택을 처음부터 다시 진행합니다. \n");
            }
        }
    }
}
