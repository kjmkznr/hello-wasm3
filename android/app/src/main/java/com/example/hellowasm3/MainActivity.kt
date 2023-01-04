package com.example.hellowasm3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hellowasm3.ui.theme.HelloWasm3Theme
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // wasm データを読み込む
        val inputStream = resources.openRawResource(R.raw.helloworld_bg)
        val bytes = inputStream.readBytes()
        inputStream.close()
        Wasm.initialize(bytes.toTypedArray())
        setContent {
            HelloWasm3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        Fibonacci(KtFib)
                        Fibonacci(Wasm)
                    }
                }
            }
        }
    }
}

@Composable
fun Fibonacci(fib: Fibonacci) {
    val scope = rememberCoroutineScope()
    var result by remember { mutableStateOf(0) }
    var text by remember { mutableStateOf("") }
    var timeFib by remember { mutableStateOf(0L) }
    Column {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = text,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = { v: String ->
                    if (v.matches("[0-9]*".toRegex())) {
                        text = v
                        result = 0
                        timeFib = 0L
                    }},
            )
            Button(
                modifier = Modifier.padding(horizontal = 5.dp),
                onClick = {
                    if (text.isNotEmpty()) {
                        scope.launch {
                            timeFib = measureTimeMillis {
                                result = fib.calc(text.toInt())
                            }
                        }
                    }
                },
                content = {
                    Text("Run")
                }
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
        ) {
            val num = if (text.isNotEmpty()) text.toInt() else 0
            val clazz = fib.javaClass.toString()
            Text(text = "$clazz: Fibonacci $num = $result ($timeFib ms)")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HelloWasm3Theme {
        Fibonacci(KtFib)
        Fibonacci(Wasm)
    }
}