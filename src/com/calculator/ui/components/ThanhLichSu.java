package com.calculator.components;

import com.calculator.constants.MauSac;
import com.calculator.utils.TrinhVeMayTinh;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp quản lý thanh lịch sử các phép tính
 * Hiển thị danh sách các phép tính đã thực hiện
 */
public class ThanhLichSu extends JPanel {
    private JTextArea vungLichSu;    // Vùng hiển thị lịch sử
    private List<String> lichSu;     // Danh sách các phép tính
    
    /**
     * Constructor khởi tạo thanh lịch sử
     */
    public ThanhLichSu() {
        setLayout(new BorderLayout());
        setBackground(MauSac.NEN);
        setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));
        
        lichSu = new ArrayList<>();
        khoiTaoGiaoDien();
    }
    
    /**
     * Khởi tạo giao diện của thanh lịch sử
     */
    private void khoiTaoGiaoDien() {
        // Tạo nhãn "Lịch sử"
        JLabel nhanLichSu = new JLabel("Lịch sử");
        nhanLichSu.setFont(new Font("SansSerif", Font.BOLD, 14));
        nhanLichSu.setForeground(MauSac.CHU_MO);
        nhanLichSu.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 0));
        
        // Khởi tạo vùng hiển thị lịch sử
        vungLichSu = new JTextArea();
        vungLichSu.setEditable(false);
        vungLichSu.setFont(new Font("SansSerif", Font.PLAIN, 14));
        vungLichSu.setBackground(MauSac.NEN_LICH_SU);
        vungLichSu.setForeground(MauSac.CHU_SANG);
        vungLichSu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        vungLichSu.setLineWrap(true);
        vungLichSu.setWrapStyleWord(true);
        
        // Tạo thanh cuộn cho vùng lịch sử
        JScrollPane thanhCuon = new JScrollPane(vungLichSu);
        thanhCuon.setPreferredSize(new Dimension(340, 120));
        thanhCuon.setBorder(TrinhVeMayTinh.taoVienBoTron(8, MauSac.NUT_TOI));
        thanhCuon.getVerticalScrollBar().setBackground(MauSac.NEN_LICH_SU);
        thanhCuon.getVerticalScrollBar().setForeground(new Color(120, 120, 120));
        
        add(nhanLichSu, BorderLayout.NORTH);
        add(thanhCuon, BorderLayout.CENTER);
    }
    
    /**
     * Thêm một phép tính vào lịch sử
     * @param phepTinh Phép tính cần thêm vào lịch sử
     */
    public void themLichSu(String phepTinh) {
        lichSu.add(phepTinh);
        capNhatVungLichSu();
    }
    
    /**
     * Cập nhật hiển thị vùng lịch sử
     */
    private void capNhatVungLichSu() {
        StringBuilder vanBanLichSu = new StringBuilder();
        for (String phepTinh : lichSu) {
            vanBanLichSu.append(phepTinh).append("\n\n");
        }
        vungLichSu.setText(vanBanLichSu.toString());
        // Cuộn xuống dòng cuối cùng
        vungLichSu.setCaretPosition(vungLichSu.getDocument().getLength());
    }
}