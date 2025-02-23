<?php
declare(strict_types=1);

namespace Demo\App\Common\Domain;

use Ramsey\Uuid\Uuid;

abstract readonly class DomainEvent
{
    public string $eventId;
    public \DateTimeImmutable $occurredOn;
    public ?string $correlationId;
    public ?string $causationId;

    protected function __construct(
        ?string $correlationId = null,
        ?string $causationId = null,
    ) {
        $this->eventId = Uuid::uuid4()->toString();
        $this->occurredOn = new \DateTimeImmutable();
        $this->correlationId = $correlationId ?? $this->eventId;
        $this->causationId = $causationId;
    }
}
