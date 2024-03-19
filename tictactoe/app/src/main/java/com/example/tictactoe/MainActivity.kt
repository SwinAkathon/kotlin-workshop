package com.example.tictactoe

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tictactoe.ui.theme.TictactoeTheme
import kotlinx.coroutines.channels.TickerMode

const val LTAG = "TicTacToe-Game"
class MainActivity : ComponentActivity() {
  private lateinit var game: TicTacToeGame
  override fun onCreate(savedInstanceState: Bundle?) {
    if (!::game.isInitialized) {
      Log.i(LTAG, "Game initialised")
      game = TicTacToeGame()
    }

    super.onCreate(savedInstanceState)
    setContent {
      TictactoeTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          MyApp(game)
        }
      }
    }
  }
}

enum class Player { X, O, None }

class TicTacToeGame {
  constructor() {
    Log.i(LTAG, "Game initialised")
  }
  // remember game state across recomposation
  var board = mutableStateListOf(
    mutableStateListOf(Player.None, Player.None, Player.None),
    mutableStateListOf(Player.None, Player.None, Player.None),
    mutableStateListOf(Player.None, Player.None, Player.None)
  )
  var currentPlayer by mutableStateOf(Player.X)
  var winner by mutableStateOf(Player.None)

  fun resetGame() {
    for (i in board.indices) {
      for (j in board[i].indices) {
        board[i][j] = Player.None
      }
    }
    currentPlayer = Player.X
    winner = Player.None
  }

  fun makeMove(row: Int, col: Int): Boolean {
    if (row !in 0..2 || col !in 0..2 ||
      board[row][col] != Player.None || winner != Player.None) {
      return false
    }

    board[row][col] = currentPlayer
    checkWinner()
    currentPlayer = if (currentPlayer == Player.X) Player.O else Player.X
    return true
  }

  private fun checkWinner() {
    // Check rows, columns, and diagonals for a winner
    // Update `winner` variable if a winner is found

    // Check rows for winner
    for (row in 0..2) {
      if (board[row][0] == board[row][1] && board[row][1] == board[row][2] && board[row][0] != Player.None) {
        winner = board[row][0]
        return
      }
    }

    // Check columns for winner
    for (col in 0..2) {
      if (board[0][col] == board[1][col] && board[1][col] == board[2][col] && board[0][col] != Player.None) {
        winner = board[0][col]
        return
      }
    }

    // Check diagonal (top-left to bottom-right) for winner
    if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != Player.None) {
      winner = board[0][0]
      return
    }

    // Check diagonal (top-right to bottom-left) for winner
    if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != Player.None) {
      winner = board[0][2]
      return
    }

    // Check for draw (no winner and no empty spaces)
    val draw = board.all { row -> row.all { cell -> cell != Player.None } }
    if (draw) {
      winner = Player.None
    }
  }
}


@Composable
fun TicTacToeScreen(game: TicTacToeGame) {

  Column(modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally) {
    for (i in 0..2) {
      Row {
        for (j in 0..2) {
          Box(modifier = Modifier
            .size(100.dp)
            .border(2.dp, Color.Black)
            .clickable { game.makeMove(i, j) },
            contentAlignment = Alignment.Center
          ) {
            Text(text = when (game.board[i][j]) {
              Player.X -> "X"
              Player.O -> "O"
              else -> ""
            }, fontSize = 24.sp)
          }
        }
      }
    }
    if (game.winner != Player.None) {
      Text(text = "Winner: ${game.winner}", fontSize = 24.sp)
    }
    Button(onClick = { game.resetGame() }) {
      Text("Reset")
    }
  }
}

@Composable
fun MyApp(game: TicTacToeGame) {
  TicTacToeScreen(game)
}
