package pokemongame.scene.battle.control

import com.lehaine.littlekt.graphics.Texture
import com.lehaine.littlekt.Context
import com.lehaine.littlekt.graphics.g2d.use
import com.lehaine.littlekt.input.Key
import com.lehaine.littlekt.math.Vec2f
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject
import pokemongame.koin.DISPLAY_BOX
import kotlin.time.Duration
import pokemongame.scene.SceneDrawer
import pokemongame.scene.battle.*
import pokemongame.types.PokemonType
import pokemongame.types.PokemonType.Companion.BUTTONS_BY_TYPE

class MoveSelector(
    private val sceneState: BattleSceneState,
    private val context: Context,
) : SceneDrawer() {
    override fun draw(dt: Duration) {
        SPRITE_BATCH.use {
            for (indexedMove in sceneState.playerState.stats.moves.withIndex()) {
                val buttonPair = BUTTONS_BY_TYPE[indexedMove.value?.type ?: PokemonType.UNKNOWN]!!
                val position = POSITIONS[indexedMove.index]

                it.draw(
                    slice = if (indexedMove.value.toString() == sceneState.selectedMove) buttonPair.second else buttonPair.first,
                    x = position.x,
                    y = position.y + MOVE_BUTTON_HEIGHT,
                    originY = ORIGIN_Y,
                    width = MOVE_BUTTON_WIDTH,
                    height = MOVE_BUTTON_HEIGHT,
                    flipY = true,
                )
            }

            it.draw(
                DISPLAY_BOX_TEXTURE,
                x = DISPLAY_BOX_START_X,
                y = TOP_MOST_Y,
                originY = ORIGIN_Y,
                width = INFO_BOX_WIDTH,
                height = INPUT_HEIGHT,
                flipY = true,
            )
        }
    }

    @Suppress("CyclomaticComplexMethod")
    private fun handleSelector() =
        with (sceneState) {
            val moves = playerState.stats.moves.map { it?.toString() ?: NO_MOVE }

            when {
                context.input.isKeyPressed(Key.W) ->
                    if (selectedMove == moves[2] && moves[0] != NO_MOVE) {
                        selectedMove = moves[0]
                    } else if (selectedMove == moves[3] && moves[1] != NO_MOVE) {
                        selectedMove = moves[1]
                    }
                context.input.isKeyPressed(Key.S) ->
                    if (selectedMove == moves[0] && moves[2] != NO_MOVE) {
                        selectedMove = moves[2]
                    } else if (selectedMove == moves[1] && moves[3] != NO_MOVE) {
                        selectedMove = moves[3]
                    }
                context.input.isKeyPressed(Key.A) ->
                    if (selectedMove == moves[1] && moves[0] != NO_MOVE) {
                        selectedMove = moves[0]
                    } else if (selectedMove == moves[3] && moves[2] != NO_MOVE) {
                        selectedMove = moves[2]
                    }
                context.input.isKeyPressed(Key.D) ->
                    if (selectedMove == moves[0] && moves[1] != NO_MOVE) {
                        selectedMove = moves[1]
                    } else if (selectedMove == moves[2] && moves[3] != NO_MOVE) {
                        selectedMove = moves[3]
                    }
            }
        }

    companion object {
        private val DISPLAY_BOX_TEXTURE: Texture by inject(Texture::class.java, named(DISPLAY_BOX))
        const val NO_MOVE = "None"

        private const val INFO_BOX_WIDTH = 250f

        private const val MOVE_BUTTON_WIDTH = (SCREEN_WIDTH - HORIZONTAL_SPACING * 3 - INFO_BOX_WIDTH) / 2f
        private val MOVE_BUTTON_HEIGHT = INPUT_HEIGHT / 2f

        private const val LEFT_MOST_X = HORIZONTAL_SPACING

        private const val DISPLAY_BOX_START_X = HORIZONTAL_SPACING * 2 + MOVE_BUTTON_WIDTH * 2

        private val TOP_LEFT = Vec2f(LEFT_MOST_X, TOP_MOST_Y)
        private val TOP_RIGHT = Vec2f(LEFT_MOST_X + MOVE_BUTTON_WIDTH, TOP_MOST_Y)
        private val BOTTOM_LEFT = Vec2f(LEFT_MOST_X, TOP_MOST_Y - MOVE_BUTTON_HEIGHT)
        private val BOTTOM_RIGHT = Vec2f(LEFT_MOST_X + MOVE_BUTTON_WIDTH, TOP_MOST_Y - MOVE_BUTTON_HEIGHT)

        private val POSITIONS = listOf(TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT)
    }
}
