package pokemongame.animations

import kotlin.time.Duration

interface PokemonAnimation {
    fun update(dt: Duration)

    fun isDone(): Boolean
}
