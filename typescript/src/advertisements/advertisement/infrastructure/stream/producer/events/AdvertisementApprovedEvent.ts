import {AdvertisementWasApproved} from "../../../../domain/events/AdvertisementWasApproved";
import {SerializableEvent} from "../../../../../../common/infrastructure/stream/SerializableEvent";

export class AdvertisementApprovedEvent extends SerializableEvent {
    private static readonly SCHEMA: string = 'https://demo.com/schemas/advertisement-approved_1_0.json';

    private constructor(
        id: string,
        schema: string,
        eventType: string,
        version: string,
        occurredOn: Date,
        correlationId: string,
        causationId: string | null,
        payload: object
    ) {
        super(id, schema, eventType, version, occurredOn, correlationId, causationId, payload);
    }

    public static create(advertisementWasApproved: AdvertisementWasApproved, correlationId: string, causationId: string | null): AdvertisementApprovedEvent {
        const payload = {
            advertisementId: advertisementWasApproved.advertisementId,
        };

        return new AdvertisementApprovedEvent(
            advertisementWasApproved.id,
            AdvertisementApprovedEvent.SCHEMA,
            advertisementWasApproved.eventType,
            advertisementWasApproved.version,
            advertisementWasApproved.occurredOn,
            correlationId,
            causationId,
            payload,
        );
    }
}