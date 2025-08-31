package com.iftikar.mediadmin.data.repository

import android.util.Log
import com.iftikar.mediadmin.data.remote.ApiOperation
import com.iftikar.mediadmin.data.remote.ApiService
import com.iftikar.mediadmin.data.remote.model.InsertProductResponseDto
import com.iftikar.mediadmin.data.remote.model.ProductRequestDto
import com.iftikar.mediadmin.domain.model.Product
import com.iftikar.mediadmin.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.resume

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProductRepository {
    override fun getAllProducts(): Flow<ApiOperation<List<Product>>> = flow {
        try {
            val result = suspendCancellableCoroutine { continuation ->
                val call = apiService.getProducts()

                call.enqueue(object : Callback<List<Product>> {
                    override fun onResponse(
                        call: Call<List<Product>>,
                        response: Response<List<Product>>
                    ) {
                        if (response.isSuccessful) {
                            val products = response.body()
                            if (products != null) {
                                continuation.resume(ApiOperation.Success(products))
                            } else {
                                continuation.resume(ApiOperation.Failure(NullPointerException("Products not found")))
                            }
                        } else {
                            continuation.resume(ApiOperation.Failure(HttpException(response)))
                        }
                    }

                    override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                        val exception = when (t) {
                            is IOException -> IOException("Please check your internet connection")
                            else -> Exception(t.message ?: "Unknown error")
                        }
                        continuation.resume(ApiOperation.Failure(exception))
                    }
                })

                // Cancel the call if the coroutine is cancelled
                continuation.invokeOnCancellation {
                    call.cancel()
                }
            }

            emit(result)
        } catch (e: Exception) {
            emit(ApiOperation.Failure(e))
        }
    }
    // I am never using Call again

    override fun getSpecificProduct(productId: String): Flow<ApiOperation<Product>> = flow {
        try {
            val response = apiService.getSpecificProduct(productId)
            if (response.isSuccessful) {
                val body = response.body()?.firstOrNull()
                if (body != null) {
                    emit(ApiOperation.Success(body))
                } else {
                    emit(ApiOperation.Failure(NullPointerException("Product not found")))
                }
            } else {
                emit(ApiOperation.Failure(Exception("Request not successful")))
            }
        } catch (e: IOException) {
            Log.e("Product-Error", "getSpecificProduct: ${e.message}")
            emit(ApiOperation.Failure(Exception("Please check internet connection")))
        } catch (e: Exception) {
            Log.e("Product-Error", "getSpecificProduct: ${e.message}")
            emit(ApiOperation.Failure(Exception(e.message ?: "Please try again!")))
        }
    }

    override fun insertProduct(product: ProductRequestDto): Flow<ApiOperation<InsertProductResponseDto>> = flow {
        try {
            val response = apiService.insertProduct(
                name = product.name,
                price = product.price,
                category = product.category,
                stock = product.stock,
            )

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(ApiOperation.Success(body))
                } else {
                    emit(ApiOperation.Failure(NullPointerException("Unknown Response")))
                }
            } else {
                emit(ApiOperation.Failure(Exception("Product insertion failed!")))
            }
        } catch (e: IOException) {
            emit(ApiOperation.Failure(Exception("Please check internet connection")))
        } catch (e: Exception) {
            Log.e("Insert Product", e.message ?: "Error")
            emit(ApiOperation.Failure(Exception("Some error occurred!")))
        }
    }
}

























