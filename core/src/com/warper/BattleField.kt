package com.warper

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.Batch

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController
import com.badlogic.gdx.math.Vector3
import com.warper.gameobjects.Stargate
import com.warper.util.ModelFactory

class BattleField(bitmapFont: BitmapFont): Stage() {

    init {
        bitmapFont.data.setScale(1f)
    }
    private var perspectiveCamera = PerspectiveCamera(120f,
            Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    private var orthographicCamera = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    var cameraInputController = CameraInputController(perspectiveCamera)
    private var player = Player(0f,0f, 0f,perspectiveCamera,orthographicCamera)
    private var modelBatch = ModelBatch()
    private var environment =  Environment()
    private var labelPlayerX
            = Label("X", 0f.toString(), bitmapFont,Gdx.graphics.width/2 - 100f,Gdx.graphics.height/2 - 30f)
    private var labelPlayerY
            = Label("Y", 0f.toString(), bitmapFont,Gdx.graphics.width/2 - 100f,Gdx.graphics.height/2 - 60f)
    private var labelPlayerZ
            = Label("Z", 0f.toString(), bitmapFont,Gdx.graphics.width/2 - 100f,Gdx.graphics.height/2 - 90f)
    private var cameraLabels: MutableList<Label> = mutableListOf()
            init{
              for(x in 0..2){
                  cameraLabels.add(Label(
                      when(x) {
                          0 ->  "camera X:"
                          1-> "camera Y:"
                          2-> "camera Z:"
                          else -> "NULL"
                      }
                  ,"k",bitmapFont,-orthographicCamera.viewportWidth/2 ,orthographicCamera.viewportHeight/2 - 30f*(x+1)))
              }
            }
    //Texture loading
    private var backGroundTexture = Texture(Gdx.files.internal("background.png"))
    private var backGroundSprite = Sprite(backGroundTexture)
    init {
        backGroundSprite.setScale((Gdx.graphics.width/backGroundSprite.texture.width).toFloat(),
                (Gdx.graphics.height/backGroundSprite.texture.height).toFloat())
        backGroundSprite.setPosition(-Gdx.graphics.width/2f, -Gdx.graphics.height/2f)
    }
    init {
        /*to do camera initialization
        /camera position is synonymous with the player position */
        perspectiveCamera.near = 1f
        perspectiveCamera.far = 5000f
        //environment
        val blueDirectionalLight = DirectionalLight()
        blueDirectionalLight.color.set(Color.BLUE)
        blueDirectionalLight.setDirection(0f,0f,-10f)
        environment.add(blueDirectionalLight)
        val purpleDirectionalLight = DirectionalLight()
        purpleDirectionalLight.color.set(Color.PURPLE)
        purpleDirectionalLight.setDirection(0f,0f,10f)
        environment.add(purpleDirectionalLight)
    }
    //
    val stargates: MutableList<Stargate> = mutableListOf()
    init {
        for (i in 0..19){
            val x: Float = (1f-  2*Math.random().toFloat()) * 10f
            val y: Float = (1f-  2*Math.random().toFloat()) * 10f
            val z: Float = -100f * i
            stargates.add(Stargate(x,y,z,ModelFactory.getBox()))
        }
    }

    override fun draw(batch: Batch) {
        Gdx.gl.glViewport(0,0,Gdx.graphics.width,Gdx.graphics.height)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        Gdx.gl.glClearColor(0f,0f,0f,1f)
        //drawing info
        orthographicCamera.update()
        batch.projectionMatrix = orthographicCamera.combined
        batch.begin()
        //black background for debug
       batch.draw(backGroundTexture,-orthographicCamera.viewportWidth/2,-orthographicCamera.viewportHeight/2,
                orthographicCamera.viewportWidth,orthographicCamera.viewportHeight)

        drawLabels(batch)
        player.draw(batch)
        batch.end()
        handle_input()
        logic()
        perspectiveCamera.update()
        modelBatch.begin(perspectiveCamera)
        player.draw3D(modelBatch,environment)
        for (stargate in stargates){
            stargate.draw3D(modelBatch,environment)
        }
        modelBatch.end()

    }

    //  TODO: HANDLE ANDROID PAN
    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean {
        println("Handle pan")
        this.player.handlePan(x,y,deltaX,deltaY)
        return true
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        val touch2dScreenCoords = this.orthographicCamera.unproject(
                Vector3(screenX.toFloat(), screenY.toFloat(), 0f)
        )
        touch2dScreenCoords.z = -1000f + perspectiveCamera.position.z
        this.player.setFocus(touch2dScreenCoords)
       return true
    }

    override fun keyDown(keycode: Int): Boolean {
        when(keycode){
            Input.Keys.R -> {
                this.player.setFocus(Vector3(
                        0f,0f,-1000f + perspectiveCamera.position.z
                ))
            }
        }
        return true
    }

    fun handle_input() {
        if(Gdx.app.type == Application.ApplicationType.Android) {
            player.handleInputAndroid()
        } else if (Gdx.app.type == Application.ApplicationType.Desktop){
            player.handleInputDesktop()
        }
    }
    fun logic(){
        this.player.handlePlayerLogic()
    }

    fun drawLabels(batch: Batch) {
        val playerPostionVector = player.getPosition()
        labelPlayerX.setValue(playerPostionVector.x.toString())
        labelPlayerY.setValue(playerPostionVector.y.toString())
        labelPlayerZ.setValue(playerPostionVector.z.toString())
        labelPlayerX.draw(batch)
        labelPlayerY.draw(batch)
        labelPlayerZ.draw(batch)
        for (x in 0..2){
            cameraLabels[x].setValue(when(x){
                0-> perspectiveCamera.position.x.toString()
                1-> perspectiveCamera.position.y.toString()
                2 -> perspectiveCamera.position.z.toString()
                else -> "0"
            })
            cameraLabels[x].draw(batch)
        }

    }

    override fun dispose() {
        this.player.dispose()
        for(stargate in this.stargates ){
            stargate.dispose()
        }
        labelPlayerX.dispose()
        labelPlayerY.dispose()
        labelPlayerZ.dispose()
        for (label in this.cameraLabels){
            label.dispose()
        }
    }

}