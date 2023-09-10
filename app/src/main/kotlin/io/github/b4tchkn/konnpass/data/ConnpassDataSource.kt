package io.github.b4tchkn.konnpass.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.github.b4tchkn.konnpass.model.EventResponseModel
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface ConnpassDataSource {
    @GET("event")
    suspend fun getEvents(
        /**
         * イベントID
         * イベント毎に割り当てられた番号で検索します。複数指定可能です
         */
        @Query("event_id") eventId: Int?,
        /**
         * キーワード (AND)
         * イベントのタイトル、キャッチ、概要、住所をAND条件部分一致で検索します。複数指定可能です
         */
        @Query("keyword") keyword: String?,
        /**
         * イベント開催年月
         * 指定した年月に開催されているイベントを検索します。複数指定可能です
         * yyyymm
         * (例) 201204
         */
        @Query("ym") yearMonth: Int?,
        /**
         * イベント開催年月日
         *  指定した年月日に開催されているイベントを検索します。複数指定可能です
         *  yyyymmdd
         * (例) 20120406
         */
        @Query("ymd") yearMonthDay: Int?,
        /**
         * 参加者のニックネーム
         * 指定したニックネームのユーザが参加しているイベントを検索します。複数指定可能です
         */
        @Query("nickname") nickname: String?,
        /**
         * 管理者のニックネーム
         * 指定したニックネームのユーザが管理しているイベントを検索します。複数指定可能です
         */
        @Query("owner_nickname") ownerNickname: String?,
        /**
         * グループID
         * グループ 毎に割り当てられた番号で、ひもづいたイベントを検索します。複数指定可能です
         */
        @Query("series_id") seriesId: Int?,
        /**
         * 検索の開始位置
         * 検索結果の何件目から出力するかを指定します
         * (初期値: 1)
         */
        @Query("start") start: Int?,
        /**
         * 検索結果の表示順
         * 検索結果の表示順を、更新日時順、開催日時順、新着順で指定します
         * 1: 更新日時順
         * 2: 開催日時順
         * 3: 新着順
         * (初期値: 1)
         */
        @Query("order") order: Int?,
        /**
         * 取得件数
         * 検索結果の最大出力データ数を指定します
         * 初期値: 10、最小値：1、最大値：100
         */
        @Query("count") count: Int?,
    ): EventResponseModel

    companion object {
        private const val BASE_URL = "https://connpass.com/api/v1/"

        fun create(
            okHttpClient: OkHttpClient,
        ): ConnpassDataSource {
            val contentType = "application/json".toMediaType()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(Json.asConverterFactory(contentType))
                .build()
                .create(ConnpassDataSource::class.java)
        }
    }
}
