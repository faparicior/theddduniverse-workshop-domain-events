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
        payload: object
    ) {
        super(id, schema, eventType, version, occurredOn, payload);
    }

    public static create(advertisementWasApproved: AdvertisementWasApproved): AdvertisementApprovedEvent {
        const payload = {
            advertisementId: advertisementWasApproved.advertisementId,
        };

        return new AdvertisementApprovedEvent(
            advertisementWasApproved.id,
            AdvertisementApprovedEvent.SCHEMA,
            advertisementWasApproved.eventType,
            advertisementWasApproved.version,
            advertisementWasApproved.occurredOn,
            payload,
        );
    }
}