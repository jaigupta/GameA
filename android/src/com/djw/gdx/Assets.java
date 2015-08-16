package com.djw.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Assets {

  public static Assets singleton;

  //Fonts
  private static BitmapFont scoreFont;

  //Textures
  private static Texture texPoolBalls;
  private static Texture texPoolStick;
  private static Texture texPoolTable;
  private static Texture texArea;
  private static Texture texPixel;

  //Sound Effects
  private static Sound soundHitBall;
  private static Sound soundHitWall;
  private static Sound soundHitPoolStick;

  //Getters

  public static BitmapFont getTextFont() { return scoreFont;}

  public static Texture getTexPoolBalls(){return texPoolBalls;}
  public static Texture getTexPoolStick(){return texPoolStick;}
  public static Texture getTexPoolTable(){return texPoolTable;}
  public static Texture getTexArea(){return texArea;}
  public static Texture getTexPixel(){return texPixel;}

  public static Sound getSoundHitBall(){ return soundHitBall;}
  public static Sound getSoundHitWall(){ return soundHitWall;}
  public static Sound getSoundHitPoolStick() { return soundHitPoolStick;}


  public Assets(){

    singleton = this;

    //Load our game assets here;

    //Load Bitmap Fonts
    scoreFont = new BitmapFont(Gdx.files.internal("scoreFont.fnt"));

    //Load Textures
    texPoolBalls = new Texture(Gdx.files.internal("PoolBalls.png"));
    texPoolStick = new Texture(Gdx.files.internal("PoolStick.png"));
    texPoolTable = new Texture(Gdx.files.internal("poolTable.png"));
    texArea = new Texture(Gdx.files.internal("area.png"));
    texPixel = new Texture(Gdx.files.internal("pixel.png"));

    //Load Sounds
    soundHitBall = Gdx.audio.newSound(Gdx.files.internal("HitBall.wav"));
    soundHitWall = Gdx.audio.newSound(Gdx.files.internal("HitWall.wav"));
    soundHitPoolStick = Gdx.audio.newSound(Gdx.files.internal("HitCue.wav"));
  }

  public void dispose(){

    scoreFont.dispose();

    texPoolBalls.dispose();
    texPoolStick.dispose();
    texPoolTable.dispose();
    texArea.dispose();
    texPixel.dispose();

    soundHitBall.dispose();
    soundHitWall.dispose();
    soundHitPoolStick.dispose();

  }
}