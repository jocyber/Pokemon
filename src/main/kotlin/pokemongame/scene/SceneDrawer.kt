package pokemongame.scene

import com.lehaine.littlekt.graphics.g2d.SpriteBatch
import kotlin.time.Duration
import org.koin.java.KoinJavaComponent.inject

/**
 * An abstract class representing an entity that is responsible for drawing content onto the screen.
 *
 * <p>
 * SceneDrawers are not typically entities such as moves or health bars because, in the case of
 * health bars, they may be used across different scenes which would want to draw them differently.
 * Moves are capable of drawing content on the screen but are not responsible for the management of
 * the screen components. This job falls onto the top level classes that orchestrate said
 * components.
 *
 * @author Jordan Harman
 */
abstract class SceneDrawer {
    /**
     * A function that draws some content onto the screen and controls how large and where the
     * components are drawn.
     *
     * @param dt the duration since the last frame
     * @see SpriteBatch
     */
    abstract fun draw(dt: Duration)

    protected companion object {
        val SPRITE_BATCH: SpriteBatch by inject(SpriteBatch::class.java)
    }
}
