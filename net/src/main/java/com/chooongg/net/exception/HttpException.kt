package com.chooongg.net.exception

import com.chooongg.basic.utils.NetworkUtils
import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

class HttpException : RuntimeException {

    object Converter {
        private var converter: HttpErrorConverter = DefaultErrorConverter()

        fun convert(type: Type) = converter.convert(type)

        fun changeConverter(converter: HttpErrorConverter) {
            this.converter = converter
        }
    }

    var type: Type
        private set(value) {
            field = value
            if (code == null) code = field.value.toString()
            if (mMessage == null) mMessage = Converter.convert(field)
        }

    var code: String? = null
        private set

    private var mMessage: String? = null

    override val message: String? get() = mMessage

    constructor() : super() {
        this.type = Type.UNKNOWN
    }

    constructor(type: Type) : super() {
        this.type = type
    }

    constructor(message: String) : super(message) {
        this.mMessage = message
        this.type = Type.CUSTOM
    }

    constructor(code: String, message: String) : super(message) {
        this.code = code
        this.mMessage = message
        this.type = Type.CUSTOM
    }

    constructor(e: Throwable) : super(e.message, e) {
        if (e is HttpException) {
            this.type = e.type
            this.code = e.code
            this.mMessage = e.mMessage
        } else {
            this.type = when (e) {
                is ConnectException,
                is UnknownHostException -> Type.CONNECT
                is NullPointerException -> Type.EMPTY
                is SSLHandshakeException -> Type.SSL
                is SocketTimeoutException -> Type.TIMEOUT
                is retrofit2.HttpException -> {
                    var tempType = Type.HTTP
                    val values = Type.values()
                    for (i in values.indices) {
                        if (values[i].value == e.code()) {
                            tempType = values[i]
                            break
                        }
                    }
                    this.code = e.code().toString()
                    this.mMessage = e.message()
                    tempType
                }
                is JSONException,
                is JsonIOException,
                is JsonParseException,
                is JsonSyntaxException,
                is java.text.ParseException,
                is android.net.ParseException,
                is androidx.core.net.ParseException,
                is android.util.MalformedJsonException,
                is com.google.gson.stream.MalformedJsonException -> Type.PARSE
                else -> if (NetworkUtils.isNetworkConnected()) Type.UNKNOWN else Type.NETWORK
            }
        }
    }

    enum class Type(val value: Int) {
        CUSTOM(-1),
        UNKNOWN(-2),
        NETWORK(-3),
        TIMEOUT(-4),
        CONNECT(-5),
        SSL(-6),
        EMPTY(-7),
        PARSE(-8),
        HTTP(-9),
        HTTP302(302),
        HTTP400(400),
        HTTP401(401),
        HTTP403(403),
        HTTP404(404),
        HTTP405(405),
        HTTP406(406),
        HTTP407(407),
        HTTP408(408),
        HTTP409(409),
        HTTP410(410),
        HTTP411(411),
        HTTP412(412),
        HTTP413(413),
        HTTP414(414),
        HTTP415(415),
        HTTP416(416),
        HTTP417(417),
        HTTP500(500),
        HTTP501(501),
        HTTP502(502),
        HTTP503(503),
        HTTP504(504),
        HTTP505(505);
    }
}