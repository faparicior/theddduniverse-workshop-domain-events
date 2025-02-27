package advertisements.advertisement.infrastructure.stream.producer.events

import advertisements.advertisement.domain.events.AdvertisementWasApproved
import common.infrastructure.stream.producer.SerializableEvent
import java.time.LocalDateTime

class AdvertisementApprovedEvent private constructor(
    override val id: String,
    override val schema: String,
    override val eventType: String,
    override val version: String,
    override val occurredOn: LocalDateTime,
    override val payload: Map<String, Any>
) : SerializableEvent(id, schema, eventType, version, occurredOn, payload) {

    companion object {
        const val SCHEMA = "https://demo.com/schemas/advertisement-approved_1_0.json"

        fun create(advertisementWasApproved: AdvertisementWasApproved): AdvertisementApprovedEvent {
            val payload = mapOf(
                "advertisementId" to advertisementWasApproved.advertisementId
            )

            return AdvertisementApprovedEvent(
                advertisementWasApproved.id,
                SCHEMA,
                advertisementWasApproved.eventType,
                advertisementWasApproved.version,
                advertisementWasApproved.occurredOn,
                payload
            )
        }
    }
}
