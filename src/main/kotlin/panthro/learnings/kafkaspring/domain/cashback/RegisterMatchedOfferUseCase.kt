package panthro.learnings.kafkaspring.domain.cashback

import org.springframework.stereotype.Component
import panthro.learnings.kafkaspring.domain.offermatching.MatchedOfferTransaction
import java.util.UUID

@Component
class RegisterMatchedOfferUseCase {
    fun invoke(matchedOfferTransaction: MatchedOfferTransaction): RegisterOfferTransactionResult {
        println("matchedOfferTransaction=$matchedOfferTransaction")
        return RegisterOfferTransactionResult.Registered(transactionId = matchedOfferTransaction.transaction.id)
    }
}


sealed class RegisterOfferTransactionResult() {

    data class Registered(val transactionId: UUID) : RegisterOfferTransactionResult()

}
