<?php
declare(strict_types=1);

namespace Demo\App\Advertisements\Advertisement\Domain\Events;

use Demo\App\Advertisements\Advertisement\Domain\Advertisement;
use Demo\App\Common\Domain\DomainEvent;
use Ramsey\Uuid\Uuid;

final readonly class AdvertisementWasApproved extends DomainEvent
{
    private const string EVENT_TYPE = 'advertisement-approved';
    private const string VERSION = '1.0';

    private function __construct(
        public string  $eventType,
        public string  $version,
        public string  $advertisementId,
        string $correlationId,
        ?string $causationId = null,
    ) {
        parent::__construct(
            $correlationId,
            $causationId,
        );
    }

    public static function create(
        Advertisement $advertisement,
        string $correlationId,
        ?string $causationId = null,
    ): AdvertisementWasApproved
    {
        return new self(
            self::EVENT_TYPE,
            self::VERSION,
            $advertisement->id()->value(),
            $correlationId,
            $causationId,
        );
    }
}
