import {v4 as uuidv4} from 'uuid';

export abstract class DomainEvent
{
    public id: string;
    public occurredOn: Date;

    protected constructor() {
        this.id = uuidv4();
        this.occurredOn = new Date();
    }
}
