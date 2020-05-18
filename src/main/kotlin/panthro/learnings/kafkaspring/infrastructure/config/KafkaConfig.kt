package panthro.learnings.kafkaspring.infrastructure.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.apache.kafka.clients.admin.NewTopic
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.SimpleAsyncTaskExecutor
import org.springframework.core.task.TaskExecutor
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer
import org.springframework.kafka.listener.SeekToCurrentErrorHandler
import org.springframework.kafka.support.converter.RecordMessageConverter
import org.springframework.kafka.support.converter.StringJsonMessageConverter
import org.springframework.stereotype.Component
import org.springframework.util.backoff.FixedBackOff
import javax.annotation.PostConstruct


@Configuration
class KafkaConfig() {
//    @Bean
//    fun errorHandler(template: KafkaTemplate<*, *>): SeekToCurrentErrorHandler {
//        return SeekToCurrentErrorHandler(
//            DeadLetterPublishingRecoverer(template), FixedBackOff(1000L, 2)
//        )
//    }

    @Bean
    fun converter(): RecordMessageConverter {
        return StringJsonMessageConverter()
    }

    @Bean
    fun transactions(
        @Value("\${topic.transaction.original}")
        topic: String
    ): NewTopic {
        return NewTopic(topic, 1, 1.toShort())
    }

    @Bean
    fun matchedMerchants(
        @Value("\${topic.transaction.merchant}")
        topic: String
    ): NewTopic {
        return NewTopic(topic, 1, 1.toShort())
    }

    @Bean
    fun matchedOffers(
        @Value("\${topic.transaction.merchant}")
        topic: String
    ): NewTopic {
        return NewTopic(topic, 1, 1.toShort())
    }

    @Bean
    fun objectMapper() = jacksonObjectMapper()

}
