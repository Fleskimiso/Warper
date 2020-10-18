package com.warper

import com.badlogic.gdx.Files
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.utils.UBJsonReader

class Player(var x: Float, var y: Float, var z: Float): Drawable {

    private var uBJsonReader = UBJsonReader()
    private var g3dModelLoader = G3dModelLoader(uBJsonReader)
    private var model = g3dModelLoader.loadModel(Gdx.files.getFileHandle("Spaceship.g3db",
    Files.FileType.Internal))
    private  var modelBuilder = ModelBuilder()
    var modelInstance = ModelInstance(model, x, y, z)
    init {
        println(model.materials.size)
       /* modelInstance = ModelInstance(modelBuilder.createBox(8f,8f,8f,
                Material(ColorAttribute(ColorAttribute.createDiffuse(Color.GREEN))),
                (VertexAttributes.Usage.Position or VertexAttributes.Usage.Normal).toLong()))*/
    }

    override fun draw(batch: Batch) {

    }
    fun draw3D( modelBatch: ModelBatch) {
        modelBatch.render(modelInstance)
    }
}