package com.ae.news.common

data class ErrorState(
    val errorMessage: String? = null,
    val onRetry: (() -> Unit)? = null
)
