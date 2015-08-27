
import java.awt.*;
import java.awt.event.*;
import java.util.Hashtable;
import java.util.Observable;
import javax.swing.*;
import java.util.Observer;

public class UserUI implements ActionListener, Observer, Visitable {

	private String userName;
	private String userMessage;
	private Hashtable<String, User> table;
	private PositiveMessages posMsg = new PositiveMessages();
	
	public UserUI(String name, Hashtable<String,User> hashtable){
		this.userName = name;
		this.table = hashtable;
		initialize(userName);
		frame.setVisible(true);
	}

	private JFrame frame;
    private JTextField userID, msg; 
    private JList<String> currentFollowing, newsFeed;
	private DefaultListModel<String> followingList = new DefaultListModel<String>();
	private DefaultListModel<String> tweets = new DefaultListModel<String>();
    private User user = new User(userName);
    private TwitterStats stats = new TwitterStats();
   
	
//	public void UserPage (String userId) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					
//					UserUI window = new UserUI(userId);
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}


	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(String s) {
		frame = new JFrame(s);
		frame.setBounds(300, 300, 400, 400);
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel followUserPanel = new JPanel();
		followUserPanel.setPreferredSize(new Dimension(350,50));
		followUserPanel.setOpaque(true);
		followUserPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		
		JPanel currentFollowingPanel = new JPanel();
		currentFollowingPanel.setPreferredSize(new Dimension(350,100));
		currentFollowingPanel.setOpaque(true);
		currentFollowingPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		
		JPanel postTweetPanel = new JPanel();
		postTweetPanel.setPreferredSize(new Dimension(350,50));
		postTweetPanel.setOpaque(true);
		postTweetPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		
		JPanel newsFeedPanel = new JPanel();
		newsFeedPanel.setPreferredSize(new Dimension(350,100));
		newsFeedPanel.setOpaque(true);
		newsFeedPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		
		userID = new JTextField(10);
		JButton followUser = new JButton("Follow User");
		msg = new JTextField(15);
		JButton postTweet = new JButton("Post Tweet");
		
		
	    currentFollowing = new JList<String> (followingList);	
        JScrollPane jscrlp1 = new JScrollPane(currentFollowing);

	    newsFeed = new JList<String>(tweets);
        JScrollPane jscrlp2 = new JScrollPane(newsFeed);
        
        JLabel jlab1 = new JLabel("Current Following");
        JLabel jlab2 = new JLabel("News Feed");

	    followUser.setActionCommand("Follow User");
	    postTweet.setActionCommand("Post Tweet");
	    
	    userID.addActionListener(this);
	    msg.addActionListener(this);
	    followUser.addActionListener(this);
	    postTweet.addActionListener(this);
	    
	    followUserPanel.add(userID);
	    followUserPanel.add(followUser);
	    currentFollowingPanel.add(jlab1);
	    currentFollowingPanel.add(jscrlp1);
	    postTweetPanel.add(msg);
	    postTweetPanel.add(postTweet);
	    newsFeedPanel.add(jlab2);
	    newsFeedPanel.add(jscrlp2);
	    
	    frame.add(followUserPanel);
	    frame.add(currentFollowingPanel);
	    frame.add(postTweetPanel);
	    frame.add(newsFeedPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Follow User")){
			String id = userID.getText();
			if(AdminControlUI.doesUserExist(id)){
				followingList.addElement(id);
				userID.setText("");
				user.addFollowings(id);
				table.get(id).addFollowers(userName);
				table.get(id).addObserver(AdminControlUI.getHashUIValue(userName));
			}
			else{
				JOptionPane.showMessageDialog(frame, "User does not exist");
			}
		}
		
		else if(e.getActionCommand().equals("Post Tweet")){
			
			String message = msg.getText();
			accept(stats);
			posMsg.checkMessage(message);
			String tweet = userName + ": " + message;
			tweets.addElement(tweet);
			msg.setText("");
			user.addNewsFeed(message);
			table.get(userName).setFeed(message);			
		}		
		
	}

	public void post (String message){
		tweets.addElement(message);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof String){
			userMessage = (String) arg;
			post(userMessage);
		}
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}

