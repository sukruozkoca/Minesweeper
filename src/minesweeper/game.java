package minesweeper;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.*;
import javax.imageio.ImageIO;

public class game extends JFrame {
    
    public game(int size, int toughness) {
        noOfMines = size*(1+toughness/2);
        this.setSize(size*MAGIC_SIZE*2 , size*MAGIC_SIZE + 150);
        this.setTitle("Mayın Tarlası");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    }

    private void setMines(int size) {
        Random rand = new Random();
        
        mineLand = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mineLand[i][j] = 0;
            }
        }

        int count = 0;
        int xPoint;
        int yPoint;
        while(count<noOfMines) {
            xPoint = rand.nextInt(size);
            yPoint = rand.nextInt(size);
            if (mineLand[xPoint][yPoint]!=-1) {
                mineLand[xPoint][yPoint]=-1;  
                count++;
            }
        }
        
    
        for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            if (mineLand[i][j]==-1) {
                    for (int k = -1; k <= 1 ; k++) {
                    for (int l = -1; l <= 1; l++) {
                        try {
                            if (mineLand[i+k][j+l]!=-1) {
                                mineLand[i+k][j+l] += 1;
                            }
                        }
                        catch (Exception e) {
                        }
                    }
                    }
            }
        }
        }
    }

    public void main(game frame, int size)
    {
        GameEngine gameEngine = new GameEngine(frame);
        MyMouseListener myMouseListener = new MyMouseListener(frame);
        JPanel mainPanel = new JPanel();

        panel1 = new JPanel();
        panel2 = new JPanel();

        this.noOfRevealed = 0;

        revealed = new boolean[size][size];
        flagged = new boolean[size][size];
        for (int i = 0; i < size; i++) 
        {
            for (int j = 0; j < size; j++)
            {
                revealed[i][j] = false;
                flagged[i][j] = false;
            }
        }


        try {
            smiley = ImageIO.read(getClass().getResource("images/gülen.png"));
            newSmiley = smiley.getScaledInstance(MAGIC_SIZE, MAGIC_SIZE, java.awt.Image.SCALE_SMOOTH);

            dead = ImageIO.read(getClass().getResource("images/üzgün.png"));
            newDead = dead.getScaledInstance(MAGIC_SIZE, MAGIC_SIZE, java.awt.Image.SCALE_SMOOTH);

            flag = ImageIO.read(getClass().getResource("images/turkbayragı.png"));
            newFlag = flag.getScaledInstance(MAGIC_SIZE, MAGIC_SIZE, java.awt.Image.SCALE_SMOOTH);

            mine = ImageIO.read(getClass().getResource("images/mayin.png"));
            newMine = mine.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        }
        catch (Exception e){
        }

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        BoxLayout g1 = new BoxLayout(panel1, BoxLayout.X_AXIS);
        panel1.setLayout(g1);

        JLabel jLabel1 = new JLabel(" Kırmızı bayrak = ");
        jLabel1.setAlignmentX(Component.LEFT_ALIGNMENT);
        jLabel1.setHorizontalAlignment(JLabel.LEFT);
        flagsLabel = new JLabel(""+this.noOfMines);

        smiling = true;
        smileButton = new JButton(new ImageIcon(newSmiley));
        smileButton.setPreferredSize(new Dimension(MAGIC_SIZE, MAGIC_SIZE));
        smileButton.setMaximumSize(new Dimension(MAGIC_SIZE, MAGIC_SIZE));
        smileButton.setBorderPainted(true);
        smileButton.setName("gülenyüz");
        smileButton.addActionListener(gameEngine);

        JLabel jLabel2 = new JLabel(" Süre :");
        timeLabel = new JLabel("0");
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        timeLabel.setHorizontalAlignment(JLabel.RIGHT);

        panel1.add(jLabel1);
        panel1.add(flagsLabel);
        panel1.add(Box.createRigidArea(new Dimension((size-1)*15 - 80,20)));
        panel1.add(smileButton, BorderLayout.PAGE_START);
        panel1.add(Box.createRigidArea(new Dimension((size-1)*15 - 85,50)));
        panel1.add(jLabel2);
        panel1.add(timeLabel);
        
        GridLayout g2 = new GridLayout(size, size);
        panel2.setLayout(g2);

        buttons = new JButton[size][size];

        for (int i=0; i<size; i++) {
            for (int j=0; j<size ; j++ ) {
                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(new Dimension(12, 12));
                buttons[i][j].setBorder(new LineBorder(Color.BLACK));
                buttons[i][j].setBorderPainted(true);
                buttons[i][j].setName(i + " " + j);
                buttons[i][j].addActionListener(gameEngine);
                buttons[i][j].addMouseListener(myMouseListener);
                panel2.add(buttons[i][j]);
            }
        }



        mainPanel.add(panel1);
        mainPanel.add(panel2);
        frame.setContentPane(mainPanel);
        this.setVisible(true);
        

        setMines(size);


        timeThread timer = new timeThread(this);
        timer.start();

    }


    public void timer() {
        String[] time = this.timeLabel.getText().split(" ");
        int time0 = Integer.parseInt(time[0]);
        ++time0;
        this.timeLabel.setText(Integer.toString(time0) + " s");
    }


    public void changeSmile() {
        if (smiling) {
            smiling=false;
            smileButton.setIcon(new ImageIcon(newDead));
        } else {
            smiling=true;
            smileButton.setIcon(new ImageIcon(newSmiley));
        }
    }


    public void buttonRightClicked(int x, int y) {
        if(!revealed[x][y]) {
            if (flagged[x][y]) {
                buttons[x][y].setIcon(null);
                flagged[x][y] = false;
                int old = Integer.parseInt(this.flagsLabel.getText());
                ++old;
                this.flagsLabel.setText(""+old);
            }
            else {
                if (Integer.parseInt(this.flagsLabel.getText())>0) {
                    buttons[x][y].setIcon(new ImageIcon(newFlag));
                    flagged[x][y] = true;
                    int old = Integer.parseInt(this.flagsLabel.getText());
                    --old;
                    this.flagsLabel.setText(""+old);
                }
            }
        }
    }

    private boolean gameWon() {
        return (this.noOfRevealed) ==
        (Math.pow(this.mineLand.length, 2) - this.noOfMines);
    }


    public void buttonClicked(int x, int y) {
        if(!revealed[x][y] && !flagged[x][y]) {
            revealed[x][y] = true;

            switch (mineLand[x][y]) {
                case -1:
                    try {
                        buttons[x][y].setIcon(new ImageIcon(newMine));
                    } catch (Exception e1) {
                    }
                    buttons[x][y].setBackground(Color.RED);
                    try {
                        smileButton.setIcon(new ImageIcon(newDead));
                    } catch (Exception e2) {
                    }

                    JOptionPane.showMessageDialog(this, "Hop Hocam Mayına Bastın !", "KAYBETTİN",
                            JOptionPane.ERROR_MESSAGE);


                    System.exit(0);

                    break;

                case 0:
                    buttons[x][y].setBackground(Color.lightGray);
                    ++this.noOfRevealed;

                    if (gameWon()) {
                        JOptionPane.showMessageDialog(rootPane,
                            "Başarılı Bir Şekilde Bitirdiniz. :)");

                        System.exit(0);
                    } 

                   
                    for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        try {
                            buttonClicked(x + i, y + j);
                        }
                        catch (Exception e3) {
                        }
                    }
                    }

                    break;

                default:
                    buttons[x][y].setText(Integer.toString(mineLand[x][y]));
                    buttons[x][y].setBackground(Color.LIGHT_GRAY);
                    ++this.noOfRevealed;
                    if (gameWon()) {
                        JOptionPane.showMessageDialog(rootPane, "Kazandınız !");

                        System.exit(0);
                    }

                    break;
            }
        }
        
    }

    private JButton[][] buttons;  
    private JPanel panel1;  
    private JPanel panel2;  
    private JLabel flagsLabel;  
    private JButton smileButton;  
    private JLabel timeLabel;  

    private int noOfMines = 0;  
    private int[][] mineLand;  
    private boolean[][] revealed;  
    private int noOfRevealed;  
    private boolean[][] flagged; 
    
    private Image smiley;
    private Image newSmiley;
    private Image flag;
    private Image newFlag;
    private Image mine;
    private Image newMine;
    private Image dead;
    private Image newDead;
    
    private boolean smiling; 

    public static final int MAGIC_SIZE = 30;

}

class GameEngine implements ActionListener {
    game parent;
    
    GameEngine(game parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object eventSource = e.getSource();
        JButton clickedButton = (JButton) eventSource;
        String name = clickedButton.getName();
        if (name.equals("smileButton")) {
            parent.changeSmile();
        }
        else {
            String[] xy = clickedButton.getName().split(" ", 2);
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);
            parent.buttonClicked(x, y);

        }
    }
}

class MyMouseListener implements MouseListener {
    game parent;

    MyMouseListener(game parent) {
        this.parent = parent;
    }

    public void mouseExited(MouseEvent arg0){
    }
    public void mouseEntered(MouseEvent arg0){
    }
    public void mousePressed(MouseEvent arg0){
    }
    public void mouseClicked(MouseEvent arg0){
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        if(SwingUtilities.isRightMouseButton(arg0)){
            Object eventSource = arg0.getSource();
            JButton clickedButton = (JButton) eventSource;
            String[] xy = clickedButton.getName().split(" ", 2);
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);
            parent.buttonRightClicked(x, y);
        }
    }
}

class timeThread implements Runnable {
    private Thread t;
    private game newGame;

    timeThread(game newGame) {
        this.newGame = newGame;
    }

    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);
                newGame.timer();
            }
            catch (InterruptedException e) {
                System.exit(0);
            }
        }
    }

    public void start() {
        if (t==null) {
            t = new Thread(this);
            t.start();
        }
    }
}

