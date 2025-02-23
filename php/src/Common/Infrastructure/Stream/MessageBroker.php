<?php
declare(strict_types=1);

namespace Demo\App\Common\Infrastructure\Stream;

use Demo\App\Common\Infrastructure\Stream\Producer\SerializableEvent;

interface MessageBroker
{
    public function publish(SerializableEvent $event, string $topic): void;
}
