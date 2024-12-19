/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package card.view;

import card.connect.SmartCard;
import card.connect.UserDAO;
import card.model.User;
import card.model.Cache;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class PINPanel extends javax.swing.JFrame {

    /**
     * Creates new form PINPanel
     */
    public SmartCard smartCard;
    private int attemptsLeft;

    public PINPanel(SmartCard smartCard) {
        initComponents();
        this.smartCard = smartCard;
        getAttemptsLeft();
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_mapin = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btn_khoitao = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("MÃ PIN");

        jButton1.setText("Đăng Nhập");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("NHẬP MÃ PIN");

        btn_khoitao.setText("Khởi Tạo Thẻ");
        btn_khoitao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_khoitaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(25, 25, 25)
                                .addComponent(btn_khoitao))
                            .addComponent(txt_mapin, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addComponent(jLabel2)))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_mapin, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_khoitao, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            if (new String(txt_mapin.getPassword()).isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui Lòng Nhập Mã Pin");
            } else {
                System.out.println("check" + attemptsLeft);
                if (attemptsLeft < 0) {
                    JOptionPane.showMessageDialog(this, "Thẻ chưa có thông tin", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int verify = smartCard.verifyPin(new String(txt_mapin.getPassword()));
                if (verify == 0x9000) {
                    JOptionPane.showMessageDialog(this, "Đăng Nhập Thành Công");
                    setCacheInfo();
                    Home home = new Home(smartCard);
                    home.setVisible(true);
                    this.setVisible(false);

                }
                if (verify == 0x6984) {
                    JOptionPane.showMessageDialog(this, "Sai mã pin bạn còn lại " + (attemptsLeft - 1) + " lần nhập", "ERROR", JOptionPane.ERROR_MESSAGE);
                    attemptsLeft -= 1;
                    return;
                }
                if (verify == 0x6700) {
                    JOptionPane.showMessageDialog(this, "Mật khẩu từ 6 đến 10 ký tự. Còn " + (attemptsLeft - 1) + " lần nhập", "ERROR", JOptionPane.ERROR_MESSAGE);
                    attemptsLeft -= 1;
                    return;
                }
                if (verify == 0x6700) {
                    JOptionPane.showMessageDialog(this, "Mật khẩu từ 6 đến 10 ký tự. Còn " + (attemptsLeft - 1) + " lần nhập", "ERROR", JOptionPane.ERROR_MESSAGE);
                    attemptsLeft -= 1;
                    return;
                }
                if (verify == 0x6985) {
                    JOptionPane.showMessageDialog(this, "Thẻ của bạn đã bị khóa ", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(PINPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btn_khoitaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_khoitaoActionPerformed
        this.setVisible(false);
        CreateCard panel = new CreateCard(smartCard);
        panel.setVisible(true);
    }//GEN-LAST:event_btn_khoitaoActionPerformed
    public void getAttemptsLeft() {
        String tem = (smartCard.attemptsLeft());
        this.attemptsLeft = tem.isEmpty() ? -1 : Integer.parseInt(tem);
        //System.out.println(attemptsLeft);
    }

    public void setCacheInfo() {
        Cache.userCache = smartCard.readAllData();
        String moneyString = smartCard.readMoney();
        Long money = moneyString.isEmpty()? 0 : Long.valueOf(moneyString);
        Cache.userCache.setMoney(money);
        byte[] imageBytes = smartCard.readImage();
        Cache.userCache.setAvatar(imageBytes);
        byte[] publicKey = smartCard.receivePublicKey();
        Cache.userCache.setPublicKey(publicKey);
//         byte[] publicKey = UserDAO.gePublicKey(Cache.userCache.getId());
//        Cache.userCache.setPublicKey(publicKey);
    }
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_khoitao;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txt_mapin;
    // End of variables declaration//GEN-END:variables
}
