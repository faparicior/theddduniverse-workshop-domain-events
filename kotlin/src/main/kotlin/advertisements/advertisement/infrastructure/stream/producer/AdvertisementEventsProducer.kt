package advertisements.advertisement.infrastructure.stream.producer

import advertisements.advertisement.domain.events.AdvertisementWasApproved
import advertisements.advertisement.infrastructure.stream.producer.events.AdvertisementApprovedEvent
import common.domain.DomainEvent
import common.domain.EventPublisher
import common.infrastructure.stream.MessageBroker
import common.infrastructure.stream.producer.SerializableEvent
import framework.ThreadContext
import java.util.UUID

class AdvertisementEventsProducer (
    private val messageBroker: MessageBroker
): EventPublisher {
    companion object {
        const val PUB_ADVERTISEMENT = "pub.advertisement"
    }

    override fun publish(events: List<DomainEvent>) {
        for (event in events) {
            publishToMessageBroker(event)
        }
    }

    private fun publishToMessageBroker(event: DomainEvent) {
        try {
            when (event) {
                is AdvertisementWasApproved -> publishAdvertisementApproved(event)
                else -> {} // Handle other event types or do nothing
            }
        } catch (e: Exception) {
            throw RuntimeException("Error publishing event to message broker", e)
        }
    }

    private fun publishAdvertisementApproved(event: AdvertisementWasApproved) {
        val correlationId = ThreadContext.get("correlationId")?.toString() ?: UUID.randomUUID().toString()
        val causationId = ThreadContext.get("causationId")?.toString()

        val advertisementApprovedEvent = AdvertisementApprovedEvent.create(
            event,
            correlationId,
            causationId
        )
        sendEventToMessageBroker(advertisementApprovedEvent)
    }

    private fun sendEventToMessageBroker(event: SerializableEvent) {
        messageBroker.publish(event, PUB_ADVERTISEMENT)
    }
}
