import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class GUI extends JFrame {
    private JTextField[][] cells = new JTextField[9][9];

    //10 Examples
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
        setSize(600, 700);
        setLayout(new BorderLayout(10, 10));
      
        JPanel gridPanel = new JPanel(new GridLayout(9, 9));
        add(gridPanel, BorderLayout.CENTER);
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                cells[r][c] = new JTextField();
                cells[r][c].setHorizontalAlignment(JTextField.CENTER);
                cells[r][c].setFont(new Font("SansSerif", Font.BOLD, 20));
                
              
                int top = (r % 3 == 0) ? 2 : 1;
                int left = (c % 3 == 0) ? 2 : 1;
                cells[r][c].setBorder(BorderFactory.createMatteBorder(top, left, 1, 1, Color.BLACK));
                gridPanel.add(cells[r][c]);
            }
        }

        //Buttons
        JPanel btnPanel = new JPanel();
        JButton btnLoad = new JButton("Load Next Example (1-10)");
        JButton btnChallenge = new JButton("User Challenge (-3 items)");
        JButton btnValidate = new JButton("Validate Board");

        btnPanel.add(btnLoad);
        btnPanel.add(btnChallenge);
        btnPanel.add(btnValidate);
        add(btnPanel, BorderLayout.SOUTH);

        btnLoad.addActionListener(e -> {
            loadBoard(examples[currentExample % examples.length]);
            currentExample++;
        });

        btnChallenge.addActionListener(e -> {
            int[][] board = examples[0];
            loadBoard(board);
            //Empty cells
            cells[0][0].setText(""); cells[4][4].setText(""); cells[8][8].setText("");
            cells[0][0].setBackground(Color.YELLOW);
            cells[4][4].setBackground(Color.YELLOW);
            cells[8][8].setBackground(Color.YELLOW);
        });

        btnValidate.addActionListener(e -> runParallelValidation());
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
        try {
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    String text = cells[r][c].getText().trim();
                    boardData[r][c] = text.isEmpty() ? 0 : Integer.parseInt(text);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter numbers only (1-9).");
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
                int result = errors.get();
                SwingUtilities.invokeLater(() -> {
                    if (result == 0) {
                        JOptionPane.showMessageDialog(this, "Valid Sudoku!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid! Errors found");
                    }
                });
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();
    }
}
