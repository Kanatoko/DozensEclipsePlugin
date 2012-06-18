package net.jumperz.app.MDozens.view;

import java.util.*;

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

public class MDomainView
extends MAbstractView
implements MObserver1
{
private Table table;
private Action loginAction;
private MContext context = MContext.getInstance();

public static MDomainView instance;
//--------------------------------------------------------------------------------
public MDomainView()
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

loginAction = new Action(){ public void run() {
login();
}};
loginAction.setText( "Login" );
initAction( loginAction, "server_lightning.png", menuManager );
}
//--------------------------------------------------------------------------------
public void onZone( Map domain )
{

}
//--------------------------------------------------------------------------------
private void login()
{
final Map dialogData = new HashMap();
MLoginDialog dialog = new MLoginDialog( shell, dialogData );
dialog.open();

if( dialogData.size() > 0 )
	{
	(new Thread(){ public void run()
	{
	context.login( ( String )dialogData.get( "user" ), ( String )dialogData.get( "apiKey" ) );
	}}).start();
	}
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
if( state == STATE_ZONE_SUCCESS )
	{
	debug( context.getZoneMap() );
	}
}
//--------------------------------------------------------------------------------
}
