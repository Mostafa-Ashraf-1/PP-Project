

import java.util.HashSet;
import java.util.Set;

public class Check {

    //For testing
    public static boolean checkBox(int[][] box) {
        Set<Integer> seen = new HashSet<>();

        for (int[] row : box) {
            for (int val : row) {
                if (val < 0 || val > 9 || !seen.add(val)) {
                    return false;
                }
            }
        }
        return true;
    }

    //For testing
    public static boolean checkArr(int[] arr) {
        Set<Integer> number = new HashSet<>();

        for (int i : arr) {
            number.add(i);
        }

        return number.size() == 9;
    }

    public static int checkCol(int[][] board, int col) {
        Set<Integer> check_set = new HashSet<>();
        int returnRowIndex = 0;

        for (int row = 0; row < board.length; row++) {

            if (board[row][col] < 1 || board[row][col] > 9)
                return -1;

            if (!check_set.add(board[row][col])) {
                return returnRowIndex;
            }

            returnRowIndex++;
        }

        return 0;
    }

    public static int checkRow(int[][] board, int row) {

        Set<Integer> check_set = new HashSet<>();

        for (int col = 0; col < 9; col++) {
            int val = board[row][col];

            if (val < 1 || val > 9)
                return -1;

            if (!check_set.add(val)) {
                return col;
            }
        }

        return 0;
    }

 
    public static boolean checkSquare(int[][] board, int startRow, int startCol) {

        Set<Integer> seen = new HashSet<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                int val = board[startRow + i][startCol + j];

                if (val < 1 || val > 9) {
                    return false;
                }

                if (!seen.add(val)) {
                    return false;
                }
            }
        }

        return true;
    }
}


