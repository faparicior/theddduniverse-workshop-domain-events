import {SerializableEvent} from "../SerializableEvent";
import {MessageBroker} from "../MessageBroker";

export class FileMessageBroker implements MessageBroker {
    private filePath: string;

    constructor(filePath: string) {
        this.filePath = filePath;
    }

    /**
     * @throws Error
     */
    public publish(event: SerializableEvent, topic: string): void {
        const fs = require('fs');
        const path = `${this.filePath}${topic}.events`;
        const data = event.toJson() + '\n';

        try {
            fs.appendFileSync(path, data);
        } catch (err) {
            throw new Error('Failed to write to stream');
        }
    }
}
