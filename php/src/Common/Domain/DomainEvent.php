<?php
declare(strict_types=1);

namespace Demo\App\Common\Domain;

use Demo\App\Common\Infrastructure\UniqueIdGenerator;

abstract readonly class DomainEvent
{
    public string $eventId;
    public string $aggregateId;
    public string $aggregateType;
    public \DateTimeImmutable $occurredOn;

    protected function __construct(
        string $aggregateId,
        string $aggregateType,
    )
    {
        $this->eventId = UniqueIdGenerator::generate();
        $this->occurredOn = new \DateTimeImmutable();
        $this->aggregateId = $aggregateId;
        $this->aggregateType = $aggregateType;
    }
}
