package bankbone;

public class Transaction implements java.io.Serializable {
	
	private static final long serialVersionUID = -499292808727431689L;
	//serialVersionUID for Serialization
	
	//Data Members
	int transactionID;			//Default -1 to show not assigned yet
	Account sender, receiver;	//Set either of these as '0' when 'Bank Cash' Account to be used as sender/receiver
	int senderAccNo, receiverAccNo;
	int amount;
	boolean interAcc;			//What was this to be used for......recognition of transfer? :: 1 when Transfer rest 0
	int success;				//0 if less bal, etc
	
	public Transaction() {
		transactionID = -1;
		sender = new Account();
		receiver = new Account();
		amount = 0;
		interAcc = false;
		success = 1;		//Unless Trans cancelled due to less bal or something
	}
	public Transaction(int senderAcc, int receiverAcc, int amount, boolean interAcc) {
		this.transactionID = -1;
		this.sender = new Account(senderAcc);
		this.senderAccNo = sender.accNo;
		this.receiver = new Account(receiverAcc);
		this.receiverAccNo = receiver.accNo;
		this.amount = amount;
		this.interAcc = interAcc;
		this.success = 1;		//Unless Trans cancelled due to less bal or something
	}
	public Transaction(Transaction trans) {		//Copy Constructor
		this.transactionID = trans.transactionID;
		this.sender = new Account(trans.sender);
		this.senderAccNo = sender.accNo;
		this.receiver = new Account(trans.receiver);
		this.receiverAccNo = receiver.accNo;
		this.amount = trans.amount;
		this.interAcc = trans.interAcc;
		this.success = trans.success;
	}
	
	//Setter
	public void setTransaction(int transID, int senderAcc, int receiverAcc, int amount, boolean interAcc, int success) {
		this.transactionID = transID;
		this.sender = new Account(senderAcc);
		this.senderAccNo = senderAcc;
		this.receiver = new Account(receiverAcc);
		this.receiverAccNo = receiverAcc;
		this.amount = amount;
		this.interAcc = interAcc;
		this.success = success;		//Unless Trans cancelled due to less bal or something
	}

	
	//Printer
	public void printTransactionResult() {
		System.out.println("Transaction Amt : " + this.amount);
		System.out.println("Transaction Success : " + this.success);
		System.out.println("Sender Account : " + senderAccNo);
		System.out.println("Receiver Account : " + receiverAccNo);
	}
	public void printTransaction() {
		System.out.println("Transaction ID : " + this.transactionID);
		System.out.println("Transaction Amt : " + this.amount);
		System.out.println("Transaction Success : " + this.success);
		System.out.println("Sender Account : " + senderAccNo);
		System.out.println("Receiver Account : " + receiverAccNo);
	}
	
}
