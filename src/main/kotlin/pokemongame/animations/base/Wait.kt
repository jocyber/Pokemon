package pokemongame.animations.base

import com.lehaine.littlekt.util.seconds
import kotlin.time.Duration
import pokemongame.animations.PokemonAnimation

class Wait(private val seconds: Float) : PokemonAnimation {
    private var timeWaited = 0f

    override fun update(dt: Duration) {
        timeWaited += dt.seconds
    }

    override fun isDone(): Boolean = timeWaited >= seconds
}
