package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class input extends JFrame {

    public input(minesweeper minesweeper) {
        this.iMinesweeper = minesweeper;
        this.setSize(400, 100);
        this.setTitle("Boyut Seçimi");
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    }
    
  
    public void set(int n) {
        size = n;
        iMinesweeper.proceed(size);
    }

    public int get() {
        return size;
    }
    
    public void main(input Input) {
        inputEngine = new InputEngine(Input);

        size=0;

        panel = new JPanel();
        
        label = new JLabel("Mayın tarlası boyutunu giriniz : ");
        panel.add(label);
        
        text = new JTextField(10);
        text.addActionListener(inputEngine);
        panel.add(text);
        
        Input.setContentPane(panel);
        this.setVisible(true);
    }
    
    final private minesweeper iMinesweeper;  
    private InputEngine inputEngine;  

    private int size;  
    private JPanel panel;
    private JLabel label;
    private JTextField text;
}

class InputEngine implements ActionListener {
    input parent;
    
    InputEngine(input parent) {
        this.parent = parent;
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        Object eventSource = evt.getSource();
        JTextField text = (JTextField) eventSource;
        String input =  "0";
        int size = 5;
        
        while(true) {
            try {
                input = text.getText();
                size = Integer.parseInt(input);
                if (size<=6) {
                    JOptionPane.showMessageDialog(parent,
                            "6 üzeri değer giriniz.", "Başarısız!",
                            JOptionPane.ERROR_MESSAGE);
                    text.setText("");
                    break;
                } else {
                    parent.setVisible(false);
                    parent.set(size);
                }
                break;
            }
            catch (NumberFormatException | HeadlessException e) {
                JOptionPane.showMessageDialog(parent,
                        "Lütfen Tam Sayı Giriniz.", "Başarısız", 
                        JOptionPane.ERROR_MESSAGE);
                text.setText("");
                break;
            }
        }
    }
}

