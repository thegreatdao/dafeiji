package com.emptyyourmind.enums;

public enum Direction
{
	UP(0), RIGHT(90), DOWN(180), LEFT(-90);
	
	private int degree;
	
	private Direction(int degree)
	{
		this.degree = degree;
	}
	
	public int getDegree()
	{
		return degree;
	}
}
