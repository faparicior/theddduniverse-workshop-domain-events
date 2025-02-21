<?php
declare(strict_types=1);

namespace Demo\App\Advertisements\Advertisement\Infrastructure\Stream\Producer;

use Demo\App\Advertisements\Advertisement\Domain\Events\AdvertisementWasApproved;
use Demo\App\Common\Domain\DomainEvent;
use Demo\App\Common\Domain\EventPublisher;
use Demo\App\Common\Infrastructure\Stream\MessageBroker;
use Demo\App\Common\Infrastructure\Stream\Producer\SerializableEvent;
use RuntimeException;

class AdvertisementEventsProducer implements EventPublisher
{
    public function __construct(private MessageBroker $messageBroker)
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
        $event = AdvertisementApprovedEvent::create($event);

        $this->sendEventToMessageBroker($event);
    }

    private function sendEventToMessageBroker(SerializableEvent $event): void
    {
        $this->messageBroker->publish($event);
    }
}
