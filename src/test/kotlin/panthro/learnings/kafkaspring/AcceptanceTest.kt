package panthro.learnings.kafkaspring

import com.ninjasquad.springmockk.MockkBean
import io.mockk.verify
import org.awaitility.Awaitility.await
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.context.TestPropertySource
import panthro.learnings.kafkaspring.domain.Transaction
import panthro.learnings.kafkaspring.domain.cashback.RegisterMatchedOfferUseCase
import java.time.Duration


@SpringBootTest
@EmbeddedKafka
@TestPropertySource(
    properties = [
        "spring.kafka.bootstrap-servers=\${spring.embedded.kafka.brokers}",
        "spring.kafka.consumer.auto-offset-reset=earliest"
    ]
)
internal class AcceptanceTest {

    @MockkBean(relaxed = true)
    lateinit var registerMatchedOfferUseCase: RegisterMatchedOfferUseCase

    @Autowired
    lateinit var template: KafkaTemplate<Any, Any>

    @Value("\${topic.transaction.original}")
    lateinit var transactionTopic: String

    @Test
    fun `should register successfully matched offers`() {

        val transaction = Transaction(id = uuid())

        template.send(transactionTopic, transaction)

        await()
            .atMost(Duration.ofDays(10))
            .untilAsserted {
                verify {
                    registerMatchedOfferUseCase.invoke(any())
                }
            }

    }
}
