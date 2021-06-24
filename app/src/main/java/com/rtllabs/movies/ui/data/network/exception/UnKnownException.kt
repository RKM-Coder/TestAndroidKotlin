package com.rtllabs.movies.ui.data.network.exception

import java.io.IOException

class UnKnownException:IOException() {

    override val message: String?
        get() = "Unknown Exception"
}