package appu26j.gui.screens;

import appu26j.assets.Assets;
import appu26j.gui.GUI;
import appu26j.gui.font.FontRenderer;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class GUIScreen extends GUI
{
	protected FontRenderer fontRendererBig, fontRendererMid, fontRenderer;
	protected float width = 0, height = 0;
	
	public abstract void drawScreen(float mouseX, float mouseY);
	
	public void mouseClicked(int mouseButton, float mouseX, float mouseY)
	{
		;
	}
	
	public void mouseReleased(int mouseButton, float mouseX, float mouseY)
	{
		;
	}
	
	public void initGUI(float width, float height)
	{
		this.width = width;
		this.height = height;
		this.fontRenderer = new FontRenderer(Assets.getAsset("segoeui.ttf"), 48);
		this.fontRendererMid = new FontRenderer(Assets.getAsset("segoeui.ttf"), 72);
		this.fontRendererBig = new FontRenderer(Assets.getAsset("segoeui.ttf"), 96);
	}
	
	protected boolean isInsideBox(float mouseX, float mouseY, float x, float y, float width, float height)
	{
		return mouseX > x && mouseX < width && mouseY > y && mouseY < height;
	}
	
	public ArrayList<FontRenderer> getFontRenderers()
	{
		return new ArrayList<>(Arrays.asList(this.fontRenderer, this.fontRendererMid, this.fontRendererBig));
	}
}
