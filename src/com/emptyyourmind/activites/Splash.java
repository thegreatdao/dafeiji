package com.emptyyourmind.activites;

import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.opengl.texture.source.AssetTextureSource;
import org.anddev.andengine.opengl.texture.source.ITextureSource;
import org.anddev.andengine.ui.activity.BaseSplashActivity;

import android.app.Activity;

public class Splash extends BaseSplashActivity
{

	private static final int SPLASH_DURATION = 3;

	@Override
	protected ScreenOrientation getScreenOrientation()
	{
		return ScreenOrientation.LANDSCAPE;
	}

	@Override
	protected ITextureSource onGetSplashTextureSource()
	{
		return new AssetTextureSource(this, "gfx/splash.png");
	}

	@Override
	protected float getSplashDuration()
	{
		return SPLASH_DURATION;
	}

	@Override
	protected Class<? extends Activity> getFollowUpActivity()
	{
		return MainScreen.class;
	}

}
