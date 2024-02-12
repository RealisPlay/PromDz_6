package Буцкий_Максим_Викторович;

import java.util.*;
import java.util.stream.Collectors;
class PhoneRow {
    private String UserName;
    private ArrayList<Integer> PhoneNumber = new ArrayList<>();

    PhoneRow(String name, int num) {
        UserName = name;
        PhoneNumber.add(num);
    }

    public int getPhoneNumberCounter() {
        return this.getPhones().size();
    }

    public String toString() {
        return String.format("{%s:%s}", this.UserName, this.PhoneNumber.toString());
    }

    public String getName() {
        return this.UserName;
    }

    public ArrayList<Integer> getPhones() {
        return this.PhoneNumber;
    }
}
class PhoneBook {
    private int _ai = 0;
    private static HashMap<Integer, PhoneRow> phoneBook = new HashMap<>();
    public ArrayList<Integer> sortedKeys = new ArrayList<>();

    public void add(String unitName, Integer phoneNum) {
        Boolean[] isExists = { false };
        phoneBook.forEach((k,v) -> {
            String name = v.getName();
            ArrayList<Integer> phones = v.getPhones();
            if (name.equals(unitName)) {
                phones.add(phoneNum);
                isExists[0] = true;
            }
        });
        if (!isExists[0]) {
            phoneBook.put(++this._ai, new PhoneRow(unitName, phoneNum));
        }
        this.wasAdded(this._ai);
    }

    public static HashMap<Integer, PhoneRow> getPhoneBook() {
        return phoneBook;
    }

    public void wasAdded(int idx) {
        String str = new String(
                String.format("# Контакт %s id(%d) номер %d ",
                        phoneBook.get(idx).getName(),
                        idx,
                        phoneBook.get(idx).getPhoneNumberCounter()
                )
        );
        System.out.println(str);
    }
}

class Printer {
    public static void main(String[] args) {
        String name1;
        String name2;
        int phone1;
        int phone2;

        if (args.length == 0) {
            name1 = "Иванов";
            name2 = "Петров";
            phone1 = 789456123;
            phone2 = 321654987;
        } else {
            name1 = args[0];
            name2 = args[1];
            phone1 = Integer.parseInt(args[2]);
            phone2 = Integer.parseInt(args[3]);
        }

        PhoneBook myPhoneBook = new PhoneBook();

        myPhoneBook.add(name1, phone1);
        myPhoneBook.add(name1, phone2);
        myPhoneBook.add(name2, phone2);
        myPhoneBook.add(name2, phone1);
        myPhoneBook.add(name2, phone2);

        System.out.println();

        Map<Integer, PhoneRow> pb = PhoneBook.getPhoneBook();
        LinkedHashMap<Integer, PhoneRow> lhm = pb.entrySet().stream().sorted(
                (e1, e2) -> Integer.compare(
                        e2.getValue().getPhoneNumberCounter(),
                        e1.getValue().getPhoneNumberCounter()
                )
        ).collect(
                Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                )
        );

        for (var item : lhm.entrySet()) {
            System.out.println(item.toString());
        }
    }
}