package com.warper

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite

class ImageButton(private val path: String, private var x: Float, private var y: Float, private var width: Float) : Drawable {
    private var texture = Texture(Gdx.files.internal(path))
    private var sprite = Sprite(texture)
    init {
        // box should be 1/6 viewport width
        sprite.setScale(width/texture.width, width/texture.height)
        sprite.setPosition(x, y)
    }
    override fun draw(batch: Batch) {
        sprite.draw(batch)
    }
    fun overLaps(x: Float, y:Float): Boolean{
        return sprite.boundingRectangle.contains(x, y)
    }
}