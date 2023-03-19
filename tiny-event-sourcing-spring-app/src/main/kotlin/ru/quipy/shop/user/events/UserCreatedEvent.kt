package ru.quipy.shop.user.events

import lombok.Getter
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import ru.quipy.shop.user.UserAggregate
import java.util.*


@Getter
@DomainEvent(name = "USER_CREATE_EVENT")
class UserCreatedEvent(
    @Getter
    override val id: UUID,
    @Getter
    val firstName: String,
    @Getter
    val lastName: String,
    @Getter
    val phone: String,
    @Getter
    val email: String,
    @Getter
    val address: String,
) : Event<UserAggregate>(
    name = "USER_CREATE_EVENT"
)