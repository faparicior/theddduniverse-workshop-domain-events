package common.infrastructure.stream

import common.infrastructure.stream.producer.SerializableEvent

interface MessageBroker {
    fun publish(event: SerializableEvent, topic: String)
}