package com.calculator.utils;

import java.awt.*;
import javax.swing.border.Border;

/**
 * Lớp tiện ích chứa các phương thức vẽ giao diện đặc biệt
 * Xử lý việc vẽ các thành phần giao diện tùy chỉnh như viền bo tròn và biểu thức toán học
 */
public class TrinhVeMayTinh {
    /**
     * Tạo viền bo tròn cho các thành phần giao diện
     * @param banKinh Bán kính bo góc
     * @param mau Màu sắc của viền
     * @return Border tùy chỉnh với góc bo tròn
     */
    public static Border taoVienBoTron(int banKinh, Color mau) {
        return new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(mau);
                g2.drawRoundRect(x, y, width - 1, height - 1, banKinh, banKinh);
                g2.dispose();
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(1, 1, 1, 1);
            }

            @Override
            public boolean isBorderOpaque() {
                return false;
            }
        };
    }

    /**
     * Vẽ biểu thức căn bậc hai
     * Vẽ dấu căn và số bên trong với đường gạch ngang phía trên
     * @param g2 Đối tượng Graphics2D để vẽ
     * @param bieuThuc Biểu thức cần vẽ
     * @param x Tọa độ x
     * @param y Tọa độ y
     */
    public static void veCanBacHai(Graphics2D g2, String bieuThuc, int x, int y) {
        FontMetrics fm = g2.getFontMetrics();
        String[] phanTu = bieuThuc.split("√");
        if (phanTu.length > 1) {
            String so = phanTu[1];
            int doRongSo = fm.stringWidth(so);
            int doRongDauCan = fm.stringWidth("√");
            
            // Vẽ dấu căn
            g2.drawString("√", x - doRongSo - doRongDauCan, y);
            
            // Vẽ đường gạch ngang trên số
            int yDuongKe = y - fm.getAscent() + 5;
            g2.drawLine(x - doRongSo, yDuongKe, x, yDuongKe);
            
            // Vẽ số trong căn
            g2.drawString(so, x - doRongSo, y);
        }
    }

    /**
     * Vẽ biểu thức phân số
     * Vẽ tử số, mẫu số và đường phân số ở giữa
     * @param g2 Đối tượng Graphics2D để vẽ
     * @param bieuThuc Biểu thức cần vẽ
     * @param x Tọa độ x
     * @param y Tọa độ y
     */
    public static void vePhanSo(Graphics2D g2, String bieuThuc, int x, int y) {
        FontMetrics fm = g2.getFontMetrics();
        String[] phanTu = bieuThuc.split("⁄");
        if (phanTu.length == 2) {
            String tuSo = phanTu[0];
            String mauSo = phanTu[1];
            
            int doRongTuSo = fm.stringWidth(tuSo);
            int doRongMauSo = fm.stringWidth(mauSo);
            int doRongLonNhat = Math.max(doRongTuSo, doRongMauSo);
            
            // Vẽ tử số
            g2.drawString(tuSo, x - doRongLonNhat + (doRongLonNhat - doRongTuSo) / 2, y - fm.getHeight() / 2);
            
            // Vẽ đường phân số
            g2.drawLine(x - doRongLonNhat, y, x, y);
            
            // Vẽ mẫu số
            g2.drawString(mauSo, x - doRongLonNhat + (doRongLonNhat - doRongMauSo) / 2, y + fm.getHeight() / 2);
        }
    }

    /**
     * Vẽ số mũ theo đúng chuẩn toán học
     * @param g2 Đối tượng Graphics2D để vẽ
     * @param coSo Số cơ sở
     * @param soMu Số mũ
     * @param x Tọa độ x
     * @param y Tọa độ y
     */
    public static void veSoMu(Graphics2D g2, String coSo, String soMu, int x, int y) {
        // Lưu font và màu hiện tại
        Font fontGoc = g2.getFont();
        Color mauGoc = g2.getColor();
        
        // Vẽ số cơ sở
        FontMetrics fmCoSo = g2.getFontMetrics();
        int doRongCoSo = fmCoSo.stringWidth(coSo);
        g2.drawString(coSo, x - doRongCoSo, y);
        
        // Tạo font nhỏ hơn cho số mũ (khoảng 40% kích thước gốc)
        Font fontSoMu = fontGoc.deriveFont((float)(fontGoc.getSize() * 0.4));
        g2.setFont(fontSoMu);
        
        // Tính toán vị trí cho số mũ
        FontMetrics fmSoMu = g2.getFontMetrics();
        int doRongSoMu = fmSoMu.stringWidth(soMu);
        
        // Đặt số mũ cao hơn và ngay sau số cơ sở
        int xSoMu = x - doRongCoSo + (doRongCoSo/6);  // Dịch phải một chút so với số cơ sở
        int ySoMu = y - (int)(fmCoSo.getHeight() * 0.6);  // Dịch lên trên
        
        // Vẽ số mũ
        g2.drawString(soMu, xSoMu, ySoMu);
        
        // Khôi phục font và màu gốc
        g2.setFont(fontGoc);
        g2.setColor(mauGoc);
    }

    /**
     * Vẽ biểu thức toán học
     * Xác định loại biểu thức và gọi phương thức vẽ tương ứng
     * @param g2 Đối tượng Graphics2D để vẽ
     * @param bieuThuc Biểu thức cần vẽ
     * @param x Tọa độ x
     * @param y Tọa độ y
     */
    public static void veBieuThucToanHoc(Graphics2D g2, String bieuThuc, int x, int y) {
        // Bật các chế độ làm mịn
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
                           RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                           RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, 
                           RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, 
                           RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        // Xử lý các loại biểu thức khác nhau
        if (bieuThuc.contains("^")) {
            String[] phanTu = bieuThuc.split("\\^");
            if (phanTu.length == 2) {
                veSoMu(g2, phanTu[0], phanTu[1], x, y);
            } else {
                g2.drawString(bieuThuc, x - g2.getFontMetrics().stringWidth(bieuThuc), y);
            }
        } else if (bieuThuc.contains("√")) {
            veCanBacHai(g2, bieuThuc, x, y);
        } else if (bieuThuc.contains("⁄")) {
            vePhanSo(g2, bieuThuc, x, y);
        } else {
            g2.drawString(bieuThuc, x - g2.getFontMetrics().stringWidth(bieuThuc), y);
        }
    }
}