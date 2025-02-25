<?php
declare(strict_types=1);

namespace Demo\App\Common\Infrastructure;

use Ramsey\Uuid\Uuid;

class UniqueIdGenerator
{
    public static function generate(): string
    {
        return Uuid::uuid4()->toString();
    }
}