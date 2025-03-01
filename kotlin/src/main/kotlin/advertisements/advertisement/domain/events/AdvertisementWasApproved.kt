package advertisements.advertisement.domain.events

import advertisements.advertisement.domain.Advertisement
import common.domain.DomainEvent

class AdvertisementWasApproved(
    val eventType: String,
    val version: String,
    val advertisementId: String,
) : DomainEvent(advertisementId, AGGREGATE_TYPE) {

    companion object {
        const val EVENT_TYPE = "advertisement-approved"
        const val VERSION = "1.0"
        const val AGGREGATE_TYPE = "advertisement"

        fun create(advertisement: Advertisement): AdvertisementWasApproved {
            return AdvertisementWasApproved(
                EVENT_TYPE,
                VERSION,
                advertisement.id.value()
            )
        }
    }
}
