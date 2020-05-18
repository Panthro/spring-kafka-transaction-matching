package panthro.learnings.kafkaspring.domain.merchantmatching

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import panthro.learnings.kafkaspring.domain.Merchant
import panthro.learnings.kafkaspring.domain.Transaction
import panthro.learnings.kafkaspring.uuid

internal class MatchMerchantUseCaseTest {

    val merchantMatcher = mockk<MerchantMatcher>()
    val matchedMerchantPublisher = mockk<MatchedMerchantPublisher>(relaxed = true)
    val useCase = MatchMerchantUseCase(merchantMatcher, matchedMerchantPublisher)

    @Test
    fun `should match merchant`() {

        val transaction = Transaction(id = uuid())
        val merchant = Merchant(id = uuid())
        val expected = MatchMerchantResult.Matched(MatchedMerchantTransaction(transaction = transaction, merchant = merchant))

        every { merchantMatcher.match(transaction = transaction) } returns merchant

        val result = useCase.invoke(transaction)


        assertThat(result).isEqualTo(expected)

        verify { matchedMerchantPublisher.publish(expected) }
    }
}
