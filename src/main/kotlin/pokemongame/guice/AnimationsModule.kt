package pokemongame.guice

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import com.google.inject.name.Named
import com.lehaine.littlekt.Context
import com.lehaine.littlekt.graphics.Texture
import com.lehaine.littlekt.graphics.g2d.Animation
import com.lehaine.littlekt.graphics.g2d.SpriteBatch
import com.lehaine.littlekt.graphics.g2d.TextureSlice
import com.lehaine.littlekt.graphics.slice
import com.lehaine.littlekt.util.seconds

class AnimationsModule(private val context: Context) : AbstractModule() {
    override fun configure() {}

    @Provides @Singleton fun getSpriteBatch(): SpriteBatch = SpriteBatch(context)

    @Provides
    @Singleton
    @Named(TACKLE_HIT)
    fun getTackleHitAnimation(@Named(HIT_EFFECT) texture: Texture): Animation<TextureSlice> =
        Animation(
            frames = listOf(texture.slice()),
            frameIndices = listOf(0),
            frameTimes = listOf(0.125f.seconds),
        )
}
