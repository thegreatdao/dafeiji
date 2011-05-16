package com.emptyyourmind.utils;

import java.util.List;

import com.emptyyourmind.sprites.ControlIcon;

public class JetStrategyUtil
{
	public static final String ICON_MOVE = "move";
	public static final String ICON_ROTATE = "rotate";
	public static final int CAMERA_WIDTH = 720;
	public static final int CAMERA_HEIGHT = 480;
	
	public static int[] findTargetCellCoordinates(float clickedX, float clickedY, int numOfHorCells, int numOfVerCells, int cellSideLength)
	{
		int targetX = 0;
		int targetY = 0;
		for(int i = 0; i < numOfHorCells; i++)
		{
			targetX = i * cellSideLength;
			if(targetX <= clickedX && targetX + cellSideLength > clickedX)
			{
				i = numOfHorCells;
			}
		}
		for(int i = 0; i < numOfVerCells; i ++)
		{
			targetY = i * cellSideLength;
			if(targetY <= clickedY && targetY + cellSideLength > clickedY)
			{
				i = numOfVerCells;
			}
		}
		return new int[]{targetX, targetY};
	}
	
	public static int[] getHeadCoordinates(int leftX, int topY, int[] references)
	{
		
		return null;
	}
	
	public static ControlIcon getControlIcon(float clickedX, float clickedY, int numOfHorCells, int numOfVerCells, int cellSideLength, List<ControlIcon> controlIcons)
	{
		int[] targetCellCoordinates = findTargetCellCoordinates(clickedX, clickedY, numOfHorCells, numOfVerCells, cellSideLength);
		int size = controlIcons.size();
		for(int i=0; i<size; i++)
		{
			ControlIcon controlIcon = controlIcons.get(i);
			if(controlIcon.getX() == targetCellCoordinates[0] && controlIcon.getY() == targetCellCoordinates[1])
			{
				return controlIcon;
			}
		}
		return null;
	}
	
}
