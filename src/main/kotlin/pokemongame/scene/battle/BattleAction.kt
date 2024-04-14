package pokemongame.scene.battle

import com.lehaine.littlekt.graphics.Texture
import com.lehaine.littlekt.graphics.g2d.TextureSlice
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject
import pokemongame.koin.BATTLE_BUTTONS

enum class BattleAction {
    FIGHT,
    BAG,
    POKEMON,
    RUN,
    CALL, // TODO: get rid of this in the texture
    BALL,
    ROCK,
    BAIT;

    companion object {
        private val BUTTONS_TEXTURE: Texture by inject(Texture::class.java, named(BATTLE_BUTTONS))

        const val BUTTON_HEIGHT = 46
        const val BUTTON_WIDTH = 130

        val BUTTONS_BY_LABEL =
            arrayOf(FIGHT, POKEMON, BAG, RUN, CALL, BALL, ROCK, BAIT)
                .withIndex()
                .associateWith { indexedLabel ->
                    Pair(
                        TextureSlice(
                            BUTTONS_TEXTURE,
                            x = 0,
                            y = BUTTON_HEIGHT * indexedLabel.index,
                            width = BUTTON_WIDTH,
                            height = BUTTON_HEIGHT,
                        ),
                        TextureSlice(
                            BUTTONS_TEXTURE,
                            x = BUTTON_WIDTH,
                            y = BUTTON_HEIGHT * indexedLabel.index,
                            width = BUTTON_WIDTH,
                            height = BUTTON_HEIGHT,
                        )
                    )
                }
                .mapKeys { it.key.value }
    }

}