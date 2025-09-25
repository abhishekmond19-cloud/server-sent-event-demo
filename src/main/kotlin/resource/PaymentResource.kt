@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.temporal.resource

import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.sse.Sse
import jakarta.ws.rs.sse.SseEventSink
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.temporal.model.PaymentEventResponse
import java.util.*

@Path("/payment")
class PaymentResource {
    @Inject
    lateinit var sse: Sse

    @GET
    @Path("/events/status")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    fun streamPaymentEvents(sink: SseEventSink) {
        GlobalScope.launch {
            val confNumber = "W1234567"
            val transId = UUID.randomUUID()
            try {
                // PENDING
                sink.send(
                    sse
                        .newEventBuilder()
                        .name("payment-status")
                        .data(
                            PaymentEventResponse(
                                status = "PENDING",
                                confNo = confNumber,
                                transId = transId,
                                message = "Payment is pending.",
                            ),
                        ).build(),
                )

                // Perform some tasks
                delay(2000)

                // PROCESSING
                sink.send(
                    sse
                        .newEventBuilder()
                        .name("payment-status")
                        .data(
                            PaymentEventResponse(
                                status = "PROCESSING",
                                confNo = confNumber,
                                transId = transId,
                                message = "Processing your payment.",
                            ),
                        ).build(),
                )

                // Perform some tasks
                delay(3000)

                // SUCCESSFUL
                sink.send(
                    sse
                        .newEventBuilder()
                        .name("payment-status")
                        .data(
                            PaymentEventResponse(
                                status = "SUCCESS",
                                confNo = confNumber,
                                transId = transId,
                                message = "Payment successful.",
                            ),
                        ).build(),
                )
            } catch (exp: Exception) {
                error("Error occurred while sending events $exp")
            } finally {
                // Close the stream after last event
                sink.close()
            }
        }
    }
}
