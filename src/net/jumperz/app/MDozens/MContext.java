package net.jumperz.app.MDozens;

import java.util.*;

import net.jumperz.net.dozens.*;
import net.jumperz.util.*;

public class MContext
extends MAbstractLogAgent
implements MConstants, MSubject1
{
private static MContext instance = new MContext();

private MSession session;
private MSubject1 subject = new MSubject1Impl();

private int state;
private Map zoneMap;
private Map recordMap;
private MProperties prop;
//--------------------------------------------------------------------------------
private MContext()
{
//singleton
}
//--------------------------------------------------------------------------------
public void setProp( MProperties p )
{
prop = p;
}
//--------------------------------------------------------------------------------
public MProperties getProperties()
{
return prop;
}
//--------------------------------------------------------------------------------
public int getState()
{
return state;
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
public Map getZoneMap()
{
return zoneMap;
}
//--------------------------------------------------------------------------------
public Map getRecordMap()
{
return recordMap;
}
//--------------------------------------------------------------------------------
public void updateRecord( String recordId, String fieldName, String value )
{
try
	{
	recordMap = session.updateRecord( recordId, fieldName, value );
	state = STATE_RECORD_SUCCESS;
	notify1();
	}
catch( Exception e )
	{
	handleError( e );
	}
}
//--------------------------------------------------------------------------------
public void login( String user, String apiKey )
{
try
	{
	session = new MSession( user, apiKey );
	session.init();
	
	state = STATE_LOGIN_SUCCESS;
	notify1();
	
	zoneMap = session.getZone();
	state = STATE_ZONE_SUCCESS;
	notify1();
	}
catch( Exception e )
	{
	handleError( e );
	}
}
//--------------------------------------------------------------------------------
public void onDomainSelect( Map domainData )
{
try
	{
	recordMap = session.getRecord( ( String )domainData.get( "name" ) );
	state = STATE_RECORD_SUCCESS;
	notify1();
	}
catch( Exception e )
	{
	handleError( e );
	}
}
//--------------------------------------------------------------------------------
private void cleanup()
{
session = null;
}
//--------------------------------------------------------------------------------
public void handleError( Exception e )
{
info( e );
cleanup();
}
//----------------------------------------------------------------
public void notify1()
{
subject.notify1();
}
//----------------------------------------------------------------
public void register1( MObserver1 observer )
{
subject.register1( observer );
}
//----------------------------------------------------------------
public void removeObservers1()
{
subject.removeObservers1();
}
//----------------------------------------------------------------
public void removeObserver1( MObserver1 observer )
{
subject.removeObserver1( observer );
}
//----------------------------------------------------------------
}