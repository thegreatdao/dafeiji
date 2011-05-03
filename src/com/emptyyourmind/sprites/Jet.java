package com.emptyyourmind.sprites;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.AlphaModifier;
import org.anddev.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.anddev.andengine.entity.modifier.ParallelEntityModifier;
import org.anddev.andengine.entity.modifier.PathModifier;
import org.anddev.andengine.entity.modifier.PathModifier.Path;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.util.modifier.IModifier;

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
		this.indexes = indexes;
		this.cellSideLength = cellSideLength;
		setRotationCenter((float)(2.5 * cellSideLength), cellSideLength / 2);
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
	
	public void move(int[] target, Direction direction)
	{
		final int[] head = getHeadCoordinates();
		if(target[0] == head[0] && target[1] == head[1])
		{
			
		}
		else
		{
			move(target, head, direction);
		}
	}

	private void move(int[] target, int[] head, final Direction direction)
	{
		final Path path = new Path(2).to(getX(), getY()).to(target[0] - cellSideLength * 2, target[1]);
		float length = path.getLength();
		float time = 1f;
		if(length > cellSideLength * 4)
		{
			time = 2f;
		}
		PathModifier pathModifier = new PathModifier(time, path);
		if(this.direction.equals(direction))
		{
			registerEntityModifier(pathModifier);
		}
		else
		{
			ParallelEntityModifier startModifier = new ParallelEntityModifier(new AlphaModifier(1f, 1, 0f), new ScaleModifier(0.5f, 1, 0));
			SequenceEntityModifier sequenceEntityModifier = new SequenceEntityModifier(new IEntityModifierListener()
			{
				@Override
				public void onModifierFinished(IModifier<IEntity> modifier, IEntity entity)
				{
					ParallelEntityModifier endModifier = new ParallelEntityModifier(new AlphaModifier(1f, 0, 1f), new ScaleModifier(0.5f, 0, 1));
					setRotation(direction.ordinal() * 90);
					registerEntityModifier(endModifier);
				}
			}, pathModifier, startModifier);
			registerEntityModifier(sequenceEntityModifier);
		}
		this.direction = direction;
	}
	
	public int[] getHeadCoordinates()
	{
		return new int[]{(int)getX() + indexes[0] * cellSideLength, (int)getY() + indexes[1] * cellSideLength};
	}
}
