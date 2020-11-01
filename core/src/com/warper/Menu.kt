package com.warper

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont

class Menu(bitmapFont: BitmapFont,private  val startBattleField: ()->Unit ): Stage() {
    //buttons
    private var buttons: MutableList<Button> = mutableListOf()
    private object Wallpaper{
        var timestamp: Float = 3f
        var blueValue: Float = 0.2f
        var shouldAdd = true
    }
    init {
        buttons.add(Button(200,bitmapFont.lineHeight.toInt() ,"Start",
                Color.WHITE,bitmapFont, (Gdx.graphics.width/2).toFloat(), (Gdx.graphics.height/2).toFloat() + bitmapFont.lineHeight*3))
        buttons.add( Button(200,bitmapFont.lineHeight.toInt(),"Settings", Color.WHITE,bitmapFont,
                (Gdx.graphics.width/2).toFloat(), (Gdx.graphics.height/2).toFloat()-bitmapFont.lineHeight - 50))
    }

    override fun draw(batch: Batch) {
        batch.begin()
        if(Wallpaper.shouldAdd){
            Wallpaper.blueValue += Gdx.graphics.deltaTime * 0.5f / Wallpaper.timestamp
        } else  {
            Wallpaper.blueValue -= Gdx.graphics.deltaTime * 0.5f / Wallpaper.timestamp
        }
        if (Wallpaper.blueValue > 0.8f) {
            Wallpaper.shouldAdd = false
        } else if (Wallpaper.blueValue < 0.1f) {
            Wallpaper.shouldAdd = true
        }

        Gdx.gl.glClearColor(0f, 0f, Wallpaper.blueValue, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        for (button in buttons){
            button.draw(batch)
        }
        batch.end()
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        //println("Touch X:${screenX} TouchY${screenY}")
        for(i in 0 until buttons.size-1){
            @Suppress("NAME_SHADOWING") val button = buttons[i]
            val touchedX = screenX
            val touchedY = Gdx.graphics.height - screenY
            if (button.getX() < touchedX && touchedX < button.getX()+ button.width
                    && button.getY() < touchedY &&  touchedY < button.getY() + button.height){
                println("Button with text: ${button.text} touched")
                    when(button.text){
                        "Start" -> {
                         startBattleField()
                        }
                        "Settings" -> {
                            //TODO move to the settings
                        }
                     }
            }
        }
        return true
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return true
    }
    override fun dispose(){
        for (button in buttons){
            button.dispose()
        }
    }

}