package com.dingstock.net.convert

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import okhttp3.ResponseBody
import okio.ByteString
import okio.ByteString.Companion.decodeHex
import retrofit2.Converter
import java.io.IOException

class MoshiResponseBodyConverter<T>(private val adapter: JsonAdapter<T>) : Converter<ResponseBody, T?> {

    companion object {
        private val UTF8_BOM: ByteString = "EFBBBF".decodeHex()
        private val options = JsonReader.Options.of("errorCode", "errorMsg", "data")
    }

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T? {
        val source = value.source()
        value.use { _ ->
            if (source.rangeEquals(0, UTF8_BOM)) {
                source.skip(UTF8_BOM.size.toLong())
            }
            val reader = JsonReader.of(source)
            var errorCode: Int = 0
            var errorMsg: String = "success"
            var data: T? = null
            try {
                reader.beginObject()
                while (reader.hasNext()) {
                    when (reader.selectName(options)) {
                        0 -> errorCode = reader.nextInt()
                        1 -> errorMsg = reader.nextString()
                        2 -> data = adapter.fromJson(reader)
                        else -> {
                            reader.skipName()
                            reader.skipValue()
                        }
                    }
                }
                reader.endObject();
                if (reader.peek() != JsonReader.Token.END_DOCUMENT) {
                    throw JsonDataException("JSON document was not fully consumed.")
                }
                reader.close();
            } catch (e: Exception) { //                Logger.e("Request Error ${e.localizedMessage}")
                //                throw e
            }
            if (errorCode != 0) {
                throw IllegalArgumentException()
            }
            return data
        }
    }
}
