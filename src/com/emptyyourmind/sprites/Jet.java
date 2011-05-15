package com.emptyyourmind.sprites;

import org.anddev.andengine.entity.modifier.PathModifier;
import org.anddev.andengine.entity.modifier.PathModifier.Path;
import org.anddev.andengine.entity.modifier.RotationModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.util.MathUtils;

import com.emptyyourmind.enums.Direction;

/**
 * @author Self-Less
 *
 */
public class Jet extends Sprite
{
	public static final int[] JET54_REFERENCE_POINT_UP = new int[]{2, 0};
	public static final int[] JET54_REFERENCE_POINT_LEFT = new int[]{3, 2};
	public static final int[] JET54_REFERENCE_POINT_DOWN = new int[]{2, 3};
	public static final int[] JET54_REFERENCE_POINT_RIGHT = new int[]{0, 2};
	public static final float ROTATION_TIME_UNIT = 0.25f;
	public static final float MOVE_TIME_UNIT = 0.25f;
	
	private int[] indexes;
	private int cellSideLength;
	private Direction direction = Direction.UP;

	public Jet(float pX, float pY, TextureRegion textureRegion, int[] indexes, int cellSideLength)
	{
		super(pX, pY, textureRegion);
		if(indexes == null)
		{
			throw new RuntimeException("error creating " + Jet.class.getName() + " You will have to specify the reference point");
		}
		this.indexes = indexes;
		this.cellSideLength = cellSideLength;
		setRotationCenter((float)(cellSideLength * 2.5), cellSideLength / 2);
	}
	
	public void setReferencePoints(int[] indexes)
	{
		this.indexes = indexes;
	}
	
	public int[] getReferencePoints()
	{
		return indexes;
	}
	
	public void moveTo(int[] target, Direction direction)
	{
		final int[] head = getHeadCoordinates();
		if(target[0] == head[0] && target[1] == head[1])
		{
		}
		else
		{
			moveTo(target, head, direction);
		}
	}

	private void moveTo(int[] target, int[] head, Direction direction)
	{
		double degree = getRotationDegree(target, head);
		final Path path = new Path(2).to(getX(), getY()).to(target[0] - cellSideLength * 2, target[1]);
		float length = path.getLength();
		float moveTime = 1f;
		float rotateTime = 1f;
		if(length > cellSideLength * 4)
		{
			moveTime = 1.5f;
		}
		PathModifier pathModifier = new PathModifier(moveTime, path);
		RotationModifier rotationModifier = new RotationModifier(rotateTime, this.direction.getDegree(), (float)-degree);
		RotationModifier rotationModifier2 = new RotationModifier(rotateTime,(float)-degree, direction.getDegree());
		SequenceEntityModifier entityModifier = new SequenceEntityModifier(rotationModifier, pathModifier, rotationModifier2);
		registerEntityModifier(entityModifier);
		this.direction = direction;
	}

	private double getRotationDegree(int[] target, int[] head)
	{
		final int b = -target[0] + head[0];
		final int a = -target[1] + head[1];
		return (double) MathUtils.radToDeg((float)Math.atan2((double)b, (double)a));
	}
	
	public int[] getHeadCoordinates()
	{
		return new int[]{(int)getX() + indexes[0] * cellSideLength, (int)getY() + indexes[1] * cellSideLength};
	}
}