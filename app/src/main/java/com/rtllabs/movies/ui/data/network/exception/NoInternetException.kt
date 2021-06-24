package com.rtllabs.movies.ui.data.network.exception

import java.io.IOException

class NoInternetException:IOException() {
    override val message: String?
        get() = "No Internet Connection"
}