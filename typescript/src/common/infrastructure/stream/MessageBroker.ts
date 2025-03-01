import {SerializableEvent} from "./SerializableEvent";

export interface MessageBroker {
    publish(event: SerializableEvent, topic: string): void;
}
