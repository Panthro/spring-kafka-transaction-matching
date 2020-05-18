package panthro.learnings.kafkaspring.domain.offermatching

import org.springframework.stereotype.Component
import panthro.learnings.kafkaspring.domain.Offer
import panthro.learnings.kafkaspring.domain.Merchant
import panthro.learnings.kafkaspring.domain.Transaction
import panthro.learnings.kafkaspring.domain.merchantmatching.MatchedMerchantTransaction
import java.util.UUID

@Component
class MatchOfferUseCase(
    val offerMatcher: OfferMatcher,
    val matchedOfferPublisher: MatchedOfferPublisher
) {
    fun invoke(matchedMerchantTransaction: MatchedMerchantTransaction): MatchOfferResult =
        offerMatcher.match(
            transaction = matchedMerchantTransaction.transaction,
            merchant = matchedMerchantTransaction.merchant
        ).let { offer ->
            MatchOfferResult.Matched(
                MatchedOfferTransaction(
                    transaction = matchedMerchantTransaction.transaction,
                    offer = offer
                )
            )
        }.also {
            matchedOfferPublisher.publish(it)
        }
}


data class MatchedOfferTransaction(val transaction: Transaction, val offer: Offer)

sealed class MatchOfferResult {
    data class Matched(
        val matchedOfferTransaction: MatchedOfferTransaction
    ) : MatchOfferResult()

}

interface MatchedOfferPublisher {
    fun publish(matched: MatchOfferResult.Matched)

}

interface OfferMatcher {
    fun match(transaction: Transaction, merchant: Merchant): Offer
}
