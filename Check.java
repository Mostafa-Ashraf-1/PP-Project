package com.mycompany.test;

import java.util.HashSet;
import java.util.Set;

public class Check {

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

    public static boolean checkArr(int[] arr) {
        Set<Integer> number = new HashSet<>();

        for (int i : arr) {
            number.add(i);
        }

        return number.size() == 9;
    }

    public static int checkCol(int[][] arr, int col) {
        Set<Integer> check_set = new HashSet<>();
        int inc = 0;

        for (int i = 0; i < arr.length; i++) {

            if (arr[i][col] < 1 || arr[i][col] > 9)
                return -1;

            if (!check_set.add(arr[i][col])) {
                return inc;
            }

            inc++;
        }

        return 0;
    }

    public static int checkRow(int[][] arr, int row) {

        Set<Integer> check_set = new HashSet<>();

        for (int col = 0; col < 9; col++) {
            int val = arr[row][col];

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
