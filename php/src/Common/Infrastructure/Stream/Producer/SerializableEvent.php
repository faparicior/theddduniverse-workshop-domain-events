<?php
declare(strict_types=1);

namespace Demo\App\Common\Infrastructure\Stream\Producer;

use Ramsey\Uuid\Uuid;

readonly abstract class SerializableEvent
{
    public function __construct(
        public string $id,
        public string $schema,
        public string $eventType,
        public string $version,
        public \DateTimeImmutable $occurredOn,
        public string $correlationId,
        public ?string $causationId,
        public string $source,
        public string $aggregateId,
        public string $aggregateType,
        public string $tenantId,
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
            'correlationId' => $this->correlationId,
            'causationId' => $this->causationId,
            'source' => $this->source,
            'aggregateId' => $this->aggregateId,
            'aggregateType' => $this->aggregateType,
            'tenantId' => $this->tenantId,
            'payload' => $this->payload,
        ]);
    }
}
