package advertisements.advertisement.application.command.updateadvertisement

import advertisements.advertisement.domain.exceptions.AdvertisementNotFoundException
import advertisements.advertisement.application.exceptions.PasswordDoesNotMatchException
import advertisements.advertisement.domain.services.AdvertisementSecurityService
import advertisements.advertisement.domain.value_object.AdvertisementId
import advertisements.advertisement.domain.value_object.Description
import advertisements.shared.value_object.Email
import advertisements.shared.value_object.Password
import advertisements.shared.value_object.UserId

class UpdateAdvertisementUseCase(
    private val advertisementRepository: advertisements.advertisement.domain.AdvertisementRepository,
    private val advertisementSecurityService: AdvertisementSecurityService,
    private val transactionManager: framework.database.TransactionManager,
) {
    fun execute(updateAdvertisementCommand: UpdateAdvertisementCommand) {
        transactionManager.beginTransaction()

        try {
            val advertisementId = AdvertisementId(updateAdvertisementCommand.id)
            val advertisement = advertisementRepository.findById(advertisementId)

            if (null === advertisement) {
                throw AdvertisementNotFoundException.withId(advertisementId.value())
            }

            advertisementSecurityService.verifyMemberUserCanManageAdvertisement(
                UserId(updateAdvertisementCommand.securityUserId),
                advertisement
            )

            if (!advertisement.password.isValidatedWith(updateAdvertisementCommand.password))
                throw PasswordDoesNotMatchException.build()

            advertisement.update(
                Description(updateAdvertisementCommand.description),
                Email(updateAdvertisementCommand.email),
                Password.fromPlainPassword(updateAdvertisementCommand.password)
            )

            advertisementRepository.save(advertisement)
            transactionManager.commit()
        } catch (e: Exception) {
            transactionManager.rollback()
            throw e
        }
    }
}
