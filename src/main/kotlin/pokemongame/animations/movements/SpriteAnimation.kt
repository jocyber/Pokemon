package pokemongame.animations.movements

import com.lehaine.littlekt.util.seconds
import kotlin.time.Duration

class SpriteAnimation(
    private val updateStrategy: (dt: Duration) -> Unit,
    private val isDoneStrategy: (totalTime: Float) -> Boolean,
) : MoveAnimation {
    private var totalTime = 0f

    override fun update(dt: Duration) {
        totalTime += dt.seconds
        updateStrategy(dt)
    }

    override fun isDone(): Boolean = isDoneStrategy(totalTime)
}
