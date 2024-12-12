package com.example.matala1


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var gridButtons: Array<Array<Button>>
    private var currentPlayer = "X"
    private var board = Array(3) { Array(3) { "" } }
    private var gameActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize grid buttons
        gridButtons = Array(3) { row ->
            Array(3) { col ->
                findViewById(resources.getIdentifier("btn_${row}${col}", "id", packageName))
            }
        }

        // Set click listeners for each button
        for (row in 0..2) {
            for (col in 0..2) {
                gridButtons[row][col].setOnClickListener {
                    if (gameActive && board[row][col].isEmpty()) {
                        makeMove(row, col, it as Button)
                    }
                }
            }
        }

        // Play Again button
        val playAgainButton = findViewById<Button>(R.id.btnPlayAgain)
        playAgainButton.setOnClickListener {
            resetGame()
        }
    }

    private fun makeMove(row: Int, col: Int, button: Button) {
        board[row][col] = currentPlayer
        button.text = currentPlayer
        if (checkWin()) {
            findViewById<TextView>(R.id.tvResult).text = "Player $currentPlayer Wins!"
            gameActive = false
            findViewById<Button>(R.id.btnPlayAgain).visibility = View.VISIBLE
        } else if (isBoardFull()) {
            findViewById<TextView>(R.id.tvResult).text = "It's a Draw!"
            gameActive = false
            findViewById<Button>(R.id.btnPlayAgain).visibility = View.VISIBLE
        } else {
            currentPlayer = if (currentPlayer == "X") "O" else "X"
            findViewById<TextView>(R.id.tvResult).text = "Player $currentPlayer's Turn"
        }
    }

    private fun checkWin(): Boolean {
        for (i in 0..2) {
            // Check rows and columns
            if ((board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) ||
                (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)
            ) return true
        }
        // Check diagonals
        if ((board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
            (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer)
        ) return true

        return false
    }

    private fun isBoardFull(): Boolean {
        for (row in board) {
            for (cell in row) {
                if (cell.isEmpty()) return false
            }
        }
        return true
    }

    private fun resetGame() {
        board = Array(3) { Array(3) { "" } }
        currentPlayer = "X"
        gameActive = true
        findViewById<TextView>(R.id.tvResult).text = "Player X's Turn"
        findViewById<Button>(R.id.btnPlayAgain).visibility = View.GONE
        for (row in 0..2) {
            for (col in 0..2) {
                gridButtons[row][col].text = ""
            }
        }
    }
}
