package panthro.learnings.kafkaspring.infrastructure.consumer

import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import panthro.learnings.kafkaspring.domain.Transaction
import panthro.learnings.kafkaspring.domain.cashback.RegisterMatchedOfferUseCase
import panthro.learnings.kafkaspring.domain.merchantmatching.MatchMerchantUseCase
import panthro.learnings.kafkaspring.domain.merchantmatching.MatchedMerchantTransaction
import panthro.learnings.kafkaspring.domain.offermatching.MatchOfferUseCase
import panthro.learnings.kafkaspring.domain.offermatching.MatchedOfferTransaction

val logger = LoggerFactory.getLogger("consumers")!!

@Component
class TransactionConsumer(
    val matchMerchantUseCase: MatchMerchantUseCase
) {

    @KafkaListener(
        id = "transaction_1",
        topics = ["\${topic.transaction.original}"]
    )
    fun consume(transaction: Transaction) {
        logger.info("<< $transaction")
            .also { matchMerchantUseCase.invoke(transaction) }
    }

}

@Component
class MatchedMerchantConsumer(
    val matchOfferUseCase: MatchOfferUseCase
) {
    @KafkaListener(
        id = "merchant_1",
        topics = ["\${topic.transaction.merchant}"])
    fun consume(matchedMerchantTransaction: MatchedMerchantTransaction) {
        logger.info("<< $matchedMerchantTransaction")
            .also { matchOfferUseCase.invoke(matchedMerchantTransaction) }
    }

}

@Component
class MatchedOfferConsumer(
    val registerMatchedOfferUseCase: RegisterMatchedOfferUseCase
) {

    @KafkaListener(
        id = "offers_1",
        topics = ["\${topic.transaction.offer}"]
    )
    fun consume(matchedOfferTransaction: MatchedOfferTransaction) {
        logger.info("<< $matchedOfferTransaction")
            .also { registerMatchedOfferUseCase.invoke(matchedOfferTransaction) }
    }

}
