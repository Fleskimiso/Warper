package com.warper

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.warper.interfaces.Drawable

class Label(private var name: String, private var value: String,private var bitmapFont: BitmapFont,
            private var x: Float, private var y: Float): Drawable {
    fun setValue(value2: String){
        this.value = value2
    }

    override fun draw(batch: Batch) {
        bitmapFont.cache.clear()
        bitmapFont.draw(batch, "$name: $value",x, y )
    }
    override fun dispose() {
        bitmapFont.dispose()
    }
}