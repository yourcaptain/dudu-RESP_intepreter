/**
 * 
 */
package com.yesdata.RESP_intepreter;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author yuanaiqing
 *
 */
public class DefaultInterpreterTest extends TestCase {

	IIntepreter defaultInterpreter = null;
	/**
	 * @param name
	 */
	public DefaultInterpreterTest(String name) {
		super(name);
	}
	
	/**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( DefaultInterpreterTest.class );
    }

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		defaultInterpreter = new DefaultRespIntepreter();
	}
    
	
    public void testFormatCommand(){
    	String command = "";
    	try {
    		command = defaultInterpreter.FormatCommand("INFO");    
    		//System.out.println(command);
    	}
    	catch(Exception ex) {
    		ex.printStackTrace();
    	}

    	assertEquals("*1\r\n$4\r\nINFO\r\n", command);
    }
    
    public void testFormatCommands(){
    	String command = "";
    	try {
    		List<String> commands = new ArrayList<String>();
    		commands.add("INFO");
    		commands.add("INFO");
    		command = defaultInterpreter.FormatCommand(commands);    
    		//System.out.println(command);
    	}
    	catch(Exception ex) {
    		ex.printStackTrace();
    	}

    	assertEquals("*2\r\n$4\r\nINFO\r\n$4\r\nINFO\r\n", command);
    }
    
    public void testIntepretResponseOfSingleSimpleString() {
    	String response = "+OK\r\n";
    	List<IRespNode> respNodes = new ArrayList<IRespNode>();
    	try {
    		respNodes = defaultInterpreter.IntepretResponse(response);
    	}
    	catch(Exception ex) {
    		ex.printStackTrace();
    	}

    	assertEquals(1, respNodes.size());
    	assertEquals(response, respNodes.get(0).toRespFormatString());
    }
    
    public void testIntepretResponseOfMultipleSimpleString() {
    	String response = "+OK\r\n+NG\r\n";
    	List<IRespNode> respNodes = new ArrayList<IRespNode>();
    	try {
    		respNodes = defaultInterpreter.IntepretResponse(response);
    	}
    	catch(Exception ex) {
    		ex.printStackTrace();
    	}

    	assertEquals(2, respNodes.size());
    	assertEquals(response, respNodes.get(0).toRespFormatString() + respNodes.get(1).toRespFormatString());
    }
    
    public void testIntepretResponseOfSingleErrorString() {
    	String response = "-ERR unknown command 'foobar'\r\n";
    	List<IRespNode> respNodes = new ArrayList<IRespNode>();
    	try {
    		respNodes = defaultInterpreter.IntepretResponse(response);
    	}
    	catch(Exception ex) {
    		ex.printStackTrace();
    	}

    	assertEquals(1, respNodes.size());
    	assertEquals(response, respNodes.get(0).toRespFormatString());
    }
    
    public void testIntepretResponseOfMultiErrorString() {
    	String response = "-ERR unknown command 'foobar'\r\n-WRONGTYPE Operation against a key holding the wrong kind of value\r\n";
    	List<IRespNode> respNodes = new ArrayList<IRespNode>();
    	try {
    		respNodes = defaultInterpreter.IntepretResponse(response);
    	}
    	catch(Exception ex) {
    		ex.printStackTrace();
    	}

    	assertEquals(2, respNodes.size());
    	assertEquals(response, respNodes.get(0).toRespFormatString() + respNodes.get(1).toRespFormatString());
    }

    public void testIntepretResponseOfSingleInteger() {
    	String response = ":100\r\n";
    	List<IRespNode> respNodes = new ArrayList<IRespNode>();
    	try {
    		respNodes = defaultInterpreter.IntepretResponse(response);
    	}
    	catch(Exception ex) {
    		ex.printStackTrace();
    	}

    	assertEquals(1, respNodes.size());
    	assertEquals(response, respNodes.get(0).toRespFormatString());
    }
    
    public void testIntepretResponseOfMultiInteger() {
    	String response = ":100\r\n:200\r\n";
    	List<IRespNode> respNodes = new ArrayList<IRespNode>();
    	try {
    		respNodes = defaultInterpreter.IntepretResponse(response);
    	}
    	catch(Exception ex) {
    		ex.printStackTrace();
    	}

    	assertEquals(2, respNodes.size());
    	assertEquals(response, respNodes.get(0).toRespFormatString() + respNodes.get(1).toRespFormatString());
    }
    

    public void testIntepretResponseOfSingleBulkString() {
    	String response = "$6\r\nfoobar\r\n";
    	List<IRespNode> respNodes = new ArrayList<IRespNode>();
    	try {
    		respNodes = defaultInterpreter.IntepretResponse(response);
    	}
    	catch(Exception ex) {
    		ex.printStackTrace();
    	}

    	assertEquals(1, respNodes.size());
    	assertEquals(response, respNodes.get(0).toRespFormatString());
    }
    
    public void testIntepretResponseOfSingleEmptyBulkString() {
    	String response = "$0\r\n\r\n";
    	List<IRespNode> respNodes = new ArrayList<IRespNode>();
    	try {
    		respNodes = defaultInterpreter.IntepretResponse(response);
    	}
    	catch(Exception ex) {
    		ex.printStackTrace();
    	}

    	assertEquals(1, respNodes.size());
    	assertEquals(response, respNodes.get(0).toRespFormatString());
    }
    
    public void testIntepretResponseOfSingleNullBulkString() {
    	String response = "$-1\r\n";
    	List<IRespNode> respNodes = new ArrayList<IRespNode>();
    	try {
    		respNodes = defaultInterpreter.IntepretResponse(response);
    	}
    	catch(Exception ex) {
    		ex.printStackTrace();
    	}

    	assertEquals(1, respNodes.size());
    	assertEquals(response, respNodes.get(0).toRespFormatString());
    }
    
    public void testIntepretResponseOfMultiBulkString1() {
    	String response = "$6\r\nfoobar\r\n$0\r\n\r\n$-1\r\n";
    	List<IRespNode> respNodes = new ArrayList<IRespNode>();
    	try {
    		respNodes = defaultInterpreter.IntepretResponse(response);
    	}
    	catch(Exception ex) {
    		ex.printStackTrace();
    	}

    	assertEquals(3, respNodes.size());
    	assertEquals(response, respNodes.get(0).toRespFormatString() + respNodes.get(1).toRespFormatString() + respNodes.get(2).toRespFormatString());
    }
    
    public void testIntepretResponseOfMultiBulkString2() {
    	String response = "$-1\r\n$6\r\nfoobar\r\n$0\r\n\r\n";
    	List<IRespNode> respNodes = new ArrayList<IRespNode>();
    	try {
    		respNodes = defaultInterpreter.IntepretResponse(response);
    	}
    	catch(Exception ex) {
    		ex.printStackTrace();
    	}

    	assertEquals(3, respNodes.size());
    	assertEquals(response, respNodes.get(0).toRespFormatString() + respNodes.get(1).toRespFormatString() + respNodes.get(2).toRespFormatString());
    }

    public void testIntepretResponseOfSingleEmptyArray() {
    	String response = "*0\r\n";
    	List<IRespNode> respNodes = new ArrayList<IRespNode>();
    	try {
    		respNodes = defaultInterpreter.IntepretResponse(response);
    	}
    	catch(Exception ex) {
    		ex.printStackTrace();
    	}

    	assertEquals(1, respNodes.size());
    	assertEquals(response, respNodes.get(0).toRespFormatString());
    }
    
    public void testIntepretResponseOf2BulkStringsArray() {
    	String response = "*2\r\n$3\r\nfoo\r\n$3\r\nbar\r\n";
    	List<IRespNode> respNodes = new ArrayList<IRespNode>();
    	try {
    		respNodes = defaultInterpreter.IntepretResponse(response);
    	}
    	catch(Exception ex) {
    		ex.printStackTrace();
    	}

    	assertEquals(1, respNodes.size());
    	assertEquals(response, respNodes.get(0).toRespFormatString());
    }
    
    public void testIntepretResponseOf3IntegersArray() {
    	String response = "*3\r\n:1\r\n:2\r\n:3\r\n";
    	List<IRespNode> respNodes = new ArrayList<IRespNode>();
    	try {
    		respNodes = defaultInterpreter.IntepretResponse(response);
    	}
    	catch(Exception ex) {
    		ex.printStackTrace();
    	}

    	assertEquals(1, respNodes.size());
    	assertEquals(response, respNodes.get(0).toRespFormatString());
    }
    
    public void testIntepretResponseOfMixedTypeArray() {
    	String response = "*5\r\n:1\r\n:2\r\n:3\r\n:4\r\n$6\r\nfoobar\r\n";
    	List<IRespNode> respNodes = new ArrayList<IRespNode>();
    	try {
    		respNodes = defaultInterpreter.IntepretResponse(response);
    	}
    	catch(Exception ex) {
    		ex.printStackTrace();
    	}

    	assertEquals(1, respNodes.size());
    	assertEquals(response, respNodes.get(0).toRespFormatString());
    }
    
    public void testIntepretResponseOfNilArray() {
    	String response = "*-1\r\n";
    	List<IRespNode> respNodes = new ArrayList<IRespNode>();
    	try {
    		respNodes = defaultInterpreter.IntepretResponse(response);
    	}
    	catch(Exception ex) {
    		ex.printStackTrace();
    	}

    	assertEquals(1, respNodes.size());
    	assertEquals(response, respNodes.get(0).toRespFormatString());
    }
    
    public void testIntepretResponseOfArraysOfArrays() {
    	String response = "*2\r\n*3\r\n:1\r\n:2\r\n:3\r\n*2\r\n+Foo\r\n-Bar\r\n";
    	List<IRespNode> respNodes = new ArrayList<IRespNode>();
    	try {
    		respNodes = defaultInterpreter.IntepretResponse(response);
    	}
    	catch(Exception ex) {
    		ex.printStackTrace();
    	}

    	assertEquals(1, respNodes.size());
    	assertEquals(response, respNodes.get(0).toRespFormatString());
    }
}
