package pokemongame.koin

import com.lehaine.littlekt.Context
import com.lehaine.littlekt.file.vfs.readTexture
import kotlinx.coroutines.runBlocking
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun animationTexturesModule(context: Context) = module {
    single(named(HIT_EFFECT)) {
        runBlocking { context.resourcesVfs["assets/sprites/moves/tackle_effect.png"].readTexture() }
    }
}

const val HIT_EFFECT = "HitEffect"
