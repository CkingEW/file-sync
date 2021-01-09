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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

@SuppressWarnings("unchecked")
public class Server_Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private final String label_str[] = new String[] {"数据资源所属IP：", "端口号：", "同步路径："};
	private Font font = null;
    private JComponent[] jcs = new JComponent[3];
	private JButton button, button2;
	private JTextArea jta = null;
	
	public Server_Frame(String title) {
		super(title);
		
        int x = 15, width = 500, height = 520, fontsize = 18;
    	Border b2 = BorderFactory.createEmptyBorder(2, 2, 2, 2), b1 = BorderFactory.createLineBorder(Color.lightGray);
    	Border b3 = BorderFactory.createCompoundBorder(b1, b2);
		
		try {
			font = new Font(Font.createFont(Font.PLAIN, new File("fonts\\Deng.ttf")).getFamily(),Font.PLAIN,fontsize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			font = new Font("宋体", Font.PLAIN, fontsize);
		}
		
		button = new JButton("开启同步服务");
    	button.setFont(font);
    	button.setBackground(Color.white);
    	button.setFocusPainted(false);
    	
    	button2 = new JButton("清空状态信息");
    	button2.setFont(font);
    	button2.setBackground(Color.white);
    	button2.setFocusPainted(false);
    	button2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jta != null)
					jta.setText("服务器状态信息：\n");
			}
		});
		
		JPanel jp1 = new JPanel(new BorderLayout(x, x));
		GridLayout gl = new GridLayout(3, 2, 5, 5), sgl = new GridLayout(1, 1);
		JPanel panel = new JPanel(gl);
		
        JLabel[] labels = new JLabel[3], l2 = new JLabel[3];
        JPanel[] panels = new JPanel[6];
        for(int i = 0; i < 3; i ++) {
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
        
        jp1.add(BorderLayout.NORTH, panel);
        jp1.add(BorderLayout.CENTER, button);
        
        jta = new JTextArea();
        jta.setEditable(false);
        jta.setFont(font);
        addString("服务器状态信息：");
        JPanel pa = new JPanel();
        pa.setLayout(new BorderLayout());
		pa.add(BorderLayout.CENTER, jta);
        JScrollPane jsp = new JScrollPane();
        jsp.setViewportView(pa);
        
    	JPanel jp = new JPanel();
    	jp.setLayout(new BorderLayout(x, x));
    	jp.setBorder(BorderFactory.createEmptyBorder(x, x, x, x));
    	jp.add(BorderLayout.NORTH, jp1);
        jp.add(BorderLayout.CENTER, jsp);
        jp.add(BorderLayout.SOUTH, button2);
        
    	setContentPane(jp);
        setBounds(((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() - width)/2, ((int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() - height)/2, width, height);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
	}
	
	public String getHostIP() {
		return ((JTextField)jcs[0]).getText();
	}
	
	public int getPort() {
		int a = 0;
		try {
			a = Integer.parseInt(((JTextField)jcs[1]).getText());
		}catch(Exception e) {}
		return a;
	}
	
	public String getSyncPath() {
		return ((JTextField)jcs[2]).getText();
	}
	
	public void setListener(ActionListener l) {
		button.addActionListener(l);
	}
	
	public void addString(String str) {
		jta.append(str+"\n");
	}
	
	public static void main(String[] args) {
		Server_Frame mf = new Server_Frame("测试");
		mf.setListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println(mf.getHostIP());
			}
		});
	}
}
