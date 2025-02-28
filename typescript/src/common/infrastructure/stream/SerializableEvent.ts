
export abstract class SerializableEvent {
    protected constructor(
        public id: string,
        public schema: string,
        public eventType: string,
        public version: string,
        public occurredOn: Date,
        public payload: { },
    ) {}

    toJson(): string {
        return JSON.stringify({
            id: this.id,
            schema: this.schema,
            eventType: this.eventType,
            version: this.version,
            occurredOn: this.occurredOn.toISOString(),
            payload: this.payload,
        });
    }
}
