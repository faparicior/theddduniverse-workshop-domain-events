<?php
declare(strict_types=1);

namespace Demo\App\Common\Domain;

use Ramsey\Uuid\Uuid;

abstract readonly class DomainEvent
{
    public string $id;
    public \DateTimeImmutable $occurredOn;

    protected function __construct(
    ) {
        $this->id = Uuid::uuid4()->toString();
        $this->occurredOn = new \DateTimeImmutable();
    }
}
