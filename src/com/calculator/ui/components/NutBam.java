package com.calculator.components;

import com.calculator.constants.MauSac;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

/**
 * Lớp tùy chỉnh nút bấm với hiệu ứng và giao diện hiện đại
 * Kế thừa từ JButton và thêm các tính năng tùy chỉnh
 */
public class NutBam extends JButton {
    private Color mauNen;
    private Color mauChu;
    private Timer boHenGio;
    private float tienTrinhHoatHoa;
    private boolean dangHoatHoa;
    
    /**
     * Constructor khởi tạo nút bấm
     * @param text Văn bản hiển thị trên nút
     * @param mauNen Màu nền của nút
     * @param mauChu Màu chữ của nút
     */
    public NutBam(String text, Color mauNen, Color mauChu) {
        super(text);
        this.mauNen = mauNen;
        this.mauChu = mauChu;
        
        caiDatGiaoDien();
        caiDatHoatHoa();
    }
    
    /**
     * Thiết lập giao diện cơ bản cho nút
     */
    private void caiDatGiaoDien() {
        setFont(new Font("SansSerif", Font.BOLD, 24));
        setForeground(mauChu);
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        
        // Thêm hiệu ứng hover
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                dangHoatHoa = true;
                tienTrinhHoatHoa = 0;
                boHenGio.start();
            }
        });
    }
    
    /**
     * Thiết lập hiệu ứng hoạt họa khi nhấn nút
     */
    private void caiDatHoatHoa() {
        boHenGio = new Timer(10, e -> {
            tienTrinhHoatHoa += 0.1f;
            if (tienTrinhHoatHoa >= 1.0f) {
                tienTrinhHoatHoa = 0;
                dangHoatHoa = false;
                boHenGio.stop();
            }
            repaint();
        });
    }
    
    /**
     * Vẽ lại giao diện nút với hiệu ứng
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int banKinh = 20;
        
        // Vẽ hiệu ứng khi nhấn
        if (dangHoatHoa) {
            float tiLe = 0.95f + 0.05f * (1 - tienTrinhHoatHoa);
            int tamX = getWidth() / 2;
            int tamY = getHeight() / 2;
            int chieuRongMoi = (int)(getWidth() * tiLe);
            int chieuCaoMoi = (int)(getHeight() * tiLe);
            int x = tamX - chieuRongMoi / 2;
            int y = tamY - chieuCaoMoi / 2;
            
            g2.setColor(mauNen);
            g2.fillRoundRect(x, y, chieuRongMoi, chieuCaoMoi, banKinh, banKinh);
            
            // Hiệu ứng phát sáng
            Color mauHienThi = new Color(
                mauNen.getRed(), 
                mauNen.getGreen(), 
                mauNen.getBlue(), 
                50 + (int)(50 * (1 - tienTrinhHoatHoa))
            );
            g2.setColor(mauHienThi);
            g2.fillRoundRect(-5, -5, getWidth() + 10, getHeight() + 10, banKinh + 10, banKinh + 10);
        } else {
            g2.setColor(mauNen);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), banKinh, banKinh);
        }
        
        // Hiệu ứng gradient
        GradientPaint gradient = new GradientPaint(
            0, 0, new Color(255, 255, 255, 30),
            0, getHeight(), new Color(0, 0, 0, 30)
        );
        g2.setPaint(gradient);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), banKinh, banKinh);
        
        // Vẽ viền
        g2.setColor(new Color(255, 255, 255, 30));
        g2.setStroke(new BasicStroke(1f));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, banKinh, banKinh);
        
        // Vẽ chữ
        g2.setColor(mauChu);
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(getText())) / 2;
        int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(getText(), x, y);
        
        g2.dispose();
    }
}