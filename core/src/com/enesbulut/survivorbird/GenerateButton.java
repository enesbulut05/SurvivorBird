package com.enesbulut.survivorbird;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.awt.Button;
import java.util.ArrayList;

public class GenerateButton {

    private TextureRegionDrawable textureRegionDrawable;
    private TextureRegion textureRegion;
    private String file;
    private TextureAtlas buttonAtlas;
    private ImageButton generatedButton;


    public TextureRegionDrawable getTextureRegionDrawable() {
        return textureRegionDrawable;
    }

    public void setFile(String file) {
        this.file = file;
        textureRegion.setRegion(buttonAtlas.findRegion(file));
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public GenerateButton(String file, ArrayList<Float> arrayList) {
        buttonAtlas = new TextureAtlas("buttons/buttons.atlas");
        this.file = file;
        this.textureRegion = buttonAtlas.findRegion(file);
        textureRegionDrawable = new TextureRegionDrawable(textureRegion);
        generatedButton = new ImageButton(textureRegionDrawable);
        generatedButton.setPosition(arrayList.get(0),arrayList.get(1));
        generatedButton.setSize(arrayList.get(2),arrayList.get(3));
    }



    public ImageButton getGeneratedButton() {
        return generatedButton;
    }
}