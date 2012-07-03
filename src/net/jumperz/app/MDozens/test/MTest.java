package net.jumperz.app.MDozens.test;

import java.util.*;

import net.jumperz.net.dozens.*;

public class MTest
{
//--------------------------------------------------------------------------------
public static void main( String[] args )
throws Exception
{
if( args.length != 2 )
	{
	System.err.println( "Usage: java net.jumperz.app.MDozens.test.MTest user apiKey" );
	return;
	}

MSession session = new MSession( args[ 0 ], args[ 1 ] );
session.init();
Map zone = session.getZone();
p( zone );
Map record = session.getRecord( "ssldb.info" );
p( record );

Map result = session.updateRecord( "14137", "prio", "15" );
p( result );
}
//--------------------------------------------------------------------------------
public static void p( Object o )
{
System.out.println( o );
}
//--------------------------------------------------------------------------------
}