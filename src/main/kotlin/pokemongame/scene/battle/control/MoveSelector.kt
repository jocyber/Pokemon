package pokemongame.scene.battle.control

import com.lehaine.littlekt.graphics.Texture
import com.lehaine.littlekt.graphics.g2d.use
import com.lehaine.littlekt.math.Vec2f
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject
import pokemongame.koin.DIALOG_BOX
import kotlin.time.Duration
import pokemongame.scene.SceneDrawer
import pokemongame.scene.battle.BattleSceneState
import pokemongame.types.PokemonType
import pokemongame.types.PokemonType.Companion.BUTTONS_BY_TYPE
import pokemongame.types.PokemonType.Companion.BUTTON_HEIGHT
import pokemongame.types.PokemonType.Companion.BUTTON_WIDTH

class MoveSelector(
    private val sceneState: BattleSceneState,
) : SceneDrawer() {

    override fun draw(dt: Duration) {
        SPRITE_BATCH.use {
            for (indexedMove in sceneState.playerState.stats.moves.withIndex()) {
                val buttonPair = BUTTONS_BY_TYPE[indexedMove.value?.type ?: PokemonType.UNKNOWN]!!
                val position = POSITIONS[indexedMove.index]

                it.draw(
                    slice = if (indexedMove.value.toString() == sceneState.selectedMove) buttonPair.second else buttonPair.first,
                    x = position.x + if (indexedMove.index == 1 || indexedMove.index == 3) RIGHT_OFFSET else 0,
                    y = position.y,
                    width = BUTTON_WIDTH.toFloat(),
                    height = BUTTON_HEIGHT.toFloat(),
                    scaleX = BUTTON_SCALE_WIDTH,
                    scaleY = BUTTON_SCALE_HEIGHT,
                    flipY = true,
                )
            }
        }
    }

    companion object {
        private val DIALOG_BOX_TEXTURE: Texture by inject(Texture::class.java, named(DIALOG_BOX))

        private const val BUTTON_SCALE_WIDTH = 1.6f
        private const val BUTTON_SCALE_HEIGHT = 1.25f

        private const val RIGHT_OFFSET = 40

        private const val LEFT_MOST_X = 25f
        private const val TOP_MOST_Y = -219f

        private val TOP_LEFT = Vec2f(LEFT_MOST_X, TOP_MOST_Y)
        private val TOP_RIGHT = Vec2f(LEFT_MOST_X + BUTTON_WIDTH * BUTTON_SCALE_WIDTH, TOP_MOST_Y)
        private val BOTTOM_LEFT = Vec2f(LEFT_MOST_X, TOP_MOST_Y - BUTTON_HEIGHT * BUTTON_SCALE_HEIGHT)
        private val BOTTOM_RIGHT = Vec2f(
            LEFT_MOST_X + BUTTON_WIDTH * BUTTON_SCALE_WIDTH,
            TOP_MOST_Y - BUTTON_HEIGHT * BUTTON_SCALE_HEIGHT
        )

        private val POSITIONS = listOf(TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT)
    }
}
