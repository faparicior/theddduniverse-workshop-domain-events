<?php
declare(strict_types=1);

namespace Demo\App\Advertisements\Advertisement\Application\Command\DisableAdvertisement;

final readonly class DisableAdvertisementCommand
{
    public function __construct(
        public string $securityUserId,
        public string $securityUserRole,
        public string $advertisementId,
    ){}
}
