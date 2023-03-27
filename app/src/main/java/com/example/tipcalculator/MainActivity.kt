package com.example.tipcalculator

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TipCalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun TipCalculatorScreen() {
    var serviceCostAmountInput by remember { mutableStateOf("") }
    var tipAmount by remember { mutableStateOf(0.0) }
    val amount = serviceCostAmountInput.toDoubleOrNull() ?: 0.0
    val randomBill = GenerateRandomBill()
    var totalAmount by remember { mutableStateOf(0.0) }
    val decimalFormat = DecimalFormat("#.##")

    Column(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.tip_calculator_heading),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(16.dp))

        Button(onClick = { serviceCostAmountInput = randomBill.toString() }) {
            Text(text = stringResource(R.string.tip_calculator_button))
        }

        Spacer(Modifier.height(16.dp))
        EditServiceCostField(
            value = serviceCostAmountInput,
            onValueChange = { serviceCostAmountInput = it }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),

            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().background(Color.White)
            ) {
                Button(
                    onClick = {
                        tipAmount = calculateTip(10.0, amount)
                        totalAmount = decimalFormat.format(amount + tipAmount).toDouble()
                    },
                    modifier = Modifier.padding(end = 8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = Color.Black),
                    border = BorderStroke(1.dp, Color.Black)
                ) {
                    Text(text = stringResource(R.string.tip_percent_1))
                }
                Button(
                    onClick = {
                        tipAmount = calculateTip(15.0, amount)
                        totalAmount = decimalFormat.format(amount + tipAmount).toDouble()
                    },
                    modifier = Modifier.padding(start = 8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = Color.Black),
                    border = BorderStroke(1.dp, Color.Black)
                ) {
                    Text(text = stringResource(R.string.tip_percent_2))
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        tipAmount = calculateTip(18.0, amount)
                        totalAmount = decimalFormat.format(amount + tipAmount).toDouble()
                    },
                    modifier = Modifier.padding(end = 8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = Color.Black),
                    border = BorderStroke(1.dp, Color.Black)
                ) {
                    Text(text = stringResource(R.string.tip_percent_3))
                }
                Button(
                    onClick = {
                        tipAmount = calculateTip(20.0, amount)
                        totalAmount = decimalFormat.format(amount + tipAmount).toDouble()
                    },
                    modifier = Modifier.padding(start = 8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = Color.Black),
                    border = BorderStroke(1.dp, Color.Black)
                ) {
                    Text(text = stringResource(R.string.tip_percent_4))
                }
            }

        }

        Column(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = stringResource(R.string.bill_amount, "$$amount"),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.tip_amount, "$$tipAmount"),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Divider(color = Color.LightGray, thickness = 4.dp, modifier = Modifier.fillMaxWidth())
            Text(
                text = stringResource(R.string.total_amount, "$$totalAmount"),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

        }

    }
}


@SuppressLint("UnrememberedMutableState")
@Composable
fun EditServiceCostField(
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.service_cost)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

private fun calculateTip(tipPercent: Double, amount: Double, decimalPlaces: Int = 2): Double {
    val tip = tipPercent / 100 * amount
    return BigDecimal(tip).setScale(decimalPlaces, RoundingMode.HALF_UP).toDouble()
}


private fun GenerateRandomBill(): String {
    val min = 1.0
    val max = 1000.0
    val amount = min + (max - min) * Math.random()
    val decimalFormat = DecimalFormat("#.##")
    val formattedAmount = decimalFormat.format(amount)

    return formattedAmount
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipCalculatorTheme {
        TipCalculatorScreen()
    }
}