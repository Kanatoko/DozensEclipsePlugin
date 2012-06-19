package net.jumperz.app.MDozens.view;

import java.util.*;
import java.util.List;

import net.jumperz.app.MDozens.MContext;
import net.jumperz.app.MDozens.api.MSession;
import net.jumperz.app.MDozens.dialog.MLoginDialog;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.DialogMessageArea;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import net.jumperz.util.*;

public class MRecordView
extends MAbstractView
implements MObserver1
{
private Action loginAction;
private MContext context = MContext.getInstance();

public static MRecordView instance;
//--------------------------------------------------------------------------------
public MRecordView()
{
instance = this;
context.register1( instance );
}
//--------------------------------------------------------------------------------
public void init2()
{
parent.setLayout( new FormLayout() );

table = new Table( parent, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI );
table.addMouseListener(new MouseAdapter() {
	@Override
	public void mouseDown(MouseEvent e) {
	onMouseDown( e );
	}
});
table.setHeaderVisible( true );
table.setLinesVisible( true );
FormData d1 = new FormData();
d1.top = new FormAttachment(0);
d1.left = new FormAttachment( 0, 1 );
d1.right = new FormAttachment( 100, -1 );
d1.bottom = new FormAttachment( 100, -1 );
table.setLayoutData( d1 );

menuManager = new MenuManager();
Menu contextMenu = menuManager.createContextMenu( table );
table.setMenu( contextMenu );
}
//--------------------------------------------------------------------------------
private void onMouseDown( MouseEvent e )
{

}
//--------------------------------------------------------------------------------
public void setFocus()
{
}
//--------------------------------------------------------------------------------
public void update()
{
int state = context.getState();
}
//--------------------------------------------------------------------------------
}
