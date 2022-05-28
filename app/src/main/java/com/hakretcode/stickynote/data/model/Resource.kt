package com.hakretcode.stickynote.data.model

sealed class Resource<T> {
    class Loading<T> : Resource<T>()
    class Empty<T> : Resource<T>()
    class Success<T>(val data: T? = null): Resource<T>()
    class Error<T>(val message: String): Resource<T>()
    class Complete<T> : Resource<T>()
}
