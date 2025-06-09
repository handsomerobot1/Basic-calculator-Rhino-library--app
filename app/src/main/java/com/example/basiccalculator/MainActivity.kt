package com.example.basiccalculator



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var resultTv: TextView
    private lateinit var solutionTv: TextView

    private lateinit var buttonC: MaterialButton
    private lateinit var buttonOpenBraket: MaterialButton
    private lateinit var buttonCloseBraket: MaterialButton
    private lateinit var buttonDivide: MaterialButton
    private lateinit var buttonMultiply: MaterialButton
    private lateinit var buttonPlus: MaterialButton
    private lateinit var buttonMinus: MaterialButton
    private lateinit var buttonEqual: MaterialButton
    private lateinit var button0: MaterialButton
    private lateinit var button1: MaterialButton
    private lateinit var button2: MaterialButton
    private lateinit var button3: MaterialButton
    private lateinit var button4: MaterialButton
    private lateinit var button5: MaterialButton
    private lateinit var button6: MaterialButton
    private lateinit var button7: MaterialButton
    private lateinit var button8: MaterialButton
    private lateinit var button9: MaterialButton
    private lateinit var buttonAc: MaterialButton
    private lateinit var buttonDot: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTv = findViewById(R.id.result_tv)
        solutionTv = findViewById(R.id.solution_tv)

        buttonC = findViewById(R.id.button_c)
        buttonOpenBraket = findViewById(R.id.button_open_braket)
        buttonCloseBraket = findViewById(R.id.button_close_braket)
        buttonDivide = findViewById(R.id.button_divide)
        buttonMultiply = findViewById(R.id.button_multiply)
        buttonPlus = findViewById(R.id.button_plus)
        buttonMinus = findViewById(R.id.button_minus)
        buttonEqual = findViewById(R.id.button_equal)
        button0 = findViewById(R.id.button_0)
        button1 = findViewById(R.id.button_1)
        button2 = findViewById(R.id.button_2)
        button3 = findViewById(R.id.button_3)
        button4 = findViewById(R.id.button_4)
        button5 = findViewById(R.id.button_5)
        button6 = findViewById(R.id.button_6)
        button7 = findViewById(R.id.button_7)
        button8 = findViewById(R.id.button_8)
        button9 = findViewById(R.id.button_9)
        buttonDot = findViewById(R.id.button_dot)
        buttonAc = findViewById(R.id.button_ac)

        // Assign onClick listeners
        assignID(buttonC)
        assignID(buttonOpenBraket)
        assignID(buttonCloseBraket)
        assignID(buttonDivide)
        assignID(buttonMultiply)
        assignID(buttonPlus)
        assignID(buttonMinus)
        assignID(buttonEqual)
        assignID(button0)
        assignID(button1)
        assignID(button2)
        assignID(button3)
        assignID(button4)
        assignID(button5)
        assignID(button6)
        assignID(button7)
        assignID(button8)
        assignID(button9)
        assignID(buttonDot)
        assignID(buttonAc)
    }

    private fun assignID(btn: MaterialButton) {
        btn.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val button = view as MaterialButton
        val buttonText = button.text.toString()
        var dataToCalculate = solutionTv.text.toString()

        when (buttonText) {
            "AC" -> {
                solutionTv.text = ""
                resultTv.text = "0"
                return
            }
            "=" -> {
                resultTv.text = solutionTv.text
                return
            }
            "C" -> {
                if (dataToCalculate.isNotEmpty()) {
                    dataToCalculate = dataToCalculate.dropLast(1)
                }
            }
            else -> {
                dataToCalculate += buttonText
            }
        }

        solutionTv.text = dataToCalculate

        // Safe: Try to evaluate only if input is valid
        if (dataToCalculate.isNotEmpty() && dataToCalculate.last().isDigit()) {
            val finalResult = getResult(dataToCalculate)
            if (finalResult != "Error") {
                resultTv.text = finalResult
            }
        } else {
            resultTv.text = ""
        }
    }


    private fun getResult(data: String): String {
        return try {
            val context = Context.enter()
            context.optimizationLevel = -1
            val scriptable: Scriptable = context.initStandardObjects()
            var finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString()
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "")
            }
            finalResult
        } catch (e: Exception) {
            "Error"
        }
    }
}
