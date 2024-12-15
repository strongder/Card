/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package card.connect;

import static card.common.Constants.AID;
import card.model.User;
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

        byte[] aid = {0x11, 0x22, 0x33, 0x44, 0x55, 0x00};
        CommandAPDU selectApplet = new CommandAPDU(0x00, 0xA4, 0x04, 0x00, aid);
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
        System.arraycopy(phoneBytes, 0, allData, 16 + 64, phoneBytes.length);
        System.arraycopy(dobBytes, 0, allData, 16 + 64 + 16, dobBytes.length);
        System.arraycopy(carNumberBytes, 0, allData, 16 + 64 + 16 + 16, carNumberBytes.length);

        CommandAPDU command = new CommandAPDU((byte) 0xB0, (byte) 0x01, (byte) 0x00, (byte) 0x00, allData);
        ResponseAPDU response = channel.transmit(command);

        if (response.getSW() != 0x9000) {
            return false;
        }
        return true;
    }

    public User readAllData() throws Exception {
        CommandAPDU command = new CommandAPDU((byte) 0xB0, (byte) 0x02, (byte) 0x00, (byte) 0x00);
        ResponseAPDU response = channel.transmit(command);

        if (response.getSW() != 0x9000) {
            throw new Exception("Lỗi khi đọc dữ liệu, mã phản hồi: " + Integer.toHexString(response.getSW()));
        }

        byte[] data = response.getData();

        String id = new String(data, 0, 16).trim();
        String name = new String(data, 16, 64).trim();
        String phone = new String(data, 16 + 64, 16).trim();
        String dob = new String(data, 16 + 64 + 16, 16).trim();
        String carNumber = new String(data, 16 + 64 + 16 + 16, 16).trim();

        User user = new User(id, name, phone, dob, carNumber);

        return user;
    }

    public int createPin(String pin) {
        byte[] pinBytes = pin.getBytes();
        CommandAPDU command = new CommandAPDU((byte) 0xB0, (byte) 0x03, (byte) 0x00, (byte) 0x00, pinBytes);

        try {
            ResponseAPDU response = channel.transmit(command);
            return response.getSW();
        } catch (CardException ex) {
            Logger.getLogger(SmartCard.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public int changePin(String pin)  {
        byte[] pinBytes = pin.getBytes();
        CommandAPDU command = new CommandAPDU((byte) 0xB0, (byte) 0x03, (byte) 0x00, (byte) 0x00, pinBytes);
        try {
            ResponseAPDU response = channel.transmit(command);
            return response.getSW();
        } catch (CardException ex) {
            Logger.getLogger(SmartCard.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public String readPin() throws Exception {
        CommandAPDU command = new CommandAPDU((byte) 0xB0, (byte) 0x04, (byte) 0x00, (byte) 0x00);
        ResponseAPDU response = channel.transmit(command);
        if (response.getSW() != 0x9000) {
            throw new Exception("Lỗi khi đọc dữ liệu, mã phản hồi: " + Integer.toHexString(response.getSW()));
        }

        byte[] data = response.getData();
        System.out.println(new String(data));
        return new String(data);
    }

    public int verifyPin(String pin) {
        byte[] pinBytes = pin.getBytes();
        CommandAPDU command = new CommandAPDU((byte) 0xB0, (byte) 0x05, (byte) 0x00, (byte) 0x00, pinBytes);
        try {
            ResponseAPDU response = channel.transmit(command);
            return response.getSW();
        } catch (CardException ex) {
            Logger.getLogger(SmartCard.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    
    public int topUpcard(Double money) {
        byte[] moneyBytes = String.valueOf(money).getBytes();
        CommandAPDU command = new CommandAPDU((byte) 0xB0, (byte) 0x03, (byte) 0x00, (byte) 0x00, moneyBytes);

        try {
            ResponseAPDU response = channel.transmit(command);
            return response.getSW();
        } catch (CardException ex) {
            Logger.getLogger(SmartCard.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    public String readMoney() {
        CommandAPDU command = new CommandAPDU((byte) 0xB0, (byte) 0x04, (byte) 0x00, (byte) 0x00);
        ResponseAPDU response;
        try {
            response = channel.transmit(command);
            byte[] data = response.getData();
             System.out.println(new String(data));
            return new String(data);
        } catch (CardException ex) {
            Logger.getLogger(SmartCard.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
