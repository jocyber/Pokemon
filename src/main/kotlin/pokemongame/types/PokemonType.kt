package pokemongame.types

import com.lehaine.littlekt.graphics.Texture
import com.lehaine.littlekt.graphics.g2d.TextureSlice
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject
import pokemongame.koin.MOVE_BUTTONS
import pokemongame.pokemon.Pokemon

enum class PokemonType {
    NORMAL,
    FIRE,
    WATER,
    ELECTRIC,
    GRASS,
    ICE,
    FIGHTING,
    POISON,
    GROUND,
    FLYING,
    PSYCHIC,
    BUG,
    ROCK,
    GHOST,
    DRAGON,
    DARK,
    STEEL,
    FAIRY,
    UNKNOWN;

    companion object {
        private val BUTTONS_TEXTURE: Texture by inject(Texture::class.java, named(MOVE_BUTTONS))

        private const val HALF_EFFECTIVE = 0.5f
        private const val NORMAL_EFFECTIVE = 1f

        const val BUTTON_WIDTH = 192
        const val BUTTON_HEIGHT = 46

        val BUTTONS_BY_TYPE =
            arrayOf(
                FLYING,
                FIGHTING,
                UNKNOWN,
                POISON,
                GROUND,
                ROCK,
                BUG,
                GHOST,
                STEEL,
                PSYCHIC,
                FIRE,
                WATER,
                GRASS,
                ELECTRIC,
                FAIRY,
                ICE,
                DRAGON,
                DARK,
                NORMAL,
            )
            .withIndex()
            .associateWith { indexedType ->
                Pair(
                    TextureSlice(
                        BUTTONS_TEXTURE,
                        x = 0,
                        y = BUTTON_HEIGHT * indexedType.index,
                        width = BUTTON_WIDTH,
                        height = BUTTON_HEIGHT,
                    ),
                    TextureSlice(
                        BUTTONS_TEXTURE,
                        x = BUTTON_WIDTH,
                        y = BUTTON_HEIGHT * indexedType.index,
                        width = BUTTON_WIDTH,
                        height = BUTTON_HEIGHT,
                    )
                )
            }
            .mapKeys { it.key.value }

        fun typeEffectiveness(pokemon: Pokemon, attacking: PokemonType): Float {
            return typeEffectiveness(pokemon.type.first, attacking) *
                (pokemon.type.second?.let { typeEffectiveness(it, attacking) } ?: 1f)
        }

        private fun typeEffectiveness(defending: PokemonType, attacking: PokemonType): Float {
            return when (defending) {
                FIRE ->
                    when (attacking) {
                        FIRE,
                        WATER -> HALF_EFFECTIVE
                        else -> NORMAL_EFFECTIVE
                    }
                WATER ->
                    when (attacking) {
                        FIRE,
                        WATER -> HALF_EFFECTIVE
                        else -> NORMAL_EFFECTIVE
                    }
                else -> 1f
            }
        }
    }
}
