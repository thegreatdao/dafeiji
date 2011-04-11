package com.emptyyourmind.sprites;

import org.anddev.andengine.entity.modifier.IEntityModifier;
import org.anddev.andengine.entity.modifier.PathModifier;
import org.anddev.andengine.entity.modifier.PathModifier.Path;
import org.anddev.andengine.entity.modifier.RotationModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.util.MathUtils;

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

	public Jet(float pX, float pY, TextureRegion textureRegion, int[] indexes, int cellSideLength)
	{
		super(pX, pY, textureRegion);
		this.indexes = indexes;
		this.cellSideLength = cellSideLength;
		if(indexes == null)
		{
			throw new RuntimeException("error creating " + Jet.class.getName() + " You will have to specify the reference point");
		}
	}
	
	public void setReferencePoints(int[] indexes)
	{
		this.indexes = indexes;
	}
	
	public int[] getReferencePoints()
	{
		return indexes;
	}
	
	public IEntityModifier moveTo(int[] target)
	{
		final int[] head = getHeadCoordinates();
		if(target[0] == head[0] && target[1] == head[1])
		{
			return null;
		}
		else
		{
			return constructModifiers(target, head);
		}
	}

	private IEntityModifier constructModifiers(int[] target, int[] head)
	{
		final int b = -target[0] + head[0];
		final int a = -target[1] + head[1];
		double degree = MathUtils.radToDeg((float)Math.atan2((double)b, (double)a));
		final Path path = new Path(2).to(getX(), getY()).to(target[0] - cellSideLength * 2, target[1]);
		return new SequenceEntityModifier(new RotationModifier(1,0, (float)-degree), new PathModifier(3f, path),new RotationModifier(1,(float)-degree, 0));
	}
	
	public int[] getHeadCoordinates()
	{
		return new int[]{(int)getX() + indexes[0] * cellSideLength, (int)getY() + indexes[1] * cellSideLength};
	}
}
