package appu26j;

import appu26j.assets.Assets;
import appu26j.gui.font.FontRenderer;
import appu26j.gui.screens.GUIInstaller;
import appu26j.gui.screens.GUIScreen;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.DoubleBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public enum AppleClientInstaller
{
	INSTANCE;

	private GUIScreen currentScreen = new GUIInstaller();
	private final int width = 1024, height = 576;
	private float mouseX = 0, mouseY = 0;
	private boolean windowHidden = false;
	private long window = 0;
	
	public void start()
	{
		this.initializeWindow();
		this.setupOpenGL();
		this.loop();
	}
	
	private void initializeWindow()
	{
		Assets.loadAssets();
		GLFWErrorCallback.createPrint(System.err).set();
		
		if (!glfwInit())
		{
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		glfwWindowHint(GLFW_FOCUS_ON_SHOW, GLFW_TRUE);
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		this.window = glfwCreateWindow(this.width, this.height, "Apple Client Installer", 0, 0);
		
		if (this.window == 0)
		{
			throw new IllegalStateException("Unable to create the GLFW window");
		}
		
		glfwMakeContextCurrent(this.window);
		glfwSwapInterval(1);
		
		glfwSetMouseButtonCallback(this.window, new GLFWMouseButtonCallback()
		{
		    public void invoke(long window, int button, int action, int mods)
		    {
		    	if (!AppleClientInstaller.this.windowHidden)
				{
					if (action == 1)
					{
						AppleClientInstaller.this.currentScreen.mouseClicked(button, AppleClientInstaller.this.mouseX, AppleClientInstaller.this.mouseY);
					}

					else
					{
						AppleClientInstaller.this.currentScreen.mouseReleased(button, AppleClientInstaller.this.mouseX, AppleClientInstaller.this.mouseY);
					}
				}
		    }
		});

		ImageParser imageParser1 = ImageParser.loadImage(Assets.getAsset("icon_16x16.png"));
		ImageParser imageParser2 = ImageParser.loadImage(Assets.getAsset("icon_32x32.png"));

		try (GLFWImage glfwImage1 = GLFWImage.malloc(); GLFWImage glfwImage2 = GLFWImage.malloc(); GLFWImage.Buffer imageBuffer = GLFWImage.malloc(2))
		{
			glfwImage1.set(imageParser1.getWidth(), imageParser1.getHeight(), imageParser1.getImage());
			glfwImage2.set(imageParser2.getWidth(), imageParser2.getHeight(), imageParser2.getImage());
			imageBuffer.put(0, glfwImage1);
			imageBuffer.put(1, glfwImage2);
			glfwSetWindowIcon(this.window, imageBuffer);
		}
	}
	
	private void setupOpenGL()
	{
		GL.createCapabilities();
		glClearColor(1, 1, 1, 1);
		glLoadIdentity();
		glViewport(0, 0, this.width, this.height);
		glOrtho(0, this.width, this.height, 0, 1, 0);
	}
	
	private void loop()
	{
		this.currentScreen.initGUI(this.width, this.height);
		glfwShowWindow(this.window);
		
		while (!glfwWindowShouldClose(this.window))
		{
			if (this.windowHidden)
			{
				break;
			}

			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
			if (!this.windowHidden)
			{
				try (MemoryStack memoryStack = MemoryStack.stackPush())
				{
					DoubleBuffer mouseX = memoryStack.mallocDouble(1);
					DoubleBuffer mouseY = memoryStack.mallocDouble(1);
					glfwGetCursorPos(this.window, mouseX, mouseY);
					this.mouseX = (float) mouseX.get();
					this.mouseY = (float) mouseY.get();
				}

				this.currentScreen.drawScreen(this.mouseX, this.mouseY);
			}

			glfwSwapBuffers(this.window);
			glfwPollEvents();
		}
		
		this.currentScreen.getFontRenderers().forEach(FontRenderer::shutdown);
		glfwDestroyWindow(this.window);
		glfwTerminate();
		Objects.requireNonNull(glfwSetErrorCallback(null)).free();
	}
	
	public void displayGUIScreen(GUIScreen guiScreen)
	{
		if (guiScreen != null)
		{
			guiScreen.initGUI(this.width, this.height);
		}
		
		this.currentScreen = guiScreen;
	}
	
	public GUIScreen getCurrentScreen()
	{
		return this.currentScreen;
	}
	
	public long getWindowID()
	{
		return this.window;
	}

	public boolean isWindowHidden()
	{
		return this.windowHidden;
	}

	public void setWindowHidden(boolean windowHidden)
	{
		this.windowHidden = windowHidden;
	}
}
