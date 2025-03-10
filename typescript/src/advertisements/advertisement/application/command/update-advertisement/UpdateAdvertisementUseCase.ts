import { AdvertisementRepository } from "../../../domain/AdvertisementRepository"
import { UpdateAdvertisementCommand } from "./UpdateAdvertisementCommand"
import {Password} from "../../../../shared/domain/value-object/Password";
import {Description} from "../../../domain/value-object/Description";
import {AdvertisementId} from "../../../domain/value-object/AdvertisementId";
import {InvalidPasswordException} from "../../exceptions/InvalidPasswordException";
import {AdvertisementNotFoundException} from "../../../domain/exceptions/AdvertisementNotFoundException";
import {Email} from "../../../../shared/domain/value-object/Email";
import {UserId} from "../../../../shared/domain/value-object/UserId";
import {SecurityService} from "../../../domain/services/SecurityService";
import {TransactionManager} from "../../../../../framework/database/TransactionManager";

export class UpdateAdvertisementUseCase {

  constructor(
    private advertisementRepository: AdvertisementRepository,
    private securityService: SecurityService,
    private transactionManager: TransactionManager,
  ) {

  }

  async execute(command: UpdateAdvertisementCommand): Promise<void> {
    await this.transactionManager.beginTransaction()

    try {
      const advertisementId = new AdvertisementId(command.id)
      const advertisement = await this.advertisementRepository.findById(advertisementId)

      if (!advertisement) {
        throw AdvertisementNotFoundException.withId(advertisementId.value())
      }

      await this.securityService.verifyMemberUserCanManageAdvertisement(new UserId(command.securityUserId), advertisement)

      if (!await advertisement.password().isValid(command.password)) {
        throw InvalidPasswordException.build()
      }

      advertisement.update(
          new Description(command.description),
          new Email(command.email),
          await Password.fromPlainPassword(command.password)
      )

      await this.advertisementRepository.save(advertisement)
      await this.transactionManager.commit();
    } catch (error) {
      await this.transactionManager.rollback();
      throw error
    }
  }
}
