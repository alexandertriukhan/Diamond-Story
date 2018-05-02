package utils

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils


class Arc : ShapeRenderer() {

    fun drawArc (x: Float, y: Float, radius: Float, start: Float, degrees: Float) {
        val segments = (6f * Math.cbrt(radius.toDouble()).toFloat() * (degrees / 360.0f)).toInt()

        if (segments <= 0) throw IllegalArgumentException("segments must be > 0.")
        val colorBits = color.toFloatBits()
        val theta = 2 * MathUtils.PI * (degrees / 360.0f) / segments
        val cos = MathUtils.cos(theta)
        val sin = MathUtils.sin(theta)
        var cx = radius * MathUtils.cos(start * MathUtils.degreesToRadians)
        var cy = radius * MathUtils.sin(start * MathUtils.degreesToRadians)

        for (i in 0 until segments) {
            renderer.color(colorBits)
            renderer.vertex(x + cx, y + cy, 0f)
            val temp = cx
            cx = cos * cx - sin * cy
            cy = sin * temp + cos * cy
            renderer.color(colorBits)
            renderer.vertex(x + cx, y + cy, 0f)
        }
    }

}