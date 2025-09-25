package org.temporal.model

data class PaymentEvent(
    val status: String,
    val message: String,
)
