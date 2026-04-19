import javax.swing.*;
import java.awt.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class GUI extends JFrame {
    private JTextField[][] cells = new JTextField[9][9];
    private JLabel statusLabel;
    private int[][][] examples = {
            {{5,3,4,6,7,8,9,1,2},{6,7,2,1,9,5,3,4,8},{1,9,8,3,4,2,5,6,7},{8,5,9,7,6,1,4,2,3},{4,2,6,8,5,3,7,9,1},{7,1,3,9,2,4,8,5,6},{9,6,1,5,3,7,2,8,4},{2,8,7,4,1,9,6,3,5},{3,4,5,2,8,6,1,7,9}},
            {{1,1,1,1,1,1,1,1,1},{2,2,2,2,2,2,2,2,2},{3,3,3,3,3,3,3,3,3},{4,4,4,4,4,4,4,4,4},{5,5,5,5,5,5,5,5,5},{6,6,6,6,6,6,6,6,6},{7,7,7,7,7,7,7,7,7},{8,8,8,8,8,8,8,8,8},{9,9,9,9,9,9,9,9,9}},
            {{8,2,7,1,5,4,3,9,6},{9,6,5,3,2,7,1,4,8},{3,4,1,6,8,9,7,5,2},{5,9,3,4,6,8,2,7,1},{4,7,2,5,1,3,6,8,9},{6,1,8,9,7,2,4,3,5},{7,8,6,2,3,5,9,1,4},{1,5,4,7,9,6,8,2,3},{2,3,9,8,4,1,5,6,7}},
            {{1,1,3,4,5,6,7,8,9},{4,5,6,7,8,9,1,2,3},{7,8,9,1,2,3,4,5,6},{2,3,4,5,6,7,8,9,1},{5,6,7,8,9,1,2,3,4},{8,9,1,2,3,4,5,6,7},{3,1,2,6,4,5,9,7,8},{6,4,5,9,7,8,3,1,2},{9,7,8,3,1,2,6,4,5}},
            {{1,4,7,2,5,8,3,6,9},{2,5,8,3,6,9,1,4,7},{3,6,9,1,4,7,2,5,8},{4,7,1,5,8,2,6,9,3},{5,8,2,6,9,3,4,7,1},{6,9,3,4,7,1,5,8,2},{7,1,4,8,2,5,9,3,6},{8,2,5,9,3,6,7,1,4},{9,3,6,7,1,4,8,2,5}},
            {{5,5,4,6,7,8,9,1,2},{6,7,2,1,9,5,3,4,8},{1,9,8,3,4,2,5,6,7},{8,5,9,7,6,1,4,2,3},{4,2,6,8,5,3,7,9,1},{7,1,3,9,2,4,8,5,6},{9,6,1,5,3,7,2,8,4},{2,8,7,4,1,9,6,3,5},{3,4,5,2,8,6,1,7,9}},
            {{2,4,8,3,9,5,7,1,6},{5,7,1,6,2,8,3,4,9},{9,3,6,7,4,1,5,8,2},{6,8,2,5,3,9,1,7,4},{3,5,9,1,7,4,6,2,8},{7,1,4,8,6,2,9,5,3},{8,6,3,4,1,7,2,9,5},{1,9,5,2,8,6,4,3,7},{4,2,7,9,5,3,8,6,1}},
            {{9,3,4,6,7,8,9,1,2},{9,7,2,1,9,5,3,4,8},{9,9,8,3,4,2,5,6,7},{9,5,9,7,6,1,4,2,3},{9,2,6,8,5,3,7,9,1},{9,1,3,9,2,4,8,5,6},{9,6,1,5,3,7,2,8,4},{9,8,7,4,1,9,6,3,5},{9,4,5,2,8,6,1,7,9}},
            {{4,3,5,2,6,9,7,8,1},{6,8,2,5,7,1,4,9,3},{1,9,7,8,3,4,5,6,2},{8,2,6,1,9,5,3,4,7},{3,7,4,6,8,2,9,1,5},{9,5,1,7,4,3,6,2,8},{5,1,9,3,2,6,8,7,4},{2,4,8,9,5,7,1,3,6},{7,6,3,4,1,8,2,5,9}},
            {{5,3,4,6,7,8,9,1,2},{6,7,2,1,9,5,3,4,8},{1,9,8,3,4,2,5,6,7},{8,5,9,7,6,1,4,2,3},{4,2,6,8,5,3,7,9,1},{7,1,3,9,2,4,8,5,6},{9,6,1,5,3,7,2,8,4},{2,8,7,4,1,9,6,3,5},{3,4,5,2,8,6,1,7,1}}
    };
    private int currentExample = 0;

    public GUI() {
        setTitle("Parallel Sudoku Validator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 750);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        statusLabel = new JLabel("Press a button to start", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.ITALIC, 22));
        statusLabel.setPreferredSize(new Dimension(600, 60));
        add(statusLabel, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(9, 9));
        gridPanel.setBackground(Color.BLACK);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                cells[r][c] = new JTextField();
                cells[r][c].setHorizontalAlignment(JTextField.CENTER);
                cells[r][c].setFont(new Font("SansSerif", Font.BOLD, 22));
                cells[r][c].setBackground(Color.WHITE);

                int top = (r % 3 == 0) ? 3 : 1;
                int left = (c % 3 == 0) ? 3 : 1;
                cells[r][c].setBorder(BorderFactory.createMatteBorder(top, left, 1, 1, Color.BLACK));
                gridPanel.add(cells[r][c]);
            }
        }
        add(gridPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        btnPanel.setBackground(Color.WHITE);

        JButton btnLoad = new JButton("Load Example");
        JButton btnChallenge = new JButton("User Challenge");
        JButton btnValidate = new JButton("Validate Board");

        styleButton(btnLoad);
        styleButton(btnChallenge);
        styleButton(btnValidate);

        btnPanel.add(btnLoad);
        btnPanel.add(btnChallenge);
        btnPanel.add(btnValidate);
        add(btnPanel, BorderLayout.SOUTH);

        btnLoad.addActionListener(e -> {
            statusLabel.setText("Loaded Example #" + (currentExample % examples.length + 1));
            statusLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
            loadBoard(examples[currentExample % examples.length]);
            currentExample++;
        });

        btnChallenge.addActionListener(e -> {
            statusLabel.setText("Challenge Mode: Fill the blanks!");
            statusLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
            loadBoard(examples[0]);
            cells[0][0].setText("");
            cells[4][4].setText("");
            cells[8][8].setText("");
            cells[0][0].setBackground(new Color(255, 255, 200));
            cells[4][4].setBackground(new Color(255, 255, 200));
            cells[8][8].setBackground(new Color(255, 255, 200));
        });

        btnValidate.addActionListener(e -> runParallelValidation());
    }

    private void styleButton(JButton btn) {
        btn.setPreferredSize(new Dimension(160, 45));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(240, 240, 240));
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
    }

    private void loadBoard(int[][] board) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                cells[r][c].setText(String.valueOf(board[r][c]));
                cells[r][c].setBackground(Color.WHITE);
            }
        }
    }

    private void runParallelValidation() {
        int[][] boardData = new int[9][9];
        for(int r=0; r<9; r++) {
            for(int c=0; c<9; c++) {
                cells[r][c].setBackground(Color.WHITE);
            }
        }

        try {
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    String text = cells[r][c].getText().trim();
                    boardData[r][c] = text.isEmpty() ? 0 : Integer.parseInt(text);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Numbers only (1-9).");
            return;
        }

        final int NUM_TASKS = 27;
        final int NUM_THREADS = 3;
        ForkJoinPool pool = new ForkJoinPool(NUM_THREADS);
        ReentrantLock lock = new ReentrantLock();
        AtomicInteger errors = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(NUM_TASKS);

        new Thread(() -> {
            pool.invoke(new MainTask(boardData, lock, latch, errors));
            try {
                latch.await();
                SwingUtilities.invokeLater(() -> {
                    if (errors.get() == 0) {
                        JOptionPane.showMessageDialog(this, "Valid Sudoku!");
                    } else {
                        highlightErrors(boardData);
                        JOptionPane.showMessageDialog(this, "Invalid! Errors marked.");
                    }
                });
            } catch (InterruptedException ex) {}
        }).start();
    }

    private void highlightErrors(int[][] board) {
        Color babyBlue = new Color(173, 216, 230);
        Color lightRed = new Color(255, 150, 150);

        //Coloring the whole Row, Col & Square Baby Blue
        for (int i = 0; i < 9; i++) {
            if (Check.checkRow(board, i) != 0) {
                for (int c = 0; c < 9; c++) cells[i][c].setBackground(babyBlue);
            }
            if (Check.checkCol(board, i) != 0) {
                for (int r = 0; r < 9; r++) cells[r][i].setBackground(babyBlue);
            }
        }
        for (int r = 0; r < 9; r += 3) {
            for (int c = 0; c < 9; c += 3) {
                if (!Check.checkSquare(board, r, c)) {
                    for (int sr = 0; sr < 3; sr++) {
                        for (int sc = 0; sc < 3; sc++) cells[r+sr][c+sc].setBackground(babyBlue);
                    }
                }
            }
        }

        //Coloring the specific cells that cause the error in Red
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int val = board[r][c];

                // Check if empty or out of range
                if (val < 1 || val > 9) {
                    cells[r][c].setBackground(lightRed);
                    continue;
                }

                boolean conflict = false;
                // Row Duplicate Check
                for (int i = 0; i < 9; i++) {
                    if (i != c && board[r][i] == val) conflict = true;
                }
                // Column Duplicate Check
                for (int i = 0; i < 9; i++) {
                    if (i != r && board[i][c] == val) conflict = true;
                }
                // Square Duplicate Check
                int sR = (r / 3) * 3, sC = (c / 3) * 3;
                for (int i = sR; i < sR + 3; i++) {
                    for (int j = sC; j < sC + 3; j++) {
                        if ((i != r || j != c) && board[i][j] == val) conflict = true;
                    }
                }

                if (conflict) {
                    cells[r][c].setBackground(lightRed);
                }
            }
        }
    }
}
