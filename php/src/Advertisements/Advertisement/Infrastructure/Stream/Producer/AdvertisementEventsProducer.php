<?php
declare(strict_types=1);

namespace Demo\App\Advertisements\Advertisement\Infrastructure\Stream\Producer;

use Demo\App\Advertisements\Advertisement\Domain\Events\AdvertisementWasApproved;
use Demo\App\Advertisements\Advertisement\Infrastructure\Stream\Producer\Events\AdvertisementApprovedEvent;
use Demo\App\Common\Domain\DomainEvent;
use Demo\App\Common\Domain\EventPublisher;
use Demo\App\Common\Infrastructure\Stream\MessageBroker;
use Demo\App\Common\Infrastructure\Stream\Producer\SerializableEvent;
use Demo\App\Common\Infrastructure\UniqueIdGenerator;
use Demo\App\Framework\ThreadContext;
use RuntimeException;

class AdvertisementEventsProducer implements EventPublisher
{
    private const string PUB_ADVERTISEMENT = 'pub.advertisement';
    private const string SOURCE = 'advertisement';

    public function __construct(private MessageBroker $messageBroker, private ThreadContext $threadContext)
    {
    }

    public function publish(DomainEvent ...$events): void
    {
        foreach ($events as $event) {
            $this->publishToMessageBroker($event);
        }
    }

    private function publishToMessageBroker(DomainEvent $event): void
    {
        try {
            match (true) {
                $event instanceof AdvertisementWasApproved => $this->publishAdvertisementApproved($event),
                default => null
            };
        } catch (\Exception $e) {
            throw new RuntimeException('Error publishing event to message broker', 0, $e);
        }
    }

    private function publishAdvertisementApproved(AdvertisementWasApproved $event)
    {
        $event = AdvertisementApprovedEvent::create(
            $event,
            self::SOURCE,
            $this->threadContext->getValue("tenantId"),
            $this->threadContext->getValue("correlationId") ?? UniqueIdGenerator::generate(),
            $this->threadContext->getValue("causationId"),
        );

        $this->sendEventToMessageBroker($event);
    }

    private function sendEventToMessageBroker(SerializableEvent $event): void
    {
        $this->messageBroker->publish($event, self::PUB_ADVERTISEMENT);
    }
}
