package common.domain

interface EventPublisher {
    fun publish(events: List<DomainEvent>)
}
