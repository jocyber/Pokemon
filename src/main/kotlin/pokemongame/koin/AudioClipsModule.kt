package pokemongame.koin

import com.lehaine.littlekt.Context
import com.lehaine.littlekt.audio.AudioClip
import com.lehaine.littlekt.file.vfs.VfsFile
import com.lehaine.littlekt.file.vfs.readAudioClip
import kotlinx.coroutines.runBlocking
import org.koin.core.qualifier.named
import org.koin.dsl.module

private fun loadAudioClip(vfsFile: VfsFile): AudioClip = runBlocking { vfsFile.readAudioClip() }

fun audioClipsModule(context: Context) = module {
    single(named(TACKLE_SOUND)) { loadAudioClip(context.resourcesVfs["assets/sounds/Tackle.wav"]) }
}

const val TACKLE_SOUND = "TackleSound"
