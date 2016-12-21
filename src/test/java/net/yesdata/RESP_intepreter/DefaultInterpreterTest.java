/**
 * 
 */
package net.yesdata.RESP_intepreter;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import net.yesdata.RESP_intepreter.DefaultRespInterpreter;
import net.yesdata.RESP_intepreter.IInterpreter;
import net.yesdata.RESP_intepreter.IRespNode;

/**
 * @author yuanaiqing
 *
 */
public class DefaultInterpreterTest extends TestCase {

	IInterpreter defaultInterpreter = null;
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
		
		defaultInterpreter = new DefaultRespInterpreter();
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

    	assertEquals("*1\r\n$4\r\nINFO\r\n*1\r\n$4\r\nINFO\r\n", command);
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
    
    public void testIntepretResponseOfInfoBulkString() {
    	String response = "$1877\r\n# Server\r\nredis_version:2.8.19\r\nredis_git_sha1:00000000\r\nredis_git_dirty:0\r\nredis_build_id:cb51525bdf115b3e\r\nredis_mode:standalone\r\nos:Linux 2.6.32-431.el6.x86_64 x86_64\r\narch_bits:64\r\nmultiplexing_api:epoll\r\ngcc_version:4.4.7\r\nprocess_id:5110\r\nrun_id:aa8e6a10fd8d0b4ac34a52841d1f0834604625ba\r\ntcp_port:6379\r\nuptime_in_seconds:424599\r\nuptime_in_days:4\r\nhz:10\r\nlru_clock:5816822\r\nconfig_file:\r\n\r\n# Clients\r\nconnected_clients:1\r\nclient_longest_output_list:0\r\nclient_biggest_input_buf:2\r\nblocked_clients:0\r\n\r\n# Memory\r\nused_memory:810056\r\nused_memory_human:791.07K\r\nused_memory_rss:2093056\r\nused_memory_peak:811080\r\nused_memory_peak_human:792.07K\r\nused_memory_lua:35840\r\nmem_fragmentation_ratio:2.58\r\nmem_allocator:jemalloc-3.6.0\r\n\r\n# Persistence\r\nloading:0\r\nrdb_changes_since_last_save:0\r\nrdb_bgsave_in_progress:0\r\nrdb_last_save_time:1481790832\r\nrdb_last_bgsave_status:ok\r\nrdb_last_bgsave_time_sec:0\r\nrdb_current_bgsave_time_sec:-1\r\naof_enabled:0\r\naof_rewrite_in_progress:0\r\naof_rewrite_scheduled:0\r\naof_last_rewrite_time_sec:-1\r\naof_current_rewrite_time_sec:-1\r\naof_last_bgrewrite_status:ok\r\naof_last_write_status:ok\r\n\r\n# Stats\r\ntotal_connections_received:36\r\ntotal_commands_processed:81\r\ninstantaneous_ops_per_sec:0\r\ntotal_net_input_bytes:2114\r\ntotal_net_output_bytes:27474\r\ninstantaneous_input_kbps:0.00\r\ninstantaneous_output_kbps:0.00\r\nrejected_connections:0\r\nsync_full:0\r\nsync_partial_ok:0\r\nsync_partial_err:0\r\nexpired_keys:0\r\nevicted_keys:0\r\nkeyspace_hits:29\r\nkeyspace_misses:0\r\npubsub_channels:0\r\npubsub_patterns:0\r\nlatest_fork_usec:113\r\n\r\n# Replication\r\nrole:master\r\nconnected_slaves:0\r\nmaster_repl_offset:0\r\nrepl_backlog_active:0\r\nrepl_backlog_size:1048576\r\nrepl_backlog_first_byte_offset:0\r\nrepl_backlog_histlen:0\r\n\r\n# CPU\r\nused_cpu_sys:149.81\r\nused_cpu_user:66.96\r\nused_cpu_sys_children:0.00\r\nused_cpu_user_children:0.00\r\n\r\n# Keyspace\r\ndb0:keys=3,expires=0,avg_ttl=0\r\n\r\n";
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
