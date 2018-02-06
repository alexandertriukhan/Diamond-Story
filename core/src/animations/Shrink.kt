package animations

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import interfaces.Anim

class Shrink(override val texture: TextureRegion, override val speed: Float, override val isLoop: Boolean) : Anim {

    private var sizeModifier = 1f
    private var waitFor1 = false
    private var increment = false
    private var minSize = 0f

    var isStopped = false

    override fun draw(batch: Batch, x: Float, y: Float, size: Float, delta: Float) {
        if (!isStopped) {
            if (sizeModifier > 1f) sizeModifier = 1f
            if (sizeModifier < 0f) sizeModifier = 0f
            batch.draw(texture, x + assertOffset(size), y + assertOffset(size), size * sizeModifier, size * sizeModifier)
            assertSizeModifier(delta)
        }
    }

    private fun assertSizeModifier(delta: Float) {
        if (!waitFor1) {
            if (sizeModifier <= minSize) {
                increment = true
                waitFor1 = true
                if (!isLoop) {
                    isStopped = true
                }
            }
        } else {
            if (sizeModifier >= 1f) {
                increment = false
                waitFor1 = false
            }
        }
        if (increment) {
            sizeModifier += delta * speed
        } else {
            sizeModifier -= delta * speed
        }
    }

    private fun assertOffset(size : Float) : Float {
        val newSize = size * sizeModifier
        return (size - newSize) / 2
    }

}