package com.warper

import com.badlogic.gdx.Files
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
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

class Player(private var x: Float,private var y: Float,private var z: Float,private var camera: PerspectiveCamera): Drawable {

    private var uBJsonReader = UBJsonReader()
    private var g3dModelLoader = G3dModelLoader(uBJsonReader)
    private var model = g3dModelLoader.loadModel(Gdx.files.getFileHandle("Spaceship.g3db",
    Files.FileType.Internal))
    private  var modelBuilder = ModelBuilder()
    private var boundingbox = BoundingBox()
    var modelInstance = ModelInstance(model, x, y, z)
    init {
        boundingbox = modelInstance.calculateBoundingBox(boundingbox)
        camera.position.set(0f,0f, -boundingbox.depth)
        camera.lookAt(boundingbox.centerX,boundingbox.centerY,boundingbox.centerZ)
    }
    override fun draw(batch: Batch) {

    }
    fun draw3D( modelBatch: ModelBatch, enviroment: Environment) {
        modelBatch.render(modelInstance, enviroment)
    }
    fun handleInputDesktop() {
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            modelInstance.transform.translate(0f,0f,-30f*Gdx.graphics.deltaTime)
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            modelInstance.transform.translate(0f,0f,30f*Gdx.graphics.deltaTime)
        }
    }
    fun handleInputAndroid() {

    }
    fun getPosition(): Vector3 {
        return this.modelInstance.transform.getTranslation(Vector3(0f,0f,0f))
    }
 }