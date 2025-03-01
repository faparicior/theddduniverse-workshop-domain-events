import {v4 as uuidv4} from 'uuid';

export abstract class DomainEvent
{
    public id: string;
    public occurredOn: Date;
    public aggregateId: string;
    public aggregateType: string;

    protected constructor(aggregateId: string, aggregateType: string){
        this.id = uuidv4();
        this.occurredOn = new Date();
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
    }
}
