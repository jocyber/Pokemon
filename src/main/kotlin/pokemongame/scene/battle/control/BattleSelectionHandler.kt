package pokemongame.scene.battle.control

import com.lehaine.littlekt.Context
import com.lehaine.littlekt.graphics.Texture
import com.lehaine.littlekt.graphics.g2d.TextureSlice
import com.lehaine.littlekt.graphics.g2d.use
import com.lehaine.littlekt.input.Key
import com.lehaine.littlekt.math.Vec2f
import kotlin.time.Duration
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject
import pokemongame.koin.BATTLE_BUTTONS
import pokemongame.koin.DIALOG_BOX
import pokemongame.scene.SceneDrawer
import pokemongame.scene.battle.BattleSceneState
import pokemongame.scene.battle.DisplayState

class BattleSelectionHandler(
    private val sceneState: BattleSceneState,
    private val context: Context,
) : SceneDrawer() {
    private var selectedAction = "FIGHT"
    private var displayMode = DisplayMode.SELECTING_ACTION

    override fun draw(dt: Duration) {
        handleSelector()

        SPRITE_BATCH.use {
            it.draw(
                DIALOG_BOX_TEXTURE,
                x = 15f,
                y = -276f,
                width = 530f,
                height = BUTTON_HEIGHT * 2f,
                scaleX = 1f,
                scaleY = BUTTON_SCALE_HEIGHT,
                flipY = true,
            )

            for ((label, position) in POSITION_BY_LABEL) {
                val buttonPair = BUTTONS_BY_LABEL[label]!!

                it.draw(
                    slice = if (label == selectedAction) buttonPair.second else buttonPair.first,
                    x = position.x,
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

    private fun handleSelector() {
        when (displayMode) {
            DisplayMode.SELECTING_ACTION -> handleActionSelector()
            DisplayMode.SELECTING_MOVE -> {}
        }
    }

    @Suppress("CyclomaticComplexMethod")
    private fun handleActionSelector() {
        when {
            context.input.isKeyPressed(Key.W) ->
                if (selectedAction == "POKEMON") {
                    selectedAction = "FIGHT"
                } else if (selectedAction == "RUN") {
                    selectedAction = "BAG"
                }
            context.input.isKeyPressed(Key.S) ->
                if (selectedAction == "FIGHT") {
                    selectedAction = "POKEMON"
                } else if (selectedAction == "BAG") {
                    selectedAction = "RUN"
                }
            context.input.isKeyPressed(Key.A) ->
                if (selectedAction == "BAG") {
                    selectedAction = "FIGHT"
                } else if (selectedAction == "RUN") {
                    selectedAction = "POKEMON"
                }
            context.input.isKeyPressed(Key.D) ->
                if (selectedAction == "FIGHT") {
                    selectedAction = "BAG"
                } else if (selectedAction == "POKEMON") {
                    selectedAction = "RUN"
                }
            context.input.isKeyPressed(Key.E) ->
                when (selectedAction) {
                    "BAG" -> sceneState.displayState = DisplayState.BAG
                    "POKEMON" -> sceneState.displayState = DisplayState.PARTY
                    "FIGHT" -> {} // displayMode = DisplayMode.SELECTING_MOVE
                }
            else -> {}
        }
    }

    companion object {
        private const val BUTTON_HEIGHT = 46
        private const val BUTTON_WIDTH = 130

        private const val BUTTON_SCALE_WIDTH = 1.5f
        private const val BUTTON_SCALE_HEIGHT = 1.23f

        private const val LEFT_MOST_X = 550f
        private const val TOP_MOST_Y = -219f

        private enum class DisplayMode {
            SELECTING_ACTION,
            SELECTING_MOVE,
        }

        private val BUTTONS_TEXTURE: Texture by inject(Texture::class.java, named(BATTLE_BUTTONS))
        private val DIALOG_BOX_TEXTURE: Texture by inject(Texture::class.java, named(DIALOG_BOX))

        private val BUTTONS_BY_LABEL =
            arrayOf("FIGHT", "POKEMON", "BAG", "RUN", "CALL", "BALL_R", "ROCK", "BAIT", "BALL_Y")
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

        private val POSITION_BY_LABEL =
            mapOf(
                "FIGHT" to Vec2f(LEFT_MOST_X, TOP_MOST_Y),
                "POKEMON" to Vec2f(LEFT_MOST_X, TOP_MOST_Y - BUTTON_HEIGHT * BUTTON_SCALE_HEIGHT),
                "BAG" to Vec2f(LEFT_MOST_X + BUTTON_WIDTH * BUTTON_SCALE_WIDTH, TOP_MOST_Y),
                "RUN" to
                    Vec2f(
                        LEFT_MOST_X + BUTTON_WIDTH * BUTTON_SCALE_WIDTH,
                        TOP_MOST_Y - BUTTON_HEIGHT * BUTTON_SCALE_HEIGHT
                    ),
            )
    }
}
