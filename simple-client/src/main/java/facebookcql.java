
import java.util.UUID;

import org.jboss.netty.bootstrap.ClientBootstrap;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;

public class facebookcql {
	
	private static Cluster cluster;
	private static Session session;
	private static UUID userLoginID;
	
	private  void connect(String node)
	{
		cluster = Cluster.builder().addContactPoint(node).build();
		Metadata metadata = cluster.getMetadata();
		System.out.printf("Connected to cluster: %s\n",metadata.getClusterName());
		for(Host host:metadata.getAllHosts())
		{
			System.out.printf("\n Address "+ host.getAddress() + " on Data Center:"+host.getDatacenter() + "\n Rack:" + host.getRack()+" is up \n");
		}
		
	}
	
	private void close()
	{
		System.out.println("Shutting down cluster" + cluster.getMetadata().getClusterName());
	}
	private void getuserID(){
		//based on users login;
		ResultSet res = session.execute("SELECT userid from facebooklite.users "+
		"WHERE username='tcodd';");
		userLoginID=res.one().getUUID("userid");
		System.out.println("*********************************************");
		System.out.println("Users login ID = "+userLoginID);
		
	}
	private void printprofileinfo(UUID userID)
	{
		ResultSet results = session.execute("SELECT * FROM facebooklite.users "+ 
	    "WHERE userid="+userID+";");
		System.out.println("**********************************************");
		
		for(Row row:results){
			System.out.println(" FirstName: "+ row.getString("firstname") + 
					"\t \n LastName: "+ row.getString("lastname") +
					"\t \n Email: "+row.getString("email") +
					  "\t \n Age: "+row.getInt("age"));
		}
		
	}
	private void printusersFriends(UUID userID){
		ResultSet resufrnds = session.execute("SELECT userid2 from facebooklite.friends_relations "+
				"WHERE are_friends = true and  userid1="+userID+";");
		System.out.println("****************************Your Friends and their posts*****************************");
		for (Row row:resufrnds){
			printprofileinfo(row.getUUID("userid2"));
			printusersposts(row.getUUID("userid2"));
		}
	}
	private void printusersposts(UUID userID){
		ResultSet resuposts = session.execute("SELECT POST, post_date, posted_device from facebooklite.posts "+
				"WHERE userid="+userID+";");
				for(Row row:resuposts){
			System.out.println(" Posts: "+ row.getString("POST") + 
					"\t | Date Posted: "+ row.getDate("post_date") + 
					"\t | Posted Device: "+row.getString("posted_device"));
			System.out.println("\n");
		}
	   //System.out.println("\n ************************************************************* \n");
	
	}
	public static void main(String[] args) {
		facebookcql client = new facebookcql();
		client.connect("127.0.0.1");
		session = cluster.connect();
		client.getuserID();
		System.out.println("\n Welcome to Facebooklite :\n---------------------------");
		System.out.println("\n My Profile :\n---------------------------");
		client.printprofileinfo(userLoginID);
		client.printusersposts(userLoginID);
		System.out.println("\n My Friends :\n---------------------------");
		client.printusersFriends(userLoginID);
		client.close();
		

	}

}
