/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package card.common;

/**
 *
 * @author admin
 */
public class Constants {
    public static final byte[]AID=new byte[]{0x11,0x22,0x33,0x44,0x55,0x00};
    public static final int INS_VERIFY=0x00;
    public static final int INS_CREATE=0x01;
    public static final int INS_GET=0x02;
    public static final int INS_UPDATE=0x03;

    public static final int P1_PIN=0x00;
    public static final int P1_INFOR=0x01;
    public static final int P1_SIGNATURE=0x02;

    public static final int P2_INFOR=0x03;
    public static final int P2_IMAGE=0x04;
    public static final int P2_MONEY=0x05;
    public static final String CARD_BLOCK="Thẻ đã bị khóa!";
    
}
