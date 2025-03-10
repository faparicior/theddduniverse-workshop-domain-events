package framework

import advertisements.advertisement.application.command.approveadvertisement.ApproveAdvertisementUseCase
import advertisements.advertisement.application.command.deleteadvertisement.DeleteAdvertisementUseCase
import advertisements.advertisement.application.command.disableadvertisement.DisableAdvertisementUseCase
import advertisements.advertisement.application.command.enableadvertisement.EnableAdvertisementUseCase
import advertisements.advertisement.application.command.publishadvertisement.PublishAdvertisementUseCase
import advertisements.advertisement.application.command.renewadvertisement.RenewAdvertisementUseCase
import advertisements.advertisement.application.command.updateadvertisement.UpdateAdvertisementUseCase
import advertisements.advertisement.domain.AdvertisementRepository
import advertisements.advertisement.domain.services.AdvertisementSecurityService
import advertisements.advertisement.infrastructure.persistence.SqLiteAdvertisementRepository
import advertisements.advertisement.infrastructure.stream.producer.AdvertisementEventsProducer
import advertisements.advertisement.ui.http.*
import advertisements.user.application.command.disablemember.DisableMemberUseCase
import advertisements.user.application.command.enablemember.EnableMemberUseCase
import advertisements.user.application.command.signupmember.SignUpMemberUseCase
import advertisements.user.domain.UserRepository
import advertisements.user.infrastructure.persistence.SqliteUserRepository
import advertisements.user.ui.http.DisableMemberController
import advertisements.user.ui.http.EnableMemberController
import advertisements.user.ui.http.SignUpMemberController
import common.domain.EventPublisher
import common.infrastructure.stream.FileMessageBroker
import framework.database.DatabaseConnection
import framework.database.SqliteConnection
import framework.database.SqliteTransactionManager
import framework.database.TransactionManager
import framework.securityuser.FrameworkSecurityService
import framework.securityuser.SecurityUserRepository
import framework.securityuser.SqliteSecurityUserRepository

class DependencyInjectionResolver {
    fun publishAdvertisementController(): PublishAdvertisementController {
        return PublishAdvertisementController(
            PublishAdvertisementUseCase(
                this.advertisementRepository(),
                this.userRepository(),
                this.transactionManager(),
            ),
            this.securityService()
        )
    }

    fun updateAdvertisementController(): UpdateAdvertisementController {
        return UpdateAdvertisementController(
            UpdateAdvertisementUseCase(
                this.advertisementRepository(),
                this.advertisementSecurityService(),
                this.transactionManager(),
            ),
            this.securityService()
        )
    }

    fun renewAdvertisementController(): RenewAdvertisementController {
        return RenewAdvertisementController(
            RenewAdvertisementUseCase(
                this.advertisementRepository(),
                this.transactionManager(),
            ),
            this.securityService(),
        )
    }

    fun disableAdvertisementController(): DisableAdvertisementController {
        return DisableAdvertisementController(
            DisableAdvertisementUseCase(
                this.advertisementRepository(),
                this.advertisementSecurityService(),
                this.transactionManager(),
            ),
            this.securityService()
        )
    }

    fun enableAdvertisementController(): EnableAdvertisementController {
       return EnableAdvertisementController(
            EnableAdvertisementUseCase(
                this.advertisementRepository(),
                this.userRepository(),
                this.advertisementSecurityService(),
                this.transactionManager(),
            ),
            this.securityService()
        )
    }

    fun approveAdvertisementController(): ApproveAdvertisementController {
        return ApproveAdvertisementController(
            ApproveAdvertisementUseCase(
                this.advertisementRepository(),
                this.advertisementSecurityService(),
                this.transactionManager(),
                this.advertisementEventPublisher(),
            ),
            this.securityService()
        )
    }

    fun transactionManager(): TransactionManager {
        return SqliteTransactionManager(
            this.connection()
        )
    }

    fun signUpMemberController(): SignUpMemberController {
        return SignUpMemberController(
            SignUpMemberUseCase(
                this.userRepository(),
                this.transactionManager(),
            ),
            this.securityService()
        )
    }

    fun disableMemberController(): DisableMemberController {
        return DisableMemberController(
            DisableMemberUseCase(
                this.userRepository(),
                this.transactionManager(),
            ),
            this.securityService()
        )
    }

    fun enableMemberController(): EnableMemberController {
        return EnableMemberController(
            EnableMemberUseCase(
                this.userRepository(),
                this.transactionManager(),
            ),
            this.securityService()
        )
    }

    fun advertisementRepository(): AdvertisementRepository {
        return SqLiteAdvertisementRepository(
            this.connection()
        )
    }

    fun userRepository(): UserRepository {
        return SqliteUserRepository(
            this.connection()
        )
    }

    fun securityService(): FrameworkSecurityService {
        return FrameworkSecurityService(
            this.securityUserRepository()
        )
    }

    fun securityUserRepository(): SecurityUserRepository {
        return SqliteSecurityUserRepository(
            this.connection()
        )
    }

    fun advertisementSecurityService(): AdvertisementSecurityService {
        return AdvertisementSecurityService(
            this.userRepository()
        )
    }

    fun connection(): DatabaseConnection {
        return SqliteConnection.getInstance()
    }

    fun deleteAdvertisementController(): DeleteAdvertisementController {
        return DeleteAdvertisementController(
            DeleteAdvertisementUseCase(
                this.advertisementRepository(),
                this.advertisementSecurityService(),
                this.transactionManager(),
            ),
            this.securityService()
        )
    }

    fun advertisementEventPublisher(): EventPublisher {
        return AdvertisementEventsProducer(
            FileMessageBroker(
                "src/main/resources/stream/"
            )
        )
    }
}