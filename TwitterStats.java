
public class TwitterStats implements Visitor {

	private int userTotal = 0;
	private int groupTotal = 0;
	private int messageTotal = 0;
	private int positiveMessages = 0;
	private double positivePercentage = 0.0;
	
	
	@Override
	public void visit(AdminControlUI admin) {		
		groupTotal++;
	}

	@Override
	public void visit(User user) {
		// TODO Auto-generated method stub
		userTotal++;
	}

	@Override
	public void visit(UserUI userUI) {
		setMessageTotal(messageTotal++);
		
	}
	
	public void setMessageTotal(int i){
		messageTotal = i;
		System.out.println(messageTotal);
	}
	
	@Override
	public void visit(PositiveMessages posMsg) {
		positiveMessages++;		
	}
	

	/**
	 * @return the userTotal
	 */
	public int getUserTotal() {
		return userTotal;
	}


	/**
	 * @return the groupTotal
	 */
	public int getGroupTotal() {
		return groupTotal;
	}

	public int getMessageTotal() {
		return messageTotal;
	}


	public void calculatePositivePercentage(){
		double posMsg = (double)getPositiveMessages();
		double totalMsg = (double)getMessageTotal();
		positivePercentage = (posMsg/totalMsg) * 100.0;
	}
	
	public double getPositivePercentage() {
		calculatePositivePercentage();
		return positivePercentage;
	}
	

	/**
	 * @return the positiveMessages
	 */
	public int getPositiveMessages() {
		return positiveMessages;
	}





}
