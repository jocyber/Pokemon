package pokemongame.guice

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import com.google.inject.name.Named
import com.lehaine.littlekt.Context
import com.lehaine.littlekt.file.vfs.readTexture
import com.lehaine.littlekt.graphics.Texture
import kotlinx.coroutines.runBlocking

class AnimationTexturesModule(private val context: Context) : AbstractModule() {
    override fun configure() {}

    @Provides
    @Singleton
    @Named(HIT_EFFECT)
    fun getHitTexture(): Texture = runBlocking {
        context.resourcesVfs["$ASSETS_DIR/tackle_effect.png"].readTexture()
    }

    private companion object {
        const val ASSETS_DIR = "assets/sprites/moves"
    }
}
