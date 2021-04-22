import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.regex.*;

public class Notepad extends WindowAdapter implements ActionListener,KeyListener
{
		Frame f;
		public TextArea text;
		int l;
		MenuBar mb;
		char ch1;
		Menu m1,m2;
		public Dialog d,d1,d2,er;
		Button b1,b2,b3,b4,b5,b6,b8;
		public String filen,path,textr,s;
		public boolean isSave=false,inNew=false,inOpen=false,inExit=false,file=false,inFind=false;
		public String rep,repto;
		FileDialog fd,fd1,fd2;
		MenuItem nw,op,sve,sveas,ext,fnd,fnd_rplc;
		Panel p2,p1,p,p4;
		public TextField l1,l4;
		public int ind;
		public Window w2;
	//	WindowCloser wc;
		int count=1,k1=0;
		int _start;
		int _end;
		int  _replaceStart = 0;
		public void open(){
			try{
							fd = new FileDialog(f,"OPEN",FileDialog.LOAD);
							fd.show();
							path=fd.getDirectory();
							filen=fd.getFile();
							boolean a=!(path.equals("")&&filen.equals(""));
							file=true;
							if(a)
							{
							text.setText("");
							File f2=new File(path,filen);
							FileInputStream fis= new FileInputStream(f2);
							int ch2;
							while((ch2=fis.read())!=-1)
							text.appendText(((char)ch2) + "");
					        }
							}
						    catch(Exception ee){
								System.out.println(ee.getMessage());
							}
		}
        public void nodata()
        {
        		er=new Dialog(f,"OOPS..!!");
							er.setSize(350,150);
							WindowCloser wc4 = new WindowCloser();
                        er.addWindowListener(wc4);
							Label l5=new Label("Your Data Is Not Here.!!",Label.CENTER);
							er.add(l5);
							 b8=new Button("ok");
							er.add(b8,"South");
							er.show();
							//er.addWindowListener(this);
							b8.addActionListener(this);
		}
		public void find()
			{
				Pattern pt = Pattern.compile(l1.getText());
				Matcher mt = pt.matcher(text.getText());
				if(mt.find(_start))
				{
					System.out.println("Yes String found");
					text.select(mt.start(),mt.end());
					_replaceStart  = mt.start();
					_start = mt.end();
					f.toFront();
				}
				else
				{
					nodata();
				}

			}
			//replaceall
        public void replaceall()
        {             
        			    textr=text.getText();
        		        rep=l1.getText();
        		        System.out.print("rep"+"	"+rep);
						repto=l4.getText();
						System.out.print("repto"+"	  "+repto);
						Pattern p = Pattern.compile(rep);
				        Matcher m = p.matcher(textr);
				        if(m.find())
				        {  
				            textr=m.replaceAll(repto);
				        	text.setText(textr);
				    	    f.toFront();
				        }
				       else
						{
							nodata();
						}
				     
        }
        // replace one
        public void replaceone()
        			{
        				textr=text.getText();
        		        rep=l1.getText();
        		        repto=l4.getText();
        		        System.out.print("rep"+"	"+rep);
        		        Pattern p2 = Pattern.compile(rep);
				        Matcher m2 = p2.matcher(textr);	
				        if(m2.find(_replaceStart))
				        {
				        		if(count==1)
				        		{  
				        			text.select(m2.start(),m2.end());
						        	count++;
						        	f.toFront();
						        }
						        else
						        {	
						        	text.replaceText(repto,m2.start(),m2.end());
					 				_start = m2.end();
					 				if(m2.find(_start))
					 				{
						        	text.select(m2.start(),m2.end());
					 				f.toFront();
						            }
						            else 
						            	nodata();
						        }
				        }
				        else
						{
							nodata();
						}
					}
		public void save__as()
			{
             try{      
						fd = new FileDialog(f,"SAVE AS",FileDialog.SAVE);
						fd.show();
						path=fd.getDirectory();
						filen=fd.getFile();
						boolean a=!(path.equals("")&&filen.equals(""));
						file=a;
						s=path+filen;
						System.out.print(filen+"	"+path);
						textr=text.getText();
						l=textr.length();
						File f2=new File(s);
						FileOutputStream fos=new FileOutputStream(f2);
						for(int i=0;i<l;i++)
							{
							ch1=textr.charAt(i);
							fos.write(ch1);
							}
						isSave=true;
						fos.close();
				}
			catch(Exception ee)
				{
						System.out.println(ee.getMessage());
			    }
			}
		public void save_f()
		{
                        System.out.println("in tryyy");
                        d.dispose();
						if(!file)
								{
									save__as();
								}
						else if(isSave==false&&k1!=0)
						{
								try
							    {
									System.out.print("in tryyy 2");
									File f2=new File(s);
									FileOutputStream fos=new FileOutputStream(f2);
									textr=text.getText();
									l=textr.length();
									for(int i=0;i<l;i++)
									{
										char ch1=textr.charAt(i);
										fos.write(ch1);
									}
									fos.close();
									isSave=true;
								    d.dispose();
							    }
								catch(Exception ee)
								{
									System.out.println(ee.getMessage());
								}
						}

		} 
		public void exit(){
			 boolean n;
    inExit=true;
						n=(isSave==true);
						String em;
						em=text.getText();
						boolean a;
						a=em.equals("");
						System.out.print(a);
						if((file&&n)||(!file&&a))
						{
						f.setVisible(false);
						f.dispose();
						System.exit(1);
						}
						else
						{
							d.show();
						} 
		}
	Notepad()
	{
	//	wc=new WindowCloser();
		d=new Dialog(f,"ATTENTION");
		d.setSize(400,150); 
		WindowCloser wc = new WindowCloser();
        d.addWindowListener(wc);
		Panel p7=new Panel();
		Label t=new Label("Do you want this change to be saved in your note for life...???",Label.CENTER);
		p7.add(t);
		p2=new Panel();
		GridLayout gl2=new GridLayout(0,3);
		p2.setLayout(gl2);
		b1=new Button("Save");//forever
		b2=new Button("Don't Save");//not intrested
		b3=new Button("Close");//discard
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		p2.add(b1);
		p2.add(b2);
		p2.add(b3);
		p7.add(p2);
		d.add(p7);
		f=new Frame();
		m1=new Menu("FILE");
		m2=new Menu("EDIT");
		f.setSize(1600,1600);
		mb=new MenuBar();
		nw=new MenuItem("NEW");
		op=new MenuItem("OPEN");
		sve=new MenuItem("SAVE");
		sveas=new MenuItem("SAVE AS");
		ext=new MenuItem("EXIT");
		fnd=new MenuItem("FIND");
		fnd_rplc=new MenuItem("FIND & REPLACE");
		nw.addActionListener(this);
		op.addActionListener(this);
		sve.addActionListener(this);
		sveas.addActionListener(this);
		ext.addActionListener(this);
		fnd.addActionListener(this);
		fnd_rplc.addActionListener(this);
		m2.add(fnd);
		m2.add(fnd_rplc);
		m1.add(nw);
		m1.add(op);
		m1.add(sve);
		m1.add(sveas);
		m1.addSeparator();
		m1.add(ext);
		mb.add(m1);
		mb.add(m2);
		f.setMenuBar(mb);
		text=new TextArea();
		text.setEnabled(true);
		text.addKeyListener(this);
		f.add(text);
		f.addWindowListener(this);
		//d.addWindowListener(this);
		f.setVisible(true);
		text.setEnabled(true);
	}
	public void actionPerformed(ActionEvent e)
		{
			String sg = e.getActionCommand();
			System.out.println(sg);
			if(e.getSource()==b1)
					{ 
                      //new function
					  if(inNew==true)
						{   
							d.dispose();
							save_f();
							boolean a=!(path.equals("")&&filen.equals(""));
							if(a)
							{
							text.setText("");
							filen=null;
							path=null;
							file=false;
							inNew=false;
						    }
					    }
					    else if(inOpen==true)
					    {   
					    	d.dispose();
					        save_f();
					        inOpen=false;
					    	open();
					    }
					    else if(inExit==true)
					    {	d.dispose();
					    	save_f();
					      boolean a=!(path.equals("")&&filen.equals(""));
					       if(a)
					       {
					    	f.setVisible(false);
							f.dispose();
							inExit=false;
						    }

							//System.exit(1);
					    }
					    else
					    {
					    	save_f();
					    }
					} 
					//don't save
			if(e.getSource()==b2)
					{   if(inNew==true)
						{
						text.setText("");
						d.dispose();
						filen=null;
						path=null;
						file=false;
						inNew=false;
						}
						else if(inOpen==true)
						{
							d.dispose();
							open();
							inOpen=false;
						}
						else if(inExit==true)
					    {
						d.dispose();
						f.setVisible(false);
						f.dispose();
						inExit=false;
						}
						else
						{		
						d.dispose();
						f.setVisible(false);
						f.dispose();
						}

					}
					//close or cancel
			if(e.getSource()==b3)
					{   
						if(inNew==true)
						{
						d.dispose();
						inNew=false;
						}
						else if(inOpen==true)
						{
						d.dispose();
						inOpen=false;
						}
					    else if(inExit==true)
					    {
						d.dispose();
						inExit=false;
						}
						else if(inFind==true)
						{
							d1.dispose();
							d2.dispose();
							inFind=false;
						}
						else
						{
						d.dispose();
						}
					}
					//to find
			if(e.getSource()==b4)
					{ 
						find();
					}
					//to replace
			if(e.getSource()==b5)
					{
						replaceone();
					}
					//to replace all
			if(e.getSource()==b6)
					{
					   replaceall();
					}
			if(e.getSource()==b8)
					{
						er.dispose();
					}
			if(e.getSource()==nw)
					{   String em;
						em=text.getText();
						boolean a;
						a=em.equals("");
						System.out.print(a);
						if(isSave==true || em.equals("") || !filen.equals("") || !path.equals(""))
						{
							text.setText("");
							filen=null;
							path=null;
							file=false;
							k1=0;
						}
						else
						{
							inNew=true;
							d.show();
						}

					}
//open button
			else if(e.getSource()==op)
					{    
						System.out.println("values are " + isSave + " and  " + text.getText().length());
						if(isSave==false && text.getText().length() != 0)
						{
						inOpen=true;
						d.show();
						}
						else
							open();
					}
			else if(e.getSource()==sve)
					{
						save_f();
					}
			else if(e.getSource()==sveas)
					{
						save__as();
					}
			else if(e.getSource()==ext)
					{   exit();
					}
					//findddd
			else if(e.getSource()==fnd)
					{   inFind=true;
						d1=new Dialog(f,"FIND");
						d1.setSize(400,200);
						WindowCloser wc1 = new WindowCloser();
                        d1.addWindowListener(wc1);
						Panel p7=new Panel();
						Panel p6=new Panel();
						GridLayout gl3=new GridLayout(0,2,10,10);
		                GridLayout gl7=new GridLayout(3,0,5,5);
						Label t1=new Label("Look Around For...???");
						t1.setSize(100,50);
						p6.add(t1);
						l1=new TextField();
						l1.addKeyListener(this);
						p6.add(l1);
						p7.add(p6);
						b4=new Button("FIND NEXT");
						b4.setEnabled(false);
						b4.addActionListener(this);
						p6.setLayout(gl7);
						p7.setLayout(gl3);
						p=new Panel();
						GridLayout gl1=new GridLayout(2,0);
						p.setLayout(gl1);
						p.add(b4);
						p.add(b3);
						b3.addActionListener(this);
						p7.add(p);
						d1.add(p7);
						//d1.addWindowListener(this);
						ind=0;
						d1.show();
						
				
						String str = text.getText();
						text.setText(Pattern.compile("\r\n").matcher(str).replaceAll("\n"));

						_replaceStart   = 0;
						_start = 0;
						_end = 0;
					}
					//findd replaCE
			else if(e.getSource()==fnd_rplc)
					{   
						inFind=true;
						d2=new Dialog(f,"FIND & REPLACE");
						d2.setSize(500,200);
						Panel p3=new Panel();
						Label t1=new Label("Look Around For..nd replace it to better.???");
						p3.add(t1);
						l1=new TextField();
						l1.addKeyListener(this);
						p3.add(l1);
						rep=l1.getText();
						l4=new TextField();
						l4.addKeyListener(this);
						p3.add(l4);
						b4=new Button("FIND NEXT");
						b4.setEnabled(false);
						b5=new Button("REPLACE");
						b5.setEnabled(false);
						b6=new Button("REPLACE_ALL");
						b6.setEnabled(false);
						b3=new Button("CLOSE");
						b4.addActionListener(this);
						b3.addActionListener(this);
						b5.addActionListener(this);
						b6.addActionListener(this);
						GridLayout gl2=new GridLayout(0,2);
						p4=new Panel();
						p4.setLayout(gl2);
						p=new Panel();
						GridLayout gl1=new GridLayout(4,0,5,5);
						p.setLayout(gl1);
						p.add(b4);
						p.add(b5);
						p.add(b6);
						b4.setEnabled(false);
						b5.setEnabled(false);
						b6.setEnabled(false);
						p.add(b3);
						p4.add(p3);
						p4.add(p);
						d2.add(p4);
						WindowCloser wc2 = new WindowCloser();
                        d2.addWindowListener(wc2);
						//d2.addWindowListener(this);
						ind=0;
						d2.show();
						
						_replaceStart   = 0;
						_start = 0;
						_end = 0;
						
						String str = text.getText();
						text.setText(Pattern.compile("\r\n").matcher(str).replaceAll("\n"));
					}
		       
		   }
	public void keyPressed(KeyEvent k)
		{               if(k.getSource()==text){
						isSave=false;
						k1++;}

		}
	public void keyReleased(KeyEvent k)
		{ 

		}
	public void keyTyped(KeyEvent k)
		{       
		    if(k.getSource()==l1)
					b4.setEnabled(true);
					if(k.getSource()==l4)
					{
					b5.setEnabled(true);
					b6.setEnabled(true);
				    }

		}
		    public void windowClosing(WindowEvent e)
    {   
        exit();
    }
	public static void main(String args[])
	{
			Notepad n=new Notepad();
	}
}