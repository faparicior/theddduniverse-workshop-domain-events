import {DomainEvent} from "./DomainEvent";

export interface EventPublisher {
    publish(...events: DomainEvent[]): void;
}
