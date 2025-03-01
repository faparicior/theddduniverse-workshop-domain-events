import {Advertisement} from "../Advertisement";
import {DomainEvent} from "../../../../common/domain/DomainEvent";
import {v4 as uuidv4} from 'uuid';

export class AdvertisementWasApproved extends DomainEvent {
    private static readonly EVENT_TYPE: string = 'advertisement-approved';
    private static readonly VERSION: string = '1.0';
    private static readonly AGGREGATE_TYPE: string = 'advertisement';

    public eventId: string;

    private constructor(
        public eventType: string,
        public version: string,
        public advertisementId: string,
    ) {
        super(advertisementId, AdvertisementWasApproved.AGGREGATE_TYPE);
        this.eventId = uuidv4();
    }

    public static create(advertisement: Advertisement): AdvertisementWasApproved {
        return new AdvertisementWasApproved(
            AdvertisementWasApproved.EVENT_TYPE,
            AdvertisementWasApproved.VERSION,
            advertisement.id().value(),
        );
    }
}