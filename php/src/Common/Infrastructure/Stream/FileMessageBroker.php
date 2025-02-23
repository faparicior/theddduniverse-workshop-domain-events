<?php
declare(strict_types=1);

namespace Demo\App\Common\Infrastructure\Stream;

use Demo\App\Common\Infrastructure\Stream\Producer\SerializableEvent;
use RuntimeException;

class FileMessageBroker implements MessageBroker
{
    private string $filePath;

    public function __construct(string $filePath)
    {
        $this->filePath = $filePath;
    }

    /**
     * @throws RuntimeException
     */
    public function publish(SerializableEvent $event, string $topic): void
    {
        $result = file_put_contents($this->filePath . $topic . ".events", $event->toJson() . PHP_EOL, FILE_APPEND);

        if ($result === false) {
            throw new RuntimeException('Failed to write to stream');
        }
    }
}
