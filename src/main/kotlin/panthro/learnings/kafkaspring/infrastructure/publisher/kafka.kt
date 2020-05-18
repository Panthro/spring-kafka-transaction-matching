package panthro.learnings.kafkaspring.infrastructure.publisher

import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import panthro.learnings.kafkaspring.domain.merchantmatching.MatchMerchantResult
import panthro.learnings.kafkaspring.domain.merchantmatching.MatchedMerchantPublisher
import panthro.learnings.kafkaspring.domain.offermatching.MatchOfferResult
import panthro.learnings.kafkaspring.domain.offermatching.MatchedOfferPublisher

@Component
class KafkaMatchedMerchantPublisher(
    @Value("\${topic.transaction.merchant}")
    private val topic: String,
    private val template: KafkaTemplate<Any, Any>
) : MatchedMerchantPublisher {
    override fun publish(result: MatchMerchantResult.Matched) {
        template.send(topic, result.matchedMerchantTransaction)
    }
}

@Component
class KafkaMatchedOfferPublisher(
    @Value("\${topic.transaction.offer}")
    private val topic: String,
    private val template: KafkaTemplate<Any, Any>
) : MatchedOfferPublisher {
    override fun publish(matched: MatchOfferResult.Matched) {
        template.send(topic, matched.matchedOfferTransaction)
    }
}
