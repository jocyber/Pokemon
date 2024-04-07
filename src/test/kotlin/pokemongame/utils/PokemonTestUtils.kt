package pokemongame.utils

import com.lehaine.littlekt.Context
import io.mockk.mockk
import io.mockk.mockkClass
import org.koin.core.context.stopKoin
import org.koin.test.mock.MockProvider
import pokemongame.koin.startKoinApp

object PokemonTestUtils {
    fun initKoin() {
        MockProvider.register { clazz -> mockkClass(clazz) }
        val mockContext = mockk<Context>()

        startKoinApp(mockContext)
    }

    fun deinitKoin() = stopKoin()
}
