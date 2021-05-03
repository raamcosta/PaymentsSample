package racosta.samples.core.repository.network

import racosta.samples.core.repository.network.dtos.PaymentDto
import racosta.samples.core.repository.network.dtos.RefundDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PaymentsService {

    @GET("payment")
    suspend fun getPayments(): Response<List<PaymentDto>>

    @POST("payment")
    suspend fun postPayment(@Body payment: PaymentDto)

    @GET("refund")
    suspend fun getRefunds(): Response<List<RefundDto>>

    @POST("refund")
    suspend fun postRefund(@Body refund: RefundDto)
}