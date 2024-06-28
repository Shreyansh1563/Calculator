package com.example.calculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

var screenText: MutableState<String> = mutableStateOf("2+2")
var isReset = false

@Composable
fun Calculator(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = screenText.value,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            color = Color.White,
            textAlign = TextAlign.End,
            maxLines = 5,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 40.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            CalculatorButton(
                txt = "C",
                modifier = Modifier.weight(1f),
                onClick = {
                    if (screenText.value != "" && !isReset) {
                        screenText.value =
                            screenText.value.substring(0, screenText.value.length - 1)
                    }
                })
            CalculatorButton(txt = "(", modifier = Modifier.weight(1f))
            CalculatorButton(txt = ")", modifier = Modifier.weight(1f))
            CalculatorButton(txt = "/", modifier = Modifier.weight(1f))
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            CalculatorButton(txt = "7", modifier = Modifier.weight(1f))
            CalculatorButton(txt = "8", modifier = Modifier.weight(1f))
            CalculatorButton(txt = "9", modifier = Modifier.weight(1f))
            CalculatorButton(txt = "*", modifier = Modifier.weight(1f))
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            CalculatorButton(txt = "4", modifier = Modifier.weight(1f))
            CalculatorButton(txt = "5", modifier = Modifier.weight(1f))
            CalculatorButton(txt = "6", modifier = Modifier.weight(1f))
            CalculatorButton(txt = "-", modifier = Modifier.weight(1f))
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            CalculatorButton(txt = "1", modifier = Modifier.weight(1f))
            CalculatorButton(txt = "2", modifier = Modifier.weight(1f))
            CalculatorButton(txt = "3", modifier = Modifier.weight(1f))
            CalculatorButton(txt = "+", modifier = Modifier.weight(1f))
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            CalculatorButton(
                txt = "AC",
                modifier = Modifier.weight(1f),
                onClick = { screenText.value = "" })
            CalculatorButton(txt = "0", modifier = Modifier.weight(1f))
            CalculatorButton(txt = ".", modifier = Modifier.weight(1f))
            CalculatorButton(
                txt = "=",
                modifier = Modifier.weight(1f),
                onClick = {
                    screenText.value =  "=${calculateResult(screenText.value)}"
                    isReset = true
                })
        }

    }
}


@Composable
fun CalculatorButton(
    txt: String,
    modifier: Modifier,
    onClick: () -> Unit = {
        if(isReset){
            screenText.value = txt
            isReset = false
        }
        else {
            screenText.value += txt
        }
    }
) {
    FloatingActionButton(
        modifier = modifier
            .padding(8.dp)
            .height(80.dp),
        onClick = onClick
    ) {
        Text(
            text = txt,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

fun calculateResult(equation: String): String{
    if(equation[equation.length-1] !in '0'..'9'){
        return equation
    }
    val context: Context = Context.enter()
    context.optimizationLevel = -1
    val scriptable: Scriptable = context.initStandardObjects()
    val finalResult = context.evaluateString(scriptable, equation, "Javascript", 1, null).toString()
    return finalResult
}


@Preview(showBackground = false)
@Composable
fun CalculatorPreview() {
    Calculator()
}