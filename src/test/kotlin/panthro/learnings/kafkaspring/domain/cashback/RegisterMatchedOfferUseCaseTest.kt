package panthro.learnings.kafkaspring.domain.cashback

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import panthro.learnings.kafkaspring.domain.Merchant
import panthro.learnings.kafkaspring.domain.Offer
import panthro.learnings.kafkaspring.domain.Transaction
import panthro.learnings.kafkaspring.domain.cashback.RegisterOfferTransactionResult.Registered
import panthro.learnings.kafkaspring.domain.offermatching.MatchedOfferTransaction
import panthro.learnings.kafkaspring.uuid
import java.util.UUID

internal class RegisterMatchedOfferUseCaseTest {


    @Test
    fun `should publish transaction to partner`() {

        val useCase = RegisterMatchedOfferUseCase()
        val transaction = Transaction(id= uuid())
        val offer = Offer(id= uuid(), merchant = Merchant(id = uuid()))
        val matchedOfferTransaction = MatchedOfferTransaction(
            transaction=transaction,
            offer = offer
        )

        val result = useCase.invoke(matchedOfferTransaction)

        assertThat(result).isEqualTo(Registered(transactionId = matchedOfferTransaction.transaction.id))


    }
}
