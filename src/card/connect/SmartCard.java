/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package card.connect;

import card.common.Constants;
import static card.common.Constants.AID;
import static card.common.Constants.CLA;
import static card.common.Constants.INS_ATTEMPTS_LEFT;
import static card.common.Constants.INS_CHANGE_MONEY;
import static card.common.Constants.INS_CHANGE_PIN;
import static card.common.Constants.INS_CREATE_INFO;
import static card.common.Constants.INS_CREATE_PIN;
import static card.common.Constants.INS_DISPLAY_INFO;
import static card.common.Constants.INS_DISPLAY_MONEY;
import static card.common.Constants.INS_VERIFY;
import static card.common.Constants.P1;
import static card.common.Constants.P2;
import card.model.User;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.smartcardio.*;

/**
 *
 * @author admin
 */
public class SmartCard {

    private Card card;
    private CardChannel channel;

    public boolean connectCard() throws Exception {
        TerminalFactory factory = TerminalFactory.getDefault();
        CardTerminals terminals = factory.terminals();
        CardTerminal terminal = terminals.list().get(0);

        card = terminal.connect("T=0");
        channel = card.getBasicChannel();

        CommandAPDU selectApplet = new CommandAPDU(0x00, 0xA4, 0x04, 0x00, AID);
        ResponseAPDU response = channel.transmit(selectApplet);
        return response.getSW() == 0x9000;
    }

    public void disconnectCard() {
        try {
            if (card != null) {
                card.disconnect(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean sendAllData(User user) throws Exception {
        byte[] idBytes = user.getId().getBytes();
        byte[] nameBytes = user.getFullName().getBytes();
        byte[] phoneBytes = user.getPhoneNumber().getBytes();
        byte[] dobBytes = user.getDateOfBirth().getBytes();
        byte[] carNumberBytes = user.getBienSo().getBytes();

        // Tạo mảng byte chứa toàn bộ dữ liệu
        byte[] allData = new byte[16 + 64 + 16 + 16 + 16];
        System.arraycopy(idBytes, 0, allData, 0, idBytes.length);
        System.arraycopy(nameBytes, 0, allData, 16, nameBytes.length);
        System.arraycopy(dobBytes, 0, allData, 16 + 64, dobBytes.length);
        System.arraycopy(phoneBytes, 0, allData, 16 + 64 + 16, phoneBytes.length);
        System.arraycopy(carNumberBytes, 0, allData, 16 + 64 + 16 + 16, carNumberBytes.length);

        CommandAPDU command = new CommandAPDU(CLA, INS_CREATE_INFO, P1, P2, allData);
        ResponseAPDU response = channel.transmit(command);

        if (response.getSW() != 0x9000) {
            return false;
        }
        return true;
    }

    public User readAllData() throws Exception {
        CommandAPDU command = new CommandAPDU(CLA, INS_DISPLAY_INFO, P1, P2);
        ResponseAPDU response = channel.transmit(command);

        if (response.getSW() != 0x9000) {
            throw new Exception("Lỗi khi đọc dữ liệu, mã phản hồi: " + Integer.toHexString(response.getSW()));
        }
        byte[] data = response.getData();

        String id = new String(data, 0, 16).trim();
        String name = new String(data, 16, 64).trim();
        String dob = new String(data, 16 + 64, 16).trim();
        String phone = new String(data, 16 + 64 + 16, 16).trim();
        String carNumber = new String(data, 16 + 64 + 16 + 16, 16).trim();

        User user = new User(id, name, dob, phone, carNumber);
        return user;
    }

    public int createPin(String pin) {
        byte[] pinBytes = pin.getBytes();
        CommandAPDU command = new CommandAPDU(CLA, INS_CREATE_PIN, P1, P2, pinBytes);

        try {
            ResponseAPDU response = channel.transmit(command);
            return response.getSW();
        } catch (CardException ex) {
            Logger.getLogger(SmartCard.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public int changePin(String pin) {
        byte[] pinBytes = pin.getBytes();
        CommandAPDU command = new CommandAPDU(CLA, INS_CHANGE_PIN, P1, P2, pinBytes);

        try {
            ResponseAPDU response = channel.transmit(command);
            return response.getSW();
        } catch (CardException ex) {
            Logger.getLogger(SmartCard.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public int verifyPin(String pin) {
        byte[] pinBytes = pin.getBytes();
        CommandAPDU command = new CommandAPDU(CLA, INS_VERIFY, P1, P2, pinBytes);

        try {
            ResponseAPDU response = channel.transmit(command);
            return response.getSW();
        } catch (CardException ex) {
            Logger.getLogger(SmartCard.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public int topUpcard(String money) {
        byte[] moneyBytes = money.getBytes();
        byte[] data = new byte[16];
        System.arraycopy(moneyBytes, 0, data, 0, moneyBytes.length);
        CommandAPDU command = new CommandAPDU(CLA, INS_CHANGE_MONEY, P1, P2, data);
        try {
            ResponseAPDU response = channel.transmit(command);
            return response.getSW();
        } catch (CardException ex) {
            Logger.getLogger(SmartCard.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public String readMoney() {
        CommandAPDU command = new CommandAPDU(CLA, INS_DISPLAY_MONEY, P1, P2);
        try {
            ResponseAPDU response = channel.transmit(command);
            byte[] data = response.getData();
            String money = new String(data, 0, 16).trim();
            System.out.println(money);
            return money;
        } catch (CardException ex) {
            Logger.getLogger(SmartCard.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public String attemptsLeft() {
        CommandAPDU command = new CommandAPDU(CLA, INS_ATTEMPTS_LEFT, P1, P2);
        try {

            ResponseAPDU response = channel.transmit(command);
            byte[] data = response.getData();
            byte attempts = data[0];  // Lấy giá trị số ở vị trí đầu tiên của mảng byte
            return String.valueOf(attempts);  // Trả về giá trị dưới dạng chuỗi

        } catch (CardException ex) {
            Logger.getLogger(SmartCard.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
