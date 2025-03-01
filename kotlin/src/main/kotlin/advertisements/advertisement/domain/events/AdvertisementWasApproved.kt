package advertisements.advertisement.domain.events

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

        fun create(advertisementId: String): AdvertisementWasApproved {
            return AdvertisementWasApproved(
                EVENT_TYPE,
                VERSION,
                advertisementId
            )
        }
    }
}
