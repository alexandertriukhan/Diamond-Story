package animations

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import interfaces.Anim

class Rotation(override val texture: TextureRegion, override val speed: Float, override val isLoop: Boolean) : Anim {

    private var animRotation = 0f

    override fun draw(batch: Batch, x : Float, y : Float, size : Float, delta : Float) {
        if (!isLoop) {
            if (animRotation < -360f) {
                return
            }
        }
        batch.draw(texture, x, y, size / 2f, size / 2f, size, size,
                1f, 1f, -animRotation * speed)
        animRotation += delta
    }

}