package com.warper

import com.badlogic.gdx.Files
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.utils.UBJsonReader
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.math.collision.BoundingBox
import com.warper.interfaces.Drawable

class Player(private var x: Float,private var y: Float,private var z: Float,private val camera: PerspectiveCamera,
var orthographicCamera: OrthographicCamera): Drawable {

    private var uBJsonReader = UBJsonReader()
    private var g3dModelLoader = G3dModelLoader(uBJsonReader)
    private var model = g3dModelLoader.loadModel(Gdx.files.getFileHandle("Spaceship.g3db",
    Files.FileType.Internal))
    private  var modelBuilder = ModelBuilder()
    private var boundingbox = BoundingBox()
    //TODO buttons rendering
    private var upButton = ImageButton("up-chevron.png",  Gdx.graphics.width/2f - Gdx.graphics.width/16f
            , 0f, Gdx.graphics.width/8f)
    private var downButton = ImageButton("down-chevron.png",-Gdx.graphics.width/2f+Gdx.graphics.width/16f , 0f,
            Gdx.graphics.width/8f)

    var modelInstance = ModelInstance(model, x, y, z)
    init {
        boundingbox = modelInstance.calculateBoundingBox(boundingbox)
        camera.position.set(0f,0f, -boundingbox.depth)
        camera.lookAt(0f,0f,0f)
    }
    override fun draw(batch: Batch) {
        //draws controll buttons
        upButton.draw(batch)
        downButton.draw(batch)
    }
    fun draw3D( modelBatch: ModelBatch, enviroment: Environment) {
        modelBatch.render(modelInstance, enviroment)
    }
    fun handleInputDesktop() {
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            this.camera.translate(0f,0f,-30f*Gdx.graphics.deltaTime)
            this.modelInstance.transform.translate(0f,0f,-30f*Gdx.graphics.deltaTime)
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            this.camera.translate(0f,0f,30f*Gdx.graphics.deltaTime)
            this.modelInstance.transform.translate(0f,0f,30f*Gdx.graphics.deltaTime)
        }
    }
    fun handleInputAndroid() {

        if(Gdx.input.isTouched){
            var touch2dCoordinates = orthographicCamera.unproject(Vector3(
                    Gdx.input.x.toFloat(),Gdx.input.y.toFloat(),0f
            ))
            if(upButton.overLaps(touch2dCoordinates.x, touch2dCoordinates.y)){
                this.camera.translate(0f,0f,-30f*Gdx.graphics.deltaTime)
                this.modelInstance.transform.translate(0f,0f,-30f*Gdx.graphics.deltaTime)
            }else if(downButton.overLaps(touch2dCoordinates.x, touch2dCoordinates.y)) {
                this.camera.translate(0f,0f,30f*Gdx.graphics.deltaTime)
                this.modelInstance.transform.translate(0f,0f,30f*Gdx.graphics.deltaTime)
            }
        }
    }
    fun getPosition(): Vector3 {
        return this.modelInstance.transform.getTranslation(Vector3(0f,0f,0f))
    }
 }