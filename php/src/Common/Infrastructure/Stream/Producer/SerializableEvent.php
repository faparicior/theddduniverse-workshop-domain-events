<?php
declare(strict_types=1);

namespace Demo\App\Common\Infrastructure\Stream\Producer;

readonly abstract class SerializableEvent
{
    public function __construct(
        public string $id,
        public string $schema,
        public string $eventType,
        public string $version,
        public \DateTimeImmutable $occurredOn,
        public array $payload,
    ) {}

    public function toJson(): string
    {
        return json_encode([
            'id' => $this->id,
            'schema' => $this->schema,
            'eventType' => $this->eventType,
            'version' => $this->version,
            'occurredOn' => $this->occurredOn->format(\DateTime::ATOM),
            'payload' => json_encode($this->payload),
        ]);
    }
}
