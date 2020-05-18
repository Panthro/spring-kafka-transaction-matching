package panthro.learnings.kafkaspring.infrastructure

import org.springframework.stereotype.Component
import panthro.learnings.kafkaspring.domain.Merchant
import panthro.learnings.kafkaspring.domain.Offer
import panthro.learnings.kafkaspring.domain.Transaction
import panthro.learnings.kafkaspring.domain.offermatching.OfferMatcher
import panthro.learnings.kafkaspring.uuid

@Component
class AlwaysMatchOfferMatcher : OfferMatcher {
    override fun match(transaction: Transaction, merchant: Merchant): Offer = Offer(id = uuid(), merchant = merchant)
}
