import {AdvertisementWasApproved} from "../../../domain/events/AdvertisementWasApproved";
import {AdvertisementApprovedEvent} from "./events/AdvertisementApprovedEvent";
import {DomainEvent} from "../../../../../common/domain/DomainEvent";
import {EventPublisher} from "../../../../../common/domain/EventPublisher";
import {SerializableEvent} from "../../../../../common/infrastructure/stream/SerializableEvent";
import {MessageBroker} from "../../../../../common/infrastructure/stream/MessageBroker";

export class AdvertisementEventsProducer implements EventPublisher {
    private static readonly PUB_ADVERTISEMENT: string = 'pub.advertisement';

    constructor(private messageBroker: MessageBroker) {}

    public publish(...events: DomainEvent[]): void {
        for (const event of events) {
            this.publishToMessageBroker(event);
        }
    }

    private publishToMessageBroker(event: DomainEvent): void {
        try {
            if (event instanceof AdvertisementWasApproved) {
                this.publishAdvertisementApproved(event);
            }
        } catch (e) {
            throw new Error('Error publishing event to message broker');
        }
    }

    private publishAdvertisementApproved(event: AdvertisementWasApproved): void {
        const approvedEvent = AdvertisementApprovedEvent.create(event);
        this.sendEventToMessageBroker(approvedEvent);
    }

    private sendEventToMessageBroker(event: SerializableEvent): void {
        this.messageBroker.publish(event, AdvertisementEventsProducer.PUB_ADVERTISEMENT);
    }
}