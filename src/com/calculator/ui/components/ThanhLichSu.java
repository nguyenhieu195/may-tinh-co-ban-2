package com.calculator.ui.components;

import com.calculator.constants.MauSac;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class ThanhLichSu extends JPanel {
    private JTextArea historyArea;
    private List<String> history;
    private JButton clearButton;
    
    public ThanhLichSu() {
        history = new ArrayList<>();
        setLayout(new BorderLayout());
        setBackground(MauSac.NEN);
        setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));
        
        // Tạo panel chứa tiêu đề và nút clear
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setBackground(MauSac.NEN);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        
        // Label "Lịch sử"
        JLabel historyLabel = new JLabel("Lịch sử");
        historyLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        historyLabel.setForeground(MauSac.CHU_MO);
        
        // Tạo nút Clear
        clearButton = new JButton("Clear") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Vẽ background với góc bo tròn
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                // Vẽ border
                g2.setColor(MauSac.NUT_TOANTU);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                
                // Vẽ text
                FontMetrics fm = g2.getFontMetrics();
                g2.setColor(getForeground());
                g2.drawString(getText(), 
                    (getWidth() - fm.stringWidth(getText())) / 2,
                    (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                
                g2.dispose();
            }
        };
        clearButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        clearButton.setForeground(MauSac.CHU_SANG);
        clearButton.setBackground(MauSac.NUT_CHUCNANG);
        clearButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        clearButton.setFocusPainted(false);
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearButton.setPreferredSize(new Dimension(70, 25));
        
        // Thêm hiệu ứng hover
        clearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                clearButton.setBackground(MauSac.NUT_TOANTU);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                clearButton.setBackground(MauSac.NUT_CHUCNANG);
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                clearButton.setBackground(MauSac.NUT_BANG);
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                clearButton.setBackground(MauSac.NUT_CHUCNANG);
            }
        });
        
        // Thêm action xóa lịch sử
        clearButton.addActionListener(e -> {
            history.clear();
            updateHistoryArea();
        });
        
        // Thêm các component vào header panel
        headerPanel.add(historyLabel);
        headerPanel.add(Box.createHorizontalGlue()); // Tạo khoảng trống giữa label và button
        headerPanel.add(clearButton);
        
        // Tạo text area hiển thị lịch sử
        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        historyArea.setBackground(MauSac.NEN_LICHSU);
        historyArea.setForeground(MauSac.CHU_SANG);
        historyArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        historyArea.setLineWrap(true);
        historyArea.setWrapStyleWord(true);
        
        // Tạo scroll pane
        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setPreferredSize(new Dimension(340, 120));
        scrollPane.setBorder(createRoundedBorder(8, MauSac.NUT_CHUCNANG));
        scrollPane.getVerticalScrollBar().setBackground(MauSac.NEN_LICHSU);
        scrollPane.getVerticalScrollBar().setForeground(new Color(120, 120, 120));
        
        // Thêm các thành phần vào panel chính
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private Border createRoundedBorder(int radius, Color color) {
        return new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
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
    
    public void addToHistory(String calculation) {
        history.add(calculation);
        updateHistoryArea();
    }
    
    private void updateHistoryArea() {
        StringBuilder historyText = new StringBuilder();
        for (String calc : history) {
            historyText.append(calc).append("\n\n");
        }
        historyArea.setText(historyText.toString());
        historyArea.setCaretPosition(historyArea.getDocument().getLength());
    }
    
    public List<String> getHistory() {
        return history;
    }
    
    public void clearHistory() {
        history.clear();
        updateHistoryArea();
    }
}