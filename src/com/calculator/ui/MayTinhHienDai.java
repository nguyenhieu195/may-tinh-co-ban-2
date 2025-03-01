package com.calculator;

import com.calculator.components.*;
import com.calculator.constants.*;
import com.calculator.utils.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

/**
 * Lớp chính của ứng dụng máy tính
 * Quản lý giao diện và xử lý tính toán
 */
public class MayTinhHienDai extends JFrame {
    // Các thành phần giao diện
    private ManHinh manHinh;
    private ThanhLichSu thanhLichSu;
    
    // Các biến quản lý trạng thái
    private StringBuilder duLieuNhap;     // Lưu số đang nhập
    private double ketQua;                // Lưu kết quả tính toán
    private String phepToanCuoi;         // Lưu phép toán đang thực hiện
    private boolean batDauNhapMoi;       // Đánh dấu bắt đầu nhập số mới
    private boolean dangNhapPhanSo;      // Đánh dấu đang nhập phân số
    private boolean dangNhapCanBacHai;   // Đánh dấu đang nhập căn bậc hai

    /**
     * Constructor khởi tạo máy tính
     */
    public MayTinhHienDai() {
        // Khởi tạo các biến trạng thái
        duLieuNhap = new StringBuilder();
        ketQua = 0;
        phepToanCuoi = "";
        batDauNhapMoi = true;

        caiDatCuaSo();
        khoiTaoThanhPhan();
    }

    /**
     * Thiết lập các thuộc tính của cửa sổ
     */
    private void caiDatCuaSo() {
        setTitle("Máy tính");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(380, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        // Tạo cửa sổ với góc bo tròn
        setShape(new RoundRectangle2D.Double(0, 0, 380, 700, 20, 20));
        getContentPane().setBackground(MauSac.NEN);
    }

    /**
     * Khởi tạo và bố trí các thành phần giao diện
     */
    private void khoiTaoThanhPhan() {
        JPanel panelChinh = new JPanel(new BorderLayout(15, 15));
        panelChinh.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelChinh.setBackground(MauSac.NEN);

        // Thêm thanh tiêu đề
        panelChinh.add(taoThanhTieuDe(), BorderLayout.NORTH);

        // Panel màn hình và lịch sử
        JPanel panelManHinhVaLichSu = new JPanel(new BorderLayout(10, 10));
        panelManHinhVaLichSu.setBackground(MauSac.NEN);

        manHinh = new ManHinh();
        thanhLichSu = new ThanhLichSu();

        panelManHinhVaLichSu.add(manHinh, BorderLayout.NORTH);
        panelManHinhVaLichSu.add(thanhLichSu, BorderLayout.CENTER);

        panelChinh.add(panelManHinhVaLichSu, BorderLayout.CENTER);

        // Thêm panel nút bấm
        panelChinh.add(taoPanelNut(), BorderLayout.SOUTH);

        add(panelChinh);
        choPhepKeoCuaSo();
    }

    /**
     * Tạo thanh tiêu đề với các nút điều khiển
     */
    private JPanel taoThanhTieuDe() {
        JPanel thanhTieuDe = new JPanel(new BorderLayout());
        thanhTieuDe.setBackground(MauSac.NEN);
        thanhTieuDe.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));

        // Panel chứa các nút điều khiển
        JPanel nutDieuKhien = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        nutDieuKhien.setBackground(MauSac.NEN);

        // Tạo các nút điều khiển cửa sổ
        JButton nutDong = taoNutTron(new Color(255, 95, 87));
        JButton nutThuNho = taoNutTron(new Color(255, 189, 46));
        JButton nutPhongTo = taoNutTron(new Color(40, 200, 64));

        nutDong.addActionListener(e -> System.exit(0));
        nutThuNho.addActionListener(e -> setState(JFrame.ICONIFIED));

        nutDieuKhien.add(nutDong);
        nutDieuKhien.add(nutThuNho);
        nutDieuKhien.add(nutPhongTo);

        // Thêm tiêu đề
        JLabel nhanTieuDe = new JLabel("Máy tính", JLabel.CENTER);
        nhanTieuDe.setFont(new Font("SansSerif", Font.BOLD, 16));
        nhanTieuDe.setForeground(MauSac.CHU_SANG);

        thanhTieuDe.add(nutDieuKhien, BorderLayout.WEST);
        thanhTieuDe.add(nhanTieuDe, BorderLayout.CENTER);

        return thanhTieuDe;
    }

    /**
     * Tạo nút tròn cho thanh tiêu đề
     * @param mau Màu của nút
     */
    private JButton taoNutTron(Color mau) {
        JButton nut = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                  RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(mau);
                g2.fillOval(0, 0, getWidth(), getHeight());
                
                g2.setColor(new Color(0, 0, 0, 40));
                g2.setStroke(new BasicStroke(1f));
                g2.drawOval(0, 0, getWidth() - 1, getHeight() - 1);
                
                g2.dispose();
            }
        };
        nut.setPreferredSize(new Dimension(14, 14));
        nut.setBorderPainted(false);
        nut.setFocusPainted(false);
        nut.setContentAreaFilled(false);
        
        nut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                nut.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });
        
        return nut;
    }

    /**
     * Tạo panel chứa các nút bấm của máy tính
     */
    private JPanel taoPanelNut() {
        JPanel panelNut = new JPanel(new GridLayout(6, 4, 12, 12));
        panelNut.setBackground(MauSac.NEN);

        // Mảng định nghĩa bố cục các nút
        String[][] cacNut = {
            {KyHieu.XOA_TAT_CA, KyHieu.DAU_XOA, "xⁿ", KyHieu.DAU_CHIA},
            {"7", "8", "9", KyHieu.DAU_NHAN},
            {"4", "5", "6", KyHieu.DAU_TRU},
            {"1", "2", "3", KyHieu.DAU_CONG},
            {"0", KyHieu.DAU_CHAM, KyHieu.DAU_CAN, KyHieu.DAU_BANG},
            {KyHieu.DAU_PHAN_TRAM, KyHieu.DAU_MO_NGOAC, KyHieu.DAU_DONG_NGOAC, 
             KyHieu.DAU_PHAN_SO}
        };

        // Tạo các nút và thêm vào panel
        for (String[] hang : cacNut) {
            for (String nhan : hang) {
                Color mauNen;
                Color mauChu = MauSac.CHU_SANG;
                
                // Xác định màu nền cho từng loại nút
                if ((KyHieu.XOA_TAT_CA + KyHieu.DAU_XOA + "xⁿ" + KyHieu.DAU_CAN + 
                     KyHieu.DAU_MO_NGOAC + KyHieu.DAU_DONG_NGOAC + 
                     KyHieu.DAU_PHAN_SO + KyHieu.DAU_PHAN_TRAM).contains(nhan)) {
                    mauNen = MauSac.NUT_CHUC_NANG;
                } else if ((KyHieu.DAU_CHIA + KyHieu.DAU_NHAN + KyHieu.DAU_TRU + 
                          KyHieu.DAU_CONG + KyHieu.DAU_BANG).contains(nhan)) {
                    mauNen = MauSac.NUT_PHEP_TOAN;
                } else if (Character.isDigit(nhan.charAt(0)) || 
                         nhan.equals(KyHieu.DAU_CHAM)) {
                    mauNen = MauSac.NUT_SO;
                } else {
                    mauNen = MauSac.NUT_TOI;
                }

                if (nhan.equals(KyHieu.DAU_BANG)) {
                    mauNen = MauSac.NUT_BANG;
                }

                NutBam nut = new NutBam(nhan, mauNen, mauChu);
                nut.addActionListener(e -> xuLyNhanNut(nhan));
                panelNut.add(nut);
            }
        }

        return panelNut;
    }

      /**
     * Xử lý sự kiện khi người dùng nhấn nút
     * @param giaTri Giá trị của nút được nhấn
     */
    private void xuLyNhanNut(String giaTri) {
        switch (giaTri) {
            case KyHieu.XOA_TAT_CA:
                xoaTatCa();
                break;
            case KyHieu.DAU_XOA:
                xoaKyTu();
                break;
            case KyHieu.DAU_BANG:
                tinhToan();
                break;
            case KyHieu.DAU_CHIA:
                xuLyPhepToan("/");
                break;
            case KyHieu.DAU_NHAN:
                xuLyPhepToan("*");
                break;
            case "xⁿ":
                xuLyPhepToan("^");
                break;
            case KyHieu.DAU_CAN:
                xuLyCanBacHai();
                break;
            case KyHieu.DAU_PHAN_SO:
                xuLyPhanSo();
                break;
            case KyHieu.DAU_CONG:
            case KyHieu.DAU_TRU:
            case KyHieu.DAU_PHAN_TRAM:
                xuLyPhepToan(giaTri);
                break;
            case KyHieu.DAU_CHAM:
                themDauCham();
                break;
            case KyHieu.DAU_MO_NGOAC:
            case KyHieu.DAU_DONG_NGOAC:
                themDauNgoac(giaTri);
                break;
            default:
                if (Character.isDigit(giaTri.charAt(0))) {
                    themSo(giaTri);
                }
                break;
        }
    }

    /**
     * Xóa tất cả và đặt lại trạng thái ban đầu
     */
    private void xoaTatCa() {
        duLieuNhap = new StringBuilder("0");
        ketQua = 0;
        phepToanCuoi = "";
        batDauNhapMoi = true;
        dangNhapPhanSo = false;
        dangNhapCanBacHai = false;
        manHinh.capNhatBieuThuc("");
        capNhatManHinh();
    }

    /**
     * Xóa ký tự cuối cùng của số đang nhập
     */
    private void xoaKyTu() {
        if (duLieuNhap.length() > 0) {
            duLieuNhap.deleteCharAt(duLieuNhap.length() - 1);
            if (duLieuNhap.length() == 0) {
                duLieuNhap.append("0");
                batDauNhapMoi = true;
            }
            capNhatManHinh();
            
            // Cập nhật biểu thức nếu đang trong phép tính
            if (!phepToanCuoi.isEmpty()) {
                String kyHieuPhepToan = chuyenDoiKyHieuPhepToan(phepToanCuoi);
                manHinh.capNhatBieuThuc(PhepTinhCoBan.dinhDangSo(ketQua) + 
                                      " " + kyHieuPhepToan + " " + duLieuNhap);
            }
        }
    }

    /**
     * Thêm số vào dữ liệu đang nhập
     * @param so Số cần thêm
     */
    private void themSo(String so) {
        if (batDauNhapMoi) {
            duLieuNhap = new StringBuilder();
            batDauNhapMoi = false;
        }
        duLieuNhap.append(so);
        capNhatManHinh();
        
        // Cập nhật biểu thức nếu đang trong phép tính
        if (!phepToanCuoi.isEmpty()) {
            String kyHieuPhepToan = chuyenDoiKyHieuPhepToan(phepToanCuoi);
            manHinh.capNhatBieuThuc(PhepTinhCoBan.dinhDangSo(ketQua) + 
                                  " " + kyHieuPhepToan + " " + duLieuNhap);
        }
    }

    /**
     * Thêm dấu chấm thập phân
     */
    private void themDauCham() {
        if (batDauNhapMoi) {
            duLieuNhap = new StringBuilder("0");
            batDauNhapMoi = false;
        }
        if (!duLieuNhap.toString().contains(".")) {
            duLieuNhap.append(".");
        }
        capNhatManHinh();
    }

    /**
     * Thêm dấu ngoặc vào biểu thức
     * @param dauNgoac Dấu ngoặc cần thêm
     */
    private void themDauNgoac(String dauNgoac) {
        if (batDauNhapMoi) {
            duLieuNhap = new StringBuilder();
            batDauNhapMoi = false;
        }
        duLieuNhap.append(dauNgoac);
        capNhatManHinh();
    }

    /**
     * Xử lý khi nhấn các nút phép toán
     * @param phepToan Phép toán được chọn
     */
    private void xuLyPhepToan(String phepToan) {
        if (duLieuNhap.length() > 0) {
            if (!phepToanCuoi.isEmpty()) {
                tinhToan();
            } else {
                try {
                    ketQua = Double.parseDouble(duLieuNhap.toString());
                } catch (NumberFormatException e) {
                    hienThiLoi("Dữ liệu không hợp lệ");
                    return;
                }
            }
        }
        phepToanCuoi = phepToan;
        batDauNhapMoi = true;
        
        String kyHieuPhepToan = chuyenDoiKyHieuPhepToan(phepToan);
        manHinh.capNhatBieuThuc(PhepTinhCoBan.dinhDangSo(ketQua) + 
                               " " + kyHieuPhepToan + " ");
    }

    /**
     * Xử lý khi nhấn nút căn bậc hai
     */
    private void xuLyCanBacHai() {
        if (!dangNhapCanBacHai) {
            dangNhapCanBacHai = true;
            duLieuNhap = new StringBuilder(KyHieu.DAU_CAN);
            batDauNhapMoi = false;
            capNhatManHinh();
        }
    }

    /**
     * Xử lý khi nhấn nút phân số
     */
    private void xuLyPhanSo() {
        if (!dangNhapPhanSo && !duLieuNhap.toString().contains(KyHieu.DAU_PHAN_SO)) {
            dangNhapPhanSo = true;
            if (duLieuNhap.length() == 0) {
                duLieuNhap.append("0");
            }
            duLieuNhap.append(KyHieu.DAU_PHAN_SO);
            capNhatManHinh();
        }
    }

    /**
     * Thực hiện tính toán khi nhấn nút bằng
     */
    private void tinhToan() {
        if (dangNhapPhanSo) {
            tinhPhanSo();
            return;
        }
        if (dangNhapCanBacHai) {
            tinhCanBacHai();
            return;
        }
        
        if (phepToanCuoi.isEmpty()) return;

        try {
            double giaTri = Double.parseDouble(duLieuNhap.toString());
            String bieuThuc = PhepTinhCoBan.dinhDangSo(ketQua);
            
            // Thực hiện phép tính tương ứng
            switch (phepToanCuoi) {
                case KyHieu.DAU_CONG: 
                    bieuThuc += " + " + PhepTinhCoBan.dinhDangSo(giaTri);
                    ketQua = PhepTinhCoBan.cong(ketQua, giaTri);
                    break;
                case KyHieu.DAU_TRU: 
                    bieuThuc += " - " + PhepTinhCoBan.dinhDangSo(giaTri);
                    ketQua = PhepTinhCoBan.tru(ketQua, giaTri);
                    break;
                case "*": 
                    bieuThuc += " × " + PhepTinhCoBan.dinhDangSo(giaTri);
                    ketQua = PhepTinhCoBan.nhan(ketQua, giaTri);
                    break;
                case "/":
                    if (giaTri == 0) throw new ArithmeticException("Không thể chia cho 0");
                    bieuThuc += " ÷ " + PhepTinhCoBan.dinhDangSo(giaTri);
                    ketQua = PhepTinhCoBan.chia(ketQua, giaTri);
                    break;
                case KyHieu.DAU_PHAN_TRAM: 
                    bieuThuc += " % " + PhepTinhCoBan.dinhDangSo(giaTri);
                    ketQua = PhepTinhCoBan.tinhPhanTram(ketQua, giaTri);
                    break;
                case "^": 
                    bieuThuc += "ⁿ" + PhepTinhCoBan.dinhDangSo(giaTri);
                    ketQua = PhepTinhCoBan.tinhLuyThua(ketQua, (int)giaTri);
                    break;
            }
            
            // Cập nhật kết quả và lịch sử
            bieuThuc += " = " + PhepTinhCoBan.dinhDangSo(ketQua);
            thanhLichSu.themLichSu(bieuThuc);
            manHinh.capNhatBieuThuc("");
            duLieuNhap = new StringBuilder(PhepTinhCoBan.dinhDangSo(ketQua));
            capNhatManHinh();
            phepToanCuoi = "";
            batDauNhapMoi = true;
            
        } catch (NumberFormatException e) {
            hienThiLoi("Dữ liệu không hợp lệ");
        } catch (ArithmeticException e) {
            hienThiLoi(e.getMessage());
        }
    }

    /**
     * Tính toán phân số
     */
    private void tinhPhanSo() {
        String duLieu = duLieuNhap.toString();
        String[] phanTu = duLieu.split(KyHieu.DAU_PHAN_SO);
        if (phanTu.length != 2) {
            hienThiLoi("Định dạng phân số không hợp lệ");
            return;
        }
        
        try {
            double tuSo = Double.parseDouble(phanTu[0]);
            double mauSo = Double.parseDouble(phanTu[1]);
            
            if (mauSo == 0) {
                throw new ArithmeticException("Không thể chia cho 0");
            }
            
            ketQua = PhepTinhCoBan.chia(tuSo, mauSo);
            String bieuThuc = phanTu[0] + KyHieu.DAU_PHAN_SO + phanTu[1] + 
                            " = " + PhepTinhCoBan.dinhDangSo(ketQua);
            thanhLichSu.themLichSu(bieuThuc);
            
            duLieuNhap = new StringBuilder(PhepTinhCoBan.dinhDangSo(ketQua));
            capNhatManHinh();
            dangNhapPhanSo = false;
            batDauNhapMoi = true;
            
        } catch (NumberFormatException e) {
            hienThiLoi("Số không hợp lệ trong phân số");
        } catch (ArithmeticException e) {
            hienThiLoi(e.getMessage());
        }
    }

    /**
     * Tính toán căn bậc hai
     */
    private void tinhCanBacHai() {
        String duLieu = duLieuNhap.toString().substring(1);
        try {
            double so = Double.parseDouble(duLieu);
            if (so < 0) {
                throw new ArithmeticException("Không thể tính căn bậc hai của số âm");
            }
            
            ketQua = PhepTinhCoBan.tinhCanBacHai(so);
            String bieuThuc = KyHieu.DAU_CAN + PhepTinhCoBan.dinhDangSo(so) + 
                            " = " + PhepTinhCoBan.dinhDangSo(ketQua);
            thanhLichSu.themLichSu(bieuThuc);
            
            duLieuNhap = new StringBuilder(PhepTinhCoBan.dinhDangSo(ketQua));
            capNhatManHinh();
            dangNhapCanBacHai = false;
            batDauNhapMoi = true;
            
        } catch (NumberFormatException e) {
            hienThiLoi("Số không hợp lệ cho căn bậc hai");
        } catch (ArithmeticException e) {
            hienThiLoi(e.getMessage());
        }
    }

    /**
     * Chuyển đổi ký hiệu phép toán từ dạng lưu trữ sang dạng hiển thị
     */
    private String chuyenDoiKyHieuPhepToan(String phepToan) {
        switch (phepToan) {
            case "*": return KyHieu.DAU_NHAN;
            case "/": return KyHieu.DAU_CHIA;
            case "^": return "ⁿ";
            default: return phepToan;
        }
    }

    /**
     * Cập nhật hiển thị trên màn hình
     */
    private void capNhatManHinh() {
        manHinh.capNhatHienThi(duLieuNhap.toString());
    }

    /**
     * Hiển thị hộp thoại lỗi
     * @param thongBao Thông báo lỗi cần hiển thị
     */
    private void hienThiLoi(String thongBao) {
        JDialog hopThoaiLoi = new JDialog(this, "Lỗi", true);
        hopThoaiLoi.setSize(300, 150);
        hopThoaiLoi.setLocationRelativeTo(this);
        hopThoaiLoi.setUndecorated(true);
        hopThoaiLoi.setShape(new RoundRectangle2D.Double(0, 0, 300, 150, 15, 15));
        
        JPanel panelLoi = new JPanel(new BorderLayout(10, 10));
        panelLoi.setBackground(MauSac.NEN);
        panelLoi.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel nhanLoi = new JLabel(thongBao, JLabel.CENTER);
        nhanLoi.setFont(new Font("SansSerif", Font.BOLD, 16));
        nhanLoi.setForeground(MauSac.CHU_SANG);
        
        JButton nutDongY = new JButton("OK");
        nutDongY.setFont(new Font("SansSerif", Font.BOLD, 14));
        nutDongY.setBackground(MauSac.NUT_BANG);
        nutDongY.setForeground(MauSac.CHU_SANG);
        nutDongY.setBorderPainted(false);
        nutDongY.setFocusPainted(false);
        nutDongY.addActionListener(e -> hopThoaiLoi.dispose());
        
        panelLoi.add(nhanLoi, BorderLayout.CENTER);
        panelLoi.add(nutDongY, BorderLayout.SOUTH);
        
        hopThoaiLoi.add(panelLoi);
        hopThoaiLoi.setVisible(true);
        
        xoaTatCa();
    }

    /**
     * Cho phép kéo cửa sổ bằng chuột
     */
    private void choPhepKeoCuaSo() {
        addMouseMotionListener(new MouseMotionAdapter() {
            private Point diemBatDau = new Point();

            @Override
            public void mouseDragged(MouseEvent e) {
                Point viTriHienTai = getLocation();
                setLocation(viTriHienTai.x + e.getX() - diemBatDau.x, 
                           viTriHienTai.y + e.getY() - diemBatDau.y);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                diemBatDau = e.getPoint();
            }
        });
    }

    /**
     * Phương thức main để khởi chạy ứng dụng
     */
    public static void main(String[] args) {
        try {
            // Cài đặt giao diện hệ thống cho macOS
            System.setProperty("apple.awt.application.appearance", "system");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            MayTinhHienDai mayTinh = new MayTinhHienDai();
            mayTinh.setVisible(true);
        });
    }
}