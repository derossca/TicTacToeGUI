import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class TicTacToeFrame extends JFrame {

    //Fields and variables
    private static final int ROW = 3;
    private static final int COL = 3;
    private JButton[][] gameButtons = new JButton[ROW][COL];
    private String player = "X";
    private static String[][] board = new String[ROW][COL];

    public TicTacToeFrame()
    {
        //Setting up the frame and frame position on screen
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createCenterFrame();
        setLayout(new BorderLayout());

        //Action listener for the game buttons
        ActionListener buttonListener = new ButtonListener();

        // Initialize the GUI board and the logical board
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(ROW, COL));

        for (int r = 0; r < ROW; r++)
        {
            for (int c = 0; c < COL; c++)
            {
                board[r][c] = " ";
                JButton gameBtn = new JButton();
                gameButtons[r][c] = gameBtn;
                gameBtn.setFont(new Font("Arial", Font.BOLD, 48));
                gameBtn.setActionCommand(r + "," + c);
                gameBtn.addActionListener(buttonListener);
                buttonPanel.add(gameBtn);
            }
        }

        //Quit button
        JButton quitBtn = new JButton("Quit");
        quitBtn.setFont(new Font("Arial", Font.BOLD, 22));
        quitBtn.addActionListener(e -> System.exit(0));

        //Adding panel and quit button to the game and their relative positions
        add(buttonPanel, BorderLayout.CENTER);
        add(quitBtn, BorderLayout.SOUTH);
        setVisible(true);
    }

    //Button Listerner class that implements ActionListener for game button functionality
    private class ButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String command = e.getActionCommand();
            int row = Integer.parseInt(command.split(",")[0]);
            int col = Integer.parseInt(command.split(",")[1]);
            JButton gameBtn = gameButtons[row][col];

            if (!gameBtn.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Invalid move! Please select another space!");
                return;
            }

            if (gameBtn.getText().equals("") && !gameOver())
            {
                board[row][col] = player;
                gameBtn.setText(player);
                if (isWin(player))
                {
                    JOptionPane.showMessageDialog(null, "Player " + player + " wins!");
                    int playAgain = JOptionPane.showConfirmDialog(null, "Play Again?!", "Game Over", JOptionPane.YES_NO_OPTION);
                    if (playAgain == JOptionPane.YES_OPTION)
                    {
                        resetGame();
                    } else {
                        System.exit(0);
                    }
                } else if (isTie()) {
                    JOptionPane.showMessageDialog(null, "The game is a tie!");
                    int playAgain = JOptionPane.showConfirmDialog(null, "Play again?", "Game Over", JOptionPane.YES_NO_OPTION);
                    if (playAgain == JOptionPane.YES_OPTION)
                    {
                        resetGame();
                    } else {
                        System.exit(0);
                    }
                } else {
                    player = player.equals("X") ? "O" : "X";
                }
            }
        }
    }

    //if game is over is true, a tie or win
    private boolean gameOver()
    {
        return isWin("X") || isWin("O") || isTie();
    }

    //resets the game
    private void resetGame()
    {
        for (int r = 0; r < ROW; r++)
        {
            for (int c = 0; c < COL; c++)
            {
                board[r][c] = " ";
                player = "X";
                gameButtons[r][c].setText("");
            }
        }
    }


    //methods for the game, checking wins, clearing and initializing board
    private void clearBoard() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                board[row][col] = " ";

            }
        }
    }

    private void initializeBoard() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                board[row][col] = " ";
            }
        }
    }

    private static boolean isValidMove(int row, int col)
    {
        boolean retVal = false;
        if(board[row][col].equals(" "))
            retVal = true;

        return retVal;

    }
    private static boolean isWin(String player)
    {
        if(isColWin(player) || isRowWin(player) || isDiagnalWin(player))
        {
            return true;
        }

        return false;
    }
    private static boolean isColWin(String player)
    {
        // checks for a col win for specified player
        for(int col=0; col < COL; col++)
        {
            if(board[0][col].equals(player) &&
                    board[1][col].equals(player) &&
                    board[2][col].equals(player))
            {
                return true;
            }
        }
        return false; // no col win
    }
    private static boolean isRowWin(String player)
    {
        // checks for a row win for the specified player
        for(int row=0; row < ROW; row++)
        {
            if(board[row][0].equals(player) &&
                    board[row][1].equals(player) &&
                    board[row][2].equals(player))
            {
                return true;
            }
        }
        return false; // no row win
    }
    private static boolean isDiagnalWin(String player)
    {
        // checks for a diagonal win for the specified player

        if(board[0][0].equals(player) &&
                board[1][1].equals(player) &&
                board[2][2].equals(player) )
        {
            return true;
        }
        if(board[0][2].equals(player) &&
                board[1][1].equals(player) &&
                board[2][0].equals(player) )
        {
            return true;
        }
        return false;
    }

    // checks for a tie before board is filled.
    // check for the win first to be efficient
    private static boolean isTie()
    {
        boolean xFlag = false;
        boolean oFlag = false;
        // Check all 8 win vectors for an X and O so
        // no win is possible
        // Check for row ties
        for(int row=0; row < ROW; row++)
        {
            if(board[row][0].equals("X") ||
                    board[row][1].equals("X") ||
                    board[row][2].equals("X"))
            {
                xFlag = true; // there is an X in this row
            }
            if(board[row][0].equals("O") ||
                    board[row][1].equals("O") ||
                    board[row][2].equals("O"))
            {
                oFlag = true; // there is an O in this row
            }

            if(! (xFlag && oFlag) )
            {
                return false; // No tie can still have a row win
            }

            xFlag = oFlag = false;

        }
        // Now scan the columns
        for(int col=0; col < COL; col++)
        {
            if(board[0][col].equals("X") ||
                    board[1][col].equals("X") ||
                    board[2][col].equals("X"))
            {
                xFlag = true; // there is an X in this col
            }
            if(board[0][col].equals("O") ||
                    board[1][col].equals("O") ||
                    board[2][col].equals("O"))
            {
                oFlag = true; // there is an O in this col
            }

            if(! (xFlag && oFlag) )
            {
                return false; // No tie can still have a col win
            }
        }
        // Now check for the diagonals
        xFlag = oFlag = false;

        if(board[0][0].equals("X") ||
                board[1][1].equals("X") ||
                board[2][2].equals("X") )
        {
            xFlag = true;
        }
        if(board[0][0].equals("O") ||
                board[1][1].equals("O") ||
                board[2][2].equals("O") )
        {
            oFlag = true;
        }
        if(! (xFlag && oFlag) )
        {
            return false; // No tie can still have a diag win
        }
        xFlag = oFlag = false;

        if(board[0][2].equals("X") ||
                board[1][1].equals("X") ||
                board[2][0].equals("X") )
        {
            xFlag =  true;
        }
        if(board[0][2].equals("O") ||
                board[1][1].equals("O") ||
                board[2][0].equals("O") )
        {
            oFlag =  true;
        }
        if(! (xFlag && oFlag) )
        {
            return false; // No tie can still have a diag win
        }

        // Checked every vector so I know I have a tie
        return true;
    }

    //Centers the frame on the screen
    private void createCenterFrame(){
        //screen dimensions
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        //center frame
        setSize(screenWidth * 3 / 4, screenHeight * 3 / 4);
        setLocation(screenWidth / 8, screenHeight / 8);
    }
}
