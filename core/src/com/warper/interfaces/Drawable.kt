package com.warper.interfaces
import com.badlogic.gdx.graphics.g2d.Batch
interface Drawable {
    fun draw(batch: Batch)
    fun dispose()
}
