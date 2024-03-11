package pokemongame.animations.movements

import kotlin.time.Duration

sealed interface MoveAnimation {
    fun update(dt: Duration)

    fun isDone(): Boolean
}
