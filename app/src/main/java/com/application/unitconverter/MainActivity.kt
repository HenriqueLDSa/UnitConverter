package com.application.unitconverter

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.application.unitconverter.ui.theme.UnitConverterTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterTheme {
                UnitConverter()
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun UnitConverterPreview(){
    UnitConverter()
}

@Composable
fun UnitConverter(){
    val context = LocalContext.current
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }
    var inputUnit by remember { mutableStateOf("Select") }
    var outputUnit by remember { mutableStateOf("Select") }
    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf(false) }
    val iConversionFactor = remember { mutableDoubleStateOf(0.0) }
    val oConversionFactor = remember { mutableDoubleStateOf(0.0) }
    var outputString by remember { mutableStateOf("") }

    fun convertUnits(context: Context, inputVal: String): String {
        val inputValueDouble = inputVal.toDoubleOrNull()
        if (inputValueDouble == null) {
            Toast.makeText(context, "Invalid numeric value!", Toast.LENGTH_SHORT).show()
            return ""
        }

        if (iConversionFactor.doubleValue == 0.0 || oConversionFactor.doubleValue == 0.0) {
            Toast.makeText(context, "Conversion factors not set", Toast.LENGTH_SHORT).show()
            return ""
        }

        val result = (inputValueDouble * iConversionFactor.doubleValue * 100.0 / oConversionFactor.doubleValue) / 100.0
        outputValue = String.format(Locale.US,"%.2f", result)

        return "$outputValue $outputUnit"
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(
            text = "Unit Converter",
            style = MaterialTheme.typography.headlineLarge
        )
        
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                inputValue = it
                if (inputUnit != "Select" && outputUnit != "Select")
                    outputString = convertUnits(context, inputValue)
                else if (inputValue.replace("\\s".toRegex(), "") != "")
                    Toast.makeText(
                        context,
                        "Please select both input and output units!",
                        Toast.LENGTH_SHORT
                    ).show()
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row {
            Box {
                Button(onClick = { iExpanded = !iExpanded }, shape = RoundedCornerShape(10)) {
                    Text(text = inputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "arrow down" )
                }

                DropdownMenu(expanded = iExpanded, onDismissRequest = { iExpanded = false }) {
                    DropdownMenuItem(
                        text = { Text(text = "Millimeters") },
                        onClick = {
                            iExpanded = !iExpanded
                            inputUnit = "Millimeters"
                            iConversionFactor.doubleValue = 0.001
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Centimeters") },
                        onClick = {
                            iExpanded = !iExpanded
                            inputUnit = "Centimeters"
                            iConversionFactor.doubleValue = 0.01
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Meters") },
                        onClick = {
                            iExpanded = !iExpanded
                            inputUnit = "Meters"
                            iConversionFactor.doubleValue = 1.0
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Kilometers") },
                        onClick = {
                            iExpanded = !iExpanded
                            inputUnit = "Kilometers"
                            iConversionFactor.doubleValue = 1000.0
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.width(20.dp))

            Box {
                Button(onClick = { oExpanded = !oExpanded }, shape = RoundedCornerShape(10)) {
                    Text(text = outputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "arrow down" )
                }

                DropdownMenu(expanded = oExpanded, onDismissRequest = { oExpanded = false }) {
                    DropdownMenuItem(
                        text = { Text(text = "Millimeters") },
                        onClick = {
                            oExpanded = !oExpanded
                            outputUnit = "Millimeters"
                            oConversionFactor.doubleValue = 0.001
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Centimeters") },
                        onClick = {
                            oExpanded = !oExpanded
                            outputUnit = "Centimeters"
                            oConversionFactor.doubleValue = 0.01
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Meters") },
                        onClick = {
                            oExpanded = !oExpanded
                            outputUnit = "Meters"
                            oConversionFactor.doubleValue = 1.0
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Kilometers") },
                        onClick = {
                            oExpanded = !oExpanded
                            outputUnit = "Kilometers"
                            oConversionFactor.doubleValue = 1000.0
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Result: $outputString",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}