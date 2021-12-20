package com.example.gp3.manager

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.android.billingclient.api.*
import com.example.gp3.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.ArrayList

/**
 *  @ProjectName:   Practise
 *      @Package:   com.example.gp3.manager
 *    @ClassName:   BillingManager
 *  @Description:   Billing Client 管理类
 *       @Author:   毛毛虫
 *   @CreateDate:   2021/11/24 4:19 下午
 *   @UpdateUser:   更新者
 *   @UpdateDate:   2021/11/24 4:19 下午
 * @UpdateRemark:   更新说明
 *      @Version:   1.0
 */
object BillingManager {
    private val TAG = BillingManager::class.java.simpleName
    fun log(content: String) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, content)
        }
    }

    //是否需要消耗掉产品
    var comsume = false
        set

    //产品类型
    var skuType = BillingClient.SkuType.SUBS //订阅
        set
    const val ITEM_TYPE_INAPP = BillingClient.SkuType.INAPP
    const val ITEM_TYPE_SUBS = BillingClient.SkuType.SUBS

    //设置的一次性商品ID
    private val inAppIds = arrayListOf<String>()
    fun setInAppIds(list: List<String>) {
        inAppIds.addAll(list)
    }

    //设置的订阅商品ID
    private val subsIds = arrayListOf<String>()
    fun setSubsIds(list: List<String>) {
        subsIds.addAll(list)
    }

    private val skuDetails = hashMapOf<String, SkuDetails>()
    private val oldSkus = arrayListOf<Purchase>()
    private val autoRenewList = arrayListOf<Boolean>()

    private val productList = arrayListOf<String>() //已交易一次性商品ID
    private val skuList = arrayListOf<String>()     //已交易订阅商品ID

    private val inAppPurchases = arrayListOf<Purchase>()
    private val subsPurchases = arrayListOf<Purchase>()
    private var currentPurchase: Purchase? = null

    private lateinit var billingClient: BillingClient

    var IsNeedToReSet = true


    //交易成功 响应
    var purchaseSuccess: () -> Unit = {}

    //取消交易 响应
    var purchaseCancel: () -> Unit = {}

    //交易失败 响应
    var purchaseError: () -> Unit = {}

    /**
     * 初始化
     */
    fun initBilling(
        context: Context,
        updateListener: PurchasesUpdatedListener = purchasesUpdatedListener
    ) {
        billingClient = BillingClient.newBuilder(context)
            .setListener(updateListener)
            .enablePendingPurchases()
            .build()
        connect()
    }

    /**
     * 连接
     */
    private fun connect(stateListener: BillingClientStateListener = this.stateListener) {
        if (billingClient.isReady) return
        billingClient.startConnection(stateListener)
    }

    /**
     * 连接状态监听
     */
    private val stateListener = object : BillingClientStateListener {
        override fun onBillingServiceDisconnected() {
            log("onBillingServiceDisconnected")
            if (IsNeedToReSet) {
                IsNeedToReSet = false
                connect()
            }
        }

        override fun onBillingSetupFinished(p0: BillingResult) {
            log("onBillingSetupFinished")
            log("启动订阅服务 启动状态：" + p0.getResponseCode())
            log("启动订阅服务 启动状态：" + p0.getDebugMessage())
            if (p0.responseCode == BillingClient.BillingResponseCode.OK) {
                log("onBillingSetupReady")
                skuDetails.clear();
                querySkuDetailsAsync(ITEM_TYPE_INAPP)
                querySkuDetailsAsync(ITEM_TYPE_SUBS)
                queryPurchasesAsync(ITEM_TYPE_INAPP)
                if (isSubscriptionSupported()) {
                    queryPurchasesAsync(ITEM_TYPE_SUBS)
                }
            }
        }
    }

    /**
     * 交易状态监听
     */
    private val purchasesUpdatedListener = PurchasesUpdatedListener { billingResult, purchases ->
        log("onPurchasesUpdated,responseCode:" + billingResult.responseCode)
        log("onPurchasesUpdated,responseCode:" + billingResult.debugMessage)
        when (billingResult.responseCode) {
            BillingClient.BillingResponseCode.OK -> {
                purchases?.let {
                    if (!TextUtils.isEmpty(skuType)) {
                        processPurchases(purchases, skuType)
                        skuType = ""
                    }
                }
            }
            BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED -> {
                purchaseSuccess()
                if (!TextUtils.isEmpty(skuType)) {
                    queryPurchasesAsync(skuType)
                    skuType = ""
                }
            }
            BillingClient.BillingResponseCode.SERVICE_DISCONNECTED -> {
                purchaseError
                connect()
            }
            //其他情况，按照购买失败处理
            BillingClient.BillingResponseCode.USER_CANCELED,
            BillingClient.BillingResponseCode.FEATURE_NOT_SUPPORTED,
            BillingClient.BillingResponseCode.SERVICE_TIMEOUT,
            BillingClient.BillingResponseCode.SERVICE_UNAVAILABLE,
            BillingClient.BillingResponseCode.BILLING_UNAVAILABLE,
            BillingClient.BillingResponseCode.ITEM_UNAVAILABLE,
            BillingClient.BillingResponseCode.DEVELOPER_ERROR,
            BillingClient.BillingResponseCode.ERROR,
            BillingClient.BillingResponseCode.ITEM_NOT_OWNED -> {
                purchaseError()
            }
        }

    }

    /**
     * 处理交易结果
     */
    private fun processPurchases(purchasesResult: List<Purchase?>?, type: String) {
        val validPurchases: MutableList<Purchase> = ArrayList(
            purchasesResult?.size ?: 0
        )
        purchasesResult ?: let {
            purchaseError()
            return
        }
        for (purchase in purchasesResult) {
            log("purchase:$purchase \n skus:${purchase?.skus}")
            if (purchase?.purchaseState == Purchase.PurchaseState.PURCHASED) {
                val isSignatureValid: Boolean = isSignatureValid(purchase)
                if (isSignatureValid) {
                    validPurchases.add(purchase)
                }
            } else {
                log(
                    "Received a error purchase of SKU: " + purchase?.skus
                )
            }
        }
        if (validPurchases.size > 0) {
            acknowledgeNonConsumablePurchasesAsync(validPurchases, type)
        } else {
            purchaseError()
        }
    }

    /**
     * 确认购买
     */
    private fun acknowledgeNonConsumablePurchasesAsync(
        nonConsumables: List<Purchase>,
        type: String
    ) {
        if (type == ITEM_TYPE_SUBS) {
            oldSkus.clear()
            autoRenewList.clear()

            skuList.clear()
            subsPurchases.clear()
        } else {
            productList.clear()
            inAppPurchases.clear()
        }
        for (purchase in nonConsumables) {
            if (purchase.isAcknowledged) {
                if (type.equals(ITEM_TYPE_SUBS)) {
                    autoRenewList.add(purchase.isAutoRenewing)
                    if (purchase.isAutoRenewing) {
                        oldSkus.add(purchase)
                    }
                    skuList.add(purchase.skus[0])
                    subsPurchases.add(purchase)
                } else {
                    productList.add(purchase.skus[0])
                    inAppPurchases.add(purchase)
                    if (comsume) {  //消耗掉一次性产品
                        handleConsumablePurchasesAsync(purchase);
                    }
                }
                purchaseSuccess()
            } else {
                val params =
                    AcknowledgePurchaseParams.newBuilder().setPurchaseToken(purchase.purchaseToken)
                        .build()
                billingClient.acknowledgePurchase(
                    params,
                    AcknowledgePurchaseResponseListener { billingResult: BillingResult ->
                        log("billingResult code:" + billingResult.responseCode)
                        log(
                            """
                        billingResult message:${billingResult.debugMessage}
                        $billingResult
                        """.trimIndent()
                        )
                        if (BillingClient.BillingResponseCode.OK == billingResult.responseCode) {
                            if (inAppIds.contains(purchase.skus[0])) {
                                BillingManager.productList.add(purchase.skus[0])
                                BillingManager.inAppPurchases.add(purchase)
                            } else if (subsIds.contains(purchase.skus[0])) {
                                autoRenewList.add(purchase.isAutoRenewing)
                                if (purchase.isAutoRenewing) {
                                    oldSkus.add(purchase)
                                }
                                BillingManager.skuList.add(purchase.skus[0])
                                BillingManager.subsPurchases.add(purchase)
                            }
                            purchaseSuccess()
                        } else {
                            purchaseError()
                        }
                    })
            }
        }
    }

    /**
     * 消耗商品
     *
     * @param purchase
     */
    private fun handleConsumablePurchasesAsync(purchase: Purchase) {
        val params = ConsumeParams.newBuilder().setPurchaseToken(purchase.purchaseToken).build()
        billingClient.consumeAsync(params,
            ConsumeResponseListener { billingResult: BillingResult?, purchaseToken: String? ->
                BillingManager.productList.remove(purchase.skus[0])
            })
    }

    /**
     * 查询已经购买或者订阅的商品
     */
    private fun queryPurchasesAsync(type: String) {
        billingClient.queryPurchasesAsync(type,
            PurchasesResponseListener { billingResult: BillingResult, list: List<Purchase?>? ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    processPurchases(list, type)
                } else if (billingResult.responseCode == BillingClient.BillingResponseCode.SERVICE_DISCONNECTED) {
                    connect()
                } else {
                    skuList.clear()
                    productList.clear()
                }
            })
    }

    /**
     * 查询商品列表
     *
     * @param type
     */
    private fun querySkuDetailsAsync(type: String) {
        val productIds = arrayListOf<String>()
        if (type == ITEM_TYPE_SUBS) {
            productIds.addAll(subsIds)
        } else {
            productIds.addAll(inAppIds)
        }
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(productIds).setType(type)
        billingClient.querySkuDetailsAsync(params.build(),
            SkuDetailsResponseListener { billingResult: BillingResult, skuDetailsList: List<SkuDetails>? ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    skuDetailsList?.let {
                        for (skuDetail in it) {
                            if (!skuDetails.containsKey(skuDetail.sku)) {
                                skuDetails.put(skuDetail.sku, skuDetail)
                            }
                        }
                    }

                }
            })
    }

    /**
     * 订阅或者购买商品
     *
     * @param activity
     * @param skuId
     * @return
     */
    fun launchBillingFlow(activity: Activity, skuId: String, type: String): BillingResult? {
        skuType = type
        val skuDetails = skuDetails[skuId]
        return if (skuDetails != null) {
            val builder = BillingFlowParams
                .newBuilder()
                .setSkuDetails(skuDetails)
            if (autoRenewList.contains(true) && skuType == ITEM_TYPE_SUBS) {
                builder.setSubscriptionUpdateParams(
                    BillingFlowParams.SubscriptionUpdateParams.newBuilder()
                        .setOldSkuPurchaseToken(oldSkus[0].purchaseToken)
                        .setReplaceSkusProrationMode(BillingFlowParams.ProrationMode.IMMEDIATE_AND_CHARGE_FULL_PRICE)
                        .build()
                )
            }
            val billingFlowParams = builder
                .build()
            billingClient.launchBillingFlow(activity, billingFlowParams)
        } else {
            null
        }
    }

    /**
     * 判断是否支出订阅
     *
     * @return
     */
    private fun isSubscriptionSupported(): Boolean {
        var succeeded = false
        if (billingClient != null) {
            val billingResult: BillingResult =
                billingClient.isFeatureSupported(BillingClient.FeatureType.SUBSCRIPTIONS)
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                succeeded = true
            } else if (billingResult.responseCode == BillingClient.BillingResponseCode.SERVICE_DISCONNECTED) {
                connect()
            }
        }
        return succeeded
    }

    /**
     * 验证购买
     *
     * @return
     */
    private fun isSignatureValid(purchase: Purchase): Boolean {
        return Security.getInstance().verifyPurchase(
            Security.BASE_64_ENCODED_PUBLIC_KEY,
            purchase.originalJson,
            purchase.signature
        )
    }

    /**
     * 是否是VIP
     */
    fun hadVip(): Boolean {
        inAppIds.forEach {
            if (productList.contains(it)) return true
        }
        subsIds.forEach {
            if (skuList.contains(it)) return true
        }
        return false
    }

    /**
     * 获得订阅的产品
     */
    fun getProduct(): String? {
        if (productList.size > 0 && inAppIds.size > 0) {
            for (purchase in inAppPurchases) {
                if (inAppIds.contains(purchase.skus[0])) {
                    currentPurchase = purchase
                    return purchase.skus[0]
                }
            }
        } else if (skuList.size > 0 && subsIds.size > 0) {
            for (purchase in subsPurchases) {
                if (subsIds.contains(purchase.skus[0])) {
                    currentPurchase = purchase
                    return purchase.skus[0]
                }
            }
        }
        return null
    }

    /**
     * 设置BASE_64_ENCODED_PUBLIC_KEY
     */
    fun setEncodeKey(key: String) {
        Security.BASE_64_ENCODED_PUBLIC_KEY = key
    }

}

