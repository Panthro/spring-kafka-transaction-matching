package panthro.learnings.kafkaspring.domain.offermatching

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import panthro.learnings.kafkaspring.domain.Offer
import panthro.learnings.kafkaspring.domain.Merchant
import panthro.learnings.kafkaspring.domain.Transaction
import panthro.learnings.kafkaspring.domain.merchantmatching.MatchedMerchantTransaction
import panthro.learnings.kafkaspring.uuid


internal class MatchOfferUseCaseTest {
    val offerMatcher = mockk<OfferMatcher>()
    val matchedOfferPublisher = mockk<MatchedOfferPublisher>(relaxed = true)
    val useCase = MatchOfferUseCase(offerMatcher = offerMatcher, matchedOfferPublisher = matchedOfferPublisher)

    @Test
    fun `should match offer`() {

        val transaction = Transaction(id = uuid())
        val merchant = Merchant(id = uuid())
        val offer = Offer(id = uuid(), merchant = merchant)
        every { offerMatcher.match(transaction, merchant) } returns offer
        val expected = MatchOfferResult.Matched(MatchedOfferTransaction(transaction = transaction, offer = offer))

        val result = useCase.invoke(MatchedMerchantTransaction(transaction, merchant))

        assertThat(result).isEqualTo(expected)
        verify { matchedOfferPublisher.publish(expected) }

    }


}
