package me.ks.chan.pica.plus.util.androidx.navigation

import org.junit.Assert.assertEquals
import org.junit.Test

class RouteTest {

    @Test
    fun testRouteArg() {
        val arg = "arg"
        val route = "route/{$arg}"
        val argsRoute = route.args(arg to "value")

        assertEquals(
            "route/value",
            argsRoute
        )
    }

}