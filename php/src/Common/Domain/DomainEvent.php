<?php
declare(strict_types=1);

namespace Demo\App\Common\Domain;

use Demo\App\Common\Infrastructure\UniqueIdGenerator;

abstract readonly class DomainEvent
{
    public string $eventId;
    public \DateTimeImmutable $occurredOn;

    protected function __construct()
    {
        $this->eventId = UniqueIdGenerator::generate();
        $this->occurredOn = new \DateTimeImmutable();
    }
}
