package com.example.wordlegame

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {

    private var correctAnswer = ""
    private var tries = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val obj = FourLetterWordList
        val background = findViewById<ConstraintLayout>(R.id.background)
        val guessButton = findViewById<Button>(R.id.guessButton)
        val resetButton = findViewById<Button>(R.id.resetButton)
        val editAnswer = findViewById<EditText>(R.id.editGuess)
        val correctDisplay = findViewById<TextView>(R.id.CorrectAnswer)
        val g1 = findViewById<TextView>(R.id.g1_across)
        val g1Check = findViewById<TextView>(R.id.g1_check_across)
        val g2 = findViewById<TextView>(R.id.g2_across)
        val g3 = findViewById<TextView>(R.id.g3_across)
        val g2Check = findViewById<TextView>(R.id.g2_check_across)
        val g3Check = findViewById<TextView>(R.id.g3_check_across)

        correctAnswer = obj.getRandomFourLetterWord()
        correctDisplay.text = getString(R.string.blank4)
        g1.text = ""
        g1Check.text = ""
        g2.text = ""
        g2Check.text = ""
        g3.text = ""
        g3Check.text = ""
        editAnswer.text.clear()
        editAnswer.inputType = InputType.TYPE_NULL

        background.setOnClickListener {
            val view: View? = this.currentFocus

            if (view != null) {
                val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }

        }
        editAnswer.setOnClickListener {
            val view: View? = this.currentFocus

            if (view != null) {
                val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.showSoftInput(view.findFocus(), 0)
            }

        }

        guessButton.setOnClickListener {
            val answer = editAnswer.text.toString().uppercase()
            
            val view: View? = this.currentFocus

            if (view != null) {
                val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }

            if (obj.isValidEntry(answer)) {
                editAnswer.text.clear()
                tries++
                if (tries == 1) {
                    g1.text = answer
                    g1Check.text = checkGuess(answer)
                } else if (tries == 2) {
                    g2.text = answer
                    g2Check.text = checkGuess(answer)
                } else if (tries == 3) {
                    g3.text = answer
                    g3Check.text = checkGuess(answer)
                    correctDisplay.text = correctAnswer
                    guessButton.visibility = View.INVISIBLE
                    editAnswer.visibility = View.INVISIBLE
                    resetButton.visibility = View.VISIBLE
                    editAnswer.inputType = InputType.TYPE_NULL
                }
            } else {
                Toast.makeText(this, "Invalid Entry. Word must have four letters only", Toast.LENGTH_SHORT).show()
            }
        }

        resetButton.setOnClickListener {
            correctAnswer = obj.getRandomFourLetterWord()
            correctDisplay.text = "XXXX"
            guessButton.visibility = View.VISIBLE
            editAnswer.visibility = View.VISIBLE
            resetButton.visibility = View.INVISIBLE
            g1.text = ""
            g1Check.text = ""
            g2.text = ""
            g2Check.text = ""
            g3.text = ""
            g3Check.text = ""
            tries = 0
        }

    }

    private fun checkGuess(guess: String) : String {
        var result = ""
        for (i in 0..3) {
            result += if (guess[i] == correctAnswer[i]) {
                "O"
            } else if (guess[i] in correctAnswer) {
                "+"
            } else {
                "X"
            }
        }
        return result
    }
}