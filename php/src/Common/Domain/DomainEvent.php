<?php
declare(strict_types=1);

namespace Demo\App\Common\Domain;

use Ramsey\Uuid\Uuid;

abstract readonly class DomainEvent
{
    public string $eventId;
    public string $aggregateId;
    public string $aggregateType;
    public \DateTimeImmutable $occurredOn;
    public ?string $correlationId;
    public ?string $causationId;

    protected function __construct(
        string $aggregateId,
        string $aggregateType,
        ?string $correlationId = null,
        ?string $causationId = null,
    ) {
        $this->eventId = Uuid::uuid4()->toString();
        $this->aggregateId = $aggregateId;
        $this->aggregateType = $aggregateType;

        $this->occurredOn = new \DateTimeImmutable();
        $this->correlationId = $correlationId ?? $this->eventId;
        $this->causationId = $causationId;
    }
}
