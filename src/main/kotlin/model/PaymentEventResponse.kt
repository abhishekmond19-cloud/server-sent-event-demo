package org.temporal.model

import java.util.UUID

data class PaymentEventResponse(
    val status: String,
    val confNo: String,
    val transId: UUID,
    val message: String? = null,
)
