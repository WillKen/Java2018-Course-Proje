public class player {
	String name;
	private static player instance = null;
	private int score;
	private int money;
	private int hp;
	public player(){
		this.score = 0;
		this.money = 0;
		this.hp = 4;
	}
	
	public static synchronized player getInstance(){
		if(instance==null)
			instance = new player();
		return instance;
	}
	
	public int get_hp(){
		return this.hp;
	}
	
	public int get_money(){
		return this.money;
	}
	
	public int get_score(){
		return this.score;
	}
	
	public void addscore(int score_){
		this.score+=score_;
	}
	
	public void setscore(int score_){
		this.score = score_;
	}
	
	public void addhp(int hp){
		this.hp+=hp;
	}
	
	public void sethp(int hp_){
		this.hp = hp_;
	}
	
	public void addmoney(int money_){
		this.money+=money_;
	}
	
	public void setmoney(int money_){
		this.money = money_;
	}
	
	public void setname(String username){
		name = username;
	}
}
