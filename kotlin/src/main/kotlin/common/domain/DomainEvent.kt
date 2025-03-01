package common.domain

import java.time.LocalDateTime
import java.util.UUID

abstract class DomainEvent(val aggregateId: String, val aggregateType: String) {
    val eventId: String
    val occurredOn: LocalDateTime

    init {
        this.eventId = UUID.randomUUID().toString()
        this.occurredOn = LocalDateTime.now()
    }
}
