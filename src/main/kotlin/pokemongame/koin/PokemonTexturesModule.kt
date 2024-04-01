package pokemongame.koin

import com.lehaine.littlekt.Context
import com.lehaine.littlekt.file.vfs.readTexture
import kotlinx.coroutines.runBlocking
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * A module for injecting Pokemon textures. Note, the named injection must be the same name as the
 * data object of the corresponding Pokemon.
 */
fun pokemonTexturesModule(context: Context) = module {
    single(named(PRIMEAPE)) {
        runBlocking { context.resourcesVfs["assets/sprites/pokemon/primeape.png"].readTexture() }
    }

    single(named(ZIGZAGOON)) {
        runBlocking {
            context.resourcesVfs["assets/sprites/pokemon/front/zigzagoon.png"].readTexture()
        }
    }
}

const val PRIMEAPE = "Primeape"
const val ZIGZAGOON = "Zigzagoon"
