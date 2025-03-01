package common.domain

import java.time.LocalDateTime
import java.util.UUID

abstract class DomainEvent {
    val id: String
    val occurredOn: LocalDateTime

    init {
        this.id = UUID.randomUUID().toString()
        this.occurredOn = LocalDateTime.now()
    }
}
