
import org.jboss.netty.bootstrap.ClientBootstrap;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class facebookcql {
	
	private static Cluster cluster;
	private static Session session;
	
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
	private void printprofileinfo()
	{
		ResultSet results = session.execute("SELECT * FROM facebooklite.users "+ 
	    "WHERE username='tcodd';");
		System.out.println("\n Found users :\n---------------------------");
		
		for(Row row:results){
			System.out.println(" FirstName: "+ row.getString("firstname") + 
					"\t \n LastName: "+ row.getString("lastname") +
					"\t \n Email: "+row.getString("email") +
					  "\t \n Age: "+row.getInt("age"));
		}
	}
	public static void main(String[] args) {
		facebookcql client = new facebookcql();
		client.connect("127.0.0.1");
		session = cluster.connect();
		client.printprofileinfo();
		client.close();
		

	}

}
