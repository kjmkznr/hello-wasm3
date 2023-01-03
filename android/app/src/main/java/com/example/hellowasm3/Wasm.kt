package com.example.hellowasm3

class Wasm {
    companion object {
        init {
            System.loadLibrary("wasm3-jni")
        }
        fun initialize(wasm: Array<Byte>) {
            loadWasm(wasm.toByteArray())
        }
        private external fun loadWasm(wasm: ByteArray)
        external fun wasmFibonacci(num: Int): Int
    }
}