package com.emptyyourmind.utils;

public class JetStrategyUtil
{
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
}
