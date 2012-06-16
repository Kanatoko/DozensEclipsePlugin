package net.jumperz.app.MDozens.dialog;

import net.jumperz.gui.MSwtUtil;
import net.jumperz.util.MProperties;
import net.jumperz.util.MStringUtil;
import java.util.*;
import net.jumperz.app.MDozens.*;

import org.eclipse.jface.dialogs.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Group;

public class MLoginDialog
extends Dialog
implements MConstants
{
private Text userText, apiText;
private Map dialogData;
//--------------------------------------------------------------------------------
public MLoginDialog( Shell parentShell, Map m )
{
super( parentShell );
setShellStyle( getShellStyle() | SWT.RESIZE );
dialogData = m;
}
//--------------------------------------------------------------------------------
protected void configureShell( Shell newShell )
{
super.configureShell( newShell );
newShell.setText( "Dozens Login" );
Image image = MUtil.getImage( newShell.getDisplay(), "server_lightning.png" );
newShell.setImage( image );
}
//--------------------------------------------------------------------------------
protected void okPressed()
{
dialogData.put( "user",  userText.getText() );
dialogData.put( "apiKey", apiText.getText() );

setReturnCode( OK );
close();
}
//--------------------------------------------------------------------------------
protected Control createDialogArea( Composite parent )
{
Composite composite = (Composite)super.createDialogArea( parent );
composite.setLayout( new FormLayout() );

Group group1 = new Group( composite, SWT.SHADOW_ETCHED_OUT );
FormData d7 = new FormData();
d7.top = new FormAttachment( 0, 6 );
d7.left = new FormAttachment( 0, 3 );
d7.right = new FormAttachment( 100, -3 );
d7.bottom = new FormAttachment( 100, -3 );
group1.setLayoutData( d7 );
group1.setLayout( new FormLayout() );

Label userLabel = new Label(group1, SWT.NONE);
FormData fd_actionLabel = new FormData();
fd_actionLabel.top = new FormAttachment(0, 20);
fd_actionLabel.left = new FormAttachment(0, 8 );
userLabel.setLayoutData(fd_actionLabel);
userLabel.setText("User :");

userText = new Text( group1, SWT.BORDER );
FormData d2 = new FormData();
d2.right = new FormAttachment( 100, -30);
d2.left = new FormAttachment( userLabel, 35 );
//d2.bottom = new FormAttachment(100, -173);
d2.top = new FormAttachment( userLabel, -2, SWT.TOP);
userText.setLayoutData( d2 );

Label apiLabel = new Label(group1, SWT.NONE);
FormData fd_apiLabel = new FormData();
fd_apiLabel.top = new FormAttachment( userLabel, 20, SWT.BOTTOM );
fd_apiLabel.left = new FormAttachment( 0, 8 );
apiLabel.setLayoutData(fd_apiLabel);
apiLabel.setText("API Key :");

apiText = new Text( group1, SWT.BORDER );
FormData d4 = new FormData();
d4.right = new FormAttachment( 100, -19);
d4.left = new FormAttachment( userLabel, 35 );
//d4.bottom = new FormAttachment(100, -173);
d4.top = new FormAttachment( apiLabel, -2, SWT.TOP);
apiText.setLayoutData( d4 );


Composite composite_1 = new Composite( group1, SWT.NONE);
FormData fd_composite_1 = new FormData();
fd_composite_1.bottom = new FormAttachment( 100 );
fd_composite_1.right = new FormAttachment( 0, 400 );
fd_composite_1.top = new FormAttachment( 0, 100 );
composite_1.setLayoutData(fd_composite_1);

return composite;
}
//--------------------------------------------------------------------------------

}
