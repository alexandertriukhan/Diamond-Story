package animations

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import interfaces.Anim

class Jump(override val texture: TextureRegion, override val speed: Float, override val isLoop: Boolean, private val maxJumpHeight: Int) : Anim {

    var increment = true
    var yOffset = 0f
    var onTopPos = false
    var isStopped = false

    override fun draw(batch: Batch, x: Float, y: Float, size: Float, delta: Float) {
        assertYOffset(delta)
        batch.draw(texture, x, y + yOffset, size, size)
    }

    private fun assertYOffset(delta: Float) {
        if (!onTopPos) {
            if (yOffset <= maxJumpHeight) {
                increment = true
                onTopPos = false
            }
        } else {
            increment = false
            onTopPos = false
            if (yOffset <= 0) {
                if (!isLoop) {
                    isStopped = true
                }
                else {
                    increment = true
                    onTopPos = false
                }
            }
        }
        if (increment) {
            yOffset += delta * speed
        } else {
            yOffset -= delta * speed
        }
    }

}