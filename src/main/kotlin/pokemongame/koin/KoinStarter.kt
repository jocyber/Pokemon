package pokemongame.koin

import com.lehaine.littlekt.Context
import org.koin.core.context.startKoin

fun startKoinApp(context: Context) = startKoin {
    modules(
        battleSceneModule(context),
        pokemonTexturesModule(context),
        audioStreamsModule(context),
        audioClipsModule(context),
        animationTexturesModule(context),
    )
}
