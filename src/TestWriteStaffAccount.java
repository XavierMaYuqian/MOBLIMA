import Controller.DataManager;

import java.util.HashMap;

public class TestWriteStaffAccount {
    public static void main(String[] args) {
        HashMap<String, String> staffAccount = new HashMap<>();
        staffAccount.put("admin", "admin");
        DataManager.writeSerializedObject("res/data/staffAccount.dat", staffAccount);

    }
}
