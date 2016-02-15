import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class MainClass extends JFrame {
	
	private static int SCORE_BOX = 0;
	private static int PUBLISH_DATE_BOX = 0;
	private static int TITLE_BOX = 0;
	private static int NU_DOWNLOAD_BOX = 0;
	private static int AGE_RANGE_BOX = 0;

	public static void main(String[] args) {
		
		new MainClass().start();
	}

	JLabel inputlbl;
	JLabel resultlbl;
	JPanel gui1,gui2,gui3;
	JCheckBox scorebox, publishDatebox,titlebox,nuDownloadbox,ageRangebox;
	
		public void start()
		{
			gui1 = new JPanel(new FlowLayout(1));
			gui2 = new JPanel(new FlowLayout(1));
			gui3 = new JPanel(new FlowLayout(1));
            gui1.setBorder(new EmptyBorder(2, 3, 2, 3));
			inputlbl=new JLabel();
			resultlbl=new JLabel();
			inputlbl.setText("	Enter CSV File Address :");
			final JTextField txt=new JTextField();
			txt.setSize(30,1);
			txt.setPreferredSize(new Dimension( 400, 24 ));
			txt.setText("D:\\Concordia\\Winter 2016\\SOEN 6461\\project\\main.csv");
			JButton but=new JButton();
			but.setText("Submit");
			add(inputlbl);
			add(resultlbl);
			resultlbl.setVisible(false);
			add(txt);
			add(but);
			gui1.add(inputlbl);
			gui1.add(txt);
			gui3.add(but);
			add(gui1);
			add(gui2);
		
			
			scorebox = new JCheckBox();
			publishDatebox = new JCheckBox();
			titlebox = new JCheckBox();
			ageRangebox = new JCheckBox();
			nuDownloadbox = new JCheckBox();
			scorebox.setText("Score");
			titlebox.setText("Title");
			publishDatebox.setText("Publish Date");
			ageRangebox.setText("Age Range");
			nuDownloadbox.setText("Number Downloads");
			gui2.add(scorebox);
			gui2.add(publishDatebox);
			gui2.add(titlebox);
			gui2.add(ageRangebox);
			gui2.add(nuDownloadbox);
			add(scorebox);
			add(publishDatebox);
			add(publishDatebox);
			add(titlebox);
			add(ageRangebox);
			add(nuDownloadbox);
			add(gui2);
			add(gui3);
			but.addActionListener(
					new ActionListener(){
						public void actionPerformed(ActionEvent e)
						{
							String result = "";
							ExtractData  ed = new ExtractData();
						
							try 
							{
								checkValueOfBoxes();
								
								result = ed.readPages(txt.getText(),SCORE_BOX,PUBLISH_DATE_BOX,
										TITLE_BOX,NU_DOWNLOAD_BOX,AGE_RANGE_BOX);
							}
							catch (Exception e1)
							{
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							resultlbl.setText(result);
							resultlbl.setVisible(true);
							gui2.add(resultlbl);
											  }
										}
								);
			setLayout(new FlowLayout());
			setSize(800,400);
			setVisible(true);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
		}

		protected void checkValueOfBoxes() {
			if (scorebox.isSelected())
			{
				SCORE_BOX = 1;

			}
			if (publishDatebox.isSelected())
			{
				PUBLISH_DATE_BOX = 1;

			}
			if (titlebox.isSelected())
			{
				TITLE_BOX = 1 ;

			}
			if (nuDownloadbox.isSelected())
			{
				NU_DOWNLOAD_BOX = 1;
			}
			if (ageRangebox.isSelected())
			{
				AGE_RANGE_BOX = 1;
			}
		}
}
