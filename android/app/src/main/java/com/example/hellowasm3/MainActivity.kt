package com.example.hellowasm3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.hellowasm3.ui.theme.HelloWasm3Theme

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
                    FibonacciResult(Wasm.wasmFibonacci(10))


                }
            }
        }
    }
}

@Composable
fun FibonacciResult(result: Int) {
    Text(text = "Fibonacci 10 = $result!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HelloWasm3Theme {
        FibonacciResult(10)
    }
}