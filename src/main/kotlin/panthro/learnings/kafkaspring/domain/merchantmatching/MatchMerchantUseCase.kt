package panthro.learnings.kafkaspring.domain.merchantmatching

import org.springframework.stereotype.Component
import panthro.learnings.kafkaspring.domain.Merchant
import panthro.learnings.kafkaspring.domain.Transaction

@Component
class MatchMerchantUseCase(
    private val merchantMatcher: MerchantMatcher,
    private val matchedMerchantPublisher: MatchedMerchantPublisher
) {
    fun invoke(transaction: Transaction): MatchMerchantResult =
        merchantMatcher.match(transaction = transaction)
            .let {
                MatchMerchantResult.Matched(
                    MatchedMerchantTransaction(
                        transaction = transaction,
                        merchant = it
                    )
                )
            }
            .also {
                matchedMerchantPublisher.publish(it)
            }
}

sealed class MatchMerchantResult {
    data class Matched(val matchedMerchantTransaction: MatchedMerchantTransaction) : MatchMerchantResult()
}

data class MatchedMerchantTransaction(
    val transaction: Transaction,
    val merchant: Merchant
)

interface MatchedMerchantPublisher {
    fun publish(result: MatchMerchantResult.Matched)
}


interface MerchantMatcher {
    fun match(transaction: Transaction): Merchant
}
