<?php
declare(strict_types=1);

namespace Demo\App\Common\Domain;

interface EventPublisher
{
    public function publish(DomainEvent ...$events): void;
}
