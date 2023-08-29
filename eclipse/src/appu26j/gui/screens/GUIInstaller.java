package appu26j.gui.screens;

import appu26j.Installer;
import appu26j.gui.GUI;

import java.awt.*;

public class GUIInstaller extends GUIScreen
{
	public String status = "";
	private float x = -450;

	@Override
	public void drawScreen(float mouseX, float mouseY)
	{
		if (this.status.isEmpty())
		{
			this.fontRendererBig.drawString("Welcome!", (this.width / 2) - (this.fontRendererBig.getStringWidth("Welcome!") / 2), 2.5F, new Color(0, 0, 0));
			this.fontRenderer.drawString("Let's install Apple Client!", (this.width / 2) - (this.fontRenderer.getStringWidth("Let's install Apple Client!") / 2), 96, new Color(0, 0, 0));
			GUI.drawRect((this.width / 2) - 200, this.height - 175, (this.width / 2) + 200, this.height - 75, this.isInsideBox(mouseX, mouseY, (this.width / 2) - 200, this.height - 175, (this.width / 2) + 200, this.height - 75) ? new Color(220, 220, 220) : new Color(240, 240, 240));
			this.fontRendererMid.drawString("Install", (this.width / 2) - (this.fontRendererMid.getStringWidth("Install") / 2), this.height - 165, new Color(0, 0, 0));
		}

		else if (this.status.equals("Which version do you want to install?"))
		{
			this.fontRendererMid.drawString(this.status, (this.width / 2) - (this.fontRendererMid.getStringWidth(this.status) / 2), (this.height / 2) - 125, new Color(0, 0, 0));
			GUI.drawRect((this.width / 2) - 225, (this.height / 2) - 25, (this.width / 2) - 15, (this.height / 2) + 50, this.isInsideBox(mouseX, mouseY, (this.width / 2) - 225, (this.height / 2) - 25, (this.width / 2) - 15, (this.height / 2) + 50) ? new Color(220, 220, 220) : new Color(240, 240, 240));
			GUI.drawRect((this.width / 2) + 15, (this.height / 2) - 25, (this.width / 2) + 225, (this.height / 2) + 50, this.isInsideBox(mouseX, mouseY, (this.width / 2) + 15, (this.height / 2) - 25, (this.width / 2) + 225, (this.height / 2) + 50) ? new Color(220, 220, 220) : new Color(240, 240, 240));
			this.fontRendererMid.drawString("1.8.9", ((this.width / 2) - 120) - (this.fontRendererMid.getStringWidth("1.8.9") / 2), (this.height / 2) - 25, new Color(0, 0, 0));
			this.fontRendererMid.drawString("1.7.10", ((this.width / 2) + 120) - (this.fontRendererMid.getStringWidth("1.7.10") / 2), (this.height / 2) - 25, new Color(0, 0, 0));
		}

		else if (this.status.equals("Done!") || this.status.equals("Apple Client is already installed!"))
		{
			this.fontRendererMid.drawString(this.status, (this.width / 2) - (this.fontRendererMid.getStringWidth(this.status) / 2), 2.5F, new Color(0, 0, 0));
			this.fontRenderer.drawString("Open MC Launcher/another launcher and launch Apple Client.", (this.width / 2) - (this.fontRenderer.getStringWidth("Open MC Launcher/another launcher and launch Apple Client") / 2), 72, new Color(0, 0, 0));
		}

		else
		{
			this.fontRendererBig.drawString("Installing...", (this.width / 2) - (this.fontRendererBig.getStringWidth("Installing...") / 2), 2.5F, new Color(0, 0, 0));
			GUI.drawRect(25, 105, this.width - 25, 145, new Color(240, 240, 240));
			GUI.drawRect(Math.max(this.x, 25), 105, Math.max(Math.min(this.x + 400, this.width - 25), Math.max(this.x, 25)), 145, new Color(0, 128, 255));
			this.fontRenderer.drawString(this.status, (this.width / 2) - (this.fontRenderer.getStringWidth(this.status) / 2), 155, new Color(0, 0, 0));
			this.x += 5;

			if (this.x > (this.width - 25))
			{
				this.x = -450;
			}
		}
	}
	
	@Override
	public void mouseClicked(int mouseButton, float mouseX, float mouseY)
	{
		super.mouseClicked(mouseButton, mouseX, mouseY);

		if (this.status.isEmpty() && this.isInsideBox(mouseX, mouseY, (this.width / 2) - 200, this.height - 175, (this.width / 2) + 200, this.height - 75) && mouseButton == 0)
		{
			this.status = "Which version do you want to install?";
		}

		if (this.status.equals("Which version do you want to install?") && mouseButton == 0)
		{
			if (this.isInsideBox(mouseX, mouseY, (this.width / 2) - 225, (this.height / 2) - 25, (this.width / 2) - 15, (this.height / 2) + 50))
			{
				Installer.install18(this);
			}

			if (this.isInsideBox(mouseX, mouseY, (this.width / 2) + 15, (this.height / 2) - 25, (this.width / 2) + 225, (this.height / 2) + 50))
			{
				Installer.install17(this);
			}
		}
	}
}