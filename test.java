//This class is for testing purposes...

import java.util.HashSet;
import java.util.Set;

public class test {

    public static void main(String args[])
    {
            //testing board(VALID)
            int[][] board = {
            {5,3,4,6,7,8,9,1,2},
            {6,7,2,1,9,5,3,4,8},
            {1,9,8,3,4,2,5,6,7},
            {8,5,9,7,6,1,4,2,3},
            {2,2,6,8,5,3,7,9,1},
            {7,1,3,9,2,4,8,5,6},
            {9,6,1,5,3,7,2,8,4},
            {2,8,7,4,1,9,6,3,5},
            {3,4,5,2,8,6,1,9,9}// 7 -> 9
        };

        //int check = Check.checkCol(board, 0);
        int CheckRow = Check.checkRow(board, 8);

        if (CheckRow == 0)
        {
            System.out.println("Valid Row");
        }
        else if (CheckRow == -1)
        {
            System.out.println("Invalid numbers");
        }
        else{System.out.println("Invalid Row");}

        Set<Integer> check_set = new HashSet<>();

        int c = 0;
        for (int i = 0; i < board.length; i++) {

            if (!check_set.add(board[i][c])) {
                System.out.println("duplicated number in row " + i + " col " + c);
            }
        }

        System.out.println("Check_set: " + check_set);
    }
}
