import {AdvertisementWasApproved} from "../../../../domain/events/AdvertisementWasApproved";
import {SerializableEvent} from "../../../../../../common/infrastructure/stream/SerializableEvent";

export class AdvertisementApprovedEvent extends SerializableEvent {
    private static readonly SCHEMA: string = 'https://demo.com/schemas/advertisement-approved_1_0.json';
    private static readonly SOURCE: string = 'advertisements';

    private constructor(
        id: string,
        schema: string,
        eventType: string,
        version: string,
        occurredOn: Date,
        correlationId: string,
        causationId: string | null,
        source: string,
        aggregateId: string,
        aggregateType: string,
        tenantId: string,
        payload: object
    ) {
        super(id, schema, eventType, version, occurredOn, correlationId, causationId, source, aggregateId, aggregateType, tenantId, payload);
    }

    public static create(advertisementWasApproved: AdvertisementWasApproved, correlationId: string, causationId: string | null, tenantId: string): AdvertisementApprovedEvent {
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
            this.SOURCE,
            advertisementWasApproved.aggregateId,
            advertisementWasApproved.aggregateType,
            tenantId,
            payload,
        );
    }
}
