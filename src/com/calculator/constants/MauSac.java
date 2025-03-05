package com.calculator.constants;

import java.awt.Color;

/**
 * Lớp chứa các hằng số màu sắc được sử dụng trong giao diện máy tính
 * Giúp duy trì tính nhất quán về màu sắc trong toàn bộ ứng dụng
 */
public class MauSac {
    // Màu nền
    /** Màu nền chính của ứng dụng */
    public static final Color NEN = new Color(17, 23, 41);  // Xanh đậm
    /** Màu nền của màn hình hiển thị */
    public static final Color NEN_MAN_HINH = new Color(22, 31, 50);  // Xanh tím nhạt
    /** Màu nền của phần lịch sử */
    public static final Color NEN_LICH_SU = new Color(30, 41, 59);  // Xanh tím
    
    // Màu nút
    /** Màu nút số */
    public static final Color NUT_SO = new Color(51, 65, 85);       // Xanh xám
    /** Màu nút phép toán */
    public static final Color NUT_PHEP_TOAN = new Color(147, 51, 234);  // Tím neon
    /** Màu nút chức năng */
    public static final Color NUT_CHUC_NANG = new Color(71, 85, 105);   // Xám xanh
    /** Màu nút bằng */
    public static final Color NUT_BANG = new Color(79, 70, 229);    // Tím xanh đậm
    /** Màu nút tối */
    public static final Color NUT_TOI = new Color(30, 41, 59);      // Xanh tím nhạt
    
    // Màu chữ
    /** Màu chữ sáng */
    public static final Color CHU_SANG = new Color(226, 232, 240);  // Trắng xanh
    /** Màu chữ mờ */
    public static final Color CHU_MO = new Color(148, 163, 184);    // Xám nhạt
    
    // Biến bổ sung để tương thích với code hiện tại
    public static final Color NEN_MANHINH = NEN_MAN_HINH;
    public static final Color NEN_LICHSU = NEN_LICH_SU;
    public static final Color NUT_TOANTU = NUT_PHEP_TOAN;
    public static final Color NUT_CHUCNANG = NUT_CHUC_NANG;
}