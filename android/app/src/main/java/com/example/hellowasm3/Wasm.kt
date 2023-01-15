package com.example.hellowasm3

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Wasm {
    companion object: Fibonacci {
        init {
            System.loadLibrary("wasm3-jni")
        }
        fun initialize(wasm: Array<Byte>) {
            loadWasm(wasm.toByteArray())
        }
        private external fun loadWasm(wasm: ByteArray)
        private external fun wasmFibonacci(num: Int): Int
        override suspend fun calc(n: Int): Int {
            return withContext(Dispatchers.IO) {
                wasmFibonacci(n)
            }
        }
    }
}