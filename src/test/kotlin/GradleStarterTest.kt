package org.example

import com.hexagonkt.http.client.Client
import com.hexagonkt.http.client.ahc.AhcAdapter
import org.junit.After
import org.junit.Before
import org.junit.Test

class GradleStarterTest {

    private val client by lazy {
        Client(AhcAdapter(), "http://localhost:${server.runtimePort}")
    }

    @Before fun startup() {
        main()
    }

    @After fun shutdown() {
        server.stop()
    }

    @Test fun `HTTP request returns proper status, headers and body`() {
        val response = client.get("/text")
        val content = response.body

        assert(response.headers["Date"] != null)
        assert(response.headers["Server"] != null)
        assert(response.headers["Transfer-Encoding"] != null)
        assert(response.headers["Content-Type"]?.first() == "text/plain")

        assert("Hello, World!" == content)
    }
}
