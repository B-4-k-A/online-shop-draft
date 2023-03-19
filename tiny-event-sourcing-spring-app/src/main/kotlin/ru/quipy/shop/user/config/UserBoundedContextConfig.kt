package ru.quipy.shop.user.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.quipy.bankDemo.accounts.api.AccountAggregate
import ru.quipy.bankDemo.accounts.logic.Account
import ru.quipy.core.EventSourcingService
import ru.quipy.core.EventSourcingServiceFactory
import ru.quipy.shop.user.User
import ru.quipy.shop.user.UserAggregate
import java.util.*


@Configuration
class UserBoundedContextConfig {

    @Autowired
    private lateinit var eventSourcingServiceFactory: EventSourcingServiceFactory

    @Bean
    fun userEsService(): EventSourcingService<UUID, UserAggregate, User> =
            eventSourcingServiceFactory.create()
}