package com.enesbulut.survivorbird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class TextGenerator {
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter params;
    private BitmapFont generatedText;




    public TextGenerator(Color color, int size){
        this.fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arcade.ttf"));
        this.params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.characters = "0123456789 Score Bst: Yu Gam Ov Nw!";
        params.color = color;
        params.size = size;
        generatedText = fontGenerator.generateFont(params);
    }

    public float calculateMid(String text){
        GlyphLayout layout = new GlyphLayout();
        layout.setText(generatedText, text); // Metnin boyutlarını hesapla
        float textWidth = layout.width;
        float textX = (Gdx.graphics.getWidth() - textWidth) / 2;
        return textX;
    }
    public void drawText(SpriteBatch batch,String text,float x, float y){
        generatedText.draw(batch,text,x,y);
    }

}
