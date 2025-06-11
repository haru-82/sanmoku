import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class sanmoku extends JFrame implements ActionListener {
    JButton[][] buttons = new JButton[3][3];
    String currentPlayer = "〇";
    int[][] winLine = new int[3][2];

    public sanmoku() {
        setTitle("〇✕ゲーム");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton btn = new JButton("");
                btn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 60));
                btn.addActionListener(this);
                buttons[i][j] = btn;
                add(btn);
            }
        }

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        if (!clicked.getText().equals("")) return;

        clicked.setText(currentPlayer);

        if (checkWinner()) {
            highlightWinner();

            
            Timer showMsgTimer = new Timer(200, new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    JOptionPane.showMessageDialog(sanmoku.this, currentPlayer + " 勝った！！！");


                    Timer resetTimer = new Timer(500, new ActionListener() {
                        public void actionPerformed(ActionEvent e2) {
                            resetBoard();
                        }
                    });
                    resetTimer.setRepeats(false);
                    resetTimer.start();
                }
            });
            showMsgTimer.setRepeats(false);
            showMsgTimer.start();

        } else if (isDraw()) {
            JOptionPane.showMessageDialog(this, "引き分けです。");
            resetBoard();
        } else {
            currentPlayer = currentPlayer.equals("〇") ? "✕" : "〇";
        }
    }

    private boolean checkWinner() {
        String p = currentPlayer;

        for (int i = 0; i < 3; i++) {
            if (p.equals(buttons[i][0].getText()) &&
                p.equals(buttons[i][1].getText()) &&
                p.equals(buttons[i][2].getText())) {
                winLine = new int[][]{{i, 0}, {i, 1}, {i, 2}};
                return true;
            }

            if (p.equals(buttons[0][i].getText()) &&
                p.equals(buttons[1][i].getText()) &&
                p.equals(buttons[2][i].getText())) {
                winLine = new int[][]{{0, i}, {1, i}, {2, i}};
                return true;
            }
        }

        if (p.equals(buttons[0][0].getText()) &&
            p.equals(buttons[1][1].getText()) &&
            p.equals(buttons[2][2].getText())) {
            winLine = new int[][]{{0, 0}, {1, 1}, {2, 2}};
            return true;
        }

        if (p.equals(buttons[0][2].getText()) &&
            p.equals(buttons[1][1].getText()) &&
            p.equals(buttons[2][0].getText())) {
            winLine = new int[][]{{0, 2}, {1, 1}, {2, 0}};
            return true;
        }

        return false;
    }

    private void highlightWinner() {
        for (int[] pos : winLine) {
            buttons[pos[0]][pos[1]].setBackground(Color.RED);
        }
    }

    private boolean isDraw() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (buttons[i][j].getText().equals("")) return false;
        return true;
    }

    private void resetBoard() {
        currentPlayer = "〇";
        winLine = new int[3][2];

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setBackground(null);
            }
    }

    public static void main(String[] args) {
        new sanmoku();
    }
}