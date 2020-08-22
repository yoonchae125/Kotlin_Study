package com.chaeyoon.rxjavastudy

import android.util.Log
import java.math.BigDecimal
import io.reactivex.rxjava3.core.Observable;
class RxGroceries {
    fun purchase(productName:String, quantity: Int):Observable<BigDecimal>{
        return Observable.fromCallable {
            doPurchase(productName, quantity)
        }
    }
    fun doPurchase(productName:String, quantity: Int):BigDecimal{
        Log.d("codbs", "Purchasing $quantity $productName")
        // 구매 로직
        Log.d("codbs", "Done $quantity $productName")
        val priceForProduct = 1000
        return priceForProduct.toBigDecimal()
    }
}