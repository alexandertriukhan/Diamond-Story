package animations

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import interfaces.Anim

class Flash(override val texture: TextureRegion, override val speed: Float, override val isLoop: Boolean) : Anim {

    private var alpha = 1f
    private val minAlpha = 0.25f
    private var increment = false
    private var waitFor1 = false

    var isStopped = false

    override fun draw(batch: Batch, x: Float, y: Float, size: Float, delta: Float) {
        if (!isStopped) {
            if (alpha > 1f) alpha = 1f
            batch.setColor(1.0f, 1.0f, 1.0f, alpha)
            batch.draw(texture, x, y, size, size)
            batch.setColor(1.0f, 1.0f, 1.0f, 1.0f)
            assertAlpha(delta)
        }
    }

    private fun assertAlpha(delta: Float) {
        if (!waitFor1) {
            if (alpha <= minAlpha) {
                increment = true
                waitFor1 = true
            }
        } else {
            if (alpha >= 1f) {
                increment = false
                waitFor1 = false
                if (!isLoop) {
                    isStopped = true
                }
            }
        }
        if (increment) {
            alpha += delta * speed
        } else {
            alpha -= delta * speed
        }
    }

}