package ru.quipy.shop.user.events

import lombok.Getter
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import ru.quipy.shop.user.UserAggregate

import java.util.UUID

@Getter
@DomainEvent(name = "USER_CHANGE_FIRST_NAME_EVENT")
class UserChangeFirstNameEvent(
    val userId: UUID,
    val firstName: String
) : Event<UserAggregate>(
    name = "USER_CHANGE_FIRST_NAME_EVENT"
)