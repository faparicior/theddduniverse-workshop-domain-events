<?php
declare(strict_types=1);

namespace Demo\App\Advertisements\Advertisement\Infrastructure\Stream\Producer\Events;

use Demo\App\Advertisements\Advertisement\Domain\Events\AdvertisementWasApproved;
use Demo\App\Common\Infrastructure\Stream\Producer\SerializableEvent;

final readonly class AdvertisementApprovedEvent extends SerializableEvent
{
    //TODO: CHANGE VERSION SCHEMA TO ADAPT TO THE EVENT
    private const string SCHEMA = 'https://demo.com/schemas/advertisement-approved_1_0.json';

//        public ?string $causationId,
//        public string $correlationId,

//        public string $source,

//        public string $tenantId,
//        public string $traceId,
//        public string $userId,
//        public string $environment,

//        public ?string $publishedOn,

//        public string $aggregateVersion,


    public static function create(AdvertisementWasApproved $advertisementWasApproved): AdvertisementApprovedEvent
    {
        $payload = [
            'advertisementId' => $advertisementWasApproved->advertisementId,
        ];

        return new self(
            $advertisementWasApproved->id,
            self::SCHEMA,
            $advertisementWasApproved->eventType,
            $advertisementWasApproved->version,
            $advertisementWasApproved->occurredOn,
            $payload,
        );
    }
}
