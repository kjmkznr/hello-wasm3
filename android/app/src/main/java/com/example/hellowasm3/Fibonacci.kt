package com.example.hellowasm3

interface Fibonacci {
    suspend fun calc(n: Int): Int
}