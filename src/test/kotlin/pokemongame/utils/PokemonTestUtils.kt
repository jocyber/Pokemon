package pokemongame.utils

import com.lehaine.littlekt.Context
import io.mockk.mockk
import io.mockk.mockkClass
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.mock.MockProvider
import pokemongame.koin.animationTexturesModule
import pokemongame.koin.audioClipsModule
import pokemongame.koin.audioStreamsModule
import pokemongame.koin.battleSceneModule
import pokemongame.koin.pokemonTexturesModule

object PokemonTestUtils {
    fun initKoin() {
        MockProvider.register { clazz -> mockkClass(clazz) }
        val mockContext = mockk<Context>()

        startKoin {
            modules(
                battleSceneModule(mockContext),
                pokemonTexturesModule(mockContext),
                audioStreamsModule(mockContext),
                audioClipsModule(mockContext),
                animationTexturesModule(mockContext),
            )
        }
    }

    fun deinitKoin() = stopKoin()
}
