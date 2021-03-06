package com.elapp.booque.utils.network

class NetworkState(val status: Status) {
    companion object {
        val LOADED: NetworkState = NetworkState(Status.SUCCESS)
        val LOADING: NetworkState = NetworkState(Status.RUNNING)
        val ERROR: NetworkState = NetworkState(Status.FAILED)
        val ENDOFLIST: NetworkState = NetworkState(Status.FAILED)
        val USERNOTFOUND: NetworkState = NetworkState(Status.UNAUTHORISED)
        val FAILEDTOADD: NetworkState = NetworkState(Status.FAILED)
        val EMPTYDATA: NetworkState = NetworkState(Status.FAILED)
        val EXPIRETOKEN: NetworkState = NetworkState(Status.EXPIRETOKEN)
        val FORBIDDEN: NetworkState = NetworkState(Status.FORBIDDEN)
        val UNKNOWN: NetworkState = NetworkState(Status.UNKNOWN)
        val BAD_GATEAWAY: NetworkState = NetworkState(Status.BAD_GATEAWAY)
        val NOT_FOUND: NetworkState = NetworkState(Status.NOT_FOUND)
    }
}