import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Mineswipper extends JFrame {
    
    private JMenuBar mb;
    private JMenu options;
    private JMenuItem mi1,mi2;
    
    private JButton bSmiley;
    private JPanel northPanel,centerPanel;
    
    private Icon sIcon;
    private Icon bIcon;
    private JButton [][] b = new JButton[10][10];
    
    public Mineswipper() {
        
        super("Mineswipper");
        
        setVisible(true);

        createResouces();
        initComponents();
        assambleMenu();
        assambleNorthPanel();
        assambleCenterPanel();
        createButtons();
        
        Game.getInstance().startGame();         
        configureFrame();
    }
    
    private void createResouces() {
         sIcon = new ImageIcon("s.gif");
         bIcon = new ImageIcon("b.gif");
    }
    
    private void initComponents() {
        mb = new JMenuBar();
        mi1 = new JMenuItem("New");
        options = new JMenu("Options");
        mi2 = new JMenuItem("Exit");
        northPanel = new JPanel();
        bSmiley = new JButton(sIcon);
        centerPanel = new JPanel();
    }
    
    private void assambleMenu() {
        setJMenuBar(mb);
        mb.add(options);
        options.add(mi1);
        options.add(mi2);
        mi1.addActionListener(ev -> newGame());
        mi2.addActionListener(ev -> System.exit(0));
    }
    
    private void assambleNorthPanel() {
        northPanel.add(bSmiley);
        bSmiley.addActionListener(ev -> newGame());
        add(northPanel, BorderLayout.NORTH);
    }
    
    private void assambleCenterPanel() {
        centerPanel.setLayout(new GridLayout(10,10));
        add(centerPanel);
    }
    
    private void createButtons() {
        for (int i = 0; i < b.length; i++) {
             for (int j = 0; j < b[i].length; j++) {
                 b[i][j] = new JButton();
                 b[i][j].addActionListener(this::buttonPressed);
                 centerPanel.add(b[i][j]);
             }
         }
    }
    
    private void configureFrame() {
        setSize(500,500);
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    private void enableAllButtons(boolean enable) {
        for (int i=0; i < b.length; i++){
             for (int j = 0; j < b[i].length; j++) {
                 b[i][j].setEnabled(enable);
             }
        }
    }
    
    private void showMineOnButtons() {
        for (int i = 0; i < b.length; i++) {
             for (int j = 0; j < b[i].length; j++) {
                 int v = Game.getInstance().getCellValue(i,j);
                 if (v < 0) {
                   b[i][j].setIcon(bIcon);
                 }
             }
        }
        
    }
    private void clearAllButtons() {
        for (int i = 0; i < b.length; i++) {
             for (int j = 0; j < b[i].length; j++) {
                 b[i][j].setIcon(null);
                 b[i][j].setText(null);
             }
        }
    }
    
    private void newGame() {
        enableAllButtons(true);
        clearAllButtons();
        Game.getInstance().startGame();
    }
    
    private void gameOver() {
        enableAllButtons(false);
        showMineOnButtons();
        JOptionPane.showMessageDialog(this,"OOPS! GAME OVER");
    }
    
    private void buttonPressed(ActionEvent ev) {
            for (int i = 0; i < b.length; i++) {
                 for (int j = 0; j < b[i].length; j++) {
                     if (ev.getSource() == b[i][j]) {
                        int v = Game.getInstance().getCellValue(i,j);
                        if (v < 0) {
                            gameOver();
                        } else {
                            b[i][j].setText(String.valueOf(v));
                            b[i][j].setEnabled(false);
                        }
                     }
                 }
            }
        }
}