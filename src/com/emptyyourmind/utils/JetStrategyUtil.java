package com.emptyyourmind.utils;

import java.util.List;

import org.anddev.andengine.entity.sprite.AnimatedSprite;

public class JetStrategyUtil
{
	public static final String SPRITE_SUB_MENU_MENU_ITEM = "sub_menu.png";
	public static final String SPRITE_GRID_MENU_ITEM = "grid_menu.png";
	public static final String SPRITE_HEALTH_BAR_MENU_ITEM = "health_bar_menu.png";
	public static final String SPRITE_MENU_ITEM_BASE = "menu_item_base.png";
	public static final String SPRITE_UFO = "ufo.png";
	public static final String SPRITE_SKULL = "skull.png";
	public static final String SPRITE_BUDDHA = "buddha.png";
	public static final String SPRITE_DUDDHA_STAR = "buddha_star.png";
	public static final String SPRITE_SKULL_STAR = "skull_star.png";
	public static final String SPRITE_UFO_FIRE = "ufo_fire.png";
	public static final String SPRTE_PLAYER_ONE = "p1.png";
	public static final String SPRITE_PLAYER_TWO = "p2.png";
	public static final String SPRITE_HUD_HEALTH_BAR_BORDER = "healthbar_border.png";
	public static final String SPRITE_HUD_HEALTH_BAR = "healthbar.png";
	public static final String SPRITE_HUD_VS = "vs.png";
	public static final String SPRITE_JET = "jet.png";
	public static final String SPRITE_FLAME_RED = "flame_red.png";
	public static final String SPRITE_FLAME_BLUE = "flame_blue.png";
	public static final String SPRITE_FLAME_PURPLE = "flame_purple.png";
	public static final String SPRITE_BG_UFO_CITY = "ufo_city.png";
	public static final String SPRITE_BG_SKULL_CITY = "skull_city.png";
	public static final String SPRITE_BG_BUDDHA_CITY = "buddha_city.png";
	public static final String SPRITE_ROTATE_ICON = "rotate.png";
	public static final String SPRITE_MOVE_ICON = "move.png";
	public static final String[] FLAMES = new String[]{SPRITE_FLAME_RED, SPRITE_FLAME_BLUE, SPRITE_FLAME_PURPLE};
	public static final String[] BG = new String[]{SPRITE_BG_UFO_CITY, SPRITE_BG_SKULL_CITY, SPRITE_BG_BUDDHA_CITY};
	public static final long SECOND_PER_FRAME_FLAME = 150L;
	public static final float ALPHA_JET_CLONE = 0.6f;
	public static final float ALPHA_CLONE_END = 0.4f;
	public static final int Y_UPPER_BOUND = 90;
	public static final int CELL_SIDE_LENGTH = 60;
	public static final int NUM_OF_HORIZONTAL_CELLS = JetStrategyUtil.CAMERA_WIDTH / CELL_SIDE_LENGTH;
	public static final int NUM_OF_VERTICAL_CELLS = JetStrategyUtil.CAMERA_HEIGHT / CELL_SIDE_LENGTH;
	public static final int TIME_INTERVAL_TO_UPDATE_BG = 10;
	public static final int INIT_POSITION_X_AND_Y_FOR_JETS = -600;
	public static final int INIT_MENU_ITEM_POSITION_Y = 500;
	public static final int NUM_OF_LAYERS = 5;
	public static final int LAYER_ANIMATED_SPRITES = 0;
	public static final int LAYER_BASE = 1;
	public static final int LAYER_OBJECTS = 2;
	public static final int LAYER_HUD = 3;
	public static final int LAYER_MAIN_MENU = 4;
	public static final int CAMERA_WIDTH = 720;
	public static final int CAMERA_HEIGHT = 480;
	public static final int WIDTH_OF_MAIN_MENU_ITEM = 80;
	public static final int SPACING_MAIN_MENU_ITEM = 10;
	
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
	
	public static AnimatedSprite getControlIcon(float clickedX, float clickedY, int numOfHorCells, int numOfVerCells, int cellSideLength, List<AnimatedSprite> controlIcons)
	{
		int[] targetCellCoordinates = findTargetCellCoordinates(clickedX, clickedY, numOfHorCells, numOfVerCells, cellSideLength);
		int size = controlIcons.size();
		for(int i=0; i<size; i++)
		{
			AnimatedSprite controlIcon = controlIcons.get(i);
			if(controlIcon.getX() == targetCellCoordinates[0] && controlIcon.getY() == targetCellCoordinates[1])
			{
				return controlIcon;
			}
		}
		return null;
	}
	
}
