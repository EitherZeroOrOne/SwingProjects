// Name: Kazarian, Narek
// Project : 3
// Due: date: May 20, 2016
// Course: cs-245-01-sp16
// Description:
// A simple Rolodex program that stores its information
// in the form of tabs and has multiple different features

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class Rolodex implements ActionListener {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new Rolodex();
      }
    });
  }

  JTabbedPane tabbedPane;
  static int fileLen = fileLength();
  static String array[][] = new String[fileLen][3];


  Rolodex() {
    readFile();
    //System.out.println("Size of array is: " + fileLen);
    JFrame frame = new JFrame("Rolodex");
    frame.setIconImage(Toolkit.getDefaultToolkit().getImage("Rolodex.png"));
    frame.setLayout(new GridLayout());
    frame.setSize(700, 400);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    JMenuBar menuBar = new JMenuBar();

    JMenu fileMenu = new JMenu("File");
    fileMenu.setMnemonic('F');

    JMenuItem menuItemOpen = new JMenuItem("Open", 'O');
    fileMenu.add(menuItemOpen);
    menuItemOpen.setEnabled(false);

    fileMenu.addSeparator();

    JMenuItem find = new JMenuItem("Find", 'F');
    fileMenu.add(find);
    // CTRL + F
    find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));

    fileMenu.addSeparator();

    JMenuItem menuItemExit = new JMenuItem("Exit", 'x');
    fileMenu.add(menuItemExit);
    JMenu tabMenu = new JMenu("Tabs");
    tabMenu.setMnemonic('T');

    JMenu placementMenu = new JMenu("Placement");
    placementMenu.setMnemonic('P');

    JMenuItem top = new JMenuItem("Top", 'T');
    placementMenu.add(top);
    JMenuItem right = new JMenuItem("Right", 'R');
    placementMenu.add(right);
    JMenuItem bottom = new JMenuItem("Bottom", 'B');
    placementMenu.add(bottom);
    JMenuItem left = new JMenuItem("Left", 'L');
    placementMenu.add(left);

    tabMenu.add(placementMenu);

    JMenu layoutMenu = new JMenu("Layout");
    layoutMenu.setMnemonic('L');
    tabMenu.add(layoutMenu);
    tabMenu.addSeparator();
    JMenuItem scroll = new JMenuItem("Scroll", 'S');
    layoutMenu.add(scroll);
    JMenuItem wrap = new JMenuItem("Wrap", 'W');
    layoutMenu.add(wrap);
    JMenuItem defaults = new JMenuItem("Defaults", 'D');
    tabMenu.add(defaults);

    // CTRL + D
    defaults.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));

    JMenu help = new JMenu("Help");
    help.setMnemonic('H');

    JMenuItem about = new JMenuItem("About", 'A');
    help.add(about);
    menuBar.add(fileMenu);
    menuBar.add(tabMenu);
    menuBar.add(help);

    menuItemOpen.setEnabled(false);

    /*
    ActionListener for all the menu items
    */

    menuItemExit.addActionListener(this);
		top.addActionListener(this);
		bottom.addActionListener(this);
		left.addActionListener(this);
		right.addActionListener(this);
		wrap.addActionListener(this);
		scroll.addActionListener(this);
		defaults.addActionListener(this);
		about.addActionListener(this);
    find.addActionListener(this);

    tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
    JLabel firstLabel = new JLabel("<HTML>Kazarian, Narek<BR> nkazarian@cpp.edu</HTML>");
    firstLabel.setHorizontalAlignment(SwingConstants.CENTER);
    tabbedPane.addTab("Kazarian, Narek", firstLabel);

    ImageIcon myIcon;
		File icnImage;
    /*
    for (int i = 0; i < array.length; i++) {
      for (int j = 0; j < 3; j++) {
        System.out.println(array[i][j]);
      }
    }
    */
		for (int a = 0; a < fileLen; a++) {
        icnImage = new File(array[a][2]);
			if (icnImage.exists()) {
        myIcon = new ImageIcon(array[a][2]);
			}
			else
				myIcon = new ImageIcon("nopic.jpg");
			tabbedPane.addTab(array[a][0], new JLabel("<HTML>Name: " + array[a][0] + "<BR> Email: " + array[a][1], myIcon, SwingConstants.CENTER));
		}
		frame.setJMenuBar(menuBar);
		frame.add(tabbedPane);
		frame.setVisible(true);
  }

  public void actionPerformed(ActionEvent ae) {
    String command = ae.getActionCommand();

    if (command.equals("About")) {
      JOptionPane.showMessageDialog(null, "<html> <EM>App</EM> <B>Rolodex version 0.1</B> <BR><EM>Icon</EM> <B>Copyright (c) 2016</B>  <EM>N. Kazarian</EM>");
    }
    if (command.equals("Top")) {
			tabbedPane.setTabPlacement(JTabbedPane.TOP);
		}
		if (command.equals("Left")) {
			tabbedPane.setTabPlacement(JTabbedPane.LEFT);
		}
		if (command.equals("Right")) {
			tabbedPane.setTabPlacement(JTabbedPane.RIGHT);
		}
		if (command.equals("Bottom")) {
			tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
		}
    if (command.equals("Find")) {
      // Returns a string. Need to switch to tab associated with string.
      String name = JOptionPane.showInputDialog("Enter tab that you want to switch to");
      // Grabs index of tab by referring to title
      int tabIndex = tabbedPane.indexOfTab(name);
      // Switch to tab at that tabIndex
      tabbedPane.setSelectedIndex(tabIndex);
    }
    if (command.equals("Scroll")) {
			tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		}
		if (command.equals("Wrap")) {
			tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		}
    if (command.equals("Defaults")) {
			tabbedPane.setTabPlacement(JTabbedPane.TOP);
			tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		}
    if (command.equals("Exit")) {
      System.exit(0);
    }
  }

  public static int fileLength() {
    int numOfLines = 0;
    File file = new File("contacts.txt");
    if (file.exists()) {
      Scanner sc;
      try {
        sc = new Scanner(file);
        while (sc.hasNextLine()) {
          String readString = sc.nextLine();
          if (!readString.equals("")) {
            numOfLines++;
          }
        }
        sc.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("Could not find file");
      System.exit(0);
    }

    return numOfLines;
  }

  public static void readFile() {
    File file = new File("contacts.txt");
    Scanner inputFile;
    int a;
    try {
      inputFile = new Scanner(file);
      a = 0;
      while (inputFile.hasNextLine()) {
        String read = inputFile.nextLine();
        if (read.equals("")) {
          //System.out.println("A is: " + a);
          continue;
        }
        String[] info = read.split("~");
        //System.out.println(info.length);
        /*
        for (int j = 0; j < info.length; j++) {
          System.out.println(info[j]);
        }
        */
        //System.out.println("Info[0]: " + info[0]);
        array[a][0] = info[0];
        //System.out.println("Array[a][0]: " + array[0][0]);
        array[a][1] = info[1];
        //System.out.println("Info[1]: " + info[1]);
        //System.out.println("Array[a][0]: " + array[0][1]);
        array[a][2] = info[2];
        a++;
      }
      inputFile.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
