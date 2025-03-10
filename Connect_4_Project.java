Connect.java:
------------
public class Connect {
    
    private int numRows;
    private int numColumns;
    private char[][] board;
    
    
    public Connect(int numRows, int numColumns)
    {
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.board = new char[numRows][numColumns];
    }
    
    
    public void createBoard()
    {
        for (int i = 0; i<this.numRows; i++)
        {
            for (int j =0; j<this.numColumns; j++)
            {
                board[i][j] = '.';
            }
        }
        
    }


    public void printBoard()
    {
       for (int i = 0; i<numRows; i++)
        {
            for (int j =0; j<numColumns; j++)
            {
                System.out.print(" " + board[i][j]+ " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    
    public void placeCoin(char XorO, int ColumnInput)
    {
        for (int i = numRows - 1; i>=0; i--)
        {
            if (board[i][ColumnInput-1] == '.')
            {
                board[i][ColumnInput-1] = XorO;
                break;
            }
        }
    }
    
    

    public boolean checkWin(char character)
    {
        for (int j = 0; j< numColumns; j++)
        {
            for (int i =0 ; i<numRows; i++)
            {
                if (i+3< numRows && board[i][j] == character && board[i+1][j] == character && board[i+2][j] == character && board[i+3][j] == character)
                {
                    return true;
                }
                
                if (j + 3 < numColumns && board[i][j] == character && board[i][j+1] == character && board[i][j+2] == character && board[i][j+3] == character)
                {
                    return true;
                }
                
                if (i + 3 < numRows && j + 3 < numColumns && board[i][j] == character && board[i+1][j+1] == character && board[i+2][j+2] == character && board[i+3][j+3] == character)
                {
                    return true;
                }
                if (i - 3 >= 0 && j - 3 >= 0 && board[i][j] == character && board[i-1][j-1] == character && board[i-2][j-2] == character && board[i-3][j-3] == character)
                {
                    return true;
                }
                
            }
            
        }
        return false;
    }
}

MyProgram.java:
--------------
import java.util.Scanner;
import java.util.Arrays;

public class MyProgram
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        
        Connect connect1 = new Connect(6,7);
        connect1.createBoard();
        
        System.out.println("Welcome to the game of Connect4!");
        System.out.println("To place your disk, select a column from 1 to 7. ");
        connect1.printBoard();
        System.out.println();
        
        boolean playAgain = true;
        
        while (playAgain)
        {
            while (true)
            {
            
                System.out.print("Player 1 (X): ");
                int ColumnInput = input.nextInt();
                connect1.placeCoin('X', ColumnInput);
                connect1.printBoard();
                
                if (connect1.checkWin('X'))
                {
                    System.out.println("Player 1 wins! ");
                    break;
                } 
                
                System.out.print("Player 2 (O): ");
                ColumnInput = input.nextInt();
                connect1.placeCoin('O', ColumnInput);
                connect1.printBoard();
                
                if (connect1.checkWin('O'))
                {
                    System.out.println("Player 2 wins! ");
                    break;
                } 
            }
        
            System.out.println("Want to play again (Y/N)?");
            char choice = input.next().charAt(0);
            
            if (choice == 'Y' || choice == 'y')
            {
                connect1.createBoard();
                connect1.printBoard();
                playAgain = true;
                
            }
            else if (choice == 'N'|| choice == 'n')
            {
                System.out.println("Thanks for playing! ");
                playAgain = false;
            }
            else
            {
                System.out.println("Invalid input.");
                playAgain = false;
            }
        }
        
        
    }
}

