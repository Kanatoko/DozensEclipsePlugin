package net.jumperz.app.MDozens;

import net.jumperz.app.MDozens.api.*;

public class MContext
extends MAbstractLogAgent
implements MConstants
{
private static MContext instance = new MContext();

private MSession session;
//--------------------------------------------------------------------------------
private MContext()
{
//singleton
}
//--------------------------------------------------------------------------------
public static MContext getInstance()
{
return instance;
}
//--------------------------------------------------------------------------------
public MSession getSession()
{
return session;
}
//--------------------------------------------------------------------------------
public void login( String user, String apiKey )
{
try
	{
	session = new MSession( user, apiKey );
	session.init();
	}
catch( Exception e )
	{
	handleError( e );
	}
}
//--------------------------------------------------------------------------------
public void handleError( Exception e )
{
info( e );
}
//--------------------------------------------------------------------------------

}