package common.infrastructure.stream.producer

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

abstract class SerializableEvent(
    open val id: String,
    open val schema: String,
    open val eventType: String,
    open val version: String,
    open val occurredOn: LocalDateTime,
    open val correlationId: String,
    open val causationId: String?,
    open val source: String,
    open val aggregateId: String,
    open val aggregateType: String,
    open val payload: Map<String, Any>
) {
    fun toJson(): String {
        return """
            {
                "id": "$id",
                "schema": "$schema",
                "eventType": "$eventType",
                "version": "$version",
                "occurredOn": "${occurredOn.format(DateTimeFormatter.ISO_DATE_TIME)}",
                "correlationId": "$correlationId",
                "causationId": "${causationId ?: ""}",
                "source": "$source",
                "aggregateId": "$aggregateId",
                "aggregateType": "$aggregateType",
                "payload": ${payloadToJson()}
            }
        """.trimIndent()
    }

    private fun payloadToJson(): String {
        val payloadJson = payload.map { (key, value) ->
            "\"$key\": \"$value\""
        }.joinToString(", ")

        return "{ $payloadJson }"
    }
}
