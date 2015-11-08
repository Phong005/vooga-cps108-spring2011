# Introduction #

The network package provides an easy way for the user to send and receive object through the network. There are two ways for you to setup the connection, either a local area network connection or an Internet network connection:

INetworkEngine e = new LocalNetworkEngine;

INetworkEngine e = new InternetNetworkEngine;

For the LocalNetworkEngine, when a host is created, it would listen on a certain port and respond to the request from the clients, so that when the client want to search for the host, it just call the search function, which broadcast an UDP request package in the local area network, the hosts available on the network would catch the request and respond to the request so that the client would know which hosts are available on the local area network.

For the InternetNetworkEngine, there would be an Internet Server running all the time, which would mantain a list of all the available hosts. When a host is created, it would send the information of itself (currently only ip address) to the Internet Server, so that the Internet Server add the ip address to the available hosts. When the client wants to search for the host, it just connects to the Internet Server and asks for list of available servers.

After you choose which connection you want to use, you can then use the createHost to create a host, use the searchHost to search available hosts on the network, use the connect to make the connection, use the send and update to send and receive Object.


# Details #

The interface for the networkEngine is shown as follows,

```

public interface INetworkEngine {
	/**
	 * connects to a server with ip IP d
	 * 
	 * @param IP
	 * @return false if successfully created
	 */
	boolean connect(String IP);

	/**
	 * Create a host, if the argument is true, then there will be connections
	 * between each clients connecting to this host. The default argument should
	 * be false.
	 * 
	 * @param name
	 * @return false if successfully created
	 */
	boolean createHost(boolean fullyConnect);

	/**
	 * 
	 * @return the list of object received from the network since last update
	 */
	List<Object> update();

	/**
	 * Search for the hosts on the network
	 * 
	 * @return
	 */
	List<ConnectInfo> searchHost();

	/**
	 * Disconnect from all connections.
	 */
	void disconnect();

	/**
	 * Send an serializable obj to all its connections
	 * 
	 * @param obj
	 * @return
	 */
	boolean send(Serializable obj);

	/**
	 * check whether currently is in connection or disconnected
	 * 
	 * @return true if currently connecting to other computers
	 */
	boolean isConnected();

	/**
	 * Return the List of connection information this computer are connecting to
	 * 
	 * @return
	 */
	List<ConnectInfo> getConnectionInfo();

	/**
	 * Return information of this computer.
	 * 
	 */
	ConnectInfo getMyInfo();
}

```