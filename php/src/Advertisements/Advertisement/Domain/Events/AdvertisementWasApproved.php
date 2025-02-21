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

    public string $eventId;

    private function __construct(
        public string $advertisementId,
        public string $version,
        public string $eventType,
    ) {
        $this->eventId = Uuid::uuid4()->toString();
        parent::__construct();
    }

    public static function create(Advertisement $advertisement): AdvertisementWasApproved
    {
        return new self(
            $advertisement->id()->value(),
            self::VERSION,
            self::EVENT_TYPE,
        );
    }
}
