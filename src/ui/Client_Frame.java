package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

@SuppressWarnings("unchecked")
public class Client_Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private final String label_str[] = new String[] {"数据资源名：", "数据资源类型：", "数据资源所属IP：", "端口号：", "路径：", "调度方式："};
	private Font font = null;
	private final String[] items = new String[] {"即时同步", "周期同步（小时）", "周期同步（天）", "手动同步"};
    private JComponent[] jcs = new JComponent[6];
	private JButton button;
	
	public Client_Frame(String title) {
		super(title);
		
        int x = 15, width = 500, height = 360, fontsize = 18;
    	Border b2 = BorderFactory.createEmptyBorder(2, 2, 2, 2), b1 = BorderFactory.createLineBorder(Color.lightGray);
    	Border b3 = BorderFactory.createCompoundBorder(b1, b2);
		
		try {
			font = new Font(Font.createFont(Font.PLAIN, new File("fonts\\Deng.ttf")).getFamily(),Font.PLAIN,fontsize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			font = new Font("宋体", Font.PLAIN, fontsize);
		}
		
		button = new JButton("同步");
    	button.setFont(font);
    	button.setBackground(Color.white);
    	button.setFocusPainted(false);
    	button.setEnabled(false);
		jcs[5] = new JComboBox<String>(items);
		jcs[5].setBackground(Color.white);
		JComboBox<String> jcb = (JComboBox<String>)(jcs[5]);
		jcb.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				int i = jcb.getSelectedIndex();
				if(i != 3)
					button.setEnabled(false);
				else
					button.setEnabled(true);
			}
		});
		
		GridLayout gl = new GridLayout(6, 2, 5, 5), sgl = new GridLayout(1, 1);
		JPanel panel = new JPanel(gl);
		
        JLabel[] labels = new JLabel[6], l2 = new JLabel[5];
        JPanel[] panels = new JPanel[12];
        for(int i = 0; i < 6; i ++) {
        	panels[2*i] = new JPanel();
        	panels[2*i].setLayout(sgl);
        	panels[2*i].setBorder(b3);
        	panels[2*i+1] = new JPanel();
        	panels[2*i+1].setBorder(b3);
        	panels[2*i+1].setLayout(new BoxLayout(panels[2*i+1], BoxLayout.LINE_AXIS));
        	panel.add(panels[2*i]);
        	panel.add(panels[2*i+1]);
        	
        	labels[i] = new JLabel(label_str[i], JLabel.RIGHT);
        	labels[i].setBorder(BorderFactory.createLineBorder(Color.gray, 2));
        	labels[i].setFont(font);
        	panels[2*i].add(labels[i]);
        	
        	if(jcs[i] == null) {
        		jcs[i] = new JTextField();
            	jcs[i].setBorder(BorderFactory.createLineBorder(Color.gray, 2));        	
            	l2[i] = new JLabel("*");
            	l2[i].setFont(font);
            	l2[i].setForeground(Color.red);
            	panels[2*i+1].add(jcs[i]);
            	panels[2*i+1].add(l2[i]);
        	}
        	else
        		panels[2*i+1].add(jcs[i]);
        	jcs[i].setFont(font);
        }
        
    	JPanel jp = new JPanel();
    	jp.setLayout(new BorderLayout(x, x));
    	jp.setBorder(BorderFactory.createEmptyBorder(x, x, x, x));
        jp.add(BorderLayout.NORTH, panel);
        jp.add(BorderLayout.CENTER, button);
        
    	setContentPane(jp);
        setBounds(((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() - width)/2, ((int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() - height)/2, width, height);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
	}
	
	public String getHostIP() {
		return ((JTextField)jcs[2]).getText();
	}
	
	public int getPort() {
		int a = 0;
		try {
			a = Integer.parseInt(((JTextField)jcs[3]).getText());
		}catch(Exception e) {}
		return a;
	}
	
	public int getSyncType() {//	获取同步类型，0为即时同步（10s），1为按小时同步，2为按天同步，3为手动同步
		return ((JComboBox<String>)jcs[5]).getSelectedIndex();
	}
	
	public void setListener(ActionListener l) {
		button.addActionListener(l);
	}
	
//	public static void main(String[] args) {
//		Client_Frame mf = new Client_Frame("测试");
//	}
}
