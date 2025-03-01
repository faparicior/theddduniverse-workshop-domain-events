
export abstract class SerializableEvent {
    protected constructor(
        public id: string,
        public schema: string,
        public eventType: string,
        public version: string,
        public occurredOn: Date,
        public correlationId: string,
        public causationId: string | null,
        public source: string,
        public aggregateId: string,
        public aggregateType: string,
        public tenantId: string,
        public payload: { },
    ) {}

    toJson(): string {
        return JSON.stringify({
            id: this.id,
            schema: this.schema,
            eventType: this.eventType,
            version: this.version,
            occurredOn: this.occurredOn.toISOString(),
            correlationId: this.correlationId,
            causationId: this.causationId ?? "",
            source: this.source,
            aggregateId: this.aggregateId,
            aggregateType: this.aggregateType,
            tenantId: this.tenantId,
            payload: this.payload,
        });
    }
}
