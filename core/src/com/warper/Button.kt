package com.warper

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.Sprite

class Button(var width: Int,var height: Int,var text: String,private var borderColor: Color,private val bitmapFont: BitmapFont,x: Float,y: Float) {
    private var sprite : Sprite? = null
    private var glyphLayout =GlyphLayout(bitmapFont, text)
    init {
        height = (1.5 * height).toInt()+1
        val pixmap = Pixmap(width,height,Pixmap.Format.RGBA8888)
        pixmap.setColor(borderColor)
        pixmap.drawLine(0,0,width-1,0)
        pixmap.drawLine(width-1,height-1,width-1,0)
        pixmap.drawLine(width-1,height-1,0,height-1)
        pixmap.drawLine(0,0,0,height-1)
        sprite = Sprite(Texture(pixmap))
        sprite!!.x = x - sprite!!.width/2
        sprite!!.y = y - sprite!!.height/2
    }
    fun draw(batch: Batch) {
        sprite!!.draw(batch)
        bitmapFont.draw(batch,glyphLayout,sprite!!.x + (sprite!!.width-glyphLayout.width)/2,
                sprite!!.y + glyphLayout.height*1.75f)
    }
    fun dispose() {
        sprite!!.texture.dispose()
        bitmapFont.dispose()
    }
    fun getButtonText(): String{
        return this.text
    }
    fun getX(): Float {
        return this.sprite!!.x
    }
    fun getY(): Float{
        return this.sprite!!.y
    }

}