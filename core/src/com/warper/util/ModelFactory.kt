package com.warper.util

import com.badlogic.gdx.Files
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.utils.JsonReader
import com.badlogic.gdx.utils.UBJsonReader

object ModelFactory {

    private val modelBuilder = ModelBuilder()
    private var model = modelBuilder.createBox(10f,5f,5f,
    Material(ColorAttribute.createDiffuse(0f,0f,1f,0f)),
            VertexAttributes.Usage.Position.toLong().or(VertexAttributes.Usage.Normal.toLong()))

    private fun getBox(): ModelInstance {
        return ModelInstance(model)
    }
    fun setBoxColor(color: Color){
        model = modelBuilder.createBox(10f,5f,5f,
                Material(ColorAttribute.createDiffuse(color)),
                VertexAttributes.Usage.Position.toLong().or(VertexAttributes.Usage.Normal.toLong()))
    }
    private var UBJsonReader = UBJsonReader()
    private var g3dModelLoader = G3dModelLoader(UBJsonReader)
    private var jsonReader = JsonReader()
    private var starGateModel =  g3dModelLoader.loadModel(
            Gdx.files.getFileHandle("Stargate_max/Stargate.g3db",
            Files.FileType.Internal)
    )
    fun getStarGateInstance(): ModelInstance {
        return ModelInstance(starGateModel)
    }
    private var spaceShipModel =  g3dModelLoader.loadModel(
            Gdx.files.getFileHandle("Spaceship.g3db",
                    Files.FileType.Internal)
    )
    fun getSpaceShipInstance(): ModelInstance {
        return ModelInstance(spaceShipModel)
    }

}