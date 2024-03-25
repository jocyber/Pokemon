package pokemongame.koin

import com.lehaine.littlekt.Context
import com.lehaine.littlekt.graphics.Texture
import com.lehaine.littlekt.graphics.g2d.Animation
import com.lehaine.littlekt.graphics.g2d.SpriteBatch
import com.lehaine.littlekt.graphics.slice
import com.lehaine.littlekt.util.seconds
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val TACKLE_HIT = "TackleHit"

fun animationsModule(context: Context) = module {
    single { SpriteBatch(context) }

    single(named(TACKLE_HIT)) {
        val hitEffect: Texture by inject(named(HIT_EFFECT))

        Animation(
            frames = listOf(hitEffect.slice()),
            frameIndices = listOf(0),
            frameTimes = listOf(0.125f.seconds),
        )
    }
}
