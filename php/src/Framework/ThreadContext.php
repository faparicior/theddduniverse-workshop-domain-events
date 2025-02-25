<?php
declare(strict_types=1);

namespace Demo\App\Framework;

class ThreadContext
{
    private static ?ThreadContext $instance = null;
    private array $storage = [];

    private function __construct() {}

    public static function getInstance(): ThreadContext
    {
        if (self::$instance === null) {
            self::$instance = new self();
        }

        return self::$instance;
    }

    public function setValue(string $key, mixed $value): void
    {
        $this->storage[$key] = $value;
    }

    public function getValue(string $key): mixed
    {
        return $this->storage[$key] ?? null;
    }
}