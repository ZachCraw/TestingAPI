import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class graphic implements ActionListener {
    JFrame base = new JFrame();
    ImageIcon icon = new ImageIcon("photo1.png");
    ArrayList<JLabel> labellist = new ArrayList<>();
    ArrayList<JPanel> panellist = new ArrayList<>();
    ArrayList<JButton> buttonlist = new ArrayList<>();
    int buttonpointer = 0;
    ArrayList<JTextField> textlist = new ArrayList<>();
    int textfieldpointer = 0;
    ArrayList<JTextArea> arealist = new ArrayList<>();
    int textareapointer = 0;

    public boolean button1 = false;
    public boolean button2 = false;
    public boolean button3 = false;
    public String response1 = "";
    public String response2;


    String[] locations = {"North","South","Center","East","West"};//top bottom center left right

    public graphic(){
    }




    public void init(String title,int x,int y,Color bgcolor){
        base.setTitle(title);
        base.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        base.setResizable(true);
        base.setSize(x,y);
        base.setIconImage(icon.getImage());
        base.getContentPane().setBackground(bgcolor);//change color of background
    }
    public void init(String title,Color bgcolor){
        base.setTitle(title);
        base.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        base.setExtendedState(JFrame.MAXIMIZED_BOTH);

        base.setIconImage(icon.getImage());
        base.getContentPane().setBackground(bgcolor);//change color of background
    }

    public void toggleframe(boolean toggle){
        base.pack();
        base.setVisible(toggle);
    }

    public void createlabel(String text,int size,Color txtcolor,Color bgcolor,Color bdcolor){
        JLabel label = new JLabel();
        Border border = BorderFactory.createLineBorder(bdcolor,3);//produces boarder obj
        label.setForeground(txtcolor);
        label.setFont(new Font("Arial", Font.PLAIN,size));

        label.setBorder(border);//creates boarder based on boarder obj
        label.setBackground(bgcolor);//changes colour
        label.setOpaque(true);//allows colour to be seen

        label.setText(text); //set text of label
        labellist.add(label);
        base.add(label);
    }

    public void createlabel(String text,int size,Color txtcolor, String ImagePATH,int hdisp,int vdisp,int gap,Color bgcolor,Color bdcolor){
        JLabel label = new JLabel();
        ImageIcon icon = new ImageIcon(ImagePATH);
        Border border = BorderFactory.createLineBorder(bdcolor,3);//produces boarder obj

        label.setHorizontalTextPosition(hdisp);//moves text LEFT,CENTRE,RIGHT
        label.setVerticalTextPosition(vdisp);//moves text TOP CENTRE,BOTTOM

        label.setForeground(txtcolor);//text colour
        label.setFont(new Font("Arial", Font.PLAIN,size));//text font and size
        label.setIconTextGap(gap);//gap between photo and text -20 is super close 0 is standard

        label.setBackground(bgcolor);//changes colour
        label.setOpaque(true);//allows colour to be seen

        label.setBorder(border);//creates boarder based on boarder obj

        label.setVerticalAlignment(JLabel.CENTER);//changes label vertical positon
        label.setHorizontalAlignment(JLabel.CENTER);//changes label horizonal position

        //manual location setup if base.setlayout(null)
        //label.setBounds(0,0,250,250);//top left at 0,0 which is top left of frame

        //frame.pack() frame auto adjust to fit labels,ensure all labeles are added before .pack() is used

        label.setText(text); //set text of label
        label.setIcon(icon);
        labellist.add(label);
        base.add(label);
    }

    public void createpanel(Color bgcolor,Color bdcolor,int xsize,int ysize){
        JPanel panel = new JPanel();//holds components
        panel.setLayout(new FlowLayout());
        panel.setPreferredSize(new Dimension(xsize,ysize));
        panel.setBackground(bgcolor);
        panel.setBorder(BorderFactory.createLineBorder(bdcolor));
        //panel.setBounds(bounds[0],bounds[1],bounds[2],bounds[3]);//if layout manager is null

        //panel.setLayout(new BorderLayout()); // for top right centre commands
        //to used set bounds you need setLayout(null);
        addlabeltopanel(panel);
        addbuttontopanel(panel);
        addtextfieldtopanel(panel);
        addtextareatopanel(panel);

        panellist.add(panel);


        //once you have a label.
        //panel.add(label); //add components by row centre first
    }


    public void addlabeltopanel(JPanel panel){
        for(int i = 0; i< labellist.size();i++){
            panel.add(labellist.get(i));
        }
        labellist.clear();
    }

    public void addbuttontopanel(JPanel panel){
        for(int i = buttonpointer; i<(buttonlist.size()-buttonpointer);i++){
            panel.add(buttonlist.get(i));
        }

    }

    public void addtextfieldtopanel(JPanel panel){
        for(int i = textfieldpointer; i< (textlist.size()-textfieldpointer);i++){
            panel.add(textlist.get(i));
        }

    }
    public void addtextareatopanel(JPanel panel){
        for(int i = textareapointer; i< (arealist.size()-textareapointer);i++){
            panel.add(arealist.get(i));
        }

    }





    public void createbutton(String text,Color txtcolor,int txtsize,Color bgcolor,Color bdcolor,int xsize,int ysize){
        JButton button = new JButton();
        button.setFont(new Font("Arial",Font.PLAIN,txtsize));
        button.setForeground(txtcolor); //text colour
        button.setBackground(bgcolor);

        button.setBorder(BorderFactory.createLineBorder(bdcolor));
        button.setSize(xsize,ysize);
        button.addActionListener(this);
        button.setText(text);
        button.setFocusable(false);
        //button.addActionListener(e -> output);//no function override needed
        buttonlist.add(button);

    }

    public void createbutton(String text,String PATH){
        JButton button = new JButton();
        ImageIcon icon = new ImageIcon(PATH);
        button.setIcon(icon);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.BOTTOM);
        button.setIconTextGap(-15);

        button.setFont(new Font("Arial",Font.PLAIN,25));
        button.setForeground(new Color(0,0,0)); //text colour
        button.setBackground(Color.gray);

        button.setBorder(BorderFactory.createLineBorder(Color.black));

        button.setBounds(0,0,100,100);
        //button.addActionListener(this);
        button.setText(text);
        button.setFocusable(false);

        //button.addActionListener(e -> output);//no function override needed
        buttonlist.add(button);
        base.add(button);
    }










    public void framelayoutmanager(String type) {
        switch (type) {
            case "null":
                base.setLayout(null);
                break;
            case "border":
                base.setLayout(new BorderLayout());
                for (int i = 0; i < panellist.size(); i++) {
                    if (panellist.get(i) != null) {
                        panellist.get(i).setPreferredSize(new Dimension(100, 100));
                        base.add(panellist.get(i), locations[i]);
                        //The width and height is constant for all borders, this can be changed
                    }
                }
                panellist.clear();
                break;
            case "flow":
                base.setLayout(new FlowLayout());
                //base.setLayout(new FlowLayout(FlowLayout.Trailing,int Hspace,int Vspace));
                for (int i = 0; i < panellist.size(); i++) {
                    if (panellist.get(i) != null) {
                        base.add(panellist.get(i));
                        //The width and height is constant for all borders, this can be changed
                    }
                }
                panellist.clear();
                break;
            case "grid":
                base.setLayout(new GridLayout());
                //base.setLayout(new GridLayout(int rows,int col,int Hspace,Vspace));
                for (int i = 0; i < panellist.size(); i++) {
                    if (panellist.get(i) != null) {
                        base.add(panellist.get(i));
                        //The width and height is constant for all borders, this can be changed
                    }
                }
                panellist.clear();
                break;
        }
    }





    public void createoption(){


        //JOptionPane.showMessageDialog(null,"here is your message","the title",JOptionPane.PLAIN_MESSAGE);//JOptionPane.different types
        //int result = JOptionPane.showConfirmDialog(null,"message","title",JOptionPane.YES_NO_CANCEL_OPTION);//0 for yes 1 for no 2 for idk
        //String name = JOptionPane.showInputDialog("wat is youre name?");
        //JOptionPane.showOptionDialog(parent,message,title,optiontype,message type,icon,buttonoptions String[],initalvalue);

    }
    public void createtextfield(int xsize,int ysize,boolean edit,String pretext){
        JTextField text = new JTextField();
        text.setPreferredSize(new Dimension(xsize,ysize));
        text.setEditable(edit);
        text.setText(pretext);
        textlist.add(text);

    }

    public void createtextarea(int xsize,int ysize,boolean edit,String pretext){
        JTextArea area = new JTextArea();
        area.setPreferredSize(new Dimension(xsize,ysize));
        area.setEditable(edit);
        area.setText(pretext);
        arealist.add(area);

    }
    public void createtextarea(int xsize,int ysize,boolean edit){
        JTextArea area = new JTextArea();
        area.setPreferredSize(new Dimension(xsize,ysize));
        area.setFont(new Font("Arial",Font.PLAIN,12));
        area.setEditable(edit);
        arealist.add(area);

    }
    public void inserttext(String addition,JTextArea field){
        field.setText(addition);
    }
    public void inserttext(String addition,JTextField field){
        field.setText(addition);
    }
    public void cleartext(JTextArea field){
        field.setText("");
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == buttonlist.get(0)){
            this.button1 = true;
            this.response1 = textlist.get(0).getText();
            buttonlist.get(0).setEnabled(false);
            this.button1 = false;//disables button after use
        }
        if(e.getSource() == buttonlist.get(1)){
            this.button2 = true;
            buttonlist.get(0).setEnabled(true);
            this.button2 = false;
        }
    }
}
