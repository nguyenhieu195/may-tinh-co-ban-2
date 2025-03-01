package com.calculator.utils;

/**
 * Lớp chứa các phương thức tính toán cơ bản
 * Thực hiện các phép tính số học mà không sử dụng các hàm có sẵn của Math
 */
public class PhepTinhCoBan {
    /**
     * Thực hiện phép cộng hai số
     * @param a Số hạng thứ nhất
     * @param b Số hạng thứ hai
     * @return Tổng của hai số
     */
    public static double cong(double a, double b) {
        return a + b;
    }

    /**
     * Thực hiện phép trừ hai số
     * @param a Số bị trừ
     * @param b Số trừ
     * @return Hiệu của hai số
     */
    public static double tru(double a, double b) {
        return a - b;
    }

    /**
     * Thực hiện phép nhân hai số
     * @param a Thừa số thứ nhất
     * @param b Thừa số thứ hai
     * @return Tích của hai số
     */
    public static double nhan(double a, double b) {
        return a * b;
    }

    /**
     * Thực hiện phép chia hai số
     * @param a Số bị chia
     * @param b Số chia
     * @return Thương của phép chia
     * @throws ArithmeticException nếu số chia bằng 0
     */
    public static double chia(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Không thể chia cho 0");
        }
        return a / b;
    }

    /**
     * Tính lũy thừa bằng phương pháp nhân lặp
     * @param coSo Cơ số của lũy thừa
     * @param soMu Số mũ của lũy thừa
     * @return Kết quả của phép lũy thừa
     */
    public static double tinhLuyThua(double coSo, int soMu) {
        if (soMu == 0) return 1;
        if (coSo == 0) return 0;
        if (soMu == 1) return coSo;
        
        boolean laSoAm = soMu < 0;
        int soMuDuong = Math.abs(soMu);
        
        double ketQua = 1;
        for (int i = 0; i < soMuDuong; i++) {
            ketQua = nhan(ketQua, coSo);
        }
        
        return laSoAm ? chia(1, ketQua) : ketQua;
    }

    /**
     * Tính căn bậc hai bằng phương pháp Newton
     * @param so Số cần tính căn bậc hai
     * @return Căn bậc hai của số
     * @throws ArithmeticException nếu số âm
     */
    public static double tinhCanBacHai(double so) {
        if (so < 0) {
            throw new ArithmeticException("Không thể tính căn bậc hai của số âm");
        }
        if (so == 0 || so == 1) return so;

        double doanTrai = 0;
        double doanPhai = so;
        double saiSo = 0.00001;

        while (tru(doanPhai, doanTrai) > saiSo) {
            double diemGiua = chia(cong(doanTrai, doanPhai), 2);
            double binhPhuong = nhan(diemGiua, diemGiua);
            
            if (binhPhuong > so) {
                doanPhai = diemGiua;
            } else {
                doanTrai = diemGiua;
            }
        }
        
        return chia(cong(doanTrai, doanPhai), 2);
    }

    /**
     * Tính phần trăm của một số
     * @param so Số cần tính phần trăm
     * @param phanTram Giá trị phần trăm
     * @return Kết quả tính phần trăm
     */
    public static double tinhPhanTram(double so, double phanTram) {
        return chia(nhan(so, phanTram), 100);
    }

    /**
     * Định dạng số để hiển thị
     * Số nguyên sẽ không hiển thị phần thập phân
     * Số thực sẽ được làm tròn đến 8 chữ số có nghĩa
     * @param so Số cần định dạng
     * @return Chuỗi biểu diễn số đã định dạng
     */
    public static String dinhDangSo(double so) {
        if (so == (long) so) {
            return String.format("%d", (long) so);
        }
        return String.format("%.8g", so);
    }
}