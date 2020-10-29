package com.warper.util

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder

object ModelFactory {

    private val modelBuilder = ModelBuilder()
    private val model = modelBuilder.createBox(10f,5f,5f,
    Material(ColorAttribute.createDiffuse(0f,0f,1f,0f)),
            VertexAttributes.Usage.Position.toLong().or(VertexAttributes.Usage.Normal.toLong()))

    public fun getBlueBox(): ModelInstance {
        return ModelInstance(model)
    }
    public fun getBox(color: Color){
        //TODO be implemented
    }
}