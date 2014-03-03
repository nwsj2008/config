package edu.bjtu.opinion;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class OutputFrame implements ShellListener {

	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private Text textArea = null;
	private boolean isClose = false;
	private MainFrame frm = null;
		
	public OutputFrame(){
		createSShell();
	}
	
	public void showFirst(MainFrame frm){
		this.frm = frm;
		Display display = Display.getDefault();
		Point location = frm.getLocation();
		location.y += 480;
		sShell.setLocation(location);
		sShell.open();
		while (!sShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
	
	public void show(){
		Point location = frm.getLocation();
		location.y += 480;
		sShell.setLocation(location);
		sShell.setVisible(true);
	}
	
	public void close(){
		isClose = true;
		sShell.close();
	}
	
	public Text getComponent(){
		return textArea;
	}

	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		sShell = new Shell(SWT.SHELL_TRIM);
		sShell.setText("输出窗口");
		sShell.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/res/output.png")));
		sShell.setMaximized(false);
		sShell.setSize(new Point(640, 200));
		sShell.setLayout(new FillLayout());
		sShell.addShellListener(this);
		textArea = new Text(sShell, SWT.MULTI | SWT.V_SCROLL);
		textArea.setEditable(true);
	}

	@Override
	public void shellActivated(ShellEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shellClosed(ShellEvent e) {
		if(!isClose){
			sShell.setVisible(false);
			e.doit = false;
		}		
	}

	@Override
	public void shellDeactivated(ShellEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shellDeiconified(ShellEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shellIconified(ShellEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
