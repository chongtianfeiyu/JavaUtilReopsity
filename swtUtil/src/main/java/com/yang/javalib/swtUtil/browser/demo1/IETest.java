package com.yang.javalib.swtUtil.browser.demo1;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleAutomation;
import org.eclipse.swt.ole.win32.OleControlSite;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.ole.win32.Variant;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
public class IETest  extends Shell {

    /**
     * Launch the application
     * 
     * @param args
     */
    public static void main(String args[]) {
        Display display = Display.getDefault();
        IETest shell = new IETest(display);
        shell.setMaximized(true);
        shell.setLayout(new FillLayout());
        Menu bar = new Menu(shell, SWT.BAR);
        shell.setMenuBar(bar);
        
        OleFrame frame = new OleFrame(shell, SWT.NONE);
        OleControlSite clientsite = null;
        OleAutomation browser = null;
        try {
        	 
            clientsite = new OleControlSite(frame, SWT.NONE, "Shell.Explorer");
            browser = new OleAutomation(clientsite);
            clientsite.doVerb(OLE.OLEIVERB_INPLACEACTIVATE);
            shell.open();
            int[] browserIDs = browser.getIDsOfNames(new String[] { "Navigate",
                    "URL" });
            Variant[] address = new Variant[] { new Variant(
                    "http://blog.csdn.net/bovy") };
            browser.invoke(browserIDs[0], address, new int[] { browserIDs[1] });
        } catch (Exception ex) {
            System.out.println("Failed to create IE! " + ex.getMessage());
            return;
        }
        while (shell != null && !shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        browser.dispose();
        display.dispose();
    }

    /** */
    /**
     * Create the shell
     * 
     * @param display
     * @param style
     */
    public IETest(Display display) {
        super(display);
        createContents();
    }

    /** */
    /**
     * Create contents of the window
     */
    protected void createContents() {
        setText("如何在Java中嵌入IE？http://blog.csdn.net/bovy");
        setMaximized(true);
        //
    }

    @Override
    protected void checkSubclass() {
        // Disable the check that prevents subclassing of SWT components
    }
}
