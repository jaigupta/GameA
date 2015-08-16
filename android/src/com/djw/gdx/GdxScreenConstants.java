package com.djw.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

/**
 *  A Display Class to handle all of our screen calculation needs.
 */
public class GdxScreenConstants {

  public static final float SCREEN_FACTOR = 1.0f;// 1.5f; //We will use this number later

  public static final float SCREEN_WIDTH = Gdx.graphics.getWidth() * SCREEN_FACTOR;       //How wide the screen is

  public static final float SCREEN_HEIGHT = Gdx.graphics.getHeight() * SCREEN_FACTOR;     //How tall the screen is

  public static final float X_PERCENT = SCREEN_WIDTH / 100.0f;   //1% of the screen width

  public static final float Y_PERCENT = SCREEN_HEIGHT / 100.0f;  //1% of the screen height

  private static final float aspectRatio = SCREEN_HEIGHT / SCREEN_WIDTH;

  private static final float xTextPercent = 10;
  private static final float yTextPercent = aspectRatio * xTextPercent;
  private static final float wTextPercent = 100 - (2 * xTextPercent);
  private static final float hTextPercent = 100 - (2 * yTextPercent);

  private static final Rectangle monitorRect = new Rectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

  private static final Rectangle textAreaRect = calculateRectangleLocation(monitorRect, xTextPercent, yTextPercent, wTextPercent, hTextPercent);

  public static Rectangle getMonitorRect(){
    return monitorRect;
  }

  public static Rectangle getTextAreaRect(){
    return textAreaRect;
  }

  public static float getPercentage(float percentage, float totalValue){
    return (totalValue * percentage) / 100;
  }

  public static float getValuePercent(float sourceValue, float totalValue){
    return (100 * sourceValue / totalValue);
  }

  public static Rectangle calculateRectangleLocation(
      Rectangle rectangle, float xPosPercent, float yPosPercent, float widthPercent, float heightPercent){
    return new Rectangle(
        rectangle.getX() + getPercentage(xPosPercent, rectangle.getWidth()),
        rectangle.getY() + getPercentage(yPosPercent, rectangle.getHeight()),
        getPercentage(widthPercent, rectangle.getWidth()),
        getPercentage(heightPercent, rectangle.getHeight()));
  }

  public static float calculateXPos(float xPosPercent){
    return monitorRect.getX() + getPercentage(xPosPercent, monitorRect.getWidth());
  }

  public static float calculateYPos(float yPosPercent){
    return monitorRect.getY() + getPercentage(yPosPercent, monitorRect.getHeight());
  }

  public static float calculateXPos(Rectangle rectangle, float xPosPercent){
    return rectangle.getX() + getPercentage(xPosPercent, rectangle.getWidth());
  }

  public static float calculateYPos(Rectangle rectangle, float yPosPercent){
    return rectangle.getY() + getPercentage(yPosPercent, rectangle.getHeight());
  }
}