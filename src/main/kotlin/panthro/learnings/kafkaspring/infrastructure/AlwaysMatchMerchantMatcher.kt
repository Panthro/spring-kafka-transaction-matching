package panthro.learnings.kafkaspring.infrastructure

import org.springframework.stereotype.Component
import panthro.learnings.kafkaspring.domain.Merchant
import panthro.learnings.kafkaspring.domain.Transaction
import panthro.learnings.kafkaspring.domain.merchantmatching.MerchantMatcher
import panthro.learnings.kafkaspring.uuid

@Component
class AlwaysMatchMerchantMatcher : MerchantMatcher {
    override fun match(transaction: Transaction): Merchant = Merchant(id = uuid())
}
