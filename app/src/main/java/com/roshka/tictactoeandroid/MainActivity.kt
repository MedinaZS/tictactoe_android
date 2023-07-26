package com.roshka.tictactoeandroid

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var textX: TextView
    private lateinit var textO: TextView
    private var game = mutableListOf<Button>()
    private var turn: String = "X"
    private var attemps = 0
    private var countWinX = 0
    private var countWinO = 0

    private val winPosibilities = arrayOf(
        /*Horizontal*/
        intArrayOf(0, 1, 2),
        intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8),
        /*Vertical*/
        intArrayOf(0, 3, 6),
        intArrayOf(1, 4, 7),
        intArrayOf(2, 5, 8),
        /*Diagonal*/
        intArrayOf(0, 4, 8),
        intArrayOf(2, 4, 6),
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textX = findViewById(R.id.text_turn_x)
        textO = findViewById(R.id.text_turn_o)

        /* Add buttons to list */
        game.add(findViewById(R.id.button_1))
        game.add(findViewById(R.id.button_2))
        game.add(findViewById(R.id.button_3))
        game.add(findViewById(R.id.button_4))
        game.add(findViewById(R.id.button_5))
        game.add(findViewById(R.id.button_6))
        game.add(findViewById(R.id.button_7))
        game.add(findViewById(R.id.button_8))
        game.add(findViewById(R.id.button_9))

    }

    fun play(view: View) {
        /* Verificar si es un botton */
        if (view is Button) {
            /*Si el texto del boton esta vacio se puede realizar la jugada*/
            if (view.text == "") {
                addPlay(view)
            }
        }
    }

    private fun addPlay(button: Button) {
        if (turn == "X") {
            button.text = "X"
            button.setTextColor(getColor(R.color.pink))
            turn = "O"
            textO.setTextColor(getColor(R.color.black))
            textX.setTextColor(getColor(R.color.gray))
        } else {
            button.text = "O"
            button.setTextColor(getColor(R.color.cyan))
            turn = "X"
            textX.setTextColor(getColor(R.color.black))
            textO.setTextColor(getColor(R.color.gray))
        }

        attemps++

        /*Verificar que se hizo mas de 3 jugadas (para ver si hay ganador) */
        if (attemps >= 3) {
            /* Como siempre entra en el if checkwinner siempre se ejecuta
            *  Cuando se cumpla las dos condiciones seria un empate*/
            if (!checkWinner() && attemps == 9)
                showAlert("It's a tie :(")
        }
    }

    private fun checkWinner(): Boolean {
        /* Verificar si se cumple alguna de las posibilidades de ganar */
        for (possibility in winPosibilities) {
            if (game[possibility[0]].text == "X" && game[possibility[1]].text == "X" && game[possibility[2]].text == "X") {
                countWinX++
                showAlert("X Win!!")
                return true
            } else if (game[possibility[0]].text == "O" && game[possibility[1]].text == "O" && game[possibility[2]].text == "O") {
                countWinO++
                showAlert("O Win!!")
                return true
            }
        }
        /* Solo se retorna falso cuando no se cumple las condiciones de arriba
        *  Ya que cuando se cumple las condiciones de ahi arriba */
        return false
    }

    private fun clearGame() {
        for (button in game) {
            button.text = ""
        }
        attemps = 0
        turn = "X"
        textX.setTextColor(getColor(R.color.black))
        textO.setTextColor(getColor(R.color.gray))
    }

    private fun startOver() {
        clearGame()
        countWinO = 0
        countWinX = 0
    }

    private fun showAlert(title: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage("\nX score: $countWinX\n\nO score: $countWinO")
            .setPositiveButton("Play Again")
            { _, _ ->
                clearGame()
            }
            .setNegativeButton("Start Over")
            { _, _ ->
                startOver()
            }
            .setCancelable(false) //Para que no se pueda cerrar con el boton de atras ni con click fuera
            .show()
    }

}