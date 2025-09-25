package org.temporal.resource

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.temporal.model.PaymentEvent

@Path("/payment")
class PaymentResource {
    @GET
    @Path("/events/status")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    fun streamPaymentEvents(): Flow<PaymentEvent> =
        flow {
            emit(PaymentEvent("PENDING", "Payment is pending..."))
            delay(2000)
            emit(PaymentEvent("PROCESSING", "Processing your payment..."))
            delay(3000)
            emit(PaymentEvent("SUCCESS", "Payment successful..."))
        }
}
