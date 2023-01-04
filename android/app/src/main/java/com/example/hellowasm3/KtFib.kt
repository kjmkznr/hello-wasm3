package com.example.hellowasm3

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class KtFib {
    companion object : Fibonacci {
        private fun fib(n: Int): Int {
            return if (n <= 1) {
                n
            } else {
                val x = n - 2
                val y = n - 1
                fib(x) + fib(y)
            }
        }

        override suspend fun calc(n: Int): Int {
            return withContext(Dispatchers.IO) {
                fib(n)
            }
        }
    }
}