package common.infrastructure.stream

import common.infrastructure.stream.producer.SerializableEvent
import java.io.File
import java.io.IOException

class FileMessageBroker(private val filePath: String): MessageBroker {
    override fun publish(event: SerializableEvent, topic: String) {
        val file = File("$filePath$topic.events")
        try {
            file.appendText(event.toJson() + System.lineSeparator())
        } catch (e: IOException) {
            throw IOException("Failed to write to stream", e)
        }
    }
}
