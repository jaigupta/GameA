package com.djw.gamea;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class GameA extends ApplicationAdapter implements InputProcessor {

  private static final int METRES_TO_PIXELS = 50;
  private static final int SM_WIDTH = 50;
  private static final int SM_HEIGHT = 325;
  private static final int SM_FACE_RAIUS = 50;
  private static final int SM_LOWER_BODY_SIZE = 125;
  private static final int SM_MID_BODY_SIZE = SM_HEIGHT - 2*SM_FACE_RAIUS - SM_LOWER_BODY_SIZE;
  private static final int UPPER_INVI_SIZE = SM_HEIGHT-2*SM_FACE_RAIUS-SM_WIDTH;
  private static final float REJOIN_ACC = 0.1f;
	private float screenWidth;
	private float screenHeight;
  private SpriteBatch sb;
  private World world;
  private Body body;
  private Box2DDebugRenderer debugRenderer;
  private OrthographicCamera camera;
  private Body head, inviBodyPart;

  private static short CATEGORY_SM_BODY = 1;
  private static short CATEGORY_BASE = 1<<1;
  private static short MASK_SM_BODY = CATEGORY_BASE;
  private static short MASK_BASE = CATEGORY_SM_BODY;

	public GameA() {}

	@Override
	public void create () {
    sb = new SpriteBatch();
    this.screenWidth = Gdx.graphics.getWidth();
    this.screenHeight = Gdx.graphics.getHeight();
    world = new World(new Vector2(0, -9.8f), true);

    BodyDef smDef = new BodyDef();
    smDef.type = BodyType.DynamicBody;
    smDef.position.set(0, SM_WIDTH*2);
    inviBodyPart = world.createBody(smDef);

    PolygonShape upperInviShape = new PolygonShape();
    upperInviShape.setAsBox(
        SM_MID_BODY_SIZE / 2, /* width */
        SM_WIDTH / 2 /* height */);
    FixtureDef upperInviShapeFixture = new FixtureDef();
    upperInviShapeFixture.shape = upperInviShape;
    upperInviShapeFixture.density = 0.0001f;
    upperInviShapeFixture.filter.categoryBits = CATEGORY_SM_BODY;
    upperInviShapeFixture.filter.maskBits = MASK_SM_BODY;
    inviBodyPart.createFixture(upperInviShapeFixture);
    upperInviShape.dispose();

    PolygonShape lowerInviShape = new PolygonShape();
    Vector2 ends[] = new Vector2[6];
    for (int i = 0; i < 6; i++) {
      // divide it into 8 parts but skip the center 2 to flatten the base.
      int part = (i<3) ? i : (i+2);
      float theta = (float)(-Math.PI/2 + (Math.PI * part / 7f));
      ends[i] = new Vector2((float) (UPPER_INVI_SIZE - SM_MID_BODY_SIZE/2 + Math.cos(theta) * SM_WIDTH/2),
          (float)(Math.sin(theta) * SM_WIDTH/2));
    }

    lowerInviShape.set(ends);
    FixtureDef lowerInviShapeFixture = new FixtureDef();
    lowerInviShapeFixture.shape = lowerInviShape;
    lowerInviShapeFixture.density = 1.0f;
    lowerInviShapeFixture.filter.categoryBits = CATEGORY_SM_BODY;
    lowerInviShapeFixture.filter.maskBits = MASK_SM_BODY;
    inviBodyPart.createFixture(lowerInviShapeFixture);
    lowerInviShape.dispose();

    BodyDef headDef = new BodyDef();
    headDef.type = BodyType.DynamicBody;
    headDef.position.set(screenWidth/4, screenHeight/4);
    head = world.createBody(headDef);
    head.setGravityScale(0);

    CircleShape headShape = new CircleShape();
    headShape.setRadius(SM_FACE_RAIUS);
    FixtureDef headShapeFixture = new FixtureDef();
    headShapeFixture.shape = headShape;
    headShapeFixture.density = 0.1f;
    headShapeFixture.filter.categoryBits = CATEGORY_SM_BODY;
    headShapeFixture.filter.maskBits = MASK_SM_BODY;
    head.createFixture(headShapeFixture);
    headShape.dispose();

    BodyDef groundDef = new BodyDef();
    groundDef.type = BodyType.StaticBody;
    Body ground = world.createBody(groundDef);

    EdgeShape edgeShape = new EdgeShape();
    edgeShape.set(-screenWidth / 2, 0, screenWidth / 2, 0);
    FixtureDef edgeShapeFixture = new FixtureDef();
    edgeShapeFixture.shape = edgeShape;
    edgeShapeFixture.density = 0.01f;
    edgeShapeFixture.filter.categoryBits = CATEGORY_BASE;
    edgeShapeFixture.filter.maskBits = MASK_BASE;
    ground.createFixture(edgeShapeFixture);
    edgeShape.dispose();

    debugRenderer = new Box2DDebugRenderer();
    Gdx.input.setInputProcessor(this);
    camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void render () {
    Gdx.gl.glClearColor(1, 1, 1, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    camera.update();
    head.setLinearVelocity(
        inviBodyPart.getPosition()
            .add(new Vector2(0, SM_FACE_RAIUS + SM_MID_BODY_SIZE))
            .sub(head.getPosition())
            .limit(300));
    // Step the physics simulation forward at a rate of 60hz
    world.step(1f / 60f, 6, 2);

    debugRenderer.render(world, camera.combined);

	}

  @Override
  public boolean keyDown(int keycode) {
    return false;
  }

  @Override
  public boolean keyUp(int keycode) {
    return false;
  }

  @Override
  public boolean keyTyped(char character) {
    return false;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    return false;
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    return false;
  }

  @Override
  public boolean scrolled(int amount) {
    return false;
  }
}
