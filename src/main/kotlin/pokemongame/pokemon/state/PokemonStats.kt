package pokemongame.pokemon.state

import com.lehaine.littlekt.graphics.Texture
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject
import pokemongame.pokemon.Pokemon

class PokemonStats(
    var level: Int,
    currentHealth: Int,
    // val gender: Gender,
    // moves
    // ivs
    // evs
    // actualStats
    var totalHealth: Int,
    val pokemon: Pokemon,
) {
    var currentHealth = currentHealth
        set(updatedHealth) {
            field =
                when {
                    updatedHealth < 0 -> 0
                    updatedHealth > totalHealth -> totalHealth
                    else -> updatedHealth
                }
        }

    val texture: Texture by inject(Texture::class.java, named(pokemon.toString()))
}
