package com.calculator.components;

import com.calculator.constants.MauSac;
import com.calculator.utils.TrinhVeMayTinh;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

/**
 * Lớp quản lý màn hình hiển thị của máy tính
 * Bao gồm trường hiển thị biểu thức và trường hiển thị kết quả
 */
public class ManHinh extends JPanel {
    private JTextField truongBieuThuc; // Hiển thị biểu thức đang tính
    private JTextField truongHienThi;  // Hiển thị số đang nhập hoặc kết quả
    
    /**
     * Constructor khởi tạo màn hình hiển thị
     */
    public ManHinh() {
        setLayout(new GridLayout(2, 1, 5, 5));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        khoiTaoTruongBieuThuc();
        khoiTaoTruongHienThi();
        
        add(truongBieuThuc);
        add(truongHienThi);
    }
    
    /**
     * Khởi tạo trường hiển thị biểu thức
     * Hiển thị các phép tính đang thực hiện
     */
    private void khoiTaoTruongBieuThuc() {
        truongBieuThuc = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
                                  RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setColor(MauSac.NEN_MAN_HINH);
                g2.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        
        truongBieuThuc.setFont(new Font("SansSerif", Font.PLAIN, 24));
        truongBieuThuc.setHorizontalAlignment(JTextField.RIGHT);
        truongBieuThuc.setEditable(false);
        truongBieuThuc.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        truongBieuThuc.setBackground(MauSac.NEN_MAN_HINH);
        truongBieuThuc.setForeground(MauSac.CHU_MO);
    }
    
    /**
     * Khởi tạo trường hiển thị số
     * Hiển thị số đang nhập hoặc kết quả tính toán
     */
    private void khoiTaoTruongHienThi() {
        truongHienThi = new JTextField("0") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
                                  RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setColor(MauSac.NEN_MAN_HINH);
                g2.fillRect(0, 0, getWidth(), getHeight());
                
                String text = getText();
                FontMetrics fm = g2.getFontMetrics(getFont());
                
                g2.setColor(getForeground());
                // Sử dụng TrinhVeMayTinh để vẽ biểu thức đặc biệt (căn, phân số)
                TrinhVeMayTinh.veBieuThucToanHoc(g2, text, 
                    getWidth() - 10, (getHeight() + fm.getAscent()) / 2);
                
                g2.dispose();
            }
        };
        
        truongHienThi.setFont(new Font("SansSerif", Font.BOLD, 48));
        truongHienThi.setHorizontalAlignment(JTextField.RIGHT);
        truongHienThi.setEditable(false);
        truongHienThi.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        truongHienThi.setBackground(MauSac.NEN_MAN_HINH);
        truongHienThi.setForeground(MauSac.CHU_SANG);
    }
    
    /**
     * Vẽ nền của màn hình với góc bo tròn
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                           RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(MauSac.NEN_MAN_HINH);
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        g2.dispose();
    }
    
    /**
     * Cập nhật biểu thức đang tính
     * @param bieuThuc Biểu thức cần hiển thị
     */
    public void capNhatBieuThuc(String bieuThuc) {
        truongBieuThuc.setText(bieuThuc);
    }
    
    /**
     * Cập nhật số đang hiển thị
     * @param giaTri Giá trị cần hiển thị
     */
    public void capNhatHienThi(String giaTri) {
        truongHienThi.setText(giaTri);
        truongHienThi.repaint();
    }
}