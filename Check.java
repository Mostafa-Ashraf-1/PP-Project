
import java.util.HashSet;
import java.util.Set;

public class Check {

    // Just testing
    public static boolean checkBox(int[][] box) {

        Set<Integer> seen = new HashSet<>();

        for (int[] row : box) {
            for (int val : row) {
                if (val < 0 || val > 9 || !seen.add(val)) { // add returns false if value already exists
                    return false;
                }
            }
        }

        return true;

    }

    // Just testing
    public static boolean checkArr(int[] arr) {
        Set<Integer> number = new HashSet<>();

        // int [] arr1 = {1,2,3,4,5,6,7,8,9};

        for (int i : arr) {
            number.add(i);
        }

        // System.out.println(number);
        int size = number.size();
        if (size < 9) {
            return false;
        }
        return true;
    }

    public static int checkCol(int[][] arr, int col) {
        Set<Integer> check_set = new HashSet<>();
        int size_before, size_after, inc = 0;

        for (int i = 0; i < arr.length; i++) {

            if (arr[i][col] < 1 || arr[i][col] > 9)
                return -1;

            size_before = check_set.size();
            check_set.add(arr[i][col]);
            size_after = check_set.size();

            if (size_before == size_after) {
                // Returns the row that causes the problem(duplication)
                return inc;
            }

            inc += 1;
        }

        return 0;
    }



        public static int checkRow(int[][] arr, int row) {

        Set<Integer> check_set = new HashSet<>();
        int size_before, size_after;

        for (int col = 0; col < 9; col++) {
            int val = arr[row][col];
            if (val < 1 || val > 9) {
                return -1;
            }

            size_before = check_set.size();
            check_set.add(val);
            size_after = check_set.size();

            if (size_before == size_after) {
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


