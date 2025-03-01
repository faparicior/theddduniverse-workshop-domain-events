package advertisements.user.application.command.enablemember

import advertisements.shared.value_object.UserId
import advertisements.user.domain.UserRepository
import advertisements.user.domain.exceptions.AdminWithIncorrectCivicCenterException
import advertisements.user.domain.exceptions.MemberDoesNotExistsException
import advertisements.user.domain.exceptions.UserNotFoundException
import framework.database.TransactionManager

class EnableMemberUseCase(
    private val userRepository: UserRepository,
    private val transactionManager: TransactionManager,
) {

    fun execute(command: EnableMemberCommand) {
        transactionManager.beginTransaction()
        try {
            val adminUser = userRepository.findAdminById(UserId(command.securityUserId))
                ?: throw UserNotFoundException.asAdmin()

            val member = userRepository.findMemberById(UserId(command.memberId))
                ?: throw MemberDoesNotExistsException.build()

            if (!adminUser.civicCenterId().equals(member.civicCenterId())) {
                throw AdminWithIncorrectCivicCenterException.differentCivicCenterFromMember()
            }

            member.enable()

            userRepository.saveMember(member)
            transactionManager.commit()
        } catch (e: Exception) {
            transactionManager.rollback()
            throw e
        }
    }
}
