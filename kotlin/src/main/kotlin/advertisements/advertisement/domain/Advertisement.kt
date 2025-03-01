package advertisements.advertisement.domain

import advertisements.advertisement.domain.events.AdvertisementWasApproved
import advertisements.advertisement.domain.value_object.*
import advertisements.shared.value_object.*
import common.domain.DomainEvent
import java.time.LocalDateTime

class Advertisement(
    val id: AdvertisementId,
    var description: Description,
    var email: Email,
    var password: Password,
    var date: AdvertisementDate,
    val civicCenterId: CivicCenterId,
    val memberId: UserId
) {
    var status: AdvertisementStatus = AdvertisementStatus.ENABLED
        private set
    var approvalStatus: AdvertisementApprovalStatus = AdvertisementApprovalStatus.PENDING_FOR_APPROVAL
        private set

    private val events: MutableList<DomainEvent> = mutableListOf()

    fun renew(password: Password) {
        this.password = password
        updateDate()
    }

    fun update(description: Description, email: Email, password: Password) {
        this.description = description
        this.email = email
        this.password = password
        updateDate()
    }

    private fun updateDate() {
        this.date = AdvertisementDate(LocalDateTime.now())
    }

    fun disable() {
        status = AdvertisementStatus.DISABLED
    }

    fun enable() {
        status = AdvertisementStatus.ENABLED
    }

    fun approve() {
        approvalStatus = AdvertisementApprovalStatus.APPROVED
        events.add(AdvertisementWasApproved.create(this))
    }

    fun pullEvents(): List<DomainEvent> {
        val events = this.events.toList()
        this.events.clear()
        return events
    }
}
