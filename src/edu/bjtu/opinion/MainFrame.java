package edu.bjtu.opinion;

import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.opinion.preprocess.Tian.ProcessTianYa;

import edu.opinion.preprocess.bjtu.PretreatMain;

/**
 * 
 * @author ch
 * 
 */
public class MainFrame implements MouseListener, Listener, ShellListener {

	private int type = 0;
	private int totalPage = 0;
	private int currPage = 0;
	private boolean isTypeChange = true;

	private Shell sShell = null; // @jve:decl-index=0:visual-constraint="19,17"
	private Button buttonStart = null;
	private Button buttonResult = null;
	private Table tableResult = null;
	private Text textLocation = null;
	private Button buttonLast = null;
	private Button buttonNext = null;
	private Button buttonBBS = null;
	private Button buttonNews = null;
	private CLabel cLabel = null;
	private Button buttonFirst = null;
	private Button buttonPrev = null;

	private OutputFrame frm = new OutputFrame(); // @jve:decl-index=0:
	private Button buttonConsole = null;

	public MainFrame() {
		Display display = Display.getDefault();
		createSShell();
		sShell.open();
		System.setOut(new GUIPrintStream(System.out, frm.getComponent()));
		frm.showFirst(this);
		while (!sShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	public Point getLocation() {
		return sShell.getLocation();
	}

	private void createSShell() {
		sShell = new Shell(Display.getDefault(), SWT.CLOSE);
		sShell.setText("信息抽取");
		sShell.setImage(new Image(Display.getCurrent(), getClass()
				.getResourceAsStream("/res/ico.png")));
		sShell.setSize(new Point(640, 480));
		sShell.setLayout(null);
		sShell.addShellListener(this);
		textLocation = new Text(sShell, SWT.BORDER);
		textLocation.setBounds(new Rectangle(5, 8, 366, 23));
		buttonStart = new Button(sShell, SWT.NONE);
		buttonStart.setBounds(new Rectangle(381, 8, 75, 25));
		buttonStart.setText("开始抽取");
		buttonStart.addMouseListener(this);
		buttonConsole = new Button(sShell, SWT.NONE);
		buttonConsole.setBounds(new Rectangle(465, 8, 75, 25));
		buttonConsole.setText("输出窗口");
		buttonConsole.addMouseListener(this);
		buttonResult = new Button(sShell, SWT.NONE);
		buttonResult.setBounds(new Rectangle(549, 8, 75, 25));
		buttonResult.setText("抽取结果");
		buttonResult.addMouseListener(this);
		buttonFirst = new Button(sShell, SWT.NONE);
		buttonFirst.setBounds(new Rectangle(297, 37, 75, 25));
		buttonFirst.setText("首页");
		buttonFirst.addMouseListener(this);
		buttonPrev = new Button(sShell, SWT.NONE);
		buttonPrev.setBounds(new Rectangle(381, 37, 75, 25));
		buttonPrev.setText("上一页");
		buttonPrev.addMouseListener(this);
		buttonNext = new Button(sShell, SWT.NONE);
		buttonNext.setBounds(new Rectangle(465, 37, 75, 25));
		buttonNext.setText("下一页");
		buttonNext.addMouseListener(this);
		buttonLast = new Button(sShell, SWT.NONE);
		buttonLast.setBounds(new Rectangle(549, 37, 75, 25));
		buttonLast.setText("尾页");
		buttonLast.addMouseListener(this);
		buttonBBS = new Button(sShell, SWT.NONE);
		buttonBBS.setBounds(new Rectangle(5, 37, 75, 25));
		buttonBBS.setText("论坛");
		buttonBBS.addMouseListener(this);
		buttonNews = new Button(sShell, SWT.NONE);
		buttonNews.setBounds(new Rectangle(89, 37, 75, 25));
		buttonNews.setText("新闻");
		buttonNews.addMouseListener(this);
		cLabel = new CLabel(sShell, SWT.NONE);
		cLabel.setText("共0页 第0页");
		cLabel.setBounds(new Rectangle(178, 40, 110, 18));

		// table to display the extraction result
		tableResult = new Table(sShell, SWT.FULL_SELECTION | SWT.BORDER
				| SWT.V_SCROLL | SWT.H_SCROLL);
		tableResult.setHeaderVisible(true);
		tableResult.setLinesVisible(true);
		tableResult.setToolTipText("双击查看详细信息");
		tableResult.setBounds(new Rectangle(1, 65, 630, 386));
		tableResult.setLayoutData(new GridData(GridData.FILL_BOTH));
		tableResult.addListener(SWT.MouseDoubleClick, this);
		int[] widths = new int[] { 30, 100, 100, 100, 100, 100 };
		String[] texts = new String[] { "ID", "标题", "作者", "时间", "内容", "地址" };
		int columnCount = widths.length;
		for (int index = 0; index < columnCount; index++) {
			TableColumn column = new TableColumn(tableResult, SWT.NONE);
			column.setWidth(widths[index]);
			column.setText(texts[index]);
			// column.pack();
		}
	}

	public void startExtraction(String location) {
		new Thread(new Runnable() {
			public void run() {
				 PretreatMain.pretreatMainBBS();
				 ProcessTianYa pty = new ProcessTianYa();
				 try {
					pty.PreTreatBBS();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * 
	 * @param type
	 *            0:bbs post, 1: bbs reply, 2: news post, 3:news reply
	 * @param page
	 */
	public void showExtraction() {
		DBUtils utils = DBUtils.getInstance();
		if (isTypeChange) {
			totalPage = utils.getPages(type, "");
			isTypeChange = false;
			currPage = 0;
		}
		List<String[]> list = utils.getPosts(type, currPage);
		tableResult.removeAll();
		for (String[] texts : list) {
			TableItem item = new TableItem(tableResult, SWT.NONE);
			item.setText(texts);
		}

		if (totalPage == 0) {
			cLabel.setText("共0页 第0页");
		} else {
			cLabel.setText("共" + totalPage + "页  第" + (currPage + 1) + "页");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swt.events.MouseAdapter#mouseUp(org.eclipse.swt.events.MouseEvent
	 * )
	 */
	@Override
	public void mouseUp(MouseEvent e) {
		if (e.widget.equals(buttonStart)) {// action when click buttonStart
			String location = textLocation.getText();
			startExtraction(location);
		} else if (e.widget.equals(buttonConsole)) {// action when click
			// buttonConsole
			frm.show();
		} else if (e.widget.equals(buttonResult)) {// action when click
													// buttonResult
			showExtraction();
		} else if (e.widget.equals(buttonBBS)) {// action when click buttonBBS
			type = 0;
			isTypeChange = true;
			showExtraction();
		} else if (e.widget.equals(buttonNews)) {// action when click buttonNews
			type = 2;
			isTypeChange = true;
			showExtraction();
		} else if (e.widget.equals(buttonFirst)) {// action when click
													// buttonFirst
			currPage = 0;
			showExtraction();
		} else if (e.widget.equals(buttonPrev)) {// action when click buttonPrev
			if (currPage > 0) {
				currPage--;
			}
			showExtraction();
		} else if (e.widget.equals(buttonNext)) {// action when click buttonNext
			if (currPage < totalPage - 1) {
				currPage++;
			}
			showExtraction();
		} else if (e.widget.equals(buttonLast)) {// action when click buttonLast
			if (totalPage > 0)
				currPage = totalPage - 1;
			showExtraction();
		} else {

		}

	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {

	}

	@Override
	public void mouseDown(MouseEvent arg0) {

	}

	@Override
	public void handleEvent(Event e) {
		if (e.widget.equals(tableResult)) {
			if (tableResult.getSelectionCount() > 0) {
				TableItem item = tableResult.getSelection()[0];
				String[] texts = new String[] { item.getText(0),
						item.getText(1), item.getText(2), item.getText(3),
						item.getText(4), item.getText(5), item.getText(6) };
				DetailFrame frm = new DetailFrame(texts, type);
				frm.show();
			}
		}

	}

	public static void main(String[] args) {
		System.out.println(System.getProperty("java.library.path", ""));
		new MainFrame();
	}

	@Override
	public void shellActivated(ShellEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void shellClosed(ShellEvent arg0) {
		frm.close();
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

} // @jve:decl-index=0:visual-constraint="685,81"
