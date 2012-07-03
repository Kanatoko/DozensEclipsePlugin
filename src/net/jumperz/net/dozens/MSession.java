package net.jumperz.net.dozens;

import net.jumperz.app.MDozens.*;
import net.jumperz.util.MStreamUtil;
import net.arnx.jsonic.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class MSession
extends MAbstractLogAgent
implements MConstants
{
private String user;
private String apiKey;

private String authToken;
//--------------------------------------------------------------------------------
public MSession( String user, String apiKey )
{
this.user = user;
this.apiKey = apiKey;
}
//--------------------------------------------------------------------------------
private Map callApi( String apiPath, Map header, String body )
throws IOException
{
debug( "calling api :" + apiPath );
URL url = new URL( "http://dozens.jp" + apiPath );
URLConnection conn = url.openConnection();
Iterator p = header.keySet().iterator();
while( p.hasNext() )
	{
	String key = ( String )p.next();
	String value = ( String )header.get( key );
	conn.addRequestProperty( key, value );
	//debug( key + ":" + value );
	}
conn.addRequestProperty( "Connection", "close" );

if( body != null )
	{
	conn.setDoOutput( true );
	conn.setRequestProperty( "Content-Type", "application/json" );
	OutputStream out = conn.getOutputStream();
	out.write( body.getBytes( "ISO-8859-1" ) );
	out.close();
	}

HttpURLConnection urlConn = ( HttpURLConnection )conn;
int statusCode = urlConn.getResponseCode();
if( statusCode != 200 )
	{
	throw new IOException( "Auth failed: statusCode=" + statusCode );
	}

String responseBody = MStreamUtil.streamToString( conn.getInputStream() );
Map result = ( Map )JSON.decode( responseBody );
return result;
}
//--------------------------------------------------------------------------------
private Map callApi( String apiPath, Map header )
throws IOException
{
return callApi( apiPath, header, null );
}
//--------------------------------------------------------------------------------
public void init()
throws IOException
{
Map header = new HashMap();
header.put( "X-Auth-User", user );
header.put( "X-Auth-Key", apiKey );

Map result = callApi( "/api/authorize.json", header );
if( !result.containsKey( "auth_token" ) )
	{
	throw new IOException( "Auth failed: auth_token not found. " + result );
	}
authToken = ( String )result.get( "auth_token" );
}
//--------------------------------------------------------------------------------
public Map getZone()
throws IOException
{
Map header = new HashMap();
header.put( "X-Auth-Token", authToken );
Map result = callApi( "/api/zone.json", header );
return result;
}
//--------------------------------------------------------------------------------
public Map getRecord( String zoneName )
throws IOException
{
Map header = new HashMap();
header.put( "X-Auth-Token", authToken );
Map result = callApi( "/api/record/" + zoneName + ".json", header );
return result;
}
//--------------------------------------------------------------------------------
public Map updateRecord( String recordId, String fieldName, String value )
throws IOException
{
// /api/record/update/{record_id}.json
Map data = new HashMap();
data.put( fieldName, value );

//debug
//data.put( "prio", new Integer( 10 ) );
//data.put( "ttl", "7200" );

Map header = new HashMap();
header.put( "X-Auth-Token", authToken );
Map result = callApi( "/api/record/update/" + recordId + ".json", header, JSON.encode( data ) );
return result;
}
//--------------------------------------------------------------------------------
}