package pokemongame.animations.base

import com.lehaine.littlekt.util.seconds
import kotlin.time.Duration
import pokemongame.animations.PokemonAnimation

class SpriteAnimation(
    private val updateStrategy: (dt: Duration) -> Unit,
    private val isDoneStrategy: (totalTime: Float) -> Boolean,
) : PokemonAnimation {
    private var totalTime = 0f

    override fun update(dt: Duration) {
        totalTime += dt.seconds
        updateStrategy(dt)
    }

    override fun isDone(): Boolean = isDoneStrategy(totalTime)
}
