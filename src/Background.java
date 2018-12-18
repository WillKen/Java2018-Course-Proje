import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class Background extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image image;// 背景图片
	
	public Background() {
		setOpaque(false);
		setLayout(null);
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
	
	protected void paintComponent(Graphics g) {
		if (image != null) {
			// 图片宽度
			int width = getWidth();
			// 图片高度
			int height = getHeight();
			// 画出图片
			g.drawImage(image, 0, 0, width, height, this);
		}
		super.paintComponent(g);
	}
}